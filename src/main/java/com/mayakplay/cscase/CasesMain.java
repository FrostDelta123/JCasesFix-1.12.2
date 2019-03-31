/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.ClientRegistry
 *  cpw.mods.fml.common.FMLCommonHandler
 *  cpw.mods.fml.common.Mod
 *  cpw.mods.fml.common.Mod$EventHandler
 *  cpw.mods.fml.common.Mod$Instance
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.eventhandler.EventBus
 *  cpw.mods.fml.common.network.NetworkRegistry
 *  cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraft.client.settings.KeyBinding
 */
package com.mayakplay.cscase;

import com.mayakplay.cscase.events.KeyHandler;
import com.mayakplay.cscase.network.CasesListPacket;
import com.mayakplay.cscase.network.CasesMainPacket;
import com.mayakplay.cscase.network.CasesViewPacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid="delta", version="1.0.0", name="Mayakplay Cases")
public class CasesMain {
    public static KeyBinding KeyTest;
    @Mod.Instance
    public static CasesMain instance;

    public static CasesMain getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.newSimpleChannel("CasesMainChannel").registerMessage(CasesMainPacket.Handler.class, CasesMainPacket.class, 0, Side.CLIENT);
        NetworkRegistry.INSTANCE.newSimpleChannel("CasesListChannel").registerMessage(CasesListPacket.Handler.class, CasesListPacket.class, 0, Side.CLIENT);
        NetworkRegistry.INSTANCE.newSimpleChannel("CasesViewChannel").registerMessage(CasesViewPacket.Handler.class, CasesViewPacket.class, 0, Side.CLIENT);
        FMLCommonHandler.instance().bus().register((Object)new KeyHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        KeyTest = new KeyBinding("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043c\u0435\u043d\u044e \u043a\u0435\u0439\u0441\u043e\u0432", 38, "\u041a\u0435\u0439\u0441\u044b");
        ClientRegistry.registerKeyBinding((KeyBinding)KeyTest);
    }
}

