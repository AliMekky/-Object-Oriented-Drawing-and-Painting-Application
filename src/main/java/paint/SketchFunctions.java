package paint;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import shapes.Line;
import shapes.NullShape;

import java.awt.Shape;
import java.util.Stack;
//import shapes.Shape;

import javax.swing.JOptionPane;

public class SketchFunctions {
	DrawingHistory dh = DrawingHistory.getinstance();//Using a singelton instance of DrawingHistory.

	public SketchFunctions() {		
	}

	public int Select(ArrayList<shapes.Shape> x, int x1, int y1) {//Returns index of the shape that is selected.if no shape is selected returns -1.
		int i;
		for (i = x.size() - 1; i >= 0; i--) {
			
			if (x.get(i).contains(x1, y1)) {
				return i;
			}
		}

		return -1;

	}

	public void clear(ArrayList<shapes.Shape> x) {//Deletes all shapes from the sketch.

		int i = x.size() - 1;
		if(x.size() != 0)
			dh.clearAll.push(new NullShape()); // add new null shape to separate between shapes of two different clear operations
		while (i != -1) {
			dh.clearAll.push(x.get(i));//Saving all the shapes in a stack in case the user clicks undo.
			i--;
		}
		
		x.clear();
		dh.fns.push(20);//Saves the integer representing the function clear in a stack in case the user clicks undo.

	}

	public void Copy(ArrayList<shapes.Shape> x, int x1, int y1) {//Adds a copy of the selected shape to the arraylist.

		int i = Select(x, x1, y1);
		if (i >= 0) {
			System.out.println(i);
			dh.fns.push(11);//Saves the integer representing the function copy in a stack in case the user clicks undo.

			x.add(x.get(i).clone());

		}
	}

	void Delete(ArrayList<shapes.Shape> x, int x1, int y1) {//Deletes the selected shape from the array list.
		int i = Select(x, x1, y1);
		if (i >= 0) {
			dh.undo.push((shapes.Shape) x.get(i));//Saves the deleted shape in a stack in case a user clicks undo.
			dh.fns.push(13);//Saves the integer representing the function delete in a stack in case the user clicks undo.
			x.remove(i);//Removes shape from the array list.
			dh.helper.push(i);//Saves the index of the deleted shape so if the user clicks undo, the shape is inserted in the same index.
		}

	}

	public void runUndo(Sketch sketch1) {
		if (!sketch1.dh.fns.empty() && sketch1.dh.fns.peek() != 20) {//Checks if functions stack isn't empty and clear button isn't pressed.
			if (sketch1.dh.fns.peek() == 13 && !sketch1.dh.undo.empty()) {//Checks if the last function done is DELETE and if there is a shape in the undo stack.
				dh.fns.pop();//pops the DELETE function from the functions stack.
				shapes.Shape s = sketch1.dh.undo.pop();//pops the last shape deleted from the stack.
				int item = dh.helper.pop();//pops the index of the last deleted shape in the array list. 
				sketch1.x.add(item, s);//Adds the last deleted shape in its index in the array list.
				sketch1.repaint();//repaints all shapes in the array list.
				sketch1.dh.fnsRedo.push(13);//pushes the DELETE function in the functions redo stack.
				sketch1.dh.helperRedo.push(item);// pushes the index of the shape in the helper redo stack.
			} else if (sketch1.dh.fns.peek() == 0 || sketch1.dh.fns.peek() == 11) {//Checks if the last function done is ADD or CLONE.
				int item = sketch1.x.size() - 1; // index of the last added shape in the array list.
				sketch1.dh.fnsRedo.push(0);//pushes the ADD function in the functions Redo stack.
				sketch1.dh.helperRedo.push(item);//pushes the index of the added shape in the helper Redo stack.
				sketch1.dh.redo.push(sketch1.x.get(item));//pushes the added shape in the redo stack.
				sketch1.x.remove(sketch1.x.size() - 1);//Removes the shape from the arraylist.
				dh.fns.pop();//pops the ADD or CLONE from the functions stack.
				sketch1.repaint();
			} else if (sketch1.dh.fns.peek() == 14) {//Checks if the last function is RESIZE.

				int item = dh.helper.pop();//pops the index of the resized shape in the arraylist from the helper stack.
				int y2 = sketch1.x.get(item).p.getY2();//resized dimensions.
				int x2 = sketch1.x.get(item).p.getX2();

				sketch1.x.get(item).p.setY2(sketch1.dh.helper.pop());//Returns the shape to its original dimensions before resize.
				sketch1.x.get(item).p.setX2(sketch1.dh.helper.pop());
				sketch1.dh.helperRedo.push(y2);//pushes the resized dimensions to the helper redo stack.
				sketch1.dh.helperRedo.push(x2);
				sketch1.dh.helperRedo.push(item);//pushes the index of the resized shape to helper redo stack.
				sketch1.dh.fnsRedo.push(14);//pushes the RESIZE function in the functions redo stack.

				dh.fns.pop();//pops the RESIZE function from the functions stack.
				sketch1.repaint();

			} else if (sketch1.dh.fns.peek() == 10) {//Checks if the last function done was MOVE.
				int item = dh.helper.pop();//index of the moved shape.
				int y2 = sketch1.x.get(item).p.getY2();//Dimensions after moving.
				int x2 = sketch1.x.get(item).p.getX2();
				int y1 = sketch1.x.get(item).p.getY1();
				int x1 = sketch1.x.get(item).p.getX1();
				sketch1.x.get(item).p.setY2(dh.helper.pop());//Returns the shape to its original coordinates before moving.
				sketch1.x.get(item).p.setX2(dh.helper.pop());
				sketch1.x.get(item).p.setY1(dh.helper.pop());
				sketch1.x.get(item).p.setX1(dh.helper.pop());
				dh.fns.pop();//Pops the MOVE function from the functions stack.
				dh.fnsRedo.push(10);//pushes the MOVE function in the functions redo stack.
				dh.helperRedo.push(y2);//pushes the new dimensions after moving to the helper redo stack.
				dh.helperRedo.push(x2);
				dh.helperRedo.push(y1);
				dh.helperRedo.push(x1);
				dh.helperRedo.push(item);//pushes the index to the helper redo stack.

				sketch1.repaint();
			}

		} else if (!sketch1.dh.fns.empty() && dh.fns.peek() == 20) {//checks if the stack is not empty and the clear button was pressed.
			int size = dh.clearAll.size();//Gets the size of the stack containing all deleted shapes.
//			for (int i = 0; i < size; i++)
//				sketch1.x.add(dh.clearAll.pop());//Adds the shapes deleted back to the the arraylist
			while(!dh.clearAll.empty()) { // adding all elements in the stack into the arraylist
				if(dh.clearAll.peek() instanceof NullShape) // if a null shape is found break the loop (indicates the shapes cleared during this operation)
					break;
				sketch1.x.add(dh.clearAll.pop());
			}
			if(dh.clearAll.size() != 0)
				dh.clearAll.pop(); // pop the null shape 
			dh.fns.pop();//Pops the CLEAR function from the functions stack.
			dh.fnsRedo.push(20);//Pushes the CLEAR function to the functions redo stack.
			sketch1.repaint();
		}

	}

	public void runRedo(Sketch sketch1) {
		if (!sketch1.dh.fnsRedo.empty()) {//Checks if the functions redo stack is not empty.
			if (sketch1.dh.fnsRedo.peek() == 13) {//Checks if the last element in the functions redo stack was the DELETE function.

				int item = sketch1.dh.helperRedo.pop();//Pops the index of the shape to be DELETED.
				sketch1.dh.undo.push(sketch1.x.get(item));//Pushes the shape to the undo stack.
				sketch1.dh.fnsRedo.pop();//Pops the DELETE function from the functions redo stack
				sketch1.dh.fns.push(13);//Pushes in the functions stack the DELETE function.
				sketch1.dh.helper.push(item);//Pushes the index of the shape to the helper stack.
				sketch1.x.remove(item);//Deletes the shape with the specified index.

				sketch1.repaint();
			}

			else if (sketch1.dh.fnsRedo.peek() == 0) {//Checks if the last element in the functions redo stack was the ADD function.
				int item = sketch1.dh.helperRedo.pop(); // pops the index of the shape to be added.
				sketch1.x.add(item, sketch1.dh.redo.pop());// Adds the shape to the array list in the specified index.
				sketch1.dh.fnsRedo.pop(); // pops the ADD function from the functions redo stack.
				sketch1.dh.fns.push(0);//pushes the ADD function to the functions stack.
				sketch1.repaint();

			}

			else if (sketch1.dh.fnsRedo.peek() == 14) {//Checks if the last function done was RESIZE.
				int item = sketch1.dh.helperRedo.pop();//pops the index of the shape to be resized.
				int x2 = sketch1.x.get(item).p.getX2();//Gets the original dimensions of the shape.
				int y2 = sketch1.x.get(item).p.getY2();
				sketch1.x.get(item).p.setX2(sketch1.dh.helperRedo.pop());//Resizes the shape to the new dimensions.
				sketch1.x.get(item).p.setY2(sketch1.dh.helperRedo.pop());
				sketch1.dh.fnsRedo.pop();//pops the RESIZE functions from functions redo stack.
				sketch1.dh.fns.push(14);//pushes the RESIZE function to the functions stack.
				sketch1.dh.helper.push(x2);//pushes the original dimensions to the helper stack.
				sketch1.dh.helper.push(y2);
				sketch1.dh.helper.push(item);//pushes the index to the helper stack.
				sketch1.repaint();
			}

			else if (sketch1.dh.fnsRedo.peek() == 10) {//Checks if the last function done was MOVE.
				int item = sketch1.dh.helperRedo.pop();//pops the index of the shape to be moved
				int x1 = sketch1.x.get(item).p.getX1(); // gets the original coordinates of the shape.
				int y1 = sketch1.x.get(item).p.getY1();
				int x2 = sketch1.x.get(item).p.getX2();
				int y2 = sketch1.x.get(item).p.getY2();
				sketch1.x.get(item).p.setX1(sketch1.dh.helperRedo.pop());//Moves the shape to the new position.
				sketch1.x.get(item).p.setY1(sketch1.dh.helperRedo.pop());
				sketch1.x.get(item).p.setX2(sketch1.dh.helperRedo.pop());
				sketch1.x.get(item).p.setY2(sketch1.dh.helperRedo.pop());
				sketch1.dh.fnsRedo.pop();//pops the MOVE function from the functions redo stack.
				sketch1.dh.fns.push(10);//pushes the MOVE function to the functions stack.
				sketch1.dh.helper.push(x1);//pushes the original coordinates to the helper stack.
				sketch1.dh.helper.push(y1);
				sketch1.dh.helper.push(x2);
				sketch1.dh.helper.push(y2);
				sketch1.dh.helper.push(item);//pushes the index to the helper stack.
				sketch1.repaint();

			}

			else if (sketch1.dh.fnsRedo.peek() == 20) {//Checks if the last function done was CLEAR.
				JOptionPane.showMessageDialog(null, "Cannot redo Clearing shapes, you can use the clear button.");
			}

		}
	}

}
