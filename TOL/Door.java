import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Door
{
	//1310, 665, 25, 95
	int height;
	Rectangle r;
	
	public Door()
	{
		height = 95;
		r = new Rectangle(1310, 665, 25, 95);
	}
	
	public Rectangle getRect()
	{
		return r;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setFrameOpen()
	{
		if (height>0)
			height--;
		
		r.setFrame(r.getX(), r.getY(), r.getWidth(), height);
	}
	
	public void setFrameClose()
	{
		if (height<95)
			height++;
		
		r.setFrame(r.getX(), r.getY(), r.getWidth(), height);
	}
}
