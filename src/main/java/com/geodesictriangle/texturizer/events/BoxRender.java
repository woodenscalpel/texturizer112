package com.geodesictriangle.texturizer.events;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.items.TextureWand;



import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Texturizer.MODID)
public class BoxRender {
	
	
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
        if (player.getHeldItemMainhand().getItem() instanceof TextureWand) {
            //drawBox(player, event.getMatrixStack());
        	ItemStack wand = player.getHeldItemMainhand();
        	
        	
        	 NBTTagCompound nbt =  wand.getOrCreateSubCompound("myNBT");

             int x1 = nbt.getInteger("corner1x");
             int y1 = nbt.getInteger("corner1y");
             int z1 = nbt.getInteger("corner1z");
             int x2 = nbt.getInteger("corner2x");
             int y2 = nbt.getInteger("corner2y");
             int z2 = nbt.getInteger("corner2z");
             boolean ValidArea = nbt.getBoolean("corner2Valid");
             
             if(ValidArea) {         	                 	 
            	GlStateManager.pushMatrix();
         		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
         		GlStateManager.disableDepth();
         		GlStateManager.disableTexture2D();
         		GlStateManager.enableBlend();

         		
         		

        	AxisAlignedBB area = new AxisAlignedBB(x1,y1,z1,x2,y2,z2);
            int xlen = (int) area.maxX - (int) area.minX + 1;
            int ylen = (int) area.maxY - (int) area.minY + 1;
            int zlen = (int) area.maxZ - (int) area.minZ + 1;
            area = new AxisAlignedBB(area.minX,area.minY,area.minZ,area.minX+xlen,area.minY+ylen,area.minZ+zlen);

 			//area = area.offset(-x1, -y1, -(z1 + 1));
        	
 			double renderPosX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
 			double renderPosY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
 			double renderPosZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;

 			GlStateManager.pushMatrix();
 			GlStateManager.translate(- renderPosX,- renderPosY,- renderPosZ);
 			Color colorRGB = new Color(555555);
 			GL11.glColor4ub((byte) colorRGB.getRed(), (byte) colorRGB.getGreen(), (byte) colorRGB.getBlue(), (byte) 255);

 		
 					

 					GlStateManager.scale(1F, 1F, 1F);

 					GL11.glLineWidth(3F);
 					renderBlockOutline(area);

 		
 			

 			GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
 			GlStateManager.popMatrix();
 			
 			
 			GlStateManager.enableDepth();
 			GlStateManager.enableTexture2D();
 			GlStateManager.disableBlend();
 			GL11.glPopAttrib();
 			GlStateManager.popMatrix();
             }
			
        }
			

		
	}
    	
    	

    
    private static void renderBlockOutline(AxisAlignedBB aabb) {
		Tessellator tessellator = Tessellator.getInstance();

		double ix = aabb.minX;
		double iy = aabb.minY;
		double iz = aabb.minZ;
		double ax = aabb.maxX;
		double ay = aabb.maxY;
		double az = aabb.maxZ;

		tessellator.getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		tessellator.getBuffer().pos(ix, iy, iz).endVertex();
		tessellator.getBuffer().pos(ix, ay, iz).endVertex();

		tessellator.getBuffer().pos(ix, ay, iz).endVertex();
		tessellator.getBuffer().pos(ax, ay, iz).endVertex();

		tessellator.getBuffer().pos(ax, ay, iz).endVertex();
		tessellator.getBuffer().pos(ax, iy, iz).endVertex();

		tessellator.getBuffer().pos(ax, iy, iz).endVertex();
		tessellator.getBuffer().pos(ix, iy, iz).endVertex();

		tessellator.getBuffer().pos(ix, iy, az).endVertex();
		tessellator.getBuffer().pos(ix, ay, az).endVertex();

		tessellator.getBuffer().pos(ix, iy, az).endVertex();
		tessellator.getBuffer().pos(ax, iy, az).endVertex();

		tessellator.getBuffer().pos(ax, iy, az).endVertex();
		tessellator.getBuffer().pos(ax, ay, az).endVertex();

		tessellator.getBuffer().pos(ix, ay, az).endVertex();
		tessellator.getBuffer().pos(ax, ay, az).endVertex();

		tessellator.getBuffer().pos(ix, iy, iz).endVertex();
		tessellator.getBuffer().pos(ix, iy, az).endVertex();

		tessellator.getBuffer().pos(ix, ay, iz).endVertex();
		tessellator.getBuffer().pos(ix, ay, az).endVertex();

		tessellator.getBuffer().pos(ax, iy, iz).endVertex();
		tessellator.getBuffer().pos(ax, iy, az).endVertex();

		tessellator.getBuffer().pos(ax, ay, iz).endVertex();
		tessellator.getBuffer().pos(ax, ay, az).endVertex();

		tessellator.draw();
	}
    	
    	
    	
    	
    	
    	
    	
       

    
    }
