package com.wildsregrown.mixin.client.sodium;

import com.wildsregrown.blocks.render.ITintedBlock;
import net.caffeinemc.mods.sodium.api.util.ColorARGB;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadOrientation;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.MaterialParameters;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.builder.ChunkMeshBufferBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import net.caffeinemc.mods.sodium.client.render.model.AbstractBlockRenderContext;
import net.caffeinemc.mods.sodium.client.render.model.MutableQuadViewImpl;
import net.caffeinemc.mods.sodium.client.render.texture.SpriteFinderCache;
import net.minecraft.client.texture.Sprite;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer", remap = false)
public abstract class BlockRendererMixin extends AbstractBlockRenderContext {

    @Shadow @Final private ChunkVertexEncoder.Vertex[] vertices;

    @Shadow @Final private Vector3f posOffset;

    @Shadow @Nullable protected abstract TerrainRenderPass attemptPassDowngrade(Sprite sprite, TerrainRenderPass pass);

    @Shadow private TranslucentGeometryCollector collector;

    @Shadow private ChunkBuildBuffers buffers;

    @Inject(method = "bufferQuad", at = @At("HEAD"), cancellable = true)
    public void overrideQuadBuffers(MutableQuadViewImpl quad, float[] brightnesses, Material material, CallbackInfo ci)
    {
        if (this.state.getBlock() instanceof ITintedBlock tintedBlock) {
            render(quad, brightnesses, material, tintedBlock.getTint(this.state, quad.getTintIndex()));
            ci.cancel();
        }
    }

    @Unique
    private void render(MutableQuadViewImpl quad, float[] brightnesses, Material material, int tint) {

        ModelQuadOrientation orientation = ModelQuadOrientation.NORMAL;
        ChunkVertexEncoder.Vertex[] vertices = this.vertices;
        Vector3f offset = this.posOffset;

        for (int dstIndex = 0; dstIndex < 4; ++dstIndex) {
            int srcIndice = orientation.getVertexIndex(dstIndex);
            ChunkVertexEncoder.Vertex out = vertices[dstIndex];
            out.x = quad.getX(srcIndice) + offset.x;
            out.y = quad.getY(srcIndice) + offset.y;
            out.z = quad.getZ(srcIndice) + offset.z;
            out.color = ColorARGB.toABGR(tint == -1 ? quad.getColor(srcIndice) : tint);
            out.ao = brightnesses[srcIndice];
            out.u = quad.getTexU(srcIndice);
            out.v = quad.getTexV(srcIndice);
            out.light = quad.getLight(srcIndice);
        }

        Sprite atlasSprite = quad.sprite(SpriteFinderCache.forBlockAtlas());
        int materialBits = material.bits();
        ModelQuadFacing normalFace = quad.normalFace();
        TerrainRenderPass pass = material.pass;
        TerrainRenderPass downgradedPass = this.attemptPassDowngrade(atlasSprite, pass);
        if (downgradedPass != null) {
            pass = downgradedPass;
        }

        if (!pass.isTranslucent() || this.collector == null || !this.collector.appendQuad(vertices, normalFace, quad.getFaceNormal())) {
            if (downgradedPass != null && material == DefaultMaterials.TRANSLUCENT && pass == DefaultTerrainRenderPasses.CUTOUT) {
                materialBits = MaterialParameters.pack(AlphaCutoffParameter.HALF, material.mipped);
            }

            ChunkModelBuilder builder = this.buffers.get(pass);
            ChunkMeshBufferBuilder vertexBuffer = builder.getVertexBuffer(normalFace);
            vertexBuffer.push(vertices, materialBits);
            if (atlasSprite != null) {
                builder.addSprite(atlasSprite);
            }

        }
    }

}