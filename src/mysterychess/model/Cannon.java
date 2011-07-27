package mysterychess.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tin Bui-Huy
 */
public class Cannon extends Role {

    public Cannon() {
        super(PieceName.cannon);
    }

    public boolean move(Point toPosition) {
        Piece capturedPie = myPiece.getTeam().getMatch().getPieceAt(toPosition);
        if (capturedPie != null && capturedPie.getTeam().equals(myPiece.getTeam())) {
            return false;
        }

        boolean capture = capturedPie != null;
        if (!isPossiblePoint(toPosition, capture)) {
            return false;
        }

        if (capture) {
            capturePieceAt(toPosition);
        } else {
            moveTo(toPosition);
        }
        
        return true;
    }

    protected boolean isPossiblePoint(Point p, boolean capture) {
        if (myPiece.getPosition().x != p.x
                && myPiece.getPosition().y != p.y) {
            return false;
        }

        List<Piece> otherPieces = myPiece.getTeam().getMatch().getPieces(myPiece.getPosition(), p);
        if (capture) {
            if (otherPieces.size() != 1) {
                return false;
            }
        } else {
            if (!otherPieces.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean isPossiblePoint(Point position) {
        return isPossiblePoint(position, true);
    }

	@Override
	public List<Point> possibleSteps() {
		List<Point> steps = new ArrayList<Point>();
		Point current = myPiece.getPosition();
		// row steps
		for (int i = 0; i < 9; i++) {
			if (i == current.x) continue;
			Point temp = new Point(i, current.y);
			if (!isDuplicated(temp)) {
				// Cannon has two types of move
				Piece victim = myPiece.getTeam().getMatch().getPieceAt(temp);
				if (isPossiblePoint(temp, victim != null)) {
					steps.add(temp);
				}
			}
		}
		// column steps
		for (int j = 0; j < 10; j++) {
			if (j == current.y) continue;
			Point temp = new Point(current.x, j);
			if (!isDuplicated(temp)) {
				// Cannon has two types of move
				Piece victim = myPiece.getTeam().getMatch().getPieceAt(temp);
				if (isPossiblePoint(temp, victim != null)) {
					steps.add(temp);
				}
			}
		}
		return steps;
	}
}
