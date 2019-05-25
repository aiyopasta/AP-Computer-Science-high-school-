import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Spike
{
	Polygon p;
	String side;
	Graphics g;
	final int POINT_LENGTH = 30;
	int[] xpoints = new int[3];
	int[] ypoints = new int[3];

	public Spike(String side, int locationOnSide, Graphics g) //r, l , t, b
	{
		this.g = g;
		this.xpoints = new int[3];
		this.ypoints = new int[3];
		int npoints = 3;
		this.side = side;

		if (side.equals("r"))
		{
			xpoints[0] = 1460;
			ypoints[0] = locationOnSide-7;

			xpoints[1] = 1460;
			ypoints[1] = locationOnSide+7;

			xpoints[2] = 1460-POINT_LENGTH;
			ypoints[2] = locationOnSide;
			p = new Polygon(xpoints, ypoints, 3);
		}

		if (side.equals("l"))
		{
			xpoints[0] = 40;
			ypoints[0] = locationOnSide-7;

			xpoints[1] = 40;
			ypoints[1] = locationOnSide+7;

			xpoints[2] = 40+POINT_LENGTH;
			ypoints[2] = locationOnSide;

			p = new Polygon(xpoints, ypoints, 3);
		}

		if (side.equals("t"))
		{
			xpoints[0] = locationOnSide-7;
			ypoints[0] = 40;

			xpoints[1] = locationOnSide+7;
			ypoints[1] = 40;

			xpoints[2] = locationOnSide;
			ypoints[2] = 40+POINT_LENGTH;
			p = new Polygon(xpoints, ypoints, 3);
		}

		if (side.equals("b"))
		{
			xpoints[0] = locationOnSide-7;
			ypoints[0] = 760;

			xpoints[1] = locationOnSide+7;
			ypoints[1] = 760;

			xpoints[2] = locationOnSide;
			ypoints[2] = 760-POINT_LENGTH;
			p = new Polygon(xpoints, ypoints, 3);
		}
	}

	public boolean intersectsWith(Mover m)
	{
		return p.intersects(m.getRect());
	}

	public void draw()
	{
		g.setColor(new Color(0, 204, 0));
		g.fillPolygon(p);
	}
	
	public Polygon getPoly()
	{
		return p;
	}
}





























































