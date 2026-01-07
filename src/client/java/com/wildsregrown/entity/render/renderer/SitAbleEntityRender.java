package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.entities.block.SitEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;

public class SitAbleEntityRender extends EntityRenderer<SitEntity, EntityRenderState> {

    public SitAbleEntityRender(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

}