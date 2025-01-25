package chess;

import chess.ChessPiece.PieceType;

import java.util.Arrays;
import java.util.Objects;


/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[8][8];
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getArrayRow()][position.getArrayColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getArrayRow()][position.getArrayColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // reset whole board to null
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        for (ChessGame.TeamColor team : ChessGame.TeamColor.values()) {
            // place pawns
            placePawns(team);
            //place backrow
            placeBackRow(team);
        }
    }

    public void placePawns(ChessGame.TeamColor team) {
        int row = (team == ChessGame.TeamColor.WHITE) ? 2 : 7;

        for (int i = 0; i < 8; i++) {
            ChessPosition pawnPosition = new ChessPosition(row, i + 1);
            addPiece(pawnPosition, new ChessPiece(team, PieceType.PAWN));
        }
    }

    public void placeBackRow(ChessGame.TeamColor team) {
        int row = (team == ChessGame.TeamColor.WHITE) ? 1 : 8;
        PieceType[] backRow = {PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP};

        for (int i = 0; i < backRow.length; i++) {
            ChessPosition position = new ChessPosition(row, i + 1);
            addPiece(position, new ChessPiece(team ,backRow[i]));
        }

        addPiece(new ChessPosition(row, 4), new ChessPiece(team, PieceType.QUEEN));
        addPiece(new ChessPosition(row, 5), new ChessPiece(team, PieceType.KING));

        for (int i = 0; i < backRow.length; i++) {
            ChessPosition position = new ChessPosition(row, i + 6);
            addPiece(position, new ChessPiece(team ,backRow[2 - i]));
        }
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print('|');
                if (board[i][j] != null) {
                    System.out.print(board[i][j]);
                }
                else {
                    System.out.print(' ');
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
