import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Cloud extends Sprite
{
	Image cloudLeft;
	Image cloudRight;
	Runner r;
	
	public static int placeInBlock;
	
	int state = 1; //1=left, 2=right
	
	public Cloud(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		cloudLeft = new ImageIcon(r.getClass().getResource("cloudLeft.png")).getImage();
		t.addImage(cloudLeft, 0);
		cloudRight = new ImageIcon(r.getClass().getResource("cloudRight.png")).getImage();
		t.addImage(cloudRight, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void update()
	{
		Runner.grid.removeSprite(this);
		
		if (hitbox.x - 1 <= Runner.currentX)
			state = 2;
		if (hitbox.x + hitbox.width >= Runner.currentX + Runner.WWIDTH)
			state = 1;
			
		if (state==2)
		{
			hitbox.x+=4;
		}
		
		if (state==1)
		{
			hitbox.x-=4;
		}
		
		Runner.grid.addSprite(this);
	}
	
	public void paint()
	{
		if (state==1)
			r.g.drawImage(cloudLeft, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
		else if (state==2)
			r.g.drawImage(cloudRight, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}