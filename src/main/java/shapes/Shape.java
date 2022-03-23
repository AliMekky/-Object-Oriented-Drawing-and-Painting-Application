package shapes;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Shape implements Cloneable {//Abstract class to achieve Template design pattern,all shapes extends this class.
	Color c;
	public Coordinates p;//Points that determines the position and dimensions of the shape.
	public Shape(){
		
	}
		
	

	public Shape(Color c,int x1,int y1,int x2,int y2) {
		this.c = c;
		p = new Coordinates(x1,y1,x2,y2);
	}



	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
	
	public abstract boolean contains(int x,int y); //Returns true if the point lies inside the shape or on its boundaries.
	public abstract Shape clone();//Copies the shape.
    public abstract void save(BufferedImage image);//Saves the drawings as an image.
    public abstract void drawShape(Graphics g);//Draws the shape.
}

