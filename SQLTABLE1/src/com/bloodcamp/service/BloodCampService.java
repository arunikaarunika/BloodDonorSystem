package com.bloodcamp.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.bloodcamp.bean.DonorRegistration;
import com.bloodcamp.bean.ScreeningResult;
import com.bloodcamp.dao.DonorRegistrationDAO;
import com.bloodcamp.util.DBUtil;
import com.bloodcamp.util.DonorAlreadyProcessedException;
import com.bloodcamp.util.MedicalDeferralException;
import com.bloodcamp.util.ValidationException;
import com.bloodcamp.dao.ScreeningResultDAO;

public class BloodCampService {
	    private DonorRegistrationDAO donorDAO = new DonorRegistrationDAO();
	    public DonorRegistration viewRegistrationDetails(String registrationID)
	            throws ValidationException {

	        
	        if (registrationID == null || registrationID.trim().isEmpty()) {
	            throw new ValidationException("Registration ID cannot be empty");
	        }

	        
	        return donorDAO.findRegistration(registrationID);
	    }

	  
	    public List<DonorRegistration> viewRegistrationsByCampDateAndStatus(
	            Date campDate, String status, String bloodGroup)
	            throws ValidationException {

	        
	        if (campDate == null) {
	            throw new ValidationException("Camp date cannot be null");
	        }

	        
	        return donorDAO.viewRegistrationsByCampDateAndStatus( campDate, status, bloodGroup);
	    }
	        private ScreeningResultDAO screeningDAO = new ScreeningResultDAO();
	        public boolean registerNewDonor(DonorRegistration registration)
	                throws ValidationException {
	            if (registration == null ||
	                registration.getRegistrationID() == null ||
	                registration.getDonarName() == null ||
	                registration.getGender() == null ||
	                registration.getContactMobile() == null ||
	                registration.getBloodGroup() == null ||
	                registration.getPreferredArrivalSlot() == null ||
	                registration.getCampDate() == null ||
	                registration.getCreatedTimestamp() == null) {

	                throw new ValidationException("Mandatory fields cannot be null");
	            }
	            if (registration.getAgeYears() < 18 || registration.getAgeYears() > 65) {
	                throw new ValidationException("Age must be between 18 and 65");
	            }
	            String gender = registration.getGender();
	            if (!(gender.equalsIgnoreCase("MALE") ||
	                  gender.equalsIgnoreCase("FEMALE") ||
	                  gender.equalsIgnoreCase("OTHER"))) {

	                throw new ValidationException("Invalid gender");
	            }
	            if (!(registration.getSelfDeclRecentIllness().equalsIgnoreCase("YES") ||
	                  registration.getSelfDeclRecentIllness().equalsIgnoreCase("NO")) ||
	                !(registration.getSelfDeclMedication().equalsIgnoreCase("YES") ||
	                  registration.getSelfDeclMedication().equalsIgnoreCase("NO"))) {

	                throw new ValidationException("Self declaration must be YES or NO");
	            }

	            
	            if (registration.getRegistrationStatus() == null) {
	                registration.setRegistrationStatus("REGISTERED");
	            }
	            DonorRegistration existing =
	                    donorDAO.findRegistration(registration.getRegistrationID());

	            if (existing != null) {
	                throw new ValidationException("Registration ID already exists");
	            }
	            return donorDAO.insertRegistration(registration);
	        }
	        public boolean cancelRegistration(String registrationID, String reason)
	                throws ValidationException, DonorAlreadyProcessedException {
	            if (registrationID == null || registrationID.trim().isEmpty() ||
	                reason == null || reason.trim().isEmpty()) {

	                throw new ValidationException("Registration ID and reason are required");
	            }
	            DonorRegistration reg =
	                    donorDAO.findRegistration(registrationID);

	            if (reg == null) {
	                return false;
	            }

	            if (screeningDAO.findScreeningByRegistration(registrationID) != null) {
	                throw new DonorAlreadyProcessedException(
	                        "Donor already screened, cannot cancel");
	            }
	            if (reg.getRegistrationStatus().equals("CANCELLED") ||
	                reg.getRegistrationStatus().equals("DONATED")) {

	                throw new ValidationException("Cannot cancel this registration");
	            }
	            return donorDAO.updateRegistrationStatusAndReason(
	                    registrationID, "CANCELLED", reason);
	        }

	        public boolean recordScreeningResult(
	                String registrationID,
	                double weightKg,
	                double hemoglobin,
	                int systolicBP,
	                int diastolicBP,
	                int pulse,
	                String decision,
	                String deferralReason,
	                java.sql.Timestamp screeningTime)
	                throws Exception {

	            Connection con = null;

	            try {
	                if (registrationID == null || registrationID.trim().isEmpty()
	                        || decision == null || decision.trim().isEmpty()
	                        || screeningTime == null) {
	                    throw new ValidationException("Mandatory fields missing");
	                }

	                if (weightKg <= 0 || hemoglobin <= 0
	                        || systolicBP <= 0 || diastolicBP <= 0 || pulse <= 0) {
	                    throw new ValidationException("Vitals must be positive values");
	                }
	                DonorRegistration reg =
	                        donorDAO.findRegistration(registrationID);

	                if (reg == null) {
	                    return false;
	                }
	                if (!"REGISTERED".equals(reg.getRegistrationStatus())) {
	                    throw new DonorAlreadyProcessedException(
	                            "Registration not in REGISTERED status");
	                }
	                if (!(decision.equalsIgnoreCase("ELIGIBLE")
	                        || decision.equalsIgnoreCase("DEFERRED"))) {
	                    throw new ValidationException("Invalid screening decision");
	                }

	                if (decision.equalsIgnoreCase("DEFERRED")
	                        && (deferralReason == null || deferralReason.trim().isEmpty())) {
	                    throw new ValidationException("Deferral reason required");
	                }
	                if (decision.equalsIgnoreCase("ELIGIBLE")) {
	                    if (hemoglobin < 12.5 || weightKg < 45) {
	                        throw new MedicalDeferralException(
	                                "Medical criteria not satisfied");
	                    }
	                }
	                con = DBUtil.getDBConnection();
	                con.setAutoCommit(false);
	                
	                int screeningID = screeningDAO.generateScreeningID();

	                ScreeningResult result = new ScreeningResult();
	                result.setScreeningID(screeningID);
	                result.setRegistrationID(registrationID);
	                result.setScreeningTimestamp(screeningTime);
	                result.setWeightKg(weightKg);
	                result.setHemoglobin(hemoglobin);
	                result.setSystolicBP(systolicBP);
	                result.setDiastolicBP(diastolicBP);
	                result.setPulseRate(pulse);
	                result.setScreeningDecision(decision);
	                result.setDeferralReason(deferralReason);
	                result.setDonationCompletedFlag("NO");
	                result.setVolumeCollectedMl(0);
	                result.setResultStatus("SUBMITTED");

	                boolean inserted =
	                        screeningDAO.insertScreeningResult(result);

	                if (!inserted) {
	                    con.rollback();
	                    return false;
	                }
	                String newStatus =
	                        decision.equalsIgnoreCase("ELIGIBLE")
	                                ? "SCREENED_ELIGIBLE"
	                                : "SCREENED_DEFERRED";

	                donorDAO.updateRegistrationStatus(
	                        registrationID, newStatus);
	                con.commit();
	                return true;

	            } catch (Exception e) {
	                try {
	                    if (con != null) {
	                        con.rollback();
	                    }
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	                throw e;

	            } finally {
	                try {
	                    if (con != null) {
	                        con.setAutoCommit(true);
	                        con.close();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        public boolean markDonationCompleted(
	                String registrationID,
	                double volumeCollectedMl,
	                String reactionNotes) {

	            if (registrationID == null || volumeCollectedMl <= 0) {
	                return false;
	            }

	            DonorRegistrationDAO donorDAO = new DonorRegistrationDAO();
	            ScreeningResultDAO screeningDAO = new ScreeningResultDAO();

	            try (Connection con = DBUtil.getDBConnection()) {

	                con.setAutoCommit(false); 

	                DonorRegistration donor =
	                        donorDAO.findRegistration(registrationID);

	                if (donor == null ||
	                    !"SCREENED_ELIGIBLE".equals(donor.getRegistrationStatus())) {
	                    return false;
	                }

	                ScreeningResult result =
	                        screeningDAO.findScreeningByRegistration(registrationID);

	                if (result == null) {
	                    return false;
	                }

	                result.setDonationCompletedFlag("YES");
	                result.setVolumeCollectedMl(volumeCollectedMl);
	                result.setImmediateReactionNotes(reactionNotes);

	                screeningDAO.updateScreeningResult(result);

	                donorDAO.updateRegistrationStatus(
	                        registrationID, "DONATED");

	                con.commit(); 
	                return true;

	            } catch (Exception e) {
	                e.printStackTrace();
	                return false;
	            }
	        }
public boolean adjustScreeningMeasurements(
        int screeningID,
        double newWeightKg,
        double newHemoglobin,
        int newSystolic,
        int newDiastolic,
        String newDecision,
        String newDeferralReason) {

    if (newWeightKg <= 0 || newHemoglobin <= 0 ||
        newSystolic <= 0 || newDiastolic <= 0) {
        return false;
    }

    ScreeningResultDAO dao = new ScreeningResultDAO();
    ScreeningResult result = dao.findScreeningById(screeningID);

    if (result == null) {
        return false;
    }

    result.setWeightKg(newWeightKg);
    result.setHemoglobin(newHemoglobin);
    result.setSystolicBP(newSystolic);
    result.setDiastolicBP(newDiastolic);
    result.setScreeningDecision(newDecision);
    result.setDeferralReason(newDeferralReason);

    return dao.updateScreeningResult(result);
}
public Map<String, Integer> getCampSummary(Date campDate) {
    ScreeningResultDAO dao = new ScreeningResultDAO();
    return dao.computeCampSummaryByBloodGroup(campDate);
}



       	    
	}
