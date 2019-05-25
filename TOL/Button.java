import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Button
{
	Ellipse2D elpsActual, elpsReg, elpsDef;
	Block b;
	boolean isPressed;
	boolean hasBeenPressed = false; 
	Graphics g;

	public Button()
	{
		isPressed = false;
		elpsReg = new Ellipse2D.Double(780, 220, 135, 25);
		elpsActual = new Ellipse2D.Double(elpsReg.getX(), elpsReg.getY(), elpsReg.getWidth(), elpsReg.getHeight());
		elpsDef = new Ellipse2D.Double(elpsReg.getX(), elpsReg.getY(), elpsReg.getWidth(), elpsReg.getHeight()-10);
	}

	public void createBlock(Graphics g)
	{
		this.g = g;
		b = new Block(780, 230, 135, 20, g);
	}

	public boolean hasBeenPressed()
	{
		return hasBeenPressed;
	}

	public boolean isPressed(Mover m)
	{
		isPressed = b.getRect().intersects(m.getRect()) || elpsActual.intersects(m.getRect());
		if (isPressed)
		{
			hasBeenPressed = true;
			elpsActual.setFrame(elpsDef.getX(), elpsDef.getY(), elpsDef.getWidth(), elpsDef.getHeight());
			return true;
		}
		else
		{
			elpsActual.setFrame(elpsReg.getX(), elpsReg.getY(), elpsReg.getWidth(), elpsReg.getHeight());
			return false;
		}
	}
	
	public void unPress()
	{
		hasBeenPressed = false;
	}

	public Block getBlock()
	{
		return b;
	}

	public Ellipse2D getElps()
	{
		return elpsActual;
	}
}
