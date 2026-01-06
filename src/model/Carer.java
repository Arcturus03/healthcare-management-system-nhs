package model;

/**
 * Carer class representing a carer/resident party in the healthcare system.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Carer extends User {

    // === CARER-SPECIFIC ATTRIBUTES ===
    private String relationType;           // Relationship to patient (e.g., "Family", "Guardian")
    private String assignedPatientId;      // ID of patient they care for
    private String accountInfo;

    // === CONSTRUCTOR ===
    public Carer(String userId, String name, String email, String phone, String relationType) {
        super(userId, name, email, phone);
        this.relationType = relationType;
        this.accountInfo = "Active";
    }

    // === OVERRIDE performRole() ===
    @Override
    public void performRole() {
        System.out.println(name + " is assisting the patient with medical care.");
    }

    // === CARER-SPECIFIC METHODS ===

    public void requestAssignment(String patientId) {
        this.assignedPatientId = patientId;
        System.out.println(name + " has requested assignment to patient " + patientId);
    }

    public void manageAppointments(String patientId) {
        System.out.println(name + " is managing appointments for patient " + patientId);
    }

    public void viewPatientRecord(String patientId) {
        System.out.println(name + " is viewing medical record for patient " + patientId);
    }

    // === GETTERS & SETTERS ===

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        if (relationType != null && !relationType.isEmpty()) {
            this.relationType = relationType;
        }
    }

    public String getAssignedPatientId() {
        return assignedPatientId;
    }

    public void setAssignedPatientId(String patientId) {
        this.assignedPatientId = patientId;
    }
}
