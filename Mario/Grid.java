import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.swing.*;

import java.util.ArrayList;

public class Grid 
{
	static int map[][] = new int[Runner.SHEIGHT/Runner.CELLSIZE][Runner.SWIDTH/Runner.CELLSIZE];
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	public Grid()
	{
		for (int x=0;x<map.length;x++)
			for (int y=0;y<map[0].length;y++)
				map[x][y] = -1;
	}
	
	public void addSprite(Sprite s)
	{
		int cellX = s.getX()/Runner.CELLSIZE;
		int cellY = s.getY()/Runner.CELLSIZE;
		int horizCells = s.getWidth()/Runner.CELLSIZE;
		int vertCells = s.getHeight()/Runner.CELLSIZE;
		
		for (int row=cellY;row<cellY+vertCells;row++)
				for (int col=cellX;col<cellX+horizCells;col++)
				{
					map[row][col] = sprites.size();
				}
		
		sprites.add(s);
	}
	
	public void removeSprite(Sprite s)
	{
		for (int i=0;i<sprites.size();i++)
		{
			if (sprites.get(i).equals(s))
			{
				int spriteNumber = sprites.indexOf(s);
				sprites.set(spriteNumber, new Sprite(0,0,0,0)); //replace sprite with blank sprite
				
				for (int a=0;a<map.length;a++)
				{
					for (int j=0;j<map[0].length;j++)
					{
						if (map[a][j]==spriteNumber)
							map[a][j] = -1;
					}
				}
			}
		}
	}
	
	public boolean isLegalMoveVert(int rowDestination, int colDestination, int width) //dir=1 if up, 2 if down
	{
		for (int i=0;i<width;i++)
		{
			try
			{
				if (map[rowDestination][colDestination+i]>-1)
					return false;
			} catch(Exception ex)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isLegalMoveHoriz(int rowDestination, int colDestination, int height)
	{
		for (int i=0;i<height;i++)
		{
			try
			{
				if (map[rowDestination+i][colDestination]>-1)
					return false;
			} catch(Exception ex)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<Sprite> spritesTouching(Sprite s, int side)
	{
		ArrayList<Sprite> spritesTouching = new ArrayList<Sprite>();
		
		if (side==1)
		{
			int width = s.hitbox.width/Runner.CELLSIZE;
			
			for (int i=0;i<width;i++)
			{
				try
				{
					if (map[s.hitbox.y/Runner.CELLSIZE - 1][s.hitbox.x/Runner.CELLSIZE + i]>-1)
						spritesTouching.add(sprites.get(map[s.hitbox.y/Runner.CELLSIZE - 1][s.hitbox.x/Runner.CELLSIZE + i]));
						
				}catch(Exception ex){}
			}
		}
		else if (side==2)
		{
			int height = s.hitbox.height/Runner.CELLSIZE;
			
			for (int i=0;i<height;i++)
			{
				try
				{
					if (map[s.hitbox.y/Runner.CELLSIZE + i][s.hitbox.x/Runner.CELLSIZE + s.hitbox.width/Runner.CELLSIZE]>-1)
						spritesTouching.add(sprites.get(map[s.hitbox.y/Runner.CELLSIZE + i][s.hitbox.x/Runner.CELLSIZE + s.hitbox.width/Runner.CELLSIZE]));
					
				}catch(Exception ex){}
			}
		}
		else if (side==3)
		{
			int width = s.hitbox.width/Runner.CELLSIZE;
			
			for (int i=0;i<width;i++)
			{
				try
				{
					if (map[s.hitbox.y/Runner.CELLSIZE + s.hitbox.height/Runner.CELLSIZE][s.hitbox.x/Runner.CELLSIZE + i]>-1)
						spritesTouching.add(sprites.get(map[s.hitbox.y/Runner.CELLSIZE + s.hitbox.height/Runner.CELLSIZE][s.hitbox.x/Runner.CELLSIZE + i]));
						
				}catch(Exception ex){}
			}
		}
		else if (side==4)
		{
			int height = s.hitbox.height/Runner.CELLSIZE;
			
			for (int i=0;i<height;i++)
			{
				try
				{
					if (map[s.hitbox.y/Runner.CELLSIZE + i][s.hitbox.x/Runner.CELLSIZE - 1]>-1)
						spritesTouching.add(sprites.get(map[s.hitbox.y/Runner.CELLSIZE + i][s.hitbox.x/Runner.CELLSIZE - 1]));
					
				}catch(Exception ex){}
			}
		}
		
		return spritesTouching;
	}
	
	public void paint()
	{
		Rectangle window = new Rectangle(Runner.currentX, Runner.currentY, Runner.currentX + Runner.WWIDTH, Runner.currentY + Runner.WHEIGHT);
		
		try
		{
			for (Sprite s : sprites)
			{
				if (window.intersects(s.hitbox) || (s instanceof Plant))
					s.paint();
			}
			
		} catch (Exception ex){}
	}
}
