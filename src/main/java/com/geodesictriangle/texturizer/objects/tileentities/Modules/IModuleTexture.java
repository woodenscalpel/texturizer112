package com.geodesictriangle.texturizer.objects.tileentities.Modules;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public interface IModuleTexture {
    //TextureAtlasSprite getTexture();
    FACING getTextureFacing();
	ResourceLocation getTextureLocation();

}
