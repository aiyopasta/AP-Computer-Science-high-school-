//TODO: Try to make streaks go at same speed by incrementing the y values and finding resulting x value.

import java.io.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;

public class MissleCommand extends JPanel implements Runnable, MouseListener
{
	final static int W_WIDTH = 1000;
	final static int W_HEIGHT = 700;
	JFrame frame = new JFrame();
	Thread t;
	Graphics g;
	final int ADD_STREAK_INTERVAL = 250;
	long iterations = ADD_STREAK_INTERVAL-20;
	int health = 50;//max 50
	int power = 20;//max 20
	int score = 0;

	ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	ArrayList<Shield> shields = new ArrayList<Shield>();
	ArrayList<Streak> streaks = new ArrayList<Streak>();
	ArrayList<Building> buildings = new ArrayList<Building>();

	long gameOverTime;

	public MissleCommand()
	{
		frame.add(this);
		frame.addMouseListener(this);
		frame.setResizable(false);
		frame.setSize(W_WIDTH, W_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final int BUILDING_INCREMENT = W_WIDTH/10;

		for (int i=1;i<19;i++)
			buildings.add(new Building(i*BUILDING_INCREMENT, W_HEIGHT-80, Color.CYAN));

		t = new Thread(this);
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;

		g2D.setColor(Color.BLACK);
		g2D.fill(new Rectangle(0,0,W_WIDTH,W_HEIGHT));

		g2D.setStroke(new BasicStroke(2));
		g2D.setColor(Color.RED);

		for (Explosion boom : explosions)
		{
			g2D.fill(boom.getEllipse());
		}

		for (Streak streak : streaks)
		{
			g2D.draw(streak.getLine());
		}

		for (Shield shield : shields)
		{
			g2D.setColor(new Color(255, (int)shield.getCircle().getHeight(), (int)shield.getCircle().getHeight()));
			g2D.fill(shield.getCircle());
		}

		for (Explosion exp : explosions)
		{
			g2D.setColor(Color.RED);
			g2D.fill(exp.getEllipse());
		}

		for (Building build : buildings)
		{
			g2D.setColor(build.getColor());
			g2D.fill(build.getRekt());
			g2D.fill(build.getElps());
		}

		Color c;

		if (health>30)
			c=Color.GREEN;
		else if (health>15)
			c=Color.YELLOW;
		else c=Color.RED;
		g2D.setColor(c);
		g2D.fill(new Rectangle(5, 5, health*6, 30));
		g2D.draw(new Rectangle(5, 5, 50*6, 30));

		g2D.setColor(Color.WHITE);
		g2D.setFont(new Font("Courier", Font.BOLD, 30));
		g2D.drawString("Commander: Incoming " + streaks.size() + " Missles.", 20+50*6,35);

		g2D.drawString("Score: " + score,10,W_HEIGHT-40);

		g2D.setColor(Color.RED);
		g2D.fill(new Rectangle(5, 40, power*6, 30));
		g2D.setColor(Color.RED.darker().darker());
		g2D.draw(new Rectangle(5, 40, 120, 30));

		g2D.setColor(Color.BLACK);
		for (int x=4;x<20;x+=4)
			g2D.fill(new Rectangle(x*6, 41, 5, 28));

		if (health<1)
		{
			g2D.setColor(Color.RED);
			g2D.fill(new Rectangle(0,0,W_WIDTH,W_HEIGHT));

			long currentTime = System.currentTimeMillis();
			long difference = (currentTime-gameOverTime)%10000/10;

			try
			{
				g2D.setColor(new Color(0,0,0,((float)difference)/((float)100)));
			}catch (Exception ex){g2D.setColor(Color.BLACK);}
			g2D.fill(new Rectangle(0,0,W_WIDTH,W_HEIGHT));

			g2D.setColor(Color.RED);
			g2D.setFont(new Font("Courier",Font.BOLD, 100));
			g2D.drawString("GAME OVER...", W_WIDTH/2 - 325,W_HEIGHT/2 - 50);
		}
	}

	public void run()
	{
		long count = 0;

		while(health>=1)
		{
			count++;

			if (power<20 && count%100==0)
				power++;

			ArrayList<Streak> tempRemoved = new ArrayList<Streak>();

			//Streak Creation

			iterations++;
					//System.out.println(iterations);

			if (iterations%ADD_STREAK_INTERVAL==0)
				for (long i=0;i<(long)(Math.random()*(iterations/ADD_STREAK_INTERVAL))+1;i++)
				{
					int randX = (int)(Math.random()*W_WIDTH)+1,
						randY = (int)(Math.random()*(W_HEIGHT-(W_HEIGHT/2)+1))+(W_HEIGHT);
					streaks.add(new Streak(new Point2D.Double(randX, randY), 40));
				}

			//explosions

			try
			{
				for (Explosion boom : explosions)
					if (boom.isValid())
						boom.explodeFrame();
					else explosions.remove(boom);

			} catch (Exception ex){}

			//lines

			try
			{
				for (Streak streak : streaks)
					if (streak.isValid())
						streak.moveFrame();
					else
					{
						streaks.remove(streak);
						tempRemoved.add(streak);
					}
			} catch (Exception ex){}

			//shield interaction

			try
			{
				for (Shield shield : shields)
					for (Streak streak : streaks)
					{
						if (streak.intersects(shield))
						{
							shield.takeDamage();
							score+=10;

							if (!shield.isValid())
							{
								shields.remove(shield);
								explosions.add(new Explosion(shield.getCircle().getX(), shield.getCircle().getY(), 0, 100));
							}

							streak.setValid(false);
						}
					}
			} catch (Exception ex){}

			try
			{
				for (Explosion explosion : explosions)
					for (Shield shield : shields)
					{
						if (explosion.intersects(shield))
						{
							shields.remove(shield);
							explosions.add(new Explosion(shield.getCircle().getX(), shield.getCircle().getY(), 0, 70));
						}
					}
			} catch (Exception ex){}

			try
			{
				for (Streak removed : tempRemoved)
				{
					if (!removed.didHitShield())
					{
						health--;
						System.out.println(removed.didHitShield());
					}
				}
			} catch(Exception ex){}

			if (count>1000000000)
				count = 0;

			/*for (Streak s : streaks)
			{
				for (Building b : buildings)
				{
					if (s.intersects(b))
					{
						streaks.remove(s);
						health--;
					}
				}
			}*/

			repaint();
			try
			{
				t.sleep(15);
			}
			catch(InterruptedException e)
			{
			}
		}

		gameOverTime = System.currentTimeMillis();

		while(health<1)
			repaint();
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
		//explosions.add(new Explosion(me.getX(), me.getY(), 0, 100));
		//streaks.add(new Streak(me.getLocationOnScreen(), 40));
		if (power>=2)
		{
			shields.add(new Shield(me.getX(), me.getY()));
			power-=2;
		}
	}

	public void mouseClicked(MouseEvent me)
	{
	}

	public static void main(String args[])
	{
		MissleCommand app=new MissleCommand();
	}
}