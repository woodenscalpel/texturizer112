package com.geodesictriangle.texturizer.objects.tileentities;

import static com.geodesictriangle.texturizer.objects.blocks.GuideBlock.FACING;

import java.util.List;
import java.util.Objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;


import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.AbstractModule;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.AbstractTextModule;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.BuildButtonModule;
import com.geodesictriangle.texturizer.objects.tileentities.Modules.IModuleTexture;
import com.geodesictriangle.texturizer.objects.tileentities.Shapes.AbstractShape;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/*
 * main rendering logic largely copy of sign tile renderer
 */
@SideOnly(Side.CLIENT)
public class RenderGuideTile extends TileEntitySpecialRenderer<GuideTile>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Texturizer.MODID + ":textures/blocks/texture.png");
	private static final ResourceLocation BACKTEXTURE = new ResourceLocation(Texturizer.MODID + ":textures/blocks/texture2.png");

    public final static ResourceLocation ARROW_TEXTURE_UP = new ResourceLocation(Texturizer.MODID + ":textures/buildingguide/arrow.png");

    /** The ModelSign instance for use in this renderer */
    private final ModelGuideBlock model = new ModelGuideBlock();

    public void render(GuideTile te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.pushMatrix();

        EnumFacing facing =  te.getWorld().getBlockState(te.getPos()).getValue(FACING);
        float f = 0.0F;
        switch(facing) {
        case SOUTH:
        	default:
        		break;
        	case NORTH:
                f = 180.0F;
                	break;
        	case WEST:
                f = 90.0F;
        		break;
        	case EAST:
                f = -90.0F;
        		break;
        }

            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
            GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
        

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(TEXTURE);
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(-8f/16f, -3.0625f/16f, -1f/16f);
        this.model.renderAll();
        GlStateManager.popMatrix();
        
        //Font time
        
     


        if (destroyStage < 0)
        {
        				//My text, block, texture rendering here

            int screenID = te.ACTIVE_SCREEN;


            List<AbstractModule> modules = te.modules;
            
            
            
            FontRenderer fontrenderer = this.getFontRenderer();
         
   

            if (destroyStage < 0)
            {

            	
                if(modules!=null) {
                    for (AbstractModule module : modules) {
                        if (module.activeScreenID == screenID || module.activeScreenID == -1) {
                        	
                        	if(module instanceof AbstractTextModule) {
                        		
                        		//Render Text
           
                        		((AbstractTextModule) module).updateText();
                                String text = ((AbstractTextModule) module).getText();
                                
                                
                                GlStateManager.pushMatrix();
                                //below line puts matrix at (0,0) of the block
                                GlStateManager.translate(-8f/16f, -3f/16f, 0f);
                                
                                //relative on the module
                                GlStateManager.translate((float)module.x/16, (float)module.y/16, 0.0002F);
                                
                                //scale text to fit in specified box (more or less)
                                float xstretch = 1;
                                float ystretch = 1;
                                int pxLen = (int) (text.length()*1.25);
                                if(pxLen > module.sizex) {
                                    float scale = module.sizex / pxLen;
                                    xstretch = scale;
                                    ystretch = scale;
                                }

                                
                                float scalefactor = 0.010416667F;
                                GlStateManager.scale(1*scalefactor*xstretch, -1*scalefactor*ystretch, 1*scalefactor);       

                                fontrenderer.drawString(text, 0, 0, 0, false);

                                GlStateManager.popMatrix();

                                
                        	}
                        	
                        	if(module instanceof IModuleTexture) {
                        		//Render Texture
                        	     GlStateManager.pushMatrix();
                     


                                 //below line puts matrix at (0,0) of the block
                                 GlStateManager.translate(-7f/16f, -1.5f/16f, 0f);
                                 
                                 
                                 //relative on the module
                                 GlStateManager.translate((float)module.x/16, (float)module.y/16, 0.0002F);
                          
                                 float scalefactor = 2/16F;
                                 GlStateManager.scale(-1*scalefactor, -1*scalefactor, 1*scalefactor);      
                                 
                                 //hardcoded because im fed up
                                 if(module instanceof BuildButtonModule) {
                                     GlStateManager.scale(2.5, 1, 1);   
                                     GlStateManager.translate(-0.3,0,0);
                                 }

                        	        
                        	        
                        
                        	        this.bindTexture(((IModuleTexture) module).getTextureLocation());

                        	        Tessellator tessellator = Tessellator.getInstance();
                        	        BufferBuilder bufferbuilder = tessellator.getBuffer();
                        	        

                        	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);


                        	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
                        	
                        	     
                        	        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
                        	        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
                         	        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
                        	        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
                        	       
                        	        tessellator.draw();
                 


                        	        GlStateManager.disableRescaleNormal();
                        	        GlStateManager.popMatrix();
                        	}
   
                        }
                    }
             
            }
          		//Render Screen Background Texture
       	     GlStateManager.pushMatrix();
    


                //below line puts matrix at (0,0) of the block
          //      GlStateManager.translate(-7f/16f, -1.5f/16f, 0f);
                
                
                //relative on the module
                GlStateManager.translate((float)0f/16f,8.5f/16f, 0.0001F);
         
                float scalefactor = 14/16F;
                GlStateManager.scale(-1*scalefactor, -1*scalefactor, 1*scalefactor);      
                

       	        this.bindTexture(BACKTEXTURE);

       	        Tessellator tessellator = Tessellator.getInstance();
       	        BufferBuilder bufferbuilder = tessellator.getBuffer();
       	        

       	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);


       	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
       	
       	     
       	        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
       	        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        	        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
       	        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
       	       
       	        tessellator.draw();



       	        GlStateManager.disableRescaleNormal();
       	        GlStateManager.popMatrix();
       	        
       	        
       	        
       	  

       
            }
        	
     

        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
        
        //Render Blocks
	        
	       AbstractShape activeshape = te.shapes.get(te.ACTIVE_SCREEN);
	             List<BlockPos> blocksToRender = activeshape.getBlocks();
	             
	             //Render Origin as well
	             blocksToRender.add(te.originOffset.originOffset);


	             for(BlockPos pos : blocksToRender){
	                 if(Objects.requireNonNull(te.getWorld()).getBlockState(te.getPos().add(pos.getX(),pos.getY(),pos.getZ())).getBlock() == Blocks.AIR || te.BIT_SIZE != 16) {
	                	 GlStateManager.pushAttrib();
	                     GlStateManager.pushMatrix();
	                     

	                     GlStateManager.translate(x, y, z);    
	                     
	                     BlockPos uorgin = te.originOffset.uoriginOffset;
	                     GlStateManager.translate(uorgin.getX()/16f,uorgin.getY()/16f,uorgin.getZ()/16f);    



	                     renderBlock(te,pos);

	                     GlStateManager.popMatrix();
	                     GlStateManager.popAttrib();
	                 }
	             }
        
    }
    

    private void renderBlock(GuideTile te, BlockPos pos) {
        GlStateManager.pushMatrix();
        
        GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
		GL14.glBlendColor(1f, 1f, 1f, 0.8f);

		

        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);


        World world = te.getWorld();
        
        
        float scaleFactor = te.BIT_SIZE;
        GlStateManager.scale(scaleFactor/16f, scaleFactor/16f, scaleFactor/16f);

        
        
        // Translate back to local view coordinates so that we can do the acual rendering here


        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
        



        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        

        IBlockState state = Blocks.COBBLESTONE.getDefaultState();
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
                
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos().add(pos.getX(),pos.getY(),pos.getZ()), bufferBuilder, true);

        tessellator.draw();


        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableBlend();
    	GL11.glPopAttrib();
        GlStateManager.popMatrix();
    }
    
    
    


    }