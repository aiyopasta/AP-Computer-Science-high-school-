import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Eskiv extends JPanel implements Runnable, KeyListener
{
	JFrame frame = new JFrame();
	Thread t;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	boolean hitSquare;
	boolean hitObst;

	boolean gameOver = false;
	boolean newGame = false;

	ArrayList<Occupant> obs = new ArrayList<Occupant>();
	Occupant sqr = new Occupant((int)(Math.random()*500)+170, (int)(Math.random()*540)+30, 20,20, false);

	int score = 0;

	Occupant ball;

	public Eskiv()
	{
		obs.add(new Occupant((int)(Math.random()*498)+170,(int)(Math.random()*538)+30,20,20, (int)(Math.random()*4)+1));
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(700,620);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		t = new Thread(this);
		ball = new Occupant(200,200,20,20);
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(130,130,130));
		g.fillRect(0,0,700,600);

		g.setColor(new Color(180,180,180));
		g.fillRect(170,30,500,540);

		Font f = new Font("Courier New",Font.BOLD,55);
		g.setFont(f);
		g.setColor(new Color(250,250,250));
		g.drawString("Eskiv",1,100);

		f = new Font("Courier New",Font.BOLD,40);
		g.setFont(f);
		g.drawString("Score: ",1,200);
		g.drawString("" + score,20,250);

		g.setColor(new Color(180,180,180));
		g.fillRoundRect(25,300, 80, 55, 15, 15);
		g.setColor(Color.WHITE);
		g.drawRoundRect(25, 300, 80, 55, 15, 15);
		g.drawString("NEW", 30, 340);

		f = new Font("Courier New", Font.PLAIN, 13);
		g.setFont(f);
		g.drawString("Press 'N'", 30, 352);

		f = new Font("Courier New",Font.BOLD,30);
		g.setFont(f);
		g.drawString("by aa.",20,500);

		//Fill Ball

		g.setColor(Color.BLACK);
		g.fillOval(ball.getX(),ball.getY(), 20,20);

		//Fill Obstacles

		g.setColor(Color.BLUE);
		for (Occupant oc : obs)
			g.fillOval(oc.getX(), oc.getY(), 10, 10);

		//Fill Squares
		g.setColor(new Color(130,130,130));
		g.fillRect(sqr.getX(), sqr.getY(), 20, 20);
		g.setColor(Color.BLACK);
		g.drawRect(sqr.getX(), sqr.getY(), 20, 20);

		//Game over sign
		if (gameOver)
		{
			g.setColor(Color.BLACK);
			f = new Font("Arial", Font.BOLD, 60);
			g.setFont(f);
			g.drawString("Game Over", 210, 180);
		}
	}

	public void keyTyped(KeyEvent ke)
	{

	}

	public void keyPressed(KeyEvent ke)
	{
		if(ke.getKeyCode()==38)  //up
			up = true;
		if(ke.getKeyCode()==39)  //right
			right = true;
		if(ke.getKeyCode()==37)  //left
			left = true;
		if(ke.getKeyCode()==40)  //down
			down = true;
		if(ke.getKeyCode()==78) //new Game
			newGame = true;
	}

	public void keyReleased(KeyEvent ke)
	{
		if(ke.getKeyCode()==38)  //up
			up = false;
		if(ke.getKeyCode()==39)  //right
			right = false;
		if(ke.getKeyCode()==37)  //left
			left = false;
		if(ke.getKeyCode()==40)  //down
				down = false;
	}

	public void run()
	{
			while(true)
			{
				if (!gameOver)
				{

				if(up)
				{
					Rectangle boundary = new Rectangle(170,30,500,540);
					Rectangle temp = new Rectangle(ball.getX(),ball.getY()-1,20,20);
					if (boundary.contains(temp))
					{
						ball.setFrame(ball.getX(),ball.getY()-1,20,20);
					}
				}

				if(down)
				{
					Rectangle boundary = new Rectangle(170,30,500,540);
					Rectangle temp = new Rectangle(ball.getX(),ball.getY()+1,20,20);
					if (boundary.contains(temp))
					{
						ball.setFrame(ball.getX(),ball.getY()+1,20,20);
					}
				}

				if(right)
				{
					Rectangle boundary = new Rectangle(170,30,500,540);
					Rectangle temp = new Rectangle(ball.getX()+1,ball.getY(),20,20);
					if (boundary.contains(temp))
					{
						ball.setFrame(ball.getX()+1,ball.getY(),20,20);
					}
				}

				if(left)
				{
					Rectangle boundary = new Rectangle(170,30,500,540);
					Rectangle temp = new Rectangle(ball.getX()-1,ball.getY(),20,20);
					if (boundary.contains(temp))
					{
						ball.setFrame(ball.getX()-1,ball.getY(),20,20);
					}
				}

				//MOVE OBSTACLES

				for (Occupant oc : obs)
				{
					Rectangle boundary = new Rectangle(170, 30, 500, 540);
					Rectangle temp;
					if (oc.getDirection() == 1)
					{
						temp = new Rectangle(oc.getX()+1, oc.getY(), 10,10);
						if (boundary.contains(temp))
							oc.setFrame(oc.getX()+1, oc.getY(), 10,10);
						else oc.setFrame(oc.getX()-1, oc.getY(), 10,10, 2);
					}
					if (oc.getDirection() == 2)
					{
						temp = new Rectangle(oc.getX()-1, oc.getY(), 10,10);
						if (boundary.contains(temp))
							oc.setFrame(oc.getX()-1, oc.getY(), 10,10);
						else oc.setFrame(oc.getX()+1, oc.getY(), 10,10, 1);
					}
					if (oc.getDirection() == 3)
					{
						temp = new Rectangle(oc.getX(), oc.getY()-1, 10,10);
						if (boundary.contains(temp))
							oc.setFrame(oc.getX(), oc.getY()-1, 10,10);
						else oc.setFrame(oc.getX(), oc.getY()+1, 10,10, 4);
					}
					if (oc.getDirection() == 4)
					{
						temp = new Rectangle(oc.getX(), oc.getY()+1, 10,10);
						if (boundary.contains(temp))
							oc.setFrame(oc.getX(), oc.getY()+1, 10,10);
						else oc.setFrame(oc.getX(), oc.getY()-1, 10,10, 3);
					}
				}

				//Check if hit square + does its stuff

				if (ball.getElps().intersects(sqr.getRect()))
				{
					sqr.setFrame((int)(Math.random()*480)+170,(int)(Math.random()*520)+30,20,20, true);
					obs.add(new Occupant((int)(Math.random()*490)+170,(int)(Math.random()*530)+30,20,20, (int)(Math.random()*4)+1));
					score+=5;
				}

				//Checks if hit obstacle

				for (Occupant oc : obs)
				{
					if (ball.getElps().intersects(new Rectangle(oc.getX(),oc.getY(),10,10)))
					{
						gameOver = true;
					}
				}

				}

				//Checks if new Game

				if(newGame)
				{
					score = 0;
					obs = new ArrayList<Occupant>();
					obs.add(new Occupant((int)(Math.random()*498)+170,(int)(Math.random()*538)+30,20,20, (int)(Math.random()*4)+1));
					gameOver = false;
					newGame = false;
				}

				repaint();
				try
				{
					t.sleep(3);
				}
				catch(InterruptedException e)
				{
				}
			}
	}

	public static void main(String args[])
	{
		Eskiv app=new Eskiv();
	}
}