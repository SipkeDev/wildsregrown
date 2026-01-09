package com.wildsregrown.entity.mobs.bandit;

import com.wildsregrown.entities.mob.bandit.Bandit;
import com.wildsregrown.entity.EntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BanditRenderer extends MobEntityRenderer<Bandit, BanditRenderState, BanditModel> {

    public BanditRenderer(EntityRendererFactory.Context context, BanditModel entityModel) {
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
    public Identifier getTexture(BanditRenderState state) {
        return Identifier.of("test");
    }

}