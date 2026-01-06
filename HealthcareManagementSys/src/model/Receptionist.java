package model;

/**
 * Receptionist class representing administrative staff.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Receptionist extends User {

    // === RECEPTIONIST-SPECIFIC ATTRIBUTES ===
    private String workStation;
    private int patientsRegistered;

    // === CONSTRUCTOR ===
    public Receptionist(String userId, String name, String email, String phone, String workStation) {
        super(userId, name, email, phone);
        this.workStation = workStation;
        this.patientsRegistered = 0;
    }

    // === OVERRIDE performRole() ===
    @Override
    public void performRole() {
        System.out.println(name + " is managing patient registrations and appointments.");
    }

    // === RECEPTIONIST-SPECIFIC METHODS ===

    public void registerPatient(String patientId) {
        patientsRegistered++;
        System.out.println(name + " has registered patient " + patientId);
    }

    public void assignCarer(String patientId, String carerId) {
        System.out.println(name + " has assigned carer " + carerId + " to patient " + patientId);
    }

    public void manageAppointments() {
        System.out.println(name + " is managing appointments.");
    }

    public void confirmCarer(String patientId, String carerId) {
        System.out.println(name + " has confirmed carer assignment for patient " + patientId);
    }

    // === GETTERS ===

    public String getWorkStation() {
        return workStation;
    }

    public int getPatientsRegistered() {
        return patientsRegistered;
    }
}
