package model;

import java.util.ArrayList;
import java.util.List;

/**
 * SpecialistDoctor class representing a specialist doctor in secondary care.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class SpecialistDoctor extends User {

    // === SPECIALIST-SPECIFIC ATTRIBUTES ===
    private String specialty;
    private List<String> prescriptions;

    // === CONSTRUCTOR ===
    public SpecialistDoctor(String userId, String name, String email, String phone, String specialty) {
        super(userId, name, email, phone);
        this.specialty = specialty;
        this.prescriptions = new ArrayList<>();
    }

    // === OVERRIDE performRole() ===
    @Override
    public void performRole() {
        System.out.println("Specialist Dr. " + name + " is reviewing a patient referral.");
    }

    // === SPECIALIST-SPECIFIC METHODS ===

    public void viewReferral(String referralId) {
        System.out.println("Dr. " + name + " is reviewing referral " + referralId);
    }

    public void createPrescription(String patientId, String medication, String dosage) {
        String prescription = "Specialist prescription for " + patientId + ": " + medication;
        prescriptions.add(prescription);
        System.out.println("Specialist prescription created by Dr. " + name);
    }

    public void updatePatientRecord(String patientId) {
        System.out.println("Specialist Dr. " + name + " has updated record for patient " + patientId);
    }

    // === GETTERS ===

    public String getSpecialty() {
        return specialty;
    }

    public List<String> getPrescriptions() {
        return new ArrayList<>(prescriptions);
    }
}
