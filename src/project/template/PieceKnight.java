package project.template;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PieceKnight extends Piece{

//	private int xPos;
//	private int yPos;
//	private int type;
	private Image image;
//	private ImageView imageView = new ImageView(); 
	
	public PieceKnight(int type, int xPos, int yPos) {
		super(type, xPos, yPos);
		name = "Knight";
		// TODO Auto-generated constructor stub
		if(type==1){
			image = new Image("file:src/ChessPiece/White_Knight.png");
			imageView.setImage(image);
			imageView.fitHeightProperty();
			imageView.fitWidthProperty();
	        imageView.setPreserveRatio(true);
	        imageView.setSmooth(true);
	        imageView.setCache(true);
		}else{
			image = new Image("file:src/ChessPiece/Black_Knight.png");
			imageView.setImage(image);
			imageView.fitHeightProperty();
			imageView.fitWidthProperty();
	        imageView.setPreserveRatio(true);
	        imageView.setSmooth(true);
	        imageView.setCache(true);
		}
	}
	
	@Override
	public ImageView getImage() {
		return (imageView);
	}
	
	@Override
	public void SelectPiece(ChessBoard chessBoard) {
		int x = 0;
		chessBoard.colorSquare(this.xPos, this.yPos, true);
		if (chessBoard.checkState && !this.isASavior)
			return;
		if (gameLogic.verticalProtection(chessBoard, this.xPos, this.yPos, this.type) || gameLogic.horizontalProtection(chessBoard, this.xPos, this.yPos, this.type) || 
			gameLogic.slashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type) || gameLogic.backslashDiagonalProtection(chessBoard, this.xPos, this.yPos, this.type))
			return;
		for (int y = -2; y <= 2; y++)
		{
			if (y != 0)
			{
				x = y % 2 == 0 ? 1 : 2;
				if (this.yPos + y >= 0 && this.yPos + y < chessBoard.getBoardHeight() && this.xPos - x >= 0 && this.xPos - x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(this.xPos - x, this.yPos + y) != this.type)
				{
					if (chessBoard.checkState)
					{
						if (gameLogic.isThisProtecting(chessBoard, this.xPos - x, this.yPos + y, this.type))
							chessBoard.colorSquare(this.xPos - x, this.yPos + y, false);
					}
					else
						chessBoard.colorSquare(this.xPos - x, this.yPos + y, false);
				}
				if (this.yPos + y >= 0 && this.yPos + y < chessBoard.getBoardHeight() && this.xPos + x >= 0 && this.xPos + x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(this.xPos + x, this.yPos + y) != this.type)
				{
					if (chessBoard.checkState)
					{
						if (gameLogic.isThisProtecting(chessBoard, this.xPos + x, this.yPos + y, this.type))
							chessBoard.colorSquare(this.xPos + x, this.yPos + y, false);
					}
					else
						chessBoard.colorSquare(this.xPos + x, this.yPos + y, false);
				}
			}
		}
	}	
}
