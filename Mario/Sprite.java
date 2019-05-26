import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.ArrayList;

public class Sprite 
{
	Rectangle hitbox;
	
	public Sprite(int x, int y, int width, int height)
	{
		hitbox = new Rectangle(x/Runner.CELLSIZE*Runner.CELLSIZE, y/Runner.CELLSIZE*Runner.CELLSIZE, width, height);
	}
	
	public int getX()
	{
		return hitbox.x;
	}
	
	public int getY()
	{
		return hitbox.y;
	}
	
	public int getWidth()
	{
		return hitbox.width;
	}
	
	public int getHeight()
	{
		return hitbox.height;
	}

	public void paint()
	{
		Runner.g.drawRect(hitbox.x-Runner.currentX, hitbox.y-Runner.currentY, hitbox.width, hitbox.height);
	}
}
