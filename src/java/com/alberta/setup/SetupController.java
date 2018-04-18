/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.setup;

import com.alberta.model.*;
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
 * @author Faraz
 */
public class SetupController extends MultiActionController {
    
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

    /*ZONE*/
    public ModelAndView addPatient(HttpServletRequest request, HttpServletResponse response) {
        //Map map = new HashMap();
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String searchTopPatient = request.getParameter("searchTopPatient");
        String addNewPatient = request.getParameter("addNewPatient");
        if (searchTopPatient == null) {
            searchTopPatient = "";
        }
        if (addNewPatient == null) {
            addNewPatient = "N";
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Patients");
        //map.put("panelCompanyList", this.serviceFactory.getSetupService().getPanelCompanies(""));
        map.put("rightName", "Patients");
        map.put("searchTopPatient", searchTopPatient);
        map.put("addNewPatient", addNewPatient);
        map.put("bloodGroup", this.serviceFactory.getSetupService().getBloodGroup());
        map.put("diseases", this.serviceFactory.getSetupService().getDiseases("Y"));
        map.put("cities", this.serviceFactory.getClinicService().getCities(""));
        return new ModelAndView("setup/addPatient", "refData", map);
    }
    
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
//        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Zone", moduleId);
        Map map = new HashMap();
        map.put("diseases", this.serviceFactory.getSetupService().getDiseases(""));
        
        return new ModelAndView("setup/addUser", "refData", map);
    }
    
    public ModelAndView addDoctor(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Doctors");
        map.put("rightName", "Doctors");
        map.put("categories", this.serviceFactory.getSetupService().getDoctorCagetories(""));
        // map.put("degree", this.serviceFactory.getSetupService().getDoctorDegrees(""));
        map.put("types", this.serviceFactory.getSetupService().getDoctorTypes(""));
        map.put("country", this.serviceFactory.getSetupService().getCountry(com.getCompanyId()));
        return new ModelAndView("setup/addDoctor", "refData", map);
    }

    //Patient
    public void savePatient(HttpServletRequest request, HttpServletResponse response, Patient po) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        po.setCompanyId(companyId);
        po.setUserName(userName);
        String patientId = this.serviceFactory.getSetupService().savePatient(po);
        JSONObject obj = new JSONObject();
        if (!patientId.isEmpty()) {
            obj.put("result", "save_success");
            obj.put("patientId", patientId);
        } else {
            obj.put("result", "save_error");
            obj.put("patientId", patientId);
        }
        response.getWriter().write(obj.toString());
    }
    
    public void savePatientReports(HttpServletRequest request, HttpServletResponse response, Patient po) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        po.setUserName(userName);
        po.setDoctorId(user.getDoctorId());
        String ReportPath = request.getServletContext().getRealPath("/upload/patient/prescription/");
        boolean flag = this.serviceFactory.getSetupService().savePatientReports(po, ReportPath);
        
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        
        response.getWriter().write(obj.toString());
    }
    
    public void getPatient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientName = request.getParameter("patientNameSearch");
        String contactNo = request.getParameter("contactNoSearch");
        String startRowNo = request.getParameter("startRowNo");
        String endRowNo = request.getParameter("endRowNo");
        String searchCharacter = request.getParameter("searchCharacter");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getPatient(patientName, contactNo, startRowNo, endRowNo, searchCharacter);
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
    
    public void getCityByCountryId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryId = request.getParameter("countryId");
        List<Map> list = this.serviceFactory.getSetupService().getCityByCountryId(countryId);
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
    
    public void getStateByCountryId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryId = request.getParameter("countryId");
        List<Map> list = this.serviceFactory.getSetupService().getStateByCountryId(countryId);
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
    
    public void getDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorName = request.getParameter("doctorNameSearch");
        String contactNo = request.getParameter("contactNoSearch");
        String doctorType = request.getParameter("doctorTypeSearch");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctors(doctorName, contactNo, doctorType);
        
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
    
    public ModelAndView addProduct(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Add Product");
        map.put("rightName", "Add Product");
        map.put("companies", this.serviceFactory.getSetupService().getPharmaCompanies());
        map.put("diseases", this.serviceFactory.getSetupService().getDiseases(""));
        return new ModelAndView("setup/addProduct", "refData", map);
    }
    
    public ModelAndView addPharma(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Pharmaceutical");
        map.put("rightName", "Pharmaceutical");
        return new ModelAndView("setup/viewPharmaCompanies", "refData", map);
//        map.put("rightName", "Companies");
//        return new ModelAndView("setup/addCompanies", "refData", map);
    }
    
    public void saveDoctor(HttpServletRequest request, HttpServletResponse response, DoctorVO vo) throws IOException {
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
        boolean flag = this.serviceFactory.getSetupService().saveDoctor(vo);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void updateDoctorExpiry(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        String expiryDate = request.getParameter("expiryDate");
        boolean flag = this.serviceFactory.getSetupService().updateDoctorExpiry(doctorId, expiryDate);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveDoctorAttachment(HttpServletRequest request, HttpServletResponse response, DoctorVO vo) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        vo.setUserName(userName);
        String ReportPath = request.getServletContext().getRealPath("/upload/doctor/doctorAttachments/");
        boolean flag = this.serviceFactory.getSetupService().saveDoctorAttachment(vo, ReportPath);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveCompanyLogo(HttpServletRequest request, HttpServletResponse response, Pharma vo) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        vo.setUserName(userName);
        String attachmentPath = request.getServletContext().getRealPath("/upload/company/logo/");
        boolean flag = this.serviceFactory.getSetupService().saveCompanyLogo(vo, attachmentPath);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void savePharma(HttpServletRequest request, HttpServletResponse response, Pharma p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        p.setCompanyId(companyId);
        p.setUserName(userName);
        
        boolean flag = this.serviceFactory.getSetupService().savePharma(p);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView addAppointment(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
//        map.put("patients", this.serviceFactory.getSetupService().getPatient(null, null,null,null));
        map.put("types", this.serviceFactory.getSetupService().getDoctorTypes(""));
        return new ModelAndView("clinic/addAppointment", "refData", map);
    }
    
    public void saveProduct(HttpServletRequest request, HttpServletResponse response, Product p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        p.setCompanyId(companyId);
        p.setUserName(userName);
        p.setMultiSelectDiseases(request.getParameterValues("selectDiseasesArr[]"));
        boolean flag = this.serviceFactory.getSetupService().saveProduct(p);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getPharmaProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pharmacyName = request.getParameter("pharmaProductList");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getPharmaProducts(pharmacyName);
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
    
    public ModelAndView viewPharmaCompanies(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "View Pharma");
        return new ModelAndView("setup/viewPharmaCompanies", "refData", map);
    }
    
    public void getPharmaCompanies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pharmacyName = request.getParameter("pharmacyName");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getPharma(pharmacyName);
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
    
    public ModelAndView addClinic(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Clinics");
        map.put("country", this.serviceFactory.getSetupService().getCountry(""));
        map.put("rightName", "Clinics");
        return new ModelAndView("setup/addClinic", "refData", map);
    }
    
    public void saveClinic(HttpServletRequest request, HttpServletResponse response, DoctorVO c) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        c.setCompanyId(companyId);
        c.setUserName(userName);
        
        boolean flag = this.serviceFactory.getSetupService().saveClinic(c);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getClinics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clinicName = request.getParameter("clinicName");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getClinic(clinicName);
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
    
    public ModelAndView assignClinicToDoctor(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Doctor Clinics");
        map.put("rightName", "Doctor Clinics");
        map.put("doctorsList", this.serviceFactory.getSetupService().getDoctors(""));
        //map.put("clinicList", this.serviceFactory.getSetupService().getClinics(""));
        return new ModelAndView("setup/assignClinicToDoctor", "refData", map);
    }
    
    public void saveDoctorClinic(HttpServletRequest request, HttpServletResponse response, DoctorClinic dc) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        dc.setWeekdays(request.getParameterValues("weekdaysarr[]"));
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        dc.setUserName(userName);
        
        boolean flag = this.serviceFactory.getSetupService().saveDoctorClinic(dc);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveVideoLink(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String doctorId = request.getParameter("doctorId");
        String videoLink = request.getParameter("videoLink");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        
        boolean flag = this.serviceFactory.getSetupService().saveVideoLink(doctorId, videoLink);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getClinicForDoctors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getClinicForDoctors(doctorId);
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
    
    public void getAvailableClinicForDoctors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        String doctorClinicId = request.getParameter("doctorClinicId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getAvailableClinicForDoctors(doctorId, doctorClinicId);
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
    
    public void deleteDoctorClinic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        String clinicId = request.getParameter("clinicId");
        String doctorId = request.getParameter("doctorId");
        boolean flag = this.serviceFactory.getSetupService().deleteDoctorClinic(id, clinicId, doctorId);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deleteDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteDoctor(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deletePatient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deletePatient(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deletePharma(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deletePharma(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deleteClinic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteClinic(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getDoctorById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getDoctorById(doctorId);
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
    
    public void getPatientById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getPatientById(patientId);
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
    
    public void getPatientDiseasesById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getPatientDiseasesById(patientId);
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
    
    public void getDoctorSpecialityDiseasesById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctorSpecialityDiseasesById(doctorId);
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
    
    public void getDoctorSpecialityById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctorSpecialityById(doctorId);
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
    
    public void getDoctorServiceById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctorServiceById(doctorId);
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
    
    public void getDoctorsForClinic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clinicId = request.getParameter("clinicId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctorsForClinic(clinicId);
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
    
    public void getClinicById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clinicId = request.getParameter("clinicId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getClinicById(clinicId);
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
    
    public void getPharmaById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pharmaId = request.getParameter("pharmaId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getPharmaById(pharmaId);
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
    
    public void getDoctorClinicById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorClinicId = request.getParameter("doctorClinicId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getDoctorClinicById(doctorClinicId);
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
    
    public void getPharmaProductById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getParameter("productId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getPharmaProductById(productId);
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
    
    public ModelAndView addBloodDonor(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        //  map.put("pharmaProductList", this.serviceFactory.getSetupService().getCompany(""));
        map.put("rightName", "Add Donor");
        
        return new ModelAndView("setup/addBloodDonor", "refData", map);
    }
    
    public ModelAndView viewMedicine(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Medicine");
        map.put("rightName", "Medicine");
        map.put("medicines", this.serviceFactory.getSetupService().getMedicines());
        return new ModelAndView("setup/addDoctorMedicine", "refData", map);
    }
    
    public void saveDoctorMedicine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String docorId = request.getParameter("docorId");
        String medicineId = request.getParameter("medicineId");
        if (docorId == null) {
            docorId = user.getDoctorId();
        }
        boolean flag = this.serviceFactory.getSetupService().saveDoctorMedicine(docorId, medicineId, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void validatePatientContact(HttpServletRequest request, HttpServletResponse response) {
        try {
            String contactNo = request.getParameter("contactNo");
            Company com = (Company) request.getSession().getAttribute("company");
            String companyId = com.getCompanyId();
            boolean flag = this.serviceFactory.getSetupService().isPatientAlreadyExists(contactNo, companyId);
            JSONObject obj = new JSONObject();
            if (flag) {
                obj.put("msg", "invalid");
            } else {
                obj.put("msg", "valid");
            }
            response.getWriter().write(obj.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void validateDoctorContact(HttpServletRequest request, HttpServletResponse response) {
        try {
            String contactNo = request.getParameter("contactNo");
            Company com = (Company) request.getSession().getAttribute("company");
            String companyId = com.getCompanyId();
            boolean flag = this.serviceFactory.getSetupService().isDoctorAlreadyExists(contactNo);
            JSONObject obj = new JSONObject();
            if (flag) {
                obj.put("msg", "invalid");
            } else {
                obj.put("msg", "valid");
            }
            response.getWriter().write(obj.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void validateStudentContact(HttpServletRequest request, HttpServletResponse response) {
        try {
            String contactNo = request.getParameter("contactNo");
            Company com = (Company) request.getSession().getAttribute("company");
            String companyId = com.getCompanyId();
            boolean flag = this.serviceFactory.getSetupService().isStudentAlreadyExists(contactNo);
            JSONObject obj = new JSONObject();
            if (flag) {
                obj.put("msg", "invalid");
            } else {
                obj.put("msg", "valid");
            }
            response.getWriter().write(obj.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void getDoctorMedicine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String doctorId = request.getParameter("doctorId");
        if (doctorId == null) {
            doctorId = user.getDoctorId();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctorsMedicine(doctorId);
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
    
    public void deleteDoctorMedicine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteDoctorMedicine(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView viewDoctorClinics(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "", doctorId = "";
        if (user != null) {
            userName = user.getUsername();
            doctorId = user.getDoctorId();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "View Clinics");
        map.put("rightName", "View Clinics");
        map.put("doctorId", doctorId);
        map.put("doctorId", doctorId);
        return new ModelAndView("setup/viewDoctorClinics", "refData", map);
    }
    
    public ModelAndView sendMessage(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "Send Message");
        return new ModelAndView("setup/sendMessage", "refData", map);
    }
    
    public void saveInTakeForm(HttpServletRequest request, HttpServletResponse response, Patient p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        p.setDiseases(request.getParameterValues("diseases[]"));
        boolean flag = this.serviceFactory.getSetupService().saveInTakeForm(p);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveDoctorSpecialityDisease(HttpServletRequest request, HttpServletResponse response, DoctorVO d) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        d.setUserName(userName);
        d.setDiseases(request.getParameterValues("diseasesarr[]"));
        boolean flag = this.serviceFactory.getSetupService().saveDoctorSpecialityDisease(d);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveDoctorSpeciality(HttpServletRequest request, HttpServletResponse response, DoctorVO d) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        d.setUserName(userName);
        d.setSpecility(request.getParameterValues("specialityarr[]"));
        boolean flag = this.serviceFactory.getSetupService().saveDoctorSpeciality(d);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveDoctorServices(HttpServletRequest request, HttpServletResponse response, DoctorVO d) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        d.setUserName(userName);
        d.setServices(request.getParameterValues("servicesarr[]"));
        boolean flag = this.serviceFactory.getSetupService().saveDoctorServices(d);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveDiseasesForm(HttpServletRequest request, HttpServletResponse response, Patient po) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        po.setDiseases(request.getParameterValues("diseasesarr[]"));
        boolean flag = this.serviceFactory.getSetupService().saveDiseases(po);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView saleHealthCards(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Sale Health Card");
        map.put("rightName", "Sale Health Card");
        map.put("patientsList", this.serviceFactory.getSetupService().getPatients(""));
        return new ModelAndView("setup/saleHealthCards", "refData", map);
    }
    
    public void getPatientHealthCards(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String patientId = request.getParameter("patientId");
        List<Map> list = this.serviceFactory.getSetupService().getPatientHealthCards(patientId);
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
    
    public void savePatientHealthCard(HttpServletRequest request, HttpServletResponse response, Patient p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        p.setUserName(userName);
        p.setUserName(userName);
        JSONObject obj = new JSONObject();
        boolean flag = this.serviceFactory.getSetupService().savePatientHealthCard(p);
        if (flag) {
            obj.put("msg", "saved");
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getHealthCardById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cardId = request.getParameter("cardId");
        String patientId = request.getParameter("patientId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getHealthCardById(cardId, patientId);
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
    
    public void deletePatientHealthCard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String healthCardId = request.getParameter("healthCardId");
        boolean flag = this.serviceFactory.getSetupService().deletePatientHealthCard(healthCardId);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void updatePatientHealthCardIndicator(HttpServletRequest request, HttpServletResponse response, Patient p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        p.setUserName(userName);
        p.setUserName(userName);
        JSONObject obj = new JSONObject();
        boolean flag = this.serviceFactory.getSetupService().updatePatientHealthCardIndicator(p);
        if (flag) {
            obj.put("msg", "saved");
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getHealthCards(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getHealthCards();
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
    
    public void getPatientDisease(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getPatientDisease(patientId);
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
    
    public ModelAndView addCompany(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Panel Company");
        map.put("panelCompanyList", this.serviceFactory.getSetupService().getPanelCompanies(""));
        map.put("rightName", "Panel Company");
        return new ModelAndView("setup/addCompany", "refData", map);
    }
    
    public ModelAndView assignPanelCompany(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Doctor Panel Company");
        
        map.put("doctorsList", this.serviceFactory.getSetupService().getDoctors(""));
        //map.put("panelCompanyList", this.serviceFactory.getSetupService().getPanelCompanies(""));
        map.put("rightName", "Doctor Panel Company");
        return new ModelAndView("setup/assignPanelCompany", "refData", map);
    }
    
    public void saveCompany(HttpServletRequest request, HttpServletResponse response, Pharma p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        p.setCompanyId(companyId);
        p.setUserName(userName);
        
        boolean flag = this.serviceFactory.getSetupService().saveCompany(p);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getCompanyById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String companyId = request.getParameter("companyId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getSetupService().getCompaniesById(companyId);
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
    
    public void getCompanies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String companyName = request.getParameter("companyName");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getCompanies(companyName);
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
    
    public void getAvailablePanelCompanies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getAvailablePanelCompanies(doctorId);
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
    
    public void deleteCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteCompany(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void savePanelCompany(HttpServletRequest request, HttpServletResponse response, Pharma p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        p.setUserName(userName);
        
        boolean flag = this.serviceFactory.getSetupService().savePanelCompany(p);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getPanelCompaniesForDoctors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getPanelCompaniesForDoctors(doctorId);
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
    
    public void deleteAssignPanelCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteAssignPanelCompany(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void updateDoctorPanelCompanyIndicator(HttpServletRequest request, HttpServletResponse response, Pharma p) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        p.setUserName(userName);
        p.setUserName(userName);
        JSONObject obj = new JSONObject();
        boolean flag = this.serviceFactory.getSetupService().updateDoctorPanelCompanyIndicator(p);
        if (flag) {
            obj.put("msg", "saved");
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView addPanelPatient(HttpServletRequest request, HttpServletResponse response) {
        //Map map = new HashMap();
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String searchTopPatient = request.getParameter("searchTopPatient");
        String addNewPatient = request.getParameter("addNewPatient");
        if (searchTopPatient == null) {
            searchTopPatient = "";
        }
        if (addNewPatient == null) {
            addNewPatient = "N";
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Panel Patient");
        map.put("panelCompanyList", this.serviceFactory.getSetupService().getPanelCompanies(""));
        map.put("rightName", "Panel Patient");
        map.put("searchTopPatient", searchTopPatient);
        map.put("addNewPatient", addNewPatient);
        map.put("diseases", this.serviceFactory.getSetupService().getDiseases(""));
        return new ModelAndView("setup/addPanelPatient", "refData", map);
    }
    
    public void getPanelPatient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientName = request.getParameter("patientNameSearch");
        String contactNo = request.getParameter("contactNoSearch");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getPanelPatient(patientName, contactNo);
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
    
    public void getDoctorActtachementsById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        String attachType = request.getParameter("attachType");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctorActtachementsById(doctorId, attachType);
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
    
    public void getReportActtachementsById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = "";
        if (doctorId.isEmpty()) {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                doctorId = user.getDoctorId();
            }
        }
        String patientId = request.getParameter("patientId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getReportActtachementsById(doctorId, patientId);
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
    
    public void deleteDoctorAttachement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteDoctorAttachement(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deleteReportAttachement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String attachmentId = request.getParameter("attachmentId");
        boolean flag = this.serviceFactory.getSetupService().deleteReportAttachement(attachmentId);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView viewHospital(HttpServletRequest request, HttpServletResponse response) {
        //Map map = new HashMap();
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Hospitals");
        map.put("country", this.serviceFactory.getSetupService().getCountry(com.getCompanyId()));
        map.put("rightName", "Hospitals");
        return new ModelAndView("clinic/viewHospital", "refData", map);
    }
    
    public void deleteHospital(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getClinicService().deleteHospital(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getHospitalById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String hospitalId = request.getParameter("hospitalId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getClinicService().getHospitalById(hospitalId);
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
    
    public void saveHospital(HttpServletRequest request, HttpServletResponse response, DoctorVO c) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        c.setCompanyId(companyId);
        c.setUserName(userName);
        
        boolean flag = this.serviceFactory.getSetupService().saveHospital(c);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getHospital(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getHospital();
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
    
    public ModelAndView addTestGroup(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Test Group");
        map.put("rightName", "Test Group");
        return new ModelAndView("setup/addTestGroup", "refData", map);
    }
    
    public void saveTestGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String testGroupId = request.getParameter("testGroupId");
        String testGroupName = request.getParameter("testGroupName");
        boolean flag = this.serviceFactory.getSetupService().saveTestGroup(testGroupId, testGroupName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getTestGroups(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getTestGroups();
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
    
    public void getTestGroupById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String testGroupId = request.getParameter("testGroupId");
        Map map = this.serviceFactory.getSetupService().getTestGroupById(testGroupId);
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
    
    public void deleteTestGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteTestGroup(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView viewExaminationQuestion(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Examination Question");
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("rightName", "Examination Question");
        return new ModelAndView("setup/viewExaminationQuestion", "refData", map);
    }
    
    public void saveExaminationQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String questionMasterId = request.getParameter("questionMasterId");
        String question = request.getParameter("question");
        String specialityId = request.getParameter("specialityId");
        String categoryId = request.getParameter("categoryId");
        boolean flag = this.serviceFactory.getSetupService().saveExaminationQuestion(questionMasterId, specialityId, question, userName, categoryId);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getExaminationQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String specialityId = request.getParameter("specialityId");
        String categoryId = request.getParameter("categoryId");
        List<Map> list = this.serviceFactory.getSetupService().getExaminationQuestion(specialityId, categoryId);
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
    
    public void getAnswerByCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String categoryId = request.getParameter("categoryId");
        List<Map> list = this.serviceFactory.getSetupService().getAnswerByCategory(categoryId);
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
    
    public void getExaminationQuestionById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String questionMasterId = request.getParameter("id");
        Map map = this.serviceFactory.getSetupService().getExaminationQuestionById(questionMasterId);
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
    
    public void deleteExaminationQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteExaminationQuestion(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String questionMasterId = request.getParameter("questionMasterId");
        String answer = request.getParameter("answer");
        boolean flag = this.serviceFactory.getSetupService().saveAnswer(questionMasterId, answer, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String questionMasterId = request.getParameter("questionMasterId");
        List<Map> list = this.serviceFactory.getSetupService().getAnswer(questionMasterId);
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
    
    public void getVaccinationDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String vaccinationId = request.getParameter("vaccinationId");
        List<Map> list = this.serviceFactory.getSetupService().getVaccinationDetail(vaccinationId);
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
    
    public void deleteVaccinationDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteVaccinationDetail(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void deleteAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteAnswer(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView addPatientExamination(HttpServletRequest request, HttpServletResponse response) {
        String patientId = request.getParameter("patientId");
        Map map = new HashMap();
        map.put("patientId", patientId);
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        map.put("revision", this.serviceFactory.getSetupService().getRevision(patientId, user.getDoctorId()));
        map.put("question", this.serviceFactory.getSetupService().getExaminationQuestionForDoctor(user.getDoctorId()));
        map.put("answer", this.serviceFactory.getSetupService().getAnswer());
        return new ModelAndView("setup/addPatientExamination", "refData", map);
    }
    
    public void saveExamination(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String patientId = request.getParameter("patientId");
        String questionarr[];
        questionarr = request.getParameterValues("questionarr[]");
        String answerarr[];
        answerarr = request.getParameterValues("answerarr[]");
        String questionCategory = request.getParameter("questionCategory");
        String revisionNo = request.getParameter("revisionNo");
        boolean flag = this.serviceFactory.getSetupService().saveExamination(patientId, user.getDoctorId(), questionarr, answerarr, userName, questionCategory, revisionNo);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getPatientRevisions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        User user = (User) request.getSession().getAttribute("user");
        List<Map> list = this.serviceFactory.getSetupService().getRevision(patientId, user.getDoctorId());
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
    
    public void getExaminationRevision(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String patientId = request.getParameter("patientId");
        String revisionNo = request.getParameter("revisionNo");
        String questionCategory = request.getParameter("questionCategory");
        User user = (User) request.getSession().getAttribute("user");
        
        List<Map> list = this.serviceFactory.getSetupService().getExaminationRevision(patientId, user.getDoctorId(), revisionNo, questionCategory);
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
    
    public void doctorFeatured(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        boolean flag = this.serviceFactory.getSetupService().doctorFeatured(id, status);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    //Examination Question Categories
    public ModelAndView viewQuestionCategories(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Question Categorirs");
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("rightName", "Question Categorirs");
        return new ModelAndView("setup/viewQuestionCategories", "refData", map);
    }
    
    public void saveQuestionCategories(HttpServletRequest request, HttpServletResponse response, CategoryVO vo) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String path = request.getServletContext().getRealPath("/upload/examCategory/");
        vo.setFolderPath(path);
        vo.setUserName(userName);
        boolean flag = this.serviceFactory.getSetupService().saveQuestionCategory(vo);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
        
    }
    
    public void getQuestionCategories(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String specialityId = request.getParameter("specialityId");
        List<Map> list = this.serviceFactory.getSetupService().getQuestionCategories(specialityId);
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
    
    public void getQuestionCategoryById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String questionMasterId = request.getParameter("id");
        Map map = this.serviceFactory.getSetupService().getQuestionCategoryById(questionMasterId);
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
    
    public void deleteQuestionCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteQuestionCategory(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    // Add Vaccination
    public ModelAndView addVaccination(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Add Vaccination");
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("rightName", "Add Vaccination");
        return new ModelAndView("setup/addVaccination", "refData", map);
    }
    
    public void saveVaccination(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String vaccinationId = request.getParameter("vaccinationId");
        String vaccinationName = request.getParameter("vaccinationName");
        String frequency = request.getParameter("frequency");
        String specialityId = request.getParameter("specialityId");
        String abbrev = request.getParameter("abbrev");
        boolean flag = this.serviceFactory.getSetupService().saveVaccination(vaccinationId, specialityId, vaccinationName, abbrev, frequency, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveVaccinationMedicine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String vaccinationId = request.getParameter("vaccinationId");
        String[] medicineName = request.getParameterValues("medicineNameArr[]");
        String[] doseUsage = request.getParameterValues("doseUsageArr[]");
        boolean flag = this.serviceFactory.getSetupService().saveVaccinationMedicine(vaccinationId, medicineName, doseUsage, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getVaccination(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String specialityId = request.getParameter("specialityId");
        List<Map> list = this.serviceFactory.getSetupService().getVaccination(specialityId);
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
    
    public void getVaccinationById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vaccinationId = request.getParameter("id");
        Map map = this.serviceFactory.getSetupService().getVaccinationById(vaccinationId);
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
    
    public void deleteVaccination(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteVaccination(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView addStudent(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Student");
        map.put("rightName", "Student");
        return new ModelAndView("setup/addStudent", "refData", map);
    }
    
    public void saveStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String studentId = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String cellNo = request.getParameter("cellNo");
        String gender = request.getParameter("gender");
        String age = request.getParameter("age");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");
        boolean flag = this.serviceFactory.getSetupService().saveStudent(studentId, studentName, cellNo, gender, age, dob, address, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getStudent();
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
    
    public void getStudentById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vaccinationId = request.getParameter("id");
        Map map = this.serviceFactory.getSetupService().getStudentById(vaccinationId);
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
    
    public void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteStudent(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public ModelAndView addDoctorArticle(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Doctor Article");
        map.put("rightName", "Doctor Article");
        return new ModelAndView("setup/addDoctorArticle", "refData", map);
    }
    
    public void saveDoctorArticle(HttpServletRequest request, HttpServletResponse response, Article ar) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        ar.setUserName(userName);
        boolean flag = this.serviceFactory.getSetupService().saveDoctorArticle(ar);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getDoctorArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getSetupService().getDoctorArticle();
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
    
    public void getDoctorArticleById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vaccinationId = request.getParameter("id");
        Map map = this.serviceFactory.getSetupService().getDoctorArticleById(vaccinationId);
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
    
    public void deleteDoctorArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getSetupService().deleteDoctorArticle(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void saveDoctorArticleAttachment(HttpServletRequest request, HttpServletResponse response, Article ar) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        ar.setUserName(userName);
        String FilePath = request.getServletContext().getRealPath("/upload/doctor/articleAttachment/");
        ar.setFilePath(FilePath);
        boolean flag = this.serviceFactory.getSetupService().saveDoctorArticleAttachment(ar);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
}
