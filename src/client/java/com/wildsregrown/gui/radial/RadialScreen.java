package com.wildsregrown.gui.radial;

import com.wildsregrown.gui.radial.stack.ScreenStack;
import com.wildsregrown.gui.radial.stack.StackBuilder;
import com.wildsregrown.gui.radial.stack.stacks.BlockScreenStack;
import com.wildsregrown.items.IRadialItem;
import com.sipke.math.MathUtil;
import com.wildsregrown.network.radial.RadialBlockStatePayload;
import com.wildsregrown.network.radial.RadialItemPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

import static com.wildsregrown.WildsRegrown.modid;

@Environment(EnvType.CLIENT)
public class RadialScreen extends Screen {

    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static final RadialScreen INSTANCE = new RadialScreen();
    //state
    public static boolean active;
    private static ScreenStack stack;
    private static int stackIndex;

    public RadialScreen() {
        super(Text.of("wrg.radialMenu.title"));
    }

    public static boolean canShow() {
        if (client.world != null && client.currentScreen == null) {
            if (!active && !client.inGameHud.getChatHud().isChatFocused()) {
                return true;
            }
        }
        return false;
    }

    public static void show() {
        if (canShow()) {
            stackIndex = -1;
            //Set instance by item/block ScreenStack
            Item item = client.player.getMainHandStack().getItem();

            if (item instanceof IRadialItem) {
                stack = StackBuilder.buildItem(item);
            } else {
                Block block = Block.getBlockFromItem(item);
                stack = StackBuilder.buildBlockState(block, client.player.isCreative());
                if (stack.children.isEmpty()) {return;}
            }

            //check singleton instance
            if (stack != null) {
                if (stack.children.size() == 1) {
                    stackIndex = 0;
                }
            }
            //Activate instance
            active = true;
            client.setScreen(INSTANCE);
        }
    }

    public static void hide() {
        if (client.world != null) {
            if (active) {
                //Send payload
                if (stack instanceof BlockScreenStack screenStack){
                    ClientPlayNetworking.send(new RadialBlockStatePayload(screenStack.propertyMap.toString()));
                }else {
                    ClientPlayNetworking.send(new RadialItemPayload(stack.itemIndice));
                }
                //Disable instance
                active = false;
                stack = null;
                client.setScreen(null);
            }
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        super.mouseClicked(click, doubled);
        if(stack == null){return false;}
        if(active) {
            //centerPos
            int cx = width / 2, cy = height / 2;
            if (click.button() == 0) {
                if (!MathUtil.isInside(cx, cy, (float) click.x(), (float) click.y(), stack.radius)){
                    close();//still "active" until tab is released
                    return true;
                }

                //if it has children we (can) move down
                if (!stack.children.isEmpty() && client.player != null) {
                    if (stackIndex == -1){
                        stackIndex = stack.itemIndice;
                        client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1f, 1f);
                        }else {
                        //update stack first
                        stack.mouseClicked(stackIndex);
                        stackIndex = -1;
                        client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1f, 1f);
                    }
                }

            }else if (click.button() == 1 || click.button() == 2){
                //Return to parent
                stackIndex = -1;
                client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1f, 1f);
            }
        }
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (client.player != null) {
            if (stack != null){
                if (stackIndex == -1) {
                    stack.drawScreen(context, textRenderer, mouseX, mouseY);
                }else if (!stack.children.isEmpty()){
                    stack.children.get(stackIndex).drawScreen(context, textRenderer, mouseX, mouseY);
                }else {
                    context.drawCenteredTextWithShadow(textRenderer, Text.literal("Invalid settings"), width/2, height-60, Color.WHITE.getRGB());
                }
            }else {
                context.drawCenteredTextWithShadow(textRenderer, Text.literal("Item / Block not supported"), width/2, height-60, Color.WHITE.getRGB());
            }
        }
        renderCenter(context);
        if (stack!=null) {
            context.drawTooltip(textRenderer, Text.literal(stack.itemIndice + " / " + RadialRenderer.mouseAngle(width / 2, height / 2, mouseX, mouseY)), 12, height - 80);
        }
    }

    private void renderCenter(DrawContext context) {
        int cx = width/2, cy = height/2;

        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, Identifier.of(modid, "radial_center"), cx-16, cy-16, 32, 32);

        if (client.player != null) {
            ItemStack current = client.player.getMainHandStack();
            ItemStack stack = current == null ? ItemStack.EMPTY : current;
            context.drawItem(stack, cx-8, cy-8);
        }

    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.applyBlur(context);
    }

    //behaviour
    @Override
    public boolean shouldPause() {
        return false;
    }

}