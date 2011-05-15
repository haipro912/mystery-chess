package mysterychess.model;

import java.awt.Point;

/**
 * This piece is only used in mystery chess.

 * @author Tin Bui-Huy
 */
public class SuperElephant extends Elephant {

    @Override
    protected boolean isInAllowArea(Point position) {
        // no restriction
        return true;
    }
}
