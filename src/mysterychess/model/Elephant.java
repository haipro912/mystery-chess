package mysterychess.model;

import java.awt.Point;

/**
 *
 * @author Tin Bui-Huy
 */
public class Elephant extends Role {

    public Elephant() {
        super(PieceName.elephant);
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

    /**
     * Only check for the bottom team
     *
     * @todo
     * @param toPosition
     * @return
     */
    protected boolean isInAllowArea(Point position) {
        if (myPiece.getTeam().getPosition() == Team.TeamPosition.TOP) {
            // (0,0) <= pos <= (8,4)
            if (position.x < 0 || position.y < 0
                    || position.x > 8 || position.y > 4) {
                return false;
            }
        } else {
            // (0,5) <= pos <= (8,9)
            if (position.x < 0 || position.y < 5
                    || position.x > 8 || position.y > 9) {
                return false;
            }
        }
        return true;
    }

    protected boolean isPossiblePoint(Point p) {
        if (!isInAllowArea(p)) {
            return false;
        }

        // Move cross
        if (Math.abs(myPiece.getPosition().x - p.x) != 2
                || Math.abs(myPiece.getPosition().y - p.y) != 2) {
            return false;
        }

        // Any piece on the road
        int x = (myPiece.getPosition().x + p.x) / 2;
        int y = (myPiece.getPosition().y + p.y) / 2;
        if (myPiece.getTeam().getMatch().getPieceAt(new Point(x, y)) != null) {
            return false;
        }
        return true;
    }
}
