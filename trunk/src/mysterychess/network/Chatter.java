package mysterychess.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tin Bui-Huy
 */
public class Chatter {
    private List<ActionListener> messageTypedListeners = new ArrayList<ActionListener>();
    private List<ActionListener> messageReceivedListeners = new ArrayList<ActionListener>();

    public void addMessageTypedListener(ActionListener l) {
        if (messageTypedListeners == null) {
            messageTypedListeners = new ArrayList<ActionListener>();
        }
        messageTypedListeners.add(l);
    }

    public void messageTyped(String message) {

        ActionEvent e = new ActionEvent(message, 1, null);
        for (ActionListener l : messageTypedListeners) {
            l.actionPerformed(e);
        }
    }
    public void addMessageReceivedListener(ActionListener l) {
        if (messageReceivedListeners == null) {
            messageReceivedListeners = new ArrayList<ActionListener>();
        }
        messageReceivedListeners.add(l);
    }

    public void messageReceived(String message) {

        ActionEvent e = new ActionEvent(message, 1, null);
        for (ActionListener l : messageReceivedListeners) {
            l.actionPerformed(e);
        }
    }

    void removeMessageTypedListener(ActionListener messageTypedListener) {
        messageTypedListeners.remove(messageTypedListener);
    }
}
