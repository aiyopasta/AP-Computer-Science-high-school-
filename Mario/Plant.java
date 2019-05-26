import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

public class Plant extends Sprite
{
	Image plantImageUp;
	Image plantImageBite;
	Image plantImageDown;
	Runner r;
	
	int state = 1; //1=up, 2=bite, 3=down
	int counter = 0;
	
	int health = 5;
	
	boolean killed = false;
	
	int x, y, width, height;
	
	public Plant(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;

		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		plantImageUp = new ImageIcon(r.getClass().getResource("marioPlantUp.gif")).getImage();
		t.addImage(plantImageUp, 0);
		plantImageBite = new ImageIcon(r.getClass().getResource("marioPlantBite.gif")).getImage();
		t.addImage(plantImageBite, 0);
		plantImageDown = new ImageIcon(r.getClass().getResource("marioPlantDown.gif")).getImage();
		t.addImage(plantImageDown, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void hit()
	{
		health--;
		if (health<=0)
			kill();
	}
	
	public void kill()
	{
		killed = true;
		r.grid.removeSprite(this);
	}
	
	public void paint()
	{
		counter++;
		
		if (counter>600)
		{
			counter = 0;
			plantImageUp.flush();
			plantImageBite.flush();
			plantImageDown.flush();
			Runner.grid.removeSprite(this);
			hitbox.x = x;
			hitbox.y = y;
			hitbox.width = width;
			hitbox.height = height;
			Runner.grid.addSprite(this);
		}
		
		if (!killed && counter<=150)
			r.g.drawImage(plantImageUp, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height-15, r);
		else if (!killed && counter>=150 && counter<=300)
			r.g.drawImage(plantImageBite, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height-15, r);
		else if (!killed && counter>=300 && counter<400)
			r.g.drawImage(plantImageDown, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height-15, r);
		
		if (counter>400)
		{
			Runner.grid.removeSprite(this);
			hitbox.width = hitbox.height = hitbox.x = hitbox.y = 0;
			Runner.grid.addSprite(this);
		}
		
		//super.paint();
	}
}
