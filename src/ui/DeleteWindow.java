package ui;

import delegates.DeleteWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class is only responsible for displaying and handling the delete GUI.
 */
public class DeleteWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the delete window
    private JTextField lonField;
    private JTextField latField;
    private JTextField eventNameField;

    // delegate
    private DeleteWindowDelegate delegate;

    public DeleteWindow() {
        super("Delete Event");
    }

    public void showFrame(DeleteWindowDelegate delegate) {
        this.delegate = delegate;

        JLabel lonLabel = new JLabel("Enter the Longitude: ");

        lonField = new JTextField(TEXT_FIELD_WIDTH);

        JLabel latLabel = new JLabel("Enter the Latitude: ");

        latField = new JTextField(TEXT_FIELD_WIDTH);

        JButton deleteButton = new JButton("Delete");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the country label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(lonLabel, c);
        contentPane.add(lonLabel);

        // place the text field for the longitude
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(lonField, c);
        contentPane.add(lonField);

        // place the latitude label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(latLabel, c);
        contentPane.add(latLabel);

        // place the text field for the latitude
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(latField, c);
        contentPane.add(latField);


        // place the delete button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(deleteButton, c);
        contentPane.add(deleteButton);

        // register delete button with action event handler
        deleteButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(300, 250);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);
    }

    public void handleDeleteFailed() {
        lonField.setText(""); // clear lon field
        latField.setText(""); // clear lat field

        JOptionPane.showMessageDialog(null, "Enter valid inputs", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        delegate.delete(lonField.getText(), latField.getText());
    }
}
