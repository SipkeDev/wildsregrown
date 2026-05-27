package com.wildsregrown.entity.mobs.bandit;

import com.wildsregrown.entities.mob.bandit.Bandit;
import com.wildsregrown.entity.EntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class BanditRenderer extends MobRenderer<Bandit, BanditRenderState, BanditModel> {

    public BanditRenderer(EntityRendererProvider.Context context, BanditModel entityModel) {
        super(context, entityModel,1f);
    }

    //public BanditRenderer(EntityRendererFactory.Context context) {
        //super(context, new BanditModel(context.getPart(EntityModelLayers.bandit)), 1F);
    //}

    @Override
    public BanditRenderState createRenderState() {
        return new BanditRenderState();
    }

    @Override
    public Identifier getTextureLocation(BanditRenderState livingEntityRenderState) {
        return Identifier.parse("test");
    }
}