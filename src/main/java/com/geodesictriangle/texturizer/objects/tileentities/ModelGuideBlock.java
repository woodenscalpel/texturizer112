package com.geodesictriangle.texturizer.objects.tileentities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class ModelGuideBlock extends ModelBase 
{
    public ModelRenderer screen;


    public ModelGuideBlock() 
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.screen = new ModelRenderer(this, 0, 0);

//(0D, 0.0D,15.0/16.0D, 1D, 1.0D, 1D);
//(0D, 0.0D, 0D, 1D, 1.0D, 0.0625D);

        this.screen.addBox(0F, 0F, 0F, 16, 16, 1, 0.0F);


    }

   public void renderAll()
   {
	 
	   this.screen.render(0.0625f);
   }
}