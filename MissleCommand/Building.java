import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Building
{
	Rectangle rekt;
	Ellipse2D semicircle;
	boolean isValid = true;
	Color c;

	public Building(int x, int y, Color c)
	{
		rekt = new Rectangle(x, y, 30, 80);
		semicircle = new Ellipse2D.Double(x, y-15, 30, 40);
		this.c = c;
	}

	public Rectangle getRekt()
	{
		return rekt;
	}

	public Ellipse2D getElps()
	{
		return semicircle;
	}

	public boolean isValid()
	{
		return isValid;
	}

	public Color getColor()
	{
		return c;
	}
}