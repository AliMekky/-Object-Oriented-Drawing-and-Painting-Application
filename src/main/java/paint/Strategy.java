package paint;
import java.util.ArrayList;
import shapes.Shape;


public class Strategy {

    SketchFunctions fun;

    

    Strategy(SketchFunctions fun) {
        this.fun = fun;
    }

    public void chooseStrategy(int mode, ArrayList<Shape> x, int x1, int y1) {
        if (mode == 13) {
            fun.Delete(x, x1, y1);
        } else if (mode == 11) {
            fun.Copy(x, x1, y1);
        }
    }

    public void chooseStrategy(ArrayList<Shape> x) {
        fun.clear(x);
    }

    public void chooseStrategy(int mode, Sketch sketch1) {
        if (mode == 15) {
            fun.runUndo(sketch1);
        }
        if (mode == 12) {
            fun.runRedo(sketch1);
        }
    }

}
