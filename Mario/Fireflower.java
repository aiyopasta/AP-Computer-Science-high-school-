import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

public class Fireflower extends Sprite
{
	Image fireflowerImage;
	Runner r;
	boolean collected = false;
	int dir = 0; //0 = left, 1 = right
	
	int dy = 1; //In terms of cellsize
	int cellsLeft = 0;
	int placeInBlock = 0; //From 0 to 9
	final int GRAVITY = 4;
	
	int placeInBlockX = 0;
	
	public Fireflower(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		fireflowerImage = new ImageIcon(r.getClass().getResource("fireFlower.png")).getImage();
		t.addImage(fireflowerImage, 0);
		
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
			r.g.drawImage(fireflowerImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
