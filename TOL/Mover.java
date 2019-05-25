import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Mover
{
	int x, y;
	Rectangle rect;

	public Mover(int x, int y)
	{
		this.x = x;
		this.y = y;
		rect = new Rectangle(x, y, 50, 50);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Rectangle getRect()
	{
		return rect;
	}

	public void setFrame(int x, int y)
	{
		this.x = x;
		this.y = y;

		rect = new Rectangle(x, y, 50, 50);
	}
}
