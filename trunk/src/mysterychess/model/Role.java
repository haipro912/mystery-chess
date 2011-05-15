package mysterychess.model;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Tin Bui-Huy
 */
public abstract class Role implements Serializable {

    Piece myPiece;
    PieceName name;

    public Role(PieceName name) {
        this.name = name;
    }

    public void setMyPiece(Piece myPiece) {
        this.myPiece = myPiece;
    }

    public PieceName getName() {
        return name;
    }

    public void setName(PieceName name) {
        this.name = name;
    }

    public boolean move(Point toPosition, boolean validated) {
        Point oldPos = myPiece.getPosition();
        if (validated) {
            Piece captured = myPiece.getTeam().getMatch().getPieceAt(toPosition);
            if (captured != null) {
                capturePieceAt(toPosition);
            } else {
                moveTo(toPosition);
            }
            return true;
        } else {
            if (!isValidPosition(toPosition)) {
                return false;
            }

            boolean moved = move(toPosition);
            if (moved) {
                myPiece.getTeam().getMatch().pieceMoved(oldPos, toPosition);
            }
            return moved;
        }
    }

    public abstract boolean move(Point toPosition);

    protected void moveTo(Point position) {
        changePosition(position);
    }

    private void changePosition(Point position) {
        myPiece.setPosition(position);
        if (!myPiece.isTurnedUp()) {
            turnUp();
        }
    }

    private void turnUp() {
        myPiece.setTurnedUp(true);
        myPiece.setCurrentRole(myPiece.getActualRole());
        myPiece.setActualRole(null);
    }

    protected void capturePieceAt(Point position) {
        myPiece.getTeam().getMatch().capturePieceAt(
                position, myPiece.getTeam());
        changePosition(position);
    }

    /**
     * Checks if the position is out of the table.
     * 
     * @param position
     * @return
     */
    protected boolean isValidPosition(Point position) {
        if (position.x < 0 || position.y < 0
                || position.x > 8 || position.y > 9) {
            return false;
        }
        return true;
    }

    public boolean canCapture(Piece p) {
        return isPossiblePoint(p.getPosition());
    }

    protected abstract boolean isPossiblePoint(Point position);
}
