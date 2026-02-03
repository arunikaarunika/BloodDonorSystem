package com.bloodcamp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bloodcamp.bean.DonorRegistration;
import com.bloodcamp.util.DBUtil;

public class DonorRegistrationDAO {

	    public DonorRegistration findRegistration(String regId) {
	        DonorRegistration d = null;

	        try {
	            Connection con = DBUtil.getDBConnection();
	            PreparedStatement ps =
	                con.prepareStatement("SELECT * FROM DONOR_REG_TBL WHERE REGISTRATION_ID=?");
	            ps.setString(1, regId);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                d = new DonorRegistration();
	                d.setRegistrationID(rs.getString("REGISTRATION_ID"));
	                d.setRegistrationStatus(rs.getString("REGISTRATION_STATUS"));
	            }
	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return d;
	    }

	    public boolean insertRegistration(DonorRegistration d) {
	        try {
	            Connection con = DBUtil.getDBConnection();
	            PreparedStatement ps = con.prepareStatement(
	                "INSERT INTO DONOR_REG_TBL VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

	            ps.setString(1, d.getRegistrationID());
	            ps.setDate(2, d.getCampDate());
	            ps.setString(3, d.getDonarName());
	            ps.setInt(4, d.getAgeYears());
	            ps.setString(5, d.getGender());
	            ps.setString(6, d.getContactMobile());
	            ps.setString(7, d.getEmail());
	            ps.setString(8, d.getBloodGroup());
	            ps.setString(9, d.getSelfDeclRecentIllness());
	            ps.setString(10, d.getSelfDeclMedication());
	            ps.setString(11, d.getPreferredArrivalSlot());
	            ps.setString(12, d.getRegistrationStatus());

	            int rows = ps.executeUpdate();
	            con.close();
	            return rows > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    public boolean updateRegistrationStatus(String regId, String status) {
	        try {
	            Connection con = DBUtil.getDBConnection();
	            PreparedStatement ps =
	                con.prepareStatement("UPDATE DONOR_REG_TBL SET REGISTRATION_STATUS=? WHERE REGISTRATION_ID=?");
	            ps.setString(1, status);
	            ps.setString(2, regId);

	            int rows = ps.executeUpdate();
	            con.close();
	            return rows > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

		public List<DonorRegistration> viewRegistrationsByCampDateAndStatus(Date campDate, String status,
				String bloodGroup) {
			return null;
		}

		public boolean updateRegistrationStatusAndReason(String registrationID, String string, String reason) {
			return false;
		}

	}
