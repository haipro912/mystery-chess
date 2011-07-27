package mysterychess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tin Bui-Huy
 */
public class Horse extends Role {

    public Horse() {
        super(PieceName.horse);
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
        int xDiff = Math.abs(myPiece.getPosition().x - p.x);
        int yDiff = Math.abs(myPiece.getPosition().y - p.y);

        if (xDiff > 2 || yDiff > 2) {
            return false;
        }

        if (xDiff + yDiff != 3) {
            return false;
        }

        int deltaX = 0;
        int deltaY = 0;

        if (xDiff == 2) {
            deltaX = (p.x - myPiece.getPosition().x) / 2;
        } else {
            deltaY = (p.y - myPiece.getPosition().y) / 2;
        }

        Point preventerPos =
                new Point(myPiece.getPosition().x + deltaX,
                myPiece.getPosition().y + deltaY);

        Piece preventer = myPiece.getTeam().getMatch().getPieceAt(preventerPos);
        if (preventer != null) {
            return false;
        }

        return true;
    }

	@Override
	public List<Point> possibleSteps() {
		List<Point> steps = new ArrayList<Point>();
		Point current = myPiece.getPosition();
		
		for (int i = -1; i <= 1; i++) {
			if (i == 0) continue;
			for (int j = -2; j <= 2; j += 2) {
				if (j == 0) continue;
				Point temp = new Point(current.x + i, current.y + j);
				if (isPossiblePoint(temp) && !isDuplicated(temp)) {
					steps.add(temp);
				}
			}
		}
		for (int i = -2; i <= 2; i += 2) {
			if (i == 0) continue;
			for (int j = -1; j <= 1; j ++) {
				if (j == 0) continue;
				Point temp = new Point(current.x + i, current.y + j);
				if (isPossiblePoint(temp) && !isDuplicated(temp)) {
					steps.add(temp);
				}
			}
		}
		return steps;
	}
}
