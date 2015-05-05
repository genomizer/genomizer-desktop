package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import responses.ResponseParser;

import util.ErrorMessageGenerator;
import util.RequestException;

/**
 *
 * Class for displaying information about errors to the user in a dialog.
 *
 * @author oi12mlw & oi12pjn
 *
 */
public class ErrorDialog extends JOptionPane {

    private static final long serialVersionUID = 1L;
    private static final int SCROLL_PANE_WIDTH = 300;
    private static final int SCROLL_PANE_HEIGHT = 150;

    private static Component parentComponent = null;

    private String title;
    private String simpleMessage;
    private String extendedMessage;
    private JPanel bodyPanel;
    private JDialog dialog;
    private JScrollPane messageScrollPane;
    private JButton moreInfoButton;
    private boolean expanded = false;
    private JButton okButton;

    /**
     * Constructs a new ErrorDialog object with title, and a simple and extended
     * message.
     *
     * @param title
     *            the title of the dialog
     * @param simpleMessage
     *            a simple message with information about the error
     * @param extendedMessage
     *            an extended message with more descriptive information about
     *            the error
     */
    public ErrorDialog(String title, String simpleMessage,
            String extendedMessage) {
        this.title = title;
        this.simpleMessage = simpleMessage;
        this.extendedMessage = extendedMessage;
        buildBodyPanel();
    }

    public ErrorDialog(String title, RequestException e)  {
        String responseBody = e.getResponseBody();
        String simpleMessage = ResponseParser.parseErrorResponse(responseBody).message;
        String extendedMessage = ErrorMessageGenerator.generateMessage(e.getResponseCode());
        this.title = title;
        this.simpleMessage = simpleMessage;
        this.extendedMessage = extendedMessage;
        buildBodyPanel();
    }

    private void buildBodyPanel() {
        bodyPanel = new JPanel(new BorderLayout());

        buildTopPanel();

        buildButtonPanel();

        buildTextArea();

    }

    private void buildTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JLabel simpleMessageLabel = new JLabel(simpleMessage);
        topPanel.add(simpleMessageLabel);
        topPanel.add(new JLabel(""));
        bodyPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void buildTextArea() {

        JTextArea messageTextArea = new JTextArea(extendedMessage);
        messageTextArea.setEditable(false);
        messageTextArea.setLineWrap(true);

        messageScrollPane = new JScrollPane(messageTextArea);
        messageScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        messageScrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        bodyPanel.add(messageScrollPane, BorderLayout.CENTER);
        Dimension size = new Dimension(SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT);
        messageScrollPane.setPreferredSize(size);
        messageScrollPane.setVisible(false);
    }

    private void buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        moreInfoButton = new JButton("More info");
        okButton = new JButton("OK");
        buttonPanel.add(moreInfoButton);
        buttonPanel.add(okButton);
        okButton.addActionListener(new OkButtonListener());
        moreInfoButton.addActionListener(new MoreButtonListener());
        bodyPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Shows the error dialog.
     */
    public void showDialog() {

        JOptionPane optionPane = new JOptionPane(bodyPanel, ERROR_MESSAGE,
                DEFAULT_OPTION, null, new Object[] {}, null);
        dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(optionPane);
        dialog.addKeyListener(new DialogListener());
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);

    }

    public static void setParentComponent(Component parentComponent) {
        ErrorDialog.parentComponent = parentComponent;
    }

    private class DialogListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                System.out.println("ENTER!");
                okButton.doClick();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

    }

    private class OkButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();

        }
    }

    private class MoreButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (expanded) {
                expanded = false;
                messageScrollPane.setVisible(false);
                moreInfoButton.setText("More info");

            } else {
                expanded = true;
                messageScrollPane.setVisible(true);
                moreInfoButton.setText("Less info");
            }
            dialog.pack();
        }

    }


    public static void showRequestErrorDialog(String title, RequestException e) {
        String responseBody = e.getResponseBody();
        String message = ResponseParser.parseErrorResponse(responseBody).message;
        String extendedMessage = e.getResponseCode() + ":\n" + responseBody;
        ErrorDialog errorDialog = new ErrorDialog(title, message,
                extendedMessage);
        errorDialog.showDialog();
    }

}
