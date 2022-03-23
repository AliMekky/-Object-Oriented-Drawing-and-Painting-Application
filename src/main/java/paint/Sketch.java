package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import shapes.Shape;
import shapes.Line;

import javax.swing.JPanel;
import shapes.ShapeFactory;

public class Sketch extends JPanel implements MouseListener, MouseMotionListener {

	DrawingHistory dh = DrawingHistory.getinstance(); // instance from the singelton drawing history to access the
														// stacks
	static Color currentColor = Color.BLACK; // default color is black
	int x1, y1; // variables to store (x,y) of mouse pressed position
	int x2, y2; // variables to store (x,y) of mouse dragged position
	//public static int font =5;
	int tempX, tempY; // variables to be used during calculations (helpers)
	int modeShape = -1; // variable to store the integer mode of shapes
	int modeFunctions = -1; // variable to store the integer mode of functions
	boolean chosen = false; // varible to indicate that the user has drawn at least one shape
	public int index = -1; // variable to store index of selected shapes
	ArrayList<Shape> x = new ArrayList<Shape>(); // array list to story the drawn shapes
	SketchFunctions fun = new SketchFunctions();
	int copyPressed = 0; // variable to store the number of the press during the COPY
	boolean copySelected = false; // variable to indicate whether the user has chosen a shape to COPY or not
	Strategy strategy = new Strategy(fun);
	public Sketch() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Iterator<Shape> it = x.iterator();// Iterator design pattern
		while (it.hasNext()) { // drawing all the elements in the arraylist
			Shape s = it.next();
			Graphics2D gd = (Graphics2D) g;
            gd.setStroke(new BasicStroke(5));
			s.drawShape(gd); // polymorphism
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();	// getting X coordinate of dragging 
		y2 = e.getY();	// getting Y coordinate of dragging
		// if the user did't choose any edit function then he will add a new shape. (chosen = TRUE)
		if (chosen && modeFunctions != 13 && modeFunctions != 11 && modeFunctions != 14 && modeFunctions != 10) {
			if (x.size() != 0) { //there is at least one shape in the arraylist
				x.get(x.size() - 1).p.setX2(x2);	//updating the X coordinate of the last element in the arraylist(last one drawn)to the x coordinate of the dragged position
				x.get(x.size() - 1).p.setY2(y2);	//updating the Y coordinate of the last element in the arraylist(last one drawn)to the y coordinate of the dragged position
			}
			repaint();
		}

		if (modeFunctions == 14) {	// integer mode 14 is resizing mode 
			if (index >= 0) {	// the user has selected a shape

				x.get(index).p.setX2(x2);	// update X coordinate of the end point of the selected shape to the x of the dragged position  
				x.get(index).p.setY2(y2);	// update Y coordinate of the end point of the selected shape to the y of the dragged position
				repaint();
			}
		} else if (modeFunctions == 10) {	// integer mode 10 is moving mode 
			{
				if (index >= 0) {	// the user has selected a shape

					int temp1 = x2 - x.get(index).p.getX1(); //getting the difference between x and the starting point (x1)
					int temp2 = y2 - x.get(index).p.getY1(); //getting the difference between y and the starting point (x2)

					x.get(index).p.setX1(x2); // update X coordinate of the starting point to x of the dragged position
					x.get(index).p.setY1(y2); // update the y coordinate of the starting point to y of the dragged position
					x.get(index).p.setX2(x.get(index).p.getX2() + temp1); // update x Coordinate of the 
					x.get(index).p.setY2(x.get(index).p.getY2() + temp2);
					repaint();

				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x1 = e.getX(); // storing the X coordinate of the pressed position
		int y1 = e.getY(); // storing the Y coordinate of the pressed position

		if (modeFunctions == 13) {
			
			strategy.chooseStrategy(modeFunctions, x, x1, y1); // 
			repaint(); // strategy Class chooses which operation to perform according to the passed arguments (Delete operation)
		}

		if (chosen && modeFunctions != 13 && modeFunctions != 11 && modeFunctions != 14 && modeFunctions != 10) {
			// Shape Factory decides which Shape to make instance of according the passed arguments
			ShapeFactory factory = new ShapeFactory(modeShape, x1, y1, x1, y1, currentColor); 
			dh.fns.push(0); // pushing integer mode of add operation to stack of operations
			x.add(factory.createShape()); // adding the new Shape to the array list that is repainted 
			repaint();
		}

		if (modeFunctions == 11) {

			copyPressed += 1;	// to determine functions of each press
			if (copyPressed == 1) { // operations executed during first press
				int size1 = x.size();// To know whether a shape is selected or not
				
				strategy.chooseStrategy(modeFunctions, x, x1, y1); // strategy Class chooses which operation to perform according to the passed arguments (Copy operation)
				int size2 = x.size(); 
				tempX = x.get(x.size() - 1).p.getX2() - x.get(x.size() - 1).p.getX1(); //storing horizontal dimension
				tempY = x.get(x.size() - 1).p.getY2() - x.get(x.size() - 1).p.getY1();	// storing vertical dimension
				if (size2 == size1) // checks if no shape is selected 
					copySelected = false;// flag to make sure that the sketch is not randomly selected but a specified
											// shape is pressed
				else
					copySelected = true;
			}
			if (copySelected) {
				if (copyPressed == 2) { // operations executed during second press if a shape is selected
					Shape s = x.get(x.size() - 1);
					s.p.setX1(x1); // drawing the copied shape in the new determined position
					s.p.setY1(y1);
					s.p.setX2(x1 + tempX);
					s.p.setY2(y1 + tempY);

					copyPressed = 0;
					repaint();
				}
			} else {
				copyPressed = 0;
			}

		} else if (modeFunctions == 14) { // resizing operation
			index = fun.Select(x, x1, y1); // store the index of the selected shape
			if (index >= 0) {
				dh.fns.push(14);
				int temp1 = x.get(index).p.getX2(); // storing X2 and Y2 to use them during undo operation
				int temp2 = x.get(index).p.getY2();
				dh.helper.push(temp1); 	// pushing X2,Y2 and index in undo stack 
				dh.helper.push(temp2);
				dh.helper.push(index);	
			}

		} else if (modeFunctions == 10) { // moving operation
			index = fun.Select(x, x1, y1); // storing the index of the selected shape
			if (index >= 0) {
				dh.fns.push(10); // pushing integer mode into undo stack 
				dh.helper.push(x.get(index).p.getX1()); // storing coordinates,dimensions and index of the selected shape to use them in undo operation 
				dh.helper.push(x.get(index).p.getY1());
				dh.helper.push(x.get(index).p.getX2());
				dh.helper.push(x.get(index).p.getY2());
				dh.helper.push(index);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
