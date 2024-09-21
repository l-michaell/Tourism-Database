package ui;

import delegates.JoinWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class is only responsible for displaying and handling the join GUI.
 */
public class JoinWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the join window
    private JTextField scoreField;

    // delegate
    private JoinWindowDelegate delegate;

    public JoinWindow() {
        super("Join by Score");
    }

    public void showFrame(JoinWindowDelegate delegate) {
        this.delegate = delegate;

        JLabel scoreLabel = new JLabel("Score: ");

        scoreField = new JTextField(TEXT_FIELD_WIDTH);

        JButton joinButton = new JButton("Join");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the score label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(scoreLabel, c);
        contentPane.add(scoreLabel);

        // place the text field for the score
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(scoreField, c);
        contentPane.add(scoreField);

        // place the join button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(joinButton, c);
        contentPane.add(joinButton);

        // register join button with action event handler
        joinButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(300, 150);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);
    }

    public void handleJoinFailed() {
        JOptionPane.showMessageDialog(null, "Enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        delegate.join(Integer.parseInt(scoreField.getText()));
    }
}
