/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.mayakplay.cscase.gui;

import com.mayakplay.cscase.Strings;
import com.mayakplay.cscase.gui.MPGui;
import com.mayakplay.cscase.network.PacketsDecoder;
import com.mayakplay.cscase.pojo.Case;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiCasesShop
extends MPGui {
    int move = 0;
    int maxMove = 0;

    @Override
    public void drawScreen(int x, int y, float ticks) {
        super.drawScreen(x, y, ticks);
        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scaled.getScaleFactor();
        int guiX = this.width / 2 - 127;
        int guiY = this.height / 2 - 113;
        this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexture.png"));
        this.drawTexturedModalRect(guiX, guiY, 0, 0, 256, 255);
        int itemsCount = PacketsDecoder.getCases().size();
        int colsCount = 3;
        int rowsCount = Math.round((float)itemsCount / (float)colsCount + 0.2f);
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);
        GL11.glScissor((int)(guiX * factor), (int)(this.height * factor - (guiY - 2) * factor - 220 * factor), (int)(247 * factor), (int)(209 * factor));
        this.maxMove = rowsCount * 80 - 208;
        if (this.move >= 0) {
            this.move = 0;
        }
        if (rowsCount > 3) {
            if (this.move <= -this.maxMove) {
                this.move = -this.maxMove;
            }
        } else {
            this.move = 0;
        }
        int counter = 0;
        for (int gnomik = 0; gnomik < rowsCount; ++gnomik) {
            for (int j = 0; j < colsCount; ++j) {
                if (counter >= itemsCount) continue;
                this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
                this.drawTexturedModalRect(guiX + 10 + j * 78, guiY + 10 + 80 * gnomik + this.move, 0, 0, 76, 78);
                if (!this.isHover(guiX + 11 + j * 78, guiY + 73 + 80 * gnomik + this.move, 74, 14)) {
                    this.drawTexturedModalRect(guiX + 11 + j * 78, guiY + 73 + 80 * gnomik + this.move, 0, 78, 74, 14);
                } else {
                    this.drawTexturedModalRect(guiX + 11 + j * 78, guiY + 73 + 80 * gnomik + this.move, 0, 92, 74, 14);
                }
                if (this.isClicked(guiX + 11 + j * 78, guiY + 73 + 80 * gnomik + this.move, 74, 14)) {
                    this.mc.player.sendChatMessage("/jcases view " + PacketsDecoder.getCases().get(counter).getName());
                    this.isClicked = false;
                }
                this.drawScaledString(PacketsDecoder.getCases().get(counter).getName(), guiX + 12 + j * 78, guiY + 14 + 80 * gnomik + this.move, 0.85f, MPGui.TextPosition.LEFT);

                //CHANGED scale. Legacy 0.85
                this.drawScaledString(Strings.available + PacketsDecoder.getCases().get(counter).getAvailable(), guiX + 12 + j * 78, guiY + 63 + 80 * gnomik + this.move, 1.15f, MPGui.TextPosition.LEFT);
                //CHANGED
                this.drawScaledString(Strings.view, guiX + 47 + j * 78, guiY + 76 + 80 * gnomik + this.move, 1.05f, MPGui.TextPosition.CENTER);
                this.draw3DCase(guiX + 21 + j * 78, guiY + 32 + 80 * gnomik + this.move, "case" + PacketsDecoder.getCases().get(counter).getName(), 160.0f);
                ++counter;
            }
        }
        GL11.glDisable((int)3089);
        GL11.glPopMatrix();
        if (rowsCount > 3) {
            this.mc.renderEngine.bindTexture(new ResourceLocation("delta", "textures/gui/caseshoptexturel.png"));
            this.drawTexturedModalRect(357, 27, 79, 0, 3, 210);
            this.drawTexturedModalRect(357, (int)(27.0f - (float)this.move * (190.0f / (float)this.maxMove)), 76, 0, 3, 20);
            float var14 = 190 / this.maxMove;
            if (this.isClicked(357, 27, 3, 210)) {
                this.isClicked = false;
                this.move = -((int)((float)(y - 27) / var14));
            }
        }
    }

    protected void mouseClickMove(int x, int y, int b, long l) {
    }

    protected void mouseMovedOrUp(int x, int y, int b) {
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.move += Mouse.getEventDWheel() / 120 * 4;
    }

    public void initGui() {
        for (int i = 0; i < PacketsDecoder.getCases().size(); ++i) {
            Case aCase = PacketsDecoder.getCases().get(i);
            String id = "case" + aCase.getName();
            String texture = aCase.getTexture();
            this.addTex(id, texture);
        }
    }
}

