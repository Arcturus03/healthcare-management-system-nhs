package controller;

import model.*;
import java.io.*;
import java.util.*;

public class DataLoader {

    public List<Patient> loadPatients(String filepath) throws IOException {
        List<Patient> patients = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        reader.readLine(); // Skip header

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                Patient p = new Patient(
                        parts[0].trim(),  // userId
                        parts[1].trim(),  // name
                        parts[2].trim(),  // email
                        parts[3].trim(),  // phone
                        parts[4].trim()   // nhsNumber
                );
                patients.add(p);
            }
        }
        reader.close();
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

    // Similar for Appointments, Prescriptions, Referrals
}
