import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Pipe
{
	Block tunnel;
	Block entrance;
	Graphics g;

	public Pipe(Graphics g, int i) // 1=starting pipe 2= ending pipe
	{
		this.g = g;

		if (i==1)
		{
			tunnel = new Block(100, 40, 60, 80, g);
			entrance = new Block(90, 120, 80, 25, g);
		}

		if (i==2)
		{
			tunnel = new Block(1380, 700, 80, 60, g);
			entrance = new Block(1355, 690, 25, 80, g);
		}
	}

	public void draw()
	{
		g.setColor(Color.ORANGE);
		tunnel.draw();
		entrance.draw();
	}

	public boolean containsMover(Mover m)
	{
		return tunnel.getRect().contains(m.getRect());
	}
}

















































