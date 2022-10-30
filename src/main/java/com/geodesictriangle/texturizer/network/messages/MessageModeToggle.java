package com.geodesictriangle.texturizer.network.messages;

import com.geodesictriangle.texturizer.objects.items.AbstractSwapWand;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageModeToggle implements IMessage,IMessageHandler<MessageModeToggle, IMessage> {

	   // private final String id;
	    //private final DimensionType type;
	   // private final BlockPos pos;

	    public MessageModeToggle(PacketBuffer buf) {
	       // id = buf.readString();
	        //type = DimensionType.getById(buf.readInt());
	        //pos = buf.readBlockPos();
	    }

	    public MessageModeToggle(){//String id){//, DimensionType type, BlockPos pos) {
	        //this.id = id;
	        //this.type = type;
	        //this.pos = pos;
	    }
	    
	    @Override
	    public IMessage onMessage(MessageModeToggle msg, MessageContext ctx) {
	        EntityPlayer player = ctx.getServerHandler().player;

            //EntityPlayer player = Texturizer.proxy.getClientPlayer();
            {
                ((AbstractSwapWand) player.getHeldItemMainhand().getItem()).toggleMode(player, player.getHeldItemMainhand());
            }
	        return null;
	    }


		@Override
		public void fromBytes(ByteBuf buf) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void toBytes(ByteBuf buf) {
			// TODO Auto-generated method stub
			
		}
	

	}