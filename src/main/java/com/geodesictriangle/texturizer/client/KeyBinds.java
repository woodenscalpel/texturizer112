package com.geodesictriangle.texturizer.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.geodesictriangle.texturizer.Texturizer;


import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

//@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Texturizer.MODID)
public class KeyBinds extends Texturizer{


	
	public static KeyBinding inv;
	public static KeyBinding mode;

	public static KeyBinding debug1;
	public static KeyBinding debug2;
	public static KeyBinding debug3;
	public static KeyBinding debug4;
/*	
	@SubscribeEvent
	@EventHandler
	@SideOnly(Side.CLIENT)
	public void init(FMLInitializationEvent event) {
		*/
	public static void init() {
	
		mode = new KeyBinding("key.texturizer.toggleWandMode", Keyboard.KEY_M, "key.categories.texturizer");
		inv = new KeyBinding("key.texturizer.openWandInv", Keyboard.KEY_G, "key.categories.texturizer");

		debug1 = new KeyBinding("key.texturizer.pluslength", Keyboard.KEY_P, "key.categories.texturizer");
		debug2 = new KeyBinding("key.texturizer.minuslength", Keyboard.KEY_O, "key.categories.texturizer");

		debug3 = new KeyBinding("key.texturizer.plusbit", Keyboard.KEY_K, "key.categories.texturizer");
		debug4 = new KeyBinding("key.texturizer.minusbit", Keyboard.KEY_J, "key.categories.texturizer");

		ClientRegistry.registerKeyBinding(mode);
		ClientRegistry.registerKeyBinding(inv);
		ClientRegistry.registerKeyBinding(debug1);
		ClientRegistry.registerKeyBinding(debug2);
		ClientRegistry.registerKeyBinding(debug3);
		ClientRegistry.registerKeyBinding(debug4);
		

		//MinecraftForge.EVENT_BUS.register(this);
	}
	

}
