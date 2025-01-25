package chess.moves;

import chess.ChessMove;

import java.util.Collection;

public interface Calculable {
    public Collection<ChessMove> pieceMoves();
}
