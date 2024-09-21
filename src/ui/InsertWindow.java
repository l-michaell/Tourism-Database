package ui;

import delegates.InsertWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class is only responsible for displaying and handling the insert GUI.
 */
public class InsertWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the insert window
    private JTextField countryField;
    private JTextField locationField;
    private JTextField lonField;

    private JTextField latField;

    // delegate
    private InsertWindowDelegate delegate;

    public InsertWindow() {
        super("Insert Event");
    }

    public void showFrame(InsertWindowDelegate delegate) {
        this.delegate = delegate;

        JLabel countryLabel = new JLabel("Enter the country: ");

        countryField = new JTextField(TEXT_FIELD_WIDTH);

        JLabel locationLabel = new JLabel("Enter the location: ");

        locationField = new JTextField(TEXT_FIELD_WIDTH);

        JLabel lonLabel = new JLabel("Enter the longitude: ");

        lonField = new JTextField(TEXT_FIELD_WIDTH);

        JLabel latLabel = new JLabel("Enter the latitude: ");

        latField = new JTextField(TEXT_FIELD_WIDTH);

        JButton insertButton = new JButton("Insert");

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
        gb.setConstraints(countryLabel, c);
        contentPane.add(countryLabel);

        // place the text field for the country
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(countryField, c);
        contentPane.add(countryField);

        // place the location label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(locationLabel, c);
        contentPane.add(locationLabel);

        // place the text field for the location
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(locationField, c);
        contentPane.add(locationField);

        // place the lon label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(lonLabel, c);
        contentPane.add(lonLabel);

        // place the text field for lon
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(lonField, c);
        contentPane.add(lonField);

        // place the lat label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(latLabel, c);
        contentPane.add(latLabel);

        // place the text field for lat
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(latField, c);
        contentPane.add(latField);

        // place the insert button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(insertButton, c);
        contentPane.add(insertButton);

        // register insert button with action event handler
        insertButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(300, 250);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);
    }

    public void handleInsertFailed() {
        countryField.setText(""); // clear country field
        locationField.setText(""); // clear location field
        lonField.setText(""); // clear lon field
        latField.setText("");//  clear lat field
        JOptionPane.showMessageDialog(null, "Enter valid inputs", "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            delegate.insert(countryField.getText(), locationField.getText(), lonField.getText(), latField.getText());


        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error.getMessage());
        }


    }

}
