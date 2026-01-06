package model;

import model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple test to verify all model classes compile and work correctly.
 * This is NOT part of the final submission, just for verification.
 */
public class TestModel {
    public static void main(String[] args) {
        System.out.println("=== Testing Model Classes ===\n");

        // Create some users
        Patient patient = new Patient("P001", "John Smith", "john@email.com", "0123456789", "NHS123456");
        GP doctor = new GP("GP001", "Dr. Sarah Jones", "sarah@clinic.com", "9876543210", "General Medicine", "Central Clinic");
        Nurse nurse = new Nurse("N001", "Emma Wilson", "emma@clinic.com", "1234567890", "Band 5");

        // Test User methods
        System.out.println("--- Testing User Methods ---");
        patient.login();
        patient.performRole();
        patient.updateProfile("newemail@email.com", "0987654321");
        patient.logout();

        System.out.println("\n--- Testing Patient Methods ---");
        patient.registerAccount();
        patient.addAllergy("Penicillin");
        patient.addAllergy("Aspirin");
        System.out.println("Patient allergies: " + patient.getAllergies());

        System.out.println("\n--- Testing GP Methods ---");
        doctor.createPrescription("P001", "Amoxicillin", "500mg twice daily");
        doctor.generateReferral("P001", "Cardiology");
        doctor.orderLabTest("P001", "Blood Test");

        System.out.println("\n--- Testing Nurse Methods ---");
        nurse.performRole();
        nurse.recordVitals("P001", "BP: 120/80, HR: 72");

        System.out.println("\n--- Testing Entity Classes ---");
        Appointment apt = new Appointment("A001", "P001", "GP001", "2025-01-15 10:00", "Room 101");
        apt.create();
        apt.checkIn();

        Prescription prescription = new Prescription("RX001", "P001", "GP001", "Amoxicillin", "500mg");
        prescription.issue();
        prescription.view();

        PatientRecord record = new PatientRecord("PR001", "P001");
        record.addEntry("Patient attended appointment on 2025-01-15");
        record.addMedication("Amoxicillin");
        record.setAllergies("Penicillin, Aspirin");
        System.out.println("Patient record created and updated.");
        System.out.println("Medications: " + record.getMedications());

        Referral referral = new Referral("REF001", "P001", "GP001", "SP001", "Cardiac evaluation");
        referral.create();

        LabTestOrder labTest = new LabTestOrder("LT001", "P001", "Blood Test");
        labTest.order();
        labTest.uploadResult("Results pending");

        System.out.println("\n=== All Tests Completed Successfully! ===");
    }
}
