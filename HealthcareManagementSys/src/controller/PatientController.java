package controller;

import model.Patient;
import java.util.*;

/**
 * PatientController manages all Patient operations (CRUD).
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class PatientController {

    private List<Patient> patients;


    // Constructor
    public PatientController() {
        this.patients = new ArrayList<>();
    }

    /**
     * CREATE: Add a new patient to the system
     *
     * @param p The Patient object to add to the system
     * @return
     */
    public boolean addPatient(Patient p) {

        patients.add(p);
        System.out.println(" Patient added: " + p.getName() + " (ID: " + p.getUserId() + ")");
        return true;
    }

    /**
     * READ: Get a specific patient by their ID
     * 
     * @param patientId The patient ID to search for (e.g., "P001")
     * @return The Patient object if found, or null if not found
     */
    public Patient getPatient(String patientId) {

        return patients.stream()
                .filter(p -> p.getUserId().equals(patientId))
                .findFirst()
                .orElse(null);
    }

    /**
     * UPDATE: Modify an existing patient's details
     *
     * @param p The Patient object with updated data (must have correct userId)
     */
    public void updatePatient(Patient p) {

        Patient existing = getPatient(p.getUserId());

        // Check if patient was found
        if (existing != null) {

            existing.setName(p.getName());

            System.out.println(" Patient updated: " + p.getUserId() + " → " + p.getName());
        } else {
            System.err.println("ERROR: Patient " + p.getUserId() + " not found. Cannot update.");
        }
    }

    /**
     * DELETE: Remove a patient from the system
     *
     * @param patientId The ID of patient to remove (e.g., "P001")
     * @return
     */
    public boolean deletePatient(String patientId) {

        boolean wasRemoved = patients.removeIf(p -> p.getUserId().equals(patientId));

        if (wasRemoved) {
            System.out.println(" Patient deleted: " + patientId);
        } else {
            System.err.println("ERROR: Patient " + patientId + " not found. Cannot delete.");
        }
        return wasRemoved;
    }

    /**
     * READ: Get all patients in the system
     * @return A copy of all patients in the system
     */
    public List<Patient> getAllPatients() {

        return new ArrayList<>(patients);
    }

    /**
     * Get patient count
     *
     * @return Total number of patients in system
     */
    public int getPatientCount() {
        return patients.size();
    }

    /**
     * POPULATE FROM CSV DATA
     * 
     * @param loadedPatients List of Patient objects from CSV file
     */
    public void loadPatientsFromData(List<Patient> loadedPatients) {

       patients.clear();
        patients.addAll(loadedPatients);
        System.out.println(" Loaded " + patients.size() + " patients from CSV into controller");
    }

    /**
     * ADVANCED: Get patients by NHS number
     *
     * @param nhsNumber The NHS number to search for
     * @return List of patients with that NHS number (usually 0 or 1)
     */
    public List<Patient> getPatientsByNHSNumber(String nhsNumber) {
        return patients.stream()
                .filter(p -> p.getNhsNumber().equals(nhsNumber))
                .toList();
    }

}