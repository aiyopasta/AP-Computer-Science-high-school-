import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Explosion
{
	Ellipse2D circle;
	boolean growOrShrink = true; //t-grow, f-shrink
	int maxRadius;

	public Explosion(double x, double y, int startRadius, int maxRadius)
	{
		circle = new Ellipse2D.Double(x-31, y-52, startRadius, startRadius);
		this.maxRadius = maxRadius;
	}

	public void explodeFrame()
	{
		if (growOrShrink)
		{
			circle.setFrame(circle.getX()-1, circle.getY()-1, circle.getWidth()+2, circle.getHeight()+2);
			growOrShrink = radiusReachedCheck();
		}
		else circle.setFrame(circle.getX()+1, circle.getY()+1, circle.getWidth()-2, circle.getHeight()-2);
	}

	private boolean radiusReachedCheck()
	{
		if (circle.getHeight()>=maxRadius*2)
		{
			return false;
		}

		return true;
	}

	public boolean isValid()
	{
		if (circle.getHeight()==0 && !growOrShrink)
			return false;

		return true;
	}

	public boolean intersects(Shield shield)
	{
		return circle.getBounds().intersects(shield.getCircle().getBounds());
	}

	public Ellipse2D getEllipse(){return circle;}
}