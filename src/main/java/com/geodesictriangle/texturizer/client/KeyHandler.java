package com.geodesictriangle.texturizer.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.network.messages.MessageChangeNBT;
import com.geodesictriangle.texturizer.network.messages.MessageModeToggle;
import com.geodesictriangle.texturizer.network.messages.MessageOpenInv;
import com.geodesictriangle.texturizer.network.messages.MessageOpenInv2;
import com.geodesictriangle.texturizer.objects.items.AbstractSwapWand;
import com.geodesictriangle.texturizer.objects.items.catwand.AbstractCatWand;
import com.geodesictriangle.texturizer.objects.items.catwand.CatWand;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Texturizer.MODID)
public class KeyHandler {
	
    //KeyBinding mode = KeyBinds.mode;
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onKeyInput(KeyInputEvent event)
	{
		

		KeyBinding mode = KeyBinds.mode;
		KeyBinding inv = KeyBinds.inv;

		KeyBinding debug1 = KeyBinds.debug1;
		KeyBinding debug2 = KeyBinds.debug2;
		KeyBinding debug3 = KeyBinds.debug3;
		KeyBinding debug4 = KeyBinds.debug4;


		//Below code works to detect keys and do actions client side, but needs to be modified to send the packets to the server side
	
		
        if (mode.isPressed()) //on press
        {

                Minecraft mc = Minecraft.getMinecraft();
                assert mc.player != null;
                if (mc.player.getHeldItemMainhand().getItem() instanceof AbstractSwapWand) {
                    Texturizer.network.sendToServer(new MessageModeToggle());
                  
                }
        }
        
        if (inv.isPressed()) //on press
        {

                Minecraft mc = Minecraft.getMinecraft();
                assert mc.player != null;
                if (mc.player.getHeldItemMainhand().getItem() instanceof AbstractSwapWand) {
                  Texturizer.network.sendToServer(new MessageOpenInv());
           //     ((AbstractSwapWand) mc.player.getHeldItemMainhand().getItem()).openInv(mc.player.world, mc.player);
                }
                if (mc.player.getHeldItemMainhand().getItem() instanceof AbstractCatWand) {
                  Texturizer.network.sendToServer(new MessageOpenInv2());
           //     ((AbstractSwapWand) mc.player.getHeldItemMainhand().getItem()).openInv(mc.player.world, mc.player);
                }
        }    
       if (debug1.isPressed()) //on press
        {

                Minecraft mc = Minecraft.getMinecraft();
                assert mc.player != null;
                if (mc.player.getHeldItemMainhand().getItem() instanceof CatWand) {
                  //Texturizer.network.sendToServer(new MessageChangeNBT(0));
                  //((CatWand) mc.player.getHeldItemMainhand().getItem()).pluslen(mc.player.getHeldItemMainhand());
                }
        }    
   if (debug2.isPressed()) //on press
        {

                Minecraft mc = Minecraft.getMinecraft();
                assert mc.player != null;
                if (mc.player.getHeldItemMainhand().getItem() instanceof CatWand) {
                //  Texturizer.network.sendToServer(new MessageChangeNBT(1));
                  //((CatWand) mc.player.getHeldItemMainhand().getItem()).minuslen(mc.player.getHeldItemMainhand());
                }
        }    
 if (debug3.isPressed()) //on press
        {

                Minecraft mc = Minecraft.getMinecraft();
                assert mc.player != null;
                if (mc.player.getHeldItemMainhand().getItem() instanceof CatWand) {
                //  Texturizer.network.sendToServer(new MessageChangeNBT(2));
                  //((CatWand) mc.player.getHeldItemMainhand().getItem()).plusbit(mc.player.getHeldItemMainhand());
                }
        }    

 if (debug4.isPressed()) //on press
        {

                Minecraft mc = Minecraft.getMinecraft();
                assert mc.player != null;
                if (mc.player.getHeldItemMainhand().getItem() instanceof CatWand) {
               //   Texturizer.network.sendToServer(new MessageChangeNBT(3));
                  //((CatWand) mc.player.getHeldItemMainhand().getItem()).minusbit(mc.player.getHeldItemMainhand());
                }
        }    




 

        }
       

}
