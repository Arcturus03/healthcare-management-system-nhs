package model;

/**
 * Nurse class representing nursing staff in the healthcare system.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Nurse extends User {

    // === NURSE-SPECIFIC ATTRIBUTES ===
    private String nursingGrade;           // Grade level (e.g., "Band 5", "Band 6")
    private String assignedPatientId;

    // === CONSTRUCTOR ===
    public Nurse(String userId, String name, String email, String phone, String nursingGrade) {
        super(userId, name, email, phone);
        this.nursingGrade = nursingGrade;
    }

    // === OVERRIDE performRole() ===
    @Override
    public void performRole() {
        System.out.println(name + " is taking patient vitals and updating records.");
    }

    // === NURSE-SPECIFIC METHODS ===

    public void updatePatientRecord(String patientId) {
        System.out.println("Nurse " + name + " has updated record for patient " + patientId);
    }

    public void assistAppointment(String patientId) {
        System.out.println("Nurse " + name + " is assisting with appointment for patient " + patientId);
    }

    public void uploadTestResult(String patientId, String testType, String result) {
        System.out.println("Nurse " + name + " uploaded " + testType + " result for patient " + patientId);
    }

    public void recordVitals(String patientId, String vitals) {
        System.out.println("Nurse " + name + " recorded vitals for patient " + patientId + ": " + vitals);
    }

    // === GETTERS & SETTERS ===

    public String getNursingGrade() {
        return nursingGrade;
    }
}
