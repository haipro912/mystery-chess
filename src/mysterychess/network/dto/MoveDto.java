/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mysterychess.network.dto;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Tin Bui-Huy
 */
public class MoveDto extends DataDto implements Serializable {

    /**
     * A version number for this class so that serialization can occur
     * without worrying about the underlying class changing between
     * serialization and deserialization.
     */
    private static final long serialVersionUID = 1;
    public Point from;
    public Point to;
    public boolean checked = false;
}
