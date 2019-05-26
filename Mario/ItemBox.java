import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class ItemBox extends Sprite
{
	Image itemBox;
	Image blockImage;
	Runner r;
	
	boolean hit = false;
	
	public ItemBox(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		itemBox = new ImageIcon(r.getClass().getResource("itemBox.gif")).getImage();
		blockImage = new ImageIcon(r.getClass().getResource("block.png")).getImage();
		t.addImage(blockImage, 0);
		t.addImage(itemBox, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void hit()
	{
		hit = true;
	}
	
	public void paint()
	{
		if (!hit)
			r.g.drawImage(itemBox, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
		else r.g.drawImage(blockImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
