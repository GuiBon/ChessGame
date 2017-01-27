package project.template;

//Chess  application

//imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/* TO DO:
 * 1) Timer sous le board avec les textes "Check" || "Checkmate"	
 * 2) Bouton pour conceed / reset le jeu 							(Method reset OK)
*/

//class definition
public class ChessApplication extends Application {
	// overridden init method
	@Override
	public void init() {
		// initialize the layout, create a CustomControl and it to the layout
		sp_mainlayout = new StackPane(); 
		cc_custom = new CustomControl();
		sp_mainlayout.getChildren().add(cc_custom);
	}
	
	// overridden start method
	@Override
	public void start(Stage primaryStage) {
		// set the title and scene, and show the stage
		primaryStage.setTitle("Chess game");
		primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(300);
		primaryStage.show();
	}

	// overridden stop method
	@Override
	public void stop() {
	}
	
	// entry point into our program to launch our JavaFX application
	public static void main(String[] args) {
			launch(args);
	}
	
	// private fields for this class
	private StackPane sp_mainlayout;	//layout which allows items to be positioned on top of each other
	private CustomControl cc_custom;	//control which has a board and detects mouse and keyboard events
}