package com.wildsregrown.gui.radial.stack;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.gui.radial.stack.item.HatchetScreenStack;
import com.wildsregrown.gui.radial.stack.item.PickAxeScreenStack;
import com.wildsregrown.gui.radial.stack.item.StoneChiselScreen;
import com.wildsregrown.gui.radial.stack.item.SwordScreenStack;
import com.wildsregrown.gui.radial.stack.stacks.BlockScreenStack;
import com.wildsregrown.gui.radial.stack.stacks.PropertyScreenStack;
import com.wildsregrown.items.tools.Chisel;
import com.wildsregrown.items.tools.Hatchet;
import com.wildsregrown.items.tools.Pickaxe;
import com.wildsregrown.items.weapons.ScorpionSword;
import com.wildsregrown.items.weapons.Sword;
import com.wildsregrown.items.weapons.TwoHandedSword;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StackBuilder {

    public static ScreenStack[] items = new ScreenStack[]{
            new SwordScreenStack(),
            new PickAxeScreenStack(),
            new HatchetScreenStack(),
            new StoneChiselScreen()
    };

    public static ScreenStack buildItem(Item item){

        if (item instanceof Sword || item instanceof TwoHandedSword || item instanceof ScorpionSword){
            return items[0];
        }
        if (item instanceof Pickaxe){
            return items[1];
        }
        if (item instanceof Hatchet){
            return items[2];
        }
        if (item instanceof Chisel){
            return items[3];
        }

        return null;
    }

    private static final List<Property> survivalWhitelist = List.of(new Property[]{
            Properties.LAYERS,
            Properties.FACING,
            Properties.HORIZONTAL_FACING,
            Properties.AXIS,
            Properties.HORIZONTAL_AXIS,
            ModProperties.VARIATIONS_2,
            ModProperties.VARIATIONS_3,
            ModProperties.VARIATIONS_4,
            ModProperties.VARIATIONS_5,
            ModProperties.VARIATIONS_8,
            Properties.BLOCK_FACE,
    });

    public static BlockScreenStack buildBlockState(Block block, boolean creative) {
        ArrayList<PropertyScreenStack> properties = new ArrayList<>();
        for (Property property : block.getDefaultState().getProperties()){
            if (!(creative || survivalWhitelist.contains(property))) {continue;}
            PropertyScreenStack stack = buildPropertyStack(property);
            properties.add(stack);
        }

        ScreenEntry[] items = new ScreenEntry[properties.size()];
        int i = 0;
        float steps = 1f/properties.size();
        for (PropertyScreenStack stack : properties){
            float brightness = 0.25f + 0.5f*(i*steps);
            items[i] = new ScreenEntry(StringUtils.capitalize(stack.property.getName().toLowerCase().replace('_', ' ')), Color.HSBtoRGB(0f,0f, brightness));
            i++;
        }

        BlockScreenStack stack = new BlockScreenStack(block, items);
        stack.children.addAll(properties);
        properties.clear();

        return stack;
    }

    private static PropertyScreenStack buildPropertyStack(Property<?> property) {

        int size = property.getValues().size();
        final float steps = 1f/size;
        final ScreenEntry[] items = new ScreenEntry[size];

        if (property == ModProperties.LINSEED_PAINT) {
            for (int i = 0; i < size; i++) {
                items[i] = new ScreenEntry(getName(property, i), LinSeedPaintable.values()[i].getRGB());
            }
        }else {
            for (int i = 0; i < size; i++) {
                float brightness = 0.25f + (0.5f * (i * steps));
                items[i] = new ScreenEntry(
                        getName(property, i),
                        Color.HSBtoRGB(0f, 0f, brightness)
                );
            }
        }

        return new PropertyScreenStack<>(property, items);
    }

    private static String getName(Property property, int i){
        return property.getValues().get(i).toString().replace("_"," ");
    }

}
