import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Occupant
{
	private int x = 0;
	private int y = 0;
	public int direction; //1 = right, 2 = left, 3 = up, 4 = down
	private Ellipse2D elps;
	private Rectangle rect;
	boolean isHit;

	public Occupant(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;

		elps = new Ellipse2D.Double(x, y, width, height);
	}

	public Occupant(int x, int y, int width, int height, int direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;

		elps = new Ellipse2D.Double(x, y, width, height);
	}

	public Occupant(int x, int y, int width, int height, boolean isHit)
	{
		this.x = x;
		this.y = y;
		this.isHit = isHit;

		rect = new Rectangle(x, y, width, height);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getDirection()
	{
		try
		{
			return direction;
		}
		catch (NullPointerException e)
		{
			return -1;
		}
	}

	public boolean isItHit()
	{
		try
		{
			return isHit;
		}
		catch (NullPointerException e)
		{
			return false;
		}
	}

	public void setFrame(int x, int y, int width, int height)
	{
		elps.setFrame(x, y, width, height);
		this.x = x;
		this.y = y;
	}

	public void setFrame(int x, int y, int width, int height, int direction)
	{
		elps.setFrame(x, y, width, height);
		this.x = x;
		this.y = y;

		this.direction = direction;
	}

	public void setFrame(int x, int y, int width, int height, boolean isHit)
	{
		rect.setFrame(x, y, width, height);
		this.x = x;
		this.y = y;

		this.isHit = isHit;
	}

	public Ellipse2D getElps()
	{
		try
		{
			return elps;
		}
		catch (NullPointerException e)
		{
			return new Ellipse2D.Double (0,0,0,0);
		}
	}

	public Rectangle getRekt()
	{
		try
		{
			return rect;
		}
		catch (NullPointerException e)
		{
			return new Rectangle (0,0,0,0);
		}
	}
}
