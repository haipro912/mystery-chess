package mysterychess.model;

/**
 *
 * @author Tin Bui-Huy
 */
public interface RemoteActionListener {

    public void errorReceived(String message);

    public void messageReceived(String message);

    public void shutdownRequested();
}
