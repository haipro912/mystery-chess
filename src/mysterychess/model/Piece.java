package mysterychess.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Tin Bui-Huy
 */
public class Piece implements Serializable {

    private Point position;
    private Team team;
    private Role actualRole;
    private Role currentRole;
    private boolean turnedUp = false;

    public Piece(Team team, Point position, Role currentRole, Role actualRole, boolean turnedUp) {
        this(team, position, currentRole, turnedUp);
        setActualRole(actualRole);
    }

    public Piece(Team team, Point position, Role currentRole, boolean turnedUp) {
        this.team = team;
        this.position = position;
        this.currentRole = currentRole;
        this.turnedUp = turnedUp;
        currentRole.setMyPiece(this);
    }

    public boolean isTurnedUp() {
        return turnedUp;
    }

    public void setTurnedUp(boolean turnedUp) {
        this.turnedUp = turnedUp;
    }

    public boolean move(Point toPosition, boolean validated) {
        if (currentRole.move(toPosition, validated)) {
            team.setActive(false);
            team.getMatch().dataChanged(this);
            return true;
        }
        return false;
    }

    public boolean isCheckIf(Piece piece, Point toPosition) {
        return true;
    }

    public Role getActualRole() {
        if (actualRole == null) {
            return currentRole;
        }
        return actualRole;
    }

    public void setActualRole(Role actualRole) {
        this.actualRole = actualRole;
        if (actualRole != null) {
            actualRole.setMyPiece(this);
        }

    }

    public Role getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(Role currentRole) {
        this.currentRole = currentRole;
        currentRole.setMyPiece(this);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean canCapture(Piece p) {
        return currentRole.canCapture(p);
    }
}
