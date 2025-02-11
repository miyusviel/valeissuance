package User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Connection.DBConn;
import Others.functions;

public class sec_set extends javax.swing.JInternalFrame {

    public static int userid;
    static Statement stmt;
    static String uname;

    public sec_set() {
        initComponents();
    }

    String GetUserName() {
        String UserID = "";

        Connection conn = DBConn.getConnection();
        String createString;
        createString = "SELECT username FROM user WHERE user_id=" + userid;


        //int rc = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(createString);

            while (rs.next()) {
                UserID = rs.getString(1);
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.getStackTrace();
        }

        return UserID;
    }

    boolean IsValidUser() {
        boolean found = false;

        Connection conn = DBConn.getConnection();
        String createString;
        createString = "SELECT * FROM user WHERE user_id=" + userid + " AND password='" + functions.convertMD5(txtcpword.getText()) + "' ";

        int rc = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(createString);

            while (rs.next()) {
                rc++;
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.getStackTrace();
        }

        if (rc > 0) {
            found = true;
        }

        return found;
    }

    public static void edit_uname(String desc, int id) {
        Connection conn = DBConn.getConnection();
        String createString;
        createString = "UPDATE user SET username='" + desc + "' "
                + "WHERE user_id=" + id;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createString);
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void edit_pword(String desc, int id) {
        Connection conn = DBConn.getConnection();
        String createString;
        createString = "UPDATE user SET password='" + desc + "' "
                + "WHERE user_id=" + id;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createString);
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmdcancel = new javax.swing.JButton();
        cmdsave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtuname = new javax.swing.JTextField();
        txtcpword = new javax.swing.JPasswordField();
        txtnpword = new javax.swing.JPasswordField();
        txtcnpword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("My User Account Settings");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/secset.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jLabel1.setText("Username:");

        cmdcancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        cmdcancel.setMnemonic('C');
        cmdcancel.setText("Cancel");
        cmdcancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdcancelActionPerformed(evt);
            }
        });

        cmdsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        cmdsave.setMnemonic('A');
        cmdsave.setText("Save");
        cmdsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdsaveActionPerformed(evt);
            }
        });

        txtuname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtcpword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtnpword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        txtcnpword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtcpword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(txtuname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnpword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcnpword))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtcpword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtuname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtnpword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcnpword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setText("Current Password:");

        jLabel3.setText("New Password:");

        jLabel4.setText("Confirm New Password:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdsave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdcancel))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 40, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdcancel)
                    .addComponent(cmdsave))
                .addGap(56, 56, 56))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdcancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdcancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_cmdcancelActionPerformed

    private void cmdsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdsaveActionPerformed
        String npword = txtnpword.getText();
        String cnpword = txtcnpword.getText();


        boolean isvalid = IsValidUser();
        if (isvalid == true) {
            if (npword.isEmpty() == false || cnpword.isEmpty() == false) {
                if (!npword.equals(cnpword)) {
                    JOptionPane.showMessageDialog(this, "Password Doesn't Match!");
                } else {
                    edit_pword(functions.convertMD5(npword), userid);
                    unameOpt();
                    this.dispose();
                    JOptionPane.showMessageDialog(this, "Changes successfully saved!");
                }
            } else {
                unameOpt();
                this.dispose();
                JOptionPane.showMessageDialog(this, "Changes successfully saved!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect Password!");
        }
    }//GEN-LAST:event_cmdsaveActionPerformed

    void unameOpt() {
        String unameval = txtuname.getText();
        if (!uname.equals(unameval)) {
            if (unameval.isEmpty() == true) {
                JOptionPane.showMessageDialog(this, "Username must not be empty!");
            } else {
                edit_uname(txtuname.getText(),userid);
            }
        }
    }

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        uname = GetUserName();
        txtuname.setText(uname);
    }//GEN-LAST:event_formInternalFrameOpened
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdcancel;
    private javax.swing.JButton cmdsave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtcnpword;
    private javax.swing.JPasswordField txtcpword;
    private javax.swing.JPasswordField txtnpword;
    private javax.swing.JTextField txtuname;
    // End of variables declaration//GEN-END:variables
}
