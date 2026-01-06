package main;

import model.Referral;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class ReferralManager {
    private static ReferralManager instance;
    private List<Referral> referralQueue;
    private List<String> emailLog;

    private ReferralManager() {
        this.referralQueue = new ArrayList<>();
        this.emailLog = new ArrayList<>();
    }

    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    public void createReferral(Referral referral) {
        referralQueue.add(referral);
        sendReferralEmail(referral);
        updateEHR(referral);
    }

    public void sendReferralEmail(Referral referral) {
        String email = "From: GP\nTo: Specialist\nReferral: " + referral.getReferralId();
        emailLog.add(email);
        System.out.println("Email sent for referral: " + referral.getReferralId());
    }

    public void updateEHR(Referral referral) {
        System.out.println("EHR updated for patient: " + referral.getPatientId());
    }

    public List<Referral> getReferralQueue() {
        return new ArrayList<>(referralQueue);
    }

    public void persistReferralsToFile(String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        for (Referral r : referralQueue) {
            writer.write("ID: " + r.getReferralId() + "\n");
            writer.write("Patient: " + r.getPatientId() + "\n");
            writer.write("Status: " + r.getStatus() + "\n\n");
        }
        writer.close();
    }
}
