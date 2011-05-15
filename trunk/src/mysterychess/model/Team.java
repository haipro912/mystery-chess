package mysterychess.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tin Bui-Huy
 */
public class Team implements Serializable {

    public static enum TeamColor {

        WHITE("Red"), BLACK("Blue");

        private String displayName;
        private TeamColor(String displayName) {
            this.displayName = displayName;
        }
        public static TeamColor getOtherTeam(TeamColor team) {
            return (team == WHITE) ? BLACK : WHITE;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public static enum TeamPosition {

        TOP,
        BOTTOM;

        public static TeamPosition getOtherPosition(TeamPosition team) {
            return (team == TOP) ? BOTTOM : TOP;
        }
    }
    
    private Match match;
    private TeamColor color;
    private TeamPosition position;
    private List<Piece> pieces = new ArrayList<Piece>(16);
    private List<Piece> lostPieces = new ArrayList<Piece>(15);
    private List<Piece> capturedPieces = new ArrayList<Piece>(15);
    private Piece general;

    public Team(Match match, TeamColor color) {
        this.match = match;
        this.color = color;
        setDefaultPosition();
    }

    public Team() {
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Piece getGeneral() {
        return general;
    }

    public Team getOtherTeam() {
        return getMatch().getOtherTeam(this);
    }

    public Piece getPieceAt(Point position) {
        for (Piece piece : pieces) {
            if (piece.getPosition().equals(position)) {
                return piece;
            }
        }
        return null;
    }

    void setActive(boolean active) {
        if (active) {
            match.setActiveTeam(this);
        } else {
            match.setInactiveTeam(this);
        }
    }

    private void setDefaultPosition() {
        // default position
        if (color == TeamColor.BLACK) {
            position = TeamPosition.TOP;
        } else {
            position = TeamPosition.BOTTOM;
        }
    }

    public TeamPosition getPosition() {
        return position;
    }

    public void setPosition(TeamPosition position) {
        this.position = position;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void setCapturedPieces(List<Piece> capturedPieces) {
        this.capturedPieces = capturedPieces;
    }

    public TeamColor getColor() {
        return color;
    }

    public void setColor(TeamColor color) {
        this.color = color;
    }

    public List<Piece> getLostPieces() {
        return lostPieces;
    }

    public void setLostPieces(List<Piece> lostPieces) {
        this.lostPieces = lostPieces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
        for (Piece piece : pieces) {
            if (piece.getCurrentRole() instanceof General) {
                general = piece;
            }
        }
    }
}
