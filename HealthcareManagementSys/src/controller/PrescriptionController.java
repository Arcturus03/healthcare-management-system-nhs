package controller;

import model.Prescription;
import java.util.*;
import java.time.LocalDate;

/**
 * PrescriptionController manages all Prescription operations (CRUD).
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class PrescriptionController {

    //INSTANCE VARIABLES ===
    private List<Prescription> prescriptions;

    // Constructor
    public PrescriptionController() {
        this.prescriptions = new ArrayList<>();
    }

    /**
     * CREATE: Add a new prescription.
     *
     * Prescription.java sets status to "Issued" by default and sets issuedDate to LocalDate.now().
     *
     * @param prescription Prescription object to add
     * @return true if added, false if null
     */
    public boolean addPrescription(Prescription prescription) {
        if (prescription == null) {
            System.err.println("ERROR: Cannot add null prescription");
            return false;
        }

        prescriptions.add(prescription);

        System.out.println(" Prescription added: " + prescription.getPrescriptionId()
                + " - " + prescription.getMedication()
                + " (" + prescription.getStatus() + ")"
                + " issued on " + prescription.getIssuedDate());

        return true;
    }

    /**
     * READ: Get a prescription by ID.
     *
     * @param prescriptionId ID to search
     * @return Prescription if found, otherwise null
     */
    public Prescription getPrescription(String prescriptionId) {
        return prescriptions.stream()
                .filter(p -> p.getPrescriptionId().equals(prescriptionId))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @param patientId Patient ID
     * @return List of prescriptions for that patient
     */
    public List<Prescription> getPrescriptionsByPatient(String patientId) {
        return prescriptions.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .toList();
    }

    /**
     * READ: Get all prescriptions issued by a clinician (GP or Specialist)
     *
     * @param clinicianId Clinician ID
     * @return List of prescriptions issued by that clinician
     */
    public List<Prescription> getPrescriptionsByClinician(String clinicianId) {
        return prescriptions.stream()
                .filter(p -> p.getClinicianId().equals(clinicianId))
                .toList();
    }

    /**
     * READ: Get all prescriptions with a given status.
     *
     * Typical statuses in model:
     * - "Issued"
     * - "Collected"
     * - "Expired"
     *
     * @param status Status to filter by
     * @return List of prescriptions with that status
     */
    public List<Prescription> getPrescriptionsByStatus(String status) {
        return prescriptions.stream()
                .filter(p -> p.getStatus().equals(status))
                .toList();
    }

    /**
     * READ: Get prescriptions issued on a specific date.
     *
     * @param date LocalDate to match (e.g., LocalDate.now())
     * @return Prescriptions issued on that date
     */
    public List<Prescription> getPrescriptionsByIssuedDate(LocalDate date) {
        return prescriptions.stream()
                .filter(p -> p.getIssuedDate().equals(date))
                .toList();
    }

    /**
     * UPDATE: Update prescription status.
     *
     * @param prescriptionId Prescription ID
     * @param newStatus New status (e.g., "Collected", "Expired")
     * @return true if updated, false if not found
     */
    public boolean updatePrescriptionStatus(String prescriptionId, String newStatus) {
        Prescription existing = getPrescription(prescriptionId);

        if (existing == null) {
            System.err.println("ERROR: Prescription " + prescriptionId + " not found");
            return false;
        }

        if (newStatus != null && !newStatus.isBlank()) {
            existing.setStatus(newStatus);
        }

        System.out.println(" Prescription status updated: " + prescriptionId + " → " + existing.getStatus());
        return true;
    }

    /**
     * UPDATE: Update prescription details.
     *
     * @param prescriptionId 
     * @param newMedication 
     * @param newDosage 
     * @param newDuration 
     * @param newNotes 
     * @return 
     */
    public boolean updatePrescriptionDetails(String prescriptionId,
                                            String newMedication,
                                            String newDosage,
                                            String newDuration,
                                            String newNotes) {

        Prescription existing = getPrescription(prescriptionId);

        if (existing == null) {
            System.err.println("ERROR: Prescription " + prescriptionId + " not found");
            return false;
        }

        if (newMedication != null && !newMedication.isBlank()) {
            existing.setMedication(newMedication);
        }

        if (newDosage != null && !newDosage.isBlank()) {
            existing.setDosage(newDosage);
        }

        if (newDuration != null && !newDuration.isBlank()) {
            existing.setDuration(newDuration);
        }

        if (newNotes != null && !newNotes.isBlank()) {
            existing.setNotes(newNotes);
        }

        System.out.println(" Prescription updated: " + prescriptionId);
        return true;
    }

    /**
     * DELETE: Remove a prescription by ID.
     *
     * @param prescriptionId ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deletePrescription(String prescriptionId) {
        boolean removed = prescriptions.removeIf(p -> p.getPrescriptionId().equals(prescriptionId));

        if (removed) {
            System.out.println(" Prescription deleted: " + prescriptionId);
        } else {
            System.err.println("ERROR: Prescription " + prescriptionId + " not found");
        }

        return removed;
    }

    /**
     * READ: Get all prescriptions (safe copy).
     *
     * @return Copy of prescriptions list
     */
    public List<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    /**
     * SPECIAL: Convenience method for "Issued" prescriptions not yet collected.
     *
     * @return List of prescriptions with status "Issued"
     */
    public List<Prescription> getIssuedPrescriptions() {
        return prescriptions.stream()
                .filter(p -> "Issued".equals(p.getStatus()))
                .toList();
    }

    /**
     * Total Prescriptions
     * @return count
     */
    public int getPrescriptionCount() {
        return prescriptions.size();
    }

    /**
     * POPULATE FROM CSV DATA
     *
     * Called after DataLoader reads prescriptions.csv and returns List<Prescription>.
     *
     * @param loadedPrescriptions prescriptions loaded from file
     */
    public void loadPrescriptionsFromData(List<Prescription> loadedPrescriptions) {
        prescriptions.clear();

        if (loadedPrescriptions != null) {
            prescriptions.addAll(loadedPrescriptions);
        }

        System.out.println(" Loaded " + prescriptions.size() + " prescriptions into controller");
    }
}
