package mysterychess.view;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;
import mysterychess.model.Match;
import mysterychess.model.Piece;
import mysterychess.model.Team;
import mysterychess.util.Util;

/**
 *
 * @author Tin Bui-Huy
 */
public class LostPiecesPanel extends RetiredPiecesPanel {
    private final static int MARGIN = 2;

    public LostPiecesPanel(Match m) {
        super(m);
        pieces = getMyTeam().getLostPieces();
        m.addDataChangedListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof Match) {
                    pieces = getMyTeam().getLostPieces();
                    repaint();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPieces(g, pieces);
    }

    protected void drawPieces(Graphics g, List<Piece> pieces) {
        int unit = getWidth() - 2 * MARGIN;
        int y = MARGIN;
        for (Piece piece : pieces) {
            drawPiece(g, piece, new Point(MARGIN, y), unit);
            y += unit;
        }
    }

     private void drawPiece(Graphics g, Piece piece, Point p, int unit) {
        try {
            int imageSize = unit;
            g.drawImage(Util.getRetiredImage(piece, false),
                    p.x,
                    p.y,
                    imageSize,
                    imageSize,
                    null);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
        }
    }

}
