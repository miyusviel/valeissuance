/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vale;

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
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Connection.DBConn;
import Others.functions;
import Others.image_attribution;
import Others.query;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

public class vale_main extends javax.swing.JFrame {

    public static vale_dialog nextTransfrmparent;

    static DefaultTableCellRenderer cellAlignCenterRenderer = new DefaultTableCellRenderer();
    static DefaultTableCellRenderer cellAlignRightRenderer = new DefaultTableCellRenderer();
    static DefaultTableModel Tablemodel;
    static Statement stmt;

    public vale_main() {
        initComponents();

        Date currentDate = new Date(); // Gets the current date and time
        startdateTrans.setDate(currentDate);  //init date chooser to current date
        enddateTrans.setDate(currentDate);
        populateTable();
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);

        try {
            imageCredits();
        } catch (IOException ex) {
            Logger.getLogger(vale_main.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLocationRelativeTo(this);

        jScrollPane1.setViewportView(tblMain);
        tblMain.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        tblMain.setRowSelectionAllowed(true);
        tblMain.setColumnSelectionAllowed(false);
        tblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private void setHorizontalAlignment(int RIGHT) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    class CustomTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Get the value of the "Status" column for the current row
            String status = table.getValueAt(row, 16).toString();
            setHorizontalAlignment(SwingConstants.CENTER);  //set table content alignment

            // Set the background color based on the "Status" value
            switch (status) {
                case "Pending" ->
                    component.setBackground(Color.YELLOW);
                case "Completed" ->
                    component.setBackground(Color.lightGray);
                case "Cancelled" ->
                    component.setBackground(Color.RED);
                case "Reopened" ->
                    component.setBackground(Color.ORANGE);
                default ->
                    component.setBackground(table.getBackground());
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
                "SELECT t.trans_id, t.vale_no, t.type, a.title, v.description as vehicle, f.description as fueltype, s.name as gas_supplier, d.townname as destination, "
                + "(SELECT u.full_name FROM user where user_id = t.createdby) AS creator, t.createddate, "
                + "DATE_ADD(t.createddate, INTERVAL 7 DAY) AS validuntil, "
                //+ "(SELECT CONCAT(last_name, ' ', first_name) FROM employee WHERE position_id = t.approvedby ORDER BY emp_id LIMIT 1) AS approvedby, "
                + "t.liter_price as price, " //modified by Rey Repe 8.20.2024 
                + "t.trans_amt, " //modified by Rey Repe 8.20.2024
                //+ "t.approveddate, " //modified by Rey Repe 8.22.2024
                + "t.volrequested, t.volgiven, ds.doc_status_desc as doc_stat, CONCAT(e.last_name, ' ', e.first_name) as full_name, t.ornumber " //, ' ', e.first_name
                + "FROM transaction t "
                + "INNER JOIN vehicle v ON v.vehicle_id = t.vehicle_id "
                + "INNER JOIN fuel f ON f.fuel_id = t.fuel_id "
                + "INNER JOIN accounts a ON a.acct_id = t.acct_id "
                + "INNER JOIN employee e ON e.emp_id = t.emp_id "
                + "INNER JOIN suppliers s ON s.supplier_id = t.supplier_id "
                + "INNER JOIN destination d ON d.town_id = t.town_id "
                + "INNER JOIN user u ON u.user_id = t.user_id "
                + "INNER JOIN document_status ds ON ds.doc_status_id = t.status_id "
                + "WHERE t.vale_no LIKE ? OR ds.doc_status_desc LIKE ?" //OR t.type LIKE ? OR a.title LIKE ? OR v.description LIKE ? OR f.description LIKE ? OR s.name LIKE ? "
                //+ "OR d.townname LIKE ? OR ds.doc_status_desc LIKE ? OR t.approvedby LIKE ? OR u.full_name LIKE ? "
                + "OR t.createddate LIKE ? "
                + "ORDER BY t.trans_id")) {
//JOptionPane.showMessageDialog(this, formattedStartDate + "");

            pstmt.setString(1, "%" + txtSearch.getText() + "");   // where parameters
            pstmt.setString(2, "%" + txtSearch.getText() + "%");
            pstmt.setString(3, "%" + txtSearch.getText() + "%");
//            pstmt.setString(4, "%" + txtSearch.getText() + "%");
//            pstmt.setString(5, "%" + txtSearch.getText() + "%");
//            pstmt.setString(6, "%" + txtSearch.getText() + "%");
//            pstmt.setString(7, "%" + txtSearch.getText() + "%");
//            pstmt.setString(8, "%" + txtSearch.getText() + "%");
//            pstmt.setString(9, "%" + txtSearch.getText() + "%");
//            pstmt.setString(10, "%" + txtSearch.getText() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                //System.out.println(rs);  // check result set output
                Tablemodel = (DefaultTableModel) tblMain.getModel();
                Tablemodel.setRowCount(0);

                cellAlignCenterRenderer.setHorizontalAlignment(0);
                cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

                tblMain.setRowHeight(28);

                // Hide the TransID column
//                TableColumn hiddenColumn = tblMain.getColumnModel().getColumn(0);   //1st table column
//                hiddenColumn.setMinWidth(0);
//                hiddenColumn.setMaxWidth(0);
//                hiddenColumn.setPreferredWidth(0);
                hideColumns(tblMain, new int[]{0, 18});  // Hide columns transID and OR Number"

                tblMain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tblMain.getColumnModel().getColumn(1).setPreferredWidth(40); //c1  
                tblMain.getColumnModel().getColumn(2).setPreferredWidth(40); //c2
                tblMain.getColumnModel().getColumn(3).setPreferredWidth(50); //type //c3
                tblMain.getColumnModel().getColumn(4).setPreferredWidth(150); //c4
                tblMain.getColumnModel().getColumn(5).setPreferredWidth(100); //c5
                tblMain.getColumnModel().getColumn(6).setPreferredWidth(50); //c6
                tblMain.getColumnModel().getColumn(7).setPreferredWidth(100); //c7
                tblMain.getColumnModel().getColumn(8).setPreferredWidth(100); //destination //c8
                tblMain.getColumnModel().getColumn(9).setPreferredWidth(50); //c9
                tblMain.getColumnModel().getColumn(10).setPreferredWidth(60); //10
                tblMain.getColumnModel().getColumn(11).setPreferredWidth(60); //c11
                tblMain.getColumnModel().getColumn(12).setPreferredWidth(60); //c12
                tblMain.getColumnModel().getColumn(13).setPreferredWidth(50); //status //c13
                tblMain.getColumnModel().getColumn(14).setPreferredWidth(50); //c14
                tblMain.getColumnModel().getColumn(15).setPreferredWidth(50); //c15
                int cnt = 0;

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    int transId = rs.getInt("trans_id"); // TransID reference   

                    String formattedPrice = decimalFormat.format(rs.getDouble("price"));
                    String formattedTransAmt = decimalFormat.format(rs.getDouble("trans_amt"));

                    Object[] rowData = {
                        transId, //c1
                        cnt + 1, //c1
                        rs.getString("vale_no"), //c2
                        rs.getString("type"), //c3    vale type
                        rs.getString("title"), //c4   account type  
                        rs.getString("vehicle"), //c5   
                        rs.getString("fueltype"), //c6
                        rs.getString("gas_supplier"), //c7
                        rs.getString("destination"), //c8
                        rs.getString("creator"), //c9
                        rs.getDate("createddate"), //c10
                        rs.getDate("validuntil"), //c11
                        //rs.getDouble("price"),
                        formattedPrice,
                        //rs.getDouble("trans_amt"),
                        formattedTransAmt,
                        //rs.getDate("approveddate"),
                        rs.getInt("volrequested"),
                        rs.getInt("volgiven"),
                        rs.getString("doc_stat"),
                        //rs.getString("driver"),
                        rs.getString("full_name"),
                        rs.getString("ornumber")
                    };

                    Tablemodel.addRow(rowData);
                    cnt++;
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void populateTable2() {
        Date startDateUtil = startdateTrans.getDate();
        Date endDateUtil = enddateTrans.getDate();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the java.util.Date objects to String in "yyyy-MM-dd" format
        String formattedStartDate = dateFormat.format(startDateUtil);
        String formattedEndDate = dateFormat.format(endDateUtil);

        try (Connection conn = DBConn.getConnection(); PreparedStatement pstmt = conn.prepareStatement(
                "SELECT t.trans_id, t.vale_no, t.type, a.title, v.description as vehicle, f.description as fueltype, s.name as gas_supplier, d.townname as destination, "
                + "(SELECT u.full_name FROM user where user_id = t.createdby) AS creator, t.createddate, "
                + "DATE_ADD(t.createddate, INTERVAL 7 DAY) AS validuntil, "
                + "t.liter_price as price, "
                + "t.trans_amt, "
                + "t.volrequested, t.volgiven, ds.doc_status_desc as doc_stat, CONCAT(e.last_name, ' ', e.first_name) as full_name, t.ornumber " //, ' ', e.first_name
                + "FROM transaction t "
                + "INNER JOIN vehicle v ON v.vehicle_id = t.vehicle_id "
                + "INNER JOIN fuel f ON f.fuel_id = t.fuel_id "
                + "INNER JOIN accounts a ON a.acct_id = t.acct_id "
                + "INNER JOIN employee e ON e.emp_id = t.emp_id "
                + "INNER JOIN suppliers s ON s.supplier_id = t.supplier_id "
                + "INNER JOIN destination d ON d.town_id = t.town_id "
                + "INNER JOIN user u ON u.user_id = t.user_id "
                + "INNER JOIN document_status ds ON ds.doc_status_id = t.status_id "
                + "WHERE t.createddate >= ? AND t.createddate <= ?"
                + "ORDER BY t.trans_id")) {

            pstmt.setString(1, "" + formattedStartDate + "");
            pstmt.setString(2, "" + formattedEndDate + "");

            try (ResultSet rs = pstmt.executeQuery()) {
                //System.out.println(rs);  // check result set output
                Tablemodel = (DefaultTableModel) tblMain.getModel();
                Tablemodel.setRowCount(0);

                cellAlignCenterRenderer.setHorizontalAlignment(0);
                cellAlignRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

                tblMain.setRowHeight(28);

                // Hide the TransID column
//                TableColumn hiddenColumn = tblMain.getColumnModel().getColumn(0);   //1st table column
//                hiddenColumn.setMinWidth(0);
//                hiddenColumn.setMaxWidth(0);
//                hiddenColumn.setPreferredWidth(0);
                hideColumns(tblMain, new int[]{0, 18});  // Hide columns transID and OR Number"

                tblMain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tblMain.getColumnModel().getColumn(1).setPreferredWidth(40); //c1  
                tblMain.getColumnModel().getColumn(2).setPreferredWidth(40); //c2
                tblMain.getColumnModel().getColumn(3).setPreferredWidth(50); //type //c3
                tblMain.getColumnModel().getColumn(4).setPreferredWidth(150); //c4
                tblMain.getColumnModel().getColumn(5).setPreferredWidth(100); //c5
                tblMain.getColumnModel().getColumn(6).setPreferredWidth(50); //c6
                tblMain.getColumnModel().getColumn(7).setPreferredWidth(100); //c7
                tblMain.getColumnModel().getColumn(8).setPreferredWidth(100); //destination //c8
                tblMain.getColumnModel().getColumn(9).setPreferredWidth(50); //c9
                tblMain.getColumnModel().getColumn(10).setPreferredWidth(60); //10
                tblMain.getColumnModel().getColumn(11).setPreferredWidth(60); //c11
                tblMain.getColumnModel().getColumn(12).setPreferredWidth(60); //c12
                tblMain.getColumnModel().getColumn(13).setPreferredWidth(50); //status //c13
                tblMain.getColumnModel().getColumn(14).setPreferredWidth(50); //c14
                tblMain.getColumnModel().getColumn(15).setPreferredWidth(50); //c15
                int cnt = 0;

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    int transId = rs.getInt("trans_id"); // TransID reference   

                    String formattedPrice = decimalFormat.format(rs.getDouble("price"));
                    String formattedTransAmt = decimalFormat.format(rs.getDouble("trans_amt"));

                    Object[] rowData = {
                        transId, //c1
                        cnt + 1, //c1
                        rs.getString("vale_no"), //c2
                        rs.getString("type"), //c3    vale type
                        rs.getString("title"), //c4   account type  
                        rs.getString("vehicle"), //c5   
                        rs.getString("fueltype"), //c6
                        rs.getString("gas_supplier"), //c7
                        rs.getString("destination"), //c8
                        rs.getString("creator"), //c9
                        rs.getDate("createddate"), //c10
                        rs.getDate("validuntil"), //c11
                        //rs.getDouble("price"),
                        formattedPrice,
                        //rs.getDouble("trans_amt"),
                        formattedTransAmt,
                        //rs.getDate("approveddate"),
                        rs.getInt("volrequested"),
                        rs.getInt("volgiven"),
                        rs.getString("doc_stat"),
                        //rs.getString("driver"),
                        rs.getString("full_name"),
                        rs.getString("ornumber")
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

        nextTransfrmparent = new vale_dialog(this, true);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMain = new javax.swing.JTable();
        cmdNew = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();
        cmdReopen = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new org.jdesktop.swingx.JXSearchField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        startdateTrans = new com.toedter.calendar.JDateChooser();
        enddateTrans = new com.toedter.calendar.JDateChooser();
        ReloadtableBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setFrameIcon(null);
        jInternalFrame1.setVisible(true);

        tblMain.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        tblMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TransID", "Rec#", "Vale#", "Type", "Account", "Vehicle", "Fuel Type", "Supplier", "Destination", "Created By", "Created", "Validity", "Price/L", "Amount", "Requested", "Fuel released", "Status", "Driver", "OR Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMain.setToolTipText("Double click row to view vale details");
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

        cmdNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new_32.png"))); // NOI18N
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

        cmdReopen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reopen_32.png"))); // NOI18N
        cmdReopen.setMnemonic('R');
        cmdReopen.setText("Reopen");
        cmdReopen.setEnabled(false);
        cmdReopen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReopenActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSearch.setBackground(new java.awt.Color(255, 255, 204));
        txtSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Search Vale Number");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        startdateTrans.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        startdateTrans.setDateFormatString("MM/dd/yyyy");

        enddateTrans.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        enddateTrans.setDateFormatString("MM/dd/yyyy");

        ReloadtableBtn.setText("Date Range View ");
        ReloadtableBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadtableBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 102));
        jLabel2.setText("Date Range Table View Option");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(enddateTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startdateTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ReloadtableBtn))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(startdateTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enddateTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(ReloadtableBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1577, Short.MAX_VALUE)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(cmdNew, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmdEdit)
                                .addGap(18, 18, 18)
                                .addComponent(cmdReopen, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(122, 122, 122)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdReopen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmdNew)
                                .addComponent(cmdEdit)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        populateTable();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
        nextTransfrmparent = new vale_dialog(this, true);
        nextTransfrmparent.cmdNewClicked();
        nextTransfrmparent.setVisible(true);
    }//GEN-LAST:event_cmdNewActionPerformed

    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
        openUpdateDialog();
    }//GEN-LAST:event_cmdEditActionPerformed

    private void tblMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMainMouseClicked
        functions fRow = new functions();

        int row = tblMain.getSelectedRow();
        int transId = (int) tblMain.getValueAt(row, 0);
        String stat = (String) tblMain.getValueAt(row, 16);

        fRow.setRowID(row);
        fRow.setTransID(transId);

        if ("Cancelled".equals(stat)) {
            cmdReopen.setEnabled(true);
        } else {
            cmdReopen.setEnabled(false);
        }

        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            openUpdateDialog();
        }
    }//GEN-LAST:event_tblMainMouseClicked

    private void tblMainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMainKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            openUpdateDialog();
        }
    }//GEN-LAST:event_tblMainKeyPressed

    private void cmdReopenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdReopenActionPerformed
        // TODO add your handling code here:
        functions fRow = new functions();

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to reopen this transaction?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            query.reopen_trans(fRow.getTransID(), 34);
        }

        populateTable();
    }//GEN-LAST:event_cmdReopenActionPerformed

    private void ReloadtableBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadtableBtnActionPerformed

        //JOptionPane.showMessageDialog(this, "reload button");
        populateTable2();// TODO add your handling code here:
    }//GEN-LAST:event_ReloadtableBtnActionPerformed

    public void imageCredits() throws FileNotFoundException, IOException {
        image_attribution image1 = new image_attribution("new_32", "https://www.freepik.com/icon/new-layer_9692606#position=13&term=new&fromView=search", new FileInputStream("new_32.png"));
        image1.attributes.put("author", "alfanz");
        System.out.println(image1.getAttribution());

        image_attribution image2 = new image_attribution("edit_32", "https://www.freepik.com/icon/pencil_1300613", new FileInputStream("edit_32.png"));
        image2.attributes.put("author", "Freepik");
        System.out.println(image2.getAttribution());

        image_attribution image3 = new image_attribution("reopen_32", "https://www.freepik.com/icon/box_6906204#position=14&term=reopen&fromView=search", new FileInputStream("reopen_32.png"));
        image3.attributes.put("author", "Freepik");
        System.out.println(image3.getAttribution());
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
    private javax.swing.JButton ReloadtableBtn;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdReopen;
    private com.toedter.calendar.JDateChooser enddateTrans;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser startdateTrans;
    private javax.swing.JTable tblMain;
    private org.jdesktop.swingx.JXSearchField txtSearch;
    // End of variables declaration//GEN-END:variables
}
