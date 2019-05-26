import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Shield
{
	final int DAMAGE_INTERVAL = 15;
	final int START_RADIUS = 100;
	int radius = START_RADIUS;
	Ellipse2D circle;
	public Shield(double x, double y)
	{
		circle = new Ellipse2D.Double(x-31, y-52, START_RADIUS, START_RADIUS);
	}

	public void takeDamage(){radius-=DAMAGE_INTERVAL;circle.setFrame(circle.getX()+DAMAGE_INTERVAL/2, circle.getY()+DAMAGE_INTERVAL/2, radius, radius);}
	public Ellipse2D getCircle(){return circle;}

	public boolean isValid()
	{
		if (!(radius>=20))
			return false;

		return true;
	}
}