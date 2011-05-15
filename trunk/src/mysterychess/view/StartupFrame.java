package mysterychess.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysterychess.model.ChessType;
import mysterychess.model.Match;
import mysterychess.model.Team.TeamColor;
import mysterychess.network.Chatter;
import mysterychess.network.RmiClient;
import mysterychess.network.RmiServer;
import mysterychess.util.Util;

/**
 * Tin Bui-Huy
 */
public class StartupFrame extends JFrame {

    JPanel southPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton okButton = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    TitledBorder titledBorder1 = new TitledBorder("");
    ButtonGroup clientServerButtonGroup = new ButtonGroup();
    JRadioButton clientRadio = new JRadioButton();
    JRadioButton serverRadio = new JRadioButton();
    JLabel portLabel = new JLabel();
    JTextField portText = new JTextField();
    JLabel addressLabel = new JLabel();
    JTextField addressText = new JTextField();
    JCheckBox iMoveFirstCheck = new JCheckBox();
    JPanel northPanel = new JPanel() {

        // This is a work around to force the image to load to memory
        // before starting up the game.
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Collection<Image> images = Util.getAllImages();
                int unit = this.getWidth() / images.size();
                int x = 0;
                int y = this.getWidth() / 2;
                for (Image im : images) {
                    g.drawImage(im,
                            x,
                            y,
                            unit,
                            unit,
                            null);
                    y += unit;
                }
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            }
        }
    };
    JRadioButton chineseChess = new JRadioButton();
    JRadioButton mysteryChessChk = new JRadioButton();
    ButtonGroup chessTypeButtonGroup = new ButtonGroup();
    JLabel gameLimitTimeLabel = new JLabel();
    JTextField gameLimitTimeText = new JTextField();
    JLabel pieceMoveLimitTimeLabel = new JLabel();
    JTextField pieceMoveLimitTimeText = new JTextField();
    JLabel secLabel = new JLabel();
    JLabel minuteLabel = new JLabel();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel paddingPanel = new JPanel();

    public StartupFrame() {
        try {
            initComponent();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponent() throws Exception {
        this.setTitle("Mystery Chess " + Util.getVersion()
                + " - Developed by " + Util.getOriginalAuthor());
        titledBorder1 = new TitledBorder("Server or Client");
        this.getContentPane().setLayout(borderLayout1);
        okButton.setPreferredSize(new Dimension(70, 23));
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startup();
            }
        });

        mainPanel.setLayout(gridBagLayout1);
        clientRadio.setSelected(true);
        clientRadio.setText("Client");
        clientRadio.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addressText.setVisible(true);
                addressLabel.setVisible(true);
                iMoveFirstCheck.setVisible(false);

                mysteryChessChk.setVisible(false);
                chineseChess.setVisible(false);

                // Time options
                gameLimitTimeLabel.setVisible(false);
                gameLimitTimeText.setVisible(false);
                minuteLabel.setVisible(false);
                pieceMoveLimitTimeLabel.setVisible(false);
                pieceMoveLimitTimeText.setVisible(false);
                secLabel.setVisible(false);
            }
        });

        serverRadio.setSelected(true);
        serverRadio.setText("Server");
        serverRadio.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addressText.setVisible(false);
                addressLabel.setVisible(false);
                iMoveFirstCheck.setVisible(true);

                mysteryChessChk.setVisible(true);
                chineseChess.setVisible(true);

                // Time options
                gameLimitTimeLabel.setVisible(true);
                gameLimitTimeText.setVisible(true);
                minuteLabel.setVisible(true);
                pieceMoveLimitTimeLabel.setVisible(true);
                pieceMoveLimitTimeText.setVisible(true);
                secLabel.setVisible(true);
            }
        });

        portLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        portLabel.setText("Port:");
        portText.setText("4444");
        addressLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        addressLabel.setText("Server address:");
        addressLabel.setVisible(false);
        addressText.setVisible(false);
        addressText.setText("192.168.103.164");
        iMoveFirstCheck.setHorizontalAlignment(SwingConstants.TRAILING);
        iMoveFirstCheck.setSelected(true);
        iMoveFirstCheck.setText("I move first");
        chineseChess.setText("Chinese Chess");
        mysteryChessChk.setSelected(true);
        mysteryChessChk.setText("Mystery Chess");
        gameLimitTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        gameLimitTimeLabel.setText("Game limit time:");
        gameLimitTimeText.setText(String.valueOf(Util.GAME_EXPIRE_TIME/(60*1000)));
        gameLimitTimeText.setHorizontalAlignment(SwingConstants.LEFT);
        pieceMoveLimitTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        pieceMoveLimitTimeLabel.setText("Piece move limit time:");
        pieceMoveLimitTimeText.setText(String.valueOf(Util.PIECE_MOVE_EXPIRE_TIME/1000));
        secLabel.setText("secs");
        minuteLabel.setText("mins");
        jLabel2.setText("jLabel2");
        southPanel.add(buttonPanel);
        buttonPanel.add(okButton);
        northPanel.setSize(new Dimension(100, 1));
        this.getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);
        this.getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);
        this.getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
        clientServerButtonGroup.add(serverRadio);
        clientServerButtonGroup.add(clientRadio);
        chessTypeButtonGroup.add(chineseChess);
        chessTypeButtonGroup.add(mysteryChessChk);
        mainPanel.add(mysteryChessChk,
                new GridBagConstraints(4, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(10, 19, 0, 12), 9, 0));
        mainPanel.add(clientRadio, new GridBagConstraints(4, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(6, 19, 0, 13), 53, 0));
        mainPanel.add(portText, new GridBagConstraints(1, 1, 5, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(9, 0, 0, 0), 242, 6));
        mainPanel.add(addressText, new GridBagConstraints(1, 2, 5, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(8, 0, 0, 0), 242, 6));
        mainPanel.add(pieceMoveLimitTimeText,
                new GridBagConstraints(5, 4, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(9, 0, 18, 0), 40, 7));
        mainPanel.add(gameLimitTimeText,
                new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(9, 0, 18, 0), 122, 7));
        mainPanel.add(secLabel, new GridBagConstraints(6, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(11, 6, 18, 22), 9, 8));
        mainPanel.add(serverRadio, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(6, 0, 0, 0), 77, 0));
        mainPanel.add(minuteLabel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 7, 18, 8), 32, 10));
        mainPanel.add(chineseChess, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(10, 0, 0, 6), 33, -1));
        mainPanel.add(pieceMoveLimitTimeLabel,
                new GridBagConstraints(3, 4, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE,
                new Insets(10, 0, 18, 5), 15, 10));
        mainPanel.add(portLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(9, 15, 0, 0), 69, 11));
        mainPanel.add(addressLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(8, 25, 0, 0), 9, 11));
        mainPanel.add(iMoveFirstCheck,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(9, 13, 0, 4), 17, 0));
        mainPanel.add(gameLimitTimeLabel,
                new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(9, 15, 18, 0), 18, 12));
        mainPanel.add(paddingPanel, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 7, 0, 0), 19, 7));
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            super.processWindowEvent(e);
            System.exit(0);
        } else {
            super.processWindowEvent(e);
        }
    }

    private void startup() {
        try {
            final Match match;
            final Chatter chater = new Chatter();
            ChessType type = mysteryChessChk.isSelected()
                    ? ChessType.MYSTERY_CHESS
                    : ChessType.NORMAL_CHESS;
            TeamColor color = TeamColor.BLACK;
            if (iMoveFirstCheck.isSelected()) {
                color = TeamColor.WHITE;
            }
            int port = Integer.parseInt(portText.getText());
            final String title;

            if (serverRadio.isSelected()) {
                try {
                    // Time in minute
                    int gameLimitTime = Integer.parseInt(gameLimitTimeText.getText());
                    Util.GAME_EXPIRE_TIME = gameLimitTime * 60 * 1000;
                } catch (NumberFormatException e) {
                }
                try {
                    // Time in second
                    int pieceMoveLimitTime = Integer.parseInt(pieceMoveLimitTimeText.getText());
                    Util.PIECE_MOVE_EXPIRE_TIME = pieceMoveLimitTime * 1000;
                } catch (NumberFormatException e) {
                }

                title = "Mystery Chess Server - " + Util.getVersion();
                match = new Match(type, color);

                RmiServer s = new RmiServer(match, port, chater);
                s.startup();
            } else {
                title = "Mystery Chess Client - " + Util.getVersion();
                String serverAddress = addressText.getText();
                RmiClient client = new RmiClient(serverAddress, port, chater);
                match = client.startup();
            }
            if (match != null) {
                this.setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        new MainFrame(match, chater, title).setVisible(true);
                    }
                });
            }
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "Invalid port number");
        } catch (UnknownHostException ue) {
             Logger.getLogger(StartupFrame.class.getName()).log(Level.SEVERE, "Problem happen while connecting to server", ue);
            JOptionPane.showMessageDialog(this, "Unknown host");
        } catch (RemoteException e) {
            Logger.getLogger(StartupFrame.class.getName()).log(Level.SEVERE, "Problem happen while starting up", e);
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IOException iOException) {
            Logger.getLogger(StartupFrame.class.getName()).log(Level.SEVERE, "Problem happen while starting up", iOException);
            JOptionPane.showMessageDialog(this, "Problem happen while starting up");
        }
    }
}
