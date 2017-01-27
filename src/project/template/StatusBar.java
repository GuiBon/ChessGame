package project.template;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class StatusBar extends HBox{

	
	public StatusBar(){
		statusBarGp = new GridPane();
		resetButton = new Button("Reset");
		whitePlayerAlert = new Label("");
		blackPlayerAlert = new Label("");
		whitePlayerTimer = new Label("");
		blackPlayerTimer = new Label("");
		winner = new Label("");

//	    statusBarGp.setGridLinesVisible(true);
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(30);
		statusBarGp.getColumnConstraints().add(column);
		column = new ColumnConstraints();
		column.setPercentWidth(30);
		statusBarGp.getColumnConstraints().add(column);
		column = new ColumnConstraints();
		column.setPercentWidth(30);
		statusBarGp.getColumnConstraints().add(column);
		statusBarGp.setPrefSize(2000, 100);
		statusBarGp.getRowConstraints().add(new RowConstraints(70/2));
		statusBarGp.getRowConstraints().add(new RowConstraints(70/2));
		statusBarGp.addRow(0, whitePlayerAlert, resetButton, blackPlayerAlert);
		statusBarGp.addRow(1, whitePlayerTimer, winner, blackPlayerTimer);
		for (Node n: statusBarGp.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
			GridPane.setValignment(n, VPos.CENTER);
			if (n instanceof Label) {
				n.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold; -fx-opacity: 1.0;");
			}
		}
		statusBarGp.setVgap(10);
		statusBarGp.setHgap(10);
		statusBarGp.setPadding(new Insets(10, 10, 10, 10));
		
		statusBarGp.setStyle("-fx-background-color: burlyWood; -fx-effect: innershadow(gaussian, rgba(0,0,0,0.4), 75, 0.5, 0, 10);");
		statusBarGp.setSnapToPixel(false);		
		getChildren().add(statusBarGp);
	}
	
	public void resize(double width, double height){
		super.resize(width, height); 
		setWidth(width);
		setHeight(height);
	}
	
	private Button 	resetButton;
	public Label	whitePlayerAlert;
	public Label	blackPlayerAlert;
	public Label	whitePlayerTimer;
	public Label	blackPlayerTimer;
	public Label	winner;
	private GridPane statusBarGp;

	public Button getResetButton() {
		return resetButton;
	}

	public void setResetButton(Button resetButton) {
		this.resetButton = resetButton;
	}	
}