package mysterychess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tin Bui-Huy
 */
public class General extends Role {

    public General() {
        super(PieceName.general);
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
            // (3,0) <= pos <= (5,2)
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

    protected boolean isPossiblePoint(Point p) {
        if (!isInAllowArea(p)) {
            return false;
        }
        if (myPiece.getPosition().x != p.x
                && myPiece.getPosition().y != p.y) {
            return false;
        }

        if (myPiece.getPosition().x == p.x
                && Math.abs(myPiece.getPosition().y - p.y) > 1) {
            return false;
        }

        if (myPiece.getPosition().y == p.y
                && Math.abs(myPiece.getPosition().x - p.x) > 1) {
            return false;
        }

        List<Piece> otherPieces = myPiece.getTeam().getMatch()
                .getPieces(myPiece.getPosition(), p);
        if (!otherPieces.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean isGeneralsFaceToFace(Piece general) {
        if (myPiece.getPosition().x != general.getPosition().x) {
            return false;
        }

        List<Piece> preventPieces = myPiece.getTeam().getMatch().getPieces(myPiece.getPosition(), general.getPosition());
        return preventPieces.isEmpty();
    }

	@Override
	public boolean canCapture(Piece p) {
		return isGeneralsFaceToFace(p);
	}

	@Override
	public List<Point> possibleSteps() {
		List<Point> steps = new ArrayList<Point>();
		Point current = myPiece.getPosition();
		
		for (int i = -1; i <= 1; i++) {
			if (i == 0) continue;
			Point temp = new Point(current.x + i, current.y);
			if (isPossiblePoint(temp) && !isDuplicated(temp)) {
				steps.add(temp);
			}
		}
		for (int j = -1; j <= 1; j++) {
			if (j == 0) continue;
			Point temp = new Point(current.x, current.y + j);
			if (isPossiblePoint(temp) && !isDuplicated(temp)) {
				steps.add(temp);
			}
		}
		return steps;
	}

}
