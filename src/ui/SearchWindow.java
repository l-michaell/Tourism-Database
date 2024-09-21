package ui;

import delegates.SearchWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class is only responsible for displaying and handling the search GUI.
 */
public class SearchWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the search window
    private JTextField locationField;

    // delegate
    private SearchWindowDelegate delegate;

    public SearchWindow() {
        super("Search By Location");
    }

    public void showFrame(SearchWindowDelegate delegate) {
        this.delegate = delegate;

        JLabel locationLabel = new JLabel("Location Constraints (conditions based on any of {address, climate, lon , lat, siteid, name, and fee} in SQL syntax): ");

        locationField = new JTextField(TEXT_FIELD_WIDTH);

        JButton searchButton = new JButton("Search");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        // place the search button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(searchButton, c);
        contentPane.add(searchButton);

        // register search button with action event handler
        searchButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(900, 150);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);
    }

    public void handleSearchFailed() {
        JOptionPane.showMessageDialog(null, "Enter a location", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        delegate.search(locationField.getText());
    }
}
