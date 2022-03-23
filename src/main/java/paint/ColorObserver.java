package paint;

public class ColorObserver implements Observer{
    ColorChooser observed;

    public ColorObserver(ColorChooser observed) {
        this.observed = observed;
    }
    @Override
    public void execute(){
        
        Sketch.currentColor = this.observed.jColorChooser1.getColor();
    }
}