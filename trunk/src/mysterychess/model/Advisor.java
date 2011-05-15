package mysterychess.model;

import java.awt.Point;

/**
 *
 * @author Tin Bui-Huy
 */
public class Advisor extends Role {

    public Advisor() {
        super(PieceName.advisor);
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

    protected boolean isPossiblePoint(Point toPosition) {
        if (!isInAllowArea(toPosition)) {
            return false;
        }
        if (Math.abs(myPiece.getPosition().x - toPosition.x) != 1
                || Math.abs(myPiece.getPosition().y - toPosition.y) != 1) {
            return false;
        }
        return true;
    }

    /**
     * Only check for the bottom team
     *
     * @todo
     * @param toPosition
     * @return
     */
    protected boolean isInAllowArea(Point position) {
        // (3,0) <= pos <= (5,2)
        if (myPiece.getTeam().getPosition() == Team.TeamPosition.TOP) {
            if (position.x < 3 || position.y < 0
                    || position.x > 5 || position.y > 2) {
                return false;
            }
        } else {

            // (3,7) <= pos <= (5,9)
            if (position.x < 3 || position.y < 7
                    || position.x > 5 || position.y > 9) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean canCapture(Piece p) {
        return isPossiblePoint(p.getPosition());
    }
}
