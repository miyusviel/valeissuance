package Others;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Connection.DBConn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class reports {

    public static void rptBoot() throws FileNotFoundException, IOException {
               try {
            JasperReport jasperReport;

            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();

            jasperReport = JasperCompileManager.compileReport("rpt/rptboot.jrxml");

          
          jPrint= JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());
            //JasperExportManager.exportReportToPdf(jPrint);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
    }

    public static void rptLeaveApp(String leave_type, int id, String dept, String shift, String dm, String gm, double credits, double bal) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("leavetype", leave_type);
            parameters.put("id", id);
            parameters.put("dept", dept);
            parameters.put("shift", shift);
            parameters.put("dm", dm);
            parameters.put("gm", gm);
            parameters.put("credits", credits);
            parameters.put("bal", bal);

            jasperReport = JasperCompileManager.compileReport("rpt/rptLeaveApp.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Leave Application Report");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
//            OutputStream output = new FileOutputStream(new File("rpt/rpt0001.pdf"));
//            JasperExportManager.exportReportToPdfStream(jPrint, output);
//
//            output.flush();
//            output.close();
//
//            try {
//                File pdfFile = new File("rpt/rpt0001.pdf");
//                if (pdfFile.exists()) {
//
//                    if (Desktop.isDesktopSupported()) {
//                        Desktop.getDesktop().open(pdfFile);
//                    } else {
//                    }
//                } else {
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }//

    public static void rptOTApp(int otaid, String pos, String rid, String rpos) {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("id", otaid);
            parameters.put("pos", pos);
            parameters.put("rname", rid);
            parameters.put("rpos", rpos);

            jasperReport = JasperCompileManager.compileReport("rpt/overtime/rptOvertime.jrxml");

        jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Overtime Authorization");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptPayslip(int ppid, String desc) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid", ppid);
            parameters.put("desc", desc);

            jasperReport = JasperCompileManager.compileReport("rpt/payslip/rptPayslip.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Employees Payslip");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
//            OutputStream output = new FileOutputStream(new File("rpt/rpt0001.pdf"));
//            JasperExportManager.exportReportToPdfStream(jPrint, output);
//
//            output.flush();
//            output.close();
//
//            try {
//                File pdfFile = new File("rpt/rpt0001.pdf");
//                if (pdfFile.exists()) {
//
//                    if (Desktop.isDesktopSupported()) {
//                        Desktop.getDesktop().open(pdfFile);
//                    } else {
//                    }
//                } else {
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }//

    public static void rptPaySum(int ppid, String desc) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid", ppid);
            parameters.put("desc", desc);

            jasperReport = JasperCompileManager.compileReport("rpt/paysum/rptPaySummary.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Employees Payroll Summary");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
//            OutputStream output = new FileOutputStream(new File("rpt/rpt0001.pdf"));
//            JasperExportManager.exportReportToPdfStream(jPrint, output);
//
//            output.flush();
//            output.close();
//
//            try {
//                File pdfFile = new File("rpt/rpt0001.pdf");
//                if (pdfFile.exists()) {
//
//                    if (Desktop.isDesktopSupported()) {
//                        Desktop.getDesktop().open(pdfFile);
//                    } else {
//                    }
//                } else {
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }//

    public static void rptLogFail(int flaid) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("id", flaid);

            jasperReport = JasperCompileManager.compileReport("rpt/logfail/rptLogFail.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Failure to Log");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptEmpField(int efaid) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("id", efaid);

            jasperReport = JasperCompileManager.compileReport("rpt/fieldemp/rptFieldEmp.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Employees Field");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptLeaveCreditBal(int ppid, String usr, String pos, String asof) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("id", ppid);
            parameters.put("user", usr);
            parameters.put("pos", pos);
            parameters.put("asof", asof);

            jasperReport = JasperCompileManager.compileReport("rpt/leavebal/rptLeaveCreditBal.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Leave Credit Balance");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptDtr(int efaid, String nym, String period, String deptname) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("id", efaid);
            parameters.put("empname", nym);
            parameters.put("period", period);
            parameters.put("deptname", deptname);

            jasperReport = JasperCompileManager.compileReport("rpt/dtr/rptDTR.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Daily Time Record Report");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void main(String[] args) {
        try {
            rptDtrAllReg3(58, 3, 2);
        } catch (IOException ex) {
            Logger.getLogger(reports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void rptDtr2Indi(int eid, int ppid, String nym, String dept) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("eid", eid);
            parameters.put("ppid", ppid);
            parameters.put("nym", nym);
            parameters.put("dept", dept);

            jasperReport = JasperCompileManager.compileReport("rpt/dtr/rptDTR2Indi.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Daily Time Record Report");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

    }

    public static void rptDtrAllReg(int ppid, int etype, int eto, int did) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid", ppid);
            parameters.put("etype", etype);
            parameters.put("eto", eto);
            parameters.put("did", did);

            jasperReport = JasperCompileManager.compileReport("rpt/dtr/rptDTRAll.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Daily Time Record Report");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptDtrAllReg3(int ppid, int etype, int eto) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid", ppid);
            parameters.put("etype", etype);
            parameters.put("eto", eto);

            jasperReport = JasperCompileManager.compileReport("rpt/dtr/rptDTRAll3.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            //JasperExportManager.exportReportToPdf(jPrint);
            Viewer.setTitle("Daily Time Record Report");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptSumCon(int startPpId, int endPpId, String desc) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid_start", startPpId);
            parameters.put("ppid_end", endPpId);
            parameters.put("desc", desc);

            jasperReport = JasperCompileManager.compileReport("rpt/sumcon/summaryOfContribution.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            Viewer.setTitle("Employees Contribution Summary");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        }

    }

    public static void rptSumAppLeave(String startDate, String endDate, String desc) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("START_DATE", startDate);
            parameters.put("END_DATE", endDate);
            parameters.put("DESC", desc);

            jasperReport = JasperCompileManager.compileReport("rpt/sumappleave/summaryOfAppliedLeave.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            Viewer.setTitle("Employees Applied Leave of Absence Summary");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
    }//

    public static void rptSumPerAtt(int ppId, String lbl) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("pp_id", ppId);
            parameters.put("DESC", lbl);

            jasperReport = JasperCompileManager.compileReport("rpt/sumperatt/summaryOfPerfectAttendance.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            Viewer.setTitle("Employees 100% Attendance Summary");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
    }

    public static void rptOTCalc(int pid, String period) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid", pid);
            parameters.put("period", period);

            jasperReport = JasperCompileManager.compileReport("rpt/calculate_hours/rptOTComputation.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            Viewer.setTitle("Overtime Detailed Computation");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
    }

    public static void rptUTCalc(int pid, String period) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid", pid);
            parameters.put("period", period);

            jasperReport = JasperCompileManager.compileReport("rpt/calculate_hours/rptUTComputation.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            Viewer.setTitle("Undertime Detailed Computation");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
    }

    public static void rptSummaryOTUT(int pid, String period) throws FileNotFoundException, IOException {
        try {
            JasperReport jasperReport;
            JasperPrint jPrint;

            //parameters
            HashMap parameters = new HashMap();
            parameters.put("ppid", pid);
            parameters.put("period", period);

            jasperReport = JasperCompileManager.compileReport("rpt/calculate_hours/rptOTUTSummary.jrxml");

            jPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConn.getConnection());

            JasperViewer Viewer = new JasperViewer(jPrint, false);
            Viewer.setTitle("Undertime and Overtime Calculation Summary");
            Viewer.setExtendedState(Viewer.getExtendedState() | Viewer.MAXIMIZED_BOTH);
            Viewer.setVisible(true);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
    }
}
