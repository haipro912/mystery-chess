package mysterychess.model;

import java.awt.Point;

/**
 *
 * @author Tin Bui-Huy
 */
public class Soldier extends Role {

    public Soldier() {
        super(PieceName.soldier);
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

    private boolean inBorder(Point position) {
        if (myPiece.getTeam().getPosition() == Team.TeamPosition.TOP) {
            if (position.y < 0 || position.y > 4) {
                return false;
            }
        } else {
            if (position.y < 5 || position.y > 9) {
                return false;
            }
        }
        return true;
    }

    protected boolean isPossiblePoint(Point p) {
        if (myPiece.getPosition().x != p.x
                && myPiece.getPosition().y != p.y) {
            return false;
        }

        // Move forward
        if (myPiece.getPosition().x == p.x) {
            if (myPiece.getTeam().getPosition() == Team.TeamPosition.TOP) {
                if (p.y - myPiece.getPosition().y != 1) {
                    return false;
                }
            } else {
                if (p.y - myPiece.getPosition().y != -1) {
                    return false;
                }
            }
        }

        if (myPiece.getPosition().y == p.y
                && Math.abs(myPiece.getPosition().x - p.x) > 1) {
            return false;
        }

        // Prevent horizontal move if in the country border
        if (myPiece.getPosition().x - p.x != 0) {
            if (inBorder(myPiece.getPosition())) {
                return false;
            }
        }

        return true;
    }
}
