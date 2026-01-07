package main;

import model.Referral;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ReferralManager - Singleton pattern for managing referral queue.
 *
 * Responsibilities:
 * - Maintain single referral queue across entire system
 * - Process referrals (queue & update EHR)
 * - Persist referrals to CSV file
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class ReferralManager {

    private static ReferralManager instance;
    private List<Referral> referralQueue;

    private ReferralManager() {
        this.referralQueue = new ArrayList<>();
    }

    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    /**
     * Create a new referral - add to queue and update EHR
     *
     * @param referral The referral to process
     */
    public void createReferral(Referral referral) {
        referralQueue.add(referral);
        updateEHR(referral);
    }

    /**
     * Update the patient's Electronic Health Record with referral info
     *
     * @param referral The referral just created
     */
    public void updateEHR(Referral referral) {
        System.out.println("EHR updated for patient: " + referral.getPatientId());
    }

    /**
     * Get a copy of the referral queue
     *
     * @return Copy of referralQueue
     */
    public List<Referral> getReferralQueue() {
        return new ArrayList<>(referralQueue);
    }

    /**
     * Persist all referrals to a CSV file
     *
     * @param filepath Path where to save the CSV
     * @throws IOException If file writing fails
     */
    public void persistReferralsToFile(String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);

        for (Referral r : referralQueue) {
            writer.write("ID: " + r.getReferralId() + "\n");
            writer.write("Patient: " + r.getPatientId() + "\n");
            writer.write("From GP: " + r.getFromGpId() + "\n");
            writer.write("To Specialist: " + r.getToSpecialistId() + "\n");
            writer.write("Reason: " + r.getReason() + "\n");
            writer.write("Status: " + r.getStatus() + "\n");
            writer.write("Urgency: " + r.getUrgencyLevel() + "\n");
            writer.write("Referral Date: " + r.getReferralDate() + "\n\n");
        }

        writer.close();
    }
}
