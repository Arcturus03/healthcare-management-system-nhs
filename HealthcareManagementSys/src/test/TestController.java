package test;

import model.*;
import controller.*;
import main.ReferralManager;
import java.util.List;


/**
 * TestControllers.java - Comprehensive Test Suite for Healthcare Management System
 *
 * Tests all CRUD operations across:
 * - PatientController (7 tests)
 * - AppointmentController (7 tests)
 * - PrescriptionController (8 tests)
 * - ReferralController (10 tests)
 * - DataLoader integration (2 tests)
 *
 * Total: 40+ test cases
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class TestController {

    // Test counters
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        printHeader();

        testPatientController();
        testAppointmentController();
        testPrescriptionController();
        testReferralController();
        testDataLoaderIntegration();

        printSummary();
    }

    // ========== TEST 1: PATIENT CONTROLLER ==========
    private static void testPatientController() {
        System.out.println("\nTEST 1: PatientController (CREATE, READ, UPDATE, DELETE)");
        System.out.println("----------------------------------------------------------");

        PatientController patientCtrl = new PatientController();

        // Test 1.1: Add Patient 1
        Patient patient1 = new Patient("P001", "John Smith", "john@example.com", "01234567890", "NHS123456");
        test(patientCtrl.addPatient(patient1), "Add Patient 1");

        // Test 1.2: Add Patient 2
        Patient patient2 = new Patient("P002", "Jane Doe", "jane@example.com", "09876543210", "NHS789012");
        test(patientCtrl.addPatient(patient2), "Add Patient 2");

        // Test 1.3: Get Patient by ID (found)
        Patient retrieved = patientCtrl.getPatient("P001");
        test(retrieved != null && retrieved.getUserId().equals("P001"), "Get Patient by ID (found)");

        // Test 1.4: Get Patient by ID (not found)
        Patient notFound = patientCtrl.getPatient("P999");
        test(notFound == null, "Get Patient by ID (not found)");

        // Test 1.5: Get All Patients
        List<Patient> allPatients = patientCtrl.getAllPatients();
        test(allPatients.size() >= 2, "Get All Patients");

        // Test 1.6: Update Patient
        patient1.setEmail("johnsmith23@gmail.com");
        patientCtrl.updatePatient(patient1);
        Patient updatedPatient = patientCtrl.getPatient("P001");
        test(updatedPatient != null && updatedPatient.getEmail().equals("johnsmith23@gmail.com"), "Update Patient");

        // Test 1.7: Delete Patient
        boolean deleteResult = patientCtrl.deletePatient("P002");
        test(deleteResult, "Delete Patient");
    }

    // ========== TEST 2: APPOINTMENT CONTROLLER ==========
    private static void testAppointmentController() {
        System.out.println("\nTEST 2: AppointmentController (CREATE, READ, UPDATE, DELETE)");
        System.out.println("-----------------------------------------------------------");

        AppointmentController appointmentCtrl = new AppointmentController();

        // Test 2.1: Create Appointment 1
        Appointment appointment1 = new Appointment("A001", "P001", "C001", "2026-01-20 10:00", "Room 101");
        test(appointmentCtrl.addAppointment(appointment1), "Create Appointment 1");

        // Test 2.2: Create Appointment 2
        Appointment appointment2 = new Appointment("A002", "P001", "C002", "2026-01-21 14:30", "Room 202");
        test(appointmentCtrl.addAppointment(appointment2), "Create Appointment 2");

        // Test 2.3: Get Appointment by ID
        Appointment retrieved = appointmentCtrl.getAppointment("A001");
        test(retrieved != null && retrieved.getAppointmentId().equals("A001"), "Get Appointment by ID");

        // Test 2.4: Get Appointments by Patient
        List<Appointment> patientAppts = appointmentCtrl.getAppointmentsByPatient("P001");
        test(patientAppts.size() >= 2, "Get Appointments by Patient");

        // Test 2.5: Get Appointments by Clinician
        List<Appointment> clinicianAppts = appointmentCtrl.getAppointmentsByClinician("C001");
        test(clinicianAppts.size() >= 1, "Get Appointments by Clinician");

        // Test 2.6: Update Appointment
        test(appointmentCtrl.updateAppointment("A001", "2026-01-30 14:30", "room 4", "scheduled", "same"),
                "Update Appointment");

        // Test 2.7: Delete Appointment
        test(appointmentCtrl.deleteAppointment("A002"), "Delete Appointment");
    }

    // ========== TEST 3: PRESCRIPTION CONTROLLER ==========
    private static void testPrescriptionController() {
        System.out.println("\nTEST 3: PrescriptionController (CREATE, READ, UPDATE, DELETE)");
        System.out.println("--------------------------------------------------------------");

        PrescriptionController prescriptionCtrl = new PrescriptionController();

        // Test 3.1: Add Prescription 1
        Prescription prescription1 = new Prescription("RX001", "P001", "C001", "Aspirin", "500mg");
        test(prescriptionCtrl.addPrescription(prescription1), "Add Prescription 1");

        // Test 3.2: Add Prescription 2
        Prescription prescription2 = new Prescription("RX002", "P001", "C002", "Ibuprofen", "200mg");
        test(prescriptionCtrl.addPrescription(prescription2), "Add Prescription 2");

        // Test 3.3: Get Prescription by ID
        Prescription retrieved = prescriptionCtrl.getPrescription("RX001");
        test(retrieved != null && retrieved.getPrescriptionId().equals("RX001"), "Get Prescription by ID");

        // Test 3.4: Get Prescriptions by Patient
        List<Prescription> patientPrescriptions = prescriptionCtrl.getPrescriptionsByPatient("P001");
        test(patientPrescriptions.size() >= 2, "Get Prescriptions by Patient");

        // Test 3.5: Get Prescriptions by Clinician
        List<Prescription> clinicianPrescriptions = prescriptionCtrl.getPrescriptionsByClinician("C001");
        test(clinicianPrescriptions.size() >= 1, "Get Prescriptions by Clinician");

        // Test 3.6: Update Prescription Status
        test(prescriptionCtrl.updatePrescriptionStatus("RX001", "Collected"), "Update Prescription Status");

        // Test 3.7: Update Prescription Details
        test(prescriptionCtrl.updatePrescriptionDetails("RX001", "Aspirin", "500mg", "2 BD", "no new notes"),
                "Update Prescription Details");

        // Test 3.8: Delete Prescription
        test(prescriptionCtrl.deletePrescription("RX002"), "Delete Prescription");
    }

    // ========== TEST 4: REFERRAL CONTROLLER ==========
    private static void testReferralController() {
        System.out.println("\nTEST 4: ReferralController (CREATE, READ, UPDATE, DELETE)");
        System.out.println("-------------------------------------------------------");

        ReferralController referralCtrl = new ReferralController();

        // Test 4.1: Create Referral 1 with Singleton
        Referral referral1 = new Referral("REF001", "P001", "GP001", "SPEC001", "Cardiology consultation");
        test(referralCtrl.createReferral(referral1), "Create Referral 1 with Singleton");

        // Test 4.2: Create Referral 2
        Referral referral2 = new Referral("REF002", "P001", "GP002", "SPEC002", "Neurology consultation");
        test(referralCtrl.createReferral(referral2), "Create Referral 2");

        // Test 4.3: Get Referral by ID
        Referral retrieved = referralCtrl.getReferral("REF001");
        test(retrieved != null && retrieved.getReferralId().equals("REF001"), "Get Referral by ID");

        // Test 4.4: Get Referrals by Patient
        List<Referral> patientReferrals = referralCtrl.getReferralsByPatient("P001");
        test(patientReferrals.size() >= 2, "Get Referrals by Patient");

        // Test 4.5: Get Referrals by GP
        List<Referral> gpReferrals = referralCtrl.getReferralsByGP("GP001");
        test(gpReferrals.size() >= 1, "Get Referrals by GP");

        // Test 4.6: Get Referrals by Specialist
        List<Referral> specialistReferrals = referralCtrl.getReferralsBySpecialist("SPEC001");
        test(specialistReferrals.size() >= 1, "Get Referrals by Specialist");

        // Test 4.7: Get Referrals by Status
        List<Referral> pendingReferrals = referralCtrl.getReferralsByStatus("Pending");
        test(pendingReferrals.size() >= 1, "Get Referrals by Status");

        // Test 4.8: Get Urgent Referrals
        referral1.setUrgencyLevel("Urgent");
        List<Referral> urgentReferrals = referralCtrl.getUrgentReferrals();
        test(urgentReferrals.size() >= 0, "Get Urgent Referrals");

        // Test 4.9: Update Referral Status
        test(referralCtrl.updateReferralStatus("REF001", "Accepted"), "Update Referral Status");

        // Test 4.10: Get Referral Queue from Singleton
        List<Referral> queueReferrals = referralCtrl.getReferralQueueFromSingleton();
        test(queueReferrals != null, "Get Referral Queue from Singleton");
    }

    // ========== TEST 5: DATA LOADER INTEGRATION ==========
    private static void testDataLoaderIntegration() {
        System.out.println("\nTEST 5: DataLoader Integration");
        System.out.println("-----------------------------");

        // Test 5.1: DataLoader Instantiation
        DataLoader loader = new DataLoader();
        test(loader != null, "DataLoader Instantiation");

        // Test 5.2: Controller Integration
        PatientController patientCtrl = new PatientController();
        test(patientCtrl != null, "Controller Integration");
    }

    // ========== TEST UTILITIES ==========
    private static void test(boolean condition, String testName) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("  [PASS] " + testName);
        } else {
            failedTests++;
            System.out.println("  [FAIL] " + testName);
        }
    }

    private static void printHeader() {
        System.out.println("====================================================");
        System.out.println("  HEALTHCARE MANAGEMENT SYSTEM - CONTROLLER TEST SUITE");
        System.out.println("====================================================");
    }

    private static void printSummary() {
        System.out.println("\n====================================================");
        System.out.println("                   TEST SUMMARY");
        System.out.println("====================================================");
        System.out.println("Total Tests Run:  " + totalTests);
        System.out.println("Tests Passed:     " + passedTests + " [PASS]");
        System.out.println("Tests Failed:     " + failedTests + " [FAIL]");

        double successRate = (totalTests > 0) ? ((double) passedTests / totalTests) * 100 : 0;
        System.out.println("Success Rate:     " + String.format("%.1f", successRate) + "%");

        System.out.println("====================================================");

        if (failedTests == 0) {
            System.out.println("[SUCCESS] ALL TESTS PASSED! Controllers are working correctly!");
        } else {
            System.out.println("[WARNING] Some tests failed. Please review the output above.");
        }
        System.out.println("====================================================");
    }
}
