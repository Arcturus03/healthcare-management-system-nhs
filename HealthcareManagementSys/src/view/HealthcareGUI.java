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
    private ClinicianController clinicianController;  // FIX 1: Add missing field

    private JTabbedPane tabbedPane;
    private JTable patientTable;
    private JTable appointmentTable;
    private JTable prescriptionTable;
    private JTable referralTable;
    private JTable clinicianTable;  // FIX 2: Add missing field

    public HealthcareGUI() {
        // Initialize Controllers
        patientController = new PatientController();
        appointmentController = new AppointmentController();
        prescriptionController = new PrescriptionController();
        referralController = new ReferralController();
        clinicianController = new ClinicianController();  // FIX 3: Initialize controller

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
        tabbedPane.addTab("Clinicians", createClinicianPanel());  // FIX 4: Added semicolon
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

            List<Clinician> clinicians = loader.loadClinicians(pathPrefix + "clinicians.csv.crdownload");
            clinicianController.loadCliniciansFromData(clinicians);

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

    /**
     * Requirement: Data Persistence.
     * Saves all in-memory data back to CSV files (simulating database update).
     */
    private void saveAllData() {
        try {
            // Determine path (similar logic to loadData)
            String pathPrefix = "data/";
            if (!new java.io.File(pathPrefix + "patients.csv.crdownload").exists()) {
                pathPrefix = "../data/";
            }

            // We write to new files to avoid corrupting the original downloads, 
            // or overwrite if that is the strict requirement. 
            // Here we overwrite for "Persistent" requirement.
            
            FileWriterUtil.writePatientsToFile(patientController.getAllPatients(), pathPrefix + "patients.csv.crdownload");
            FileWriterUtil.writeCliniciansToFile(clinicianController.getAllClinicians(), pathPrefix + "clinicians.csv.crdownload");  // FIX 5: Add clinician save
            FileWriterUtil.writeAppointmentsToFile(appointmentController.getAllAppointments(), pathPrefix + "appointments.csv.crdownload");
            FileWriterUtil.writePrescriptionsToFile(prescriptionController.getAllPrescriptions(), pathPrefix + "prescriptions.csv.crdownload");
            FileWriterUtil.writeReferralsToFile(referralController.getAllReferrals(), pathPrefix + "referrals.csv.crdownload");
            
            JOptionPane.showMessageDialog(this, "All changes saved to disk successfully.");
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    // ================= PATIENT PANEL =================
    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "NHS Number", "Email", "Phone"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Prevent direct table editing
        };

        // Populate
        for (Patient p : patientController.getAllPatients()) {
            model.addRow(new Object[]{p.getUserId(), p.getName(), p.getNhsNumber(), p.getEmail(), p.getPhone()});
        }

        patientTable = new JTable(model);
        panel.add(new JScrollPane(patientTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        
        // --- ADD ---
        JButton addBtn = new JButton("Add Patient");
        addBtn.addActionListener(e -> {
            JTextField idField = new JTextField("P" + (model.getRowCount() + 100)); // Auto-gen suggestion
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField phoneField = new JTextField();
            
            Object[] message = {"ID:", idField, "Name:", nameField, "Email:", emailField, "Phone:", phoneField};

            if (JOptionPane.showConfirmDialog(null, message, "Add Patient", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                Patient p = new Patient(idField.getText(), nameField.getText(), emailField.getText(), phoneField.getText(), "NHS-Gen");
                if (patientController.addPatient(p)) {
                    model.addRow(new Object[]{p.getUserId(), p.getName(), p.getNhsNumber(), p.getEmail(), p.getPhone()});
                }
            }
        });

        // --- EDIT ---
        JButton editBtn = new JButton("Edit Selected");
        editBtn.addActionListener(e -> {
            int row = patientTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a patient first."); return; }
            
            String id = (String) model.getValueAt(row, 0);
            Patient p = patientController.getPatient(id);
            
            if (p != null) {
                JTextField nameField = new JTextField(p.getName());
                JTextField emailField = new JTextField(p.getEmail());
                JTextField phoneField = new JTextField(p.getPhone());
                
                Object[] message = {"Name:", nameField, "Email:", emailField, "Phone:", phoneField};

                if (JOptionPane.showConfirmDialog(null, message, "Edit Patient Details", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    // Update Object
                    p.setName(nameField.getText());
                    p.setEmail(emailField.getText());
                    // Note: Patient class doesn't have setPhone visible in diagram sometimes, but User class does.
                    // Assuming standard setters inherited from User:
                    // p.setPhone(phoneField.getText()); // If available in User class
                    
                    patientController.updatePatient(p);
                    
                    // Update UI
                    model.setValueAt(nameField.getText(), row, 1);
                    model.setValueAt(emailField.getText(), row, 3);
                    model.setValueAt(phoneField.getText(), row, 4);
                }
            }
        });

        // --- DELETE ---
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setForeground(Color.RED); // Visual cue for delete
        deleteBtn.addActionListener(e -> {
            int row = patientTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a patient first."); return; }
            
            String id = (String) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete patient " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (patientController.deletePatient(id)) {
                    model.removeRow(row);
                }
            }
        });

        // --- SAVE ---
        JButton saveBtn = new JButton("Save All Data");
        saveBtn.addActionListener(e -> saveAllData());

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));
        btnPanel.add(saveBtn);
        
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }



    // ================= APPOINTMENT PANEL =================
    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Date/Time", "Status", "Reason"};
        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Appointment a : appointmentController.getAllAppointments()) {
            model.addRow(new Object[]{
                a.getAppointmentId(), a.getPatientId(), a.getClinicianId(), a.getDateTime(), a.getStatus(), a.getReason()
            });
        }

        appointmentTable = new JTable(model);
        panel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel();

        // --- ADD ---
        JButton addBtn = new JButton("Book Appointment");
        addBtn.addActionListener(e -> {
            JTextField patientField = new JTextField();
            JTextField clinicianField = new JTextField();
            JTextField dateField = new JTextField("2026-01-01 09:00");
            JTextField reasonField = new JTextField();

            Object[] message = {"Patient ID:", patientField, "Clinician ID:", clinicianField, "Date (YYYY-MM-DD HH:MM):", dateField, "Reason:", reasonField};

            if (JOptionPane.showConfirmDialog(null, message, "Book Appointment", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String id = "A" + (model.getRowCount() + 200); 
                Appointment newAppt = new Appointment(id, patientField.getText(), clinicianField.getText(), dateField.getText(), "Surgery");
                newAppt.setReason(reasonField.getText());
                
                if(appointmentController.addAppointment(newAppt)) {
                    model.addRow(new Object[]{id, newAppt.getPatientId(), newAppt.getClinicianId(), newAppt.getDateTime(), "Scheduled", newAppt.getReason()});
                }
            }
        });

        // --- EDIT ---
        JButton editBtn = new JButton("Edit/Reschedule");
        editBtn.addActionListener(e -> {
            int row = appointmentTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select an appointment."); return; }

            String id = (String) model.getValueAt(row, 0);
            String currentDateTime = (String) model.getValueAt(row, 3);
            String currentReason = (String) model.getValueAt(row, 5);

            JTextField dateField = new JTextField(currentDateTime);
            JTextField reasonField = new JTextField(currentReason);
            String[] statuses = {"Scheduled", "Completed", "Cancelled"};
            JComboBox<String> statusBox = new JComboBox<>(statuses);
            statusBox.setSelectedItem(model.getValueAt(row, 4));

            Object[] message = {"New Date/Time:", dateField, "Status:", statusBox, "Reason:", reasonField};

            if (JOptionPane.showConfirmDialog(null, message, "Edit Appointment", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String newStatus = (String) statusBox.getSelectedItem();
                appointmentController.updateAppointment(id, dateField.getText(), null, newStatus, reasonField.getText());
                
                model.setValueAt(dateField.getText(), row, 3);
                model.setValueAt(newStatus, row, 4);
                model.setValueAt(reasonField.getText(), row, 5);
            }
        });

        // --- DELETE ---
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> {
            int row = appointmentTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) model.getValueAt(row, 0);
                if(JOptionPane.showConfirmDialog(this, "Delete Appointment " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (appointmentController.deleteAppointment(id)) { model.removeRow(row); }
                }
            }
        });
        
        JButton saveBtn = new JButton("Save All Data");
        saveBtn.addActionListener(e -> saveAllData());

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));
        btnPanel.add(saveBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }



    // ================= PRESCRIPTION PANEL =================
    private JPanel createPrescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Medication", "Dosage", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Prescription p : prescriptionController.getAllPrescriptions()) {
            model.addRow(new Object[]{
                p.getPrescriptionId(), p.getPatientId(), p.getClinicianId(), p.getMedication(), p.getDosage(), p.getStatus()
            });
        }

        prescriptionTable = new JTable(model);
        panel.add(new JScrollPane(prescriptionTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        
        // --- ADD ---
        JButton issueBtn = new JButton("Issue New");
        issueBtn.addActionListener(e -> {
            JTextField patientField = new JTextField();
            JTextField medField = new JTextField();
            JTextField dosageField = new JTextField();
            Object[] message = {"Patient ID:", patientField, "Medication:", medField, "Dosage:", dosageField};

            if (JOptionPane.showConfirmDialog(null, message, "Issue Prescription", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String id = "RX" + (model.getRowCount() + 200);
                Prescription p = new Prescription(id, patientField.getText(), "C001", medField.getText(), dosageField.getText());
                if(prescriptionController.addPrescription(p)) {
                    model.addRow(new Object[]{p.getPrescriptionId(), p.getPatientId(), p.getClinicianId(), p.getMedication(), p.getDosage(), p.getStatus()});
                }
            }
        });

        // --- EDIT ---
        JButton editBtn = new JButton("Edit");
        editBtn.addActionListener(e -> {
            int row = prescriptionTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a prescription."); return; }

            String id = (String) model.getValueAt(row, 0);
            JTextField medField = new JTextField((String) model.getValueAt(row, 3));
            JTextField dosageField = new JTextField((String) model.getValueAt(row, 4));

            Object[] message = {"Medication:", medField, "Dosage:", dosageField};
            
            if (JOptionPane.showConfirmDialog(null, message, "Edit Prescription", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                // Call updateDetail method in controller
                prescriptionController.updatePrescriptionDetails(id, medField.getText(), dosageField.getText(), null, null);
                model.setValueAt(medField.getText(), row, 3);
                model.setValueAt(dosageField.getText(), row, 4);
            }
        });
        
        // --- DELETE ---
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> {
            int row = prescriptionTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) model.getValueAt(row, 0);
                if(JOptionPane.showConfirmDialog(this, "Delete " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (prescriptionController.deletePrescription(id)) { model.removeRow(row); }
                }
            }
        });
        
        // --- CHANGE STATUS (Bidirectional: Issued <-> Collected) ---
        JButton changeStatusBtn = new JButton("Change Status");
        changeStatusBtn.addActionListener(e -> {
            int row = prescriptionTable.getSelectedRow();
            if (row < 0) { 
                JOptionPane.showMessageDialog(this, "Select a prescription first."); 
                return; 
            }
            
            String id = (String) model.getValueAt(row, 0);
            String currentStatus = (String) model.getValueAt(row, 5);
            
            // Dropdown with status options
            String[] statuses = {"Issued", "Collected", "Expired"};
            String newStatus = (String) JOptionPane.showInputDialog(
                this, 
                "Current Status: " + currentStatus + "\n\nSelect New Status:", 
                "Change Prescription Status", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                statuses, 
                currentStatus  // pre-select current status
            );
            
            if (newStatus != null && !newStatus.equals(currentStatus)) {
                prescriptionController.updatePrescriptionStatus(id, newStatus);
                model.setValueAt(newStatus, row, 5);
                JOptionPane.showMessageDialog(this, "Status changed from " + currentStatus + " to " + newStatus);
            } else if (newStatus != null) {
                JOptionPane.showMessageDialog(this, "Status unchanged - same status selected.");
            }
        });

        JButton saveBtn = new JButton("Save All");
        saveBtn.addActionListener(e -> saveAllData());

        btnPanel.add(issueBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(changeStatusBtn);  // Replaces "Mark Collected" button
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));
        btnPanel.add(saveBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    
    }




    // ================= CLINICIAN PANEL =================
    private JPanel createClinicianPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "First Name", "Last Name", "Title", "Speciality", "GMC", "Phone", "Email", "Workplace", "Status", "Start Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Clinician c : clinicianController.getAllClinicians()) {
            model.addRow(new Object[]{
                c.getClinicianId(), c.getFirstName(), c.getLastName(), c.getTitle(),
                c.getSpeciality(), c.getGmcNumber(), c.getPhone(), c.getEmail(),
                c.getWorkplaceType(), c.getEmploymentStatus(), c.getStartDate()
            });
        }

        clinicianTable = new JTable(model);
        panel.add(new JScrollPane(clinicianTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        // --- ADD ---
        JButton addBtn = new JButton("Add Clinician");
        addBtn.addActionListener(e -> {
            JTextField idField = new JTextField("C" + String.format("%03d", model.getRowCount() + 13));
            JTextField firstNameField = new JTextField();
            JTextField lastNameField = new JTextField();
            String[] titles = {"Dr.", "Consultant", "Senior Nurse", "Practice Nurse", "Staff Nurse"};
            JComboBox<String> titleBox = new JComboBox<>(titles);
            JTextField specialityField = new JTextField();
            JTextField gmcField = new JTextField();
            JTextField phoneField = new JTextField("07");
            JTextField emailField = new JTextField();
            JTextField workplaceIdField = new JTextField("S001");
            String[] workplaceTypes = {"GP Surgery", "Hospital"};
            JComboBox<String> workplaceBox = new JComboBox<>(workplaceTypes);
            String[] statuses = {"Full-time", "Part-time"};
            JComboBox<String> statusBox = new JComboBox<>(statuses);
            JTextField startDateField = new JTextField("2025-01-01");

            Object[] message = {
                "Clinician ID:", idField,
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Title:", titleBox,
                "Speciality:", specialityField,
                "GMC Number:", gmcField,
                "Phone:", phoneField,
                "Email:", emailField,
                "Workplace ID:", workplaceIdField,
                "Workplace Type:", workplaceBox,
                "Employment Status:", statusBox,
                "Start Date (YYYY-MM-DD):", startDateField
            };

            if (JOptionPane.showConfirmDialog(null, message, "Add Clinician", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                Clinician c = new Clinician(
                    idField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    (String) titleBox.getSelectedItem(),
                    specialityField.getText(),
                    gmcField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    workplaceIdField.getText(),
                    (String) workplaceBox.getSelectedItem(),
                    (String) statusBox.getSelectedItem(),
                    startDateField.getText()
                );
                if (clinicianController.addClinician(c)) {  // FIX 6: Use instance method (lowercase 'c')
                    model.addRow(new Object[]{
                        c.getClinicianId(), c.getFirstName(), c.getLastName(), c.getTitle(),
                        c.getSpeciality(), c.getGmcNumber(), c.getPhone(), c.getEmail(),
                        c.getWorkplaceType(), c.getEmploymentStatus(), c.getStartDate()
                    });
                }
            }
        });

        // --- EDIT ---
        JButton editBtn = new JButton("Edit Selected");
        editBtn.addActionListener(e -> {
            int row = clinicianTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a clinician first."); return; }

            String id = (String) model.getValueAt(row, 0);
            Clinician c = clinicianController.getClinician(id);

            if (c != null) {
                JTextField firstNameField = new JTextField(c.getFirstName());
                JTextField lastNameField = new JTextField(c.getLastName());
                JTextField specialityField = new JTextField(c.getSpeciality());
                JTextField phoneField = new JTextField(c.getPhone());
                JTextField emailField = new JTextField(c.getEmail());
                String[] statuses = {"Full-time", "Part-time"};
                JComboBox<String> statusBox = new JComboBox<>(statuses);
                statusBox.setSelectedItem(c.getEmploymentStatus());

                Object[] message = {
                    "First Name:", firstNameField,
                    "Last Name:", lastNameField,
                    "Speciality:", specialityField,
                    "Phone:", phoneField,
                    "Email:", emailField,
                    "Employment Status:", statusBox
                };

                if (JOptionPane.showConfirmDialog(null, message, "Edit Clinician", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    c.setFirstName(firstNameField.getText());
                    c.setLastName(lastNameField.getText());
                    c.setSpeciality(specialityField.getText());
                    c.setPhone(phoneField.getText());
                    c.setEmail(emailField.getText());
                    c.setEmploymentStatus((String) statusBox.getSelectedItem());
                    clinicianController.updateClinician(c);

                    // Update table row
                    model.setValueAt(c.getFirstName(), row, 1);
                    model.setValueAt(c.getLastName(), row, 2);
                    model.setValueAt(c.getSpeciality(), row, 4);
                    model.setValueAt(c.getPhone(), row, 6);
                    model.setValueAt(c.getEmail(), row, 7);
                    model.setValueAt(c.getEmploymentStatus(), row, 9);
                }
            }
        });

        // --- DELETE ---
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setForeground(Color.RED);
        deleteBtn.addActionListener(e -> {
            int row = clinicianTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a clinician first."); return; }

            String id = (String) model.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "Delete clinician " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (clinicianController.deleteClinician(id)) {
                    model.removeRow(row);
                }
            }
        });

        // --- SAVE ---
        JButton saveBtn = new JButton("Save All Data");
        saveBtn.addActionListener(e -> saveAllData());

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));
        btnPanel.add(saveBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }





    // ================= REFERRAL PANEL =================
    private JPanel createReferralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Patient ID", "From GP", "To Specialist", "Urgency", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Referral r : referralController.getAllReferrals()) {
            model.addRow(new Object[]{
                r.getReferralId(), r.getPatientId(), r.getFromGpId(), r.getToSpecialistId(), r.getUrgencyLevel(), r.getStatus()
            });
        }

        referralTable = new JTable(model);
        panel.add(new JScrollPane(referralTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        // --- NEW ---
        JButton createBtn = new JButton("New Referral");
        createBtn.addActionListener(e -> {
            JTextField patientField = new JTextField();
            JTextField specField = new JTextField();
            JTextField reasonField = new JTextField();
            String[] urgencies = {"Routine", "Urgent", "Emergency"};
            JComboBox<String> urgencyBox = new JComboBox<>(urgencies);

            Object[] message = {"Patient ID:", patientField, "To Specialist ID:", specField, "Urgency:", urgencyBox, "Reason:", reasonField};

            if (JOptionPane.showConfirmDialog(null, message, "Create Referral", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String id = "R" + (model.getRowCount() + 100);
                Referral r = new Referral(id, patientField.getText(), "C001", specField.getText(), reasonField.getText());
                r.setUrgencyLevel((String) urgencyBox.getSelectedItem());
                
                if(referralController.createReferral(r)) {
                    model.addRow(new Object[]{r.getReferralId(), r.getPatientId(), r.getFromGpId(), r.getToSpecialistId(), r.getUrgencyLevel(), r.getStatus()});
                }
            }
        });

        // --- UPDATE STATUS ---
        JButton updateBtn = new JButton("Update Status");
        updateBtn.addActionListener(e -> {
            int row = referralTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a referral first."); return; }

            String id = (String) model.getValueAt(row, 0);
            String currentStatus = (String) model.getValueAt(row, 5);
            
            String[] statuses = {"Pending", "Approved", "In Progress", "Completed", "Rejected"};
            String newStatus = (String) JOptionPane.showInputDialog(this, "Set Status:", "Update Referral", JOptionPane.QUESTION_MESSAGE, null, statuses, currentStatus);
            
            if(newStatus != null) {
                referralController.updateReferralStatus(id, newStatus);
                model.setValueAt(newStatus, row, 5);
            }
        });

        // --- DELETE ---
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> {
            int row = referralTable.getSelectedRow();
            if (row >= 0) {
                String id = (String) model.getValueAt(row, 0);
                if(JOptionPane.showConfirmDialog(this, "Delete Referral " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (referralController.deleteReferral(id)) { model.removeRow(row); }
                }
            }
        });

        // --- LETTER ---
        JButton letterBtn = new JButton("Generate Letter");
        letterBtn.addActionListener(e -> {
            // (Existing letter logic remains here...)
            int row = referralTable.getSelectedRow();
            if(row >= 0) {
                String id = (String) model.getValueAt(row, 0);
                Referral r = referralController.getReferral(id);
                if(r != null) {
                    try {
                        String filename = "output/Referral_" + id + ".txt";
                        new java.io.File("output").mkdirs();
                        FileWriterUtil.generateReferralLetter(r, filename); 
                        JOptionPane.showMessageDialog(this, "Letter generated: " + filename);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                    }
                }
            }
        });
        

        // SAVE
        JButton saveBtn = new JButton("Save All Data");
        saveBtn.addActionListener(e -> saveAllData());

        btnPanel.add(createBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(letterBtn);
        btnPanel.add(new JSeparator(SwingConstants.VERTICAL));  // Separator for clarity
        btnPanel.add(saveBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        

        return panel;
    }
}