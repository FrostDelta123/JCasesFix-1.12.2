/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
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
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package com.mayakplay.cscase.gui;

import com.mayakplay.cscase.ColorHelper;
import com.mayakplay.cscase.Strings;
import com.mayakplay.cscase.gui.MPGui;
import com.mayakplay.cscase.model.ModelGuiCase;
import com.mayakplay.cscase.network.PacketsDecoder;
import com.mayakplay.cscase.network.Recieve;
import com.mayakplay.cscase.pojo.CaseItem;
import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiCaseView
extends MPGui {
    private String caseName;
    private boolean isWon = false;
    float rollMove = 0.0f;
    private List<CaseItem> ROLLING_ITEMS = null;
    private boolean isCaseLoading = false;
    private float caseLoadingRotation = 160.0f;
    private float littleWaiting = 0.0f;
    private float slow = PacketsDecoder.randFloat(9.38f, 9.42f);
    private int randStop = 0;
    private boolean isRolling = false;
    private boolean useful1 = true;
    private int current = 0;
    private int lastInt = 0;
    int counter = 0;
    float gridAnim = 0.0f;
    float animation = 0.0f;
    float mainAnimation = 25.0f;
    float fenceAnim = 0.0f;
    float numAnim = 0.0f;
    ModelGuiCase model = new ModelGuiCase();
    private int itemsCount;
    private ItemStack itemStack;
    private int quality;

    public GuiCaseView(String caseName) {
        this.caseName = caseName;
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        super.drawScreen(x, y, ticks);
        this.drawDefaultBackground();
        ScaledResolution scaled = new ScaledResolution(this.mc);
        int factor = scaled.getScaleFactor();
        int guiX = this.width / 2 - 127;
        int guiY = this.height / 2 - 113;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturecw.png"));
        this.drawTexturedModalRect(guiX, guiY, 0, 0, 256, 72);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.6f);
        this.drawTexturedModalRect(guiX, guiY, 0, 0, 256, 255);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();

        //CHANGED
        this.drawScaledString(Strings.itemsCanDrop, guiX + 12, guiY + 63, 1.10f, MPGui.TextPosition.LEFT);
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
        if (!Recieve.isRolling) {
            if (!this.isCaseLoading) {
                if (!this.isHover(guiX + 65, guiY + 39, 125, 14)) {
                    this.drawTexturedModalRect(guiX + 65, guiY + 39, 0, 214, 125, 14);
                } else {
                    this.drawTexturedModalRect(guiX + 65, guiY + 39, 0, 228, 125, 14);
                }
                if (this.isClicked(guiX + 65, guiY + 39, 125, 14)) {
                    this.isCaseLoading = true;
                    this.mc.player.sendChatMessage("/jcases roll " + this.caseName);
                    this.randStop = PacketsDecoder.randInt(12, 21);
                    this.isClicked = false;
                }
                this.draw3DCase(guiX + 101, guiY + 13, "case" + this.caseName, 160.0f);
                //CHANGED
                this.drawScaledString(Strings.openPrice(), guiX + 128, guiY + 42, 1.10f, MPGui.TextPosition.CENTER);
            } else {
                this.draw3DCase(guiX + 101, guiY + 13, "case" + this.caseName, this.caseLoadingRotation);
                this.caseLoadingRotation += this.delta * 2.0f;
                this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
                this.drawTexturedModalRect(guiX + 65, guiY + 39, 0, 242, 125, 14);
                //CHANGED
                this.drawScaledString(Strings.opening, guiX + 81, guiY + 42, 1.10f, MPGui.TextPosition.LEFT);
            }
        } else {
            if (this.ROLLING_ITEMS == null) {
                this.ROLLING_ITEMS = PacketsDecoder.getRandomItemsForRoll();
            }
            this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
            this.drawTexturedModalRect(guiX + 65, guiY + 39, 0, 242, 125, 14);
            //CHANGED
            this.drawScaledString(Strings.opening, guiX + 81, guiY + 42, 1.10f, MPGui.TextPosition.LEFT);
            this.drawRollingItems();
        }
        this.drawItemsGrid(x, y, guiX + 8, guiY + 73);
        if (this.isWon) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)400.0f);
            this.drawWonScreen(x, y, ticks);
            GL11.glPopMatrix();
        }
    }

    public void onGuiClosed() {
        Recieve.isRolling = false;
    }

    private void drawRollingItems() {
        ScaledResolution scaled = new ScaledResolution(this.mc);
        int factor = scaled.getScaleFactor();
        int guiX = this.width / 2 - 127;
        int guiY = this.height / 2 - 113;
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);
        GL11.glScissor((int)((guiX + 66) * factor), (int)(this.height * factor - (guiY + 7) * factor - 30 * factor), (int)(123 * factor), (int)(30 * factor));
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
        for (int i = 0; i < this.ROLLING_ITEMS.size(); ++i) {
            Color cl = ColorHelper.getColorByRare(this.ROLLING_ITEMS.get(i).getRarity());
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glTranslatef((float)((float)guiX + this.rollMove + 66.0f + 40.0f * (float)i), (float)(guiY + 8), (float)0.0f);
            this.drawTexturedModalRect(0, 0, 127, 0, 38, 20);
            int poop = (int)((float)guiX + this.rollMove + 66.0f + 40.0f * (float)i);
            int contact = guiX + 127;
            if (contact >= poop && contact < 40 + poop) {
                this.current = i;
            }

            this.lastInt = this.current;
            GL11.glColor4f((float)((float)cl.getRed() / 255.0f), (float)((float)cl.getGreen() / 255.0f), (float)((float)cl.getBlue() / 255.0f), (float)1.0f);
            this.drawTexturedModalRect(0, 20, 127, 20, 38, 8);
            GL11.glDisable((int)3042);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
            ItemStack is = this.ROLLING_ITEMS.get(i).getItemStack();
            this.draw3DGuiItem(is, (float)guiX + this.rollMove + 85.0f + 40.0f * (float)i, guiY + 26, 30.0f);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            if (is.getDisplayName().length() > 12) {
                //CHANGED
                this.drawScaledString(is.getDisplayName().substring(0, 11) + "...", (float)guiX + this.rollMove + 67.0f + 40.0f * (float)i, guiY + 29, 1.10f, MPGui.TextPosition.LEFT);
            } else {
                //CHANGED
                this.drawScaledString(is.getDisplayName(), (float)guiX + this.rollMove + 67.0f + 40.0f * (float)i, guiY + 29, 1.10f, MPGui.TextPosition.LEFT);
            }
            this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
        }
        GL11.glDisable((int)3089);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)400.0f);
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
        this.drawTexturedModalRect(guiX + 102, guiY + 7, 165, 0, 51, 30);
        GL11.glPopMatrix();
        if (this.rollMove >= (float)(-((this.ROLLING_ITEMS.size() - 4) * 40) + this.randStop)) {
            this.rollMove -= this.delta * this.slow;
            if (this.slow >= 0.05f) {
                this.slow -= this.delta / 50.0f;
            }
        } else if (this.littleWaiting < 6.0f) {
            this.littleWaiting += this.delta * this.slow;
        } else {
            this.itemStack = this.ROLLING_ITEMS.get(57).getItemStack().copy();
            this.itemsCount = this.itemStack.getCount();
            this.quality = this.ROLLING_ITEMS.get(57).getRarity();
            this.isWon = true;
        }
    }

    private void drawItemsGrid(int mouseX, int mouseY, int x, int y) {
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (this.counter >= (int)this.gridAnim) continue;
                this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
                this.drawTexturedModalRect(x + 3 + 47 * j, y + 2 + 48 * i, 82, 0, 45, 33);
                Color cl = ColorHelper.getColorByRare(PacketsDecoder.getCaseItemsList().get(this.counter).getRarity());
                GL11.glPushMatrix();
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                this.drawTexturedModalRect(x + 3 + 47 * j, y + 2 + 48 * i + 33, 82, 33, 45, 12);
                GL11.glColor4f((float)((float)cl.getRed() / 255.0f), (float)((float)cl.getGreen() / 255.0f), (float)((float)cl.getBlue() / 255.0f), (float)0.9f);
                this.drawTexturedModalRect(x + 3 + 47 * j, y + 2 + 48 * i + 33, 82, 33, 45, 12);
                GL11.glDisable((int)3042);
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glPopMatrix();
                ItemStack is = PacketsDecoder.getCaseItemsList().get(this.counter).getItemStack();
                this.draw3DGuiItem(is, x + 25 + 47 * j, y + 29 + 48 * i, 38.0f);
                String name = is.getDisplayName();
                if (PacketsDecoder.getCaseItemsList().get(this.counter).getRarity() == 5) {
                    name = "\u2726 " + name + " \u2726";
                }
                if (name.length() > 14) {
                    //CHANGED
                    this.drawScaledString(name.substring(0, 13) + "-", x + 5 + 47 * j, y + 36 + 48 * i, 0.75f, MPGui.TextPosition.LEFT);
                    if (name.length() > 27) {
                        //CHANGED
                        this.drawScaledString(name.substring(13, 26) + "...", x + 5 + 47 * j, y + 41 + 48 * i, 0.75f, MPGui.TextPosition.LEFT);
                    } else {
                        //CHANGED
                        this.drawScaledString(name.substring(13), x + 5 + 47 * j, y + 41 + 48 * i, 0.75f, MPGui.TextPosition.LEFT);
                    }
                } else {
                    //CHANGED
                    this.drawScaledString(name, x + 5 + 47 * j, y + 38 + 48 * i, 0.75f, MPGui.TextPosition.LEFT);
                }
                ++this.counter;
            }
        }
        if (this.gridAnim < (float)PacketsDecoder.getCaseItemsList().size()) {
            this.gridAnim += this.delta / 2.0f;
        }
        if (this.gridAnim > (float)PacketsDecoder.getCaseItemsList().size()) {
            this.gridAnim = PacketsDecoder.getCaseItemsList().size();
        }
        this.counter = 0;
    }

    private void drawWonScreen(int x, int y, float ticks) {
        this.drawDefaultBackground();
        ScaledResolution scaled = new ScaledResolution(this.mc);
        int factor = scaled.getScaleFactor();
        int panX = 205;
        int panY = 105;
        int guiX = this.width / 2 - panX / 2;
        int guiY = this.height / 2 - panY / 2;
        Color color = ColorHelper.getColorByRare(this.quality);
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/opctexture.png"));
        this.drawTexturedModalRect(guiX - 7, guiY - 16, 0, 0, 219, 146);
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)0.7f);
        this.drawTexturedModalRect(guiX - 7, guiY - 16, 0, 0, 219, 16);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/opctextureu.png"));
        GL11.glPopMatrix();
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
                this.isWon = false;
                this.isClicked = false;
            }
            //CHANGE
            this.drawScaledString(Strings.continueOK, guiX - 5 + 67 + 42, guiY - 11 + 124, 1.10f, MPGui.TextPosition.CENTER);
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
        ItemStack is = this.itemStack.copy();
        is.setItemDamage(this.itemStack.getItemDamage());
        is.setCount(1);
        EntityItem entityItem = new EntityItem((World)this.mc.world, 0.0, 0.0, 0.0, is);
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
}

