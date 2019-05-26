import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

public class Mushroom extends Sprite
{
	Image mushroomImage;
	Runner r;
	boolean collected = false;
	int dir = 0; //0 = left, 1 = right
	
	int dy = 1; //In terms of cellsize
	int cellsLeft = 0;
	int placeInBlock = 0; //From 0 to 9
	final int GRAVITY = 4;
	
	int placeInBlockX = 0;
	
	public Mushroom(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		mushroomImage = new ImageIcon(r.getClass().getResource("mushroom.png")).getImage();
		t.addImage(mushroomImage, 0);
		
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
	
	public void update()
	{
		Runner.grid.removeSprite(this);
		
		if (dir==0)
		{
			if (placeInBlockX>=20)
			{
				placeInBlockX=0;
			}
			
			if (placeInBlockX>0)
			{
				hitbox.x-=1;
				placeInBlockX++;
			}
			
			if (placeInBlockX==0)
			{
				if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, (hitbox.x/Runner.CELLSIZE)-1, hitbox.height/Runner.CELLSIZE))
					placeInBlockX++;
				else dir = 1;
			}
		}
		
		if (dir==1)
		{
			//if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE + hitbox.width/Runner.CELLSIZE, hitbox.height/Runner.CELLSIZE))
				//hitbox.x+=20;
			//else dir = 0;
			
			if (placeInBlockX>=20)
			{
				placeInBlockX=0;
			}
			
			if (placeInBlockX>0)
			{
				hitbox.x+=1;
				placeInBlockX++;
			}
			
			if (placeInBlockX==0)
			{
				if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE + hitbox.width/Runner.CELLSIZE, hitbox.height/Runner.CELLSIZE))
					placeInBlockX++;
				else dir = 0;
			}
		}
		
		fall();
		Runner.grid.addSprite(this);
	}
	
	public void fall()
	{
		if (cellsLeft==0 && dy!=0)
		{
			dy+=GRAVITY;
			cellsLeft=dy;
		}
		else if (cellsLeft>0)
		{
			if (placeInBlock==10)
			{
				placeInBlock=0;
				cellsLeft--;
			}
			
			if (placeInBlock>0)
			{
				//Runner.grid.removeSprite(this);
				hitbox.y+=2;
			//	Runner.grid.addSprite(this);
				placeInBlock++;
			}
			else if (placeInBlock==0)
			{
				if (Runner.grid.isLegalMoveVert(hitbox.y/Runner.CELLSIZE + hitbox.height/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE, hitbox.width/Runner.CELLSIZE))
				{
					placeInBlock++;
					hitbox.y+=2;
				}
				else
				{
					cellsLeft = 0;
					dy = 1;
					placeInBlock = 0;
				}
			}
		}
	}
	
	public void paint()
	{
		if (!collected)
			r.g.drawImage(mushroomImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
