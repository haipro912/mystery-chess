/**
 * 
 */
package mysterychess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import mysterychess.Main;
import mysterychess.network.Chatter;

/**
 * @author The Tran
 * 
 */
public class ChatPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String NEWLINE = System.getProperty("line.separator");

    public static final String REGULAR = "regular";
    public static final String ITALIC = "italic";
    public static final String BOLD = "bold";
    public static final String ANIMATION = "animation";

    /**
     * The text pane that supports custom styled (bold, italic, image...)
     */
    private JTextPane outputPane;
    private JTextField inputText = new JTextField();

    private final Chatter chatter;

    public ChatPanel(Chatter chatter) {
        this.chatter = chatter;

        setLayout(new BorderLayout());
        outputPane = createTextPane();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setSize(new Dimension(100, 20));
        inputPanel.add(inputText, BorderLayout.CENTER);

        JScrollPane areaScrollPane = new JScrollPane(outputPane);
        areaScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(areaScrollPane, BorderLayout.CENTER);
        add(inputText, BorderLayout.SOUTH);

        addListeners();
    }

    private JTextPane createTextPane() {
        JTextPane textPane = new JTextPane();

        textPane.setAutoscrolls(true);
        textPane.setEditable(false);
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument(doc);

        return textPane;
    }

    private void appendString(String text, String style) {
        StyledDocument doc = outputPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, doc.getStyle(style));
        } catch (BadLocationException ble) {
            System.err.println("Couldn't insert text into the chat pane.");
        }
    }

    private void appendAnimationGif(int gifCode) {
        StyledDocument doc = outputPane.getStyledDocument();
        try {
            setAnimationGif(doc, "../anim/" + gifCode + ".gif");
            doc.insertString(doc.getLength(), " ", doc.getStyle(ANIMATION));
        } catch (BadLocationException ble) {
            System.err
                    .println("Couldn't insert animation gif into the chat pane.");
        }
    }

    private String processInput(String input) {
        input = input.replace(":))", ":2:");
        input = input.replace(":((", ":1:");
        input = input.replace(":]", ":11:");
        input = input.replace(":\")", ":5:");
        input = input.replace(">\"<", ":9:");
        input = input.replace(";))", ":14:");
        input = input.replace(":)", ":20:");
        return input;
    }

    private void updateTextPane(String input) {
        input = processInput(input);

        int j = -1;
        do {
            j = input.indexOf(":");
            if (j == -1) {
                appendString(input, REGULAR);
            } else {
                if (j > 0) {
                    String plainText = input.substring(0, j);
                    appendString(plainText, REGULAR);
                }
                // extract animation code
                int k = -1;
                if (j + 1 < input.length()) {
                    k = input.indexOf(":", j + 1);
                    if (k != -1) {
                        String code = input.substring(j + 1, k);
                        int gifCode = 20;
                        try {
                            gifCode = Integer.parseInt(code);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        appendAnimationGif(gifCode);
                    }
                }
                if (k == -1) {
                    input = input.substring(j);
                    appendString(input, REGULAR);
                    break;
                } else {
                    input = input.substring(k + 1);
                }
            }
        } while (j != -1);
    }

    private void addListeners() {
        inputText.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String text = inputText.getText().trim();
                if (text.length() > 0) {
                    appendString(NEWLINE + "You: ", BOLD);
                    updateTextPane(inputText.getText());

                    chatter.messageTyped(inputText.getText());
                    inputText.setText("");
                }
            }
        });

        chatter.addMessageReceivedListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String msg = (String) e.getSource();
                appendString(NEWLINE + "Guest: ", BOLD);
                updateTextPane(msg);
                showMessageReceivedIcon();
            }

            private void showMessageReceivedIcon() {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            Color oldColor = outputPane.getBackground();
                            outputPane.setBackground(Color.cyan);
                            sleep(300);
                            outputPane.setBackground(oldColor);
                        } catch (InterruptedException ex) {
                        }
                    }
                };
                t.start();
            }
        });

    }

    protected void addStylesToDocument(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(
                StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle(REGULAR, def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle(ITALIC, regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle(BOLD, regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle(ANIMATION, regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
    }

    private void setAnimationGif(StyledDocument doc, String path) {
        Style s = doc.getStyle(ANIMATION);
        ImageIcon icon = createImageIcon(path, "");
        if (icon != null) {
            StyleConstants.setIcon(s, icon);
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = Main.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
