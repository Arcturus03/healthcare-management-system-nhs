package controller;

import model.Referral;
import main.ReferralManager;
import java.io.IOException;
import java.util.*;

/**
 * ReferralController manages all Referral operations using the Singleton ReferralManager.
 *
 * WHY SINGLETON FOR REFERRALS?
 * - Only ONE referral processing queue across entire system
 * - Prevents duplicate referral processing
 * - Maintains single audit trail
 *
 * REFERRAL WORKFLOW:
 * 1. GP creates referral to specialist
 * 2. ReferralManager queues it (Singleton ensures single queue)
 * 3. System updates EHR with referral info
 * 4. Specialist reviews and accepts/rejects
 * 5. Patient record updated
 *
 * THIS CONTROLLER is the bridge between:
 * - GUI (creates referral request)
 * - ReferralManager (Singleton that processes it)
 * - Model (Referral object with data)
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class ReferralController {

    // === INSTANCE VARIABLES ===
    // We keep a local copy for quick GUI access
    // But the REAL processing happens in ReferralManager Singleton
    private List<Referral> referrals;

    // Get the SINGLETON instance of ReferralManager
    // This is the same instance used everywhere in the app
    private ReferralManager referralManager;

    /**
     * CONSTRUCTOR
     *
     * Initialize controller and get Singleton instance
     */
    public ReferralController() {
        this.referrals = new ArrayList<>();

        // Get the SINGLE ReferralManager instance
        // First call creates it
        // All subsequent calls return the SAME instance
        this.referralManager = ReferralManager.getInstance();
    }

    /**
     * CREATE: Add a new referral to the system
     *
     * WHAT IT DOES:
     * 1. Adds referral to our local list (for GUI)
     * 2. Sends referral to ReferralManager Singleton
     * 3. ReferralManager queues it and updates EHR
     *
     * WHY TWO PLACES?
     * - Local list: fast access for GUI display
     * - ReferralManager: official processing with audit trail
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
        // This handles:
        // - Queuing
        // - EHR update
        // - Audit trail
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
     * WHY USEFUL?
     * - Patient wants to see their referral history
     * - Doctor checks what specialists patient is seeing
     * - Care coordinator tracks all referrals for patient
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
     * WHY USEFUL?
     * - GP review audit: what referrals did I send?
     * - Compliance check: ensuring appropriate referrals
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
     * WHY USEFUL?
     * - Specialist checks their referral queue
     * - Workload management: how many referrals came this week?
     * - Appointment scheduling based on referral count
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
     * WHY USEFUL?
     * - Specialist dashboard: urgent cases first
     * - Hospital emergency triage
     * - SLA tracking (urgent referrals should be seen within 2 weeks)
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
     * STATUS TRANSITIONS:
     * "Pending" → "Accepted" (specialist agrees to see patient)
     * "Pending" → "Rejected" (specialist declines)
     * "Accepted" → "In Progress" (patient appointment scheduled/started)
     * "In Progress" → "Completed" (treatment finished)
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
     * WHY MIGHT WE DELETE?
     * - Referral created by mistake
     * - Patient requests cancellation
     * - Administrative error correction
     *
     * NOTE: In real healthcare, deletion is rare (audit trails prevent it)
     * Usually we'd just mark as "Cancelled" instead
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
     * This shows referrals in the SINGLETON queue
     * (not necessarily the same as local referrals list)
     *
     * WHY EXPOSE THIS?
     * - GUI might want to show what's actually in processing queue
     * - Different from local list (which is for display)
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
     * Persists all referrals to output file for archival/backup.
     * Can be imported to Excel, databases, or other analysis tools.
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
     * Called after DataLoader reads referrals.csv
     *
     * @param loadedReferrals List of referrals from CSV
     */
    public void loadReferralsFromData(List<Referral> loadedReferrals) {
        referrals.clear();
        referrals.addAll(loadedReferrals);
        System.out.println(" Loaded " + referrals.size() + " referrals into controller");
    }
}