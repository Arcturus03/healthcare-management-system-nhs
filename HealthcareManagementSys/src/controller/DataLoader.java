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
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                // Use the regex instead of simple split(",")
                String[] parts = line.split(CSV_SPLIT_REGEX, -1); 
                
                if (parts.length >= 5) {
                    // Clean up quotes from fields (e.g., "Birmingham" -> Birmingham)
                    String address = parts[8].replace("\"", "").trim(); 
                    
                    Patient p = new Patient(
                            parts[0].trim(),  // userId
                            parts[1].trim() + " " + parts[2].trim(),  // Combine First/Last Name
                            parts[7].trim(),  // email
                            parts[6].trim(),  // phone
                            parts[4].trim()   // nhsNumber
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
                // CSV Cols:
                // 0:id, 1:patient, 2:clinician, 3:facility, 4:date(YYYY-MM-DD), 5:time, ...
                if (parts.length >= 10) {
                    Appointment app = new Appointment(
                        parts[0].trim(), // id
                        parts[1].trim(), // patientId
                        parts[2].trim(), // clinicianId
                        parts[4].trim() + " " + parts[5].trim(), // dateTime
                        parts[9].trim() // reason (using reason_for_visit)
                    );
                    app.setStatus(parts[8].trim()); // status
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
                // CSV: 0:id, 1:pat, 2:clin, ... 5:med, 6:dosage, ...
                if (parts.length >= 7) {
                    Prescription p = new Prescription(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[5].trim(),
                        parts[6].trim()
                    );
                    if(parts.length > 12) p.setStatus(parts[12].trim());
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
                // CSV: 0:id, 1:pat, 2:from, 3:to, ... 7:urgency, 8:reason, 9:summary, ... 11:status
                if (parts.length >= 10) {
                    Referral r = new Referral(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[8].trim()
                    );
                    r.setUrgencyLevel(parts[7].trim());
                    r.setClinicalSummary(parts[9].trim());
                    if(parts.length > 11) r.setStatus(parts[11].trim());
                    referrals.add(r);
                }
            }
        }
        System.out.println("Loaded " + referrals.size() + " referrals");
        return referrals;
    }
}
