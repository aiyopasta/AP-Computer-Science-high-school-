import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SpikeTrail 
{
	String side;
	int locOnSide;
	Graphics g;
	int amount;
	
	ArrayList<Spike> spks = new ArrayList<Spike>();
	
	public SpikeTrail(String side, int loc, int amount, Graphics g)
	{
		this.side = side;
		locOnSide = loc;
		this.g = g;
		this.amount = amount;
		
		for (int x=0;x<amount;x++)
			spks.add(new Spike(side, loc + x*20, g));
	}
	
	public void draw()
	{
		for (Spike spk : spks)
			spk.draw();
	}
	
	public boolean intersectsWith(Mover m)
	{
		for (Spike spk : spks)
			if (spk.intersectsWith(m))
				return true;
		
		return false;
	}
}
