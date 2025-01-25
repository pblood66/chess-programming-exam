package chess.moves;

import chess.*;

import java.util.HashSet;

public abstract class MoveCalculator implements Calculable{
    protected ChessBoard board;
    protected ChessPosition position;
    protected ChessGame.TeamColor pieceColor;
    protected int[][] PATHS;

    // calculate single moves
    protected ChessMove calculateSingleMove(ChessPosition currPosition, int[] path) {
        int row = currPosition.getRow() + path[0];
        int col = currPosition.getColumn() + path[1];
        ChessPosition newPosition = new ChessPosition(row, col);

        return new ChessMove(position, newPosition, null);
    }

    // calculate single move for pawns
    protected ChessMove calculateSingleMove(ChessPosition currPosition, int[] path, ChessPiece.PieceType promotion) {
        int row = currPosition.getRow() + path[0];
        int col = currPosition.getColumn() + path[1];
        ChessPosition newPosition = new ChessPosition(row, col);

        return new ChessMove(position, newPosition, promotion);
    }


    // isOutOfBounds
    protected boolean isOutOfBounds(ChessPosition currentPosition) {
        if (currentPosition.getRow() > 8 || currentPosition.getRow() < 1) {
            return true;
        }
        else return currentPosition.getColumn() > 8 || currentPosition.getColumn() < 1;
    }
}
