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
}

