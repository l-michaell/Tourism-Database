package ui;

import delegates.ProjectionWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class is only responsible for displaying and handling the projection GUI.
 */
public class ProjectionWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the projection window
    private JTextField relationField;
    private JTextField attributeField;

    // delegate
    private ProjectionWindowDelegate delegate;

    public ProjectionWindow() {
        super("Projection");
    }

    public void showFrame(ProjectionWindowDelegate delegate) {
        this.delegate = delegate;

        JLabel relationLabel = new JLabel("Relation: ");

        relationField = new JTextField(TEXT_FIELD_WIDTH);

        JLabel attributeLabel = new JLabel("Attributes: ");

        attributeField = new JTextField(TEXT_FIELD_WIDTH);

        JButton projectionButton = new JButton("Projection");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the relation label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(relationLabel, c);
        contentPane.add(relationLabel);

        // place the text field for the relation
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(relationField, c);
        contentPane.add(relationField);

        // place the attributes label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(attributeLabel, c);
        contentPane.add(attributeLabel);

        // place the text field for the attributes
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(attributeField, c);
        contentPane.add(attributeField);

        // place the projection button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(projectionButton, c);
        contentPane.add(projectionButton);

        // register projection button with action event handler
        projectionButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(300, 200);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);
    }

    public void handleProjectionFailed() {
        JOptionPane.showMessageDialog(null, "Enter valid inputs", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        delegate.projection(relationField.getText(), attributeField.getText());
    }
}
