package com.bloodcamp.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.bloodcamp.bean.ScreeningResult;
import com.bloodcamp.util.DBUtil;
public class ScreeningResultDAO {
	    public int generateScreeningID() {

	        int screeningID = 0;

	        try {
	            Connection con = DBUtil.getDBConnection();

	            PreparedStatement ps =
	                con.prepareStatement("SELECT NVL(MAX(SCREENING_ID),0)+1 FROM SCREENING_RESULT_TBL");

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                screeningID = rs.getInt(1);
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return screeningID;
	    }

	    
	    public boolean insertScreeningResult(ScreeningResult result) {

	        boolean status = false;

	        try {
	            Connection con = DBUtil.getDBConnection();

	            PreparedStatement ps = con.prepareStatement(
	                "INSERT INTO SCREENING_RESULT_TBL " +
	                "(SCREENING_ID, REGISTRATION_ID, SCREENING_TIMESTAMP, WEIGHT_KG, " +
	                "HEMOGLOBIN_G_DL, SYSTOLIC_BP, DIASTOLIC_BP, PULSE_RATE, " +
	                "SCREENING_DECISION, DEFERRAL_REASON, DONATION_COMPLETED_FLAG, " +
	                "VOLUME_COLLECTED_ML, IMMEDIATE_REACTION_NOTES, RESULT_STATUS) " +
	                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
	            );

	            ps.setInt(1, result.getScreeningID());
	            ps.setString(2, result.getRegistrationID());
	            ps.setTimestamp(3, result.getScreeningTimestamp());
	            ps.setDouble(4, result.getWeightKg());
	            ps.setDouble(5, result.getHemoglobin());
	            ps.setInt(6, result.getSystolicBP());
	            ps.setInt(7, result.getDiastolicBP());
	            ps.setInt(8, result.getPulseRate());
	            ps.setString(9, result.getScreeningDecision());
	            ps.setString(10, result.getDeferralReason());
	            ps.setString(11, result.getDonationCompletedFlag());
	            ps.setDouble(12, result.getVolumeCollectedMl());
	            ps.setString(13, result.getImmediateReactionNotes());
	            ps.setString(14, result.getResultStatus());

	            int rows = ps.executeUpdate();
	            if (rows > 0) {
	                status = true;
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return status;
	    }

	   
	    public ScreeningResult findScreeningByRegistration(String registrationID) {

	        ScreeningResult result = null;

	        try {
	            Connection con = DBUtil.getDBConnection();

	            PreparedStatement ps =
	                con.prepareStatement("SELECT * FROM SCREENING_RESULT_TBL WHERE REGISTRATION_ID=?");

	            ps.setString(1, registrationID);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                result = new ScreeningResult();
	                result.setScreeningID(rs.getInt("SCREENING_ID"));
	                result.setRegistrationID(rs.getString("REGISTRATION_ID"));
	                result.setScreeningTimestamp(rs.getTimestamp("SCREENING_TIMESTAMP"));
	                result.setWeightKg(rs.getDouble("WEIGHT_KG"));
	                result.setHemoglobin(rs.getDouble("HEMOGLOBIN_G_DL"));
	                result.setSystolicBP(rs.getInt("SYSTOLIC_BP"));
	                result.setDiastolicBP(rs.getInt("DIASTOLIC_BP"));
	                result.setPulseRate(rs.getInt("PULSE_RATE"));
	                result.setScreeningDecision(rs.getString("SCREENING_DECISION"));
	                result.setDeferralReason(rs.getString("DEFERRAL_REASON"));
	                result.setDonationCompletedFlag(rs.getString("DONATION_COMPLETED_FLAG"));
	                result.setVolumeCollectedMl(rs.getDouble("VOLUME_COLLECTED_ML"));
	                result.setImmediateReactionNotes(rs.getString("IMMEDIATE_REACTION_NOTES"));
	                result.setResultStatus(rs.getString("RESULT_STATUS"));
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return result;
	    }
	    public boolean updateScreeningResult(ScreeningResult result) {

	        boolean status = false;

	        try {
	            Connection con = DBUtil.getDBConnection();

	            PreparedStatement ps = con.prepareStatement(
	                "UPDATE SCREENING_RESULT_TBL SET " +
	                "WEIGHT_KG=?, HEMOGLOBIN_G_DL=?, SYSTOLIC_BP=?, DIASTOLIC_BP=?, " +
	                "PULSE_RATE=?, SCREENING_DECISION=?, DEFERRAL_REASON=?, " +
	                "DONATION_COMPLETED_FLAG=?, VOLUME_COLLECTED_ML=?, " +
	                "IMMEDIATE_REACTION_NOTES=?, RESULT_STATUS=? " +
	                "WHERE SCREENING_ID=?"
	            );

	            ps.setDouble(1, result.getWeightKg());
	            ps.setDouble(2, result.getHemoglobin());
	            ps.setInt(3, result.getSystolicBP());
	            ps.setInt(4, result.getDiastolicBP());
	            ps.setInt(5, result.getPulseRate());
	            ps.setString(6, result.getScreeningDecision());
	            ps.setString(7, result.getDeferralReason());
	            ps.setString(8, result.getDonationCompletedFlag());
	            ps.setDouble(9, result.getVolumeCollectedMl());
	            ps.setString(10, result.getImmediateReactionNotes());
	            ps.setString(11, result.getResultStatus());
	            ps.setInt(12, result.getScreeningID());

	            int rows = ps.executeUpdate();
	            if (rows > 0) {
	                status = true;
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return status;
	    }
	    public Map<String, Integer> computeCampSummaryByBloodGroup(Date campDate) {

	        Map<String, Integer> summary = new HashMap<>();

	        try {
	            Connection con = DBUtil.getDBConnection();

	            PreparedStatement ps = con.prepareStatement(
	                "SELECT D.BLOOD_GROUP, COUNT(*) " +
	                "FROM DONOR_REG_TBL D JOIN SCREENING_RESULT_TBL S " +
	                "ON D.REGISTRATION_ID = S.REGISTRATION_ID " +
	                "WHERE D.CAMP_DATE = ? " +
	                "GROUP BY D.BLOOD_GROUP"
	            );

	            ps.setDate(1, campDate);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                summary.put(rs.getString(1), rs.getInt(2));
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return summary;
	    }

		public ScreeningResult findScreeningById(int screeningID) {

			    ScreeningResult result = null;

			    String sql =
			        "SELECT * FROM SCREENING_RESULT_TBL WHERE SCREENING_ID = ?";

			    try (Connection con = DBUtil.getDBConnection();
			         PreparedStatement ps = con.prepareStatement(sql)) {

			        ps.setInt(1, screeningID);
			        ResultSet rs = ps.executeQuery();

			        if (rs.next()) {
			            result = new ScreeningResult();
			            result.setScreeningID(rs.getInt("SCREENING_ID"));
			            result.setRegistrationID(rs.getString("REGISTRATION_ID"));
			            result.setBloodGroup(rs.getString("BLOOD_GROUP"));
			            result.setResultStatus(rs.getString("RESULT_STATUS"));
			        }

			    } catch (SQLException e) {
			        e.printStackTrace();
			    }

			    return result;
			}

		}


