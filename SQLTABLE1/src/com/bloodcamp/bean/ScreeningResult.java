package com.bloodcamp.bean;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ScreeningResult {
		private int screeningID;
		private String registrationID;
		private Timestamp screeningTimestamp;
		private double weightKg;
		private double hemoglobin;
		private int systolicBP;
		private int diastolicBP;
		private int pulseRate;
		private String screeningDecision;
		private String deferralReason;
		private String donationCompletedFlag;
		private double volumeCollectedMl;
		private String RegistrationStatus;
		private String immediateReactionNotes;
		private String resultStatus;
		private String bloodGroup;
		public int getScreeningID() {
			return screeningID;
		}
		public void setScreeningID(int screeningID) {
			this.screeningID = screeningID;
		}
		public String getRegistrationID() {
			return registrationID;
		}
		public void setRegistrationID(String registrationID) {
			this.registrationID = registrationID;
		}
		public Timestamp getScreeningTimestamp() {
			return screeningTimestamp;
		}
		public void setScreeningTimestamp(java.sql.Timestamp screeningTimestamp) {
		    this.screeningTimestamp = screeningTimestamp;
		}

		public double getWeightKg() {
			return weightKg;
		}
		public void setWeightKg(double weightKg) {
			this.weightKg = weightKg;
		}
		public double getHemoglobin() {
			return hemoglobin;
		}
		public void setHemoglobin(double hemoglobin) {
			this.hemoglobin = hemoglobin;
		}
		public int getSystolicBP() {
			return systolicBP;
		}
		public void setSystolicBP(int systolicBP) {
			this.systolicBP = systolicBP;
		}
		public int getDiastolicBP() {
			return diastolicBP;
		}
		public void setDiastolicBP(int diastolicBP) {
			this.diastolicBP = diastolicBP;
		}
		public int getPulseRate() {
			return pulseRate;
		}
		public void setPulseRate(int pulseRate) {
			this.pulseRate = pulseRate;
		}
		public String getScreeningDecision() {
			return screeningDecision;
		}
		public void setScreeningDecision(String screeningDecision) {
			this.screeningDecision = screeningDecision;
		}
		public String getDeferralReason() {
			return deferralReason;
		}
		public void setDeferralReason(String deferralReason) {
			this.deferralReason = deferralReason;
		}
		public String getDonationCompletedFlag() {
			return donationCompletedFlag;
		}
		public void setDonationCompletedFlag(String donationCompletedFlag) {
			this.donationCompletedFlag = donationCompletedFlag;
		}
		public double getVolumeCollectedMl() {
			return volumeCollectedMl;
		}
		public void setVolumeCollectedMl(double volumeCollectedMl) {
			this.volumeCollectedMl = volumeCollectedMl;
		}
		public String getRegistrationStatus() {
			return RegistrationStatus;
		}
		public void setRegistrationStatus(String registrationStatus) {
			RegistrationStatus = registrationStatus;
		}
		public String getImmediateReactionNotes() {
			return immediateReactionNotes;
		}
		public void setImmediateReactionNotes(String immediateReactionNotes) {
			this.immediateReactionNotes = immediateReactionNotes;
		}
		public String getResultStatus() {
			return resultStatus;
		}
		public void setResultStatus(String resultStatus) {
			this.resultStatus = resultStatus;
		}
		public String getBloodGroup() {
		    return bloodGroup;
		}

		public void setBloodGroup(String bloodGroup) {
		    this.bloodGroup = bloodGroup;
		}
		public void setScreeningID(Timestamp screeningTime) {
			// TODO Auto-generated method stub
			
		}
		
	}