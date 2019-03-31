
package com.mayakplay.cscase.gui;

import com.google.common.collect.Maps;
import com.mayakplay.cscase.model.ModelItemCase;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class MPGui
extends GuiScreen {
    ModelItemCase modelItemCase = new ModelItemCase();
    float delta = 0.0f;
    final double tc = 60.0;
    long lastTime = System.nanoTime();
    private int MX = 0;
    private int MY = 0;
    protected boolean isClicked = false;
    private static Map<String, DynamicTexture> images = Maps.newHashMap();

    protected void drawScaledString(String text, float x, float y, float scale, TextPosition textPosition) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)0.0f);
        if (textPosition == TextPosition.CENTER) {
            this.drawCenteredString(this.mc.fontRenderer, text, 0, 0, 16777215);
        } else if (textPosition == TextPosition.LEFT) {
            this.drawString(this.mc.fontRenderer, text, 0, 0, 16777215);
        } else if (textPosition == TextPosition.RIGHT) {
            this.drawString(this.mc.fontRenderer, text, -this.fontRenderer.getStringWidth(text), 0, 16777215);
        }
        GL11.glPopMatrix();
    }

    protected void drawCompleteImage(int posX, int posY, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex3f((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex3f((float)0.0f, (float)height, (float)0.0f);
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex3f((float)width, (float)height, (float)0.0f);
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex3f((float)width, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public boolean isHover(int xx, int yy, int xx1, int yy1) {
        int mouseX = this.MX;
        int mouseY = this.MY;
        return mouseX >= xx && mouseX < xx1 + xx && mouseY >= yy && mouseY < yy1 + yy;
    }

    public boolean isClicked(int xx, int yy, int xx1, int yy1) {
        int mouseX = this.MX;
        int mouseY = this.MY;
        return mouseX >= xx && mouseX < xx1 + xx && mouseY >= yy && mouseY < yy1 + yy && this.isClicked;
    }

    protected void draw3DCase(int x, int y, String texture, float rotation) {
        GL11.glPushMatrix();
        MPGui.bindTexture(texture);
        GL11.glTranslatef((float)(x + 27), (float)(y - 12), (float)44.0f);
        GL11.glScalef((float)20.0f, (float)20.0f, (float)20.0f);
        GL11.glRotatef((float)-8.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)rotation, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glDisable((int)2884);
        this.modelItemCase.renderModel(0.0625f);
        GL11.glPopMatrix();
    }

    protected void mouseClicked(int x, int y, int b) throws IOException {
        super.mouseClicked(x, y, b);
        this.isClicked = true;
        Timing timing = new Timing(100);
        timing.start();
    }

    public void drawScreen(int x, int y, float ticks) {
        this.MX = x;
        this.MY = y;
        double ns = 1.6666666666666666E7;
        long now = System.nanoTime();
        this.delta = (float)((double)(now - this.lastTime) / ns);
        this.lastTime = now;
    }

    protected void draw3DGuiItem(ItemStack itemStack, float x, float y, float scale) {
        ItemStack is = itemStack.copy();
        is.setCount(1);
        itemStack.setItemDamage(itemStack.getItemDamage());
        EntityItem entityItem = new EntityItem((World)this.mc.world, 0.0, 0.0, 0.0, is);
        entityItem.hoverStart = 0.0f;
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glTranslatef((float)x, (float)y, (float)4.0f);
        GL11.glRotatef((float)-11.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)160.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        mc.getRenderManager().renderEntity(entityItem, 0.0, 0.0, 0.0, 0.2f, 0.2f, false);
        //RenderManager.instance.func_147939_a((Entity)entityItem, 0.0, 0.0, 0.0, 0.2f, 0.2f, false);

        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void bindTexture(String name) {
        if (images.get(name) != null) {
            GL11.glBindTexture((int)3553, (int)images.get(name).getGlTextureId());
        }
    }

    public void addTex(String name, String image) {
        try {
            images.put(name, new DynamicTexture(ImageIO.read(new URL(image))));
        }
        catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    static enum TextPosition {
        LEFT,
        CENTER,
        RIGHT;
        
    }

    class Timing
    extends Thread {
        private int timer;

        public Timing(int timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.timer);
            }
            catch (InterruptedException var2) {
                var2.printStackTrace();
            }
            MPGui.this.isClicked = false;
            this.stop();
        }
    }

}

