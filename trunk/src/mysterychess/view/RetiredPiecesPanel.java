package mysterychess.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import mysterychess.model.Match;
import mysterychess.model.Piece;
import mysterychess.model.Team;
import mysterychess.model.Team.TeamPosition;

/**
 *
 * @author Tin Bui-Huy
 */
public abstract class RetiredPiecesPanel extends JPanel {

    protected List<Piece> pieces;
    protected Match match;

    public RetiredPiecesPanel(Match match) {
        this.setPreferredSize(new Dimension(40, 100));
        this.match = match;
        match.addPieceCapturedListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Capture event");
                Piece p = (Piece) e.getSource();
                if (pieces.contains(p)) {
                    repaint();
                }
            }
        });
    }

    protected Team getMyTeam() {
        return match.getTeam(TeamPosition.BOTTOM);
    }
}
