import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Mario extends Sprite
{
	ArrayList<Image> images = new ArrayList<Image>();
	int imageNumber = 1;
	final static int MARIO_LEFT = 0;
	final static int MARIO_RIGHT = 1;
	final static int MARIO_WALK_LEFT = 2;
	final static int MARIO_WALK_RIGHT = 3;
	final static int MARIO_JUMP_LEFT = 4;
	final static int MARIO_JUMP_RIGHT = 5;

	int placeInBlock = 0;
	int state = 1;
	int yInitial;
	boolean killed = false;
	Runner r;
	
	boolean justHit = false;
	int justHitCount = 0;
	
	boolean hasFire = false;
	
	//Gravity Fall :( --> didn't work
	
	//boolean fall;
	//boolean goUp;
	//final int GRAVITY = 4;
	//int dy = 1;
	//int cellsLeft = 0;
	
	public Mario(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		Image marioLeft = new ImageIcon(r.getClass().getResource("marioStationaryLeft.png")).getImage();
		Image marioRight = new ImageIcon(r.getClass().getResource("marioStationaryRight.png")).getImage();
		Image marioWalkLeft = new ImageIcon(r.getClass().getResource("marioWalkingLeft.gif")).getImage();
		Image marioWalkRight = new ImageIcon(r.getClass().getResource("marioWalkingRight.gif")).getImage();
		Image marioJumpLeft = new ImageIcon(r.getClass().getResource("marioJumpLeft.gif")).getImage();
		Image marioJumpRight = new ImageIcon(r.getClass().getResource("marioJumpRight.gif")).getImage();
		
		images.add(marioLeft);
		t.addImage(marioLeft, 0);
		images.add(marioRight);
		t.addImage(marioRight, 0);
		images.add(marioWalkLeft);
		t.addImage(marioWalkLeft, 0);
		images.add(marioWalkRight);
		t.addImage(marioWalkRight, 0);
		images.add(marioJumpLeft);
		t.addImage(marioJumpLeft, 0);
		images.add(marioJumpRight);
		t.addImage(marioJumpRight, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void hit()
	{
		justHit = true;
	}
	
	public boolean isJustHit()
	{
		if (justHit)
			return true;
		
		return false;
	}
	
	public void giveFirePower()
	{
		hasFire = true;
	}
	
	public void removeFirePower()
	{
		hasFire = false;
	}
	
	public void kill()
	{
		killed = true;
		r.grid.removeSprite(this);
	}
	
	public void update(String keyPressed)
	{
		if (justHit)
			justHitCount++;
		
		if (justHitCount>100)
		{
			justHit = false;
			justHitCount = 0;
		}
		
		Runner.grid.removeSprite(this);
		
		if (keyPressed.equals("up"))
		{
			if (!Runner.grid.isLegalMoveVert(hitbox.y/Runner.CELLSIZE + hitbox.height/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE, hitbox.width/Runner.CELLSIZE))
			{
				state = 2;
				yInitial = hitbox.y - 1;
			}
		}
		
		if (keyPressed.equals("down"))
		{
			
		}
		
		if (keyPressed.equals("left"))
		{
			if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, (hitbox.x/Runner.CELLSIZE)-1, hitbox.height/Runner.CELLSIZE))
				hitbox.x-=20;
			
			imageNumber = MARIO_WALK_LEFT;
		}
		
		if (keyPressed.equals("right"))
		{
			if (Runner.grid.isLegalMoveHoriz(hitbox.y/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE + hitbox.width/Runner.CELLSIZE, hitbox.height/Runner.CELLSIZE))
				hitbox.x+=20;
		
			imageNumber = MARIO_WALK_RIGHT;
		}
		
		if (keyPressed.equals("-"))
		{
			if (imageNumber==3 || imageNumber==5)
				imageNumber = 1;
			
			if (imageNumber==2 || imageNumber==4)
				imageNumber = 0;
		}
		
		verticalMovement();
		
		Runner.grid.addSprite(this);
	}
	
	private void verticalMovement()
	{
		if (hitbox.y < yInitial - 200)
			state = 1;
		
		if (state==2)
		{
			if (placeInBlock>=2)
			{
				placeInBlock=0;
			}
			
			if (placeInBlock>0)
			{
				hitbox.y-=10;
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
			
			if (placeInBlock>=2)
			{
				placeInBlock=0;
			}
			
			if (placeInBlock>0)
			{
				hitbox.y+=10;
				placeInBlock++;
			}
			
			if (placeInBlock==0)
			{
				if (Runner.grid.isLegalMoveVert(hitbox.y/Runner.CELLSIZE + hitbox.height/Runner.CELLSIZE, hitbox.x/Runner.CELLSIZE, hitbox.width/Runner.CELLSIZE))
					placeInBlock++;
			}
		}
	}
	
	public void paint()
	{
		if(!killed)
			r.g.drawImage(images.get(imageNumber), hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
		//r.g.drawRect(hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height);
	}
}
