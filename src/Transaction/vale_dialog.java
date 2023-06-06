/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Transaction;

import Connection.DBConn;
import Others.functions;
import Others.query;
import static Transaction.vale_main.nextTransfrmparent;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;


/**
 *
 * @author Administrator
 */
public class vale_dialog extends javax.swing.JDialog {
    
    private vale_main parentForm;
    
    public vale_dialog(vale_main parentForm , boolean modal) {
        super(parentForm, true);
        this.parentForm = parentForm;

        initComponents();
        setLocationRelativeTo(this);
        
        cmbStatus.setRenderer(new ComboBoxRenderer());
        cmbVehicle.setRenderer(new ComboBoxRenderer());
        cmbAccount.setRenderer(new ComboBoxRenderer());
        cmbSupplier.setRenderer(new ComboBoxRenderer());
        cmbFuel.setRenderer(new ComboBoxRenderer());
        cmbDestination.setRenderer(new ComboBoxRenderer());

        populateComboBox(cmbStatus, "SELECT doc_status_id, doc_status_desc FROM document_status");
        populateComboBox(cmbVehicle, "SELECT vehicle_id, description FROM vehicle");
        populateComboBox(cmbAccount, "SELECT acct_id, title FROM accounts");
        populateComboBox(cmbSupplier, "SELECT supplier_id, name FROM suppliers where name like '%station%'");
        populateComboBox(cmbFuel, "SELECT fuel_id, concat(TYPE, \" - \", DESCRIPTION) AS description FROM fuel");
        populateComboBox(cmbDestination, "SELECT town_id, townname FROM destination");
        
        cmbType.addItem("Vale Slip");
        cmbType.addItem("Others");
        cmbType.setSelectedItem("Vale Slip");
    }
   
    private void populateComboBox(JComboBox comboBox, String query) {
        comboBox.removeAllItems();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            // Populate the combobox with data from the result set
            while (rs.next()) {
                String desc = rs.getString(2); // Assuming the second column is description or doc_status_desc
                model.addElement(desc);
            }

            comboBox.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Helper class to hold the ID and description of the combobox item
    class ComboBoxItem {
        private int id;
        private String description;

        public ComboBoxItem(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
    
    // Custom ListCellRenderer to display ComboBoxItem objects correctly
    class ComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof ComboBoxItem) {
                ComboBoxItem item = (ComboBoxItem) value;
                setText(item.getDescription());
            }
            return this;
        }
    }
    
    public void cmdNewClicked() {
        cmdCancel.setVisible(false); // Hide the cmdCancel button
        ImageIcon saveImage = new ImageIcon(getClass().getResource("/images/add.png"));
        cmdSave.setText("Add");
        cmdSave.setIcon(saveImage);
    }
    
    public void cmdEditClicked() {
        cmdCancel.setVisible(true); // Show the cmdCancel button
        cmbAccount.setEnabled(false);
        cmbDestination.setEnabled(false);
        cmbFuel.setEnabled(false);
        cmbStatus.setEnabled(false);
        cmbSupplier.setEnabled(false);
        cmbType.setEnabled(false);
        cmbVehicle.setEnabled(false);
        txtCode.setEnabled(false);
        txtVolreq.setEnabled(false);
        
//        Object colVal = parentForm.tblMain.getValueAt(selectedRowIndex, columnIndex);

        
        
        
        ImageIcon saveImage = new ImageIcon(getClass().getResource("/images/save.png"));
        cmdSave.setText("Save");
        cmdSave.setIcon(saveImage);
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
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbVehicle = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbAccount = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbType = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbFuel = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbDestination = new javax.swing.JComboBox<>();
        cmdSave = new javax.swing.JButton();
        cmdBack = new javax.swing.JButton();
        txtCode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtVolreq = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txtGiven = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApproved = new javax.swing.JTextField();
        cmdCancel = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jInternalFrame1.setVisible(true);

        jPanel1.setMaximumSize(new java.awt.Dimension(553, 491));

        cmbStatus.setMaximumRowCount(40);
        cmbStatus.setMaximumSize(new java.awt.Dimension(1000, 1000));

        jLabel1.setText("Status:");

        jLabel2.setText("Vehicle:");

        cmbVehicle.setMaximumRowCount(40);

        jLabel3.setText("Account:");

        cmbAccount.setMaximumRowCount(40);

        jLabel4.setText("Slip Type:");

        cmbType.setMaximumRowCount(40);

        jLabel5.setText("Supplier:");

        cmbSupplier.setMaximumRowCount(40);

        jLabel6.setText("Fuel:");

        cmbFuel.setMaximumRowCount(40);

        jLabel7.setText("Destination:");

        cmbDestination.setMaximumRowCount(40);

        cmdSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        cmdSave.setMnemonic('P');
        cmdSave.setText("Save");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });

        cmdBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        cmdBack.setMnemonic('C');
        cmdBack.setText("Back");
        cmdBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBackActionPerformed(evt);
            }
        });

        jLabel8.setText("Code:");

        jLabel9.setText("Volume Requested:");

        txtVolreq.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtVolreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVolreqActionPerformed(evt);
            }
        });

        jLabel10.setText("Volume Issued:");

        txtGiven.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtGiven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGivenActionPerformed(evt);
            }
        });

        jLabel11.setText("Approved By:");

        cmdCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelitem.png"))); // NOI18N
        cmdCancel.setMnemonic('P');
        cmdCancel.setText("Cancel");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        jLabel12.setText("Remarks:");

        txtRemarks.setColumns(20);
        txtRemarks.setRows(5);
        jScrollPane1.setViewportView(txtRemarks);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbVehicle, 0, 198, Short.MAX_VALUE)
                    .addComponent(cmbFuel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbDestination, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbAccount, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cmdSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdCancel)
                        .addGap(18, 18, 18)
                        .addComponent(cmdBack))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCode)
                                .addComponent(txtVolreq)
                                .addComponent(txtGiven)
                                .addComponent(txtApproved)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11))
                                    .addGap(85, 85, 85)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbFuel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVolreq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGiven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApproved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(152, 152, 152))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdBack)
                    .addComponent(cmdSave)
                    .addComponent(cmdCancel))
                .addContainerGap())
        );

        jInternalFrame1.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jInternalFrame1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_cmdBackActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
       int volg = Integer.parseInt(txtGiven.getText().replace(",", ""));
       String apprvby = txtApproved.getText();       
       String rem = txtRemarks.getText();
       
       functions trans = new functions();
       
       query.update_trans(trans.getTransID(), apprvby, functions.GetSystemNowDate(), volg, rem);
       
       JOptionPane.showMessageDialog(null, "Vale Updated", "Message", JOptionPane.INFORMATION_MESSAGE);
       parentForm.populateTable();
       this.dispose();
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void txtVolreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVolreqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVolreqActionPerformed

    private void txtGivenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGivenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGivenActionPerformed

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdCancelActionPerformed

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
            java.util.logging.Logger.getLogger(vale_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vale_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vale_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vale_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                vale_dialog dialog = new vale_dialog((vale_main) new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbAccount;
    private javax.swing.JComboBox<String> cmbDestination;
    private javax.swing.JComboBox<String> cmbFuel;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbSupplier;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JComboBox<String> cmbVehicle;
    private javax.swing.JButton cmdBack;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdSave;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtApproved;
    private javax.swing.JTextField txtCode;
    private javax.swing.JFormattedTextField txtGiven;
    private javax.swing.JTextArea txtRemarks;
    private javax.swing.JFormattedTextField txtVolreq;
    // End of variables declaration//GEN-END:variables
}
