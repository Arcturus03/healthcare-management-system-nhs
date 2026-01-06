package model;

import java.util.ArrayList;
import java.util.List;

/**
 * LabTechnician class representing laboratory staff.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class LabTechnician extends User {

    // === LAB TECHNICIAN-SPECIFIC ATTRIBUTES ===
    private String labArea;
    private List<String> assignedTests;
    private List<String> testResults;

    // === CONSTRUCTOR ===
    public LabTechnician(String userId, String name, String email, String phone, String labArea) {
        super(userId, name, email, phone);
        this.labArea = labArea;
        this.assignedTests = new ArrayList<>();
        this.testResults = new ArrayList<>();
    }

    // === OVERRIDE performRole() ===
    @Override
    public void performRole() {
        System.out.println(name + " is processing lab tests.");
    }

    // === LAB TECHNICIAN-SPECIFIC METHODS ===

    public void viewTestOrder(String testOrderId) {
        assignedTests.add(testOrderId);
        System.out.println("Lab Technician " + name + " is viewing test order " + testOrderId);
    }

    public void uploadTestResult(String testOrderId, String result) {
        testResults.add(result);
        System.out.println("Lab Technician " + name + " uploaded result for test " + testOrderId);
    }

    // === GETTERS ===

    public String getLabArea() {
        return labArea;
    }

    public List<String> getAssignedTests() {
        return new ArrayList<>(assignedTests);
    }

    public List<String> getTestResults() {
        return new ArrayList<>(testResults);
    }
}
