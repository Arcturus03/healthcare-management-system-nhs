package controller;

import model.Clinician;
import java.util.*;

/**
 * ClinicianController manages all Clinician operations (CRUD).
 *  Didn't add any more comments as I made this last minute
 * 
 * @author Hrithik Chandra
 * @version 1.0
 */
public class ClinicianController {

    private List<Clinician> clinicians;

    public ClinicianController() {
        this.clinicians = new ArrayList<>();
    }

    public boolean addClinician(Clinician clinician) {
        if (clinician == null) return false;
        clinicians.add(clinician);
        System.out.println("Clinician added: " + clinician.getName());
        return true;
    }

    public Clinician getClinician(String clinicianId) {
        return clinicians.stream()
                .filter(c -> c.getUserId().equals(clinicianId))
                .findFirst()
                .orElse(null);
    }

    public List<Clinician> getCliniciansBySpecialty(String specialty) {
        return clinicians.stream()
                .filter(c -> c.getSpeciality().equalsIgnoreCase(specialty))
                .toList();
    }

    public boolean updateClinician(Clinician clinician) {
        Clinician existing = getClinician(clinician.getUserId());
        if (existing != null) {
            existing.setName(clinician.getName());
            existing.setEmail(clinician.getEmail());
            existing.setSpeciality(clinician.getSpeciality());
            return true;
        }
        return false;
    }

    public boolean deleteClinician(String clinicianId) {
        return clinicians.removeIf(c -> c.getUserId().equals(clinicianId));
    }

    public List<Clinician> getAllClinicians() {
        return new ArrayList<>(clinicians);
    }

    public void loadCliniciansFromData(List<Clinician> loadedClinicians) {
        clinicians.clear();
        if (loadedClinicians != null) {
            clinicians.addAll(loadedClinicians);
        }
        System.out.println("Loaded " + clinicians.size() + " clinicians into controller");
    }

    public int getClinicianCount() {
        return clinicians.size();
    }
}