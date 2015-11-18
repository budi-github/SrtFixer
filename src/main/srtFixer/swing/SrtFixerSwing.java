package main.srtFixer.swing;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * SrtFixer Swing program launcher.
 * 
 * @author budi
 */
public class SrtFixerSwing {

    /**
     * Main function. Launch the swing application.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingView.getInstance().setVisible(true);
            }
        });
    }

}
