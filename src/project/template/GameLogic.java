package project.template;

import java.util.Iterator;

public class GameLogic {
	
	//method to detect stalemate
	private boolean isOneKingStalemate(ChessBoard chessBoard, Piece king, int type)
	{
		int nbPiece = 0;
		boolean stalemate = true;

		// A Player has only 1 king left, which is not in check position and can't move
		for (int y = 0; y < chessBoard.getBoardHeight(); y++)
		{
			for (int x = 0; x < chessBoard.getBoardWidth(); x++)
			{
				if (chessBoard.getBoardPosition(x, y) == type)
					nbPiece++;
			}
		}
		if (nbPiece == 1)
		{
			for (int y = king.yPos - 1; y <= king.yPos + 1; y++)
			{
				for (int x = king.xPos - 1; x <= king.xPos + 1; x++)
				{
					if(y >= 0 && y < chessBoard.getBoardHeight() && x >= 0 && x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(x, y) != type)
					{
						if (!isCheck(chessBoard, x, y, type, true))
						{
							stalemate = false;
							break;
						}
					}
				}
				if (!stalemate)
					break;
			}
		}
		else
			stalemate = false;
		return (stalemate);
	}
	
	public boolean isLimitPieceStalemate(ChessBoard chessBoard)
	{
		// Both Player have less then:
		// A king and a Queen
		// A king and a Rook
		// A king and two knight
		// A king, a bishop and a knight
		// A king and two bishop (one light square, one dark square)
		// A king and at least a pawn
		if (chessBoard.playerOneQueen != 0 || chessBoard.playerTwoQueen != 0)
			return (false);
		else if (chessBoard.playerOneRook != 0 || chessBoard.playerTwoRook != 0)
			return (false);
		else if (chessBoard.playerOneKnight > 1 || chessBoard.playerTwoKnight > 1)
			return (false);
		else if (((chessBoard.playerOneBishopDarkSquare != 0 || chessBoard.playerOneBishopLightSquare != 0) && chessBoard.playerOneKnight != 0) ||
				((chessBoard.playerTwoBishopDarkSquare != 0 || chessBoard.playerTwoBishopLightSquare != 0) && chessBoard.playerTwoKnight != 0))
			return (false);
		else if ((chessBoard.playerOneBishopDarkSquare != 0 && chessBoard.playerOneBishopLightSquare != 0) || (chessBoard.playerTwoBishopDarkSquare != 0 && chessBoard.playerTwoBishopLightSquare != 0))
			return (false);
		else if (chessBoard.playerOnePawn > 1 || chessBoard.playerTwoPawn > 1)
			return (false);
		return (true);
	}
	
	public boolean isStalemate(ChessBoard chessBoard, Piece king, int type)
	{
		if (isOneKingStalemate(chessBoard, king, type) || isLimitPieceStalemate(chessBoard))
		{
			chessBoard.stalemate = true;
			return (true);
		}
		return (false);
	}
	
	// Method to check if a piece is protecting the king from a check
	public boolean verticalProtection(ChessBoard chessBoard, int xPos, int yPos, int type)
	{
		int y = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;

		// King on the Vertical Up
		for (y = yPos - 1; y >= 0; y--)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type && chessBoard.getPiece(xPos, y).name == "King")
			{
				for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++)
				{
					if (chessBoard.getBoardPosition(xPos, y) == type)
						break;
					else if (chessBoard.getBoardPosition(xPos, y) == enemyType)
					{
						if (chessBoard.getPiece(xPos,  y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook")
							return (true);
						else
							break;
					}
				}
				break;
			}
			else if (chessBoard.getBoardPosition(xPos, y) != 0)
				break;
		}		
		// King on the Vertical Down
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type && chessBoard.getPiece(xPos, y).name == "King")
			{
				for (y = yPos - 1; y >= 0; y--)
				{
					if (chessBoard.getBoardPosition(xPos, y) == type)
						break;
					else if (chessBoard.getBoardPosition(xPos,  y) == enemyType)
					{
						if (chessBoard.getPiece(xPos, y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook")
							return (true);
						else
							break;
					}
				}
				break;				
			}
			else if (chessBoard.getBoardPosition(xPos, y) != 0)
				break;
		}
		return (false);
	}
	
	public boolean horizontalProtection(ChessBoard chessBoard, int xPos, int yPos, int type)
	{
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;

		// King on the Horizontal Left
		for (x = xPos - 1; x >= 0; x--)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type && chessBoard.getPiece(x, yPos).name == "King")
			{
				for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++)
				{
					if (chessBoard.getBoardPosition(x, yPos) == type)
						break;
					else if (chessBoard.getBoardPosition(x, yPos) == enemyType)
					{
						if (chessBoard.getPiece(x,  yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook")
							return (true);
						else
							break;
					}
				}
				break;
			}
			else if (chessBoard.getBoardPosition(x, yPos) != 0)
				break;
		}
		// King on the Horizontal Right
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type && chessBoard.getPiece(x, yPos).name == "King")
			{
				for (x = xPos - 1; x >= 0; x--)
				{
					if (chessBoard.getBoardPosition(x, yPos) == type)
						break;
					else if (chessBoard.getBoardPosition(x,  yPos) == enemyType)
					{
						if (chessBoard.getPiece(x, yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook")
							return (true);
						else
							break;
					}
				}
				break;				
			}
			else if (chessBoard.getBoardPosition(x, yPos) != 0)
				break;
		}
		return (false);
	}
	
	public boolean slashDiagonalProtection(ChessBoard chessBoard, int xPos, int yPos, int type)
	{
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;

		// King on the Diagonal / Up
		int y = yPos - 1;
		for (int x = xPos + 1; x < chessBoard.getBoardWidth() && y >= 0; x++, y--)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name == "King")
			{
				y = yPos + 1;
				for (x = xPos - 1; x >= 0 && y < chessBoard.getBoardHeight(); x--, y++)
				{
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType)
					{
						if (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop")
							return (true);
						else
							break;
					}
				}
				break;
			}
			else if (chessBoard.getBoardPosition(x, y) != 0)
				break;
		}
		// King on the Diagonal / Down
		y = yPos + 1;
		for (int x = xPos - 1; x >= 0 && y < chessBoard.getBoardHeight(); x--, y++)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name == "King")
			{
				y = yPos - 1;
				for (x = xPos + 1; x < chessBoard.getBoardWidth() && y >= 0; x++, y--)
				{
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType)
					{
						if (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop")
							return (true);
						else
							break;
					}
				}
				break;
			}
			else if (chessBoard.getBoardPosition(x, y) != 0)
				break;			
		}
		return (false);
	}
	
	public boolean backslashDiagonalProtection(ChessBoard chessBoard, int xPos, int yPos, int type)
	{
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;

		// King on the Diagonal \ Up
		int y = yPos - 1;
		for(int x = xPos - 1; x >= 0 && y >= 0; x--, y--)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name == "King")
			{
				y = yPos + 1;
				for(x = xPos + 1; x < chessBoard.getBoardWidth() && y < chessBoard.getBoardHeight(); x++, y++)
				{
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType)
					{
						if (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop")
							return (true);
						else
							break;
					}
				}
				break;
			}
			else if (chessBoard.getBoardPosition(x, y) != 0)
				break;
		}
		// King on the Diagonal \ Down
		y = yPos + 1;
		for(int x = xPos + 1; x < chessBoard.getBoardWidth() && y < chessBoard.getBoardHeight(); x++, y++)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name == "King")
			{
				y = yPos - 1;
				for(x = xPos - 1; x >= 0 && y >= 0; x--, y--)
				{
					if (chessBoard.getBoardPosition(x, y) == type)
						break;
					else if (chessBoard.getBoardPosition(x, y) == enemyType)
					{
						if (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop")
							return (true);
						else
							break;
					}
				}
				break;
			}
			else if (chessBoard.getBoardPosition(x, y) != 0)
				break;
		}		
		return (false);
	}
		
	// Method to check check
	public boolean isCheck(ChessBoard chessBoard, int xPos, int yPos, int type, boolean kingCanCapture) {
		int y = 0;
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		
		// Horizontal Left
		for (x = xPos - 1; x >= 0; x--)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type && chessBoard.getPiece(x, yPos).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType)
			{
				if (x == xPos - 1 && chessBoard.getPiece(x, yPos) != null && kingCanCapture && chessBoard.getPiece(x, yPos).name == "King")
					return (true);
				else if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook"))
					return (true);
				else
					break;
			}
		}
		// Horizontal Right
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type && chessBoard.getPiece(x, yPos).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType)
			{
				if (x == xPos + 1 && chessBoard.getPiece(x, yPos) != null && kingCanCapture && chessBoard.getPiece(x, yPos).name == "King")
					return (true);
				else if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook"))
					return (true);
				else
					break;
			}
		}
		// Vertical Up
		for (y = yPos - 1; y >= 0; y--)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type && chessBoard.getPiece(xPos, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType)
			{
				if (y == yPos - 1 && chessBoard.getPiece(xPos, y) != null && kingCanCapture && chessBoard.getPiece(xPos, y).name == "King")
					return (true);
				else if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook"))
					return (true);
				else
					break;
			}
		}
		// Vertical Down
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type && chessBoard.getPiece(xPos, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType)
			{
				if (y == yPos + 1 && chessBoard.getPiece(xPos, y) != null && kingCanCapture && chessBoard.getPiece(xPos, y).name == "King")
					return (true);
				else if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook"))
					return (true);
				else
					break;
			}
		}
		// Diagonal 1 \ Up
		for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && ((kingCanCapture && chessBoard.getPiece(x, y).name == "King") || (type == 1 && chessBoard.getPiece(x, y).name == "Pawn")))
					return (true);
				else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					return (true);
				else
					break;
			}
		}
		// Diagonal 1 \ Down
		for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight() && x < chessBoard.getBoardWidth(); y++, x++)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && ((kingCanCapture && chessBoard.getPiece(x, y).name == "King") || (type == 2 && chessBoard.getPiece(x, y).name == "Pawn")))
					return (true);
				else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					return (true);
				else
					break;
			}
		}
		// Diagonal 2 / Up
		for (y = yPos - 1, x = xPos + 1; y >= 0 && x < chessBoard.getBoardWidth(); y--, x++)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && ((kingCanCapture && chessBoard.getPiece(x, y).name == "King") || (type == 1 && chessBoard.getPiece(x, y).name == "Pawn")))
					return (true);
				else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					return (true);
				else
					break;
			}
		}
		// Diagonal 2 / Down
		for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight() && x >= 0; y++, x--)
		{
			if (chessBoard.getBoardPosition(x, y) == type && chessBoard.getPiece(x, y).name != "King")
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && ((kingCanCapture && chessBoard.getPiece(x, y).name == "King") || (type == 2 && chessBoard.getPiece(x, y).name == "Pawn")))
					return (true);
				else if (chessBoard.getBoardPosition(x, y) != 0 && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					return (true);
				else
					break;
			}
		}		
		// Knight
		for (y = -2; y <= 2; y++)
		{
			if (y != 0)
			{
				x = y % 2 == 0 ? 1 : 2;
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos - x >= 0 && xPos - x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos - x, yPos + y) != type && chessBoard.getBoardPosition(xPos - x, yPos + y) != 0)
				{
					if (chessBoard.getPiece(xPos - x, yPos + y) != null && chessBoard.getPiece(xPos - x, yPos + y).name == "Knight")
						return (true);
				}
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos + x >= 0 && xPos + x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos + x, yPos + y) != type && chessBoard.getBoardPosition(xPos + x, yPos + y) != 0)
				{
					if (chessBoard.getPiece(xPos + x, yPos + y) != null && chessBoard.getPiece(xPos + x, yPos + y).name == "Knight")
						return (true);
				}
			}
		}
		return (false);
	}
	
	// Method to find all the piece that create a check
	public void findAllCheckPieces(ChessBoard chessBoard, int xPos, int yPos, int type) {
		int y = 0;
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		
		// Horizontal Left
		for (x = xPos - 1; x >= 0; x--)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType)
			{
				if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		// Horizontal Right
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType)
			{
				if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		// Vertical Up
		for (y = yPos - 1; y >= 0; y--)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType)
			{
				if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		// Vertical Down
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType)
			{
				if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.checkPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		// Diagonal 1 \ Up
		for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 1 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		// Diagonal 1 \ Down
		for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight() && x < chessBoard.getBoardWidth(); y++, x++)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 2 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		// Diagonal 2 / Up
		for (y = yPos - 1, x = xPos + 1; y >= 0 && x < chessBoard.getBoardWidth(); y--, x++)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 1 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		// Diagonal 2 / Down
		for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight() && x >= 0; y++, x--)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 2 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0 && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.checkPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}		
		// Knight
		for (y = -2; y <= 2; y++)
		{
			if (y != 0)
			{
				x = y % 2 == 0 ? 1 : 2;
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos - x >= 0 && xPos - x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos - x, yPos + y) != type && chessBoard.getBoardPosition(xPos - x, yPos + y) != 0)
				{
					if (chessBoard.getPiece(xPos - x, yPos + y) != null && chessBoard.getPiece(xPos - x, yPos + y).name == "Knight")
						chessBoard.checkPieces.add(chessBoard.getPiece(xPos - x, yPos + y));
				}
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos + x >= 0 && xPos + x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos + x, yPos + y) != type && chessBoard.getBoardPosition(xPos + x, yPos + y) != 0)
				{
					if (chessBoard.getPiece(xPos + x, yPos + y) != null && chessBoard.getPiece(xPos + x, yPos + y).name == "Knight")
						chessBoard.checkPieces.add(chessBoard.getPiece(xPos + x, yPos + y));
				}
			}
		}
	}
	
	// Method to find all the piece that can save the king from a checkmate
	public void findAllSaviorPieces(ChessBoard chessBoard, int xPos, int yPos, int type, boolean protect) {
		int y = 0;
		int x = 0;
		int enemyType = 0;
		if (type == 1)
			enemyType = 2;
		else
			enemyType = 1;
		
		// Horizontal Left
		for (x = xPos - 1; x >= 0; x--)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType)
			{
				if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		// Horizontal Right
		for (x = xPos + 1; x < chessBoard.getBoardWidth(); x++)
		{
			if (chessBoard.getBoardPosition(x, yPos) == type)
				break;
			else if (chessBoard.getBoardPosition(x, yPos) == enemyType)
			{
				if (chessBoard.getPiece(x, yPos) != null && (chessBoard.getPiece(x, yPos).name == "Queen" || chessBoard.getPiece(x, yPos).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, yPos));
				else
					break;
			}
		}
		// Vertical Up
		for (y = yPos - 1; y >= 0; y--)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType)
			{
				if (enemyType == 2 && protect == true && y == yPos - 1 && chessBoard.getPiece(xPos, y) != null && chessBoard.getPiece(xPos, y).name == "Pawn")
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (enemyType == 2 && protect == true && y == yPos - 2 && chessBoard.getPiece(xPos, y) != null && chessBoard.getPiece(xPos, y).name == "Pawn" && chessBoard.getPiece(xPos, y).isFirstTime())
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		// Vertical Down
		for (y = yPos + 1; y < chessBoard.getBoardHeight(); y++)
		{
			if (chessBoard.getBoardPosition(xPos, y) == type)
				break;
			else if (chessBoard.getBoardPosition(xPos, y) == enemyType)
			{
				if (enemyType == 1 && protect == true && y == yPos + 1 && chessBoard.getPiece(xPos, y) != null && chessBoard.getPiece(xPos, y).name == "Pawn")
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (enemyType == 1 && protect == true && y == yPos + 2 && chessBoard.getPiece(xPos, y) != null && chessBoard.getPiece(xPos, y).name == "Pawn" && chessBoard.getPiece(xPos, y).isFirstTime())
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				if (chessBoard.getPiece(xPos, y) != null && (chessBoard.getPiece(xPos, y).name == "Queen" || chessBoard.getPiece(xPos, y).name == "Rook"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(xPos, y));
				else
					break;
			}
		}
		// Diagonal 1 \ Up
		for (y = yPos - 1, x = xPos - 1; y >= 0 && x >= 0; y--, x--)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (protect == false && y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 1 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		// Diagonal 1 \ Down
		for (y = yPos + 1, x = xPos + 1; y < chessBoard.getBoardHeight() && x < chessBoard.getBoardWidth(); y++, x++)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (protect == false && y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 2 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		// Diagonal 2 / Up
		for (y = yPos - 1, x = xPos + 1; y >= 0 && x < chessBoard.getBoardWidth(); y--, x++)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (protect == false && y == yPos - 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 1 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}
		// Diagonal 2 / Down
		for (y = yPos + 1, x = xPos - 1; y < chessBoard.getBoardHeight() && x >= 0; y++, x--)
		{
			if (chessBoard.getBoardPosition(x, y) == type)
				break;
			else if (chessBoard.getBoardPosition(x, y) == enemyType)
			{
				if (protect == false && y == yPos + 1 && chessBoard.getBoardPosition(x, y) != 0 && chessBoard.getPiece(x, y) != null && (type == 2 && chessBoard.getPiece(x, y).name == "Pawn"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				if (chessBoard.getBoardPosition(x, y) != 0 && (chessBoard.getPiece(x, y).name == "Queen" || chessBoard.getPiece(x, y).name == "Bishop"))
					chessBoard.saviorPieces.add(chessBoard.getPiece(x, y));
				else
					break;
			}
		}		
		// Knight
		for (y = -2; y <= 2; y++)
		{
			if (y != 0)
			{
				x = y % 2 == 0 ? 1 : 2;
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos - x >= 0 && xPos - x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos - x, yPos + y) != type && chessBoard.getBoardPosition(xPos - x, yPos + y) != 0)
				{
					if (chessBoard.getPiece(xPos - x, yPos + y) != null && chessBoard.getPiece(xPos - x, yPos + y).name == "Knight")
						chessBoard.saviorPieces.add(chessBoard.getPiece(xPos - x, yPos + y));
				}
				if (yPos + y >= 0 && yPos + y < chessBoard.getBoardHeight() && xPos + x >= 0 && xPos + x < chessBoard.getBoardWidth() && chessBoard.getBoardPosition(xPos + x, yPos + y) != type && chessBoard.getBoardPosition(xPos + x, yPos + y) != 0)
				{
					if (chessBoard.getPiece(xPos + x, yPos + y) != null && chessBoard.getPiece(xPos + x, yPos + y).name == "Knight")
						chessBoard.saviorPieces.add(chessBoard.getPiece(xPos + x, yPos + y));
				}
			}
		}
	}

	
	// Method to check checkmate
	public boolean isCheckmate(ChessBoard chessboard, int xPos, int yPos, int type)
	{
		boolean checkmate = true;
		int x = xPos;
		int y = yPos;
		for (y = yPos - 1; y <= yPos + 1; y++)
		{
			for (x = xPos - 1; x <= xPos + 1; x++)
			{
				if(y >= 0 && y < chessboard.getBoardHeight() && x >= 0 && x < chessboard.getBoardWidth() && chessboard.getBoardPosition(x, y) != type)
				{
					if (!isCheck(chessboard, x, y, type, true))
					{
						checkmate = false;
						break;
					}
				}
			}
			if (!checkmate)
				break;
		}
		/* NO castle when you are in check
		// Check is the king can castle
		if (chessboard.getPiece(xPos, yPos).canCastle(chessboard) == 1 && !isCheck(chessboard, xPos - 1, yPos, type, true))
			return (false);
		if (chessboard.getPiece(xPos, yPos).canCastle(chessboard) == 2 && !isCheck(chessboard, xPos + 2, yPos, type, true))
			return (false);
		if (chessboard.getPiece(xPos, yPos).canCastle(chessboard) == 3 && !isCheck(chessboard, xPos - 1, yPos, type, true))
			return (false);
		if (chessboard.getPiece(xPos, yPos).canCastle(chessboard) == 4 && !isCheck(chessboard, xPos + 2, yPos, type, true))
			return (false);
				*/
		if (chessboard.checkPieces.size() < 2)
		{
			Piece checkPiece = chessboard.checkPieces.get(0);
			canCapture(chessboard, checkPiece);
			canProtect(chessboard, xPos, yPos, type, checkPiece);
			if (!chessboard.saviorPieces.isEmpty())
			{
				for(Iterator<Piece> piece = chessboard.saviorPieces.iterator(); piece.hasNext(); )
				{
					Piece item = piece.next();
					item.isASavior = true;
					if (verticalProtection(chessboard, item.xPos, item.yPos, item.type) || horizontalProtection(chessboard, item.xPos, item.yPos, item.type) ||
				    	slashDiagonalProtection(chessboard, item.xPos, item.yPos, item.type) || backslashDiagonalProtection(chessboard, item.xPos, item.yPos, item.type))
				    {
				    	item.isASavior = false;
				    	piece.remove();
				    }
				}
			}
			if (!chessboard.saviorPieces.isEmpty())
				checkmate = false;
		}
		return (checkmate);
	}
	
	// Method to check is someone can capture the piece that threat the king
	public void canCapture(ChessBoard chessboard, Piece checkPiece)
	{
		findAllSaviorPieces(chessboard, checkPiece.xPos, checkPiece.yPos, checkPiece.type, false);
	}
	
	// Method to check is someone can capture the threatening piece or protect the king from the piece that threat him
	public void canProtect(ChessBoard chessboard, int xPos, int yPos, int type, Piece checkPiece)
	{
		if (checkPiece.name == "Knight" || checkPiece.name == "Pawn")
			return;
		// Vertical up threat
		if (xPos == checkPiece.xPos && yPos > checkPiece.yPos)
			for (int y = checkPiece.yPos + 1; y < yPos; y++)
				findAllSaviorPieces(chessboard, checkPiece.xPos, y, checkPiece.type, true);
		// Vertical down threat
		if (xPos == checkPiece.xPos && yPos < checkPiece.yPos)
			for (int y = checkPiece.yPos - 1; y > yPos; y--)
				findAllSaviorPieces(chessboard, checkPiece.xPos, y, checkPiece.type, true);
		// Horizontal left threat
		if (xPos > checkPiece.xPos && yPos == checkPiece.yPos)
			for (int x = checkPiece.xPos + 1; x < xPos; x++)
				findAllSaviorPieces(chessboard, x, checkPiece.yPos, checkPiece.type, true);
		// Horizontal right threat
		if (xPos < checkPiece.xPos && yPos == checkPiece.yPos)
			for (int x = checkPiece.xPos - 1; x > xPos; x--)
				findAllSaviorPieces(chessboard, x, checkPiece.yPos, checkPiece.type, true);
		// Diagonal 1 \ up threat
		int y = checkPiece.yPos + 1;
		if (xPos > checkPiece.xPos && yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos + 1; x < xPos && y < yPos; x++, y++)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);
		// Diagonal 1 \ down threat
		y = checkPiece.yPos - 1;
		if (xPos < checkPiece.xPos && yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos - 1; x > xPos && y > yPos; x--, y--)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);				
		// Diagonal 2 / up threat
		y = checkPiece.yPos + 1;
		if (xPos < checkPiece.xPos && yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos - 1; x > xPos && y < yPos; x--, y++)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);
		// Diagonal 2 / down threat
		y = checkPiece.yPos - 1;
		if (xPos > checkPiece.xPos && yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos + 1; x < xPos && y > yPos; x++, y--)
				findAllSaviorPieces(chessboard, x, y, checkPiece.type, true);
	}
	
	public boolean isThisProtecting(ChessBoard chessboard, int xPos, int yPos, int type)
	{
		Piece checkPiece = chessboard.checkPieces.get(0);
		// Vertical up threat
		if (chessboard.getKing(type).xPos == checkPiece.xPos && chessboard.getKing(type).yPos > checkPiece.yPos)
			if (xPos == chessboard.getKing(type).xPos && yPos < chessboard.getKing(type).yPos && yPos > checkPiece.yPos)
				return (true);
		// Vertical down threat
		if (chessboard.getKing(type).xPos == checkPiece.xPos && chessboard.getKing(type).yPos < checkPiece.yPos)
			if (xPos == chessboard.getKing(type).xPos && yPos > chessboard.getKing(type).yPos && yPos < checkPiece.yPos)
				return (true);
		// Horizontal left threat
		if (chessboard.getKing(type).xPos > checkPiece.xPos && chessboard.getKing(type).yPos == checkPiece.yPos)
			if (yPos == chessboard.getKing(type).yPos && xPos < chessboard.getKing(type).xPos && xPos > checkPiece.xPos)
				return (true);
		// Horizontal right threat
		if (chessboard.getKing(type).xPos < checkPiece.xPos && chessboard.getKing(type).yPos == checkPiece.yPos)
			if (yPos == chessboard.getKing(type).yPos && xPos > chessboard.getKing(type).xPos && xPos < checkPiece.xPos)
				return (true);
		// Diagonal 1 \ up threat
		int y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos > checkPiece.xPos && chessboard.getKing(type).yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos; x < chessboard.getKing(type).xPos && y < chessboard.getKing(type).yPos; x++, y++)
				if (xPos == x && yPos == y)
					return (true);
		// Diagonal 1 \ down threat
		y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos < checkPiece.xPos && chessboard.getKing(type).yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos; x > chessboard.getKing(type).xPos && y > chessboard.getKing(type).yPos; x--, y--)
				if (xPos == x && yPos == y)
					return (true);
		// Diagonal 2 / up threat
		y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos < checkPiece.xPos && chessboard.getKing(type).yPos > checkPiece.yPos)
			for (int x = checkPiece.xPos; x > chessboard.getKing(type).xPos && y < chessboard.getKing(type).yPos; x--, y++)
				if (xPos == x && yPos == y)
					return (true);
		// Diagonal 2 / down threat
		y = checkPiece.yPos;
		if (chessboard.getKing(type).xPos > checkPiece.xPos && chessboard.getKing(type).yPos < checkPiece.yPos)
			for (int x = checkPiece.xPos; x < chessboard.getKing(type).xPos && y > chessboard.getKing(type).yPos; x++, y--)
				if (xPos == x && yPos == y)
					return (true);
		return (false);
	}
}
