package project.template;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

public class Window extends Group{

	
	// constructor for the class
		public Window(int i) {
			// Make a new Rectangle and Translate, add the Translate to the Rectangle, add the Rectagle to the Group
			pos = new Translate();
			r = new Rectangle();
			r.getTransforms().add(pos);
			if(i==0)
			r.setFill(Color.GREY);
			else
			r.setFill(Color.WHITE);
			
			getChildren().add(r);
		}

		// overridden version of the resize method
		@Override
		public void resize(double width, double height) {
			// call the super class method and update the height and the width of the rectangle representing the window 
			super.resize(width, height);
			r.setHeight(height);
			r.setWidth(width);
		}

		// overridden version of the relocate method
		@Override
		public void relocate(double x, double y) {
			// call the superclass method and update the relevant transform
			super.relocate(x, y);
			pos.setX(x);
			pos.setY(y);
		}
		
		public void highlightWindow(Color color) {
			r.setStrokeType(StrokeType.INSIDE);
			r.setStrokeWidth(4);
			r.setStroke(color);
			if (color == Color.GREEN)
				isHighlighted = true;
		}

		public void unhighlight() {
			r.setStroke(null);
			isHighlighted = false;
		}
		
		public boolean isHighlighted() {
			return (isHighlighted);
		}
		
		public Rectangle getRectangle() {
			return (r);
		}
		
		// private fields of the class
		private Rectangle r;
		private Translate pos; 		//translate to set the position of this window
		private boolean isHighlighted = false;
}
