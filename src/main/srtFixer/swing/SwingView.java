package main.srtFixer.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.srtFixer.SrtFixer;

/**
 * SrtFixer Swing view.
 * 
 * @author budi
 */
public class SwingView extends JFrame implements PropertyChangeListener {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Application name.
     */
    private static final String applicationName = "SrtFixer";

    /**
     * Help label text.
     */
    private static final String helpLabelText =
            "~~~ If the subtitles appear too FAST, INCREASE time. ~~~ If the subtitles appear too SLOW, DECREASE time. ~~~";

    /**
     * Media {@link JLabel}.
     */
    private static final JLabel mediaLabel = new JLabel("Media Name:");

    /**
     * Media {@link JTextField}.
     */
    private static final JTextField mediaField = new JTextField(30);

    /**
     * Resync {@link JLabel}.
     */
    private static final JLabel resyncLabel = new JLabel("Resync:");

    /**
     * Resync {@link JTextField}.
     */
    private static final JTextField resyncField = new JTextField(2);

    /**
     * Help {@link JLabel}.
     */
    private static final JLabel helpLabel = new JLabel(helpLabelText);

    /**
     * Fix Button {@link JButton}.
     */
    private static final JButton fixButton = new JButton("Fix!");

    /**
     * Instance of {@link SwingView}.
     */
    private static final SwingView instance = new SwingView();

    /**
     * Get instance of {@link SwingView}.
     * 
     * @return instance of {@link SwingView}
     */
    public static SwingView getInstance() {
        return instance;
    }

    /**
     * Private class constructor.
     */
    private SwingView() {
        super(applicationName);

        fixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                analyze(event);
            }

            private void analyze(ActionEvent event) {
                try {
                    if (!mediaField.getText().isEmpty()) {
                        if (resyncField.getText().isEmpty()) {
                            resyncField.setText("0");
                        }
                        SrtFixer.run(mediaField.getText(), Double.parseDouble(resyncField.getText()), true);
                        helpLabel.setText(helpLabelText);
                    }
                } catch (Exception e) {
                    helpLabel.setText(e.getMessage());
                }
            }
        });

        // set up layout
        setLayout(new GridBagLayout());
        setResizable(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;

        constraints.gridy = 0;

        constraints.gridx = 0;
        add(mediaLabel, constraints);

        constraints.gridx = 1;
        add(mediaField, constraints);

        constraints.gridx = 2;
        add(resyncLabel, constraints);

        constraints.gridx = 3;
        add(resyncField, constraints);

        constraints.gridx = 4;
        add(fixButton, constraints);

        constraints.gridwidth = 4;
        constraints.gridy = 1;

        constraints.gridx = 0;
        add(helpLabel, constraints);

        pack();
        setLocationRelativeTo(null); // center screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
        // pass
    }

}
