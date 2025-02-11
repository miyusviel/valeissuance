/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Reports;

import Connection.DBConn;
import Others.image_attribution;
import Others.main_menu;
import Others.reports;
import java.awt.Container;
import java.awt.Window;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Administrator
 */
public class volume_summary extends javax.swing.JDialog {
    
    public static main_menu frmparent;
    
    static DefaultTableCellRenderer cellAlignCenterRenderer = new DefaultTableCellRenderer();
    static DefaultTableCellRenderer cellAlignRightRenderer = new DefaultTableCellRenderer();
    static DefaultTableModel Tablemodel;
    static Statement stmt;
    
    String rptType;
    
    private DefaultComboBoxModel<String> model;
    
    /**
     * Creates new form Volume
     */
    
    public volume_summary(main_menu parent, boolean modal) {
        this.frmparent = parent;
        this.setModal(modal);
        initComponents();
        populateTableByDriver();
        setLocationRelativeTo(this);
        
        try {
            imageCredits();
        } catch (IOException ex) {
            Logger.getLogger(volume_summary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jScrollPane1.setViewportView(tblMain);
        
//        cmbFilter.addItem("Driver");
//        cmbFilter.addItem("Vehicle");
//        cmbFilter.addItem("Supplier");
//        cmbFilter.addItem("Account");
//        cmbFilter.setSelectedItem("Driver");
        
        model = new DefaultComboBoxModel<>();
        
        model.addElement("Driver");
        model.addElement("Vehicle");
        model.addElement("Supplier");
        model.addElement("Account");
        
        cmbFilter.setModel(model);
//        cmbFilter.setSelectedItem("Driver");

    }
    
    void populateTableByDriver() {
        rptType = "Driver";
        
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "SELECT t.emp_id, CONCAT(e.first_name, ' ', e.last_name) AS driver, COALESCE( SUM(t.volrequested), 0) AS sumreq, COALESCE( SUM(t.volgiven), 0) AS sumgiven, COALESCE( COUNT(t.trans_id), 0) AS counttrans "+
                    "FROM transaction t "+
                    "INNER JOIN vehicle v ON v.vehicle_id = t.vehicle_id " +
                    "INNER JOIN fuel f ON f.fuel_id = t.fuel_id " +
                    "INNER JOIN accounts a ON a.acct_id = t.acct_id " +
                    "INNER JOIN employee e ON e.emp_id = t.emp_id " +
                    "INNER JOIN suppliers s ON s.supplier_id = t.supplier_id " +
                    "INNER JOIN destination d ON d.town_id = t.town_id " +
                    "INNER JOIN user u ON u.user_id = t.user_id " +
                    "INNER JOIN document_status ds ON ds.doc_status_id = t.status_id "+
                    "WHERE first_name LIKE ? OR last_name LIKE ? "+
                    "GROUP BY t.emp_id, driver")) {

            pstmt.setString(1, "%" + txtSearch.getText() + "%");
            pstmt.setString(2, "%" + txtSearch.getText() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                Tablemodel = (DefaultTableModel) tblMain.getModel();
                Tablemodel.setRowCount(0);
                
                cellAlignCenterRenderer.setHorizontalAlignment(0);
                cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

                tblMain.setRowHeight(28);            

                // Hide the empId column
                TableColumn hiddenColumn1 = tblMain.getColumnModel().getColumn(0);
                TableColumn colId = tblMain.getColumnModel().getColumn(1);
                TableColumn colDriver = tblMain.getColumnModel().getColumn(2);
                
                colId.setPreferredWidth(1);
                colDriver.setPreferredWidth(100);
                
                hiddenColumn1.setMinWidth(0);
                hiddenColumn1.setMaxWidth(0);
                hiddenColumn1.setPreferredWidth(0);

                int cnt = 0;

                while (rs.next()) {
                    int empId = rs.getInt("emp_id"); // TransID reference
                    Object[] rowData = {
                            empId,
                            cnt + 1,
                            rs.getString("driver"),
                            rs.getInt("sumreq"),
                            rs.getInt("sumgiven"),
                            rs.getInt("counttrans"),
                    };

                    Tablemodel.addRow(rowData);
                    cnt++;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    void populateTableByVehicle() {
        rptType = "Vehicle";
        
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "SELECT t.vehicle_id, v.description, COALESCE( SUM(t.volrequested), 0) AS sumreq, COALESCE( SUM(t.volgiven), 0) AS sumgiven, COALESCE( COUNT(t.trans_id), 0) AS counttrans " +
                    "FROM transaction t " +
                    "INNER JOIN vehicle v ON v.vehicle_id = t.vehicle_id " +
                    "INNER JOIN fuel f ON f.fuel_id = t.fuel_id " +
                    "INNER JOIN accounts a ON a.acct_id = t.acct_id " +
                    "INNER JOIN employee e ON e.emp_id = t.emp_id " +
                    "INNER JOIN suppliers s ON s.supplier_id = t.supplier_id " +
                    "INNER JOIN destination d ON d.town_id = t.town_id " +
                    "INNER JOIN user u ON u.user_id = t.user_id " +
                    "INNER JOIN document_status ds ON ds.doc_status_id = t.status_id " +
                    "WHERE v.description LIKE ? "+
                    "GROUP BY t.vehicle_id, v.description")) {
        

            pstmt.setString(1, "%" + txtSearch.getText() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                Tablemodel = (DefaultTableModel) tblMain.getModel();
                Tablemodel.setRowCount(0);
                
                cellAlignCenterRenderer.setHorizontalAlignment(0);
                cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

                tblMain.setRowHeight(28);            

                // Hide the empId column
                TableColumn hiddenColumn1 = tblMain.getColumnModel().getColumn(0);
                TableColumn colId = tblMain.getColumnModel().getColumn(1);
                TableColumn colVehicle = tblMain.getColumnModel().getColumn(2);
                
                colId.setPreferredWidth(1);
                colVehicle.setPreferredWidth(100);
                
                hiddenColumn1.setMinWidth(0);
                hiddenColumn1.setMaxWidth(0);
                hiddenColumn1.setPreferredWidth(0);

                int cnt = 0;

                while (rs.next()) {
                    int empId = rs.getInt("vehicle_id"); // TransID reference
                    Object[] rowData = {
                            empId,
                            cnt + 1,
                            rs.getString("v.description"),
                            rs.getInt("sumreq"),
                            rs.getInt("sumgiven"),
                            rs.getInt("counttrans"),
                    };

                    Tablemodel.addRow(rowData);
                    cnt++;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    void populateTableBySupplier() {
        rptType = "Supplier";
        
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "SELECT t.supplier_id, s.name, COALESCE( SUM(t.volrequested), 0) AS sumreq, COALESCE( SUM(t.volgiven), 0) AS sumgiven, COALESCE( COUNT(t.trans_id), 0) AS counttrans " +
                    "FROM transaction t " +
                    "INNER JOIN vehicle v ON v.vehicle_id = t.vehicle_id " +
                    "INNER JOIN fuel f ON f.fuel_id = t.fuel_id " +
                    "INNER JOIN accounts a ON a.acct_id = t.acct_id " +
                    "INNER JOIN employee e ON e.emp_id = t.emp_id " +
                    "INNER JOIN suppliers s ON s.supplier_id = t.supplier_id " +
                    "INNER JOIN destination d ON d.town_id = t.town_id " +
                    "INNER JOIN user u ON u.user_id = t.user_id " +
                    "INNER JOIN document_status ds ON ds.doc_status_id = t.status_id " +
                    "WHERE s.name LIKE ? OR v.description LIKE ? OR f.description LIKE ? "+
                    "GROUP BY t.supplier_id, s.name ")) {

            pstmt.setString(1, "%" + txtSearch.getText() + "%");
            pstmt.setString(2, "%" + txtSearch.getText() + "%");
            pstmt.setString(3, "%" + txtSearch.getText() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                Tablemodel = (DefaultTableModel) tblMain.getModel();
                Tablemodel.setRowCount(0);
                
                cellAlignCenterRenderer.setHorizontalAlignment(0);
                cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

                tblMain.setRowHeight(28);            

                // Hide the empId column
                TableColumn hiddenColumn1 = tblMain.getColumnModel().getColumn(0);
                TableColumn colId = tblMain.getColumnModel().getColumn(1);
                TableColumn colVehicle = tblMain.getColumnModel().getColumn(2);
                
                colId.setPreferredWidth(1);
                colVehicle.setPreferredWidth(100);
                
                hiddenColumn1.setMinWidth(0);
                hiddenColumn1.setMaxWidth(0);
                hiddenColumn1.setPreferredWidth(0);
                
                int cnt = 0;

                while (rs.next()) {
                    int empId = rs.getInt("supplier_ID"); // TransID reference
                    Object[] rowData = {
                            empId,
                            cnt + 1,
                            rs.getString("name"),
                            rs.getInt("sumreq"),
                            rs.getInt("sumgiven"),
                            rs.getInt("counttrans"),
                    };

                    Tablemodel.addRow(rowData);
                    cnt++;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    void populateTableByAccount() {
        rptType = "Account";
        
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "Select t.acct_id, a.title, COALESCE( SUM(t.volrequested), 0) AS sumreq, COALESCE( SUM(t.volgiven), 0) AS sumgiven, COALESCE( COUNT(t.trans_id), 0) AS counttrans " +
                    "FROM transaction t " +
                    "INNER JOIN vehicle v ON v.vehicle_id = t.vehicle_id " +
                    "INNER JOIN fuel f ON f.fuel_id = t.fuel_id " +
                    "INNER JOIN accounts a ON a.acct_id = t.acct_id " +
                    "INNER JOIN employee e ON e.emp_id = t.emp_id " +
                    "INNER JOIN suppliers s ON s.supplier_id = t.supplier_id " +
                    "INNER JOIN destination d ON d.town_id = t.town_id " +
                    "INNER JOIN user u ON u.user_id = t.user_id " +
                    "INNER JOIN document_status ds ON ds.doc_status_id = t.status_id " +
                    "WHERE a.title LIKE ? "+
                    "GROUP BY t.acct_id, a.title ")) {

            pstmt.setString(1, "%" + txtSearch.getText() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                Tablemodel = (DefaultTableModel) tblMain.getModel();
                Tablemodel.setRowCount(0);
                
                cellAlignCenterRenderer.setHorizontalAlignment(0);
                cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

                tblMain.setRowHeight(28);            

                // Hide the empId column
                TableColumn hiddenColumn1 = tblMain.getColumnModel().getColumn(0);
                TableColumn colId = tblMain.getColumnModel().getColumn(1);
                TableColumn colVehicle = tblMain.getColumnModel().getColumn(2);
                
                colId.setPreferredWidth(1);
                colVehicle.setPreferredWidth(100);
                
                hiddenColumn1.setMinWidth(0);
                hiddenColumn1.setMaxWidth(0);
                hiddenColumn1.setPreferredWidth(0);
                
                int cnt = 0;

                while (rs.next()) {
                    int empId = rs.getInt("acct_id"); // TransID reference
                    Object[] rowData = {
                            empId,
                            cnt + 1,
                            rs.getString("title"),
                            rs.getInt("sumreq"),
                            rs.getInt("sumgiven"),
                            rs.getInt("counttrans"),
                    };

                    Tablemodel.addRow(rowData);
                    cnt++;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new org.jdesktop.swingx.JXSearchField();
        jLabel4 = new javax.swing.JLabel();
        cmdPrint = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable();
        cmbFilter = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jInternalFrame1.setVisible(true);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel4.setText("Filter:");

        cmdPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print_32.png"))); // NOI18N
        cmdPrint.setText("Print");
        cmdPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPrintActionPerformed(evt);
            }
        });

        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Emp_ID", "ID", "Driver", "Total Volume Requested", "Total Volume Issued", "Number of Requests"
            }
        ));
        jScrollPane1.setViewportView(tblMain);

        cmbFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(cmbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdPrint))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );

        try {
            jInternalFrame1.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
        if ("".equals(cmbFilter.getSelectedItem().toString())){
            cmbFilter.setSelectedIndex(0);
        }
        cmbFilter.actionPerformed(evt);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cmdPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPrintActionPerformed
        // TODO add your handling code here:
        reports.rptVolumeByVehicle(rptType);
//        reports.rptVolumeByVehicle(rptType);
        
        // Bring the JasperViewer's preview window to the front
//        SwingUtilities.invokeLater(() -> {
//            Window[] windows = Window.getWindows();
//            for (Window window : windows) {
//                if (window instanceof JasperViewer) {
//                    window.toFront();
//                    ((JasperViewer) window).setAlwaysOnTop(true); // Set as always on top
//                    break; // Assuming there's only one JasperViewer window
//                }
//            }
//        });
    }//GEN-LAST:event_cmdPrintActionPerformed

    private void cmbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterActionPerformed
        // TODO add your handling code here:
        System.out.println("cmbFilter ActionListener triggered");
        
        if (null != cmbFilter.getSelectedItem().toString()) {
            switch (cmbFilter.getSelectedItem().toString()) {
                case "Driver" -> {
                    populateTableByDriver();
                    TableColumnModel columnModel = tblMain.getColumnModel();
                    columnModel.getColumn(2).setHeaderValue("Driver");
                    columnModel.getColumn(4).setHeaderValue("Total Volume Issued");
                    break;
                }
                case "Vehicle" -> {
                    populateTableByVehicle();
                    TableColumnModel columnModel = tblMain.getColumnModel();
                    columnModel.getColumn(2).setHeaderValue("Vehicle");
                    columnModel.getColumn(4).setHeaderValue("Total Volume Used");
                    break;
                }
                case "Supplier" -> {
                    populateTableBySupplier();
                    TableColumnModel columnModel = tblMain.getColumnModel();
                    columnModel.getColumn(2).setHeaderValue("Supplier");
                    columnModel.getColumn(4).setHeaderValue("Total Volume Bought");
                    break;
                }
                case "Account" -> {
                    populateTableByAccount();
                    TableColumnModel columnModel = tblMain.getColumnModel();
                    columnModel.getColumn(2).setHeaderValue("Account");
                    columnModel.getColumn(4).setHeaderValue("Total Volume Issued");
                    break;
                }
            }

            // Update the table UI to reflect the changes
            tblMain.getTableHeader().repaint();
            tblMain.repaint();
        }
    }//GEN-LAST:event_cmbFilterActionPerformed

    public void imageCredits() throws FileNotFoundException, IOException {
        image_attribution image1 = new image_attribution("print_32", "https://www.freepik.com/icon/printer_3003232#position=39&page=1&term=print&fromView=search", new FileInputStream("print_32.png"));
        image1.attributes.put("author", "Freepik");
        System.out.println(image1.getAttribution());
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(frmUtilBapaSalesrecap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(volume_summary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(volume_summary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(volume_summary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbFilter;
    private javax.swing.JButton cmdPrint;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMain;
    private org.jdesktop.swingx.JXSearchField txtSearch;
    // End of variables declaration//GEN-END:variables
}
