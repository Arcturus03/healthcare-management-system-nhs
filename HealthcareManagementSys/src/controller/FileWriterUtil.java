package controller;

import model.*;
import java.io.*;
import java.util.*;

public class FileWriterUtil {

    // write prescriptions to file
    public static void writePrescriptionsToFile(List<Prescription> prescriptions, String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        writer.write("prescriptionId,patientId,medication,dosage,status\n");

        for (Prescription p : prescriptions) {
            writer.write(p.getPrescriptionId() + "," +
                    p.getPatientId() + "," +
                    p.getMedication() + "," +
                    p.getDosage() + "," +
                    p.getStatus() + "\n");
        }
        writer.close();
        System.out.println("Prescriptions written to " + filepath);
    }


    // write referrals to file
    public static void writeReferralsToFile(List<Referral> referrals, String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        writer.write("referralId,patientId,fromGP,toSpecialist,reason,status,urgency\n");

        for (Referral r : referrals) {
            writer.write(r.getReferralId() + "," +
                    r.getPatientId() + "," +
                    r.getFromGpId() + "," +
                    r.getToSpecialistId() + "," +
                    r.getReason() + "," +
                    r.getStatus() + "," +
                    r.getUrgencyLevel() + "\n");
        }
        writer.close();
        System.out.println("Referrals written to " + filepath);
    }



    // write patients to file
    public static void writePatientsToFile(List<Patient> patients, String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        writer.write("userId,name,nhsNumber,email,phone\n");

        for (Patient p : patients) {
            writer.write(p.getUserId() + "," +
                    p.getName() + "," +
                    p.getNhsNumber() + "," +
                    p.getEmail() + "," +
                    p.getPhone() + "\n");
        }
        writer.close();
        System.out.println("Patients written to " + filepath);
    }


    // write appointments to file
    public static void writeAppointmentsToFile(List<Appointment> appointments, String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        writer.write("appointmentId,patientId,clinicianId,dateTime,status,reason\n");

        for (Appointment a : appointments) {
            writer.write(a.getAppointmentId() + "," +
                    a.getPatientId() + "," +
                    a.getClinicianId() + "," +
                    a.getDateTime() + "," +
                    a.getStatus() + "," +
                    a.getReason() + "\n");
        }
        writer.close();
        System.out.println("Appointments written to " + filepath);
    }



    // write clinicians to file
    public static void writeCliniciansToFile(List<Clinician> clinicians, String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        // Match original CSV header exactly
        writer.write("clinician_id,first_name,last_name,title,speciality,gmc_number,phone_number,email,workplace_id,workplace_type,employment_status,start_date\n");

        for (Clinician c : clinicians) {
            writer.write(
                c.getClinicianId() + "," +
                c.getFirstName() + "," +
                c.getLastName() + "," +
                c.getTitle() + "," +
                c.getSpeciality() + "," +
                c.getGmcNumber() + "," +
                c.getPhone() + "," +
                c.getEmail() + "," +
                c.getWorkplaceId() + "," +
                c.getWorkplaceType() + "," +
                c.getEmploymentStatus() + "," +
                c.getStartDate() + "\n"
            );
        }
        writer.close();
        System.out.println("Clinicians written to " + filepath);
    }


    
    //Generates a readable text referral letter (Requirement: output text content)
    
    public static void generateReferralLetter(Referral r, String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        writer.write("================================================\n");
        writer.write("              NHS REFERRAL LETTER               \n");
        writer.write("================================================\n\n");
        writer.write("Date: " + r.getReferralDate() + "\n");
        writer.write("Urgency: " + r.getUrgencyLevel() + "\n\n");
        
        writer.write("FROM: GP ID " + r.getFromGpId() + "\n");
        writer.write("TO:   Specialist ID " + r.getToSpecialistId() + "\n\n");
        
        writer.write("RE:   PATIENT " + r.getPatientId() + "\n\n");
        
        writer.write("Dear Colleague,\n\n");
        writer.write("I would appreciate your assessment of this patient.\n");
        writer.write("Reason for referral: " + r.getReason() + "\n\n");
        
        writer.write("Clinical Summary:\n");
        writer.write(r.getClinicalSummary() != null ? r.getClinicalSummary() : "No summary provided.");
        writer.write("\n\nSincerely,\nNHS GP Service");
        
        writer.close();
        System.out.println("Referral letter generated: " + filepath);
    }


    // Generates a readable prescription slip (Requirement: output text content)

    public static void generatePrescriptionSlip(Prescription p, String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        writer.write("================================================\n");
        writer.write("              NHS PRESCRIPTION SLIP             \n");
        writer.write("================================================\n\n");
        writer.write("Date Issued: " + p.getIssuedDate() + "\n");
        writer.write("Prescription ID: " + p.getPrescriptionId() + "\n\n");
        
        writer.write("PATIENT ID: " + p.getPatientId() + "\n");
        writer.write("PRESCRIBER: " + p.getClinicianId() + "\n\n");
        
        writer.write("------------------------------------------------\n");
        writer.write("MEDICATION DETAILS\n");
        writer.write("------------------------------------------------\n");
        writer.write("Drug:    " + p.getMedication() + "\n");
        writer.write("Dosage:  " + p.getDosage() + "\n");
        writer.write("Status:  " + p.getStatus() + "\n\n");
        
        writer.write("------------------------------------------------\n");
        writer.write("INSTRUCTIONS\n");
        writer.write("------------------------------------------------\n");
        writer.write("Take as directed by your healthcare provider.\n");
        writer.write("Complete the full course of medication.\n\n");
        
        writer.write("================================================\n");
        writer.write("        NHS Healthcare Management System        \n");
        writer.write("================================================\n");
        
        writer.close();
        System.out.println("Prescription slip generated: " + filepath);
    }



}

