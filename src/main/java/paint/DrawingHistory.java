/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.ArrayList;
import java.util.Stack;
import shapes.Shape;

/**
 *
 * @author LENOVO
 */
public class DrawingHistory {
	private static DrawingHistory dh = null;// Singelton Design pattern
	Stack<Shape> undo = new Stack<Shape>();// Stores all shapes that are deleted,moved,resized or copied.
	Stack<Shape> clearAll = new Stack<Shape>();// Stores all the deleted shapes when user clicks clear.
	Stack<Integer> fns = new Stack<Integer>();// Stores all the functions done.
	Stack<Integer> helper = new Stack<Integer>();// Stores anything related to shapes like index and dimensions.
	Stack<Shape> redo = new Stack<Shape>();// Same as undo stack.
	Stack<Integer> fnsRedo = new Stack<Integer>();// Stores all "undone" functions.
	Stack<Integer> helperRedo = new Stack<Integer>();// Same as helper stack.

	private DrawingHistory() {
	}

	public static DrawingHistory getinstance() {
		if (dh == null)
			dh = new DrawingHistory();
		return dh;
	}

}
