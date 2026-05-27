package com.wildsregrown.entity.blockEntities.renderer;

import com.wildsregrown.entities.block.SitEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class SitAbleEntityRender extends EntityRenderer<SitEntity, EntityRenderState> {

    public SitAbleEntityRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

}