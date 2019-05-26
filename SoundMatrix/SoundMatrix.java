import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.applet.*;
import java.net.*;
import javax.swing.border.*;

public class SoundMatrix extends JFrame implements Runnable, MouseListener
{
	private Container container;
	private JPanel buttonPanel;
	private JPanel bottomControls;
	private JPanel top;
	private JPanel bigPanel;
	private JToggleButton buttons[][];
	private JSlider slider = new JSlider();
	private JButton clearAll = new JButton("Deselect All");
	//private JLabel title;
	private JLabel description;
	private boolean notStopped = true;
	private Thread animator;

	boolean pressed = false;

	AudioClip soundClip[]=new AudioClip[8];

	private final int NUM_BUTTONS = 8;

	public SoundMatrix()
	{
		super("Sound Matrix -- Aditya Abhyankar");

		for (int x=1;x<9;x++)
		{
			try
			{
				URL test = new URL("file:C:/Users/10010897/Desktop/HCS/Big Projects/Sound Matrix/" + x + ".wav");
				soundClip[x-1] = JApplet.newAudioClip(test);
			}catch(MalformedURLException mue)
			{
				System.out.println("File not found");
			}
		}

		bigPanel = new JPanel();
		buttonPanel = new JPanel();
		bottomControls = new JPanel();
		top = new JPanel();
		buttons = new JToggleButton[NUM_BUTTONS][NUM_BUTTONS];
		clearAll.setBackground(new Color(100, 20, 20));
		clearAll.setForeground(Color.WHITE);
		clearAll.addMouseListener(this);
		//title = new JLabel("Sound Matrix", SwingConstants.CENTER);
		description = new JLabel("(Click and drag across buttons to make music!)", SwingConstants.CENTER);

		//title.setFont(new Font("Gill Sans", Font.BOLD, 20)); //Font f = new Font("Gill Sans", Font.PLAIN, 85);
		description.setFont(new Font("Gill Sans", Font.BOLD, 12));
		//title.setForeground(Color.WHITE);
		description.setForeground(Color.PINK);

		slider.setBackground(new Color(45, 45, 45));

		buttonPanel.setOpaque(false);
		top.setOpaque(false);
		bottomControls.setOpaque(false);
		bigPanel.setBackground(Color.BLACK);

		container = getContentPane();
		buttonPanel.setLayout(new GridLayout(NUM_BUTTONS, NUM_BUTTONS, 5, 5));

		for (int x=0;x<NUM_BUTTONS;x++)
		{
			for(int y=0;y<NUM_BUTTONS;y++)
			{
				buttons[x][y] = new JToggleButton();
				buttons[x][y].setPreferredSize(new Dimension(25,25));
				buttons[x][y].addMouseListener(this);
				buttonPanel.add(buttons[x][y]);
				buttons[x][y].setBackground(new Color(35, 35, 35));
				buttons[x][y].setBorder(new EtchedBorder());
			}
		}

		bottomControls.add(slider, BorderLayout.WEST);
		bottomControls.add(clearAll, BorderLayout.EAST);
		//top.add(title, BorderLayout.NORTH);
		top.add(description, BorderLayout.SOUTH);

		bigPanel.add(top, BorderLayout.NORTH);
		bigPanel.add(buttonPanel, BorderLayout.CENTER);
		bigPanel.add(bottomControls, BorderLayout.SOUTH);

		container.add(bigPanel);

		setSize(NUM_BUTTONS*35 + 50, NUM_BUTTONS*35 + 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		animator = new Thread(this);
		animator.start();
	}

	public void mouseClicked(MouseEvent e)
	{
		try
		{
			if (((JButton)e.getSource()).equals(clearAll))
				for (int x=0;x<NUM_BUTTONS;x++)
				{
					for (int y=0;y<NUM_BUTTONS;y++)
					{
						if (buttons[x][y].isSelected())
							buttons[x][y].setSelected(false);
					}
				}
		} catch (Exception E){}
	}

	public void mousePressed(MouseEvent e)
	{
		pressed = true;
	}

	public void mouseReleased(MouseEvent e)
	{
		pressed = false;
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
		try
		{
			if (pressed)
			{
				if (((JToggleButton)e.getSource()).contains(e.getPoint()));
					((JToggleButton)e.getSource()).setSelected(true);
			}
		} catch (Exception E){}
	}

	public void run()
	{
		while(notStopped)
		{
			//SOUND -------------------------------------------------------------

			for (int x=0;x<NUM_BUTTONS;x++)
			{
				for (int y=0;y<NUM_BUTTONS;y++)
				{

					if (buttons[y][x].isSelected())
					{
						soundClip[y].play();
					}
				}

				try
				{
					animator.sleep(Math.abs(200-slider.getValue()));
				} catch(Exception e){}

			}

			//CHANGE COLOR ------------------------------------------------------
			for (int x=0;x<NUM_BUTTONS;x++)
			{
				for(int y=0;y<NUM_BUTTONS;y++)
				{
					if (buttons[x][y].isSelected())
					{
						buttons[x][y].setBorder(new EmptyBorder(25, 25, 25, 25));
					}
					else buttons[x][y].setBorder(new EtchedBorder());
				}
			}

			/*try
			{
				animator.sleep(1200);
			} catch(Exception e){}*/
		}
	}

	public static void main(String args[])
	{
		SoundMatrix app=new SoundMatrix();
	}
}