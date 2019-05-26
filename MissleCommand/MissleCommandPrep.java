import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class MissleCommandPrep extends JPanel implements Runnable, MouseListener
{
	final int W_WIDTH = 1000;
	final int W_HEIGHT = 700;
	JFrame frame = new JFrame();
	Thread t;
	Graphics g;

	ArrayList<Ellipse2D> circles = new ArrayList<Ellipse2D>(),
	increasing = new ArrayList<Ellipse2D>(),
	decreasing = new ArrayList<Ellipse2D>();

	ArrayList<Line2D> lines = new ArrayList<Line2D>();

	Rectangle rekt = new Rectangle(0,W_HEIGHT,W_WIDTH,W_HEIGHT);
	Rectangle rekt2 = new Rectangle(W_WIDTH, 0, W_WIDTH, W_HEIGHT);
	Rectangle rekt3 = new Rectangle(-W_WIDTH, 0, W_WIDTH, W_HEIGHT);

	public MissleCommandPrep()
	{
		frame.add(this);
		frame.addMouseListener(this);
		//frame.setResizable(false);
		frame.setSize(W_WIDTH, W_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		t = new Thread(this);
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;

		g2D.setStroke(new BasicStroke(7));
		g2D.setColor(Color.RED);

		g2D.fill(rekt);
		g2D.fill(rekt2);
		g2D.fill(rekt3);

		for (Ellipse2D circle : circles)
		{
			g2D.fill((Ellipse2D)circle);
		}

		for (Line2D line : lines)
		{
			g2D.draw(line);
		}
	}

	public void run()
	{
		while(true)
		{
			//circles

			for (Ellipse2D circle : increasing)
			{
				circle.setFrame(circle.getX()-1, circle.getY()-1, circle.getWidth()+2, circle.getHeight()+2);
			}

			for (Ellipse2D circle : decreasing)
			{
				circle.setFrame(circle.getX()+1, circle.getY()+1, circle.getWidth()-2, circle.getHeight()-2);
			}

			try
			{
				for (Ellipse2D circle : circles)
				{
					if (circle.getHeight()>=200)
					{
						increasing.remove(circle);
						decreasing.add(circle);
					}

					if (circle.getHeight()<=0)
					{
						decreasing.remove(circle);
						circles.remove(circle);
					}
				}
			} catch (Exception ex){}

			//lines

			try
			{

				for (Line2D line : lines)
				{
					double slope = (line.getBounds().getHeight()/line.getBounds().getWidth());

					Point2D temp = null;

					if (line.getP1().getX()<line.getP2().getX())
						line.setLine(line.getP1().getX()-1, line.getP1().getY()+slope, line.getP2().getX()-1, line.getP2().getY()+slope);
					else if (line.getP2().getX()<line.getP1().getX())
						line.setLine(line.getP1().getX()+1, line.getP1().getY()+slope, line.getP2().getX()+1, line.getP2().getY()+slope);
				}
			} catch (Exception e){}

			try
			{
				for (Line2D line : lines)
					if ((rekt.contains(line.getP2())) || (rekt2.contains(line.getP2())) || (rekt3.contains(line.getP2())))
						lines.remove(line);
			} catch (Exception ex){}

			repaint();
			try
			{
				t.sleep(5);
			}
			catch(InterruptedException e)
			{
			}
		}
	}

	public void mouseExited(MouseEvent me)
	{
	}

	public void mouseEntered(MouseEvent me)
	{
	}

	public void mouseReleased(MouseEvent me)
	{
	}

	public void mousePressed(MouseEvent me)
	{
		Ellipse2D circle = new Ellipse2D.Double(me.getX()-31, me.getY()-52, 60, 60);
		circles.add(circle);
		increasing.add(circle);

		final double LINE_LENGTH = 40;
		final double LINE_LENGTH_SQUARED = Math.pow(LINE_LENGTH,2);

		double x1 = me.getX();
		double y1 = me.getY();
		double randX = x1;

		while (randX==x1)
			randX = (int)(Math.random()*W_WIDTH);

		double randY = 0;

		double slope = y1/Math.abs(randX-x1);

		double dx = Math.sqrt(LINE_LENGTH_SQUARED/(1 + Math.pow(slope, 2)));
		double dy = slope*dx;

		double x2=0, y2 = -dy;

		if (randX>x1)
			x2 = (randX + dx);
		else if (randX<x1)
			x2 = (randX - dx);

		Line2D line = new Line2D.Double(randX, randY, x2, y2);
		lines.add(line);

		/*System.out.println("MOUSE CLICKED @ " + x1 + ", " + y1);
		System.out.println("Random Point Chosen: " + randX + ", " + randY);
		System.out.println("Slope: " + slope);
		System.out.println("Distances: " + dx + ", " + dy);
		System.out.println("Resulting Other Point: " + x2 + ", " + y2);*/
	}

	public void mouseClicked(MouseEvent me)
	{
	}

	public static void main(String args[])
	{
		MissleCommandPrep app=new MissleCommandPrep();
	}
}