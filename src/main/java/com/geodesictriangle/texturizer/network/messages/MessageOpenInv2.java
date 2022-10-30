package com.geodesictriangle.texturizer.network.messages;

import com.geodesictriangle.texturizer.objects.items.AbstractSwapWand;
import com.geodesictriangle.texturizer.objects.items.catwand.AbstractCatWand;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenInv2 implements IMessage,IMessageHandler<MessageOpenInv2, IMessage> {

	   // private final String id;
	    //private final DimensionType type;
	   // private final BlockPos pos;

	    public MessageOpenInv2(PacketBuffer buf) {
	       // id = buf.readString();
	        //type = DimensionType.getById(buf.readInt());
	        //pos = buf.readBlockPos();
	    }

	    public MessageOpenInv2(){//String id){//, DimensionType type, BlockPos pos) {
	        //this.id = id;
	        //this.type = type;
	        //this.pos = pos;
	    }
	    
	    @Override
	    public IMessage onMessage(MessageOpenInv2 msg, MessageContext ctx) {
	    	/*
	        EntityPlayer player = null;
	    	if(ctx.side == Side.SERVER) {
	    		Texturizer.logger.info("Firing for server");
	         player = ctx.getServerHandler().player;
	    	}
	    	if(ctx.side == Side.CLIENT) {
	    		Texturizer.logger.info("Firing for client");

		         player = Texturizer.proxy.getClientPlayer();
		    	}
		    	*/
	         EntityPlayer player = ctx.getServerHandler().player;

	
			World world = player.getEntityWorld();


         //EntityPlayer player = Texturizer.proxy.getClientPlayer();
         {
             ((AbstractCatWand) player.getHeldItemMainhand().getItem()).openInv(world,player);
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