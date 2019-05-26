import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Block extends Sprite
{
	Image blockImage;
	Runner r;
	
	public Block(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		blockImage = new ImageIcon(r.getClass().getResource("block.png")).getImage();
		t.addImage(blockImage, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void paint()
	{
		r.g.drawImage(blockImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
