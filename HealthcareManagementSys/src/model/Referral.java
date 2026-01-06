package model;

import java.time.LocalDate;

/**
 * Referral class representing a referral from GP to Specialist.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Referral {

    // === ATTRIBUTES ===
    private String referralId;
    private String patientId;
    private String fromGpId;              // GP making the referral
    private String toSpecialistId;        // Specialist receiving the referral
    private String reason;
    private String status;                // "Pending", "Received", "Completed"
    private LocalDate referralDate;
    private String urgencyLevel;          // "Routine", "Urgent", "Emergency"
    private String clinicalSummary;

    // === CONSTRUCTOR ===
    public Referral(String referralId, String patientId, String fromGpId, String toSpecialistId, String reason) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.fromGpId = fromGpId;
        this.toSpecialistId = toSpecialistId;
        this.reason = reason;
        this.status = "Pending";
        this.referralDate = LocalDate.now();
        this.urgencyLevel = "Routine";
    }

    // === METHODS ===

    public void create() {
        System.out.println("Referral " + referralId + " created for patient " + patientId);
    }

    public void update() {
        System.out.println("Referral " + referralId + " has been updated.");
    }

    public void close() {
        this.status = "Completed";
        System.out.println("Referral " + referralId + " has been closed.");
    }

    // === GETTERS & SETTERS ===

    public String getReferralId() {
        return referralId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFromGpId() {
        return fromGpId;
    }

    public String getToSpecialistId() {
        return toSpecialistId;
    }

    public void setToSpecialistId(String toSpecialistId) {
        this.toSpecialistId = toSpecialistId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReferralDate() {
        return referralDate;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getClinicalSummary() {
        return clinicalSummary;
    }

    public void setClinicalSummary(String clinicalSummary) {
        this.clinicalSummary = clinicalSummary;
    }
}
