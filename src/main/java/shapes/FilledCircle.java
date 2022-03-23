package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FilledCircle extends Shape {

    public FilledCircle(Color c, int x1, int y1, int x2, int y2) {
        super(c, x1, y1, x2, y2);
    }

    @Override
    public void drawShape(Graphics g) {//Covers all cases of the 4 quadrants where (x1,y1) is considered the origin, and sets the color.
        g.setColor(super.getC());
        if (super.p.getX2() < super.p.getX1() && super.p.getY2() < super.p.getY1()) {
            g.fillOval(super.p.getX2(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        } else if (super.p.getX2() < super.p.getX1()) {
            g.fillOval(super.p.getX2(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        } else if (super.p.getY2() < super.p.getY1()) {
            g.fillOval(super.p.getX1(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        } else {
            g.fillOval(super.p.getX1(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        }

    }

    @Override
    public Shape clone() {//returns a copy of the circle.
        
        return new FilledCircle(super.getC(), super.p.getX1(), super.p.getY1(), super.p.getX2(), super.p.getY2());

    }

    @Override
    public void save(BufferedImage image) {//Same concept of covering the 4 cases, draws into an image.
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(5));
        g.setColor(super.getC());
        if (super.p.getX2() < super.p.getX1() && super.p.getY2() < super.p.getY1()) {
            g.fillOval(super.p.getX2(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        } else if (super.p.getX2() < super.p.getX1()) {
            g.fillOval(super.p.getX2(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        } else if (super.p.getY2() < super.p.getY1()) {
            g.fillOval(super.p.getX1(), super.p.getY2(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        } else {
            g.fillOval(super.p.getX1(), super.p.getY1(), Math.abs(super.p.getX2() - super.p.getX1()), Math.abs(super.p.getX2() - super.p.getX1()));
        }
    }

    public int getRadius() {//Gets radius of the circle.

        return Math.abs((super.p.getX2() - super.p.getX1())) / 2;

    }

    public int getCenterX() {//Gets X coordinate of the center.
        return Math.min(super.p.getX1(), super.p.getX2()) + this.getRadius();
    }

    public int getCenterY() {//Gets Y coordinate of the center.
        return Math.min(super.p.getY1(), super.p.getY2()) + this.getRadius();
    }

    @Override

    public boolean contains(int x, int y) {//Returns true if point (x,y) satisfies equation of circle, therefore it's inside the circle or on its boundaries.

        return (((x - this.getCenterX()) * (x - this.getCenterX())
                + (y - this.getCenterY()) * (y - this.getCenterY())) <= (this.getRadius() * this.getRadius()));

    }

}
