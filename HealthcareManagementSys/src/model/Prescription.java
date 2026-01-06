package model;

import java.time.LocalDate;

/**
 * Prescription class representing a medication prescription.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Prescription {

    // === ATTRIBUTES ===
    private String prescriptionId;
    private String patientId;
    private String clinicianId;           // GP or Specialist who issued
    private String medication;
    private String dosage;
    private String duration;              // How long to take it
    private String status;                // "Issued", "Collected", "Expired"
    private LocalDate issuedDate;
    private String notes;

    // === CONSTRUCTOR ===
    public Prescription(String prescriptionId, String patientId, String clinicianId, String medication, String dosage) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.medication = medication;
        this.dosage = dosage;
        this.status = "Issued";
        this.issuedDate = LocalDate.now();
    }

    // === METHODS ===

    public void issue() {
        this.status = "Issued";
        System.out.println("Prescription " + prescriptionId + " issued for patient " + patientId);
    }

    public void validate() {
        System.out.println("Prescription " + prescriptionId + " has been validated.");
    }

    public void view() {
        System.out.println("Prescription " + prescriptionId + ": " + medication + " " + dosage);
    }

    // === GETTERS & SETTERS ===

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
