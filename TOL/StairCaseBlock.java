import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StairCaseBlock 
{
	Block b1;
	Block b2;
	Block b3;

	public StairCaseBlock(int x, int y, int height, int startingWidth, int additiveFactor, Graphics g)
	{
		b1 = new Block(x+(additiveFactor*2), y, startingWidth, height, g);
		b2 = new Block(x+additiveFactor, y+height, startingWidth+additiveFactor*2, height, g);
		b3 = new Block(x, y+height*2, startingWidth + additiveFactor*4, height, g);
	}
	
	public ArrayList<Block> getBlocks()
	{
		ArrayList<Block> temp = new ArrayList<Block>();
		temp.add(b1);
		temp.add(b2);
		temp.add(b3);
		return temp;
	}
	
	public void draw()
	{
		b1.draw();
		b2.draw();
		b3.draw();
	}
}