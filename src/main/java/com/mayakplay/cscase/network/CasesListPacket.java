/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.ByteBufUtils
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.common.network.simpleimpl.IMessageHandler
 *  cpw.mods.fml.common.network.simpleimpl.MessageContext
 *  io.netty.buffer.ByteBuf
 */
package com.mayakplay.cscase.network;


import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CasesListPacket
implements IMessage {
    String text;

    public CasesListPacket() {
    }

    public CasesListPacket(String text) {
        this.text = text;
    }

    public void fromBytes(ByteBuf buf) {
        this.text = ByteBufUtils.readUTF8String((ByteBuf)buf);
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String((ByteBuf)buf, (String)this.text);
    }

    public static class Handler
    implements IMessageHandler<CasesListPacket, IMessage> {
        public IMessage onMessage(CasesListPacket message, MessageContext ctx) {
            Recieve.CASES_LIST = Recieve.CASES_LIST.equalsIgnoreCase("") ? message.text : Recieve.CASES_LIST + "," + message.text;
            return null;
        }
    }

}

