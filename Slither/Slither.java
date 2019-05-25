import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class Slither extends JPanel implements Runnable, MouseListener, MouseMotionListener
{
	final static int W_WIDTH = 1000;
	final static int W_HEIGHT = 700;
	final static int S_WIDTH = 5000;
	final static int S_HEIGHT = 4000;

	double xCurrent, yCurrent;

	JFrame frame = new JFrame();
	Thread t;
	Graphics g;

	static Node pointer = new Node(S_WIDTH/2, S_HEIGHT/2, 30);
	Snake snake = new Snake((S_WIDTH/2)+20, (S_HEIGHT/2)+20, (Color.RED));

	public Slither()
	{
		frame.add(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		//frame.setResizable(false);
		frame.setSize(W_WIDTH, W_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		xCurrent = ((int)snake.getNodes().get(0).getX())-(W_WIDTH/2);
		yCurrent = ((int)snake.getNodes().get(0).getY())-(W_HEIGHT/2);

		t = new Thread(this);
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;

		try
		{
		Image background = new ImageIcon(this.getClass().getResource("slither-background.jpg")).getImage();


		//g2D.setColor(Color.BLACK);
		//g2D.fill(new Rectangle(0,0,W_WIDTH,W_HEIGHT));
		g2D.drawImage(background, (int)-xCurrent, (int)-yCurrent, S_WIDTH, S_HEIGHT, this);
		} catch(Exception ex){}

		for (int i=0;i<snake.getNodes().size(); i++)
		{
			Color c = snake.getColor();
			//System.out.println(c);

			//try
			//{
				final int colorIncrement = 255/snake.getNodes().size();

			for (int j=0;j<i;j++)
			{
				if (c.getRed()-(colorIncrement)-160>0)
					c = new Color(c.getRed()-(colorIncrement), c.getGreen(), c.getBlue(), 170);
				if (c.getBlue()-(colorIncrement)-160>0)
					c = new Color(c.getRed(), c.getGreen(), c.getBlue()-(colorIncrement), 170);
				if (c.getGreen()-(colorIncrement)-160>0)
					c = new Color(c.getRed(), c.getGreen()-(colorIncrement), c.getBlue(), 170);
				//if (c.getAlpha()-(colorIncrement)-180>0)
					//c = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()-(colorIncrement*j));

				//System.out.println(c.getAlpha());

				//System.out.println(c.getGreen());
			}
			//} catch (Exception ex){System.out.println("Color error");}

			g2D.setColor(c);
			Ellipse2D paintCircle = new Ellipse2D.Double(snake.getNodes().get(i).getX()-xCurrent, snake.getNodes().get(i).getY()-yCurrent, snake.getNodes().get(i).getDiameter(), snake.getNodes().get(i).getDiameter());
			g2D.fill(paintCircle);
		}
	}

	public void run()
	{
		while(true)
		{
			snake.setFrame();

			final int MOVE_SPEED = 5; //The problem is that the xcomp and ycomp of the screen are adding up to go faster than snake.

			if (pointer.getX()>snake.getNodes().get(0).getX() && (xCurrent+W_WIDTH+5)<S_WIDTH)
				xCurrent+=snake.getSpeedX();
			else if (pointer.getX()<snake.getNodes().get(0).getX() && (xCurrent-5)>0)
				xCurrent-=snake.getSpeedX();

			if (pointer.getY()>snake.getNodes().get(0).getY() && (yCurrent+W_HEIGHT+5)<S_HEIGHT)
				yCurrent+=snake.getSpeedY();
			else if (pointer.getY()<snake.getNodes().get(0).getY() && (yCurrent-5)>0)
				yCurrent-=snake.getSpeedY();

			/*if ((snake.getNodes().get(0).getX() - xCurrent)>(W_WIDTH/3) && (xCurrent+W_WIDTH+5)<S_WIDTH)
				xCurrent+=MOVE_SPEED;
			else if ((snake.getNodes().get(0).getX() - xCurrent)<(2*W_WIDTH/3) && (xCurrent-5)>0)
				xCurrent-=MOVE_SPEED;

			if ((snake.getNodes().get(0).getY() - yCurrent)>(W_HEIGHT/3) && (yCurrent+W_HEIGHT+5)<S_HEIGHT)
				yCurrent+=MOVE_SPEED;
			else if ((snake.getNodes().get(0).getY() - yCurrent)<(2*W_HEIGHT/3) && (yCurrent-5)>0)
				yCurrent-=MOVE_SPEED;*/

			repaint();
			try
			{
				t.sleep(30);
			}
			catch(InterruptedException e)
			{
			}
		}
	}

	public void mouseMoved(MouseEvent me)
	{
		pointer.setFrame(me.getX()+xCurrent, me.getY()+yCurrent, pointer.getDiameter());
	}

	public void mouseDragged(MouseEvent me)
	{
	}

	public void mouseExited(MouseEvent me)
	{
	}

	public void mouseEntered(MouseEvent me)
	{
	}

	public void mouseReleased(MouseEvent me)
	{
		for (Node n : snake.getNodes())
			n.setBoost(false);
	}

	public void mousePressed(MouseEvent me)
	{
		for (Node n : snake.getNodes())
		{
			n.setBoost(true);
		}
	}

	public void mouseClicked(MouseEvent me)
	{
	}

	public static void main(String args[])
	{
		Slither app=new Slither();
	}
}