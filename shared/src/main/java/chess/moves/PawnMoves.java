package chess.moves;

import chess.*;
import chess.ChessPiece.PieceType;

import java.util.HashSet;

public class PawnMoves extends MoveCalculator {
    private int direction;

    public PawnMoves(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
        this.pieceColor = board.getPiece(position).getTeamColor();
        this.direction = (this.pieceColor == ChessGame.TeamColor.WHITE) ? 1 : -1;
        this.PATHS = new int[][] {{direction, 0}, {direction * 2, 0}};
    }


    public HashSet<ChessMove> pieceMoves() {
        PieceType[] promotionTypes = (canPromote()) ?
                new PieceType[] {PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT, PieceType.QUEEN} :
                new PieceType[] {null};


        // calculate Column moves
        HashSet<ChessMove> moves = calculateColumn(promotionTypes);
        // calculate attack moves
        moves.addAll(calculateAttack(promotionTypes));

        return moves;
    }

    private boolean canPromote() {
        if (pieceColor == ChessGame.TeamColor.WHITE && position.getRow() == 7) {
            return true;
        }
        else return pieceColor == ChessGame.TeamColor.BLACK && position.getRow() == 2;
    }

    private HashSet<ChessMove> calculateColumn(PieceType[] promotionTypes) {
        HashSet<ChessMove> moves = new HashSet<>();

        if (isOnStart()) {
            for (int[] path : PATHS) {
                ChessMove potentialMove = calculateSingleMove(position, path);
                ChessPiece potentialBlock = board.getPiece(potentialMove.getEndPosition());

                if (potentialBlock == null) {
                    moves.add(potentialMove);
                }
                else {
                    break;
                }
            }
        }
        else {
            for (PieceType promotion : promotionTypes) {
                ChessMove potentialMove = calculateSingleMove(position, PATHS[0], promotion);
                ChessPosition newPosition = potentialMove.getEndPosition();

                if (isOutOfBounds(newPosition)) {
                    break;
                }
                ChessPiece potentialBlock = board.getPiece(newPosition);
                if (potentialBlock == null) {
                    moves.add(potentialMove);
                }
                else {
                    break;
                }
            }
        }

        return moves;
    }

    private boolean isOnStart() {
        if (pieceColor == ChessGame.TeamColor.WHITE && position.getRow() == 2) {
            return true;
        }
        else return pieceColor == ChessGame.TeamColor.BLACK && position.getRow() == 7;
    }

    private HashSet<ChessMove> calculateAttack(PieceType[] promotionType) {
        HashSet<ChessMove> moves = new HashSet<>();
        int[][] attacks = {{direction, 1}, {direction, -1}};

        for (int[] attack : attacks) {
            for (PieceType promotion : promotionType) {
                ChessMove potentialMove = calculateSingleMove(position, attack, promotion);
                ChessPosition newPosition = potentialMove.getEndPosition();

                if (isOutOfBounds(newPosition)) {
                    break;
                }
                ChessPiece potentialEnemy = board.getPiece(newPosition);
                if (potentialEnemy == null) {
                    break;
                }
                else if (potentialEnemy.getTeamColor() != pieceColor) {
                    moves.add(potentialMove);
                }
                else {
                    break;
                }
            }
        }

        return moves;
    }


}
