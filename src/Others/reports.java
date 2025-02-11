package Others;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Connection.DBConn;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class reports {
 public static void rptFuelAccountPayable(String startdate, String enddate,String user) {
    //by Rey D. Repe
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            //parameters.put("batchID", param);
            parameters.put("startdate", startdate);
            parameters.put("enddate", enddate);
            parameters.put("user", user);
            
            //JOptionPane.showMessageDialog(null,user);

            jasperReport = JasperCompileManager.compileReport(".//rpt/FuelOilAccountsPayable.jrxml"); //".\\rpt/rptVolumeByDriver2.jrxml"

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JRViewer aViewer = new JRViewer(jPrint);
            JDialog aFrame = new JDialog(new javax.swing.JFrame(), true);
            aFrame.getContentPane().add(aViewer);
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            aFrame.setSize(screenSize.width, screenSize.height);
            java.awt.Insets insets = aFrame.getInsets();
            aFrame.setSize(aFrame.getWidth() + insets.left + insets.right, aFrame.getHeight() + insets.top + insets.bottom + 20);
            aFrame.setLocation((screenSize.width - aFrame.getWidth()) / 2, (screenSize.height - aFrame.getHeight()) / 2);
            aFrame.setVisible(true);

            try {
                DBConn.getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(reports.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }
 
    public static void rptTopVolumebyVehicle(String startdate, String enddate) {
    //by Rey D. Repe
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            //parameters.put("batchID", param);
            parameters.put("startdate", startdate);
            parameters.put("enddate", enddate);
            
            //JOptionPane.showMessageDialog(null,startdate);

            jasperReport = JasperCompileManager.compileReport(".//rpt/rptTopVolumeByVehicle.jrxml"); //".\\rpt/rptVolumeByDriver2.jrxml"

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JRViewer aViewer = new JRViewer(jPrint);
            JDialog aFrame = new JDialog(new javax.swing.JFrame(), true);
            aFrame.getContentPane().add(aViewer);
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            aFrame.setSize(screenSize.width, screenSize.height);
            java.awt.Insets insets = aFrame.getInsets();
            aFrame.setSize(aFrame.getWidth() + insets.left + insets.right, aFrame.getHeight() + insets.top + insets.bottom + 20);
            aFrame.setLocation((screenSize.width - aFrame.getWidth()) / 2, (screenSize.height - aFrame.getHeight()) / 2);
            aFrame.setVisible(true);

            try {
                DBConn.getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(reports.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptVolumeByVehicle(String type) {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;
            String jrxmlPath;

            // Parameters
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("rpttype", type);

            if ("Vehicle".equals(type)) {
                jrxmlPath = ".\\rpt/rptVolumeByVehicle.jrxml";
            } else if ("Driver".equals(type)) {
                jrxmlPath = ".\\rpt/rptVolumeByDriver2.jrxml";
            } else if ("Supplier".equals(type)) {
                jrxmlPath = ".\\rpt/rptVolumeBySupplier.jrxml";
            } else if ("Account".equals(type)) {
                jrxmlPath = ".\\rpt/rptVolumeByAccount.jrxml";
            } else {
                // Handle any other cases or provide a default path
                jrxmlPath = ".\\rpt/rptVolumeByDriver2.jrxml";
            }

            try {
                jasperReport = JasperCompileManager.compileReport(jrxmlPath);
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, "Error loading the report template.");
                e.printStackTrace();
                return;
            }

            try {
                jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());
            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, "Error generating the report.");
                e.printStackTrace();
                return;
            }

            JasperViewer viewer = new JasperViewer(jPrint, false);
            viewer.setTitle("Volume by " + type + " Summary");
            viewer.setExtendedState(viewer.getExtendedState() | JasperViewer.MAXIMIZED_BOTH);
            viewer.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        // Close the database connection when the viewer is closed
                        DBConn.getConnection().close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            JRViewer aViewer = new JRViewer(jPrint);

            JDialog aFrame = new JDialog(new javax.swing.JFrame(), true);
            aFrame.getContentPane().add(aViewer);
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            aFrame.setSize(screenSize.width, screenSize.height);
            java.awt.Insets insets = aFrame.getInsets();
            aFrame.setSize(aFrame.getWidth() + insets.left + insets.right, aFrame.getHeight() + insets.top + insets.bottom + 20);
            aFrame.setLocation((screenSize.width - aFrame.getWidth()) / 2, (screenSize.height - aFrame.getHeight()) / 2);
            aFrame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    public static void rptVolumeByDriver(String type) {
//        try {
//            JasperReport jasperReport;
//            JasperPrint jPrint;
//
//            // Parameters
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("rpttype", type);
//
//            try {
//                jasperReport = JasperCompileManager.compileReport(".\\rpt/rptVolumeByDriver.jrxml");
//            } catch (JRException e) {
//                JOptionPane.showMessageDialog(null, "Error loading the report template.");
//                e.printStackTrace();
//                return;
//            }
//
//            try {
//                jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());
//            } catch (JRException e) {
//                JOptionPane.showMessageDialog(null, "Error generating the report.");
//                e.printStackTrace();
//                return;
//            }
//
//            JasperViewer viewer = new JasperViewer(jPrint, false);
//            viewer.setTitle("Volume by " + type + " Summary");
//            viewer.setExtendedState(viewer.getExtendedState() | JasperViewer.MAXIMIZED_BOTH);
//            viewer.setVisible(true);
//            viewer.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    try {
//                        // Close the database connection when the viewer is closed
//                        DBConn.getConnection().close();
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "An unexpected error occurred.");
//            e.printStackTrace();
//            
//        }
    }

}
