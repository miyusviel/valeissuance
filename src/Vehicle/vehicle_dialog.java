/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Vehicle;

import Others.functions;
import Others.query;
import Others.cmb_customizer;
import Others.cmb_customizer.ComboBoxItem;
import static Others.cmb_customizer.findComboBoxItemByDescription;
import Others.image_attribution;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class vehicle_dialog extends javax.swing.JDialog {

    /**
     * Creates new form destination_dialog
     */
    private vehicle_main parentForm;
    private Integer check = 0;

    public vehicle_dialog(vehicle_main parentForm, boolean modal) {
        super(parentForm, true);
        this.parentForm = parentForm;

        initComponents();
        try {
            imageCredits();
        } catch (IOException ex) {
            Logger.getLogger(vehicle_dialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLocationRelativeTo(this);

        // Create an instance of the ComboBoxCustomizer class
        cmb_customizer cmbCustomizer = new cmb_customizer();

        // Populate both combo boxes
        cmbCustomizer.populateComboBox(cmbFuel, "SELECT fuel_id, description FROM fuel");
        cmbCustomizer.populateComboBox(cmbDepartment, "SELECT dept_id, dept_name FROM department ORDER BY dept_name");

        // Create an instance of the ComboBoxRenderer class
        cmb_customizer.ComboBoxRenderer renderer = new cmb_customizer.ComboBoxRenderer();

        // Set the ComboBoxRenderer as the cell renderer for both combo boxes
        cmbFuel.setRenderer(renderer);
        cmbDepartment.setRenderer(renderer); //Added by Aubrey Heramis
    }

    public void cmdNewClicked() {
        check = 0;

        ImageIcon saveImage = new ImageIcon(getClass().getResource("/images/add_32.png"));
        cmdSave.setText("Add");
        cmdSave.setIcon(saveImage);
        cmdSave.setMnemonic('A');
    }

    public void cmdEditClicked() {
        System.out.println("DEBUG: cmdEditClicked started");
        
        functions fRow = new functions();
        int row = fRow.getRowID();
        /*String selectedItemDesc;
        check = 1;

//---------------------------------
        //Added by Aubrey Heramis
        //Added to set existing string color to black instead of gray
        txtType.setText(parentForm.getTbl().getValueAt(row, 4).toString());
        txtType.setForeground(new Color(0, 0, 0));
        
        txtDescription.setText(parentForm.getTbl().getValueAt(row, 5).toString());  // Changed from 4 to 5
        txtDescription.setForeground(new Color(0, 0, 0));
        
        txtMake.setText(parentForm.getTbl().getValueAt(row, 6).toString());  // Changed from 5 to 6
        txtMake.setForeground(new Color(0, 0, 0));
        
        txtModel.setText(parentForm.getTbl().getValueAt(row, 7).toString());  // Changed from 6 to 7
        txtModel.setForeground(new Color(0, 0, 0));
        
        txtVersion.setText(parentForm.getTbl().getValueAt(row, 8).toString());  // Changed from 7 to 8
        txtVersion.setForeground(new Color(0, 0, 0));
        
        txtYear.setText(parentForm.getTbl().getValueAt(row, 9).toString());  // Changed from 8 to 9
        txtYear.setForeground(new Color(0, 0, 0));
        
        txtPlate.setText(parentForm.getTbl().getValueAt(row, 11).toString());  // Changed from 10 to 11
        txtPlate.setForeground(new Color(0, 0, 0));
        
        txtEngine.setText(parentForm.getTbl().getValueAt(row, 12).toString());  // Changed from 11 to 12
        txtEngine.setForeground(new Color(0, 0, 0));
        
        txtChassis.setText(parentForm.getTbl().getValueAt(row, 13).toString());  // Changed from 12 to 13
        txtChassis.setForeground(new Color(0, 0, 0));
        
        txtCost.setText(parentForm.getTbl().getValueAt(row, 14).toString());  // Changed from 13 to 14
        txtCost.setForeground(new Color(0, 0, 0));
        
        txtRemarks.setText(parentForm.getTbl().getValueAt(row, 15).toString());  // Changed from 14 to 15
        txtRemarks.setForeground(new Color(0, 0, 0));*/
        check = 1;
        System.out.println("DEBUG: Loading data for row=" + row);
        
        try {
            txtType.setText(parentForm.getTbl().getValueAt(row, 4).toString());
            txtType.setForeground(new Color(0, 0, 0));
            
            txtDescription.setText(parentForm.getTbl().getValueAt(row, 5).toString());  // Changed from 4 to 5
            txtDescription.setForeground(new Color(0, 0, 0));
            
            txtMake.setText(parentForm.getTbl().getValueAt(row, 6).toString());  // Changed from 5 to 6
            txtMake.setForeground(new Color(0, 0, 0));
            
            txtModel.setText(parentForm.getTbl().getValueAt(row, 7).toString());  // Changed from 6 to 7
            txtModel.setForeground(new Color(0, 0, 0));
            
            txtVersion.setText(parentForm.getTbl().getValueAt(row, 8).toString());  // Changed from 7 to 8
            txtVersion.setForeground(new Color(0, 0, 0));
            
            txtYear.setText(parentForm.getTbl().getValueAt(row, 9).toString());  // Changed from 8 to 9
            txtYear.setForeground(new Color(0, 0, 0));
            
            // Load Registration Date
            Object cellValue = parentForm.getTbl().getValueAt(row, 10);
            if (cellValue instanceof Date) {
                Date selectedDate = (Date) cellValue;
                RegDate.setDate(selectedDate);
            }
            
            // Load Plate
            txtPlate.setText(parentForm.getTbl().getValueAt(row, 11).toString());
            txtPlate.setForeground(new Color(0, 0, 0));
            
            txtEngine.setText(parentForm.getTbl().getValueAt(row, 12).toString());  // Changed from 11 to 12
            txtEngine.setForeground(new Color(0, 0, 0));
            
            txtChassis.setText(parentForm.getTbl().getValueAt(row, 13).toString());  // Changed from 12 to 13
            txtChassis.setForeground(new Color(0, 0, 0));
            
            txtCost.setText(parentForm.getTbl().getValueAt(row, 14).toString());  // Changed from 13 to 14
            txtCost.setForeground(new Color(0, 0, 0));
            
            txtRemarks.setText(parentForm.getTbl().getValueAt(row, 15).toString());
            txtRemarks.setForeground(new Color(0, 0, 0));  // Changed from 14 to 15
            /*
            //---------------------------------

        // Handle date field
        Object cellValue = parentForm.getTbl().getValueAt(row, 10);
        // JOptionPane.showMessageDialog(this,cellValue );  //display the date from from jTable
        if (cellValue instanceof Date) {
            Date selectedDate = (Date) cellValue;
            RegDate.setDate(selectedDate);
        }
            */
            /*txtRemarks.setForeground(new Color(0, 0, 0));

            // Handle date field
            Object cellValue = parentForm.getTbl().getValueAt(row, 10);
            // JOptionPane.showMessageDialog(this,cellValue );  //display the date from from jTable
            if (cellValue instanceof Date) {
                Date selectedDate = (Date) cellValue;
                RegDate.setDate(selectedDate);
            }

        /*
            //---------------------------------

        // Handle date field
        Object cellValue = parentForm.getTbl().getValueAt(row, 10);
        // JOptionPane.showMessageDialog(this,cellValue );  //display the date from from jTable
        if (cellValue instanceof Date) {
            Date selectedDate = (Date) cellValue;
            RegDate.setDate(selectedDate);
        }
        */

            // Handle fuel selection
            String fuelType = parentForm.getTbl().getValueAt(row, 3).toString();  // Get the combined string
            String[] fuelParts = fuelType.split(" - ");  // Split it into type and description
            if (fuelParts.length == 2) {
                ComboBoxItem selectedItem = findComboBoxItemByDescription(cmbFuel, fuelParts[1].trim());  // Use the description part
                if (selectedItem != null) {
                    cmbFuel.setSelectedItem(selectedItem);
                }
            }

            // Handle department selection
            String selectedDeptDesc = parentForm.getTbl().getValueAt(row, 2).toString(); // Adjust index based on your table
            ComboBoxItem selectedDept = findComboBoxItemByDescription(cmbDepartment, selectedDeptDesc);
            if (selectedDept != null) {
                cmbDepartment.setSelectedItem(selectedDept);
            }

            // Update save button
            ImageIcon saveImage = new ImageIcon(getClass().getResource("/images/save_32.png"));
            cmdSave.setText("Save");
            cmdSave.setIcon(saveImage);
//Added for debugging only ------------------------
            System.out.println("DEBUG: All fields loaded successfully");
        } catch (Exception e) {
            System.out.println("DEBUG: Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
        //--------------------------------- 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtType = new javax.swing.JTextField();
        cmdSave = new javax.swing.JButton();
        cmdBack = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbFuel = new javax.swing.JComboBox<>();
        cmbDepartment = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtMake = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtModel = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtVersion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtPlate = new javax.swing.JTextField();
        txtYear = new javax.swing.JFormattedTextField();
        RegDate = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtChassis = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtCost = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        txtEngine = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jInternalFrame1.setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel8.setText("Type:");

        txtType.setForeground(new java.awt.Color(153, 153, 153));
        txtType.setText("Enter vehicle type");
        txtType.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTypeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTypeFocusLost(evt);
            }
        });
        txtType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTypeActionPerformed(evt);
            }
        });

        cmdSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_32.png"))); // NOI18N
        cmdSave.setMnemonic('S');
        cmdSave.setText("Save");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
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

        jLabel9.setText("Description:");

        txtDescription.setForeground(new java.awt.Color(153, 153, 153));
        txtDescription.setText("Enter vehicle description");
        txtDescription.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescriptionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescriptionFocusLost(evt);
            }
        });
        txtDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescriptionActionPerformed(evt);
            }
        });

        jLabel6.setText("Department:");

        cmbFuel.setMaximumRowCount(40);
        cmbFuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFuelActionPerformed(evt);
            }
        });

        cmbDepartment.setMaximumRowCount(40);
        cmbDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartmentActionPerformed(evt);
            }
        });

        jLabel10.setText("Make:");

        txtMake.setForeground(new java.awt.Color(153, 153, 153));
        txtMake.setText("Enter vehicle maker");
        txtMake.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMakeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMakeFocusLost(evt);
            }
        });
        txtMake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMakeActionPerformed(evt);
            }
        });

        jLabel11.setText("Model:");

        txtModel.setForeground(new java.awt.Color(153, 153, 153));
        txtModel.setText("Enter vehicle model");
        txtModel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtModelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtModelFocusLost(evt);
            }
        });
        txtModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModelActionPerformed(evt);
            }
        });

        jLabel12.setText("Version:");

        txtVersion.setForeground(new java.awt.Color(153, 153, 153));
        txtVersion.setText("Enter vehicle version");
        txtVersion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVersionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVersionFocusLost(evt);
            }
        });

        jLabel13.setText("Year Made:");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Registration Date:");

        jLabel15.setText("Plate Number:");

        txtPlate.setForeground(new java.awt.Color(153, 153, 153));
        txtPlate.setText("Enter vehicle plate number (e.g. ABC1234)");
        txtPlate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPlateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlateFocusLost(evt);
            }
        });
        txtPlate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlateActionPerformed(evt);
            }
        });

        txtYear.setForeground(new java.awt.Color(153, 153, 153));
        txtYear.setText("Enter vehicle year made");
        txtYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtYearFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtYearFocusLost(evt);
            }
        });
        txtYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtYearActionPerformed(evt);
            }
        });

        RegDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        RegDate.setDateFormatString("MM/dd/yyyy");

        jLabel16.setText("Chassis Number:");

        txtChassis.setForeground(new java.awt.Color(153, 153, 153));
        txtChassis.setText("Enter vehicle chassis number");
        txtChassis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtChassisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtChassisFocusLost(evt);
            }
        });

        jLabel17.setText("Cost:");

        txtCost.setForeground(new java.awt.Color(153, 153, 153));
        txtCost.setText("Enter vehicle cost (e.g. 500000)");
        txtCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCostFocusLost(evt);
            }
        });
        txtCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostActionPerformed(evt);
            }
        });

        jLabel18.setText("Remarks:");

        txtRemarks.setColumns(20);
        txtRemarks.setForeground(new java.awt.Color(153, 153, 153));
        txtRemarks.setRows(5);
        txtRemarks.setText("Enter remarks (Optional)");
        txtRemarks.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRemarksFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRemarksFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txtRemarks);

        jLabel19.setText("Engine Number:");

        txtEngine.setForeground(new java.awt.Color(153, 153, 153));
        txtEngine.setText("Enter vehicle engine number");
        txtEngine.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEngineFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEngineFocusLost(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Vehicle Details");

        jLabel7.setText("Fuel: ");

        jLabel20.setForeground(new java.awt.Color(255, 0, 0));
        jLabel20.setText("*");

        jLabel21.setForeground(new java.awt.Color(255, 0, 0));
        jLabel21.setText("*");

        jLabel22.setForeground(new java.awt.Color(255, 0, 0));
        jLabel22.setText("*");

        jLabel23.setForeground(new java.awt.Color(255, 0, 0));
        jLabel23.setText("*");

        jLabel24.setForeground(new java.awt.Color(255, 0, 0));
        jLabel24.setText("*");

        jLabel25.setForeground(new java.awt.Color(255, 0, 0));
        jLabel25.setText("*");

        jLabel26.setForeground(new java.awt.Color(255, 0, 0));
        jLabel26.setText("*");

        jLabel27.setForeground(new java.awt.Color(255, 0, 0));
        jLabel27.setText("*");

        jLabel28.setForeground(new java.awt.Color(255, 0, 0));
        jLabel28.setText("*");

        jLabel29.setForeground(new java.awt.Color(255, 0, 0));
        jLabel29.setText("*");

        jLabel30.setForeground(new java.awt.Color(255, 0, 0));
        jLabel30.setText("*");

        jLabel31.setForeground(new java.awt.Color(255, 0, 0));
        jLabel31.setText("*");

        jLabel32.setForeground(new java.awt.Color(255, 0, 0));
        jLabel32.setText("*");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtType)
                                    .addComponent(txtMake, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtModel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                    .addComponent(cmbFuel, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbDepartment, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtVersion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtYear, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1))
                                .addGap(117, 117, 117)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(txtCost)
                            .addComponent(txtChassis)
                            .addComponent(txtEngine)
                            .addComponent(txtPlate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(RegDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel18)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 130, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdSave)
                        .addGap(18, 18, 18)
                        .addComponent(cmdBack)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel7)
                    .addComponent(jLabel20)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RegDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFuel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel6)
                    .addComponent(jLabel21)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel8)
                    .addComponent(jLabel22)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEngine, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel23)
                        .addComponent(jLabel31)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtChassis, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel24)
                    .addComponent(jLabel17)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMake, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel25)
                    .addComponent(jLabel18))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdBack)
                    .addComponent(cmdSave))
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostActionPerformed

    private void txtPlateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlateActionPerformed

    private void cmbDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDepartmentActionPerformed

    private void cmbFuelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFuelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFuelActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescriptionActionPerformed

    private void cmdBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_cmdBackActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        functions fRow = new functions();

        try {
            ComboBoxItem fuel = (ComboBoxItem) cmbFuel.getSelectedItem();
            ComboBoxItem dept = (ComboBoxItem) cmbDepartment.getSelectedItem(); //Added by Aubrey Heramis
            int deptId = dept.getId();
            /*String type = txtType.getText();
            String desc = txtDescription.getText();
            String make = txtMake.getText();
            String model = txtModel.getText();
            String version = txtVersion.getText();
            //int year = Integer.parseInt(txtYear.getText()); */
            
            // Added error trappings where fields are empty. Except for REMARKS - Aubrey Heramis
            String type = txtType.getText().trim();
            if (type.isEmpty() || type.equals("Enter vehicle type")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Type", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtType.requestFocus();
                return;
            }

            String desc = txtDescription.getText().trim();
            if (desc.isEmpty() || desc.equals("Enter vehicle description")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Description", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtDescription.requestFocus();
                return;
            }

            String make = txtMake.getText().trim();
            if (make.isEmpty() || make.equals("Enter vehicle maker")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Make", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtMake.requestFocus();
                return;
            }

            String model = txtModel.getText().trim();
            if (model.isEmpty() || model.equals("Enter vehicle model")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Model", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtModel.requestFocus();
                return;
            }

            String version = txtVersion.getText().trim();
            if (version.isEmpty() || version.equals("Enter vehicle version")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Version", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtVersion.requestFocus();
                return;
            }

            String plate = txtPlate.getText().trim();
            if (plate.isEmpty() || plate.equals("Enter vehicle plate number (e.g. ABC1234)")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Plate Number", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtPlate.requestFocus();
                return;
            }

            String engine = txtEngine.getText().trim();
            if (engine.isEmpty() || engine.equals("Enter vehicle engine number")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Engine Number", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtEngine.requestFocus();
                return;
            }

            String chassis = txtChassis.getText().trim();
            if (chassis.isEmpty() || chassis.equals("Enter vehicle chassis number")) {
                JOptionPane.showMessageDialog(this, "Please enter vehicle Chassis Number", "Required Field", JOptionPane.ERROR_MESSAGE);
                txtChassis.requestFocus();
                return;
            }

            /// Added condition for year that it will only accept integers - Aubrey Heramis
            int year;
            try {
                year = Integer.parseInt(txtYear.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number for Year", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
                txtYear.requestFocus();
                return;
            }

            // Added error trapping, accepting integer values only - Aubrey Heramis
            float cost;
            try {
                String costText = txtCost.getText().trim();
                if (costText.equals("Enter vehicle cost (e.g. 500000)")) { //Condition added to determine if the field is null - Aubrey Heramis
                    JOptionPane.showMessageDialog(this, 
                        "Please enter a valid number for Cost", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    txtCost.requestFocus();
                    return;
                }
                // Added regex validation to only allow numbers and one decimal point - Aubrey Heramis
                if (!costText.matches("^[0-9]+(\\.[0-9]{0,2})?$")) {
                    JOptionPane.showMessageDialog(this, 
                        "Please enter a valid number format (e.g. 1000 or 1000.00)", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    txtCost.requestFocus();
                    return;
                }
                cost = Float.parseFloat(costText);
            } catch (NumberFormatException e) {
                txtCost.requestFocus();
                return;
            }
            /*String rem = txtRemarks.getText(); */ //Remarks is optional
            String rem = "N/A";  // Default value
            if (!txtRemarks.getText().trim().isEmpty() && !txtRemarks.getText().equals("Enter remarks (Optional)")) {
                rem = txtRemarks.getText().trim();
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (check == 1) {  // Edit 
                try {
                    if (RegDate.getDate() != null) {
                        String sd = dateFormat.format(RegDate.getDate());
                        /*try {
                            java.util.Date utilDate = dateFormat.parse(sd);
                            long milliseconds = utilDate.getTime();
                            java.sql.Date sqlDate = new java.sql.Date(milliseconds);*/
                        java.util.Date utilDate = dateFormat.parse(sd);
                        long milliseconds = utilDate.getTime();
                        java.sql.Date sqlDate = new java.sql.Date(milliseconds);

                        /* query obj = new query();

                            obj.new_vehicle(deptId, fuel.getId(), type, desc, make, model, version, year, sqlDate, plate, engine, chassis, cost, rem, functions.GetSystemNowDate(), fRow.getUserID()); //Added deptID for Department JBoxCombo- Aubrey Heramis
                            this.dispose();
                        } catch (ParseException ex) {
                            Logger.getLogger(vehicle_dialog.class.getName()).log(Level.SEVERE, null, ex);
                        }*/
                        query.update_vehicle(
                            fRow.getTransID(),
                            deptId, 
                            fuel.getId(), 
                            type,
                            desc,
                            make,
                            model,
                            version,
                            year,
                            sqlDate,
                            plate,
                            engine,
                            chassis,
                            cost,
                            rem,
                            functions.GetSystemNowDate(),
                            fRow.getUserID()
                        );
                        
                        this.dispose();
                    } else {
                        /*JOptionPane.showMessageDialog(this, "Please input proper registration date " + "", "Set Date", JOptionPane.ERROR_MESSAGE);*/
                        JOptionPane.showMessageDialog(this, "Please input proper registration date", "Set Date", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsString = sw.toString();
                    System.out.println(exceptionAsString);

                    JOptionPane.showMessageDialog(this, "Saving Error!", exceptionAsString, JOptionPane.ERROR_MESSAGE);
                }
            /* } else {
                if (RegDate.getDate() != null) {
                    String sd = dateFormat.format(RegDate.getDate());

                    try { */
            
            } else {  // New record mode
                try {
                    if (RegDate.getDate() != null) {
                        String sd = dateFormat.format(RegDate.getDate());
                        java.util.Date utilDate = dateFormat.parse(sd);
                        long milliseconds = utilDate.getTime();
                        java.sql.Date sqlDate = new java.sql.Date(milliseconds);

                        query obj = new query();
                        /*query.update_vehicle(fRow.getTransID(), deptId, fuel.getId(), type, desc, make, model, version, year, sqlDate, plate, engine, chassis, cost, rem, functions.GetSystemNowDate(), fRow.getUserID()); //Added deptID for Department - Aubrey Heramis*/
                        obj.new_vehicle(deptId, fuel.getId(), type, desc, make, model, version, year, sqlDate, plate, engine, chassis, cost, rem, functions.GetSystemNowDate(), fRow.getUserID());
                        
                        this.dispose();
                    /* } catch (ParseException ex) {
                        Logger.getLogger(vehicle_dialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please input proper registration date " + "", "Set Date", JOptionPane.ERROR_MESSAGE);
                */
                    } else {
                        JOptionPane.showMessageDialog(this, "Please input proper registration date", "Set Date", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsString = sw.toString();
                    System.out.println(exceptionAsString);

                    JOptionPane.showMessageDialog(this, "Saving Error!", exceptionAsString, JOptionPane.ERROR_MESSAGE);
                }
            }

            parentForm.populateTable();
        } catch (Exception e) {
            e.getStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);

            JOptionPane.showMessageDialog(this, "Saving Error!", exceptionAsString, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void txtTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTypeActionPerformed

    private void txtTypeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTypeFocusLost
        // TODO add your handling code here:
        if (txtType.getText().equals(""))
        {
            txtType.setText("Enter vehicle type");
            txtType.setForeground(new Color(153, 153, 153));
        }
         
    }//GEN-LAST:event_txtTypeFocusLost

    private void txtTypeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTypeFocusGained
        // TODO add your handling code here:
        if (txtType.getText().equals("Enter vehicle type"))
        {
            txtType.setText("");
            txtType.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtTypeFocusGained

    private void txtDescriptionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescriptionFocusGained
        // TODO add your handling code here:
        if (txtDescription.getText().equals("Enter vehicle description"))
        {
            txtDescription.setText("");
            txtDescription.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtDescriptionFocusGained

    private void txtDescriptionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescriptionFocusLost
        // TODO add your handling code here:
        if (txtDescription.getText().equals(""))
        {
            txtDescription.setText("Enter vehicle description");
            txtDescription.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtDescriptionFocusLost

    private void txtMakeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMakeFocusGained
        // TODO add your handling code here:
        if (txtMake.getText().equals("Enter vehicle maker"))
        {
            txtMake.setText("");
            txtMake.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtMakeFocusGained

    private void txtMakeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMakeFocusLost
        // TODO add your handling code here:
        if (txtMake.getText().equals(""))
        {
            txtMake.setText("Enter vehicle maker");
            txtMake.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtMakeFocusLost

    private void txtModelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtModelFocusGained
        // TODO add your handling code here:
        if (txtModel.getText().equals("Enter vehicle model"))
        {
            txtModel.setText("");
            txtModel.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtModelFocusGained

    private void txtModelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtModelFocusLost
        // TODO add your handling code here:
        if (txtModel.getText().equals(""))
        {
            txtModel.setText("Enter vehicle model");
            txtModel.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtModelFocusLost

    private void txtVersionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVersionFocusGained
        // TODO add your handling code here:
        if (txtVersion.getText().equals("Enter vehicle version"))
        {
            txtVersion.setText("");
            txtVersion.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtVersionFocusGained

    private void txtVersionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVersionFocusLost
        // TODO add your handling code here:
        if (txtVersion.getText().equals(""))
        {
            txtVersion.setText("Enter vehicle version");
            txtVersion.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtVersionFocusLost

    private void txtYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtYearFocusGained
        // TODO add your handling code here:
        if (txtYear.getText().equals("Enter vehicle year made"))
        {
            txtYear.setText("");
            txtYear.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtYearFocusGained

    private void txtYearFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtYearFocusLost
        // TODO add your handling code here:
        if (txtYear.getText().equals(""))
        {
            txtYear.setText("Enter vehicle year made");
            txtYear.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtYearFocusLost

    private void txtPlateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlateFocusGained
        // TODO add your handling code here:
        if (txtPlate.getText().equals("Enter vehicle plate number (e.g. ABC1234)"))
        {
            txtPlate.setText("");
            txtPlate.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtPlateFocusGained

    private void txtPlateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlateFocusLost
        // TODO add your handling code here:
        if (txtPlate.getText().equals(""))
        {
            txtPlate.setText("Enter vehicle plate number (e.g. ABC1234)");
            txtPlate.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtPlateFocusLost

    private void txtEngineFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEngineFocusGained
        // TODO add your handling code here:
        if (txtEngine.getText().equals("Enter vehicle engine number"))
        {
            txtEngine.setText("");
            txtEngine.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtEngineFocusGained

    private void txtEngineFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEngineFocusLost
        // TODO add your handling code here:
        if (txtEngine.getText().equals(""))
        {
            txtEngine.setText("Enter vehicle engine number");
            txtEngine.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtEngineFocusLost

    private void txtChassisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChassisFocusGained
        // TODO add your handling code here:
        if (txtChassis.getText().equals("Enter vehicle chassis number"))
        {
            txtChassis.setText("");
            txtChassis.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtChassisFocusGained

    private void txtChassisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChassisFocusLost
        // TODO add your handling code here:
        if (txtChassis.getText().equals(""))
        {
            txtChassis.setText("Enter vehicle chassis number");
            txtChassis.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtChassisFocusLost

    private void txtCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostFocusGained
        // TODO add your handling code here:
        if (txtCost.getText().equals("Enter vehicle cost (e.g. 500000)"))
        {
            txtCost.setText("");
            txtCost.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtCostFocusGained

    private void txtCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCostFocusLost
        // TODO add your handling code here:
        if (txtCost.getText().equals(""))
        {
            txtCost.setText("Enter vehicle cost (e.g. 500000)");
            txtCost.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtCostFocusLost

    private void txtRemarksFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRemarksFocusGained
        // TODO add your handling code here:
        if (txtRemarks.getText().equals("Enter remarks (Optional)"))
        {
            txtRemarks.setText("");
            txtRemarks.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtRemarksFocusGained

    private void txtRemarksFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRemarksFocusLost
        // TODO add your handling code here:
        if (txtRemarks.getText().equals(""))
        {
            txtRemarks.setText("Enter remarks (Optional)");
            txtRemarks.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtRemarksFocusLost

    private void txtMakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMakeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMakeActionPerformed

    private void txtModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModelActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_txtModelActionPerformed

    private void txtYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtYearActionPerformed

    public void imageCredits() throws FileNotFoundException, IOException {
        image_attribution image1 = new image_attribution("save_32", "https://www.freepik.com/icon/diskette_469319#position=26&page=5&term=save&fromView=search", new FileInputStream("save_32.png"));
        image1.attributes.put("author", "Freepik");
        System.out.println(image1.getAttribution());

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
            java.util.logging.Logger.getLogger(vehicle_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vehicle_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vehicle_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vehicle_dialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                destination_dialog dialog = new destination_dialog(new javax.swing.JFrame(), true);
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
    private com.toedter.calendar.JDateChooser RegDate;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbFuel;
    private javax.swing.JButton cmdBack;
    private javax.swing.JButton cmdSave;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtChassis;
    private javax.swing.JFormattedTextField txtCost;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtEngine;
    private javax.swing.JTextField txtMake;
    private javax.swing.JTextField txtModel;
    private javax.swing.JTextField txtPlate;
    private javax.swing.JTextArea txtRemarks;
    private javax.swing.JTextField txtType;
    private javax.swing.JTextField txtVersion;
    private javax.swing.JFormattedTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
