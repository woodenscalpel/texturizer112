package com.geodesictriangle.texturizer.objects.items;

import com.geodesictriangle.texturizer.Texturizer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiWand extends GuiContainer
{
	private static final ResourceLocation GUI_WAND = new ResourceLocation(Texturizer.MODID + ":textures/gui/wandgui.png");
	private final InventoryPlayer playerInv;
	private final ItemStack wand;
	
	public GuiWand(InventoryPlayer playerInv, ItemStack wandInv, EntityPlayer player) 
	{
		super(new WandContainer(playerInv, wandInv, player));
		this.playerInv = playerInv;
		this.wand = wandInv;
		
		this.xSize = 248;
		this.ySize = 196;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(GUI_WAND);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		  this.fontRenderer.drawString("Palette", 8, 20, 4210752);
	        this.fontRenderer.drawString("Wand Inventory", 97, 20, 4210752);
		this.fontRenderer.drawString(this.wand.getDisplayName(), 8, 6, 000000);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 000000);
	}
}