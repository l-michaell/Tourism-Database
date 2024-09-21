package ui;

import delegates.UpdateWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class is only responsible for displaying and handling the update GUI.
 */
public class UpdateWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the update window
    private JTextField fieldNameField;
    private JTextField newFieldValField;
    private JTextField siteIDField;

    // delegate
    private UpdateWindowDelegate delegate;

    public UpdateWindow() {
        super("Update Event Name");
    }

    public void showFrame(UpdateWindowDelegate delegate) {
        this.delegate = delegate;

        JLabel fieldNameLabel = new JLabel("Field Name: ");

        fieldNameField = new JTextField(TEXT_FIELD_WIDTH);

        JLabel newFieldValLabel = new JLabel("New Value: ");

        newFieldValField = new JTextField(TEXT_FIELD_WIDTH);

        JLabel siteIDLabel = new JLabel("Enter the ID: ");

        siteIDField = new JTextField(TEXT_FIELD_WIDTH);

        JButton updateButton = new JButton("Update");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the old event name label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(fieldNameLabel, c);
        contentPane.add(fieldNameLabel);

        // place the text field for the field name field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(fieldNameField, c);
        contentPane.add(fieldNameField);

        // label for new val
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(newFieldValLabel, c);
        contentPane.add(newFieldValLabel);

        // place the text field new val
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(newFieldValField, c);
        contentPane.add(newFieldValField);

        // place the siteID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(siteIDLabel, c);
        contentPane.add(siteIDLabel);

        // place the text field for siteID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(siteIDField, c);
        contentPane.add(siteIDField);


        // place the update button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(updateButton, c);
        contentPane.add(updateButton);

        // register update button with action event handler
        updateButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(300, 250);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);
    }

    public void handleUpdateFailed() {
        fieldNameField.setText(""); // clear old event name field
        newFieldValField.setText(""); // clear new event name field
        siteIDField.setText("");
        JOptionPane.showMessageDialog(null, "Enter valid inputs", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        delegate.update(fieldNameField.getText(), newFieldValField.getText(), siteIDField.getText());
    }
}
