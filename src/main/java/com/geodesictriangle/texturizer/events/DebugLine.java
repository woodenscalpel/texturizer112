package com.geodesictriangle.texturizer.events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.items.TextureWand;
import com.geodesictriangle.texturizer.objects.items.catwand.CatWand;
import com.geodesictriangle.texturizer.util.helpers.CatenaryHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Texturizer.MODID)
public class DebugLine {
	
	
	/**
	 * Sets up the OpenGL environment for rendering in world space.
	 * @param origin - Origin of the camera (typically player's eye position).
	 */
	public static void begin(double x, double y, double z) {

		
		GlStateManager.pushMatrix();
		//GlStateManager.rotate(angle.x, 1, 0, 0); // Fixes camera rotation.
		//GlStateManager.rotate(angle.y + 180, 0, 1, 0); // Fixes camera rotation.
		GlStateManager.translate(0.5 - x, 0.5 - y, 0.5 - z);
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	/**
	 * Returns the OpenGL environment back to normal.
	 */
	public static void end() {
	
		
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
		
	}
	
	

    @SubscribeEvent
    public static void render(RenderWorldLastEvent event) {
    	

		EntityPlayer player = Minecraft.getMinecraft().player;

	    assert player != null;
        if (player.getHeldItemMainhand().getItem() instanceof CatWand) {
            //drawBox(player, event.getMatrixStack());
        	ItemStack  wand = player.getHeldItemMainhand();

         	 NBTTagCompound nbt =  wand.getOrCreateSubCompound("myNBT");

             int x1 = nbt.getInteger("corner1x");
             int y1 = nbt.getInteger("corner1y");
             int z1 = nbt.getInteger("corner1z");
             int x2 = nbt.getInteger("corner2x");
             int y2 = nbt.getInteger("corner2y");
             int z2 = nbt.getInteger("corner2z");

             float s = nbt.getFloat("length");
             int bitsize = nbt.getInteger("bitsize");

            	GlStateManager.pushMatrix();
         		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
         		GlStateManager.disableDepth();
         		GlStateManager.disableTexture2D();
         		GlStateManager.enableBlend();

         		
        	
 			double renderPosX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
 			double renderPosY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
 			double renderPosZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;

 			GlStateManager.pushMatrix();
 			GlStateManager.translate(- renderPosX,- renderPosY,- renderPosZ);
 			Color colorRGB = new Color(555555);
 			GL11.glColor4ub((byte) colorRGB.getRed(), (byte) colorRGB.getGreen(), (byte) colorRGB.getBlue(), (byte) 255);

 		
 					

 					GlStateManager.scale(1F, 1F, 1F);

 					GL11.glLineWidth(3F);
 					
 					renderTestLine2(x1,y1,z1,x2,y2,z2);
 					renderTestLine(x1,y1,z1,x2,y2,z2,s,bitsize);
 					//GlStateManager.rotate( 90, 0, 1, 0 );
 					GlStateManager.enableTexture2D();

					CatenaryHelper catbuilder = new CatenaryHelper();
					List<List<Double>> points = catbuilder.getBitCatPoints(x1, y1, z1, x2, y2, z2,s,bitsize);
 					Set<BlockPos> blocklist = catbuilder.getBlockPos(points);
 					renderBlocks(player,(CatWand) wand.getItem(), blocklist,bitsize);
 					renderBlock(player,(CatWand) wand.getItem(),new BlockPos(0,10,0),16);


 			

 			GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
 			GlStateManager.popMatrix();
 			
 			
 			GlStateManager.enableDepth();
 			GlStateManager.enableTexture2D();
 			GlStateManager.disableBlend();
 			GL11.glPopAttrib();
 			GlStateManager.popMatrix();
            
			
        }
			

		
	}
    	
       
    private static void renderBlocks(EntityPlayer player, CatWand item, Set<BlockPos> blocklist, int bitsize) {
			double bitscale = bitsize/16f;
 			double renderPosX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
 			double renderPosY = Minecraft.getMinecraft().getRenderManager().viewerPosY+2;
 			double renderPosZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;

/*
    	for(BlockPos pos : blocklist) {
    		renderBlock(player,item,pos,bitsize);
    	}
    	*/
 		List<blockdistance> sortedlist = new ArrayList<>();

    	for(BlockPos pos : blocklist) {
    		sortedlist.add(new blockdistance(pos,(Math.sqrt(Math.pow((renderPosX - pos.getX()*bitscale),2)+Math.pow((renderPosY - pos.getY()*bitscale),2)+Math.pow((renderPosZ - pos.getZ()*bitscale),2)))));

    	}
        sortedlist.sort(Comparator.comparing(blockdistance::getDistance));

    	for(blockdistance pos : sortedlist) {
    		renderBlock(player,item,pos.position,bitsize);
    	}

		
	}
	private static void renderTestLine(double x1, double y1, double z1, double x2, double y2, double z2,double s,double bitsize) {
		double bitscale = bitsize/16f;
		Tessellator tessellator = Tessellator.getInstance();

		CatenaryHelper catbuilder = new CatenaryHelper();
		List<List<Double>> points = catbuilder.getBitCatPoints(x1, y1, z1, x2, y2, z2,s,bitsize);

		tessellator.getBuffer().begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		for(List<Double> i : points) {
		tessellator.getBuffer().pos(i.get(0)*bitscale, i.get(1)*bitscale, i.get(2)*bitscale).endVertex();
		}
		tessellator.draw();
	}
    private static void renderTestLine2(double x1, double y1, double z1, double x2, double y2, double z2) {
		Tessellator tessellator = Tessellator.getInstance();

		tessellator.getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		tessellator.getBuffer().pos(x1, y1,z1).endVertex();
		tessellator.getBuffer().pos(x2, y2,z2).endVertex();
		tessellator.draw();
    }	
    	
    private static void renderBlock(EntityPlayer player,CatWand wand, BlockPos pos,int bitsize) {
        GlStateManager.pushMatrix();
        
        GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
		GL14.glBlendColor(1f, 1f, 1f, 0.8f);

		

        RenderHelper.disableStandardItemLighting();
        //this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        


        World world = player.getEntityWorld();
        
        
        //Float scaleFactor = (float) wand.BIT_SIZE;
        Float scaleFactor = (float) bitsize;
        GlStateManager.scale(scaleFactor/16f, scaleFactor/16f, scaleFactor/16f);

        
        
        // Translate back to local view coordinates so that we can do the acual rendering here


        //GlStateManager.translate(-te.getPos().getX(), -wand.getPos().getY(), -te.getPos().getZ());
        



        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        

        IBlockState state = Blocks.COBBLESTONE.getDefaultState();
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
                
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, pos, bufferBuilder, false);

        tessellator.draw();


        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }


    
    }
