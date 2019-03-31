/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package com.mayakplay.cscase.gui;

import com.mayakplay.cscase.gui.MPGui;
import com.mayakplay.cscase.model.ModelGuiCase;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiCaseWon
extends MPGui {
    float animation = 0.0f;
    float mainAnimation = 25.0f;
    float fenceAnim = 0.0f;
    float numAnim = 0.0f;
    ModelGuiCase model = new ModelGuiCase();
    private int itemsCount;
    private ItemStack itemStack;
    private int quality;

    public GuiCaseWon(ItemStack itemStack, int quality) {
        this.quality = quality;
        this.itemsCount = itemStack.getCount();
        this.itemStack = new ItemStack(itemStack.getItem(), 1);
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        super.drawScreen(x, y, ticks);
        this.drawDefaultBackground();
        ScaledResolution scaled = new ScaledResolution(this.mc);
        int factor = scaled.getScaleFactor();
        int panX = 205;
        int panY = 105;
        int guiX = this.width / 2 - panX / 2;
        int guiY = this.height / 2 - panY / 2;
        Color color = Color.white;
        switch (this.quality) {
            case 1: {
                color = Color.WHITE;
                break;
            }
            case 2: {
                color = Color.GREEN;
                break;
            }
            case 3: {
                color = Color.BLUE;
                break;
            }
            case 4: {
                color = Color.MAGENTA;
                break;
            }
            case 5: {
                color = Color.ORANGE;
                break;
            }
            case 6: {
                color = Color.RED;
            }
        }
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/opctexture.png"));
        this.drawTexturedModalRect(guiX - 7, guiY - 16, 0, 0, 219, 146);
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.drawCenteredString(this.fontRenderer, this.itemStack.getDisplayName(), this.width / 2, guiY - 12, 16777215);
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/opctexture.png"));
        if (this.fenceAnim == -0.9f) {
            if (!this.isHover(guiX - 7 + 67, guiY - 16 + 124, 85, 17)) {
                this.drawTexturedModalRect(guiX - 7 + 67, guiY - 16 + 124, 0, 146, 85, 17);
            } else {
                this.drawTexturedModalRect(guiX - 7 + 67, guiY - 16 + 124, 0, 164, 85, 17);
            }
            if (this.isClicked(guiX - 7 + 67, guiY - 16 + 124, 85, 17)) {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            //CHANGED
            this.drawScaledString("\u0420\u045f\u0421\u0402\u0420\u0455\u0420\u0491\u0420\u0455\u0420\u00bb\u0420\u00b6\u0420\u0451\u0421\u201a\u0421\u040a", guiX - 5 + 67 + 42, guiY - 11 + 124, 1.10f, MPGui.TextPosition.CENTER);
        }
        if (this.animation >= 360.0f) {
            this.animation = 0.0f;
        }
        if (this.mainAnimation <= 8.0f) {
            this.mainAnimation = 8.0f;
        }
        if (this.mainAnimation <= 15.0f) {
            this.fenceAnim -= this.delta / 90.0f;
        }
        if (this.fenceAnim <= -0.9f) {
            this.fenceAnim = -0.9f;
        }
        if ((double)this.fenceAnim <= -0.8) {
            this.numAnim += this.delta / 60.0f;
        }
        if (this.numAnim >= 0.3f) {
            this.numAnim = 0.3f;
        }
        GL11.glPushMatrix();
        EntityItem entityItem = new EntityItem((World)this.mc.world, 0.0, 0.0, 0.0, this.itemStack);
        entityItem.hoverStart = 0.0f;
        GL11.glEnable((int)3089);
        GL11.glScissor((int)(guiX * factor), (int)(this.height * factor - guiY * factor - panY * factor), (int)(panX * factor), (int)(panY * factor));
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2884);
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/casesopened.png"));
        GL11.glTranslatef((float)(this.width / 2 + 7), (float)(this.height / 2 - 113), (float)360.0f);
        GL11.glScalef((float)118.0f, (float)118.0f, (float)1.0f);
        GL11.glRotatef((float)270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)this.mainAnimation, (float)0.0f, (float)1.0f, (float)1.0f);
        this.model.renderModel(0.0625f, this.fenceAnim, this.numAnim);
        RenderHelper.disableStandardItemLighting();
        GL11.glTranslatef((float)-0.5f, (float)1.35f, (float)0.0f);
        GL11.glScalef((float)1.2f, (float)1.2f, (float)1.2f);
        GL11.glRotatef((float)8.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)(this.animation + 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);

        mc.getRenderManager().renderEntity(entityItem, 0.0, 0.0, 0.0, 0.2f, 0.2f, false);
        //RenderManager.instance.renderEntityWithPosYaw((Entity)entityItem, 0.0, 0.0, 0.0, 0.2f, 0.2f);

        GL11.glDisable((int)3089);
        GL11.glPopMatrix();
        if ((double)this.numAnim >= 0.22) {
            GL11.glPushMatrix();
            if (this.itemsCount >= 10) {
                GL11.glTranslatef((float)(this.width / 2 + 77), (float)(this.height / 2 - 37), (float)400.0f);
            } else {
                GL11.glTranslatef((float)((float)(this.width / 2 + 77) + 4.6f), (float)(this.height / 2 - 37), (float)400.0f);
            }
            GL11.glScalef((float)1.5f, (float)1.5f, (float)1.5f);
            this.fontRenderer.drawString("" + this.itemsCount, 0, 0, -872415232);
            GL11.glPopMatrix();
        }
        if (this.mainAnimation <= 8.0f) {
            this.animation += this.delta;
        } else {
            this.mainAnimation += -this.delta / 6.0f;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

