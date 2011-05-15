package mysterychess.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import mysterychess.util.Util;

/**
 * Tin Bui-Huy
 */
public class AboutFrame extends JDialog implements ActionListener {
    JPanel sidePanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel insetsPanel1 = new JPanel();
    JPanel insetsPanel2 = new JPanel();
    JPanel insetsPanel3 = new JPanel();
    JButton button1 = new JButton();
    JLabel imageLabel = new JLabel();
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();
    ImageIcon image1 = new ImageIcon();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    GridLayout gridLayout1 = new GridLayout();
    String product = "Mystery Chess";
    String originalAuthor = Util.getOriginalAuthor() + " (huytin@gmail.com)";
    String originalGraphicsDesigner = Util.getGraphicsAuthor();
    String version = Util.getVersion();
    String copyright = "Copyright (c) 2011";
    String comments = "";

    public AboutFrame(Frame parent) {
        super(parent);
        try {
            initComponent();
            pack();
        } catch (Exception exception) {
        }
    }

    public AboutFrame() {
        this(null);
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void initComponent() throws Exception {
        image1 = new ImageIcon(Util.getAboutImage());
        imageLabel.setIcon(image1);
        setTitle("About");
        sidePanel.setLayout(borderLayout1);
        centerPanel.setLayout(borderLayout2);
        insetsPanel1.setLayout(flowLayout1);
        insetsPanel2.setLayout(flowLayout1);
        insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridLayout1.setRows(4);
        gridLayout1.setColumns(1);
        label1.setText(product + " " + version);
        label2.setText("Originally Developed by " + originalAuthor);
        label3.setText("Graphics Designed by " + originalGraphicsDesigner);
        label4.setText(copyright);
        insetsPanel3.setLayout(gridLayout1);
        insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
        button1.setText("OK");
        button1.addActionListener(this);
        insetsPanel2.add(imageLabel, null);
        centerPanel.add(insetsPanel2, BorderLayout.WEST);
        getContentPane().add(sidePanel, null);
        insetsPanel3.add(label1, null);
        insetsPanel3.add(label2, null);
        insetsPanel3.add(label3, null);
        insetsPanel3.add(label4, null);
        centerPanel.add(insetsPanel3, BorderLayout.CENTER);
        insetsPanel1.add(button1, null);
        sidePanel.add(insetsPanel1, BorderLayout.SOUTH);
        sidePanel.add(centerPanel, BorderLayout.NORTH);
        setResizable(false);
    }

    /**
     * Close the dialog on a button event.
     *
     * @param actionEvent ActionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == button1) {
            dispose();
        }
    }
}