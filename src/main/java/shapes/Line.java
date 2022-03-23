package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Line extends Shape implements Cloneable {
	public Line(int x1, int y1, int x2, int y2, Color c) {
		super(c, x1, y1, x2, y2);

	}

	@Override
	public void drawShape(Graphics g) {// Draws Line.
		g.setColor(super.getC());
		g.drawLine(super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2());

	}

	@Override
	public Shape clone() { // Returns a copy of the line.
		return new Line(super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2(), super.getC());

	}

	@Override
	public void save(BufferedImage image) {// Saves line into an image.
		Graphics2D g = image.createGraphics();
		g.setStroke(new BasicStroke(5));
		g.setColor(super.getC());
		g.drawLine(super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2());
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public boolean contains(int x, int y) {// Returns true if point (x,y) lies on the line.
		double lineSlope = 0;// Gets the slope of the line using its start and end points.
		double pointSlope1 = 0; // Gets the slope of the line using its start point and point (x,y).
		double pointSlope2 = 0; // Gets the slope of the line using its end point and point (x,y).
		if (p.getX1() != p.getX2() && x != p.getX1() && x != p.getX2()) {// Condition that checks if its a line not a point.
			lineSlope = (p.getY2() - p.getY1()) / (p.getX2() - p.getX1());
			pointSlope1 = (y - p.getY1()) / (x - p.getX1());
			pointSlope2 = (y - p.getY2()) / (x - p.getX2());
		}
		// If the difference between the slope of the line using original points and
		// slope of line using (x,y) is very small
		// checks if x belongs to [min(x1,x2),max(x1,x2)] and if y belongs to
		// [min(y1,y2),max(y1,y2)]
		// returns true
		return Math.abs(lineSlope - pointSlope1) <= 0.01 && Math.abs(lineSlope - pointSlope2) <= 0.01
				&& Math.min(p.getX1(), p.getX2()) <= x && Math.max(p.getX1(), p.getX2()) >= x
				&& Math.min(p.getY1(), p.getY2()) <= y && Math.max(p.getY1(), p.getY2()) >= y;
	}
}
