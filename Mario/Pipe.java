import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

public class Pipe extends Sprite
{
	Image pipeImage;
	Runner r;
	
	public Pipe(int x, int y, int width, int height, Runner r)
	{
		super(x, y, width, height);
		
		this.r = r;
		
		MediaTracker t = new MediaTracker(r);
		pipeImage = new ImageIcon(r.getClass().getResource("pipe.png")).getImage();
		t.addImage(pipeImage, 0);
		
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
	}
	
	public void paint()
	{
		r.g.drawImage(pipeImage, hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height, r);
	}
}
