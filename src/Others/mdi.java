package Others;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import Connection.DBConn;
import Others.functions;
import User.login;
import User.sec_set;
//import mod.user.sec_set;

public final class mdi extends javax.swing.JFrame {

    public static int UGID, userid, gender;
    static Statement stmt;
    public static String fname;

    public mdi() {
        initComponents();
         this.setLocationRelativeTo(null);
//        setIcon();
        LoadStandardMenus(this);
        LoadMainMenus();
        showuser();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH); //set JFrame maximized
        

    }

//    void setIcon() {
//        ImageIcon img = new ImageIcon(getClass().getResource("/images/download.png"));
//        this.setIconImage(img.getImage());
//    }
    public static int getUserID() {
        return userid;
    }

    void GetFnameAndGender() {
        Connection conn = DBConn.getConnection();
        String createString;
        createString = "SELECT full_name, gender FROM user WHERE user_id=" + userid;

        //int rc = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(createString);

            while (rs.next()) {
                fname = rs.getString(1);
                gender = rs.getInt(2);
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    void showuser() {
        GetFnameAndGender();

        String uico = "";
        if (gender == 1) {
            uico = "/images/malemini.png";
        } else if (gender == 2) {
            uico = "/images/femalemini.png";
        }
        String lbl = "<html><table border=0 cellpadding=0 cellspacing=0>"
                + "<tr><td>&nbsp&nbsp<img src=" + getClass().getResource(uico) + ">&nbsp</td><td><font color=#FF9900>Welcome </font><font color=#585858>" + fname + "!</font></td>"
                + "<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img src=" + getClass().getResource("/images/logintime.png") + ">&nbsp</td><td><font color=#FF9900>Login Date/Time:&nbsp</font><font color=#585858>" + functions.getdateN() + "</font>&nbsp&nbsp<font color=#006600>" + functions.gettimeN() + "</font></td></th>";
        lbluser.setText(lbl);
    }

    void LoadStandardMenus(final JFrame frame) {
        mnubr.removeAll();

        //Load Basic Main Menus
        JMenu mnu = new JMenu();
        JMenuItem iLogOff = new JMenuItem();
        JMenuItem iExit = new JMenuItem();
        JMenuItem iSecSet = new JMenuItem();

        Icon ico1 = new javax.swing.ImageIcon(getClass().getResource("/images/system.png"));
        mnu.setText("SYSTEM");
        mnu.setIcon(ico1);
        mnu.setMnemonic('S');

        mnubr.add(mnu);

        Icon ico4 = new javax.swing.ImageIcon(getClass().getResource("/images/secset.png"));
        iSecSet.setIcon(ico4);
        iSecSet.setText("My User Account Settings");
        iSecSet.setMnemonic('y');

        Icon ico2 = new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"));
        iLogOff.setIcon(ico2);
        iLogOff.setText("Log-off");
        iLogOff.setMnemonic('L');

        Icon ico3 = new javax.swing.ImageIcon(getClass().getResource("/images/exitmain.png"));
        iExit.setText("Exit");
        iExit.setIcon(ico3);
        iExit.setMnemonic('x');

        //iExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
        mnu.add(iSecSet);
        mnu.add(iLogOff);
        mnu.add(iExit);
        //--Basic Menus End Here

        //Action Listener for basic menus
        iSecSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when Logoff button is pressed
                //frame.dispose();
                sec_set frm = new sec_set();
                sec_set.userid = userid;
                myDesktop.add(frm);
                frm.setVisible(true);

            }
        });

        iLogOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when Logoff button is pressed
                frame.dispose();

                login frm = new login();
                frm.setIconImage(Toolkit.getDefaultToolkit().getImage("ico.png"));
                frm.setVisible(true);
            }
        });

        iExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when Exit button is pressed
                System.exit(0);
            }
        });

        frame.setJMenuBar(mnubr);
        frame.pack();
    }

    void LoadMainMenus() {
        Connection conn = DBConn.getConnection();
        String createString;
        //QUERY - GROUP ALL MENU HEADER TO BE ABLE TO DISPLAY IT IN MDI FORM
        createString = "SELECT mi.menu_id, m.menu_caption, m.mnemonic, m.icon_path FROM user_group_assigned uga, "
                + "user_group_privilege ugp, user_privilege up,  "
                + "gui_menu_item mi,gui_menu m  "
                + "WHERE uga.grp_id=ugp.grp_id AND ugp.privilege_id=up.privilege_id  "
                + "AND mi.menu_item_id=up.menu_item_id AND m.menu_id=mi.menu_id AND uga.user_id=" + userid + " "
                + "GROUP BY mi.menu_id ORDER BY m.seq";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(createString);

            while (rs.next()) {
                String shrt = rs.getString(3);
                char monic = shrt.charAt(0);
                System.out.println(rs.getString(4));
                Icon ico = new javax.swing.ImageIcon(getClass().getResource(rs.getString(4)));

                JMenu mnu = new JMenu();
                mnu.setMnemonic(monic);
                mnu.setIcon(ico);
                mnu.setText(rs.getString(2));
                mnubr.add(mnu);

                LoadMenuItems(rs.getInt(1), mnu);
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
        }
    }

    void LoadMenuItems(int menuid, JMenu mnu) {
        Connection conn = DBConn.getConnection();
        String createString;
        //QUERY - LOAD MENUITEMS
        createString = "SELECT mi.menu_item_id, mi.menu_item_caption, mi.mnemonic, up.class_path, mi.icon_path, up.type "
                + "FROM user_group_assigned uga,user_group_privilege ugp, user_privilege up, gui_menu_item mi,gui_menu m "
                + "WHERE uga.grp_id=ugp.grp_id AND ugp.privilege_id=up.privilege_id  "
                + "AND mi.menu_item_id=up.menu_item_id AND m.menu_id=mi.menu_id AND uga.user_id=" + userid + " AND mi.menu_id=" + menuid + " "
                + "GROUP BY menu_item_id ORDER BY mi.seq ";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(createString);

            while (rs.next()) {
                System.out.println(rs.getString(5));
                Icon ico = new javax.swing.ImageIcon(getClass().getResource(rs.getString(5)));

                String shrt = rs.getString(3);
                char monic = shrt.charAt(0);

                JMenuItem mnuI = new JMenuItem();

                mnuI.setText(rs.getString(2));
                mnuI.setMnemonic(monic);
                mnuI.setIcon(ico);
                mnu.add(mnuI);

                //Action Listener for basic menus
                final String cls = rs.getString(4);
                final int disp = rs.getInt(6);

                mnuI.addActionListener(new ActionListener() {
                    //PUT ACTION ON MENUITEM CLICK
                    public void actionPerformed(ActionEvent e) {
                        //Execute when Logoff button is pressed

                        try {
                            try {
                                if (disp < 3) {
                                    JInternalFrame frame = (JInternalFrame) Class.forName(cls).newInstance();
                                    myDesktop.add(frame);
                                    if (disp == 1) {
                                        try {
                                            frame.setMaximum(true);
                                        } catch (PropertyVetoException ex) {
                                            Logger.getLogger(mdi.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    frame.setVisible(true);
                                } else {
                                    JFrame frame = (JFrame) Class.forName(cls).newInstance();
                                    frame.setVisible(true);
                                }
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(mdi.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InstantiationException ex) {
                                Logger.getLogger(mdi.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (java.lang.ClassNotFoundException se) {
                            JOptionPane.showMessageDialog(null, se.getMessage());
                        }

                    }
                });

            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        lbluser = new javax.swing.JLabel();
        myDesktop = new javax.swing.JDesktopPane();
        bck = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mnubr = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vale Fuel Issuance System - NORECO I");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jToolBar1.setRollover(true);

        lbluser.setText("Welcome!");
        jToolBar1.add(lbluser);

        myDesktop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bck.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout bckLayout = new javax.swing.GroupLayout(bck);
        bck.setLayout(bckLayout);
        bckLayout.setHorizontalGroup(
            bckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
        );
        bckLayout.setVerticalGroup(
            bckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        myDesktop.add(bck, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 310));
        myDesktop.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 840, 330));

        setJMenuBar(mnubr);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(myDesktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(myDesktop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        bck.setBounds(0, 0, myDesktop.getWidth(), myDesktop.getHeight());
        bck.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mdi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbluser;
    private javax.swing.JMenuBar mnubr;
    private javax.swing.JDesktopPane myDesktop;
    // End of variables declaration//GEN-END:variables
}
