package controller;

import model.Patient;
import java.util.*;

/**
 * PatientController manages all Patient operations (CRUD).
 *
 * WHAT IS THIS CONTROLLER?
 * - This is the CONTROLLER layer in MVC architecture
 * - Handles all patient-related operations (Create, Read, Update, Delete)
 * - Acts as a bridge between:
 *   * GUI (View) - receives requests from user
 *   * Patient Model - stores patient data
 *
 * WHY SEPARATE CONTROLLER?
 * - GUI code doesn't directly touch patient data
 * - Logic is centralized in one place
 * - Easy to test CRUD operations independently
 * - If we change database later, GUI code stays the same
 *
 * PATIENT WORKFLOW:
 * 1. New patient registers (create patient)
 * 2. Patient updates profile (update patient)
 * 3. Patient can be searched by ID (read patient)
 * 4. Inactive patients removed from system (delete patient)
 * 5. List all patients for admin (read all)
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class PatientController {

    // === INSTANCE VARIABLES ===
    // This list holds ALL patients in the system
    // When a patient is added/removed/updated, this list changes
    // Only this controller can directly modify this list
    private List<Patient> patients;

    /**
     * CONSTRUCTOR
     *
     * Called when we create a new PatientController
     * Initializes an empty patient list
     *
     * WHY INITIALIZE HERE?
     * - Ensures we always start with empty list
     * - Ready to add patients or load from CSV
     * - Prevents null pointer exceptions
     *
     * EXAMPLE USAGE IN MAIN:
     * PatientController controller = new PatientController();  // Constructor runs
     * // Now controller.patients is an empty ArrayList ready to use
     */
    public PatientController() {
        // Create a new ArrayList to store Patient objects
        // ArrayList is flexible - grows automatically as we add patients
        this.patients = new ArrayList<>();
    }

    /**
     * CREATE: Add a new patient to the system
     *
     * WHAT IT DOES:
     * 1. Takes a Patient object from GUI
     * 2. Adds it to the patients list
     * 3. This patient is now in the system
     *
     * WHEN IS THIS CALLED?
     * - New patient registration (receptionist adds patient)
     * - Loading patients from CSV file
     * - Importing patient data from another system
     *
     * EXAMPLE:
     * Patient newPatient = new Patient("P001", "John Smith", "john@email.com", "0123456789", "NHS123");
     * patientController.addPatient(newPatient);
     * // John Smith is now in the system
     *
     * WHY NO RETURN VALUE?
     * - In real systems, would return boolean (true if added, false if failed)
     * - This simple version always succeeds (assumes valid input)
     *
     * FUTURE IMPROVEMENT:
     * - Add validation (check if patient already exists)
     * - Check for duplicate NHS numbers
     * - Return boolean for error handling
     *
     * @param p The Patient object to add to the system
     */
    public void addPatient(Patient p) {
        // Add the patient to the list
        // .add() appends to end of list
        // Time complexity: O(1) - always fast, regardless of list size
        patients.add(p);

        // Print confirmation to console
        System.out.println("✓ Patient added: " + p.getName() + " (ID: " + p.getUserId() + ")");
    }

    /**
     * READ: Get a specific patient by their ID
     *
     * WHAT IT DOES:
     * 1. Searches for patient with matching ID
     * 2. Returns the Patient object if found
     * 3. Returns null if not found
     *
     * WHEN IS THIS CALLED?
     * - Patient logs in (search by ID)
     * - Doctor needs to view patient details
     * - Creating appointment (find patient first)
     * - Creating prescription (find patient first)
     *
     * HOW DOES IT WORK? (Java Streams - Modern Java 8+)
     *
     * patients.stream()
     * - Converts list into a stream (pipeline of data)
     *
     * .filter(p -> p.getUserId().equals(patientId))
     * - Keeps ONLY patients where userId matches our search
     * - Removes all others
     * - Example: if searching for "P001", only patient with userId="P001" passes through
     *
     * .findFirst()
     * - Gets the FIRST matching patient (should be only one due to unique IDs)
     * - Returns Optional<Patient> (special wrapper that handles "not found" case)
     *
     * .orElse(null)
     * - If found: returns the Patient object
     * - If not found: returns null
     *
     * EXAMPLE:
     * Patient found = patientController.getPatient("P001");
     * if (found != null) {
     *     System.out.println("Found: " + found.getName());
     * } else {
     *     System.out.println("Patient not found");
     * }
     *
     * ALTERNATIVE (Old-style for-loop, less readable):
     * public Patient getPatient(String patientId) {
     *     for (Patient p : patients) {
     *         if (p.getUserId().equals(patientId)) {
     *             return p;  // Found it!}
     *     }
     *     return null;  // Not found
     * }
     *
     * @param patientId The patient ID to search for (e.g., "P001")
     * @return The Patient object if found, or null if not found
     */
    public Patient getPatient(String patientId) {
        // Use Java Stream API to find patient
        return patients.stream()
                // Filter: keep only patients matching this ID
                .filter(p -> p.getUserId().equals(patientId))
                // Find: get first matching patient
                .findFirst()
                // Or-else: if not found, return null
                .orElse(null);
    }

    /**
     * UPDATE: Modify an existing patient's details
     *
     * WHAT IT DOES:
     * 1. Finds the patient with matching ID
     * 2. Updates their name (and could update other fields)
     * 3. The patient object in memory is modified
     *
     * WHEN IS THIS CALLED?
     * - Patient changes their name
     * - Patient updates contact information
     * - Admin corrects patient details (typo in name, etc.)
     *
     * WHY NOT REPLACE THE WHOLE OBJECT?
     * - If we create new Patient object, we lose other data (email, NHS number, etc.)
     * - Better to modify only the fields that changed
     * - Keeps all relationships intact (appointments, prescriptions, etc.)
     *
     * HOW IT WORKS:
     * 1. Call getPatient() to find existing patient
     * 2. If found, modify its name using setter
     * 3. That same object in the list is modified (because Java passes by reference)
     *
     * EXAMPLE:
     * Patient updateData = new Patient("P001", "John Smith UPDATED", "john@email.com", "0123456789", "NHS123");
     * patientController.updatePatient(updateData);
     *
     * AFTER THIS:
     * The patient with ID "P001" now has name "John Smith UPDATED"
     * Email, phone, NHS number unchanged
     *
     * WHY ONLY UPDATE NAME?
     * - This is simplified version
     * - Real version would update: name, email, phone, allergies, etc.
     * - Current version only updates name to keep it simple
     *
     * FUTURE IMPROVEMENT:
     * public boolean updatePatient(String patientId, String newName, String newEmail, String newPhone) {
     *     Patient existing = getPatient(patientId);
     *     if (existing != null) {
     *         existing.setName(newName);
     *         existing.setEmail(newEmail);  // Add email update
     *         existing.setPhone(newPhone);  // Add phone update
     *         return true;
     *     }
     *     return false;  // Patient not found
     * }
     *
     * @param p The Patient object with updated data (must have correct userId)
     */
    public void updatePatient(Patient p) {
        // Find the existing patient with this ID
        // getPatient() returns the actual object in our list (not a copy)
        Patient existing = getPatient(p.getUserId());

        // Check if patient was found
        if (existing != null) {
            // Patient exists, so update their name
            // existing.setName() modifies the actual object in our list
            existing.setName(p.getName());

            // Print confirmation
            System.out.println("✓ Patient updated: " + p.getUserId() + " → " + p.getName());
        } else {
            // Patient doesn't exist, print error
            System.err.println("ERROR: Patient " + p.getUserId() + " not found. Cannot update.");
        }
    }

    /**
     * DELETE: Remove a patient from the system
     *
     * WHAT IT DOES:
     * 1. Finds patient with matching ID
     * 2. Removes that patient from the list
     * 3. Patient is no longer in system
     *
     * WHEN IS THIS CALLED?
     * - Patient requests data deletion (GDPR right to be forgotten)
     * - Patient deactivates account
     * - Admin removes duplicate patient records
     * - Cleaning up test data
     *
     * WHY NOT JUST SET STATUS TO "INACTIVE"?
     * - In real healthcare: NEVER delete! Use status instead (legal requirement)
     * - We delete here for simplicity (this is learning project, not production)
     * - In production: mark as "Deleted" with timestamp, keep record forever
     *
     * HOW IT WORKS:
     * removeIf() = remove elements matching condition
     * - Loops through list
     * - If condition true, removes that element
     * - Stops checking when done
     *
     * EXAMPLE:
     * patientController.deletePatient("P001");
     * // Now patient with ID "P001" is removed from list
     *
     * ALTERNATIVE (Old-style approach):
     * public void deletePatient(String patientId) {
     *     for (int i = 0; i < patients.size(); i++) {
     *         if (patients.get(i).getUserId().equals(patientId)) {
     *             patients.remove(i);  // Remove this patient
     *             break;
     *         }
     *     }
     * }
     *
     * WHY IS STREAM BETTER?
     * - More readable
     * - Less error-prone (no index out of bounds)
     * - Functional style
     *
     * @param patientId The ID of patient to remove (e.g., "P001")
     */
    public void deletePatient(String patientId) {
        // Remove all patients matching this condition
        // removeIf() returns true if at least one was removed
        boolean wasRemoved = patients.removeIf(p -> p.getUserId().equals(patientId));

        // Print confirmation or error
        if (wasRemoved) {
            System.out.println("✓ Patient deleted: " + patientId);
        } else {
            System.err.println("ERROR: Patient " + patientId + " not found. Cannot delete.");
        }
    }

    /**
     * READ: Get all patients in the system
     *
     * WHAT IT DOES:
     * 1. Returns a copy of all patients in the list
     * 2. NOT the original list (for safety)
     *
     * WHEN IS THIS CALLED?
     * - Admin panel displays all patients
     * - Generate report of all patients
     * - Export all patient data
     * - Populate patient dropdown in GUI
     *
     * WHY RETURN A COPY (NOT the original)?
     * SECURITY REASON:
     * - If we return our actual list: caller could do patients.clear()
     * - This would delete EVERYTHING from our controller!
     * - By returning a copy: external code can't break our system
     *
     * EXAMPLE OF SECURITY ISSUE:
     * Bad version:
     * public List<Patient> getAllPatients() {
     *     return patients;  // Returns actual list!
     * }
     *
     * In GUI code:
     * List<Patient> list = patientController.getAllPatients();
     * list.clear();  // OOPS! Deletes all patients from controller!
     *
     * Good version (what we do here):
     * public List<Patient> getAllPatients() {
     *     return new ArrayList<>(patients);  // Returns copy
     * }
     *
     * In GUI code:
     * List<Patient> list = patientController.getAllPatients();
     * list.clear();  // Only clears the COPY, not the original!
     *
     * EXAMPLE:
     * List<Patient> allPatients = patientController.getAllPatients();
     * for (Patient p : allPatients) {
     *     System.out.println(p.getName());
     * }
     *
     * @return A copy of all patients in the system
     */
    public List<Patient> getAllPatients() {
        // Create a NEW ArrayList with all patients
        // This is a copy, not the original
        // new ArrayList<>(patients) = constructor that copies contents
        return new ArrayList<>(patients);
    }

    /**
     * HELPER: Get patient count
     *
     * WHAT IT DOES:
     * - Returns how many patients are in system
     *
     * WHEN IS THIS CALLED?
     * - Dashboard shows "Total Patients: 150"
     * - Statistics/reporting
     * - Validation (system should have > 0 patients)
     *
     * EXAMPLE:
     * int count = patientController.getPatientCount();
     * System.out.println("System has " + count + " patients");
     *
     * @return Total number of patients in system
     */
    public int getPatientCount() {
        return patients.size();
    }

    /**
     * POPULATE FROM CSV DATA
     *
     * WHAT IT DOES:
     * 1. Clears any existing patients (fresh start)
     * 2. Adds all patients loaded from CSV file
     *
     * WHEN IS THIS CALLED?
     * - Application startup
     * - User clicks "Load Data" button in GUI
     * - Reloading fresh data from file
     *
     * HOW IT WORKS:
     * 1. DataLoader reads patients.csv
     * 2. Returns list of Patient objects
     * 3. This method receives that list
     * 4. Adds all of them to our system
     *
     * EXAMPLE FLOW:
     * DataLoader loader = new DataLoader();
     * List<Patient> loaded = loader.loadPatients("data/patients.csv");  // Returns 100 patients
     * patientController.loadPatientsFromData(loaded);  // Adds all 100 to controller
     *
     * @param loadedPatients List of Patient objects from CSV file
     */
    public void loadPatientsFromData(List<Patient> loadedPatients) {
        // Clear any existing patients
        // Start with fresh data
        patients.clear();

        // Add all patients from CSV
        // .addAll() adds every patient from the list
        patients.addAll(loadedPatients);

        // Print confirmation
        System.out.println("✓ Loaded " + patients.size() + " patients from CSV into controller");
    }

    /**
     * ADVANCED: Get patients by NHS number
     *
     * BONUS METHOD: Not used yet, but useful for future
     *
     * @param nhsNumber The NHS number to search for
     * @return List of patients with that NHS number (usually 0 or 1)
     */
    public List<Patient> getPatientsByNHSNumber(String nhsNumber) {
        return patients.stream()
                .filter(p -> p.getNhsNumber().equals(nhsNumber))
                .toList();
    }

    /**
     * ADVANCED: Get patients by name (partial match)
     *
     * BONUS METHOD: Not used yet, but useful for patient search by name
     *
     * EXAMPLE:
     * User types "Smith" in search box
     * Returns all patients with "Smith" in their name
     *
     * @param nameFragment The name (or part of name) to search for
     * @return List of patients matching the search
     */
    public List<Patient> getPatientsByName(String nameFragment) {
        return patients.stream()
                // toUpperCase() = case-insensitive search
                // "John Smith" will match "smith" or "SMITH"
                .filter(p -> p.getName().toUpperCase().contains(nameFragment.toUpperCase()))
                .toList();
    }
}