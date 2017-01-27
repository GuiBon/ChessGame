package project.template;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

//class declaration - abstract because we will not want to create a Piece object but we would
//like to specify the private fields that all pieces should have in addition to their behaviours
public abstract class Piece extends Group{

	// Piece can be either white (1) or black (2)
	protected int type;
	// Position of the piece on the board
	protected int xPos;
	protected int yPos;
	// Name of the piece
	protected String name;
	// ImageView
	protected ImageView imageView = new ImageView();
	// Position 
	protected Translate pos;
	// GameLogic
	protected GameLogic gameLogic = new GameLogic();
	// True if it's the first time that the Piece is used.
	protected boolean isFirstTime;
	// Variable to know if the piece can move in a check situation
	protected boolean isASavior = false;
	
	public Piece(int type, int xPos, int yPos) {
		this.type = type;
		this.xPos = xPos;
		this.yPos = yPos;
		isFirstTime = true;
	}
	
	// Select method: When a piece is selected by a first click
	// Highlight all the available position where the piece can go
	public void SelectPiece(ChessBoard board) {
	}
	
	// Move method: When a piece is already selected and that the player click on a highlighted position
	// Change the position of the piece and update the board
	public void MovePiece(ChessBoard chessBoard, int x, int y) {
		chessBoard.setBoard(this.xPos, this.yPos, 0);
		chessBoard.setPiece(this.xPos, this.yPos, null);
		if (!chessBoard.checkState && this.canCastle(chessBoard)!=0){
			if(this.canCastle(chessBoard)==1){
				chessBoard.setBoard(x-1, y, this.type);
				chessBoard.setPiece(x-1, y, this);
				this.xPos = x - 1;
				chessBoard.setBoard(5, y, chessBoard.getPiece(7, y).type);
				chessBoard.setPiece(5, y, chessBoard.getPiece(7, y));
				chessBoard.getPiece(7, y).xPos = 7;
				chessBoard.setBoard(7, y, 0);
				chessBoard.setPiece(7, y, null);
			}
			if(this.canCastle(chessBoard)==2){
				chessBoard.setBoard(x+2, y, this.type);
				chessBoard.setPiece(x+2, y, this);
				this.xPos = x + 2;
				chessBoard.setBoard(3, y, chessBoard.getPiece(0, y).type);
				chessBoard.setPiece(3, y, chessBoard.getPiece(0, y)); 
				chessBoard.getPiece(3, y).xPos = 3;
				chessBoard.setBoard(0, y, 0);
				chessBoard.setPiece(0, y, null);
			}
			if(this.canCastle(chessBoard)==3){
				chessBoard.setBoard(x-1, y, this.type);
				chessBoard.setPiece(x-1, y, this);
				this.xPos = x - 1;
				chessBoard.setBoard(5, y, chessBoard.getPiece(7, y).type);
				chessBoard.setPiece(5, y, chessBoard.getPiece(7, y)); 
				chessBoard.getPiece(5, y).xPos = 5;
				chessBoard.setBoard(7, y, 0);
				chessBoard.setPiece(7, y, null);
			}
			if(this.canCastle(chessBoard)==4){
				chessBoard.setBoard(x+2, y, this.type);
				chessBoard.setPiece(x+2, y, this);
				this.xPos = x + 2;
				chessBoard.setBoard(3, y, chessBoard.getPiece(0, y).type);
				chessBoard.setPiece(3, y, chessBoard.getPiece(0, y)); 
				chessBoard.getPiece(3, y).xPos = 3;
				chessBoard.setBoard(0, y, 0);
				chessBoard.setPiece(0, y, null);
			}
		}
		else{
			this.xPos = x;
			this.yPos = y;
			if (chessBoard.getPiece(x, y) != null)
				chessBoard.getPiece(x, y).capture(chessBoard);
			chessBoard.setBoard(x, y, this.type);
			chessBoard.setPiece(x, y, this);
			if (this.name == "Pawn" && ((this.type == 1 && this.yPos == 0) || (this.type == 2 && this.yPos == 7)))
			{
				chessBoard.createPromotePiece(this);
				if (this.type == 1)
					chessBoard.playerOnePawn--;
				else
					chessBoard.playerTwoPawn--;
			}
		}
	}
	
	// Return the image of the piece
	public ImageView getImage() {
		return (imageView);
	}
	
	public void centerImage() {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
	
	// Capture method: When a piece is captured by another one
	public void capture(ChessBoard chessBoard) {
		if (this.type == 1)
		{
			if (this.name == "Rook")
				chessBoard.playerOneRook--;
			else if (this.name == "Knight")
				chessBoard.playerOneKnight--;
			else if (this.name == "Queen")
				chessBoard.playerOneQueen--;
			else if (this.name == "Pawn")
				chessBoard.playerOnePawn--;
			else if (this.name == "Bishop" && (this.xPos + this.yPos) % 2 != 0)
				chessBoard.playerOneBishopDarkSquare--;
			else if (this.name == "Bishop" && (this.xPos + this.yPos) % 2 == 0)
				chessBoard.playerOneBishopLightSquare--;
		}
		else
		{
			if (this.name == "Rook")
				chessBoard.playerTwoRook--;
			else if (this.name == "Knight")
				chessBoard.playerTwoKnight--;
			else if (this.name == "Queen")
				chessBoard.playerTwoQueen--;
			else if (this.name == "Pawn")
				chessBoard.playerTwoPawn--;
			else if (this.name == "Bishop" && (this.xPos + this.yPos) % 2 == 0)
				chessBoard.playerTwoBishopLightSquare--;
			else if (this.name == "Bishop" && (this.xPos + this.yPos) % 2 != 0)
				chessBoard.playerTwoBishopDarkSquare--;
		}
		chessBoard.getChildren().remove(this.getImage());
	}
	
	public int canCastle(ChessBoard chessBoard){
		return 0;
	}

	public void resize(double width, double height) {
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
	}

	// overridden version of the relocate method
	public void relocate(double x, double y) {
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);	
		centerImage();
	}
	
	public void resetPiece()
	{
		this.isFirstTime = true;
		this.isASavior = false;
	}

	public boolean isFirstTime() {
		return isFirstTime;
	}

	public void setFirstTime(boolean isFirstTime) {
		this.isFirstTime = isFirstTime;
	}
	
	public int getX(){
		return this.xPos;
	}
	
	public int getY(){
		return this.yPos;
	}
	

}
