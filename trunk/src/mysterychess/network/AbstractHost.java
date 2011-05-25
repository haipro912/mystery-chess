package mysterychess.network;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysterychess.model.ChessType;
import mysterychess.model.Match;
import mysterychess.model.ModelActionListener;
import mysterychess.model.Team.TeamColor;
import mysterychess.network.dto.MoveDto;
import mysterychess.network.dto.TableDto;
import mysterychess.util.Task;
import mysterychess.util.Util;

/**
 *
 * @author Tin Bui-Huy
 */
public abstract class AbstractHost implements CommonRemote {

    List<ModelActionListener> modelActionListeners = new ArrayList<ModelActionListener>();
    private Match match;
    private Chatter chater;
    protected boolean ready = false;
    protected boolean gameOverred = false;

    public AbstractHost() {
    }

    protected abstract CommonRemote getOtherSide();

    protected abstract void createNewGame(ChessType type, TeamColor bottomTeam);

    protected abstract void loadTable(TableDto t) throws RemoteException;

    public void shutdown() {
        try {
            CommonRemote otherPlayer = getOtherSide();
            if (otherPlayer != null) {
                getOtherSide().resigned();
            }
            System.exit(0);
        } catch (RemoteException ex) {
            Logger.getLogger(AbstractHost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void init(Match match, Chatter chater) {
        this.match = match;
        this.chater = chater;
        this.match.addModelChangedListener(new ModelActionListener() {

            public void messageSent(final String msg) {
                Task t = new Task() {

                    public void perform() throws Exception {
                        getOtherSide().message(msg);
                    }
                };
                Util.execute(t);
            }

            public void errorSent(final String msg) {
                Task t = new Task() {

                    public void perform() throws Exception {
                        getOtherSide().error(msg);
                    }
                };
                Util.execute(t);
            }

            public void pieceMoved(Point from, Point to) {
                final MoveDto data = new MoveDto();
                data.from = transform(from);
                data.to = transform(to);
                Task t = new Task() {

                    public void perform() throws Exception {
                        try {
                            getOtherSide().pieceMoved(data);
                        } catch (RemoteException ex) {
                            Logger.getLogger(AbstractHost.class.getName()).log(Level.SEVERE,
                            ex.getMessage(), ex);
                            AbstractHost.this.match.receivedError("Connection refused. Guest may resigned.");
                        }
                    }
                };
                Util.execute(t);
            }

            public void shutdownRequested() {
                shutdown();
            }

            public void newGameCreated(ChessType type, TeamColor bottomTeam) {
                createNewGame(type, bottomTeam);
            }

            public void gameLoaded(TableDto t) {
                try {
                    loadTable(t);
                } catch (RemoteException ex) {
                    Logger.getLogger(AbstractHost.class.getName()).log(Level.SEVERE,
                            ex.getMessage(), ex);
                    AbstractHost.this.match.receivedError(ex.getCause().getMessage());
                }
            }

            public void resigned() {
                try {
                    getOtherSide().resigned();
                } catch (RemoteException ex) {
                    Logger.getLogger(AbstractHost.class.getName()).log(Level.SEVERE,
                            ex.getMessage(), ex);
                }
            }

            public void gameOver(String msg) {
                try {
                getOtherSide().gameOver(msg);
                } catch (RemoteException ex) {
                    Logger.getLogger(AbstractHost.class.getName()).log(Level.SEVERE,
                            ex.getMessage(), ex);
                    AbstractHost.this.match.receivedError(ex.getCause().getMessage());
                }
            }
        });

        chater.addMessageTypedListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String msg = (String) e.getSource();
                try {
                    getOtherSide().chat(msg);
                } catch (RemoteException ex) {
                    Logger.getLogger(AbstractHost.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    protected abstract Point transform(Point p);

    /**
     * This method is called remotely.
     *
     * @param data
     * @throws RemoteException
     */
    public void pieceMoved(final MoveDto data) throws RemoteException {
        Thread t = new Thread() {

            @Override
            public void run() {
                Point from = transform(data.from);
                Point to = transform(data.to);
                match.getPieceAt(from).move(to, true);
            }
        };
        t.start();
    }

    /**
     * This method is called remotely.
     *
     * @param msg
     * @throws RemoteException
     */
    public void message(final String msg) throws RemoteException {
        Thread t = new Thread() {

            public void run() {
                match.receivedMessage(msg);
            }
        };
        t.start();
    }

    /**
     * This method is called remotely.
     *
     * @param msg
     * @throws RemoteException
     */
    public void error(final String msg) {
        Thread t = new Thread() {

            public void run() {
                Logger.getLogger(AbstractHost.class.getName()).log(Level.INFO,
                        "Received an error from other side: " + msg);
                if (match != null) {
                    match.receivedError(msg);
                }
            }
        };
        t.start();
    }

    /**
     * This method is called remotely.
     */
    public void chat(String msg) {
        if (msg != null && !msg.equals("")) {
            chater.messageReceived(msg);
        }
    }

    /**
     * This method is called remotely.
     *
     * @throws RemoteException
     */
    public void resigned() throws RemoteException {
        match.resign(false);
    }
    
        /**
     * This method is called remotely.
     *
     * @throws RemoteException
     */
    public void gameOver(String msg) throws RemoteException {
        match.gameOver(msg, false);
    }

//    /**
//     * This method is called remotely.
//     *
//     * @param msg
//     * @throws RemoteException
//     */
//    public void gameOverred(String msg) throws RemoteException {
//        match.receivedMessage(msg);
//        match.gameOverred(msg);
//        gameOverred = true;
//        ready = false;
//    }
}
