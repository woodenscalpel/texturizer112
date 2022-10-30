package com.geodesictriangle.texturizer.network.messages;

import com.geodesictriangle.texturizer.objects.items.AbstractSwapWand;
import com.geodesictriangle.texturizer.objects.items.catwand.AbstractCatWand;
import com.geodesictriangle.texturizer.objects.items.catwand.CatWand;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageChangeNBT implements IMessage,IMessageHandler<MessageChangeNBT, IMessage> {


	    private float val;
	    private String tag;

		public MessageChangeNBT(PacketBuffer buf) {
	       // id = buf.readString();
	        //type = DimensionType.getById(buf.readInt());
	        //pos = buf.readBlockPos();
	    }

	    public MessageChangeNBT(){}//String id){//, DimensionType type, BlockPos pos) {

	    public MessageChangeNBT(String nbttag, float newvalue){
	    	val = newvalue;
	    	tag = nbttag;
	    }

	    
	    @Override
	    public IMessage onMessage(MessageChangeNBT msg, MessageContext ctx) {
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

			
			float val = msg.val;
			String tag = msg.tag;
   
           ((CatWand) player.getHeldItemMainhand().getItem()).setnbtval(player.getHeldItemMainhand(),tag,val);
		
	        return null;
	    }


		@Override
		public void fromBytes(ByteBuf buf) {
			val = buf.readFloat();
			int len = buf.readInt();
			tag = (String) buf.readCharSequence(len, CharsetUtil.ISO_8859_1);
			// TODO Auto-generated method stub
			
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeFloat(val);
			buf.writeInt(tag.length());
			buf.writeCharSequence(tag, CharsetUtil.ISO_8859_1);
			// TODO Auto-generated method stub
			
		}

	
	

	}