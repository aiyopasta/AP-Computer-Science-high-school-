import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Streak
{
	Line2D line;
	final int MAX_SLOPE = 10;
	double slope=0, length, lengthSquared;
	Rectangle rekt = new Rectangle(0,MissleCommand.W_HEIGHT,MissleCommand.W_WIDTH,MissleCommand.W_HEIGHT);
	Rectangle rekt2 = new Rectangle(MissleCommand.W_WIDTH, 0, MissleCommand.W_WIDTH, MissleCommand.W_HEIGHT);
	Rectangle rekt3 = new Rectangle(-MissleCommand.W_WIDTH, 0, MissleCommand.W_WIDTH,MissleCommand. W_HEIGHT);

	boolean isValid = true;
	boolean hitShield = false;

	public Streak(Point2D passThroughPoint, int length)
	{
		this.length = length;
		this.lengthSquared = Math.pow(length,2);

		double x1 = passThroughPoint.getX();
		double y1 = passThroughPoint.getY();
		double randX = x1;

		while (randX==x1)
			randX = (int)(Math.random()*MissleCommand.W_WIDTH);

		double randY = 0;

		double slope = y1/Math.abs(randX-x1);

		double dx = Math.sqrt(lengthSquared/(1 + Math.pow(slope, 2)));
		double dy = slope*dx;

		double x2=0, y2 = -dy;

		if (randX>x1)
			x2 = (randX + dx);
		else if (randX<x1)
			x2 = (randX - dx);

		line = new Line2D.Double(randX, randY, x2, y2);
	}

	public void moveFrame()
	{
		try
		{
			slope = (line.getBounds().getHeight()/line.getBounds().getWidth());

			Point2D temp = null;

			if (line.getP1().getX()<line.getP2().getX())
				line.setLine(line.getP1().getX()-1, line.getP1().getY()+slope, line.getP2().getX(), line.getP2().getY());
			else if (line.getP2().getX()<line.getP1().getX())
				line.setLine(line.getP1().getX()+1, line.getP1().getY()+slope, line.getP2().getX(), line.getP2().getY());
		} catch (Exception ex){}
	}

	public boolean isValid()
	{
		try
		{
			if (!isValid)
				return false;

			//System.out.println(slope);
			if (slope>=MAX_SLOPE || line.intersects(rekt) || line.intersects(rekt2) || line.intersects(rekt3))
				isValid = false;
			else isValid = true;
		} catch (Exception ex){}

		return isValid;
	}

	public void setValid(boolean valid)
	{
		isValid = valid;
	}

	public boolean didHitShield(){return hitShield;}

	public boolean intersects(Shield shield)
	{
		if (shield.getCircle().contains(getBottomPoint()))
			hitShield=true;

		return shield.getCircle().contains(getBottomPoint());
	}

	public boolean intersects(Building build)
	{
		if (build.getElps().contains(getBottomPoint()))
			isValid = false;

		return build.getElps().contains(getBottomPoint());
	}

	public Line2D getLine(){return line;}

	public Point2D getBottomPoint()
	{
		if (line.getP1().getY()<line.getP2().getY())
			return line.getP2();

		return line.getP1();
	}
}