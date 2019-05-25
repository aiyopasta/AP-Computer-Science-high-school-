import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Node
{
	int diameter;
	Ellipse2D circle;
	double x,y;
	final int BOOST_SPEED = 15;
	boolean boost = false;

	double xSpeed = 0, ySpeed = 0;

	Node next;

	public Node(double x, double y, int diameter)
	{
		this.x = x+(diameter/2);
		this.y = y+(diameter/2);
		this.diameter = diameter;
		circle = new Ellipse2D.Double(x,y,diameter,diameter);
	}

	public Node(){}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public int getDiameter()
	{
		return diameter;
	}

	public Ellipse2D getCircle()
	{
		return circle;
	}

	public Node getNextNode()
	{
		return next;
	}

	public void setBoost(boolean booost)
	{
		boost = booost;
	}

	public void setNextNode(Node next)
	{
		this.next = next;
	}

	public void setFrame()
	{
		if (next!=null)
		{
			double slope = (next.getY() - y)/(next.getX() - x);
			int speed;

			if (!boost)
				speed = 5;
			else speed = BOOST_SPEED;

			double dx = next.getX() - x;
			double dy = next.getY() - y;

			if (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))<(diameter/3))
				speed = 0;

			if (dx>0)
				if (dy>0)
				{
					xSpeed = Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)));
					ySpeed = Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)));
					circle.setFrame(x+=Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2))), y+=Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2))), diameter, diameter);
					//dy+=diameter;
				}
				else if (dy<0)
				{
					xSpeed = Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)));
					ySpeed = Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)));
					circle.setFrame(x+=Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2))), y-=Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2))), diameter, diameter);
					//dy-=diameter;
				}
				else
				{
					xSpeed = speed;
					ySpeed = 0;
					circle.setFrame(x+=speed, y, diameter, diameter);
				}
			else if (dx<0)
			{
				//dx+=diameter;

				if (dy>0)
				{
					xSpeed = Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)));
					ySpeed = Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)));
					circle.setFrame(x-=Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2))), y+=Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2))), diameter, diameter);
					//dy+=diameter;
				}
				else if (dy<0)
				{
					xSpeed = Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)));
					ySpeed = Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)));
					circle.setFrame(x-=Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2))), y-=Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2))), diameter, diameter);
					//dy-=diameter;
				}
				else
				{
					xSpeed = speed;
					ySpeed = 0;
					circle.setFrame(x-=speed, y, diameter, diameter);
				}
			}
			else
			{
				xSpeed = 0;
				ySpeed = speed;
				circle.setFrame(x, y+=speed, diameter, diameter);
			}

		}
	}

	public double getSpeedX()
	{
		return xSpeed;
	}

	public double getSpeedY()
	{
		return ySpeed;
	}

	public void setFrame(double x, double y, int diameter)
	{
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		circle.setFrame(x, y, diameter, diameter);
	}
}

/*
import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Node
{
	int diameter;
	Ellipse2D circle;
	double x,y;
	final int BOOST_SPEED = 10;
	boolean boost = false;

	int xSpeed = 0, ySpeed = 0;

	Node next;

	public Node(double x, double y, int diameter)
	{
		this.x = x+(diameter/2);
		this.y = y+(diameter/2);
		this.diameter = diameter;
		circle = new Ellipse2D.Double(x,y,diameter,diameter);
	}

	public Node(){}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public int getDiameter()
	{
		return diameter;
	}

	public Ellipse2D getCircle()
	{
		return circle;
	}

	public Node getNextNode()
	{
		return next;
	}

	public void setBoost(boolean booost)
	{
		boost = booost;
	}

	public void setNextNode(Node next)
	{
		this.next = next;
	}

	public void setFrame()
	{
		if (next!=null)
		{
			double slope = (next.getY() - y)/(next.getX() - x);
			int speed;

			if (!boost)
				speed = 5;
			else speed = BOOST_SPEED;

			double dx = next.getX() - x;
			double dy = next.getY() - y;

			if (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))<(diameter/3))
				speed = 0;

			if (dx>0)
				if (dy>0)
				{
					circle.setFrame(x+=xSpeed=(int)(Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)))), y+=ySpeed=(int)(Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)))), diameter, diameter);
					//dy+=diameter;
				}
				else if (dy<0)
				{
					circle.setFrame(x+=xSpeed=(int)(Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)))), y-=ySpeed=(int)(Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)))), diameter, diameter);
					//dy-=diameter;
				}
				else circle.setFrame(x+=xSpeed=(int)(speed), y+=ySpeed=0, diameter, diameter);
			else if (dx<0)
			{
				//dx+=diameter;

				if (dy>0)
				{
					circle.setFrame(x-=xSpeed=(int)(Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)))), y+=ySpeed=(int)(Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)))), diameter, diameter);
					//dy+=diameter;
				}
				else if (dy<0)
				{
					circle.setFrame(x-=xSpeed=(int)(Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope, 2)))), y-=ySpeed=(int)(Math.sqrt((Math.pow(speed, 2)*Math.pow(slope, 2))/(1+Math.pow(slope, 2)))), diameter, diameter);
					//dy-=diameter;
				}
				else circle.setFrame(x-=xSpeed=(int)(speed), y+=ySpeed=0, diameter, diameter);
			}
			else circle.setFrame(x+=xSpeed=0, y+=ySpeed=(int)(speed), diameter, diameter);

		}
	}

	public int getSpeedX()
	{
		return xSpeed;
	}

	public int getSpeedY()
	{
		return ySpeed;
	}

	public void setFrame(double x, double y, int diameter)
	{
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		circle.setFrame(x, y, diameter, diameter);
	}
}*/