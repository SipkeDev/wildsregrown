package com.wildsregrown.entity.render;

import com.wildsregrown.entity.render.renderer.*;
import com.wildsregrown.registries.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ModEntitiesRenderRegistery {

    public static void register(){
        EntityRendererRegistry.register(ModEntities.sitAbleEntity, SitAbleEntityRender::new);

        //Blocks
        BlockEntityRendererFactories.register(ModEntities.structureBlock,               StructureBlockRenderer::new);
        BlockEntityRendererFactories.register(ModEntities.crateEntity,                  CrateRenderer::new);
        BlockEntityRendererFactories.register(ModEntities.tableChestEntity,             TableChestRenderer::new);
        BlockEntityRendererFactories.register(ModEntities.counterShelvesEntity,         CounterShelvesRender::new);
        BlockEntityRendererFactories.register(ModEntities.drawerEntity,                 DrawerRender::new);
        BlockEntityRendererFactories.register(ModEntities.shelvesEntity,                ShelvesRender::new);
        BlockEntityRendererFactories.register(ModEntities.genericSingleStorageEntity,   SingleStorageRender::new);
        BlockEntityRendererFactories.register(ModEntities.itemLootPedestal,             ItemLootPedestalRender::new);
        BlockEntityRendererFactories.register(ModEntities.portableWorkbenchEntity,      PortableWorkbenchRender::new);

    }

}
