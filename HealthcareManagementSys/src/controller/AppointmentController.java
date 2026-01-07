package controller;

import model.Appointment;
import java.util.*;

/**
 * AppointmentController manages all Appointment operations (CRUD).
 *
 * NOTE (IMPORTANT):
 * This controller is written to match Appointment.java exactly:
 * - Uses clinicianId (not gpId)
 * - Uses dateTime as a single field (not appointmentDate + time)
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class AppointmentController {

    // === INSTANCE VARIABLES ===
    // This list holds ALL appointments in the system.
    // Each time an appointment is added/removed, this list changes.
    private List<Appointment> appointments;

    /**
     * CONSTRUCTOR
     *
     * Initializes an empty appointment list ready to use.
     */
    public AppointmentController() {
        this.appointments = new ArrayList<>();
    }

    /**
     * CREATE: Add a new appointment to the system.
     *
     * @param appointment Appointment object to add
     * @return true if added successfully, false if null
     */
    public boolean addAppointment(Appointment appointment) {
        if (appointment == null) {
            System.err.println("ERROR: Cannot add null appointment");
            return false;
        }

        appointments.add(appointment);

        // Appointment.java provides getDateTime(), not getAppointmentDate().
        System.out.println("✓ Appointment added: " + appointment.getAppointmentId()
                + " - " + appointment.getPatientId()
                + " at " + appointment.getDateTime());

        return true;
    }

    /**
     * READ: Get a specific appointment by ID.
     *
     * @param appointmentId Appointment ID to search for
     * @return Appointment object if found, otherwise null
     */
    public Appointment getAppointment(String appointmentId) {
        return appointments.stream()
                .filter(apt -> apt.getAppointmentId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }

    /**
     * READ: Get all appointments for a specific patient.
     *
     * @param patientId Patient ID
     * @return List of appointments for that patient
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointments.stream()
                .filter(apt -> apt.getPatientId().equals(patientId))
                .toList();
    }

    /**
     * READ: Get all appointments for a specific clinician (doctor/nurse/etc.).
     *
     * Appointment.java uses clinicianId, not gpId.
     *
     * @param clinicianId Clinician ID
     * @return List of appointments for that clinician
     */
    public List<Appointment> getAppointmentsByClinician(String clinicianId) {
        return appointments.stream()
                .filter(apt -> apt.getClinicianId().equals(clinicianId))
                .toList();
    }

    /**
     * UPDATE: Modify an existing appointment.
     *
     * Appointment.java stores the schedule as ONE string: dateTime ("YYYY-MM-DD HH:MM").
     * So we update it using setDateTime().
     *
     * @param appointmentId Appointment ID to update
     * @param newDateTime New dateTime string (e.g., "2026-01-10 14:30")
     * @param newLocation New location (e.g., "Room 3")
     * @param newStatus New status (Scheduled, Completed, Cancelled)
     * @param newReason New reason (optional)
     * @return true if updated, false if not found
     */
    public boolean updateAppointment(String appointmentId,
                                     String newDateTime,
                                     String newLocation,
                                     String newStatus,
                                     String newReason) {

        Appointment existing = getAppointment(appointmentId);

        if (existing == null) {
            System.err.println("ERROR: Appointment " + appointmentId + " not found");
            return false;
        }

        // Only update fields if new values are provided
        if (newDateTime != null && !newDateTime.isBlank()) {
            existing.setDateTime(newDateTime);
        }

        if (newLocation != null && !newLocation.isBlank()) {
            existing.setLocation(newLocation);
        }

        if (newStatus != null && !newStatus.isBlank()) {
            existing.setStatus(newStatus);
        }

        if (newReason != null && !newReason.isBlank()) {
            existing.setReason(newReason);
        }

        System.out.println("✓ Appointment updated: " + appointmentId);
        return true;
    }

    /**
     * DELETE: Remove an appointment from the system.
     *
     * @param appointmentId Appointment ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteAppointment(String appointmentId) {
        boolean removed = appointments.removeIf(apt -> apt.getAppointmentId().equals(appointmentId));

        if (removed) {
            System.out.println("✓ Appointment deleted: " + appointmentId);
        } else {
            System.err.println("ERROR: Appointment " + appointmentId + " not found");
        }

        return removed;
    }

    /**
     * READ: Get all appointments (safe copy).
     *
     * @return Copy of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * COUNT: Total number of appointments.
     *
     * @return Total appointments
     */
    public int getAppointmentCount() {
        return appointments.size();
    }

    /**
     * POPULATE FROM CSV DATA
     *
     * @param loadedAppointments Appointments loaded from CSV
     */
    public void loadAppointmentsFromData(List<Appointment> loadedAppointments) {
        appointments.clear();

        if (loadedAppointments != null) {
            appointments.addAll(loadedAppointments);
        }

        System.out.println("✓ Loaded " + appointments.size() + " appointments into controller");
    }
}
