package mysterychess.model;

import java.awt.Point;
import java.util.List;

/**
 *
 * @author Tin Bui-Huy
 */
public class Chariot extends Role {

    public Chariot() {
        super(PieceName.chariot);
    }

    public boolean move(Point toPosition) {
        if (!isPossiblePoint(toPosition)) {
            return false;
        }

        Piece capturedPie = myPiece.getTeam().getMatch().getPieceAt(toPosition);
        if (capturedPie != null && capturedPie.getTeam().equals(myPiece.getTeam())) {
            return false;
        }

        if (capturedPie == null) {
            moveTo(toPosition);
        } else {
            capturePieceAt(toPosition);
        }
        return true;
    }

    protected boolean isPossiblePoint(Point p) {
        if (myPiece.getPosition().x != p.x
                && myPiece.getPosition().y != p.y) {
            return false;
        }

        List<Piece> otherPieces = myPiece.getTeam().getMatch().getPieces(
                myPiece.getPosition(), p);
        if (!otherPieces.isEmpty()) {
            return false;
        }
        return true;
    }
}
