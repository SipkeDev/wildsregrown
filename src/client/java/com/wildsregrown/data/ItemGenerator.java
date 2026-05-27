package com.wildsregrown.data;

import com.wildsregrown.render.item.property.ItemOxidationProperty;
import net.minecraft.client.data.*;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wildsregrown.WildsRegrown.modid;

public class ItemGenerator {

    private final TextureSlot textureKey0 = TextureSlot.create("0");
    private final ItemModelGenerators generator;

    public ItemGenerator(ItemModelGenerators itemModelGenerator) {
        this.generator = itemModelGenerator;
    }

    public final void registerHatchet(Item item) {

        ModelTemplate model = new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(modid,"items/tools/hatchet")), Optional.empty(), textureKey0);
        model.create(ModelLocationUtils.getModelLocation(item), new TextureMapping().put(textureKey0, Identifier.fromNamespaceAndPath(modid, "block/metals/iron_0")), generator.modelOutput);

        ItemModel.Unbaked unbaked0 = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked unbaked1 = ItemModelUtils.plainModel(registerOxidationModel(item, 1, model));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(registerOxidationModel(item, 2, model));
        ItemModel.Unbaked unbaked3 = ItemModelUtils.plainModel(registerOxidationModel(item, 3, model));
        generator.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.isUsingItem(), ItemModelUtils.rangeSelect(
                                new ItemOxidationProperty(), 1F, unbaked0,
                                ItemModelUtils.override(unbaked0, 0f),
                                ItemModelUtils.override(unbaked1, 1f),
                                ItemModelUtils.override(unbaked2, 2f),
                                ItemModelUtils.override(unbaked3, 3f)
                        ),
                        unbaked0)
        );
    }

    public final void registerPickAxe(Item item) {

        ModelTemplate model = new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(modid,"items/tools/pickaxe")), Optional.empty(), textureKey0);
        model.create(ModelLocationUtils.getModelLocation(item), new TextureMapping().put(textureKey0, Identifier.fromNamespaceAndPath(modid, "block/metals/iron_0")), generator.modelOutput);

        ItemModel.Unbaked unbaked0 = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked unbaked1 = ItemModelUtils.plainModel(registerOxidationModel(item, 1, model));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(registerOxidationModel(item, 2, model));
        ItemModel.Unbaked unbaked3 = ItemModelUtils.plainModel(registerOxidationModel(item, 3, model));
        generator.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.isUsingItem(), ItemModelUtils.rangeSelect(
                new ItemOxidationProperty(), 1F, unbaked0,
                        ItemModelUtils.override(unbaked0, 0f),
                        ItemModelUtils.override(unbaked1, 1f),
                        ItemModelUtils.override(unbaked2, 2f),
                        ItemModelUtils.override(unbaked3, 3f)
                        ),
                unbaked0)
        );
    }

    public final Identifier registerOxidationModel(Item item, int i, ModelTemplate model) {
        String suffix = "_" + i;
        return model.create(ModelLocationUtils.getModelLocation(item, suffix),
                new TextureMapping().put(textureKey0, Identifier.fromNamespaceAndPath(modid, "block/metals/iron_" + i)), generator.modelOutput);
    }

}
