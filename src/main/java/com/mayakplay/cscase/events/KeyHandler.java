/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.gameevent.InputEvent
 *  cpw.mods.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.settings.KeyBinding
 */
package com.mayakplay.cscase.events;

import com.mayakplay.cscase.CasesMain;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;


public class KeyHandler {
    @SubscribeEvent
    public void KeyPress(InputEvent.KeyInputEvent event) {
        if (CasesMain.KeyTest.isPressed()) {
            Minecraft.getMinecraft().player.sendChatMessage("/jcases list");
        }
    }
}

