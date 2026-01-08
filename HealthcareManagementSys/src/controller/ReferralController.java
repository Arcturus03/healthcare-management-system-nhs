package controller;

import model.Referral;
import main.ReferralManager;
import java.io.IOException;
import java.util.*;

/**
 * ReferralController manages all Referral operations using the Singleton ReferralManager.

 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class ReferralController {

    private List<Referral> referrals;

    private ReferralManager referralManager;

    // Constructor
    public ReferralController() {
        this.referrals = new ArrayList<>();

        this.referralManager = ReferralManager.getInstance();
    }

    /**
     * CREATE: Add a new referral to the system
     *
     *
     * @param referral The Referral object to create
     * @return true if created successfully
     */
    public boolean createReferral(Referral referral) {
        // Validate referral
        if (referral == null) {
            System.err.println("ERROR: Cannot create null referral");
            return false;
        }

        // Add to local list (for GUI to display)
        referrals.add(referral);

        // Send to ReferralManager Singleton for OFFICIAL processing
        referralManager.createReferral(referral);

        System.out.println(" Referral created and queued: " + referral.getReferralId());

        return true;
    }

    /**
     * READ: Get a specific referral by ID
     *
     * @param referralId The ID to search for
     * @return The Referral object, or null if not found
     */
    public Referral getReferral(String referralId) {
        return referrals.stream()
                .filter(ref -> ref.getReferralId().equals(referralId))
                .findFirst()
                .orElse(null);
    }

    /**
     * READ: Get all referrals for a specific patient
     *
     * @param patientId The patient's ID
     * @return List of all referrals for that patient
     */
    public List<Referral> getReferralsByPatient(String patientId) {
        return referrals.stream()
                .filter(ref -> ref.getPatientId().equals(patientId))
                .toList();
    }

    /**
     * READ: Get all referrals from a specific GP
     *
     * @param gpId The GP's ID
     * @return List of all referrals sent by that GP
     */
    public List<Referral> getReferralsByGP(String gpId) {
        return referrals.stream()
                .filter(ref -> ref.getFromGpId().equals(gpId))
                .toList();
    }

    /**
     * READ: Get all referrals TO a specific specialist
     *
     *
     * @param specialistId The specialist's ID
     * @return List of all referrals received by that specialist
     */
    public List<Referral> getReferralsBySpecialist(String specialistId) {
        return referrals.stream()
                .filter(ref -> ref.getToSpecialistId().equals(specialistId))
                .toList();
    }

    /**
     * READ: Get all referrals with a specific status
     *
     * REFERRAL STATUSES:
     * - "Pending" = Waiting for specialist response
     * - "Accepted" = Specialist will see patient
     * - "Rejected" = Specialist declined
     * - "Completed" = Patient seen, treatment finished
     * - "In Progress" = Patient currently being treated
     *
     * @param status The status to filter by
     * @return List of referrals with that status
     */
    public List<Referral> getReferralsByStatus(String status) {
        return referrals.stream()
                .filter(ref -> ref.getStatus().equals(status))
                .toList();
    }

    /**
     * READ: Get all URGENT referrals
     *
     * @return List of all urgent referrals
     */
    public List<Referral> getUrgentReferrals() {
        return referrals.stream()
                .filter(ref -> ref.getUrgencyLevel().equals("Urgent") || ref.getUrgencyLevel().equals("Emergency"))
                .toList();
    }

    /**
     * UPDATE: Change referral status
     *
     * @param referralId The referral to update
     * @param newStatus The new status
     * @return true if updated, false if not found
     */
    public boolean updateReferralStatus(String referralId, String newStatus) {
        // Find the referral
        Referral existing = getReferral(referralId);

        if (existing == null) {
            System.err.println("ERROR: Referral " + referralId + " not found");
            return false;
        }

        // Update status
        existing.setStatus(newStatus);

        System.out.println(" Referral status updated: " + referralId + " → " + newStatus);

        return true;
    }

    /**
     * DELETE: Remove a referral from the system
     *
     * @param referralId The ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteReferral(String referralId) {
        boolean removed = referrals.removeIf(ref -> ref.getReferralId().equals(referralId));

        if (removed) {
            System.out.println(" Referral deleted: " + referralId);
        } else {
            System.err.println("ERROR: Referral " + referralId + " not found");
        }

        return removed;
    }

    /**
     * READ: Get all referrals
     *
     * @return Copy of all referrals
     */
    public List<Referral> getAllReferrals() {
        return new ArrayList<>(referrals);
    }

    /**
     * SINGLETON ACCESS: Get the ReferralManager queue
     *
     * @return List of referrals in the Singleton queue
     */
    public List<Referral> getReferralQueueFromSingleton() {
        // Get directly from ReferralManager Singleton
        return referralManager.getReferralQueue();
    }

    /**
     * SAVE ALL REFERRALS TO CSV FILE
     *
     * @param filepath Where to save the CSV file
     * @throws IOException If file writing fails
     */
    public void saveReferralsToFile(String filepath) throws IOException {
        try {
            // Save referrals as CSV through Singleton
            referralManager.persistReferralsToFile(filepath);
            System.out.println(" Referrals persisted to file: " + filepath);
        } catch (IOException e) {
            System.err.println("ERROR: Could not save referrals to file");
            throw e;
        }
    }

    /**
     * COUNT: Total referrals
     *
     * @return Total number of referrals
     */
    public int getReferralCount() {
        return referrals.size();
    }

    /**
     * POPULATE FROM CSV DATA
     *
     * @param loadedReferrals List of referrals from CSV
     */
    public void loadReferralsFromData(List<Referral> loadedReferrals) {
        referrals.clear();
        referrals.addAll(loadedReferrals);
        System.out.println(" Loaded " + referrals.size() + " referrals into controller");
    }
}