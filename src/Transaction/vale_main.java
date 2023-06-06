/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Transaction;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.event.KeyEvent;

import Connection.DBConn;
import Transaction.vale_dialog;

public class vale_main extends javax.swing.JFrame {
    
    public static vale_dialog nextTransfrmparent;
    
    static DefaultTableCellRenderer cellAlignCenterRenderer = new DefaultTableCellRenderer();
    static DefaultTableCellRenderer cellAlignRightRenderer = new DefaultTableCellRenderer();
    static DefaultTableModel Tablemodel;
    static Statement stmt;

    public vale_main() {
        initComponents();
        populateTable();
        setLocationRelativeTo(this);
        
        jScrollPane1.setViewportView(tblMain);
        tblMain.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
    }
    
    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Get the value of the "Status" column for the current row
            String status = table.getValueAt(row, 15).toString();

        // Set the background color based on the "Status" value
            switch (status) {
                case "Pending" -> component.setBackground(Color.RED);
                case "Completed" -> component.setBackground(Color.GREEN);
                default -> component.setBackground(table.getBackground());
            }

            // Set the foreground color for better visibility
            component.setForeground(table.getForeground());

            if (isSelected) {
                // Set the selection background color for the entire row
                component.setBackground(table.getSelectionBackground());
                component.setForeground(table.getSelectionForeground());
            }

            return component;
        }
    }
    
    private void populateTable() {
        Connection conn = DBConn.getConnection();
        String createString;
        createString = "SELECT t.trans_id, t.vale_no, t.type, a.title, v.description, f.description, s.name, d.townname, t.createdby, t.createddate," +
                " DATE_ADD(createddate, INTERVAL 7 day) AS validuntil, t.approvedby, t.approveddate, t.volrequested, t.volgiven, ds.doc_status_desc, e.last_name, u.full_name" +
                " FROM transaction t" +
                " INNER JOIN vehicle v ON v.vehicle_id = t.trans_id" +
                " INNER JOIN fuel f ON f.fuel_id = t.fuel_id" +
                " INNER JOIN accounts a ON a.acct_id = t.acct_id" +
                " INNER JOIN employee e ON e.emp_id = t.emp_id" +
                " INNER JOIN suppliers s ON s.supplier_id = t.supplier_id" +
                " INNER JOIN destination d ON d.town_id = t.town_id" +
                " INNER JOIN user u ON u.user_id = t.user_id"+
                " INNER JOIN document_status ds ON ds.doc_status_id = t.status_id"+
                " WHERE t.vale_no LIKE '%"+ txtSearch.getText() +"%'" +
                " OR t.type LIKE '%"+ txtSearch.getText() +"%'" +
                " OR a.title LIKE '%"+ txtSearch.getText() +"%'" +
                " OR v.description LIKE '%"+ txtSearch.getText() +"%'" +
                " OR f.description LIKE '%"+ txtSearch.getText() +"%'" +
                " OR s.name LIKE '%"+ txtSearch.getText() +"%'" +
                " OR d.townname LIKE '%"+ txtSearch.getText() +"%'" +
                " OR t.createdby LIKE '%"+ txtSearch.getText() +"%'" +
                " OR ds.doc_status_desc LIKE '%"+ txtSearch.getText() +"%'" +
                " OR t.approvedby LIKE '%"+ txtSearch.getText() +"%'" +
                " OR u.full_name LIKE '%"+ txtSearch.getText() +"%'";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(createString);

            Tablemodel = (DefaultTableModel) tblMain.getModel();

            cellAlignCenterRenderer.setHorizontalAlignment(0);
            cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

            tblMain.setRowHeight(28);            
            tblMain.setColumnSelectionAllowed(false);

            Tablemodel.setNumRows(0);
            int cnt = 0;

            while (rs.next()) {
                Tablemodel.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getDate(11), rs.getString(12), rs.getDate(13), rs.getInt(14), rs.getInt(15), rs.getString(16), rs.getString(17)});
                cnt++;
            }

            stmt.close();
            conn.close();
            
            tblMain.setDefaultEditor(Object.class, null);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void openUpdateDialog() {
        int selectedRow = tblMain.getSelectedRow();
        int statusColumnIndex = 2; // Assuming the status column is at index 2

        Object statusValue = tblMain.getValueAt(selectedRow, statusColumnIndex);
        String status = String.valueOf(statusValue);

        nextTransfrmparent = new vale_dialog(this, true);      
        nextTransfrmparent.setVisible(true);
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
        txtSearch = new org.jdesktop.swingx.JXSearchField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable();
        cmdNew = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setTitle("Vale Main");
        jInternalFrame1.setVisible(true);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Code", "Type", "Account", "Vehicle", "Fuel Type", "Supplier", "Destination", "Created By", "Date Created", "Validity", "Approved By", "Date Approved", "Volume Requested", "Volume Issued", "Status", "Logged By"
            }
        ));
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

        cmdNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        cmdNew.setMnemonic('A');
        cmdNew.setText("New");
        cmdNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });

        cmdEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        cmdEdit.setMnemonic('A');
        cmdEdit.setText("Update");
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1487, Short.MAX_VALUE)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(cmdNew, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmdEdit)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdNew)
                    .addComponent(cmdEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        populateTable();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
        nextTransfrmparent = new vale_dialog(this, true);         // Call vale_dialog form
        nextTransfrmparent.cmdNewClicked();                                     // Notify vale_dialog form that cmdNew is triggered
        nextTransfrmparent.setVisible(true);
    }//GEN-LAST:event_cmdNewActionPerformed

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        if (tblMain.getRowCount() > 0) {
            tblMain.setRowSelectionInterval(0, 0);
        }
        
        openUpdateDialog();
    }//GEN-LAST:event_cmdEditActionPerformed

    private void tblMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMainMouseClicked
        if (evt.getClickCount() == 2) {
            openUpdateDialog();
        }
    }//GEN-LAST:event_tblMainMouseClicked

    private void tblMainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMainKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            openUpdateDialog();
        }
    }//GEN-LAST:event_tblMainKeyPressed

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
            java.util.logging.Logger.getLogger(vale_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vale_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vale_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vale_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vale_main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdNew;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMain;
    private org.jdesktop.swingx.JXSearchField txtSearch;
    // End of variables declaration//GEN-END:variables
}
