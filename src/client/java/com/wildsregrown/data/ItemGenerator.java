package com.wildsregrown.data;

import com.wildsregrown.render.item.property.ItemOxidationProperty;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.RangeDispatchItemModel;
import net.minecraft.client.render.item.property.numeric.UseDurationProperty;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wildsregrown.WildsRegrown.modid;

public class ItemGenerator {

    private final TextureKey textureKey0 = TextureKey.of("0");
    private final ItemModelGenerator generator;

    public ItemGenerator(ItemModelGenerator itemModelGenerator) {
        this.generator = itemModelGenerator;
    }

    public final void registerHatchet(Item item) {

        Model model = new Model(Optional.of(Identifier.of(modid,"items/tools/hatchet")), Optional.empty(), textureKey0);
        model.upload(ModelIds.getItemModelId(item), new TextureMap().put(textureKey0, Identifier.of(modid, "block/metals/iron_0")), generator.modelCollector);

        ItemModel.Unbaked unbaked0 = ItemModels.basic(ModelIds.getItemModelId(item));
        ItemModel.Unbaked unbaked1 = ItemModels.basic(registerOxidationModel(item, 1, model));
        ItemModel.Unbaked unbaked2 = ItemModels.basic(registerOxidationModel(item, 2, model));
        ItemModel.Unbaked unbaked3 = ItemModels.basic(registerOxidationModel(item, 3, model));
        generator.output.accept(item, ItemModels.condition(ItemModels.usingItemProperty(), ItemModels.rangeDispatch(
                                new ItemOxidationProperty(), 1F, unbaked0,
                                ItemModels.rangeDispatchEntry(unbaked0, 0f),
                                ItemModels.rangeDispatchEntry(unbaked1, 1f),
                                ItemModels.rangeDispatchEntry(unbaked2, 2f),
                                ItemModels.rangeDispatchEntry(unbaked3, 3f)
                        ),
                        unbaked0)
        );
    }

    public final void registerPickAxe(Item item) {

        Model model = new Model(Optional.of(Identifier.of(modid,"items/tools/pickaxe")), Optional.empty(), textureKey0);
        model.upload(ModelIds.getItemModelId(item), new TextureMap().put(textureKey0, Identifier.of(modid, "block/metals/iron_0")), generator.modelCollector);

        ItemModel.Unbaked unbaked0 = ItemModels.basic(ModelIds.getItemModelId(item));
        ItemModel.Unbaked unbaked1 = ItemModels.basic(registerOxidationModel(item, 1, model));
        ItemModel.Unbaked unbaked2 = ItemModels.basic(registerOxidationModel(item, 2, model));
        ItemModel.Unbaked unbaked3 = ItemModels.basic(registerOxidationModel(item, 3, model));
        generator.output.accept(item, ItemModels.condition(ItemModels.usingItemProperty(), ItemModels.rangeDispatch(
                new ItemOxidationProperty(), 1F, unbaked0,
                        ItemModels.rangeDispatchEntry(unbaked0, 0f),
                        ItemModels.rangeDispatchEntry(unbaked1, 1f),
                        ItemModels.rangeDispatchEntry(unbaked2, 2f),
                        ItemModels.rangeDispatchEntry(unbaked3, 3f)
                        ),
                unbaked0)
        );
    }

    public final Identifier registerOxidationModel(Item item, int i, Model model) {
        String suffix = "_" + i;
        return model.upload(ModelIds.getItemSubModelId(item, suffix),
                new TextureMap().put(textureKey0, Identifier.of(modid, "block/metals/iron_" + i)), generator.modelCollector);
    }

}
