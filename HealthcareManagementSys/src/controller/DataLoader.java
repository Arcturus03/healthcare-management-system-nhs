package controller;

import model.*;
import java.io.*;
import java.util.*;

public class DataLoader {

    // Regex to split by comma ONLY if it's not inside quotes
    private static final String CSV_SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    public List<Patient> loadPatients(String filepath) throws IOException {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            reader.readLine(); // Skip header: userId,name,nhsNumber,email,phone

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(CSV_SPLIT_REGEX, -1); 
                
                // CSV columns: 0=userId, 1=name, 2=nhsNumber, 3=email, 4=phone
                if (parts.length >= 5) {
                    Patient p = new Patient(
                            parts[0].trim(),  // userId
                            parts[1].trim(),  // name
                            parts[3].trim(),  // email
                            parts[4].trim(),  // phone
                            parts[2].trim()   // nhsNumber
                    );
                    patients.add(p);
                }
            }
        }
        System.out.println("Loaded " + patients.size() + " patients");
        return patients;
    }


    public List<GP> loadGPs(String filepath) throws IOException {
        List<GP> gps = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        reader.readLine(); // Skip header


        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                GP gp = new GP(
                        parts[0].trim(),  // userId
                        parts[1].trim(),  // name
                        parts[2].trim(),  // email
                        parts[3].trim(),  // phone
                        parts[4].trim(),  // specialty
                        parts[5].trim()   // practiceName
                );
                gps.add(gp);
            }
        }
        reader.close();
        System.out.println("Loaded " + gps.size() + " GPs");
        return gps;
    }


    public List<Appointment> loadAppointments(String filepath) throws IOException {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(CSV_SPLIT_REGEX, -1);
                // Columns: 0=id, 1=patientId, 2=clinicianId, 3=dateTime, 4=status, 5=reason
                if (parts.length >= 5) {
                    Appointment app = new Appointment(
                        parts[0].trim(),  // appointmentId
                        parts[1].trim(),  // patientId
                        parts[2].trim(),  // clinicianId
                        parts[3].trim(),  // dateTime
                        "Surgery"         // location (not in CSV, use default)
                    );
                    app.setStatus(parts[4].trim());  // status
                    if (parts.length > 5 && !parts[5].trim().equals("null")) {
                        app.setReason(parts[5].trim());
                    }
                    appointments.add(app);
                }
            }
        }
        System.out.println("Loaded " + appointments.size() + " appointments");
        return appointments;
    }


    public List<Prescription> loadPrescriptions(String filepath) throws IOException {
        List<Prescription> prescriptions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(CSV_SPLIT_REGEX, -1);
                // Columns: 0=id, 1=patientId, 2=medication, 3=dosage, 4=status
                if (parts.length >= 4) {
                    Prescription p = new Prescription(
                        parts[0].trim(),  // prescriptionId
                        parts[1].trim(),  // patientId
                        "C001",           // clinicianId (not in CSV, use default)
                        parts[2].trim(),  // medication
                        parts[3].trim()   // dosage
                    );
                    if (parts.length > 4) {
                        p.setStatus(parts[4].trim());  // status
                    }
                    prescriptions.add(p);
                }
            }
        }
        System.out.println("Loaded " + prescriptions.size() + " prescriptions");
        return prescriptions;
    }

    public List<Referral> loadReferrals(String filepath) throws IOException {
        List<Referral> referrals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(CSV_SPLIT_REGEX, -1);
                // Columns: 0=id, 1=patientId, 2=fromGP, 3=toSpecialist, 4=reason, 5=status, 6=urgency
                if (parts.length >= 5) {
                    Referral r = new Referral(
                        parts[0].trim(),  // referralId
                        parts[1].trim(),  // patientId
                        parts[2].trim(),  // fromGpId
                        parts[3].trim(),  // toSpecialistId
                        parts[4].trim()   // reason
                    );
                    if (parts.length > 6) {
                        r.setUrgencyLevel(parts[6].trim());  // urgency
                    }
                    if (parts.length > 5) {
                        r.setStatus(parts[5].trim());  // status
                    }
                    referrals.add(r);
                }
            }
        }
        System.out.println("Loaded " + referrals.size() + " referrals");
        return referrals;
    }
}
