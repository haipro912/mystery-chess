package mysterychess.network;

import java.rmi.RemoteException;
import mysterychess.model.ChessType;
import mysterychess.model.Team.TeamColor;
import mysterychess.network.dto.MoveDto;
import mysterychess.network.dto.TableDto;

/**
 * 
 * @author Tin Bui-Huy
 */
public interface RemoteServer extends CommonRemote {

    public void registerClientCallback(ClientCallback callback, String clientVersion) throws RemoteException;

    public void deregisterClientCallback(ClientCallback callback) throws RemoteException;

    public void createTable(ChessType type, TeamColor clientTeam) throws RemoteException;

    public TableDto joinGame(String clientName) throws RemoteException;

    public void setClientReady() throws RemoteException;

    public void pieceMoved(MoveDto d) throws RemoteException;

    public long getPieceMoveLimitTime() throws RemoteException;

    public long getGameLimitTime() throws RemoteException;

    public void setGameLimitTime(long time) throws RemoteException;

    public void setPieceMoveLimitTime(long time) throws RemoteException;

    public void loadTable(TableDto tableDto) throws RemoteException;
}
