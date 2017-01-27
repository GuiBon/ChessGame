package project.template;

import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Timer {
	public int whiteTimer = 900;
	public int blackTimer = 900;
	public int playerTurn = 0;
	public boolean timeIsOver = false;
	private ChessBoard chessboard;
	
	public Timer(ChessBoard _chessboard) {
		chessboard = _chessboard;
	}
	
	public Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (playerTurn == 1 && !timeIsOver && !chessboard.checkmate && !chessboard.stalemate)
			{
				whiteTimer -= 1;
				chessboard.getStatusBar().whitePlayerTimer.setText("White timer: " + TimeUnit.SECONDS.toMinutes(whiteTimer) + ":" + (whiteTimer % 60));
			}
			else if (playerTurn == 2 && !timeIsOver)
			{
				blackTimer -= 1;
				chessboard.getStatusBar().blackPlayerTimer.setText("Black timer: " + TimeUnit.SECONDS.toMinutes(blackTimer) + ":" + (blackTimer % 60));
			}
			if (!timeIsOver && (whiteTimer == 0 || blackTimer == 0))
			{
				chessboard.timerOver(playerTurn);
				timeIsOver = true;
			}
		}
	}));
}
