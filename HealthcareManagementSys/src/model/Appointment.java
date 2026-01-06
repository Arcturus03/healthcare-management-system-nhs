package model;

/**
 * Appointment class representing a scheduled appointment in the healthcare system.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Appointment {

    // === ATTRIBUTES ===
    private String appointmentId;
    private String patientId;
    private String clinicianId;
    private String dateTime;              // Format: "YYYY-MM-DD HH:MM"
    private String location;
    private String status;                // "Scheduled", "Completed", "Cancelled"
    private String reason;                // Reason for appointment

    // === CONSTRUCTOR ===
    public Appointment(String appointmentId, String patientId, String clinicianId, String dateTime, String location) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.dateTime = dateTime;
        this.location = location;
        this.status = "Scheduled";
    }

    // === METHODS ===

    public void create() {
        System.out.println("Appointment " + appointmentId + " created for " + patientId);
    }

    public void amend(String newDateTime, String newLocation) {
        this.dateTime = newDateTime;
        this.location = newLocation;
        System.out.println("Appointment " + appointmentId + " amended.");
    }

    public void cancel() {
        this.status = "Cancelled";
        System.out.println("Appointment " + appointmentId + " has been cancelled.");
    }

    public void checkIn() {
        this.status = "Completed";
        System.out.println("Patient " + patientId + " has checked in to appointment.");
    }

    // === GETTERS & SETTERS ===

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
