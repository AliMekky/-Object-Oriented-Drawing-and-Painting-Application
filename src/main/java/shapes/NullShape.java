package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class NullShape extends Shape{

	
	public NullShape() {
		super(Color.white,0,0,0,0);
	}
	
	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Shape clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(BufferedImage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawShape(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
