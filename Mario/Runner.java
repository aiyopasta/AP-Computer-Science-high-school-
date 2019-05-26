import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import java.util.ArrayList;

public class Runner extends JPanel implements Runnable, KeyListener
{
	Thread t;
	JFrame frame;
	static Graphics g;
	static Grid grid = new Grid();
	public static final int CELLSIZE = 20;
	public static final int WWIDTH = 1370/CELLSIZE*CELLSIZE;
	public static final int WHEIGHT = 725/CELLSIZE*CELLSIZE;
	public static final int SHEIGHT = 1000/CELLSIZE*CELLSIZE;
	public static final int SWIDTH = 4000/CELLSIZE*CELLSIZE;
	
	boolean up = false, down = false, right = false, left = false, shoot = false;
	boolean viewUp = false, viewDown = false, viewRight = false, viewLeft = false;
	
	static int currentX = 0, currentY = SHEIGHT-WHEIGHT;
	boolean gameOver = false;
	
	AudioClip soundClip[]=new AudioClip[1];
	
	long blinking = 0;
	
	Image background;
	
	Image bowserLogo;
	int bowserX=-2850, bowserY=-2865, bowserWidth=WWIDTH+5700, bowserHeight=WHEIGHT+5700;
	
	int coinsCollected = 0;
	
	Mario mario;
	Goomba goomba1;
	Goomba goomba2;
	Goomba goomba3;
	Crusher crusher;
	Cloud cloud;
	MovingPlatform platform;
	
	ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
	
	ArrayList<Spikeball> balls = new ArrayList<Spikeball>();
	
	Mushroom mushroom1;
	
	public void prep()
	{
		MediaTracker t = new MediaTracker(this);
		background = new ImageIcon(this.getClass().getResource("mario_background.jpeg")).getImage();
		bowserLogo = new ImageIcon(this.getClass().getResource("bowserLogo.png")).getImage();
		t.addImage(background, 0);
		t.addImage(bowserLogo, 0);
		try
		{
			t.waitForID(0);
		} catch (InterruptedException e){}
		
		mario = new Mario(20, 860, 40, 80, this); //860 base, 500 air
		grid.addSprite(mario);
		
		for (int b=0;b<100;b++)
		{
			if (!((b>60 && b<65) || (b>72 && b<77) || (b>81 && b<90)))
			{
				Block bl = new Block(b*40, 940, 40, 40, this);
				grid.addSprite(bl);
			}
			
			if (b==60)
			{
				Block bl = new Block(b*40, 900, 40, 40, this);
				grid.addSprite(bl);
			}
		}
		
		for (int b=0;b<8;b++)
		{
			for (int i=0;i<b;i++)
			{
				Block bl = new Block(640+40*b, 940-40*i, 40, 40, this);
				grid.addSprite(bl);
			}
		}
		
		/*for (int b=0;b<8;b++)
		{
			for (int i=b-1;i>0;i--)
			{
				Block bl = new Block(1680-40*b, 940-40*i, 40, 40, this);
				grid.addSprite(bl);
			}
		}*/
		
		for (int b=0;b<11;b++)
		{
			for (int i=0;i<7;i++)
			{
				Block bl = new Block(960+40*b, 940-40*i, 40, 40, this);
				grid.addSprite(bl);
			}
		}
		
		for (int i=0;i<3;i++)
		{
			ItemBox itmbx = new ItemBox(280+60*i, SHEIGHT-220, 40, 40, this);
			grid.addSprite(itmbx);
		}
		
		ItemBox itmbx = new ItemBox(280+60, SHEIGHT-380, 40, 40, this);
		grid.addSprite(itmbx);
		
		ItemBox itmbxOther = new ItemBox(1360+40, SHEIGHT-280, 40, 40, this);
		grid.addSprite(itmbxOther);
		
		goomba1 = new Goomba(920, 660, 40, 40, this);
		grid.addSprite(goomba1);
		
		goomba2 = new Goomba(1360+40, SHEIGHT-100, 40, 40, this);
		grid.addSprite(goomba2);
		
		goomba3 = new Goomba(2340, SHEIGHT-100, 40, 40, this);
		grid.addSprite(goomba3);
		
		Plant plant = new Plant((1360 + 240) + 175 , SHEIGHT-440, 180, 200, this);
		grid.addSprite(plant);
		
		for (int i=0;i<3;i++)
		{
			Pipe p = new Pipe((1360+240) + i*200, SHEIGHT-260, 140, 200, this);
			grid.addSprite(p);
		}
		
		crusher = new Crusher(2640, SHEIGHT - 500, 240, 260, this);
		grid.addSprite(crusher);
		
		for (int i=0;i<7;i++)
		{
			Coin c = new Coin(2640 + i*40, 900, 20, 20, this);
			grid.addSprite(c);
		}
		
		cloud = new Cloud(20, SHEIGHT - WHEIGHT + 40, 100, 100, this);
		grid.addSprite(cloud);
		
		platform = new MovingPlatform(3260, 920, 360, 60, this);
		grid.addSprite(platform);
		
		Flag flag = new Flag(4000-360, 440, 140, 500, this);
		grid.addSprite(flag);
		
		//for (int i=0;i<)
		
		for (int i=0;i<11;i++)
		{
			Coin c = new Coin(940 + i*40, 640, 20, 20, this);
			grid.addSprite(c);
		}
		
		for (int i=0;i<14;i++)
		{
			Coin c = new Coin(100 + i*40, 880, 20, 20, this);
			grid.addSprite(c);
		}
	}
	
	public Runner()
	{
		prep();
		
		frame = new JFrame("Super Mario Bros. Java -- By Aditya A.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WWIDTH, WHEIGHT);
		frame.add(this);
		frame.addKeyListener(this);
		frame.setVisible(true);
		//frame.setResizable(false);
		
		t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		while(!gameOver)
		{	
			//Sprite Control
			
			if (shoot && mario.hasFire)
			{
				Fireball f = new Fireball(mario.hitbox.x + mario.hitbox.width, mario.hitbox.y + mario.hitbox.height*2/3, 20, 20, this);
				fireballs.add(f);
				grid.addSprite(f);
			}
			
			if (up)
				mario.update("up");
			if (down)
				mario.update("down");
			if (left)
				mario.update("left");
			if (right)
				mario.update("right");
			
			if (!up && !down && !right && !left)
				mario.update("-");
			
			mario.update("");
			if (!goomba1.killed)
				goomba1.update();
			if (!goomba2.killed)
				goomba2.update();
			if (!goomba3.killed)
				goomba3.update();
			crusher.update();
			cloud.update();
			platform.update();
			
			for (Fireball f : fireballs)
				f.update();
			
			if (mushroom1!=null && !mushroom1.collected)
				mushroom1.update();
			
			if (cloud.placeInBlock%20==0)
			{
				int temp = (int)(Math.random()*100);
				if (temp==58)
				{
					Spikeball ball = new Spikeball(cloud.hitbox.x, cloud.hitbox.y + cloud.hitbox.height, 40, 40, this);
					balls.add(ball);
					grid.addSprite(ball);
				}
			}
			
			for (Spikeball s : balls)
				s.update();
			
			if (balls.size()>5)
			{
				Spikeball b = balls.get(0);
				balls.remove(b);
				grid.removeSprite(b);
			}
			
			//Automatic View Control
			
			if (mario.hitbox.x-currentX>WWIDTH*2/3)
				currentX+=20;
			
			if (mario.hitbox.x-currentX<WWIDTH/3 && currentX>0)
				currentX-=20;
			
			//Analyze Manual View Control
			final int MOVE_CONSTANT = 10;
			
			if (viewUp && currentY-MOVE_CONSTANT >= 0)
				currentY-=MOVE_CONSTANT;
			if (viewDown && currentY+WHEIGHT+MOVE_CONSTANT <= SHEIGHT)
				currentY+=MOVE_CONSTANT;
			if (viewRight && currentX+WWIDTH+MOVE_CONSTANT <= SWIDTH)
				currentX+=MOVE_CONSTANT;
			if (viewLeft && currentX-MOVE_CONSTANT >= 0)
				currentX-=MOVE_CONSTANT;
			
			//Touching Anything
			
			if (System.currentTimeMillis() - blinking>=2000)
				blinking = 0;
			
			ArrayList<Sprite> touching = grid.spritesTouching(mario, 3);
			for (Sprite s : touching)
				if (s instanceof Goomba)
				{
					grid.removeSprite(s);
					((Goomba) s).kill();
				}
			
			for (int side=2;side<=4;side+=2)
			{
				touching.clear();
				touching = grid.spritesTouching(mario, side);
				for (Sprite s : touching)
					if (s instanceof Goomba && !mario.isJustHit())
					{
						if (mario.hasFire)
						{
							mario.removeFirePower();
							mario.hit();
						}
						else if (mario.hitbox.width==40)
							mario.kill();
						else 
						{
							mario.hitbox.width=40;
							mario.hitbox.height=80;
							mario.hit();
						}
					}
			}
			
			for (int side=1;side<=4;side++)
			{
				touching.clear();
				touching = grid.spritesTouching(mario, side);
				for (Sprite s : touching)
					if (s instanceof Coin)
					{
						grid.removeSprite(s);
						((Coin) s).collect();
						coinsCollected++;
					}
			}
			
			for (int side=1;side<=4;side++)
			{
				touching.clear();
				touching = grid.spritesTouching(mario, side);
				for (Sprite s : touching)
					if ((s instanceof Crusher || s instanceof Plant || s instanceof Spikeball) && !mario.isJustHit())
					{
						if (mario.hasFire)
						{
							mario.removeFirePower();
							mario.hit();
						}
						else if (mario.hitbox.width==40)
							mario.kill();
						else 
						{
							mario.hit();
							mario.hitbox.width=40;
							mario.hitbox.height=80;
						}
					}
			}
			
			for (int side=1;side<=4;side++)
			{
				touching.clear();
				
				for (Fireball f : fireballs)
				{
					touching = grid.spritesTouching(f, side);
					for (Sprite s : touching)
						if (s instanceof Goomba)
						{
							grid.removeSprite(s);
							((Goomba)s).kill();
							f.extinguish();
							fireballs.remove(f);
						}
						else if (s instanceof Plant)
						{
							((Plant) s).hit();
							f.extinguish();
						}
				}
			}
			
			touching.clear();
			touching = grid.spritesTouching(mario, 1);
			for (Sprite s : touching)
				if (s instanceof ItemBox)
				{
					if (!((ItemBox) s).hit)
					{
						((ItemBox) s).hit();
						
						int temp = (int)(Math.random()*7);
						if (temp==6 || temp==5 || temp == 4)
						{
							mushroom1 = new Mushroom(s.hitbox.x, s.hitbox.y - 40, 40, 40, this);
							grid.addSprite(mushroom1);
						}
						//else if (temp==5 || temp==4) //FIRE BALLS DO NOT WORK PROPERLY!!!
						//{
						//	Fireflower f = new Fireflower(s.hitbox.x, s.hitbox.y - 40, 40, 40, this);
						//	grid.addSprite(f);
						//}
						else
						{
							Coin c = new Coin(s.hitbox.x, s.hitbox.y - 20, 20, 20, this);
							grid.addSprite(c);
						}
						
					}
				}
			
			for (int side=1;side<=4;side++)
			{
				touching.clear();
				touching = grid.spritesTouching(mario, side);
				for (Sprite s : touching)
					if (s instanceof Mushroom)
					{
						((Mushroom) s).collect();
						
						if (mario.hitbox.width==40)
						{
							mario.hitbox.y-=20;
							mario.hitbox.height+=20;
							mario.hitbox.width+=10;
						}
					}
			}
			
			for (int side=1;side<=4;side++)
			{
				touching.clear();
				touching = grid.spritesTouching(mario, side);
				for (Sprite s : touching)
					if (s instanceof Fireflower)
					{
						((Fireflower) s).collect();
						
						if (mario.hitbox.width==40)
						{
							mario.hitbox.y-=20;
							mario.hitbox.height+=20;
							mario.hitbox.width+=10;
						}
						
						mario.giveFirePower();
					}
			}
			
			for (int side=1;side<=4;side++)
			{
				touching.clear();
				touching = grid.spritesTouching(mario, side);
				for (Sprite s : touching)
					if (s instanceof Flag)
					{
						gameOver = true;
					}
			}
			
			//Necessary Other Stuff...
			repaint();
			try
			{
				if (!mario.killed)
					Thread.sleep(25);
				else Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e)
				{}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.g = g;
		
		g.drawImage(background, -currentX, -currentY, 2000, 1031, this);
		g.drawImage(background, -currentX+2000, -currentY, 2000, 1031, this);
		
		grid.paint();
		
		if (mario.killed)
		{
			if (!(bowserWidth==WWIDTH) && !(bowserHeight==WHEIGHT))
			{
				g.drawImage(bowserLogo, bowserX, bowserY, bowserWidth, bowserHeight, this);
				bowserWidth-=20;
				bowserHeight-=20;
				bowserX+=10;
				bowserY+=10;
			}
			else
			{
				g.setColor(Color.RED);
				g.fillRect(0, 0, WWIDTH, WHEIGHT);
				g.drawImage(bowserLogo, bowserX, bowserY, bowserWidth, bowserHeight, this);
			}
		}
		
		Font f = new Font("Pixel Emulator", Font.BOLD, 20);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString("Coins Collected: " + coinsCollected, WWIDTH-300, 50);
		
		if (mario.isJustHit())
		{
			g.setColor(Color.RED);
			f = new Font("Pixel Emulator", Font.BOLD, 40);
			g.setFont(f);
			g.drawString("!", mario.hitbox.x-Runner.currentX + 20, mario.hitbox.y-Runner.currentY - 20);
		}
		
		if (gameOver)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WWIDTH, WHEIGHT);
			f = new Font("Pixel Emulator", Font.BOLD, 170);
			g.setFont(f);
			g.setColor(Color.GREEN);
			g.drawString("YOU WIN!!!", WWIDTH/2 - 620, WHEIGHT/2);
		}
		
		//g.setColor(Color.BLACK);
		//g.fillRect(2200-currentX, 940-currentY, 200, 40);
		//g.fillRect(2520-currentX, 940-currentY, 200, 40);
	}
	
	public void keyPressed(KeyEvent ke)
	{
		int key = ke.getKeyCode();

		if (key==KeyEvent.VK_RIGHT)
			right = true;
		if (key==KeyEvent.VK_LEFT)
			left = true;
		if (key==KeyEvent.VK_UP)
			up = true;
		if (key==KeyEvent.VK_DOWN)
			down = true;
		if (key==KeyEvent.VK_SPACE)
			shoot = true;
		
		//Manual View Control
		
		if (key==KeyEvent.VK_D)
			viewRight = true;
		if (key==KeyEvent.VK_A)
			viewLeft = true;
		if (key==KeyEvent.VK_W)
			viewUp = true;
		if (key==KeyEvent.VK_S)
			viewDown = true;
	}
	
	public void keyReleased(KeyEvent ke)
	{
		int key = ke.getKeyCode();

		if (key==KeyEvent.VK_RIGHT)
			right = false;
		if (key==KeyEvent.VK_LEFT)
			left = false;
		if (key==KeyEvent.VK_UP)
			up = false;
		if (key==KeyEvent.VK_DOWN)
			down = false;
		if (key==KeyEvent.VK_SPACE)
			shoot = false;
		
		//Manual View Control
		
		if (key==KeyEvent.VK_D)
			viewRight = false;
		if (key==KeyEvent.VK_A)
			viewLeft = false;
		if (key==KeyEvent.VK_W)
			viewUp = false;
		if (key==KeyEvent.VK_S)
			viewDown = false;
	}
	
	public void keyTyped(KeyEvent ke)
	{
		
	}
	
	public static void main(String args[])
	{
		Runner app = new Runner();
	}
}