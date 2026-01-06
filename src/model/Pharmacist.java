package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pharmacist class representing pharmacy staff.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Pharmacist extends User {

    // === PHARMACIST-SPECIFIC ATTRIBUTES ===
    private String licenseNumber;
    private List<String> verifiedPrescriptions;

    // === CONSTRUCTOR ===
    public Pharmacist(String userId, String name, String email, String phone, String licenseNumber) {
        super(userId, name, email, phone);
        this.licenseNumber = licenseNumber;
        this.verifiedPrescriptions = new ArrayList<>();
    }

    // === OVERRIDE performRole() ===
    @Override
    public void performRole() {
        System.out.println(name + " is verifying and dispensing prescriptions.");
    }

    // === PHARMACIST-SPECIFIC METHODS ===

    public void verifyPrescription(String prescriptionId) {
        verifiedPrescriptions.add(prescriptionId);
        System.out.println("Pharmacist " + name + " has verified prescription " + prescriptionId);
    }

    public void issuePrescription(String patientId, String medication) {
        System.out.println("Pharmacist " + name + " has issued " + medication + " to patient " + patientId);
    }

    // === GETTERS ===

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public List<String> getVerifiedPrescriptions() {
        return new ArrayList<>(verifiedPrescriptions);
    }
}
