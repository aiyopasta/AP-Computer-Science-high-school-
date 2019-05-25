import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UpsideDownTBlock
{
	Block horiz;
	Block vert;

	public UpsideDownTBlock(int x, int y, int longDist, int shortDist, Graphics g)
	{
		horiz = new Block(x, y+(longDist-shortDist), longDist, shortDist, g);
		vert = new Block(x+(longDist-shortDist)/2, y, shortDist, longDist, g);
	}

	public ArrayList<Block> getBlocks()
	{
		ArrayList<Block> temp = new ArrayList<Block>();
		temp.add(horiz);
		temp.add(vert);
		return temp;
	}

	public void draw()
	{
		horiz.draw();
		vert.draw();
	}
}