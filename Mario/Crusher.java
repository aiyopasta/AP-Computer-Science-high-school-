import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Crusher extends Sprite
{
	Image crusherUp;
	Image crusherDown;
	Runner r;
	
	int placeInBlock;
	
	int state = 1; //0=stationary, 1=down, 2=up
	
	public Crusher(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		crusherUp = new ImageIcon(r.getClass().getResource("crusherUp.png")).getImage();
		t.addImage(crusherUp, 0);
		crusherDown = new ImageIcon(r.getClass().getResource("crusherDown.png")).getImage();
		t.addImage(crusherDown, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void update()
	{
		Runner.grid.removeSprite(this);
		
		if (hitbox.y/Runner.CELLSIZE < 25)
			state = 1;
		
		if (state==2)
		{
			if (placeInBlock>=5)
			{
				placeInBlock=0;
			}
			
			if (placeInBlock>0)
			{
				hitbox.y-=4;
				placeInBlock++;
			}
			
			if (placeInBlock==0)
			{
				if (Runner.grid.isLegalMoveVert(hitbox.y/Runner.CELLSIZE - 1, hitbox.x/Runner.CELLSIZE, hitbox.width/Runner.CELLSIZE))
					placeInBlock++;
				else state = 1;
			}
		}
		
		if (state==1)
		{
			//if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE + hitbox.width/Runner.CELLSIZE, hitbox.height/Runner.CELLSIZE))
				//hitbox.x+=20;
			//else dir = 0;
			
			if (placeInBlock>=4)
			{
				placeInBlock=0;
			}
			
			if (placeInBlock>0)
			{
				hitbox.y+=5;
				placeInBlock++;
			}
			
			if (placeInBlock==0)
			{
				if (Runner.grid.isLegalMoveVert(hitbox.y/Runner.CELLSIZE + hitbox.height/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE, hitbox.width/Runner.CELLSIZE))
					placeInBlock++;
				else state = 2;
			}
		}
		
		Runner.grid.addSprite(this);
	}
	
	public void paint()
	{
		if (state==0 || state==2)
			r.g.drawImage(crusherUp, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
		else if (state==1)
			r.g.drawImage(crusherDown, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}