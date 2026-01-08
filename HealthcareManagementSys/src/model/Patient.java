package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient class representing a patient in the healthcare system.
 * Inherits from User abstract class.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Patient extends User {

    // === PATIENT-SPECIFIC ATTRIBUTES ===
    private String nhsNumber;              // NHS number is unique to patients
    private String medicalHistory;         
    private List<String> allergies;        
    private String accountInfo;            

    // === CONSTRUCTOR ===
    /**
     * Constructor to create a new Patient
     *
     * @param userId 
     * @param name
     * @param email
     * @param phone 
     * @param nhsNumber
     */
    public Patient(String userId, String name, String email, String phone, String nhsNumber) {
        super(userId, name, email, phone);  
        this.nhsNumber = nhsNumber;
        this.allergies = new ArrayList<>();  
        this.accountInfo = "Active";
    }

    // === OVERRIDE performRole() METHOD ===
    @Override
    public void performRole() {
        System.out.println(name + " is viewing their medical record.");
    }

    // === PATIENT-SPECIFIC METHODS ===
    
    //Register a new account for the patient
    
    public void registerAccount() {
        this.accountInfo = "Active";
        System.out.println(name + " has registered an account.");
    }

    /**
     * Add an allergy to the patient's profile
     *
     * @param allergy The allergy to add
     */
    public void addAllergy(String allergy) {
        if (allergy != null && !allergy.isEmpty()) {
            allergies.add(allergy);
            System.out.println("Allergy added: " + allergy);
        }
    }

    /**
     * Remove an allergy from the patient's profile
     *
     * @param allergy The allergy to remove
     */
    public void removeAllergy(String allergy) {
        if (allergies.remove(allergy)) {
            System.out.println("Allergy removed: " + allergy);
        }
    }

    /**
     * View the patient's medical history
     *
     * @return Medical history string
     */
    public String viewMedicalHistory() {
        return medicalHistory;
    }

    /**
     * Update the patient's medical history
     *
     * @param history New medical history
     */
    public void updateMedicalHistory(String history) {
        this.medicalHistory = history;
        System.out.println("Medical history updated for " + name);
    }

    // === GETTERS ===

    public String getNhsNumber() {
        return nhsNumber;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public List<String> getAllergies() {
        return new ArrayList<>(allergies);  // Return copy for security
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    // === SETTERS ===

    public void setNhsNumber(String nhsNumber) {
        if (nhsNumber != null && !nhsNumber.isEmpty()) {
            this.nhsNumber = nhsNumber;
        }
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
