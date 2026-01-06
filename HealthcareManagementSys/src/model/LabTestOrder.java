package model;

import java.time.LocalDate;

/**
 * LabTestOrder class representing a lab test order.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class LabTestOrder {

    // === ATTRIBUTES ===
    private String testOrderId;
    private String patientId;
    private String testType;              // "Blood", "Urine", "X-Ray", etc.
    private String result;
    private String status;                // "Ordered", "Processing", "Completed"
    private LocalDate orderDate;
    private LocalDate resultDate;

    // === CONSTRUCTOR ===
    public LabTestOrder(String testOrderId, String patientId, String testType) {
        this.testOrderId = testOrderId;
        this.patientId = patientId;
        this.testType = testType;
        this.status = "Ordered";
        this.orderDate = LocalDate.now();
    }

    // === METHODS ===

    public void order() {
        System.out.println("Lab test " + testOrderId + " ordered for patient " + patientId);
    }

    public void uploadResult(String result) {
        this.result = result;
        this.status = "Completed";
        this.resultDate = LocalDate.now();
        System.out.println("Result uploaded for test " + testOrderId);
    }

    public void viewResult() {
        System.out.println("Test " + testOrderId + " result: " + result);
    }

    // === GETTERS & SETTERS ===

    public String getTestOrderId() {
        return testOrderId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getTestType() {
        return testType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getResultDate() {
        return resultDate;
    }
}
