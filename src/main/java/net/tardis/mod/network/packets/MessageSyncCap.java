package net.tardis.mod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.tardis.mod.capability.CapabilityTardis;

import java.util.UUID;

/**
 * Created by Sub
 * on 16/09/2018.
 */
public class MessageSyncCap implements IMessage {
	
	private EntityPlayer player;
	private NBTTagCompound data;
	
	public MessageSyncCap() {
	}
	
	public MessageSyncCap(EntityPlayer player, NBTTagCompound data) {
		this.player = player;
		this.data = data;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, player.getGameProfile().getId().toString());
		ByteBufUtils.writeTag(buf, data);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		if (Minecraft.getMinecraft().player == null)
			return;
		player = Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(UUID.fromString(ByteBufUtils.readUTF8String(buf)));
		data = ByteBufUtils.readTag(buf);
	}
	
	public static class Handler implements IMessageHandler<MessageSyncCap, IMessage> {
		
		@Override
		public IMessage onMessage(MessageSyncCap message, MessageContext ctx) {
			EntityPlayer player = message.player;
			if (player != null)
				Minecraft.getMinecraft().addScheduledTask(() -> CapabilityTardis.get(player).deserializeNBT(message.data));
			return null;
		}
	}
	
}