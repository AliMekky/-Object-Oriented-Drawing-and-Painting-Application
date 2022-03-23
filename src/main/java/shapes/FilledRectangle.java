package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FilledRectangle extends Shape{
	public FilledRectangle(int x1,int y1,int x2,int y2,Color c) {
		super(c,x1,y1,x2,y2);
		
		
		
	}

	@Override
	public void drawShape(Graphics g) {//Covers all cases of the 4 quadrants and sets the color of the shape.
		g.setColor(super.getC());
		if(super.p.getX2() < super.p.getX1() && super.p.getY2() < super.p.getY1()) 
			g.fillRect(super.p.getX2(),super.p.getY2(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
		else if (super.p.getX2() < super.p.getX1())
			g.fillRect(super.p.getX2(),super.p.getY1(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
		else if (super.p.getY2() < super.p.getY1())
			g.fillRect(super.p.getX1(),super.p.getY2(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
		else		
                    g.fillRect(super.p.getX1(),super.p.getY1(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
		
	}

	@Override
	public Shape clone() {//Returns copy of the FilledRectangle.
          return new FilledRectangle(super.p.getX1(),super.p.getY1(),super.p.getX2(),super.p.getY2(),super.getC());
	
	}

    @Override
    public void save(BufferedImage image) {//Same concept of 4 quadrants. Saves shape into an image.
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(5));
        g.setColor(super.getC());
		if(super.p.getX2() < super.p.getX1() && super.p.getY2() < super.p.getY1()) 
			g.fillRect(super.p.getX2(),super.p.getY2(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
		else if (super.p.getX2() < super.p.getX1())
			g.fillRect(super.p.getX2(),super.p.getY1(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
		else if (super.p.getY2() < super.p.getY1())
			g.fillRect(super.p.getX1(),super.p.getY2(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
		else		
                    g.fillRect(super.p.getX1(),super.p.getY1(),Math.abs(super.p.getX2()-super.p.getX1()),Math.abs(super.p.getY2() - super.p.getY1()));
	}

    @Override
    public boolean contains(int x, int y) {//returns true if point (x,y) is inside the shape or on its boundaries.
                if(x<=p.getX2() && x>=p.getX1() && y<=p.getY2() && y>=p.getY1()){
			return true;
                }
		else if (x<=p.getX1() && x>=p.getX2() && y<=p.getY1() && y>=p.getY2())
			return true;
		else if (x<=p.getX1() && x>=p.getX2() && y<=p.getY2() && y>=p.getY1())
			return true;
		else if (x<=p.getX2() && x>=p.getX1() && y<=p.getY1() && y>=p.getY2())
			return true;
		return false;

    }
	

}

