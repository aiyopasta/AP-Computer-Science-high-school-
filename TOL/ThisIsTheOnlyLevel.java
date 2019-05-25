import java.awt.*;

import javax.swing.*;

import java.io.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;

//import sun.audio.*;

import java.util.ArrayList; ///Users/adityaabhyankar/Desktop/Google Drive/School Files (10th Grade)/AP CS/workspace/TOL

public class ThisIsTheOnlyLevel extends JPanel implements Runnable, KeyListener, MouseListener //TODO: Make dead fall, allow creation of multiple dead, create more levels, create celebration screen after level is defeated
{
	//Graphics
	JFrame frame = new JFrame();
	Thread t;

	//Controls

	boolean up;
	boolean down;
	boolean left;
	boolean right;

	int facing = 1; //1 = right, 2 = left

	//Game Management

	boolean gameOver = false;
	int deaths = 0;
	boolean dead;
	long becameDead;
	long timeDead;
	int deadX, deadY;
	int deadFacing;
	static int stageNumber = 1;
	ArrayList<String> hints = new ArrayList<String>();
	ArrayList<String> messages = new ArrayList<String>();
	String message;

	//Falling + Bouncing

	//int jumpVertical = 0;
	int fallingVertical = 0;
	int bounce = 0;
	boolean falling = false;
	boolean bouncing = false;

	//Jumping

	int jumpVertical = 0;
	final int JUMP_LIMIT = 200;

	//Game Components

	Mover m;
	Stage stage;
	Pipe entrance, exit;
	Door door;
	Button button;
	ArrayList<SpikeTrail> spks = new ArrayList<SpikeTrail>();
	Image elephant,elephantRight, deadElephant, deadElephantRight;

	//Timer

	long startTime;
	long stopTime;
	boolean running;

	//Vars For Particular Levels

	boolean theRealHasBeenPressed; //LEVEL SIX
	long milliDead; //LEVEL SIX
	boolean eskiv = false;

	//Celebration
	boolean celebrate;
	boolean cele, celeWait, celeAgain;
	int celeX, celeY;
	long waitStart;

	public ThisIsTheOnlyLevel() //TODO: Make parabolic motion + add Bounce effect
	{
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.add(this);
		frame.setSize(1500,900);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m = new Mover(105, 40);
		button = new Button();
		door = new Door();

		hints.add("Arrow Control");
		hints.add("Lortnoc Worra");
		hints.add("Peek-a-boo");
		hints.add("Do You Remember?");
		hints.add("Crucial Timing");
		hints.add("Klique");
		hints.add("Strive For Five");
		hints.add("Score 40");

		messages.add("You think you are done? Haha think again!");
		messages.add("But the level isn't over yet!");
		messages.add("About time you beat that stage.");
		messages.add("The end is in another castle.");
		messages.add("Still not good enough.");
		messages.add("Level Compete? No");
		messages.add("Have fun with the next one!");
		messages.add("How long will it last?");
		messages.add("So close but yet so far.");
		messages.add("Not quite there yet.");
		messages.add("I can feel your anger boiling.");
		//messages.add("Like the catchy music?");

		startTime = System.currentTimeMillis();

		elephant = new ImageIcon(this.getClass().getResource("elephant.png")).getImage();
		elephantRight = new ImageIcon(this.getClass().getResource("elephantRight.png")).getImage();
		deadElephant = new ImageIcon(this.getClass().getResource("deadElephant.png")).getImage(); //deadElephant
		deadElephantRight = new ImageIcon(this.getClass().getResource("deadElephantRight.png")).getImage(); //deadElephantRight

		MediaTracker t1 = new MediaTracker(this);

		t1.addImage(elephant, 0);
		t1.addImage(elephantRight, 0);
		try
		{
			t1.waitForID(0);
		} catch (InterruptedException e)
		{}

		t = new Thread(this);
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		stage = new Stage(g);
		super.paintComponent(g);
		g.setColor(new Color(0, 128, 255));
		g.fillRect(0, 0, 1500, 800);

		if (!gameOver)
		{

			//Bottom Part

			g.setColor(new Color(0, 200, 100));
			g.fillRect(0,  770,  1500,  150);
			g.setColor(new Color(0, 150, 50));
			g.fillRect(0,  780,  1500,  60);
			Font f = new Font("Gill Sans", Font.PLAIN, 85);
			g.setFont(f);
			g.setColor(new Color(200, 200, 200));
			g.drawString("Level 1", 20, 840);
			f = new Font("Gill Sans", Font.PLAIN, 30);
			g.setFont(f);
			g.drawString(hints.get(stageNumber-1), 315, 805);
			f = new Font("Gill Sans", Font.PLAIN, 40);
			g.setFont(f);
			g.drawString("STAGE " + stageNumber, 315, 840);
			f = new Font("Gill Sans", Font.PLAIN, 25);
			g.setFont(f);
			g.setColor(Color.RED);
			g.drawString("Deaths: " + deaths, 1160, 860);


			//Timer
			f = new Font("Gill Sans", Font.PLAIN, 86);
			g.setFont(f);

			long totalTime = (System.currentTimeMillis() - startTime);
			int milli = (int)totalTime % 1000;
			milli = (int)(((double)milli / 1000.0) * 100);
			int secs = (((int)totalTime / 1000) % 60);
			int minutes = ((int)totalTime / 1000 / 60) % 60;
			String seconds;
			if (secs<10)
				seconds = "0" + secs;
			else seconds = "" + secs;

			String milliseconds;
			if (milli < 10)
				milliseconds = "0" + milli;
			else milliseconds = "" + milli;

			g.setColor(new Color(21, 255, 255));
			g.drawString("" + minutes + ":" + seconds + "." + milliseconds, 1150, 840);

			if (!(stageNumber == 4))
				stage.draw();
			else
			{
				g.setColor(Color.BLACK);
				g.fillRect(40, 40, 1420, 720);
			}

			//Fill Spikes

			spks.clear();

			spks.add(new SpikeTrail("t", 700, 15, g));
			spks.add(new SpikeTrail("t", 200, 5, g));
			spks.add(new SpikeTrail("t", 450, 6, g));
			spks.add(new SpikeTrail("l", 450, 16, g));
			spks.add(new SpikeTrail("b", 400, 8, g));
			spks.add(new SpikeTrail("b", 1170, 4, g));
			spks.add(new SpikeTrail("r", 300, 15, g));
			spks.add(new SpikeTrail("r", 100, 5, g));

			for (SpikeTrail trl : spks)
				trl.draw();

			//Fill Button
			button.createBlock(g);

			if (button.isPressed(m))
				g.setColor(Color.RED);
			else g.setColor(new Color(204,0,0));

			g.fillOval((int)button.getElps().getX(), (int)button.getElps().getY(), (int)button.getElps().getWidth(), (int)button.getElps().getHeight());

			g.setColor(new Color(150, 0, 0));
			button.getBlock().draw();

			//Fill Mover
			g.setColor(Color.RED);

			if(facing == 1)
				g.drawImage(elephantRight,m.getX(), m.getY(),this);
			else if(facing == 2)
				g.drawImage(elephant,m.getX(), m.getY(),this);

			if (stageNumber == 7)
			{
				f = new Font("Gill Sans", Font.PLAIN, 20);
				g.setFont(f);
				g.setColor(Color.RED);
				g.drawString("" + (((System.currentTimeMillis() - startTime) / 1000)) % 5, m.getX() + 19, m.getY());
			}

			//Fill Door
			g.setColor(new Color(0, 153, 0));
			g.fillRect(1310, 665, 25, door.getHeight());

			//Fill Pipes
			entrance = new Pipe(g, 1);
			exit = new Pipe(g, 2);
			entrance.draw();
			exit.draw();

			//Fill Dead

			if (dead && (System.currentTimeMillis() - becameDead) / 1000 < 4)
				if (deadFacing == 1)
					g.drawImage(deadElephantRight,deadX, deadY,this);
						else if (deadFacing == 2)
							g.drawImage(deadElephant,deadX, deadY,this);
			else if (dead && (System.currentTimeMillis() - becameDead) / 1000 > 4)
			{
				dead = false;
			}

			//Celebration

			if (celebrate)
			{
				if (cele)
				{
					g.setColor(new Color(0, 153, 76));
					g.fillRect(celeX, celeY, 1500, 200);

					g.setColor(new Color(0, 255, 128));
					g.fillRect(celeX + 10, celeY + 10, 1500-20, 200-20);

					f = new Font("Gill Sans", Font.PLAIN, 60);
					g.setFont(f);
					g.setColor(Color.WHITE);
					g.drawString("STAGE COMPLETE", celeX + 500, celeY + 80);
					f = new Font("Gill Sans", Font.PLAIN, 35);
					g.setFont(f);
					g.drawString(message, celeX + 500, celeY + 160);

					celeY+=10;
					if (celeY >=300)
					{
						cele = false;
						celeWait = true;
						waitStart = System.currentTimeMillis();
					}
				}
				else if (celeWait)
				{
					if ((System.currentTimeMillis() - waitStart) / 1000 < 2)
					{
						g.setColor(new Color(0, 153, 76));
						g.fillRect(celeX, celeY, 1500, 200);
						g.setColor(new Color(0, 255, 128));
						g.fillRect(celeX + 10, celeY + 10, 1500-20, 200-20);
						f = new Font("Gill Sans", Font.PLAIN, 60);
						g.setFont(f);
						g.setColor(Color.WHITE);
						g.drawString("STAGE COMPLETE", celeX + 500, celeY + 80);
						f = new Font("Gill Sans", Font.PLAIN, 35);
						g.setFont(f);
						g.drawString(message, celeX + 500, celeY + 160);
					}
					else if ((System.currentTimeMillis() - waitStart) / 1000 >= 1)
					{
						celeWait = false;
						celeAgain = true;
					}
				}
				else if (celeAgain)
				{
					g.setColor(new Color(0, 153, 76));
					g.fillRect(celeX, celeY, 1500, 200);
					g.setColor(new Color(0, 255, 128));
					g.fillRect(celeX + 10, celeY + 10, 1500-20, 200-20);
					f = new Font("Gill Sans", Font.PLAIN, 60);
					g.setFont(f);
					g.setColor(Color.WHITE);
					g.drawString("STAGE COMPLETE", celeX + 500, celeY + 80);
					f = new Font("Gill Sans", Font.PLAIN, 35);
					g.setFont(f);
					g.drawString(message, celeX + 500, celeY + 160);

					celeY+=10;
					if (celeY >=760)
					{
						celebrate = false;
						celeAgain = false;
					}
				}
			}

		}
		else
		{
			//Game Over

			if (gameOver)
			{
				g.setColor(Color.BLACK);
				g.fillRect(40, 40, 1420, 720);
				Font f = new Font("Gill Sans", Font.PLAIN, 200);
				g.setFont(f);
				g.setColor(Color.WHITE);
				g.drawString("WELL DONE", 100, 350);
				f = new Font("Gill Sans", Font.PLAIN, 80);
				g.setFont(f);
				g.drawString("You Beat The Only Level In The Game", 65, 440);
				g.drawImage(elephantRight, 700, 600 ,this);
			}
		}
	}

	public void stopTime()
	{
		stopTime = System.currentTimeMillis();
		running = false;
	}

	public void mouseClicked(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{

	}

	public void mousePressed(MouseEvent e)
	{
		if (stageNumber == 6)
		{
			Rectangle temp = new Rectangle(780, 210, 135, 60);

			if (temp.contains(e.getPoint()))
			{
				theRealHasBeenPressed = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{

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
	}

	public void keyReleased(KeyEvent ke)
	{
		if(ke.getKeyCode()==38)  //up
			up = false;
		if(ke.getKeyCode()==39)  //right
			right = false;
		if(ke.getKeyCode()==37)  //left
			left = false;
	}

	private void celebration()
	{
		celebrate = true;
		cele = true;
		celeX = 0;
		celeY = -200;
	}

	private void levelOne()
	{
		try
		{
			if (jumpVertical<200)
			{
				int yInitial = m.getY()+jumpVertical;

				Mover temp = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(temp))
				{
					m.setFrame((int)temp.getX(), (int)temp.getY());
					jumpVertical++;

				}
				else if (stage.intersectsWith(temp))
					jumpVertical = 200;
			}

			if (up)
			{
				Mover temp = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(temp))
				{
					jumpVertical = 0;
				}
			}

			if (right)
			{
				facing = 1;

				Mover temp = new Mover(m.getX()+1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m) && !door.getRect().intersects(m.getRect()))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			if (left)
			{
				facing = 2;

				Mover temp = new Mover(m.getX()-1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			//Checks if it should fall down
			Mover temp = new Mover(m.getX(), m.getY()+1);

			if(!stage.supportsMover(temp))
			{
				m.setFrame(temp.getX(), temp.getY());
				fallingVertical++;
			}
			else if (stage.supportsMover(temp) && falling)
				falling = false;

			//Checks if inside pipe

			if (exit!=null && exit.containsMover(m))
			{
				m.setFrame(105, 40);

				for (int x=0;x<95;x++)
					door.setFrameClose();

				button.unPress();
				stageNumber++;
				celebration();
				message = messages.get((int)(Math.random()*messages.size()));
			}

			//Checks if hit spikes

			if (spks!=null)
			{
				try
				{
					for (SpikeTrail trl : spks)
						if(trl.intersectsWith(m))
						{
							deadX = m.getX();
							deadY = m.getY();
							deadFacing = facing;
							m.setFrame(105, 40);
							button.unPress();
							deaths++;
							dead = true;
							becameDead = System.currentTimeMillis();
						}

				} catch(Exception e)
				{
				}
			}

			if (stageNumber == 7 && (((System.currentTimeMillis() - startTime) / 1000) % 5 == 0))
			{
				milliDead = System.currentTimeMillis();
				if (!entrance.containsMover(m))
					deaths++;
				deadX = m.getX();
				deadY = m.getY();
				deadFacing = facing;
				m.setFrame(105, 40);
				button.unPress();
				dead = true;
				becameDead = System.currentTimeMillis();
			}

			//Bounce

			if (!falling)
			{
				Mover other = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(other))
				{
					bounce = fallingVertical/15;
					bouncing = true;
				}
			}

			if (bounce>0)
			{
				Mover other = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(other))
				{
					m.setFrame((int)other.getX(), (int)other.getY());
					bounce--;

					//System.out.println(stage.intersectsWith(temp));

				}
				else if (stage.intersectsWith(other))
					bounce = 0;
			}
			else if (bouncing)
			{
				fallingVertical = 0;
				bouncing = false;
			}

			shouldOpenDoor();

			//Checks if dead should fall


			deadY++;


		} catch (NullPointerException e)
		{
		}
	}

	private void levelTwo()
	{
		try
		{
			if (jumpVertical<200)
			{
				int yInitial = m.getY()+jumpVertical;

				Mover temp = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(temp))
				{
					m.setFrame((int)temp.getX(), (int)temp.getY());
					jumpVertical++;

				}
				else if (stage.intersectsWith(temp))
					jumpVertical = 200;
			}

			if (up)
			{
				Mover temp = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(temp))
				{
					jumpVertical = 0;
				}
			}

			if (left)
			{
				facing = 1;

				Mover temp = new Mover(m.getX()+1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m) && !door.getRect().intersects(m.getRect()))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			if (right)
			{
				facing = 2;

				Mover temp = new Mover(m.getX()-1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			//Checks if it should fall down
			Mover temp = new Mover(m.getX(), m.getY()+1);

			if(!stage.supportsMover(temp))
			{
				m.setFrame(temp.getX(), temp.getY());
				fallingVertical++;
			}
			else if (stage.supportsMover(temp) && falling)
				falling = false;

			//Checks if inside pipe

			if (exit!=null && exit.containsMover(m))
			{
				m.setFrame(105, 40);

				for (int x=0;x<95;x++)
					door.setFrameClose();

				button.unPress();
				stageNumber++;
				celebration();
				message = messages.get((int)(Math.random()*messages.size()));
			}

			//Checks if hit spikes

			if (spks!=null)
			{
				try
				{
					for (SpikeTrail trl : spks)
						if(trl.intersectsWith(m))
						{
							deadX = m.getX();
							deadY = m.getY();
							deadFacing = facing;
							m.setFrame(105, 40);
							button.unPress();
							deaths++;
							dead = true;
							becameDead = System.currentTimeMillis();
						}
				} catch(Exception e)
				{
				}
			}

			//Bounce

			if (!falling)
			{
				Mover other = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(other))
				{
					bounce = fallingVertical/15;
					bouncing = true;
				}
			}

			if (bounce>0)
			{
				Mover other = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(other))
				{
					m.setFrame((int)other.getX(), (int)other.getY());
					bounce--;

					//System.out.println(stage.intersectsWith(temp));

				}
				else if (stage.intersectsWith(other))
					bounce = 0;
			}
			else if (bouncing)
			{
				fallingVertical = 0;
				bouncing = false;
			}

			shouldOpenDoor();

			deadY++;

		} catch (NullPointerException e)
		{
		}
	}

	private void levelThree()
	{
		try
		{
			if (jumpVertical<200)
			{
				int yInitial = m.getY()+jumpVertical;

				Mover temp = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(temp))
				{
					m.setFrame((int)temp.getX(), (int)temp.getY());
					jumpVertical++;

				}
				else if (stage.intersectsWith(temp))
					jumpVertical = 200;
			}

			if (up)
			{
				Mover temp = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(temp))
				{
					jumpVertical = 0;
				}
			}

			if (right)
			{
				facing = 1;

				Mover temp = new Mover(m.getX()+1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m) && !door.getRect().intersects(m.getRect()))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			if (left)
			{
				facing = 2;

				Mover temp = new Mover(m.getX()-1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			//Checks if it should fall down
			Mover temp = new Mover(m.getX(), m.getY()+1);

			if(!stage.supportsMover(temp))
			{
				m.setFrame(temp.getX(), temp.getY());
				fallingVertical++;
			}
			else if (stage.supportsMover(temp) && falling)
				falling = false;

			//Checks if inside pipe

			if (exit!=null && exit.containsMover(m))
			{
				m.setFrame(105, 40);

				for (int x=0;x<95;x++)
					door.setFrameClose();

				button.unPress();
				stageNumber++;
				celebration();
				message = messages.get((int)(Math.random()*messages.size()));
			}

			//Checks if hit spikes

			if (spks!=null)
			{
				try
				{
					for (SpikeTrail trl : spks)
						if(trl.intersectsWith(m))
						{
							deadX = m.getX();
							deadY = m.getY();
							deadFacing = facing;
							m.setFrame(105, 40);
							button.unPress();
							deaths++;
							dead = true;
							becameDead = System.currentTimeMillis();
						}
				} catch(Exception e)
				{
				}
			}

			//Bounce

			if (!falling)
			{
				Mover other = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(other))
				{
					bounce = fallingVertical/15;
					bouncing = true;
				}
			}

			if (bounce>0)
			{
				Mover other = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(other))
				{
					m.setFrame((int)other.getX(), (int)other.getY());
					bounce--;

					//System.out.println(stage.intersectsWith(temp));

				}
				else if (stage.intersectsWith(other))
					bounce = 0;
			}
			else if (bouncing)
			{
				fallingVertical = 0;
				bouncing = false;
			}

			shouldOpenDoor();

			deadY++;

		} catch (NullPointerException e)
		{
		}
	}

	private void levelFive()
	{
		try
		{
			if (jumpVertical<200)
			{
				int yInitial = m.getY()+jumpVertical;

				Mover temp = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(temp))
				{
					m.setFrame((int)temp.getX(), (int)temp.getY());
					jumpVertical++;

				}
				else if (stage.intersectsWith(temp))
					jumpVertical = 200;
			}

			if (((System.currentTimeMillis() - startTime) / 1000) % 3 == 0)
			{
				if (left)
				{
					facing = 1;

					Mover temp = new Mover(m.getX()+1, m.getY());

					if (!stage.intersectsWith(temp) && !entrance.containsMover(m) && !door.getRect().intersects(m.getRect()))
						m.setFrame((int)temp.getX(), (int)temp.getY());
				}

				if (right)
				{
					facing = 2;

					Mover temp = new Mover(m.getX()-1, m.getY());

					if (!stage.intersectsWith(temp) && !entrance.containsMover(m))
						m.setFrame((int)temp.getX(), (int)temp.getY());
				}
			}
			else
			{
				if (right)
				{
					facing = 1;

					Mover temp = new Mover(m.getX()+1, m.getY());

					if (!stage.intersectsWith(temp) && !entrance.containsMover(m) && !door.getRect().intersects(m.getRect()))
						m.setFrame((int)temp.getX(), (int)temp.getY());
				}

				if (left)
				{
					facing = 2;

					Mover temp = new Mover(m.getX()-1, m.getY());

					if (!stage.intersectsWith(temp) && !entrance.containsMover(m))
						m.setFrame((int)temp.getX(), (int)temp.getY());
				}
			}

			if (up)
			{
				Mover temp = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(temp))
				{
					jumpVertical = 0;
				}
			}

			//Checks if it should fall down
			Mover temp = new Mover(m.getX(), m.getY()+1);

			if(!stage.supportsMover(temp))
			{
				m.setFrame(temp.getX(), temp.getY());
				fallingVertical++;
			}
			else if (stage.supportsMover(temp) && falling)
				falling = false;

			//Checks if inside pipe

			if (exit!=null && exit.containsMover(m))
			{
				m.setFrame(105, 40);

				for (int x=0;x<95;x++)
					door.setFrameClose();

				button.unPress();
				stageNumber++;
				celebration();
				message = messages.get((int)(Math.random()*messages.size()));
			}

			//Checks if hit spikes

			if (spks!=null)
			{
				try
				{
					for (SpikeTrail trl : spks)
						if(trl.intersectsWith(m))
						{
							deadX = m.getX();
							deadY = m.getY();
							deadFacing = facing;
							m.setFrame(105, 40);
							button.unPress();
							deaths++;
							dead = true;
							becameDead = System.currentTimeMillis();
						}
				} catch(Exception e)
				{
				}

				deadY++;

			}

			//Bounce

			if (!falling)
			{
				Mover other = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(other))
				{
					bounce = fallingVertical/15;
					bouncing = true;
				}
			}

			if (bounce>0)
			{
				Mover other = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(other))
				{
					m.setFrame((int)other.getX(), (int)other.getY());
					bounce--;

					//System.out.println(stage.intersectsWith(temp));

				}
				else if (stage.intersectsWith(other))
					bounce = 0;
			}
			else if (bouncing)
			{
				fallingVertical = 0;
				bouncing = false;
			}

			shouldOpenDoor();

			deadY++;

		} catch (NullPointerException e)
		{
		}
	}

	private void levelSix()
	{
		try
		{
			if (jumpVertical<200)
			{
				int yInitial = m.getY()+jumpVertical;

				Mover temp = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(temp))
				{
					m.setFrame((int)temp.getX(), (int)temp.getY());
					jumpVertical++;

				}
				else if (stage.intersectsWith(temp))
					jumpVertical = 200;
			}

			if (up)
			{
				Mover temp = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(temp))
				{
					jumpVertical = 0;
				}
			}

			if (right)
			{
				facing = 1;

				Mover temp = new Mover(m.getX()+1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m) && !door.getRect().intersects(m.getRect()))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			if (left)
			{
				facing = 2;

				Mover temp = new Mover(m.getX()-1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			//Checks if it should fall down
			Mover temp = new Mover(m.getX(), m.getY()+1);

			if(!stage.supportsMover(temp))
			{
				m.setFrame(temp.getX(), temp.getY());
				fallingVertical++;
			}
			else if (stage.supportsMover(temp) && falling)
				falling = false;

			//Checks if inside pipe

			if (exit!=null && exit.containsMover(m))
			{
				m.setFrame(105, 40);

				for (int x=0;x<95;x++)
					door.setFrameClose();

				button.unPress();
				stageNumber++;
				celebration();
				message = messages.get((int)(Math.random()*messages.size()));
			}

			//Checks if hit spikes

			if (spks!=null)
			{
				try
				{
					for (SpikeTrail trl : spks)
						if(trl.intersectsWith(m))
						{
							deadX = m.getX();
							deadY = m.getY();
							deadFacing = facing;
							m.setFrame(105, 40);
							button.unPress();
							deaths++;
							dead = true;
							becameDead = System.currentTimeMillis();
						}
				} catch(Exception e)
				{
				}
			}

			//Bounce

			if (!falling)
			{
				Mover other = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(other))
				{
					bounce = fallingVertical/15;
					bouncing = true;
				}
			}

			if (bounce>0)
			{
				Mover other = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(other))
				{
					m.setFrame((int)other.getX(), (int)other.getY());
					bounce--;

					//System.out.println(stage.intersectsWith(temp));

				}
				else if (stage.intersectsWith(other))
					bounce = 0;
			}
			else if (bouncing)
			{
				fallingVertical = 0;
				bouncing = false;
			}

			shouldOpenDoor();

			deadY++;

		} catch (NullPointerException e)
		{
		}
	}

	private void levelEight()
	{
		try
		{
			if (jumpVertical<200)
			{
				int yInitial = m.getY()+jumpVertical;

				Mover temp = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(temp))
				{
					m.setFrame((int)temp.getX(), (int)temp.getY());
					jumpVertical++;

				}
				else if (stage.intersectsWith(temp))
					jumpVertical = 200;
			}

			if (up)
			{
				Mover temp = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(temp))
				{
					jumpVertical = 0;
				}
			}

			if (right)
			{
				facing = 1;

				Mover temp = new Mover(m.getX()+1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m) && !door.getRect().intersects(m.getRect()))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			if (left)
			{
				facing = 2;

				Mover temp = new Mover(m.getX()-1, m.getY());

				if (!stage.intersectsWith(temp) && !entrance.containsMover(m))
					m.setFrame((int)temp.getX(), (int)temp.getY());
			}

			//Checks If Button Has Been Pressed

			if (button.hasBeenPressed() && !eskiv)
			{
				eskiv = true;
				String[] arguments = new String[] {"blah"};
				Eskiv.main(arguments);
				Eskiv.visible = true;
				right = false;
				left = false;
			}

			//Checks if it should fall down
			Mover temp = new Mover(m.getX(), m.getY()+1);

			if(!stage.supportsMover(temp))
			{
				m.setFrame(temp.getX(), temp.getY());
				fallingVertical++;
			}
			else if (stage.supportsMover(temp) && falling)
				falling = false;

			//Checks if inside pipe

			if (exit!=null && exit.containsMover(m))
			{
				m.setFrame(105, 40);

				for (int x=0;x<95;x++)
					door.setFrameClose();

				button.unPress();
				stageNumber++;
				celebration();
				message = messages.get((int)(Math.random()*messages.size()));
			}

			//Checks if hit spikes

			if (spks!=null)
			{
				try
				{
					for (SpikeTrail trl : spks)
						if(trl.intersectsWith(m))
						{
							//eskiv = false;
							Eskiv.score = 0;
							deadX = m.getX();
							deadY = m.getY();
							deadFacing = facing;
							m.setFrame(105, 40);
							button.unPress();
							deaths++;
							dead = true;
							becameDead = System.currentTimeMillis();
							Eskiv.visible = false;
						}
				} catch(Exception e)
				{
				}
			}

			//Bounce

			if (!falling)
			{
				Mover other = new Mover(m.getX(), m.getY()+2);

				if (stage.supportsMover(other))
				{
					bounce = fallingVertical/15;
					bouncing = true;
				}
			}

			if (bounce>0)
			{
				Mover other = new Mover((int)m.getX(), (int)m.getY()-2);
				if (!stage.intersectsWith(other))
				{
					m.setFrame((int)other.getX(), (int)other.getY());
					bounce--;

					//System.out.println(stage.intersectsWith(temp));

				}
				else if (stage.intersectsWith(other))
					bounce = 0;
			}
			else if (bouncing)
			{
				fallingVertical = 0;
				bouncing = false;
			}

			shouldOpenDoor();

			deadY++;

		} catch (NullPointerException e)
		{
		}
	}

	private void shouldOpenDoor()
	{
		if (stageNumber == 1 || stageNumber == 2 || stageNumber == 4 || stageNumber == 5)
		{
			if (button.hasBeenPressed())
				door.setFrameOpen();
			else door.setFrameClose();
		}
		else if (stageNumber == 3)
		{
			if (facing == 2)
				for (int x=0;x<3;x++)
					door.setFrameOpen();
			else door.setFrameClose();
		}
		else if (stageNumber == 6)
			if (theRealHasBeenPressed)
				door.setFrameOpen();
			else door.setFrameClose();
		else if (stageNumber == 7)
			door.setFrameOpen();

		if (stageNumber == 8)
		{
			if (Eskiv.score >= 40)
			{
				door.setFrameOpen();
				Eskiv.visible = false;
			}
			else door.setFrameClose();
		}
	}

	public void run()
	{

		//public Level(Mover m, Stage stage, int jumpVertical, Pipe entrance, Door door, int fallingVertical, boolean falling, ArrayList<SpikeTrail> spks, Button button, int bounce, boolean bouncing)

		while(!gameOver)
		{
			if(stageNumber == 1)
				levelOne();

			if (stageNumber == 2)
				levelTwo();

			if (stageNumber == 3)
				levelThree();

			if (stageNumber == 4)
				levelOne();

			if (stageNumber == 5)
				levelFive();

			if (stageNumber == 6)
				levelSix();

			if (stageNumber == 7)
				levelOne();

			if (stageNumber == 8)
				levelEight();

			if (stageNumber == 9)
			{
				try
				{
				gameOver = true;
				String htmlFilePath = "/Users/10010897/Desktop/RealTOLGame.html"; // path to your new file
				File htmlFile = new File(htmlFilePath);
				Desktop.getDesktop().browse(htmlFile.toURI());
				Desktop.getDesktop().open(htmlFile);
				} catch(Exception e)
				{}
			}

			repaint();
			try
			{
				t.sleep(2);
			}
			catch(InterruptedException e)
			{
			}
		}
	}

	public static void main(String args[]) throws Exception
	{
//		try
//		{
//			String fileName = "/Users/adityaabhyankar/Desktop/TOL/TOL.wav";
//			InputStream in = new FileInputStream(fileName);
//			AudioStream audioStream = new AudioStream(in);
//			AudioPlayer.player.start(audioStream);
//		}
//		catch (Exception e)
//		{
//			System.out.println("IDK WHY THE SOUND ISN'T WORKING...");
//		}

		new ThisIsTheOnlyLevel();
	}
}