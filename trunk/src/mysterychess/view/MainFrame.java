/*
 * MainFrame.java
 *
 * Created on Mar 10, 2011, 8:51:45 PM
 */
package mysterychess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import mysterychess.model.Match;
import mysterychess.model.Team;
import mysterychess.network.Chatter;
import mysterychess.util.Util;

/**
 *
 * @author Administrator
 */
public class MainFrame extends javax.swing.JFrame {

    private final static int CHAT_PANEL_MIN_SIZE = 100;
    private Match match;
    private Team myTeam;
    private JPanel chatPanel;
    private ChessPanel chessPanel;
    private final Chatter chatter;
    private JTextField inputText = new JTextField();
    private JTextArea outputText = new JTextArea();

    /** Creates new form MainFrame */
    public MainFrame(Match m, Chatter chatter, String title) {
        super(title);
        this.match = m;
        this.chatter = chatter;
//        if (match.getWhiteTeam().getPosition() == TeamPosition.BOTTOM) {
//            myTeam = match.getWhiteTeam();
//        } else {
//            myTeam = match.getBlackTeam();
//        }
        initComponents();
        pack();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        JScrollPane outputPanel = new JScrollPane(outputText);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setSize(new Dimension(100, 20));
        inputPanel.add(inputText, BorderLayout.CENTER);

        chatPanel.add(outputPanel, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane();
        contentPane.add(splitPane, java.awt.BorderLayout.CENTER);
        chessPanel = new ChessPanel(match);
        splitPane.setLeftComponent(chessPanel);
        splitPane.setRightComponent(chatPanel);

        this.setPreferredSize(new Dimension(730, 600));
        splitPane.setDividerLocation(550);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.80);
        outputText.setEditable(false);
        addListeners();
    }

    private void addListeners() {
        inputText.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                outputText.append("\n" + "You: " + inputText.getText());
                chatter.messageTyped(inputText.getText());
                inputText.setText("");
            }
        });

        chatter.addMessageReceivedListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String msg = (String) e.getSource();
                outputText.append("\n" + "Guest: " + msg);
                outputText.setCaretPosition(outputText.getText().length());
                showMessageReceivedIcon();
            }

            private void showMessageReceivedIcon() {
                Thread t = new Thread() {

                    public void run() {
                        try {
                            Color oldColor = outputText.getBackground();
                            outputText.setBackground(Color.cyan);
                            sleep(300);
                            outputText.setBackground(oldColor);
                        } catch (InterruptedException ex) {
                        }
                    }
                };
                t.start();
            }
        });

    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            super.processWindowEvent(e);
            match.shutdown(true);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        } else {
            super.processWindowEvent(e);
        }
    }
}
