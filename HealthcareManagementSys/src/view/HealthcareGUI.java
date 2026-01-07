package view;

import controller.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class HealthcareGUI extends JFrame {

    private PatientController patientController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;

    private JTabbedPane tabbedPane;
    private JTable patientTable;
    private JTable appointmentTable;
    private JTable prescriptionTable;
    private JTable referralTable;

    public HealthcareGUI() {
        // Initialize Controllers
        patientController = new PatientController();
        appointmentController = new AppointmentController();
        prescriptionController = new PrescriptionController();
        referralController = new ReferralController();

        // Load Data
        loadData();

        // Setup Frame
        setTitle("NHS Healthcare Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI Initialization
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Patients", createPatientPanel());
        tabbedPane.addTab("Appointments", createAppointmentPanel());
        tabbedPane.addTab("Prescriptions", createPrescriptionPanel());
        tabbedPane.addTab("Referrals", createReferralPanel());

        add(tabbedPane);
    }

    private void loadData() {
        DataLoader loader = new DataLoader();
        try {
            // Load CSVs
            // Adjust paths if needed depending on execution context
            String pathPrefix = "data/";
            // Fallback for IntelliJ working directory differences
            if (!new java.io.File(pathPrefix + "patients.csv.crdownload").exists()) {
                pathPrefix = "../data/";
            }

            List<Patient> patients = loader.loadPatients(pathPrefix + "patients.csv.crdownload");
            patientController.loadPatientsFromData(patients);

            List<Appointment> appointments = loader.loadAppointments(pathPrefix + "appointments.csv.crdownload");
            appointmentController.loadAppointmentsFromData(appointments);

            List<Prescription> prescriptions = loader.loadPrescriptions(pathPrefix + "prescriptions.csv.crdownload");
            prescriptionController.loadPrescriptionsFromData(prescriptions);
            
            List<Referral> referrals = loader.loadReferrals(pathPrefix + "referrals.csv.crdownload");
            referralController.loadReferralsFromData(referrals);

        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= PATIENT PANEL =================
    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "NHS Number", "Email", "Phone"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Populate
        for (Patient p : patientController.getAllPatients()) {
            model.addRow(new Object[]{p.getUserId(), p.getName(), p.getNhsNumber(), p.getEmail(), p.getPhone()});
        }

        patientTable = new JTable(model);
        panel.add(new JScrollPane(patientTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            for (Patient p : patientController.getAllPatients()) {
                model.addRow(new Object[]{p.getUserId(), p.getName(), p.getNhsNumber(), p.getEmail(), p.getPhone()});
            }
        });
        
        JButton addBtn = new JButton("Add Patient (Demo)");
        addBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Patient ID (e.g., P099):");
            if(id != null && !id.isBlank()) {
                Patient p = new Patient(id, "New Patient", "email@example.com", "07000000000", "0000000000");
                patientController.addPatient(p); 
                model.addRow(new Object[]{p.getUserId(), p.getName(), p.getNhsNumber(), p.getEmail(), p.getPhone()});
            }
        });

        btnPanel.add(refreshBtn);
        btnPanel.add(addBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ================= APPOINTMENT PANEL =================
    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Date/Time", "Status", "Reason"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Appointment a : appointmentController.getAllAppointments()) {
            model.addRow(new Object[]{
                a.getAppointmentId(), a.getPatientId(), a.getClinicianId(), a.getDateTime(), a.getStatus(), a.getReason()
            });
        }

        appointmentTable = new JTable(model);
        panel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel();
        JButton cancelBtn = new JButton("Cancel Selected");
        cancelBtn.addActionListener(e -> {
            int row = appointmentTable.getSelectedRow();
            if(row >= 0) {
                String id = (String) model.getValueAt(row, 0);
                appointmentController.updateAppointment(id, null, null, "Cancelled", null);
                model.setValueAt("Cancelled", row, 4);
            }
        });
        btnPanel.add(cancelBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    // ================= PRESCRIPTION PANEL =================
    private JPanel createPrescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Medication", "Dosage", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Prescription p : prescriptionController.getAllPrescriptions()) {
            model.addRow(new Object[]{
                p.getPrescriptionId(), p.getPatientId(), p.getClinicianId(), p.getMedication(), p.getDosage(), p.getStatus()
            });
        }

        prescriptionTable = new JTable(model);
        panel.add(new JScrollPane(prescriptionTable), BorderLayout.CENTER);
        return panel;
    }

    // ================= REFERRAL PANEL =================
    private JPanel createReferralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient ID", "From GP", "To Specialist", "Urgency", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Referral r : referralController.getAllReferrals()) {
            model.addRow(new Object[]{
                r.getReferralId(), r.getPatientId(), r.getFromGpId(), r.getToSpecialistId(), r.getUrgencyLevel(), r.getStatus()
            });
        }

        referralTable = new JTable(model);
        panel.add(new JScrollPane(referralTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton letterBtn = new JButton("Generate Letter");
        letterBtn.addActionListener(e -> {
            int row = referralTable.getSelectedRow();
            if(row >= 0) {
                String id = (String) model.getValueAt(row, 0);
                Referral r = referralController.getReferral(id);
                if(r != null) {
                    try {
                        String filename = "output/Referral_" + id + ".txt";
                        // Ensure output directory exists
                        new java.io.File("output").mkdirs();
                        FileWriterUtil.generateReferralLetter(r, filename); 
                        JOptionPane.showMessageDialog(this, "Letter generated: " + filename);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error writing letter: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a referral first.");
            }
        });
        btnPanel.add(letterBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }
}