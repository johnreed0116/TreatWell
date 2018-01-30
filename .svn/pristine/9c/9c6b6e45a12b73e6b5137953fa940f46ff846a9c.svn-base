/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.report;

import com.alberta.model.Company;
import com.alberta.model.User;
import com.alberta.service.ServiceFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author farazahmad
 */
public class ReportController extends MultiActionController {

    private ServiceFactory serviceFactory;

    /**
     * @return the serviceFactory
     */
    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    /**
     * @param serviceFactory the serviceFactory to set
     */
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public void reportPerformaById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap map = new HashMap();
        Company comp = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String performaMasterId = request.getParameter("performaMasterId");
        ResultSet rs = serviceFactory.getReportService().getPerformaById(performaMasterId, comp.getCompanyId());
        if (rs != null && rs.next()) {
            long amnt = Long.parseLong(rs.getString("AMNT") != null ? rs.getString("AMNT") : "0");
            map.put("amntToWords", com.alberta.utility.ConvertNumberFormat.convertToWords(amnt) + " Only");

            String logoPath = request.getServletContext().getRealPath("/upload" + File.separatorChar + "company"
                    + File.separatorChar + "report" + File.separatorChar + comp.getCompanyId() + File.separatorChar + comp.getReportLogoPath());
            String postedInd = rs.getString("POSTED_IND");
            String cancelledBy = rs.getString("CANCELLED_BY");
            String imagePath = request.getServletContext().getRealPath("/images" + File.separatorChar);
            if (postedInd.equalsIgnoreCase("N") && cancelledBy == null) {
                map.put("imagePath", imagePath + File.separatorChar + "draft.jpg");
            } else if (postedInd.equalsIgnoreCase("N") && cancelledBy != null) {
                map.put("imagePath", imagePath + File.separatorChar + "cancelled.jpg");
            }
            File folder = new File(logoPath);
            if (!folder.exists()) {
                map.put("logoPath", imagePath + File.separatorChar + "logo_report.jpg");

            } else {
                map.put("logoPath", logoPath);
            }

            rs.beforeFirst();
        }
        map.put("userName", user.getUsername());
        map.put("COMP_NAME", comp.getCompanyName());
        this.previewReport(request, response, rs, map, "PerformaA");
    }

    private void previewReport(HttpServletRequest request, HttpServletResponse response, ResultSet rs, HashMap map, String reportName) throws Exception {
        FileInputStream input = null;
        try {
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            JasperDesign jasperdesign;

            jasperdesign = JRXmlLoader.load(request.getServletContext().getRealPath("/reports/" + reportName + ".jrxml"));
            jasperReport = JasperCompileManager.compileReport(jasperdesign);
            JRResultSetDataSource jr = new JRResultSetDataSource(rs);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, jr);
            JasperExportManager.exportReportToPdfFile(jasperPrint, request.getServletContext().getRealPath("/reports/" + reportName + ".pdf"));
            String filename = request.getServletContext().getRealPath("/reports/" + reportName + ".pdf");
            File rtf = new File(filename);
            int readBytes = 0;

            response.setContentType("application/pdf");
            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-disposition", "inline; filename=\"" + reportName + ".pdf\"");

            response.setContentLength((int) rtf.length());
            input = new FileInputStream(rtf);
            BufferedInputStream buf = new BufferedInputStream(input);
            OutputStream stream = response.getOutputStream();
            while ((readBytes = buf.read()) != -1) {
                stream.write(readBytes);
            }
            stream.flush();

        } catch (Exception exp) {
            throw new Exception("Exception Occured.." + exp.getMessage());
        }
    }

    private void previewReport(HttpServletRequest request, HttpServletResponse response, Connection con, HashMap map, String reportName) throws Exception {
        FileInputStream input = null;
        try {
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            JasperDesign jasperdesign;

            jasperdesign = JRXmlLoader.load(request.getServletContext().getRealPath("/reports/" + reportName + ".jrxml"));
            jasperReport = JasperCompileManager.compileReport(jasperdesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
            JasperExportManager.exportReportToPdfFile(jasperPrint, request.getServletContext().getRealPath("/reports/" + reportName + ".pdf"));
            String filename = request.getServletContext().getRealPath("/reports/" + reportName + ".pdf");
            File rtf = new File(filename);
            int readBytes = 0;

            response.setContentType("application/pdf");
            response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-disposition", "inline; filename=\"" + reportName + ".pdf\"");

            response.setContentLength((int) rtf.length());
            input = new FileInputStream(rtf);
            BufferedInputStream buf = new BufferedInputStream(input);
            OutputStream stream = response.getOutputStream();
            while ((readBytes = buf.read()) != -1) {
                stream.write(readBytes);
            }
            stream.flush();

        } catch (Exception exp) {
            throw new Exception("Exception Occured.." + exp.getMessage());
        }
    }
}
