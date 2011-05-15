package mysterychess.view;

import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import mysterychess.model.ChessType;
import javax.swing.*;
import mysterychess.util.Util;

/**
 * Tin Bui-Huy
 */
public class NewGamePanel extends JDialog {

    ActionListener listener;
    JButton okButton = new JButton();
    JButton cancelButton = new JButton();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JRadioButton normalChessRadio = new JRadioButton();
    JRadioButton mysteryChessRadio = new JRadioButton();
    JCheckBox iMoveFirstCheck = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    JTextField gameLimitTimeText = new JTextField();
    JLabel jLabel2 = new JLabel();
    JTextField pieceMoveLimitTimeText = new JTextField();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();

    public NewGamePanel() {
        try {
            initComponent();
            this.pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponent() throws Exception {
        this.setTitle("Create new game");
        this.setModal(true);
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                setTimeLimits();
                listener.actionPerformed(e);
            }
        });
        this.setLayout(borderLayout1);
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        jPanel2.setLayout(gridBagLayout1);
        gridLayout1.setHgap(5);
        jPanel3.setLayout(gridLayout1);
        normalChessRadio.setText("Chinese Chess");
        mysteryChessRadio.setSelected(true);
        mysteryChessRadio.setText("Mystery Chess");
        iMoveFirstCheck.setSelected(true);
        iMoveFirstCheck.setText("I move first");
        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setText("Game limit time:");
        gameLimitTimeText.setText(String.valueOf(Util.GAME_EXPIRE_TIME/(60*1000)));
        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setText("Piece move limit time:");
        pieceMoveLimitTimeText.setText(String.valueOf(Util.PIECE_MOVE_EXPIRE_TIME/1000));
        jLabel3.setText("mins");
        jLabel4.setText("secs");
        this.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel3.add(okButton);
        jPanel3.add(cancelButton);
        this.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jPanel3);
        buttonGroup1.add(mysteryChessRadio);
        buttonGroup1.add(normalChessRadio);
        jPanel2.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(11, 55, 0, 0), 37, 0));
        jPanel2.add(gameLimitTimeText,
                new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(9, 7, 0, 0), 54, 0));
        jPanel2.add(jLabel3, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(11, 6, 0, 37), 13, 0));
        jPanel2.add(mysteryChessRadio,
                new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(20, 7, 0, 37), 20, 0));
        jPanel2.add(normalChessRadio,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(20, 43, 0, 0), 28, 0));
        jPanel2.add(iMoveFirstCheck,
                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(8, 43, 0, 0), 43, 0));
        jPanel2.add(jLabel2, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(12, 55, 16, 0), 10, 0));
        jPanel2.add(pieceMoveLimitTimeText,
                new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(10, 7, 15, 0), 49, 0));
        jPanel2.add(jLabel4, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(12, 6, 19, 37), 13, 0));
    }

    public void setListener(ActionListener l) {
        this.listener = l;
    }

    public boolean isMoveFirst() {
        return iMoveFirstCheck.isSelected();
    }

    public ChessType getChessType() {
        if (mysteryChessRadio.isSelected()) {
            return ChessType.MYSTERY_CHESS;
        }
        return ChessType.NORMAL_CHESS;
    }

    private void setTimeLimits() {
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
    }
}
