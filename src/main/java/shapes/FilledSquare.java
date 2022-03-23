package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FilledSquare extends Shape {

	public FilledSquare(int x1, int y1, int x2, int y2, Color c) {
		super(c, x1, y1, x2, y2);

	}

	@Override
	public void drawShape(Graphics g) { // Covers all cases of the 4 quadrants where (x1,y1) is considered the origin,
										// and sets the color.
		g.setColor(super.getC());
		if (super.p.getX2() < super.p.getX1() && super.p.getY2() < super.p.getY1()) {
			g.fillRect(super.p.getX2(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		} else if (super.p.getX2() < super.p.getX1()) {
			g.fillRect(super.p.getX2(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		} else if (super.p.getY2() < super.p.getY1()) {
			g.fillRect(super.p.getX1(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		} else {
			g.fillRect(super.p.getX1(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		}

	}

	@Override
	public Shape clone() { // returns a copy of the Filled Square.

		return new FilledSquare(super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2(), super.getC());

	}

	@Override
	public void save(BufferedImage image) { // Same concept of covering the 4 cases, draws into an image.
		Graphics2D g = image.createGraphics();
		g.setStroke(new BasicStroke(5));
		g.setColor(super.getC());
		if (super.p.getX2() < super.p.getX1() && super.p.getY2() < super.p.getY1()) {
			g.fillRect(super.p.getX2(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		} else if (super.p.getX2() < super.p.getX1()) {
			g.fillRect(super.p.getX2(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		} else if (super.p.getY2() < super.p.getY1()) {
			g.fillRect(super.p.getX1(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		} else {
			g.fillRect(super.p.getX1(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()),
					Math.abs(super.p.getX2() - super.p.getX1()));
		}
	}

	@Override
	public boolean contains(int x, int y) { // returns true if point (x,y) is inside the shape or on its boundaries.
		if (x <= p.getX2() && x >= p.getX1() && y <= p.getY2() && y >= p.getY1()) {
			return true;
		} else if (x <= p.getX1() && x >= p.getX2() && y <= p.getY1() && y >= p.getY2()) {
			return true;
		} else if (x <= p.getX1() && x >= p.getX2() && y <= p.getY2() && y >= p.getY1()) {
			return true;
		} else if (x <= p.getX2() && x >= p.getX1() && y <= p.getY1() && y >= p.getY2()) {
			return true;
		}
		return false;

	}

}
