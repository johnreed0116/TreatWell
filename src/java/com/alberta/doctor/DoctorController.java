/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.doctor;

import com.alberta.model.CategoryVO;
import com.alberta.model.Company;
import com.alberta.model.DoctorVO;
import com.alberta.model.User;
import com.alberta.service.ServiceFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author farazahmad
 */
public class DoctorController extends MultiActionController {
    
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
    
    public ModelAndView viewDoctorsDatabase(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Doctors");
        map.put("rightName", "Temp Doctors");
        map.put("categories", this.serviceFactory.getSetupService().getDoctorCagetories(""));
        map.put("clinics", this.serviceFactory.getSetupService().getClinic(""));
        map.put("country", this.serviceFactory.getSetupService().getCountry(com.getCompanyId()));
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("degree", this.serviceFactory.getSetupService().getDoctorDegrees(""));
        map.put("countries", this.serviceFactory.getSetupService().getCountry(com.getCompanyId()));
        map.put("services", this.serviceFactory.getSetupService().getMedicalServices(""));
        return new ModelAndView("doctor/addTempDoctor", "refData", map);
    }
    
    public void getTempDoctorById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getDoctorService().getTempDoctorById(doctorId);
        JSONObject obj = new JSONObject();
        if (map != null) {
            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next().getKey();
                obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
            }
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveDoctorInDatabase(HttpServletRequest request, HttpServletResponse response, DoctorVO vo) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        vo.setCompanyId(companyId);
        vo.setUserName(userName);
        String ReportPath = request.getServletContext().getRealPath("/upload/doctor/");
        vo.setPath(ReportPath);
        boolean flag = this.serviceFactory.getDoctorService().saveDoctor(vo);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getTempDoctors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorName = request.getParameter("doctorNameSearch");
        String contactNo = request.getParameter("contactNoSearch");
        String doctorType = request.getParameter("doctorTypeSearch");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getDoctorService().getTempDoctors(doctorName, contactNo, doctorType);
        
        List<JSONObject> objList = new ArrayList();
        JSONObject obj = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                obj = new JSONObject();
                Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
                while (itr.hasNext()) {
                    String key = itr.next().getKey();
                    obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
                }
                objList.add(obj);
            }
        }
        response.getWriter().write(objList.toString());
    }
    
    public ModelAndView viewDiagnostic(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Diagnostic");
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("rightName", "Diagnostic");
        return new ModelAndView("doctor/viewDiagnostic", "refData", map);
    }
    
    public void saveDiagnostic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String diagnosticId = request.getParameter("diagnosticId");
        String diagnosticTitle = request.getParameter("diagnosticTitle");
        String diagnosticInd = request.getParameter("diagnosticInd");
        String specialityId = request.getParameter("specialityId");
        boolean flag = this.serviceFactory.getDoctorService().saveDiagnostic(diagnosticId, specialityId, diagnosticTitle, userName, diagnosticInd);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getDiagnostic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String specialityId = request.getParameter("specialityId");
        List<Map> list = this.serviceFactory.getDoctorService().getDiagnostic(specialityId);
        List<JSONObject> objList = new ArrayList();
        JSONObject obj = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                obj = new JSONObject();
                Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
                while (itr.hasNext()) {
                    String key = itr.next().getKey();
                    obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
                }
                objList.add(obj);
            }
        }
        response.getWriter().write(objList.toString());
    }
    
    public void getDiagnosticById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String diagnosticId = request.getParameter("id");
        Map map = this.serviceFactory.getDoctorService().getDiagnosticById(diagnosticId);
        JSONObject obj = new JSONObject();
        if (map != null) {
            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next().getKey();
                obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
            }
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deleteDiagnostic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getDoctorService().deleteDiagnostic(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    //Vaccination Categories
    public ModelAndView viewVaccinationCategories(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Vaccination Category");
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("rightName", "Vaccination Category");
        return new ModelAndView("doctor/addVaccinationCategory", "refData", map);
    }
    
    public void saveVaccinationCategories(HttpServletRequest request, HttpServletResponse response, CategoryVO vo) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        vo.setUserName(userName);
        boolean flag = this.serviceFactory.getDoctorService().saveVaccinationCategory(vo);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
        
    }
    
    public void getVaccinationCategories(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String specialityId = request.getParameter("specialityId");
        List<Map> list = this.serviceFactory.getDoctorService().getVaccinationCategories(specialityId);
        List<JSONObject> objList = new ArrayList();
        JSONObject obj = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                obj = new JSONObject();
                Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
                while (itr.hasNext()) {
                    String key = itr.next().getKey();
                    obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
                }
                objList.add(obj);
            }
        }
        response.getWriter().write(objList.toString());
    }
    
    public void getVaccinationCategoryById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String questionMasterId = request.getParameter("id");
        Map map = this.serviceFactory.getDoctorService().getVaccinationCategoryById(questionMasterId);
        JSONObject obj = new JSONObject();
        if (map != null) {
            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next().getKey();
                obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
            }
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deleteVaccinationCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getDoctorService().deleteVaccinationCategory(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView viewPrescriptionForPrint(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        response.setContentType("text/html;charset=UTF-8");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = new HashMap();

//        map.put("patients", this.serviceFactory.getSetupService().getPatient(null, null,null,null));
        String userType = request.getSession().getAttribute("userType").toString();
        map.put("userType", userType);
        map.put("doctorId", user.getDoctorId());
        // Map map = new HashMap();
        String id = request.getParameter("id");
        String doctorId = "", prescriptionLang = "";
        Map masterObj = this.serviceFactory.getPerformaService().getPrescriptionMasterById(id);
        if (masterObj != null && masterObj.size() > 0) {
            doctorId = (String) masterObj.get("TW_DOCTOR_ID").toString();
            Map docObj = this.serviceFactory.getSetupService().getDoctorById(doctorId);
            if (docObj != null && docObj.size() > 0) {
                prescriptionLang = (String) docObj.get("PRESCRIPTION_LANG").toString();
            }
        }
        map.put("master", masterObj);
        map.put("prescriptionLang", prescriptionLang);
        map.put("medicines", this.serviceFactory.getPerformaService().getPrescriptionForMedicine(id));
        map.put("tests", this.serviceFactory.getPerformaService().getPrescriptionForLabTest(id));
        map.put("topImages", this.serviceFactory.getPerformaService().getMarginsByDoctorId(doctorId));
        return new ModelAndView("doctor/viewPrescription", "refData", map);
    }
    
}
