import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Flag extends Sprite
{
	Image flagImage;
	Runner r;
	
	public Flag(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		flagImage = new ImageIcon(r.getClass().getResource("marioFlag.png")).getImage();
		t.addImage(flagImage, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void paint()
	{
		r.g.drawImage(flagImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
