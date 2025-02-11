/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Vale;

import Connection.DBConn;
import Others.cmb_customizer;
import Others.cmb_customizer.ComboBoxItem;
import static Others.cmb_customizer.findComboBoxItemByDescription;
import Others.functions;
import Others.image_attribution;
import Others.query;
import static Others.query.check_duplicate_vale;
import static Vale.vale_main.nextTransfrmparent;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Administrator
 */
public class vale_dialog extends javax.swing.JDialog {

    static Double pricePerLiter;
    private vale_main parentForm;
    private Integer check = 0;

    public vale_dialog(vale_main parentForm, boolean modal) {
        super(parentForm, true);
        this.parentForm = parentForm;

        initComponents();
        try {
            imageCredits();
        } catch (IOException ex) {
            Logger.getLogger(vale_dialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLocationRelativeTo(this);

        cmb_customizer.ComboBoxRenderer renderer = new cmb_customizer.ComboBoxRenderer();
        cmb_customizer cmbCustomizer = new cmb_customizer();

        //JComboBox<cmb_customizer.ComboBoxItem> cmbDriver = new JComboBox<>();
        cmbStatus.setRenderer(renderer);
        cmbVehicle.setRenderer(renderer);
        cmbAccount.setRenderer(renderer);
        cmbSupplier.setRenderer(renderer);
        cmbFuel.setRenderer(renderer);
        cmbDestination.setRenderer(renderer);
        cmbDriver.setRenderer(renderer);

        cmbType.addItem("Vale Slip");
        cmbType.addItem("Others");
        cmbType.setSelectedItem("Vale Slip");

        cmbCustomizer.populateComboBox(cmbStatus, "SELECT doc_status_id, doc_status_desc FROM document_status");
        cmbCustomizer.populateComboBox(cmbVehicle, "SELECT vehicle_id, description FROM vehicle");
        cmbCustomizer.populateComboBox(cmbAccount, "SELECT acct_id, title FROM accounts where acct_id IN (5671, 5694, 5682, 5688, 5726, 5735, 5745, 5693, 5661, 5664, 5111) order by title");
        cmbCustomizer.populateComboBox(cmbSupplier, "SELECT supplier_id, name FROM suppliers where name like '%station%'");
        cmbCustomizer.populateComboBox(cmbFuel, "SELECT fuel_id, description FROM fuel");
        cmbCustomizer.populateComboBox(cmbDriver, "SELECT e.emp_id, CONCAT(e.last_name, ' ', e.first_name) FROM employee e "
                + "LEFT JOIN position p ON p.position_id = e.position_id "
                + "ORDER BY e.last_name");
        cmbCustomizer.populateComboBox(cmbDestination, "SELECT town_id, townname FROM destination");
        cmbCustomizer.populateComboBox(cmbApproved, "SELECT position_id, position_desc FROM position WHERE position_desc LIKE \"%Manager%\"");

        // Add the DocumentListener to txtGiven for checking if more than txtVolreq onchange
        txtGiven.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateGivenValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateGivenValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateGivenValue();
            }

            private void validateGivenValue() {
                Long volReq;
                Long volGiven;

                try {
                    volReq = Long.valueOf(txtVolreq.getText());
                    volGiven = Long.valueOf(txtGiven.getText());
                } catch (NumberFormatException ex) {
                    return;
                }

                if (volGiven > volReq) {
                    JOptionPane.showMessageDialog(null, "Volume issued cannot exceed the volume requested!", "Error", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtGiven.setText("")); //update the text field's content outside of the event notification to avoid 'Attempt to mutate in notification' error.
                }
            }
        });
    }

    public void cmdNewClicked() {
        functions fRow = new functions();
        int row = fRow.getRowID();
        String selectedItemDesc;

        ComboBoxItem selectedItem;

        check = 0;
        cmdCancel.setVisible(false);
        ImageIcon saveImage = new ImageIcon(getClass().getResource("/images/add_32.png"));
        cmdSave.setText("Add");
        cmdSave.setIcon(saveImage);
        cmdSave.setMnemonic('A');
        txtGiven.setEnabled(false);
        txtPrice.setEnabled(false);
        txtTotalAmt.setEnabled(false);
        txtORno.setEnabled(false);

        txtCode.requestFocus();
        txtCode.putClientProperty("caretAspectRatio", 0.2);  //set cursor size
        txtVolreq.putClientProperty("caretAspectRatio", 0.2);  //set cursor size

        txtPrice.setText("0.00");  //by R. Repe
        txtTotalAmt.setText("0.00");

        //Default Values
        selectedItemDesc = "Pending";
        selectedItem = findComboBoxItemByDescription(cmbStatus, selectedItemDesc);
        cmbStatus.setSelectedItem(selectedItem);

        selectedItemDesc = "Miscellaneous General Expenses";
        selectedItem = findComboBoxItemByDescription(cmbAccount, selectedItemDesc);
        cmbAccount.setSelectedItem(selectedItem);

        java.util.Date date = new java.util.Date();
        dateTrans.setDate(date);  //set current date to date Chooser

    }

    public void cmdEditClicked() {
        check = 1;
        cmdCancel.setVisible(true);
        cmbAccount.setEnabled(true);
        cmbDestination.setEnabled(true);
        cmbFuel.setEnabled(true);
        cmbStatus.setEnabled(false);
        cmbSupplier.setEnabled(true);
        cmbType.setEnabled(false);
        cmbVehicle.setEnabled(true);
        txtCode.setEnabled(false);
        txtVolreq.setEnabled(false);
        cmbApproved.setEnabled(false);
        cmbDriver.setEnabled(false);
        dateTrans.setEnabled(true);
        txtPrice.setEnabled(false);
        txtORno.setEnabled(true);

        txtGiven.requestFocus();
        txtGiven.putClientProperty("caretAspectRatio", 0.2);  //set cursor size
        txtTotalAmt.putClientProperty("caretAspectRatio", 0.2);  //set cursor size

        ImageIcon saveImage = new ImageIcon(getClass().getResource("/images/save_32.png"));
        cmdSave.setText("Save");
        cmdSave.setIcon(saveImage);

        getRowVal();
    }

    private void getRowVal() {   // call this if edit/posting of values
        functions fRow = new functions();

        int row = fRow.getRowID();
        if (row >= 0) {
            String selectedItemDesc;

            //assign data to textboxes, from JTable during loading the dialog            
            txtCode.setText(parentForm.getTbl().getValueAt(row, 2).toString());
            txtVolreq.setText(parentForm.getTbl().getValueAt(row, 14).toString());
            txtGiven.setText(parentForm.getTbl().getValueAt(row, 14).toString());
            txtPrice.setText(parentForm.getTbl().getValueAt(row, 12).toString());
            txtTotalAmt.setText(parentForm.getTbl().getValueAt(row, 13).toString());

            txtORno.setText(parentForm.getTbl().getValueAt(row, 18).toString());

            txtGiven.selectAll();  //select the content of the textbox

            Object cellValue = parentForm.getTbl().getValueAt(row, 10);
            // JOptionPane.showMessageDialog(this,cellValue );  //display the date from from jTable
            if (cellValue instanceof Date) {
                Date selectedDate = (Date) cellValue;
                dateTrans.setDate(selectedDate);
            }

            // Set selected item for cmbType
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 3).toString();
            ComboBoxItem selectedItem = findComboBoxItemByDescription(cmbType, selectedItemDesc);
            if (selectedItem != null) {
                cmbType.setSelectedItem(selectedItem);
            }

            // Set selected item for cmbAccount
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 4).toString();
            selectedItem = findComboBoxItemByDescription(cmbAccount, selectedItemDesc);
            if (selectedItem != null) {
                cmbAccount.setSelectedItem(selectedItem);
            }

            // Set selected item for cmbVehicle
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 5).toString();
            selectedItem = findComboBoxItemByDescription(cmbVehicle, selectedItemDesc);
            if (selectedItem != null) {
                cmbVehicle.setSelectedItem(selectedItem);
            }

            // Set selected item for cmbFuel
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 6).toString();
            selectedItem = findComboBoxItemByDescription(cmbFuel, selectedItemDesc);
            if (selectedItem != null) {
                cmbFuel.setSelectedItem(selectedItem);
            }
//            //fuel checker
//            ComboBoxItem fuel = (ComboBoxItem) cmbFuel.getSelectedItem();
//             JOptionPane.showMessageDialog(null, fuel.getDescription());

            // Set selected item for cmbSupplier
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 7).toString();
            selectedItem = findComboBoxItemByDescription(cmbSupplier, selectedItemDesc);
            if (selectedItem != null) {
                cmbSupplier.setSelectedItem(selectedItem);
            }

            // Set selected item for cmbDestination
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 8).toString();
            selectedItem = findComboBoxItemByDescription(cmbDestination, selectedItemDesc);
            if (selectedItem != null) {
                cmbDestination.setSelectedItem(selectedItem);
            }

            // Set selected item for cmbStatus
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 16).toString();
            selectedItem = findComboBoxItemByDescription(cmbStatus, selectedItemDesc);
            if (selectedItem != null) {
                cmbStatus.setSelectedItem(selectedItem);
            }

            // Set selected item for cmbDriver
            selectedItemDesc = parentForm.getTbl().getValueAt(row, 17).toString();
            selectedItem = findComboBoxItemByDescription(cmbDriver, selectedItemDesc);
            if (selectedItem != null) {
                cmbDriver.setSelectedItem(selectedItem);
            }

            //driver checker
//            ComboBoxItem driver = (ComboBoxItem) cmbDriver.getSelectedItem();
//            JOptionPane.showMessageDialog(null, driver.getDescription());
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
        jLabel11 = new javax.swing.JLabel();
        cmdCancel = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        cmbDriver = new javax.swing.JComboBox<>();
        cmbApproved = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        dateTrans = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabelTransAmt = new javax.swing.JLabel();
        txtTotalAmt = new javax.swing.JFormattedTextField();
        txtPrice = new javax.swing.JFormattedTextField();
        jLabelPrice = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtGiven = new javax.swing.JFormattedTextField();
        jLabelTransAmt1 = new javax.swing.JLabel();
        txtORno = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jInternalFrame1.setTitle("Fuel Vale Updating/Posting");
        jInternalFrame1.setVisible(true);

        jPanel1.setMaximumSize(new java.awt.Dimension(553, 491));

        cmbStatus.setMaximumRowCount(40);
        cmbStatus.setMaximumSize(new java.awt.Dimension(1000, 1000));
        cmbStatus.setNextFocusableComponent(cmbType);

        jLabel1.setText("Status:");

        jLabel2.setText("Vehicle:");

        cmbVehicle.setMaximumRowCount(40);
        cmbVehicle.setNextFocusableComponent(cmbFuel);

        jLabel3.setText("Account Charging:");

        cmbAccount.setMaximumRowCount(40);
        cmbAccount.setNextFocusableComponent(cmbVehicle);
        cmbAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAccountActionPerformed(evt);
            }
        });

        jLabel4.setText("Slip Type:");

        cmbType.setMaximumRowCount(40);
        cmbType.setNextFocusableComponent(cmbAccount);
        cmbType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTypeActionPerformed(evt);
            }
        });

        jLabel5.setText("Supplier:");

        cmbSupplier.setMaximumRowCount(40);
        cmbSupplier.setNextFocusableComponent(cmbDriver);
        cmbSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSupplierActionPerformed(evt);
            }
        });

        jLabel6.setText("Fuel:");

        cmbFuel.setMaximumRowCount(40);
        cmbFuel.setNextFocusableComponent(cmbSupplier);

        jLabel7.setText("Destination:");

        cmbDestination.setMaximumRowCount(40);
        cmbDestination.setNextFocusableComponent(txtCode);

        cmdSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_32.png"))); // NOI18N
        cmdSave.setMnemonic('S');
        cmdSave.setText("Save");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        cmdSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmdSaveKeyPressed(evt);
            }
        });

        cmdBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back_32.png"))); // NOI18N
        cmdBack.setMnemonic('B');
        cmdBack.setText("Back");
        cmdBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBackActionPerformed(evt);
            }
        });

        txtCode.setNextFocusableComponent(txtVolreq);
        txtCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeActionPerformed(evt);
            }
        });

        jLabel8.setText("Vale Number:");

        jLabel9.setText("Volume Requested:");

        txtVolreq.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtVolreq.setNextFocusableComponent(txtGiven);
        txtVolreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVolreqActionPerformed(evt);
            }
        });

        jLabel11.setText("Approved By:");

        cmdCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel_32.png"))); // NOI18N
        cmdCancel.setMnemonic('C');
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

        jLabel13.setText("Driver:");

        cmbDriver.setMaximumRowCount(40);
        cmbDriver.setNextFocusableComponent(cmbDestination);

        cmbApproved.setMaximumRowCount(40);
        cmbApproved.setNextFocusableComponent(dateTrans);
        cmbApproved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbApprovedActionPerformed(evt);
            }
        });

        jLabel14.setText("Date:");

        dateTrans.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        dateTrans.setDateFormatString("MM/dd/yyyy");
        dateTrans.setNextFocusableComponent(txtRemarks);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(255, 255, 204), null));

        jLabelTransAmt.setText("Total Amount");

        txtTotalAmt.setBackground(new java.awt.Color(204, 255, 204));
        txtTotalAmt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtTotalAmt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalAmt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotalAmtFocusLost(evt);
            }
        });
        txtTotalAmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalAmtActionPerformed(evt);
            }
        });
        txtTotalAmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalAmtKeyPressed(evt);
            }
        });

        txtPrice.setBackground(new java.awt.Color(255, 255, 204));
        txtPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtPrice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtPrice.setNextFocusableComponent(cmbApproved);
        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        jLabelPrice.setText("Price per liter/s");

        jLabel10.setText("Actual Volume Issued:");

        txtGiven.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        txtGiven.setNextFocusableComponent(txtTotalAmt);
        txtGiven.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGivenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGivenFocusLost(evt);
            }
        });
        txtGiven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGivenActionPerformed(evt);
            }
        });

        jLabelTransAmt1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelTransAmt1.setText("OR Number");

        txtORno.setBackground(new java.awt.Color(204, 255, 204));
        txtORno.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtORno.setNextFocusableComponent(cmdSave);
        txtORno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtORnoFocusGained(evt);
            }
        });
        txtORno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtORnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelTransAmt))
                            .addComponent(txtTotalAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGiven, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(jLabelPrice)
                                .addGap(43, 43, 43))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabelTransAmt1)
                                        .addGap(40, 40, 40))
                                    .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(txtORno))
                                .addContainerGap(45, Short.MAX_VALUE))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelPrice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelTransAmt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotalAmt, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelTransAmt1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtORno)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbVehicle, 0, 276, Short.MAX_VALUE)
                    .addComponent(cmbType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbAccount, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbFuel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbDriver, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbDestination, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel13)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addComponent(txtVolreq, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(83, 83, 83))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(cmdSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdBack)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(86, 86, 86))
                                    .addComponent(cmbApproved, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(135, 135, 135))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(12, 12, 12))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVolreq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbFuel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbApproved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmdBack)
                                    .addComponent(cmdSave)
                                    .addComponent(cmdCancel)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame1.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jInternalFrame1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_cmdBackActionPerformed

    private void checkInput() {

        functions fRow = new functions();

        if (check == 0) {  //add new vale
            try {
                String code = txtCode.getText();
                int vol = Integer.parseInt(txtVolreq.getText().replace(",", ""));
                String rem = txtRemarks.getText();
                double price = Double.parseDouble(txtPrice.getText());
                double amt = Double.parseDouble(txtTotalAmt.getText());

                String type = (String) cmbType.getSelectedItem();
                ComboBoxItem acct = (ComboBoxItem) cmbAccount.getSelectedItem();
                ComboBoxItem ride = (ComboBoxItem) cmbVehicle.getSelectedItem();
                ComboBoxItem fuel = (ComboBoxItem) cmbFuel.getSelectedItem();
                ComboBoxItem supp = (ComboBoxItem) cmbSupplier.getSelectedItem();
                ComboBoxItem town = (ComboBoxItem) cmbDestination.getSelectedItem();
                ComboBoxItem stat = (ComboBoxItem) cmbStatus.getSelectedItem();
                ComboBoxItem apprvby = (ComboBoxItem) cmbApproved.getSelectedItem();
                ComboBoxItem driver = (ComboBoxItem) cmbDriver.getSelectedItem();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                if (dateTrans.getDate() != null) {
                    String sd = dateFormat.format(dateTrans.getDate()); //combox data for actual travel date

                    try {
                        java.util.Date utilDate = dateFormat.parse(sd);
                        long milliseconds = utilDate.getTime();
                        java.sql.Date sqlDate = new java.sql.Date(milliseconds);

                        //call the duplicate function checker 
                        Boolean isDuplicate = check_duplicate_vale(driver.getId(), sqlDate); //check for duplicates

                        //########################################################################
                        // Check for duplicate vale here
                        if (isDuplicate) {

                            int confirm = JOptionPane.showConfirmDialog(this, "Duplicate Fuel Vale slip found. Do you still want to proceed? " + driver.getDescription() + " for " + sqlDate, "Confirmation", JOptionPane.YES_NO_OPTION);

                            if (confirm == JOptionPane.YES_OPTION) {
                                JOptionPane.showMessageDialog(this, "Go ahead, create the vale for " + driver.getDescription() + "  for " + sqlDate);
                                query obj = new query();

                                
                                //call add new trans function object
                                obj.new_trans(code, type, fRow.getUserID(), sqlDate, vol, rem, stat.getId(), acct.getId(), driver.getId(), ride.getId(), fuel.getId(), supp.getId(), fRow.getUserID(), town.getId(), apprvby.getId(), functions.getSystemNowDateTimeString(), price, amt);
                                this.dispose();
                            }
                        } else {  //no duplicates detected during checking
                            query obj = new query();

                            //call add new trans function object
                            obj.new_trans(code, type, fRow.getUserID(), sqlDate, vol, rem, stat.getId(), acct.getId(), driver.getId(), ride.getId(), fuel.getId(), supp.getId(), fRow.getUserID(), town.getId(), apprvby.getId(), functions.getSystemNowDateTimeString(), price, amt);
                            this.dispose();
                            //System.out.println("No duplicate data found. Proceed with insertion.");
                            //JOptionPane.showMessageDialog(null, "New Vale is created ");
                        }
                        //JOptionPane.showMessageDialog(this, "Selected Item: " + driver.getDescription());
                        //CHECK DUPLICATES ENDS HERE ########################################################################

                    } catch (ParseException ex) {
                        Logger.getLogger(vale_dialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a proper date! " + "", "Set date", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.getStackTrace();
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                System.out.println(exceptionAsString);

                JOptionPane.showMessageDialog(this, "Saving Error!", exceptionAsString, JOptionPane.ERROR_MESSAGE);
            }
        } else {  //edit or posting vale

            double price = Double.parseDouble(txtPrice.getText().replace(",", ""));
            double amt = Double.parseDouble(txtTotalAmt.getText().replace(",", ""));
            int volgiven = Integer.parseInt(txtGiven.getText().replace(",", ""));
            String rem = txtRemarks.getText();
            String ornum = txtORno.getText();  //for checking if OR No is blank 

            //Edit or posting of returned vale slip
            ComboBoxItem supp = (ComboBoxItem) cmbSupplier.getSelectedItem();  //supplier object for update

            ComboBoxItem driver = (ComboBoxItem) cmbDriver.getSelectedItem();  //supplier object

            query.update_trans(fRow.getTransID(), functions.GetSystemNowDate(), volgiven, rem, functions.GetSystemNowDate(), price, amt, fRow.getUserID(), 33, supp.getId(), ornum);

            this.dispose();
        }
    }

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        functions fRow = new functions();
        checkInput();

        parentForm.populateTable();
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void txtVolreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVolreqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVolreqActionPerformed

    private void txtGivenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGivenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGivenActionPerformed

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        functions fRow = new functions();
        String rem = txtRemarks.getText();

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the transaction?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            query.cancel_trans(fRow.getTransID(), 26, rem, functions.GetSystemNowDate(), fRow.getUserID());
            this.dispose();
        }

        parentForm.populateTable();
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void txtTotalAmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalAmtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalAmtActionPerformed

    private void txtTotalAmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalAmtKeyPressed
        int key = evt.getKeyCode();
        DecimalFormat df = new DecimalFormat("###,###,###.00");

        if (key == KeyEvent.VK_ENTER) {
            //String total = df.format(tot);
            pricePerLiter = Double.parseDouble(txtTotalAmt.getText()) / Double.parseDouble(txtGiven.getText());        // TODO add your handling code here:
            txtPrice.setText(df.format(pricePerLiter));
            txtTotalAmt.setText(df.format(txtTotalAmt));
            // JOptionPane.showMessageDialog(this, pricePerLiter);
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtTotalAmtKeyPressed

    private void txtTotalAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalAmtFocusLost
        //JOptionPane.showMessageDialog(this,"lost focus");
        //evt.getSource();
        // int key = evt.getKeyCode();
        DecimalFormat df = new DecimalFormat("###,###,###.00");
        pricePerLiter = Double.parseDouble(txtTotalAmt.getText()) / Double.parseDouble(txtGiven.getText());        // TODO add your handling code here:
        txtPrice.setText(df.format(pricePerLiter));
        txtTotalAmt.setText(df.format(txtTotalAmt));
    
        // JOptionPane.showMessageDialog(this, pricePerLiter);

    }//GEN-LAST:event_txtTotalAmtFocusLost

    private void cmdSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmdSaveKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            checkInput();
            parentForm.populateTable();
        }
    }//GEN-LAST:event_cmdSaveKeyPressed

    private void txtGivenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGivenFocusLost

        txtTotalAmt.selectAll();  //select the content of the textbox

    }//GEN-LAST:event_txtGivenFocusLost

    private void txtGivenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGivenFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGivenFocusGained

    private void txtORnoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtORnoFocusGained
   txtORno.selectAll();  //select the content of the textbox        // TODO add your handling code here:
    }//GEN-LAST:event_txtORnoFocusGained

    private void cmbApprovedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbApprovedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbApprovedActionPerformed

    private void cmbSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSupplierActionPerformed

    private void txtCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeActionPerformed

    private void cmbTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTypeActionPerformed

    private void txtORnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtORnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtORnoActionPerformed

    private void cmbAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAccountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAccountActionPerformed

    public void imageCredits() throws FileNotFoundException, IOException {
        image_attribution image1 = new image_attribution("save_32", "https://www.freepik.com/icon/diskette_469319#position=26&page=5&term=save&fromView=search", new FileInputStream("save_32.png"));
        image1.attributes.put("author", "Freepik");
        System.out.println(image1.getAttribution());

        image_attribution image2 = new image_attribution("cancel_32", "https://www.freepik.com/icon/prohibition_469377#position=4&term=save&fromView=search", new FileInputStream("cancel_32.png"));
        image2.attributes.put("author", "Freepik");
        System.out.println(image2.getAttribution());

        image_attribution image3 = new image_attribution("back_32", "https://www.freepik.com/icon/back_469375#position=5&term=save&fromView=search", new FileInputStream("back_32.png"));
        image3.attributes.put("author", "Freepik");
        System.out.println(image3.getAttribution());

        image_attribution image4 = new image_attribution("add_32", "https://www.freepik.com/icon/plus_1377215#position=16&term=add&fromView=search", new FileInputStream("add_32.png"));
        image4.attributes.put("author", "Freepik");
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
    private javax.swing.JComboBox<String> cmbApproved;
    private javax.swing.JComboBox<String> cmbDestination;
    private javax.swing.JComboBox<String> cmbDriver;
    private javax.swing.JComboBox<String> cmbFuel;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbSupplier;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JComboBox<String> cmbVehicle;
    private javax.swing.JButton cmdBack;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdSave;
    private com.toedter.calendar.JDateChooser dateTrans;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JLabel jLabelTransAmt;
    private javax.swing.JLabel jLabelTransAmt1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCode;
    private javax.swing.JFormattedTextField txtGiven;
    private javax.swing.JTextField txtORno;
    private javax.swing.JFormattedTextField txtPrice;
    private javax.swing.JTextArea txtRemarks;
    private javax.swing.JFormattedTextField txtTotalAmt;
    private javax.swing.JFormattedTextField txtVolreq;
    // End of variables declaration//GEN-END:variables
}
