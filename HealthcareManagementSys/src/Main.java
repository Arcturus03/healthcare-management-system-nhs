import javax.swing.SwingUtilities;
import view.HealthcareGUI;

/**
 * Main Entry Point for the Healthcare Management System.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthcareGUI().setVisible(true));
    }
}

