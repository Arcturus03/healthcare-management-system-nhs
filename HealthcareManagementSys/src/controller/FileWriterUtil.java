package controller;

import model.*;
import java.io.*;
import java.util.*;

public class FileWriterUtil {

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

    /**
     * Generates a readable text referral letter (Requirement: output text content)
     */
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
}

