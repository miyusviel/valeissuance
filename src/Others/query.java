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

/**
 *
 * @author Administrator
 */
public class query {
    
    static Statement stmt;
    
    public static void update_trans(int id, String apprvby, Date apprvdt, int volg, String remarks) {
        Connection conn = DBConn.getConnection();
        String createString;
        createString = "UPDATE Transaction SET approvedby='" + apprvby + "', approveddate='" + apprvdt + "', volgiven='" + volg +"', remarks='" + remarks + "' "
                + "WHERE trans_id=" + id;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createString);
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
}
