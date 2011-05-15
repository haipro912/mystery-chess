package mysterychess.network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import mysterychess.network.dto.MoveDto;
import mysterychess.network.dto.TableDto;

/**
 *
 * @author Tin Bui-Huy
 */
public interface ClientCallback extends CommonRemote {

    public void pieceMoved(MoveDto d) throws RemoteException;

    public void setTable(TableDto table) throws RemoteException;

//    public void gameOverred(String msg) throws RemoteException;

    public boolean isAlive() throws RemoteException;
}
