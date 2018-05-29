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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
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

    public void reportPrescriptionById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap map = new HashMap();
        User user = (User) request.getSession().getAttribute("user");
        String prescriptionId = request.getParameter("prescriptionId");
        Map prescMaster = this.serviceFactory.getPerformaService().getPrescriptionMasterById(prescriptionId);
        String doctorId = "";
        String clinicId = "";
        if (prescMaster != null) {
            doctorId = prescMaster.get("TW_DOCTOR_ID").toString();
            clinicId = prescMaster.get("TW_CLINIC_ID").toString();
        }
        Map printLayout = this.serviceFactory.getClinicService().getPrintLayouts(doctorId, clinicId);
        map.put("SUBREPORT_DIR", request.getServletContext().getRealPath("/reports/") + File.separator);
        map.put("userName", user.getUsername());
        map.put("prescription_id", new BigDecimal(prescriptionId));
        if (printLayout != null) {
            String imagePath = request.getServletContext().getRealPath("/upload/doctor/latterPad/");
            if (printLayout.get("TOP_IMAGE") != null) {
                map.put("headerImage", imagePath + File.separatorChar + doctorId + File.separatorChar + printLayout.get("TOP_IMAGE").toString());
            } else {
                map.put("headerImage", request.getServletContext().getRealPath("/images/") + File.separatorChar + "blank-wallpaper.jpg");
            }
            if (printLayout.get("BOTTOM_IMAGE") != null) {
                map.put("footerImage", imagePath + File.separatorChar + doctorId + File.separatorChar + printLayout.get("BOTTOM_IMAGE").toString());
            } else {
                map.put("footerImage", request.getServletContext().getRealPath("/images/") + File.separatorChar + "blank-wallpaper.jpg");
            }
        } else {
            map.put("headerImage", request.getServletContext().getRealPath("/images/") + File.separatorChar + "blank-wallpaper.jpg");
            map.put("footerImage", request.getServletContext().getRealPath("/images/") + File.separatorChar + "blank-wallpaper.jpg");
        }
        map.put("userName", user.getUsername());
        this.previewReport(request, response, this.serviceFactory.getReportService().getDao().getJdbcTemplate().getDataSource().getConnection(), map, "prescription");

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
            System.out.println("jasperdesign: " + jasperdesign.toString());
            jasperReport = JasperCompileManager.compileReport(jasperdesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
            JasperExportManager.exportReportToPdfFile(jasperPrint, request.getServletContext().getRealPath("/reports/" + reportName + ".pdf"));
            String filename = request.getServletContext().getRealPath("/reports/" + reportName + ".pdf");
            System.out.println("filename: " + filename);
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
            exp.printStackTrace();
            throw new Exception("Exception Occured.." + exp.getMessage());
        }
    }
}
