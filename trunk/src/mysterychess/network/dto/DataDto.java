/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mysterychess.network.dto;

import java.awt.Point;
import java.io.Serializable;
import mysterychess.model.ChessType;
import mysterychess.model.PieceName;
import mysterychess.model.Team.TeamColor;

/**
 *
 * @author Tin Bui-Huy
 */
public class DataDto implements Serializable {

    /**
     * A version number for this class so that serialization can occur
     * without worrying about the underlying class changing between
     * serialization and deserialization.
     */
    private static final long serialVersionUID = 1;
}

//class ChatMessageDto extends DataDto implements Serializable {
//
//    private static final long serialVersionUID = 1;
//    public String message;
//
//    ChatMessageDto(String msg) {
//        this.message = msg;
//    }
//}







