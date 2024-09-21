package ui;

import delegates.MainWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The class is only responsible for displaying and handling the main GUI.
 */
public class MainWindow extends JFrame implements ActionListener {
    // delegate
    private MainWindowDelegate delegate;

    public MainWindow() {
        super("Welcome to Toursite Main Page!");
    }

    public void showFrame(MainWindowDelegate delegate) {
        this.delegate = delegate;

        JButton insertWindowButton = new JButton("Insert A New Location");
        JButton deleteWindowButton = new JButton("Delete A Toursite Location");
        JButton updateWindowButton = new JButton("Update Events");
        JButton searchWindowButton = new JButton("Search Events");
        JButton projectionWindowButton = new JButton("View columns (attribute) of a table (relation)");
        JButton joinWindowButton = new JButton("Find Reviews of Toursites With Score Greater Than");
        JButton groupByButton = new JButton("Group By Country Name");
        JButton havingButton = new JButton("Show Toursites Having Multiple Event Offerings");
        JButton nestedGroupByButton = new JButton("Find Toursites With Events Longer Than 1 Hour");
        JButton divisionButton = new JButton(" Find Toursites Where There Are All Types of Events");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the insert window button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(insertWindowButton, c);
        contentPane.add(insertWindowButton);

        // place the delete window button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(deleteWindowButton, c);
        contentPane.add(deleteWindowButton);

        // place the update window button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(updateWindowButton, c);
        contentPane.add(updateWindowButton);

        // place the search window button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(searchWindowButton, c);
        contentPane.add(searchWindowButton);

        // place the projection window button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(projectionWindowButton, c);
        contentPane.add(projectionWindowButton);

        // place the join window button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(joinWindowButton, c);
        contentPane.add(joinWindowButton);

        // place the group by button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(groupByButton, c);
        contentPane.add(groupByButton);

        // place the having button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(havingButton, c);
        contentPane.add(havingButton);

        // place the nested group by button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(nestedGroupByButton, c);
        contentPane.add(nestedGroupByButton);

        // place the division button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(divisionButton, c);
        contentPane.add(divisionButton);

        // register window buttons with action event handler
        insertWindowButton.addActionListener(this);
        deleteWindowButton.addActionListener(this);
        updateWindowButton.addActionListener(this);
        searchWindowButton.addActionListener(this);
        projectionWindowButton.addActionListener(this);
        joinWindowButton.addActionListener(this);
        groupByButton.addActionListener(this);
        havingButton.addActionListener(this);
        nestedGroupByButton.addActionListener(this);
        divisionButton.addActionListener(this);

        // set action command for each button
        insertWindowButton.setActionCommand("InsertWindow");
        deleteWindowButton.setActionCommand("DeleteWindow");
        updateWindowButton.setActionCommand("UpdateWindow");
        searchWindowButton.setActionCommand("SearchWindow");
        projectionWindowButton.setActionCommand("ProjectionWindow");
        joinWindowButton.setActionCommand("JoinWindow");
        groupByButton.setActionCommand("GroupBy");
        havingButton.setActionCommand("Having");
        nestedGroupByButton.setActionCommand("NestedGroupBy");
        divisionButton.setActionCommand("Division");

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setSize(400, 500);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String windowName = e.getActionCommand();
        delegate.openWindow(windowName);
    }
}