import javax.swing.SwingUtilities;
import view.HealthcareGUI;

/**
 * Main Entry Point for the Healthcare Management System.
 */
public class main {
    public static void main(String[] args) {
        // Enforce thread safety for Swing components
        SwingUtilities.invokeLater(() -> {
            try {
                // Initialize the Main GUI Frame
                HealthcareGUI  healthcareGUI = new HealthcareGUI();
                healthcareGUI.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

