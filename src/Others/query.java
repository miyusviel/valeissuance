/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Others;

import Connection.DBConn;

import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class query {

    static Statement stmt;

    public static Boolean check_duplicate_vale(int driver, java.sql.Date traveldate) { //check duplicate issuances of vale
        //JOptionPane.showMessageDialog(null, traveldate);

        String query = "select count(*) from transaction where emp_id = ? and createddate = ? ";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, driver);
            statement.setDate(2, traveldate);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                //return true; // Duplicate found
                //int cnt = resultSet.getInt(1);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        JOptionPane.showMessageDialog(null, query);
        return false;
    }

    public void new_trans(String code, String type, int crtby, Date crtdt, int vol, String rem, int sid, int aid, int eid, int vid, int fid, int supid, int uid, int tid, int apprvby, String transdt, double price, double amt) {
        String query = "INSERT INTO Transaction (vale_no, type, createdby, createddate, volrequested, remarks, status_id, acct_id, emp_id, vehicle_id, fuel_id, supplier_id, user_id, town_id, approvedby, trans_date,liter_price,trans_amt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, code);
            statement.setString(2, type);
            statement.setInt(3, crtby);
            statement.setDate(4, (java.sql.Date) crtdt);
            statement.setInt(5, vol);
            statement.setString(6, rem);
            statement.setInt(7, sid);
            statement.setInt(8, aid);
            statement.setInt(9, eid);
            statement.setInt(10, vid);
            statement.setInt(11, fid);
            statement.setInt(12, supid);
            statement.setInt(13, uid);
            statement.setInt(14, tid);
            statement.setInt(15, apprvby);
            //statement.setDate(16, (java.sql.Date) transdt);
            statement.setString(16, transdt);
            statement.setDouble(17, price);
            statement.setDouble(18, amt);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vale Created!", "User alert", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create vale.", "User alert", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "User alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void new_destination(String town, Date transdt, int uid) {
        String query = "INSERT INTO Destination (townname, trans_date, user_id) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, town);
            statement.setDate(2, (java.sql.Date) transdt);
            statement.setInt(3, uid);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "New Destination Created!", "User alert", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create destination.", "User alert", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "User alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void new_fuel(String type, String desc, Date transdt, int uid) {
        String query = "INSERT INTO Fuel (type, description, trans_date, user_id) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, type);
            statement.setString(2, desc);
            statement.setDate(3, (java.sql.Date) transdt);
            statement.setInt(4, uid);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "New Oil Entry Created!", "User alert", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create oil entry.", "User alert", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "User alert", JOptionPane.ERROR_MESSAGE);
        }
    }
    /*
    public void new_vehicle(int dept_id, int fuel_id, String type, String desc, String make, String model, String version, int year, Date reg, String plate, String engine, String chassis, float cost, String rem, Date transdt, int uid) {
        String query = "INSERT INTO Vehicle (dept_id, fuel_id, type, description, make, model, version, yearmade, regdate, plate_no, engine_no, chassis_no, cost, remarks, trans_date, user_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //Added dept_id for JBoxCombo- Aubrey Heramis

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, dept_id); //Added dept_id for Department JBoxCombo option- Aubrey Heramis
            statement.setInt(2, fuel_id);
            statement.setString(3, type);
            statement.setString(4, desc);
            statement.setString(5, make);
            statement.setString(6, model);
            statement.setString(7, version);
            statement.setInt(8, year);
            statement.setDate(9, (java.sql.Date) reg);
            statement.setString(10, plate);
            statement.setString(11, engine);
            statement.setString(12, chassis);
            statement.setFloat(13, cost);
            statement.setString(14, rem);
            statement.setDate(15, (java.sql.Date) transdt);
            statement.setInt(16, uid);  */

    
    public void new_vehicle(int dept_id, int fuel_id, String type, String desc, String make, String model, String version, int year, Date reg, String plate, String engine, String chassis, float cost, String rem, Date transdt, int uid) {
        try (Connection conn = DBConn.getConnection(); PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO vehicle (dept_id, fuel_id, type, description, make, model, version, yearmade, regdate, " +
                "plate_no, engine_no, chassis_no, cost, remarks, trans_date, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            
            pstmt.setInt(1, dept_id);
            pstmt.setInt(2, fuel_id);
            pstmt.setString(3, type);
            pstmt.setString(4, desc);
            pstmt.setString(5, make);
            pstmt.setString(6, model);
            pstmt.setString(7, version);
            pstmt.setInt(8, year);
            pstmt.setDate(9, (java.sql.Date) reg);
            pstmt.setString(10, plate);
            pstmt.setString(11, engine);
            pstmt.setString(12, chassis);
            pstmt.setFloat(13, cost);
            pstmt.setString(14, rem);
            pstmt.setDate(15, (java.sql.Date) transdt);
            pstmt.setInt(16, uid);
            
            //int rowsAffected = statement.executeUpdate();
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vehicle Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create vehicle.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void update_trans(int id, Date apprvdt, int vol, String rem, Date transdt, double price, double amt, int uid, int sid, int supid, String ornumber) {
        String query = "UPDATE Transaction SET approveddate = ?, volgiven = ?, remarks = ?, trans_date = ?,user_id = ?,liter_price = ?, trans_amt = ?,  status_id = ?, supplier_id = ?,ornumber = ?  WHERE trans_id = ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setDate(1, (java.sql.Date) apprvdt);
            statement.setInt(2, vol);
            statement.setString(3, rem);
            statement.setDate(4, (java.sql.Date) transdt);
            statement.setInt(5, uid);
            statement.setDouble(6, price); //new param by R. Repe
            statement.setDouble(7, amt);  //new param by R. Repe
            statement.setInt(8, sid);
            statement.setInt(9, supid); //new param by R. Repe
            statement.setString(10, ornumber); //new param by R. Repe
            statement.setInt(11, id);

            System.out.println(query);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vale Updated", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update vale", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update_destination(int id, String town, Date transdt, int uid) {
        String query = "UPDATE Destination SET townname = ?, trans_date = ?, user_id = ? WHERE town_id = ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, town);
            statement.setDate(2, (java.sql.Date) transdt);
            statement.setInt(3, uid);
            statement.setInt(4, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Destination Updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update destination!", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update_fuel(int id, String type, String desc, Date transdt, int uid) {
        String query = "UPDATE Fuel SET type = ?, description = ?, trans_date = ?, user_id = ? WHERE fuel_id = ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, type);
            statement.setString(2, desc);
            statement.setDate(3, (java.sql.Date) transdt);
            statement.setInt(4, uid);
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Oil Entry Updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update oil entry!", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Added dept_id for Department JBoxCombo option- Aubrey Heramis
    public static void update_vehicle(int id, int dept_id, int fuel_id, String type, String desc, String make, String model, String version, int year, Date reg, String plate, String engine, String chassis, float cost, String rem, Date transdt, int uid) {
        /*String query = "UPDATE Vehicle SET dept_id = ?, fuel_id = ?, type = ?, description = ?, make = ?, model = ?, version = ?, yearmade = ?, regdate = ?, plate_no = ?, engine_no = ?, chassis_no = ?, cost = ?, remarks = ?, trans_date = ?, user_id = ? WHERE vehicle_id = ?";*/
        String query = "UPDATE Vehicle SET dept_id = ?, fuel_id = ?, type = ?, description = ?, make = ?, model = ?, " +
                "version = ?, yearmade = ?, regdate = ?, plate_no = ?, engine_no = ?, chassis_no = ?, " +
                "cost = ?, remarks = ?, trans_date = ?, user_id = ? WHERE vehicle_id = ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, dept_id);
            statement.setInt(2, fuel_id);
            statement.setString(3, type);
            statement.setString(4, desc);
            statement.setString(5, make);
            statement.setString(6, model);
            statement.setString(7, version);
            statement.setInt(8, year);
            statement.setDate(9, (java.sql.Date) reg);
            statement.setString(10, plate);
            statement.setString(11, engine);
            statement.setString(12, chassis);
            statement.setFloat(13, cost);
            statement.setString(14, rem);
            statement.setDate(15, (java.sql.Date) transdt);
            statement.setInt(16, uid);
            statement.setInt(17, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vehicle Information Updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update vehicle information!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void cancel_trans(int id, int sid, String rem, Date transdt, int uid) {
        String query = "UPDATE Transaction SET status_id= ?, remarks = ?, trans_date = ?, user_id = ? WHERE trans_id= ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, sid);
            statement.setString(2, rem);
            statement.setDate(3, (java.sql.Date) transdt);
            statement.setInt(4, uid);
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vale Cancelled", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to cancel vale!", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void delete_destination(int id, Date transdt, int uid) {
        String query = "UPDATE Destination SET is_active = 0, trans_date = ?, user_id = ? WHERE town_id = ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setDate(1, (java.sql.Date) transdt);
            statement.setInt(2, uid);
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Destination Deleted!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete destination!", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void delete_fuel(int id, Date transdt, int uid) {
        String query = "UPDATE Fuel SET is_active = 0, trans_date = ?, user_id = ? WHERE fuel_id = ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setDate(1, (java.sql.Date) transdt);
            statement.setInt(2, uid);
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Fuel Entry Deleted!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete fuel entry!", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void delete_vehicle(int id, Date transdt, int uid) {
        String query = "UPDATE Vehicle SET is_active = 0, trans_date = ?, user_id = ? WHERE vehicle_id = ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setDate(1, (java.sql.Date) transdt);
            statement.setInt(2, uid);
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vehicle Information Deleted!", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete vehicle entry!", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void reopen_trans(int id, int sid) {
        String query = "UPDATE Transaction SET status_id= ? WHERE trans_id= ?";

        try (Connection conn = DBConn.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, sid);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Vale Reopened", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to reopen vale", "Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

}
