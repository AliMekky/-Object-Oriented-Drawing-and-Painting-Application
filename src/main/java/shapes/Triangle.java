package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Triangle extends Shape {

	int x3, y3;

	public Triangle(int x1, int y1, int x2, int y2, Color c) {
		super(c, x1, y1, x2, y2);
	}

	@Override
	public void drawShape(Graphics g) {
		int[] x = new int[3];
		int[] y = new int[3];
		x[0] = super.p.getX1();
		x[1] = super.p.getX2();
		x[2] = super.p.getX1() - (super.p.getX2() - super.p.getX1()); // getting x3 from x1 and x2
		y[0] = super.p.getY1();
		y[1] = super.p.getY2();
		y[2] = super.p.getY2(); // y3 = y2 because its isosceles triangle.
		g.setColor(super.getC());
		g.drawPolygon(x, y, 3);

	}

	@Override
	public Shape clone() { // returns a copy of the triangle.

		return new Triangle(super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2(), super.getC());

	}

	@Override
	public void save(BufferedImage image) { // Saves shape into image.
		Graphics2D g = image.createGraphics();
		g.setStroke(new BasicStroke(5));
		g.setColor(super.getC());
		int[] x = new int[3];
		int[] y = new int[3];
		x[0] = super.p.getX1();
		x[1] = super.p.getX2();
		x[2] = super.p.getX1() - (super.p.getX2() - super.p.getX1());
		y[0] = super.p.getY1();
		y[1] = super.p.getY2();
		y[2] = super.p.getY2();
		g.drawPolygon(x, y, 3);

	}

	double area(int x1, int y1, int x2, int y2, int x3, int y3) {// Calculates area of triangle.
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	@Override

	public boolean contains(int x, int y) {
		this.x3 = super.p.getX1() - (super.p.getX2() - super.p.getX1());
		this.y3 = super.p.getY2();

		/* Calculate area of triangle ABC */
		double A = area(super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2(), this.x3, this.y3);

		/* Calculate area of triangle PBC */
		double A1 = area(x, y, super.p.getX2(), super.p.getY2(), this.x3, this.y3);

		/* Calculate area of triangle PAC */
		double A2 = area(super.p.getX1(), super.p.getY1(), x, y, this.x3, this.y3);

		/* Calculate area of triangle PAB */
		double A3 = area(super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2(), x, y);

		/* Check if sum of A1, A2 and A3 is same as A */
		return (A == A1 + A2 + A3);
	}
}
