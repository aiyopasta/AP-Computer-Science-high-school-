import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Block
{
	Rectangle r;
	int x,y,width,height;
	Graphics g;

	public Block(int x, int y, int width, int height, Graphics g)
	{
		r = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.g = g;
	}

	public void draw()
	{
		g.fillRect(x, y, width, height);
	}

	public Rectangle getRect()
	{
		return r;
	}
}