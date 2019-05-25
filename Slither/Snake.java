import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Snake
{
	ArrayList<Node> nodes = new ArrayList<Node>();
	Color color;
	int diameter = 30, length = 100;

	public Snake(int i, int j, Color c)
	{
		for (int x=0;x<100;x++)
			nodes.add(new Node(i + x*10, j, 30));

		nodes.get(0).setNextNode(Slither.pointer);

		for (int x=1;x<nodes.size();x++)
			nodes.get(x).setNextNode(nodes.get(x-1));

		color = c;
	}

	public Color getColor()
	{
		return color;
	}

	public ArrayList<Node> getNodes()
	{
		return nodes;
	}

	public int getDiameter()
	{
		return diameter;
	}

	public int getLength()
	{
		return length;
	}

	public void expand()
	{
		for (Node n : nodes)
			n.setFrame(n.getX(), n.getY(), n.getDiameter()+5);
	}

	public double getSpeedX()
	{
		return nodes.get(0).getSpeedX();
	}

	public double getSpeedY()
	{
		return nodes.get(0).getSpeedY();
	}

	public void setColor(Color newColor)
	{
		color = newColor;
	}

	public void setFrame()
	{
		for (Node n : nodes)
			n.setFrame();
	}
}