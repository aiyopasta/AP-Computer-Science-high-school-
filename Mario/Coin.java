import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Coin extends Sprite
{
	Image coinImage;
	Runner r;
	boolean collected = false;
	
	public Coin(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		coinImage = new ImageIcon(r.getClass().getResource("coin.gif")).getImage();
		t.addImage(coinImage, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void collect()
	{
		collected = true;
		r.grid.removeSprite(this);
	}
	
	public void paint()
	{
		if (!collected)
			r.g.drawImage(coinImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
