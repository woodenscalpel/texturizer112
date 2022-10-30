package com.geodesictriangle.texturizer.objects.items.catwand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.geodesictriangle.texturizer.Texturizer;
import com.geodesictriangle.texturizer.network.messages.MessageChangeNBT;
import com.geodesictriangle.texturizer.util.helpers.IdHelper;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiCat extends GuiContainer
{

	private double lenoffset;
	private double bitoffset;

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
        NBTTagCompound nbt =  wand.getOrCreateSubCompound("myNBT");
        Float bitsize = nbt.getFloat("bitsize");
        Float length = nbt.getFloat("length");
        
        //button specific code. Can't be switch statement as 'id' is given out after compile time, and not constant
        
		if(button.id == lengthmodule.incbutton.id) {
        Texturizer.network.sendToServer(new MessageChangeNBT("length",(float) (length + lenoffset)));
        lenoffset += 0.1;
        Texturizer.logger.error(String.format("%f, %f",length, lenoffset));
		}
		if(button.id == lengthmodule.decbutton.id) {
                  Texturizer.network.sendToServer(new MessageChangeNBT("length",(float) (length + lenoffset)));
        lenoffset -= 0.1;
		}
		if(button.id ==  bitmodule.incbutton.id) {
                  if(bitsize + bitoffset < 16) {
        bitoffset += 1;
                  }
     			 Texturizer.network.sendToServer(new MessageChangeNBT("bitsize",(float) (bitsize + bitoffset)));

		}
		if(button.id == bitmodule.decbutton.id) {
                  if(bitsize + bitoffset > 1) {
        bitoffset -= 1;
                  }
     			 Texturizer.network.sendToServer(new MessageChangeNBT("bitsize",(float) (bitsize + bitoffset)));
		}
		if(button.id == buildbutton.id) {
			//TODO BUILD SIGNAL MESSAGE (I deleted the framework to send signals lol :^) )
                 // Texturizer.network.sendToServer(new MessageChangeNBT(6));
		}
		updateText();
		super.actionPerformed(button);
	}

	private void updateText() {
        NBTTagCompound nbt =  wand.getOrCreateSubCompound("myNBT");
        Float bitsize = nbt.getFloat("bitsize");
        Float length = nbt.getFloat("length");
		lengthmodule.textfield.setText(String.format("%.2f",length + lenoffset));
		bitmodule.textfield.setText(String.format("%.0f",bitsize + bitoffset));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		super.drawScreen(mouseX, mouseY, partialTicks);

		for(GuiTextField textbox : textlist) {
			textbox.drawTextBox();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		// TODO Auto-generated method stub
		super.mouseClicked(mouseX, mouseY, mouseButton);
		lengthmodule.textfield.mouseClicked(mouseX, mouseY, mouseButton);
		bitmodule.textfield.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected boolean checkHotbarKeys(int keyCode) {
		// TODO Auto-generated method stub
		return super.checkHotbarKeys(keyCode);
	}
	private static final ResourceLocation GUI_CAT = new ResourceLocation(Texturizer.MODID + ":textures/gui/catwandgui.png");
	private final InventoryPlayer playerInv;
	private final ItemStack wand;


	List<GuiTextField> textlist = new ArrayList<>();


	GuiValueModule lengthmodule;
	GuiValueModule bitmodule;

	GuiValueModule p1xmod;
	GuiValueModule p1ymod;
	GuiValueModule p1zmod;

	GuiValueModule p2xmod;
	GuiValueModule p2ymod;
	GuiValueModule p2zmod;

	GuiButton buildbutton;
	
	public GuiCat(InventoryPlayer playerInv, ItemStack wandInv, EntityPlayer player) 
	{
		super(new CatContainer(playerInv, wandInv, player));
		this.playerInv = playerInv;
		this.wand = wandInv;
		
		this.xSize = 248;
		this.ySize = 196;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(GUI_CAT);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		this.fontRenderer.drawString("Length", 8, 20, 000000);
		this.fontRenderer.drawString("Bit Size", 8, 30, 000000);
	    this.fontRenderer.drawString("Wand Inventory", 152, 20, 4210752);
		this.fontRenderer.drawString(this.wand.getDisplayName(), 8, 6, 000000);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 000000);
	}

	@Override
	public void initGui() {

		IdHelper idhelp = new IdHelper();

		int centerx = (this.width - this.xSize) /2;
		int centery = (this.height - this.ySize) /2;

        NBTTagCompound nbt =  wand.getOrCreateSubCompound("myNBT");
        Float length = nbt.getFloat("length");
        Float bitsize = nbt.getFloat("bitsize");

		buttonList.clear();

		lengthmodule = new GuiValueModule(this, centerx + 55, centery + 20, 50, idhelp);
        lengthmodule.textfield.setText(String.format("%.2f",length));

		bitmodule = new GuiValueModule(this, centerx + 55, centery + 30, 50, idhelp);
        bitmodule.textfield.setText(String.format("%.0f",bitsize));

		p1xmod = new GuiValueModule(this, centerx + 20, centery + 50, 20, idhelp);
        //p1xmod.textfield.setText(String.format("%.0f",bitsize));
		p1ymod = new GuiValueModule(this, centerx + 20, centery + 60, 20, idhelp);
        //p1xmod.textfield.setText(String.format("%.0f",bitsize));
		p1zmod = new GuiValueModule(this, centerx + 20, centery + 70, 20, idhelp);
        //p1xmod.textfield.setText(String.format("%.0f",bitsize));

		p2xmod = new GuiValueModule(this, centerx + 90, centery + 50, 20, idhelp);
        //p1xmod.textfield.setText(String.format("%.0f",bitsize));
		p2ymod = new GuiValueModule(this, centerx + 90, centery + 60, 20, idhelp);
        //p1xmod.textfield.setText(String.format("%.0f",bitsize));
		p2zmod = new GuiValueModule(this, centerx + 90, centery + 70, 20, idhelp);
        //p1xmod.textfield.setText(String.format("%.0f",bitsize));

		buildbutton = new GuiButton(idhelp.getnextid(), centerx + 50, centery + 85, 50, 12, "BUILD");
		buttonList.add(buildbutton);


		//test2.setEnableBackgroundDrawing(true);
		//test2.setFocused(true);
		super.initGui();
	}
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		lengthmodule.textfield.textboxKeyTyped(typedChar, keyCode);
		bitmodule.textfield.textboxKeyTyped(typedChar, keyCode);
		try {
		Float length = Float.parseFloat(lengthmodule.textfield.getText());
        NBTTagCompound nbt =  wand.getOrCreateSubCompound("myNBT");
        lenoffset = 0;
        Texturizer.network.sendToServer(new MessageChangeNBT("length",(float) (length + lenoffset)));
        nbt.setFloat("length", length);
		}
		catch(NumberFormatException e) {}
		finally {}

		try {
		Integer test2 = Integer.parseInt(bitmodule.textfield.getText());
		if(test2 > 16) {test2 = 16;}
		if(test2 < 1) {test2 = 1;}
        NBTTagCompound nbt =  wand.getOrCreateSubCompound("myNBT");
        //Texturizer.network.sendToServer(new MessageChangeNBT(5,(float) test2));
        Texturizer.network.sendToServer(new MessageChangeNBT("bitsize",(float) (test2)));
        bitoffset = 0;
        nbt.setFloat("bitsize", test2);
		}
		catch(NumberFormatException e) {}
		finally {}

		super.keyTyped(typedChar, keyCode);
		/*
		if(!lentext.textboxKeyTyped(typedChar, keyCode)) {
		super.keyTyped(typedChar, keyCode);
		}
		*/
	}






	private class GuiValueModule {
		private int x;
		private int y;
		private int textboxwidth;
		private GuiTextField textfield;
		private GuiButton incbutton;
		private GuiButton decbutton;
		private GuiCat parent;
		//ids
		final public int pid;
		final public int mid;
		final public int tid;

		private int pad = 5;
		private int h= 10;

		public GuiValueModule(GuiCat parent, int x, int y, int textboxwidth,IdHelper idhelper) {
			this.x = x;
			this.y = y;
			this.pid = idhelper.getnextid();
			this.mid = idhelper.getnextid();
			this.tid = idhelper.getnextid();
			this.parent = parent;
			this.textboxwidth = textboxwidth;
			this.decbutton = new GuiButton(pid, x, y, h, h, "-");
			this.textfield = new GuiTextField(tid, parent.fontRenderer, x+pad+h, y, textboxwidth, h);
			this.incbutton = new GuiButton(mid,x+textboxwidth+h+pad*2, y, h, h, "+");
			buttonList.add(decbutton);
			buttonList.add(incbutton);
			textlist.add(textfield);
		}

		public void addbuttons() {
			buttonList.add(decbutton);
			buttonList.add(incbutton);
		}
		public void drawtext() {
			this.textfield.drawTextBox();
		}
		public int getpid() {
			return this.pid;
		}
		
	}

}