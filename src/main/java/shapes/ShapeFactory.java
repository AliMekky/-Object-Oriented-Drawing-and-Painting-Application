/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

import java.awt.Color;

/**
 *
 * @author LENOVO
 */
public class ShapeFactory {// Factory design pattern that helps in creating shape objects.
	private int mode;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color color;

	public ShapeFactory(int mode, int x1, int y1, int x2, int y2, Color color) {
		this.mode = mode;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;

	}

	public Shape createShape() {// Each shape is linked with an integer mode.
		if (mode == 0)
			return new Line(x1, y1, x2, y2, color);
		else if (mode == 1)
			return new Rectangle(x1, y1, x2, y2, color);
		else if (mode == 2)
			return new FilledRectangle(x1, y1, x2, y2, color);
		else if (mode == 3)
			return new Square(x1, y1, x2, y2, color);
		else if (mode == 4)
			return new FilledSquare(x1, y1, x2, y2, color);
		else if (mode == 5)
			return new Circle(color, x1, y1, x2, y2);
		else if (mode == 6)
			return new FilledCircle(color, x1, y1, x2, y2);
		else if (mode == 7)
			return new Triangle(x1, y1, x2, y2, color);
		else if (mode == 8)
			return new FilledTriangle(x1, y1, x2, y2, color);
		return null;
	}

}
