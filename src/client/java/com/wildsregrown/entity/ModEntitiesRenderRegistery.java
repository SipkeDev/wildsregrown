package com.wildsregrown.entity;

import com.wildsregrown.entity.blockEntities.renderer.*;
import com.wildsregrown.entity.mobs.bandit.BanditRenderer;
import com.wildsregrown.registries.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class ModEntitiesRenderRegistery {

    public static void register(){
        EntityRendererRegistry.register(ModEntities.sitAbleEntity, SitAbleEntityRender::new);
        EntityRendererRegistry.register(ModEntities.bandit, ctx -> new BanditRenderer(ctx, null));

        //Blocks
        //BlockEntityRenderers.register(ModEntities.structureBlock,               StructureBlockRenderer::new);
        BlockEntityRenderers.register(ModEntities.crateEntity,                  CrateRenderer::new);
        BlockEntityRenderers.register(ModEntities.tableChestEntity,             TableChestRenderer::new);
        BlockEntityRenderers.register(ModEntities.counterShelvesEntity,         CounterShelvesRender::new);
        BlockEntityRenderers.register(ModEntities.shelvesEntity,                ShelvesRender::new);
        BlockEntityRenderers.register(ModEntities.genericSingleStorageEntity,   SingleStorageRender::new);
        BlockEntityRenderers.register(ModEntities.itemLootPedestal,             ItemLootPedestalRender::new);
        BlockEntityRenderers.register(ModEntities.portableWorkbenchEntity,      PortableWorkbenchRender::new);

    }

}
