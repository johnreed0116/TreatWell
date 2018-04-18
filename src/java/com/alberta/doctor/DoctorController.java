/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.doctor;

import com.alberta.model.Company;
import com.alberta.model.DoctorVO;
import com.alberta.model.User;
import com.alberta.service.ServiceFactory;
import java.io.IOException;
import java.util.ArrayList;
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
}
