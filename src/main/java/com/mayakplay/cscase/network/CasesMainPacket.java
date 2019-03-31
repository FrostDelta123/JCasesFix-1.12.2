/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.ByteBufUtils
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.common.network.simpleimpl.IMessageHandler
 *  cpw.mods.fml.common.network.simpleimpl.MessageContext
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package com.mayakplay.cscase.network;

import com.mayakplay.cscase.gui.GuiCaseView;
import com.mayakplay.cscase.gui.GuiCasesShop;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CasesMainPacket
implements IMessage {

    String text;

    public CasesMainPacket() {
    }

    public CasesMainPacket(String text) {
        this.text = text;
    }

    public void fromBytes(ByteBuf buf) {
        this.text = ByteBufUtils.readUTF8String((ByteBuf)buf);
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String((ByteBuf)buf, (String)this.text);
    }

    public static class Handler
    implements IMessageHandler<CasesMainPacket, IMessage> {
        public IMessage onMessage(CasesMainPacket message, MessageContext ctx) {
            String[] args;
            if (message.text.equals("Clear")) {
                Recieve.CASES_LIST = "";
                Recieve.CURRENT_CASE_ITEMS_LIST = "";
                Recieve.WON_ITEM = "";
            }
            if (message.text.equals("ClearLast")) {
                Recieve.CURRENT_CASE_ITEMS_LIST = "";
            }
            if (message.text.equals("Open")) {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiCasesShop());
            }
            if ((args = message.text.split(","))[0].equals("View")) {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiCaseView(args[1]));
            }
            if (args[0].equals("SetWon")) {
                Recieve.WON_ITEM = args[1] + "," + args[2] + "," + args[3] + "," + args[4];
            }
            if (message.text.equals("RollCase")) {
                Recieve.isRolling = true;
            }
            return null;
        }
    }

}

