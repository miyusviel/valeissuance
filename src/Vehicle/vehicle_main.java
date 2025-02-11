/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vehicle;

import Connection.DBConn;
import Others.functions;
import Others.image_attribution;
import Others.query;
import Vale.vale_main;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter; // Added to enable search functionality - Aubrey Heramis
import javax.swing.table.TableModel;
import javax.swing.RowFilter; // Changed from javax.swing.table.RowFilter to javax.swing.RowFilter

/**
 *
 * @author Administrator
 */
public class vehicle_main extends javax.swing.JFrame {

    /**
     * Creates new form destination_main
     */
    public static vehicle_dialog nextTransfrmparent;

    static DefaultTableCellRenderer cellAlignCenterRenderer = new DefaultTableCellRenderer();
    static DefaultTableCellRenderer cellAlignRightRenderer = new DefaultTableCellRenderer();
    static DefaultTableModel Tablemodel;
    static Statement stmt;

    public vehicle_main() {
        initComponents();
        cmdDelete.setEnabled(false);
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        populateTable();

        setLocationRelativeTo(this);

        jScrollPane1.setViewportView(tblMain);
        tblMain.setRowSelectionAllowed(true);
        tblMain.setColumnSelectionAllowed(false);
        tblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private static void hideColumns(JTable table, int[] columnIndices) {
        TableColumnModel columnModel = table.getColumnModel();

        for (int i : columnIndices) {
            TableColumn column = columnModel.getColumn(i);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setPreferredWidth(0);
        }
    }

    void populateTable() {
        try (Connection conn = DBConn.getConnection(); PreparedStatement pstmt = conn.prepareStatement(
        /* "SELECT vehicle_id, CONCAT(f.type, ' - ', f.description) AS fuel_entry, v.type, v.description, make, model, version, yearmade, regdate, plate_no, engine_no, chassis_no, cost, v.remarks "
                + "FROM vehicle v "
                + "INNER JOIN fuel f ON f.fuel_id = v.fuel_id "
                + "WHERE (f.description LIKE ? OR v.type LIKE ? OR v.description LIKE ? OR v.make LIKE ? OR v.model LIKE ? OR v.version LIKE ? OR v.yearmade LIKE ? OR v.regdate LIKE ? OR v.plate_no LIKE ? OR v.engine_no LIKE ? OR v.chassis_no LIKE ?) AND v.is_active = 1 "
                + "ORDER BY v.description")) { */
                
               /*"SELECT v.vehicle_id, d.dept_name, CONCAT(f.type, ' - ', f.description) AS fuel_entry, " + // Moved dept_name before fuel_entry
                "v.type, v.description, make, model, version, yearmade, regdate, plate_no, " +
                "engine_no, chassis_no, cost, v.remarks " +
                "FROM vehicle v " +
                "INNER JOIN fuel f ON f.fuel_id = v.fuel_id " +
                "INNER JOIN department d ON d.dept_id = v.dept_id " + //Added INNER JOIN department d ON d.dept_id = v.dept_id - Aubrey Heramis
                "WHERE (f.description LIKE ? OR v.type LIKE ? OR v.description LIKE ? OR " +
                "v.make LIKE ? OR v.model LIKE ? OR v.version LIKE ? OR v.yearmade LIKE ? OR " +
                "v.regdate LIKE ? OR v.plate_no LIKE ? OR v.engine_no LIKE ? OR v.chassis_no LIKE ?) " +
                "AND v.is_active = 1 ORDER BY v.description")) {    */

            "SELECT v.vehicle_id, d.dept_name, " +
            "CONCAT(f.type, ' - ', f.description) AS fuel_entry, " +
            "v.type, v.description, make, model, version, yearmade, regdate, plate_no, " +
            "engine_no, chassis_no, cost, v.remarks " +
            "FROM vehicle v " +
            "INNER JOIN fuel f ON f.fuel_id = v.fuel_id " +
            "INNER JOIN department d ON d.dept_id = v.dept_id " +
            "WHERE (d.dept_name LIKE ? OR f.description LIKE ? OR v.type LIKE ? OR " +
            "v.description LIKE ? OR v.make LIKE ? OR v.model LIKE ? OR v.version LIKE ? OR " +
            "v.yearmade LIKE ? OR v.regdate LIKE ? OR v.plate_no LIKE ? OR " +
            "v.engine_no LIKE ? OR v.chassis_no LIKE ?) " +
            "AND v.is_active = 1 ORDER BY v.description")) {

            pstmt.setString(1, "%" + txtSearch.getText() + "%");
            pstmt.setString(2, "%" + txtSearch.getText() + "%");
            pstmt.setString(3, "%" + txtSearch.getText() + "%");
            pstmt.setString(4, "%" + txtSearch.getText() + "%");
            pstmt.setString(5, "%" + txtSearch.getText() + "%");
            pstmt.setString(6, "%" + txtSearch.getText() + "%");
            pstmt.setString(7, "%" + txtSearch.getText() + "%");
            pstmt.setString(8, "%" + txtSearch.getText() + "%");
            pstmt.setString(9, "%" + txtSearch.getText() + "%");
            pstmt.setString(10, "%" + txtSearch.getText() + "%");
            pstmt.setString(11, "%" + txtSearch.getText() + "%");
            pstmt.setString(12, "%" + txtSearch.getText() + "%"); //Added to enable search functionality for description - Aubrey Heramis

            // Hide multiple columns
            try (ResultSet rs = pstmt.executeQuery()) {
                Tablemodel = (DefaultTableModel) tblMain.getModel();
                Tablemodel.setRowCount(0);

                cellAlignCenterRenderer.setHorizontalAlignment(0);
                cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

                tblMain.setRowHeight(28);

                hideColumns(tblMain, new int[]{0, 1});  // Hide columns transID and OR Number"

                tblMain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tblMain.getColumnModel().getColumn(0).setPreferredWidth(40);   // Vehicle Id
                tblMain.getColumnModel().getColumn(1).setPreferredWidth(100);  // Id
                tblMain.getColumnModel().getColumn(2).setPreferredWidth(155);  // Department
                tblMain.getColumnModel().getColumn(3).setPreferredWidth(120);   // Fuel
                tblMain.getColumnModel().getColumn(4).setPreferredWidth(150);  // Type
                tblMain.getColumnModel().getColumn(5).setPreferredWidth(170);  // Description
                tblMain.getColumnModel().getColumn(6).setPreferredWidth(100);  // Make
                tblMain.getColumnModel().getColumn(7).setPreferredWidth(80);   // Model
                tblMain.getColumnModel().getColumn(8).setPreferredWidth(80);   // Version
                tblMain.getColumnModel().getColumn(9).setPreferredWidth(80); // Year Made
                tblMain.getColumnModel().getColumn(10).setPreferredWidth(110); // Reg Date
                tblMain.getColumnModel().getColumn(11).setPreferredWidth(100); // Plate #
                tblMain.getColumnModel().getColumn(12).setPreferredWidth(90); // Engine #
                tblMain.getColumnModel().getColumn(13).setPreferredWidth(100); // Chassis #
                tblMain.getColumnModel().getColumn(14).setPreferredWidth(100); // Cost
                tblMain.getColumnModel().getColumn(15).setPreferredWidth(140); // Remarks

                int cnt = 0;

                while (rs.next()) {
                    int id = rs.getInt("vehicle_id"); // TransID reference
                    Object[] rowData = {
                        id,
                        cnt + 1,
                        /*rs.getString("dept_name"), 
                        rs.getString("fuel_entry"),
                        //rs.getString("fuel_type"), //+ " - " + rs.getString("fuel_description"),  // Use separate fuel fields*/
                        rs.getString("dept_name"), // Added dept_name from department before fuel_entry - Aubrey Heramis
                        rs.getString("fuel_entry"),
                        rs.getString("v.type"),
                        rs.getString("v.description"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("version"),
                        rs.getInt("yearmade"),
                        rs.getDate("regdate"),
                        rs.getString("plate_no"),
                        rs.getString("engine_no"),
                        rs.getString("chassis_no"),
                        rs.getFloat("cost"),
                        rs.getString("remarks")
                    };
                    Tablemodel.addRow(rowData);
                    cnt++;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void openUpdateDialog() {
        functions fRow = new functions();

        if (tblMain.getRowCount() > 0 && tblMain.getSelectedRow() < 0) {
            tblMain.setRowSelectionInterval(0, 0);
            fRow.setRowID(0);
        }

        nextTransfrmparent = new vehicle_dialog(this, true);
        nextTransfrmparent.cmdEditClicked();
        nextTransfrmparent.setVisible(true);
    }

    public JTable getTbl() {
        return tblMain;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable();
        cmdNew = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vehicle Management");
        setResizable(false);

        jInternalFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jInternalFrame1.setToolTipText("");
        jInternalFrame1.setVisible(true);

        txtSearch.setBackground(new java.awt.Color(255, 255, 204));
        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSearch.setPrompt("     SEARCH");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        tblMain.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Vehicle_ID", "ID", "Department", "Fuel", "Vehicle Type", "Description", "Make", "Model", "Version", "Year Made", "Registration Date", "Plate Number", "Engine", "Chassis", "Cost", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMain.setToolTipText("Double click row to view vehicle details");
        tblMain.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tblMain.setColumnSelectionAllowed(true);
        tblMain.setShowHorizontalLines(true);
        tblMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMainMouseClicked(evt);
            }
        });
        tblMain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMainKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblMain);
        tblMain.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        cmdNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_32.png"))); // NOI18N
        cmdNew.setMnemonic('N');
        cmdNew.setText("New");
        cmdNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });

        cmdEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit_32.png"))); // NOI18N
        cmdEdit.setMnemonic('U');
        cmdEdit.setText("Update");
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });

        cmdDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_32.png"))); // NOI18N
        cmdDelete.setMnemonic('C');
        cmdDelete.setText("Delete");
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(cmdNew, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmdEdit)
                        .addGap(18, 18, 18)
                        .addComponent(cmdDelete)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmdNew)
                        .addComponent(cmdEdit)
                        .addComponent(cmdDelete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
        populateTable();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
        nextTransfrmparent = new vehicle_dialog(this, true);
        nextTransfrmparent.cmdNewClicked();
        nextTransfrmparent.setVisible(true);
    }//GEN-LAST:event_cmdNewActionPerformed

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
         //functions fRow = new functions();
        //Added by Aubrey Heramis
        //-------------------------------- 
        // Check if any row is selected. Added a showmessge if no row is selected
        System.out.println("Edit button clicked");

        if (tblMain.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int row = tblMain.getSelectedRow();
        int transId = (int) tblMain.getValueAt(row, 0);
        System.out.println("Selected row: " + row + ", Transaction ID: " + transId);//new

        functions fRow = new functions();
        fRow.setRowID(row);
        fRow.setTransID(transId);
        
        //openUpdateDialog();
        //--------------------------------  
        nextTransfrmparent = new vehicle_dialog(this, true);
        nextTransfrmparent.cmdEditClicked();
        nextTransfrmparent.setVisible(true);
        //--------------------------------  
    }//GEN-LAST:event_cmdEditActionPerformed

    private void tblMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMainMouseClicked
        // TODO add your handling code here:
        // Check if any row is selected
        if (tblMain.getSelectedRow() == -1) {
            return;
        }
        /*functions fRow = new functions();
        int row = tblMain.getSelectedRow();
        int transId = (int) tblMain.getValueAt(row, 0);
        
        fRow.setRowID(row);
        fRow.setTransID(transId);*/

        if (evt.getClickCount() == 2) {
            int row = tblMain.getSelectedRow();
            int transId = (int) tblMain.getValueAt(row, 0);
            System.out.println("Double-clicked row: " + row + ", Transaction ID: " + transId);

            functions fRow = new functions();
            fRow.setRowID(row);
            fRow.setTransID(transId);

            /*if (evt.getClickCount() == 2) {
          
                nextTransfrmparent = new vehicle_dialog(this, true); */ // Added to initialize double-clicking row of data - Aubrey Heramis
            nextTransfrmparent = new vehicle_dialog(this, true);
            nextTransfrmparent.cmdEditClicked();
            nextTransfrmparent.setVisible(true); // Show dialog before opening update
        }
    }//GEN-LAST:event_tblMainMouseClicked

    private void tblMainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMainKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //nextTransfrmparent = new vehicle_dialog(this, true);//Added to initialize double-clicking row of data - Aubrey Heramis
            // Check if any row is selected
            if (tblMain.getSelectedRow() == -1) {
                return;
            }
            
            // functions fRow = new functions();
            int row = tblMain.getSelectedRow();
            int transId = (int) tblMain.getValueAt(row, 0);
            System.out.println("Enter key pressed on row: " + row + ", Transaction ID: " + transId);

            functions fRow = new functions();
            fRow.setRowID(row);
            fRow.setTransID(transId);

            nextTransfrmparent = new vehicle_dialog(this, true);
            nextTransfrmparent.cmdEditClicked();
            //openUpdateDialog();
            nextTransfrmparent.setVisible(true);
        }
    }//GEN-LAST:event_tblMainKeyPressed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        functions fRow = new functions();

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this vehicle entry?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            query.delete_vehicle(fRow.getTransID(), functions.GetSystemNowDate(), fRow.getUserID());
        }

        populateTable();
    }//GEN-LAST:event_cmdDeleteActionPerformed

    public void imageCredits() throws FileNotFoundException, IOException {
        image_attribution image1 = new image_attribution("add_32", "https://www.freepik.com/icon/plus_1377215#position=16&term=add&fromView=search", new FileInputStream("add_32.png"));
        image1.attributes.put("author", "Freepik");
        System.out.println(image1.getAttribution());

        image_attribution image2 = new image_attribution("edit_32", "https://www.freepik.com/icon/pencil_1300613", new FileInputStream("edit_32.png"));
        image2.attributes.put("author", "Freepik");
        System.out.println(image2.getAttribution());

        image_attribution image4 = new image_attribution("delete_32", "https://www.freepik.com/icon/plus_1377215#position=16&term=add&fromView=search", new FileInputStream("delete_32.png"));
        image4.attributes.put("author", "IYAHICON");
        System.out.println(image4.getAttribution());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vehicle_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vehicle_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vehicle_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vehicle_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vehicle_main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdNew;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMain;
    private org.jdesktop.swingx.JXSearchField txtSearch;
    // End of variables declaration//GEN-END:variables

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        String searchText = txtSearch.getText().toLowerCase();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tblMain.getModel());
        tblMain.setRowSorter(sorter);
        
        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15)); // Added column 2 for department
        }
    }
}
