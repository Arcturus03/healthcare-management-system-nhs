package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PatientRecord class representing a patient's complete medical record.
 * Aggregates appointments, prescriptions, referrals, and lab tests.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class PatientRecord {

    // === ATTRIBUTES ===
    private String recordId;
    private String patientId;
    private List<String> entries;         
    private List<String> currentMedications;  
    private String allergies;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
    private String emergencyContact;

    // === CONSTRUCTOR ===
    public PatientRecord(String recordId, String patientId) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.entries = new ArrayList<>();
        this.currentMedications = new ArrayList<>();
        this.createdDate = LocalDate.now();
        this.lastUpdated = LocalDate.now();
    }

    // === METHODS ===

    /**
     * Add an entry to the patient record
     *
     * @param entry A log entry (appointment note, test result, etc.)
     */
    public void addEntry(String entry) {
        entries.add(entry);
        this.lastUpdated = LocalDate.now();
        System.out.println("Entry added to patient record " + recordId);
    }

    /**
     * Update an existing entry
     *
     * @param entryIndex Index of entry to update
     * @param newData New data for the entry
     */
    public void updateEntry(int entryIndex, String newData) {
        if (entryIndex >= 0 && entryIndex < entries.size()) {
            entries.set(entryIndex, newData);
            this.lastUpdated = LocalDate.now();
            System.out.println("Entry updated in patient record " + recordId);
        }
    }

    /**
     * Get all entries
     *
     * @return List of all entries
     */
    public List<String> getEntries() {
        return new ArrayList<>(entries);
    }

    /**
     * Add a medication to current medications list
     *
     * @param medication Medication name
     */
    public void addMedication(String medication) {
        if (medication != null && !medication.isEmpty()) {
            currentMedications.add(medication);
            this.lastUpdated = LocalDate.now();
        }
    }

    /**
     * Remove a medication from current medications list
     *
     * @param medication Medication name
     */
    public void removeMedication(String medication) {
        if (currentMedications.remove(medication)) {
            this.lastUpdated = LocalDate.now();
        }
    }

    /**
     * Get all current medications
     *
     * @return List of current medications
     */
    public List<String> getMedications() {
        return new ArrayList<>(currentMedications);
    }

    /**
     * Get allergies
     *
     * @return Allergy information
     */
    public String getAllergies() {
        return allergies;
    }

    /**
     * Set allergies
     *
     * @param allergies Allergy information
     */
    public void setAllergies(String allergies) {
        this.allergies = allergies;
        this.lastUpdated = LocalDate.now();
    }

    // === GETTERS & SETTERS ===

    public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String contact) {
        if (contact != null && !contact.isEmpty()) {
            this.emergencyContact = contact;
        }
    }
}
