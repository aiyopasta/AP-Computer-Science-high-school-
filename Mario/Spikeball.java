import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Spikeball extends Sprite
{
	Image ballImage;
	Runner r;
	
	int placeInBlock = 0;
	boolean fall = true;
	
	public Spikeball(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		ballImage = new ImageIcon(r.getClass().getResource("spikeBall.png")).getImage();
		t.addImage(ballImage, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void update()
	{
		if (Runner.grid.isLegalMoveVert(hitbox.y/Runner.CELLSIZE + hitbox.height/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE, hitbox.width/Runner.CELLSIZE))
			fall = true;
		
		if (fall)
		{
			Runner.grid.removeSprite(this);
			
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
				else fall = false;
			}
		
			Runner.grid.addSprite(this);
		}
	}
	
	public void paint()
	{
		r.g.drawImage(ballImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
