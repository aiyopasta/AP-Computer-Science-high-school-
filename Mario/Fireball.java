import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

public class Fireball extends Sprite
{
	Image fireballImage;
	Runner r;
	boolean extinguish = false;
	int dir; //0 = left, 1 = right
	int dirY;
	
	int dy = 1; //In terms of cellsize
	int cellsLeft = 0;
	int placeInBlock = 0; //From 0 to 9
	final int GRAVITY = 4;
	
	int placeInBlockX = 0;
	
	public Fireball(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		this.r = r;
		Mario temp = r.mario;
		if (temp.imageNumber==1 || temp.imageNumber==3 || temp.imageNumber==5)
			dir = 1;
		else dir = 0;
		
		MediaTracker t = new MediaTracker(r);
		fireballImage = new ImageIcon(r.getClass().getResource("fireball.gif")).getImage();
		t.addImage(fireballImage, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void extinguish()
	{
		extinguish = true;
		r.grid.removeSprite(this);
	}
	
	public void update()
	{
		Runner.grid.removeSprite(this);
		
		if (dir==0)
		{
			if (placeInBlockX>=2)
			{
				placeInBlockX=0;
			}
			
			if (placeInBlockX>0)
			{
				hitbox.x-=10;
				placeInBlockX++;
			}
			
			if (placeInBlockX==0)
			{
				if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, (hitbox.x/Runner.CELLSIZE)-1, hitbox.height/Runner.CELLSIZE))
					placeInBlockX++;
				else
				{
					r.grid.removeSprite(this);
					extinguish();
				}
			}
		}
		
		if (dir==1)
		{
			//if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE + hitbox.width/Runner.CELLSIZE, hitbox.height/Runner.CELLSIZE))
				//hitbox.x+=20;
			//else dir = 0;
			
			if (placeInBlockX>=2)
			{
				placeInBlockX=0;
			}
			
			if (placeInBlockX>0)
			{
				hitbox.x+=10;
				placeInBlockX++;
			}
			
			if (placeInBlockX==0)
			{
				if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE + hitbox.width/Runner.CELLSIZE, hitbox.height/Runner.CELLSIZE))
					placeInBlockX++;
				else
				{
					r.grid.removeSprite(this);
					extinguish();
				}
			}
		}
		
		
		
		Runner.grid.addSprite(this);
	}
	
	public void paint()
	{
		if (!extinguish)
			r.g.drawImage(fireballImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
		
		//super.paint();
	}
}
