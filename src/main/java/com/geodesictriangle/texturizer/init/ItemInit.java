package com.geodesictriangle.texturizer.init;

import java.util.ArrayList;
import java.util.List;

import com.geodesictriangle.texturizer.objects.items.PathWand;
import com.geodesictriangle.texturizer.objects.items.TextureWand;
import com.geodesictriangle.texturizer.objects.items.catwand.CatWand;

import net.minecraft.item.Item;

public class ItemInit{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final Item TEXTURE_WAND =  new TextureWand("texturewand");
	public static final Item PATH_WAND =  new PathWand("pathwand");
	public static final Item CAT_WAND =  new CatWand("catwand");
}
