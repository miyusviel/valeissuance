package User;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Connection.DBConn;
import Others.UserContext;
import Others.main_menu;
import Others.functions;
import Others.image_attribution;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class login extends javax.swing.JFrame {

    static Statement stmt;
    //public static mdi frmmdi;

    public login() {
        initComponents();
        try {
            imageCredits();
        } catch (IOException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLocationRelativeTo(this);
        getRootPane().setDefaultButton(cmdlogin);
        // this.setSize(548, 342);
        // lblsys.setText(myFunction.getSystemInfo());
    }

    boolean IsValidUser(String uname, String pword) {
        boolean found = false;

        Connection conn = DBConn.getConnection();
        String createString;
        createString = "SELECT * FROM user WHERE username='" + uname + "' AND password='" + pword + "'";

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

    int GetUserID(String uname, String pword) {
        int UserID = 0;

        Connection conn = DBConn.getConnection();
        String createString;
        createString = "SELECT user_id FROM user WHERE username='" + uname + "' AND password='" + pword + "'";

        //int rc = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(createString);

            while (rs.next()) {
                UserID = rs.getInt(1);
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.getStackTrace();
        }

        return UserID;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtuname = new javax.swing.JTextField();
        txtpword = new javax.swing.JPasswordField();
        cmdlogin = new javax.swing.JButton();
        cmdexit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VFSI - User Login");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Username");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Password");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/VFIS Logo.gif"))); // NOI18N
        jLabel4.setToolTipText("");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 204));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Version 0.1 7-13-2023");

        txtuname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtuname.setForeground(new java.awt.Color(51, 51, 51));
        txtuname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtunameFocusGained(evt);
            }
        });

        txtpword.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtpword.setForeground(new java.awt.Color(51, 51, 51));
        txtpword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtpwordFocusGained(evt);
            }
        });

        cmdlogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login_32.png"))); // NOI18N
        cmdlogin.setMnemonic('L');
        cmdlogin.setText("Login");
        cmdlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdloginActionPerformed(evt);
            }
        });

        cmdexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout_32.png"))); // NOI18N
        cmdexit.setMnemonic('x');
        cmdexit.setText("Exit");
        cmdexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdexitActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setText("Welcome Back!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtuname)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpword)
                            .addComponent(cmdlogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmdexit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(16, 16, 16)))))
                .addGap(67, 67, 67)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel3)
                .addGap(39, 39, 39)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtuname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpword, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(cmdlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdexit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        txtuname.getAccessibleContext().setAccessibleName("");
        txtpword.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        txtuname.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void cmdloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdloginActionPerformed
        String uname = txtuname.getText();
        String pword = txtpword.getText();

        String md5pword = functions.convertMD5(pword);

        boolean val = IsValidUser(uname, md5pword); //VALIDATE USER IF VALID

        if (val == false) {
            JOptionPane.showMessageDialog(this, "Access Denied! Unauthorized User.");
            txtuname.requestFocus();
            return;
        } else if (val == true) {

            //GET USER ID
            int uid = GetUserID(uname, md5pword);

            //Pass UID to Functions Class
            functions fuid = new functions();
            fuid.setUserID(uid);

            //To retain user ID everywhere
            
            Integer userId = uid; // Retrieved from login process
            UserContext.getInstance().setUserId(userId);
            
            
            this.dispose();
            try {
                try {
                    try {
                        JFrame frame;
                        frame = (JFrame) Class.forName("Others.main_menu").newInstance();
                        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("ico.png"));
                        frame.setVisible(true);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (java.lang.ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }//GEN-LAST:event_cmdloginActionPerformed

    private void cmdexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdexitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_cmdexitActionPerformed

    private void txtpwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtpwordFocusGained
        txtpword.selectAll();
    }//GEN-LAST:event_txtpwordFocusGained

    private void txtunameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtunameFocusGained
        txtuname.selectAll();
    }//GEN-LAST:event_txtunameFocusGained

    public void imageCredits() throws FileNotFoundException, IOException {
        //image_attribution image1 = new image_attribution("login_32", "C:\\JavaProjects\\ValeIssuance\\src\\images", new FileInputStream("/images/login_32.png"));
        //image1.attributes.put("author", "VectorPortal");
        FileInputStream fis = new FileInputStream("C:\\JavaProjects\\ValeIssuance\\src\\images\\login_32.png");
        image_attribution image1 = new image_attribution("login_32", "C:\\JavaProjects\\ValeIssuance\\src\\images", fis);
        //System.out.println(image1.getAttribution());

        //image_attribution image2 = new image_attribution("logout_32", "https://www.freepik.com/icon/logout_4400828#position=41&page=1&term=exit&fromView=search", new FileInputStream("images/logout_32.png"));
        //image2.attributes.put("author", "Freepik");
        FileInputStream fis2 = new FileInputStream("C:\\JavaProjects\\ValeIssuance\\src\\images\\logout_32.png");
        image_attribution image2 = new image_attribution("logout_32", "C:\\JavaProjects\\ValeIssuance\\src\\images", fis2);
        //System.out.println(image2.getAttribution());
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdexit;
    private javax.swing.JButton cmdlogin;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPasswordField txtpword;
    private javax.swing.JTextField txtuname;
    // End of variables declaration//GEN-END:variables
}
