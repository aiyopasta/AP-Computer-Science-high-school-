import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Stage
{
	Rectangle screen = new Rectangle(40, 40, 1420, 720);
	Block b1, b2, b3, b5, b6, b8, b11;
	StairCaseBlock b7;
	PlusBlock b9;
	UpsideDownTBlock b4, b10;
	Graphics g;

	public Stage(Graphics g)
	{
		this.g = g;

		b1 = new Block(40, 300, 35, 35, g);
		b2 = new Block(230, 350, 100, 35, g);
		b3 = new Block(180, 600, 90, 35, g);
		b4 = new UpsideDownTBlock(440, 410, 140, 35, g);
		b5 = new Block(460, 175, 40, 35, g);
		b6 = new Block(710, 250, 250, 35, g);
		b7 = new StairCaseBlock(550, 610, 50, 200, 100, g); //655, 105
		b8 = new Block(825, 430, 65, 35, g);
		b9 = new PlusBlock(1050, 400, 200, 35, g);
		b10 = new UpsideDownTBlock(1150, 128, 120, 35, g);
		b11 = new Block(1300, 600, 160, 65, g);
	}

	public void draw()
	{
		g.setColor(Color.WHITE);
		g.fillRect((int)screen.getX(), (int)screen.getY(), (int)screen.getWidth(), (int)screen.getHeight());
		g.setColor(new Color(0, 128, 255));


		b1.draw();
		b2.draw();
		b3.draw();
		b4.draw();
		b5.draw();
		b6.draw();
		b7.draw();
		b8.draw();
		b9.draw();
		b10.draw();
		b11.draw();
	}

	public boolean intersectsWith(Mover m)
	{
		boolean a = m.getRect().intersects(b1.getRect());
		boolean b = m.getRect().intersects(b2.getRect());
		boolean c = m.getRect().intersects(b3.getRect());

		boolean d = false;

		for (Block bl : b4.getBlocks())
			if (m.getRect().intersects(bl.getRect()))
				d = true;

		boolean e = m.getRect().intersects(b5.getRect());
		boolean f = m.getRect().intersects(b6.getRect());

		boolean g = false;

		for (Block bl : b7.getBlocks())
			if (m.getRect().intersects(bl.getRect()))
				d = true;

		boolean h = m.getRect().intersects(b8.getRect());

		boolean i = false;

		for (Block bl : b9.getBlocks())
			if (m.getRect().intersects(bl.getRect()))
				d = true;


		boolean j = false;

		for (Block bl : b10.getBlocks())
			if (m.getRect().intersects(bl.getRect()))
				d = true;

		boolean k = m.getRect().intersects(b11.getRect());
		boolean l = !screen.contains(m.getRect());
		if (a || b || c || d || e || f || g || h || i || j || k || l)
			return true;

		return false;
	}

	public boolean supportsMover(Mover m)
	{
		Point2D pLeft = new Point2D.Double((int)m.getRect().getX(), (int)m.getRect().getY()+50);
		Point2D pRight = new Point2D.Double((int)m.getRect().getX()+49, (int)m.getRect().getY()+50);
		Point2D pMiddle = new Point2D.Double((int)m.getRect().getX()+25, (int)m.getRect().getY()+50);

		boolean a = b1.getRect().contains(pLeft) || b1.getRect().contains(pRight) || b1.getRect().contains(pMiddle);
		boolean b = b2.getRect().contains(pLeft) || b2.getRect().contains(pRight) || b2.getRect().contains(pMiddle);
		boolean c = b3.getRect().contains(pLeft) || b3.getRect().contains(pRight) || b3.getRect().contains(pMiddle);

		boolean d = false;

		for (Block bl : b4.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || bl.getRect().contains(pMiddle))
				d = true;

		boolean e = b5.getRect().contains(pLeft) || b5.getRect().contains(pRight) || b5.getRect().contains(pMiddle);
		boolean f = b6.getRect().contains(pLeft) || b6.getRect().contains(pRight) || b6.getRect().contains(pMiddle);

		boolean g = false;

		for (Block bl : b7.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || bl.getRect().contains(pMiddle))
				d = true;

		boolean h = b8.getRect().contains(pLeft) || b8.getRect().contains(pRight) || b8.getRect().contains(pMiddle);

		boolean i = false;

		for (Block bl : b9.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || bl.getRect().contains(pMiddle))
				d = true;


		boolean j = false;

		for (Block bl : b10.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || b1.getRect().contains(pMiddle))
				d = true;

		boolean k = b11.getRect().contains(pLeft) || b11.getRect().contains(pRight) || b11.getRect().contains(pMiddle);
		boolean l = !screen.contains(pLeft) || !screen.contains(pRight) || !screen.contains(pMiddle);

		if (a || b || c || d || e || f || g || h || i || j || k || l)
			return true;

		return false;
	}

	public boolean containsPoints(Point p1, Point p2, Point p3)
	{
		Point2D pLeft = p1;
		Point2D pRight = p2;
		Point2D pMiddle = p3;

		boolean a = b1.getRect().contains(pLeft) || b1.getRect().contains(pRight) || b1.getRect().contains(pMiddle);
		boolean b = b2.getRect().contains(pLeft) || b2.getRect().contains(pRight) || b2.getRect().contains(pMiddle);
		boolean c = b3.getRect().contains(pLeft) || b3.getRect().contains(pRight) || b3.getRect().contains(pMiddle);

		boolean d = false;

		for (Block bl : b4.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || bl.getRect().contains(pMiddle))
				d = true;

		boolean e = b5.getRect().contains(pLeft) || b5.getRect().contains(pRight) || b5.getRect().contains(pMiddle);
		boolean f = b6.getRect().contains(pLeft) || b6.getRect().contains(pRight) || b6.getRect().contains(pMiddle);

		boolean g = false;

		for (Block bl : b7.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || bl.getRect().contains(pMiddle))
				d = true;

		boolean h = b8.getRect().contains(pLeft) || b8.getRect().contains(pRight) || b8.getRect().contains(pMiddle);

		boolean i = false;

		for (Block bl : b9.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || bl.getRect().contains(pMiddle))
				d = true;


		boolean j = false;

		for (Block bl : b10.getBlocks())
			if (bl.getRect().contains(pLeft) || bl.getRect().contains(pRight) || b1.getRect().contains(pMiddle))
				d = true;

		boolean k = b11.getRect().contains(pLeft) || b11.getRect().contains(pRight) || b11.getRect().contains(pMiddle);
		boolean l = !screen.contains(pLeft) || !screen.contains(pRight) || !screen.contains(pMiddle);

		if (a || b || c || d || e || f || g || h || i || j || k || l)
			return true;

		return false;
	}

	public Rectangle getScreen()
	{
		return screen;
	}
}