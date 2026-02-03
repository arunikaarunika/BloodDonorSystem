package com.bloodcamp.app;

import com.bloodcamp.bean.DonorRegistration;
import com.bloodcamp.service.BloodCampService;
import com.bloodcamp.util.DonorAlreadyProcessedException;
import com.bloodcamp.util.MedicalDeferralException;
import com.bloodcamp.util.ValidationException;

public class BloodCampMain {

    private static BloodCampService service = new BloodCampService();

    public static void main(String[] args) {

        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.println("--- Neighborhood Blood Donation Camp Console ---");
        try {
            DonorRegistration d = new DonorRegistration();

            d.setRegistrationID("D35");
            d.setCampDate(new java.sql.Date(System.currentTimeMillis()));
            d.setDonarName("Arunika");
            d.setAgeYears(40);
            d.setGender("FEMALE");
            d.setContactMobile("9998887770");
            d.setEmail("arunika.rao@example.com");
            d.setBloodGroup("O+");
            d.setSelfDeclRecentIllness("NO");
            d.setSelfDeclMedication("NO");
            d.setPreferredArrivalSlot("10:00-11:00");
            d.setRegistrationStatus("REGISTERED");
            d.setCreatedTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

            boolean ok = service.registerNewDonor(d);
            System.out.println(ok ? "DONOR REGISTERED" : "DONOR REGISTRATION FAILED");

        } 
        catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } 
        catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
        try {
            java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

            boolean ok = service.recordScreeningResult(
                    "RG2001",
                    60.5,
                    13.8,
                    120,
                    78,
                    76,
                    "ELIGIBLE",
                    "",
                    now
            );

            System.out.println(ok ? "SCREENING RECORDED" : "SCREENING FAILED");

        } 
        catch (MedicalDeferralException e) {
            System.out.println("Deferral Rule: " + e.getMessage());
        } 
        catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } 
        catch (DonorAlreadyProcessedException e) {
            System.out.println("Processing Error: " + e.getMessage());
        } 
        catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        sc.close();
    }
}
