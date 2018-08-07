/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.performa;

import com.alberta.model.Company;
import com.alberta.model.DoctorVO;
import com.alberta.model.Lab;
import com.alberta.model.PerformaVO;
import com.alberta.model.Pharma;
import com.alberta.model.PrescriptionVO;
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
public class PerformaController extends MultiActionController {

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

    public ModelAndView addAppointment(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "Add Appointment");
        User user = (User) request.getSession().getAttribute("user");
        //map.put("patients", this.serviceFactory.getSetupService().getPatient(null, null));
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("ADMIN")) {
            map.put("clinics", this.serviceFactory.getSetupService().getClinic(""));
        } else if (userType.equalsIgnoreCase("DOCTOR")) {
            String clinicId = "", doctorId = "";
            doctorId = user.getDoctorId();
            map.put("doctorId", doctorId);
            Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
            if (clinic != null) {
                clinicId = clinic.get("TW_CLINIC_ID").toString();
                map.put("clinicId", clinicId);
            }
            Map clinicTime = this.serviceFactory.getSetupService().getTimeForClinic(doctorId, clinicId);
            map.put("timeFrom", clinicTime.get("TIME_FROM").toString());
            map.put("timeTo", clinicTime.get("TIME_TO").toString());
        } else if (userType.equalsIgnoreCase("CLINIC")) {
            String clinicId = "";
            clinicId = user.getClinicId();
            map.put("clinicId", clinicId);
        }
        map.put("userType", userType);
        map.put("bloodGroup", this.serviceFactory.getSetupService().getBloodGroup());
        map.put("cities", this.serviceFactory.getClinicService().getCitysOfPakistan());
        return new ModelAndView("clinic/addAppointment", "refData", map);
    }

    public void getAvailablePatientsForAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String date = request.getParameter("date");
        String clinicId = request.getParameter("clinicId");
        String doctorId = request.getParameter("doctorId");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        List list = this.serviceFactory.getSetupService().getAvailablePatientsForAppointment(date, doctorId, clinicId);
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

    public ModelAndView viewAppointments(HttpServletRequest request, HttpServletResponse response) {
        String clinicId = "";
        User user = (User) request.getSession().getAttribute("user");
        Map map = new HashMap();
        map.put("rightName", "Appointments Summary");
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("CLINIC")) {
            if (user != null) {
                clinicId = user.getClinicId();
                map.put("doctors", this.serviceFactory.getSetupService().getDoctorsForClinic(clinicId));
            }
        }
        map.put("userType", userType);
        map.put("diseases", this.serviceFactory.getSetupService().getDiseases("Y"));
        return new ModelAndView("performa/viewAppointmentSummary", "refData", map);
    }

    public void saveAppointment(HttpServletRequest request, HttpServletResponse response, PerformaVO vo) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        vo.setUserName(userName);
        vo.setCompanyId(companyId);
        String userType = request.getSession().getAttribute("userType").toString();
        JSONObject obj = new JSONObject();
        if (userType.equalsIgnoreCase("ADMIN")) {
            vo.setClinicId(request.getParameter("clinicId"));
            String appointmentId = this.serviceFactory.getPerformaService().saveAppointment(vo);
            if (!appointmentId.isEmpty()) {
                obj.put("msg", "saved");
                this.serviceFactory.getSmsService().sendAppointmentMessage(appointmentId);
            } else {
                obj.put("msg", "error");
            }
        } else if (userType.equalsIgnoreCase("CLINIC")) {
            vo.setClinicId(request.getParameter("clinicId"));
            String appointmentId = this.serviceFactory.getPerformaService().saveAppointment(vo);
            if (!appointmentId.isEmpty()) {
                obj.put("msg", "saved");
                this.serviceFactory.getSmsService().sendAppointmentMessage(appointmentId);
            } else {
                obj.put("msg", "error");
            }
        } else {
            Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
            User user = (User) request.getSession().getAttribute("user");
            if (clinic != null) {
                vo.setClinicId(clinic.get("TW_CLINIC_ID").toString());
                if (user != null) {
                    vo.setDoctorId(user.getDoctorId());
                }
                String appointmentId = this.serviceFactory.getPerformaService().saveAppointment(vo);
                if (!appointmentId.isEmpty()) {
                    obj.put("msg", "saved");
                    this.serviceFactory.getSmsService().sendAppointmentMessage(appointmentId);
                } else {
                    obj.put("msg", "error");
                }
            } else {
                obj.put("msg", "no_clinic");
            }
        }

        response.getWriter().write(obj.toString());
    }

    public void getDoctorsByService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String serviceId = request.getParameter("serviceId");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        List list = this.serviceFactory.getSetupService().getDoctorsByService(serviceId, com.getCompanyId());
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

    public void getAppointmentsForDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        String clinicId = request.getParameter("clinicId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        List list = this.serviceFactory.getPerformaService().getAppointmentsForDoctor(doctorId, clinicId, startDate, endDate);
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

    public void getAppointmentsForDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String date = request.getParameter("appointmentDate");
        String doctorName = request.getParameter("doctorName");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Company com = (Company) request.getSession().getAttribute("company");
        String userType = request.getSession().getAttribute("userType").toString();
        String clinicId = "";
        if (userType.equalsIgnoreCase("DOCTOR")) {
            Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
            clinicId = clinic.get("TW_CLINIC_ID").toString();
        } else if (userType.equalsIgnoreCase("CLINIC")) {
            clinicId = user.getClinicId();
        }
        List list = this.serviceFactory.getPerformaService().getAppointmentsForDate(date, clinicId, doctorName);
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

    public void updateAppointmentStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        String id = request.getParameter("appointmentId");
        String status = request.getParameter("status");
        boolean flag = this.serviceFactory.getPerformaService().updateAppointmentStatus(id, status, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("msg", "saved");
            if (status.equalsIgnoreCase("C")) {
                this.serviceFactory.getSmsService().sendAppointmentCancelMessage(id);
            }
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }

    public void updateAppointmentDateTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        String id = request.getParameter("appointmentId");
        String time = request.getParameter("time");
        String date = request.getParameter("date");
        boolean flag = this.serviceFactory.getPerformaService().updateAppointmentDateTime(id, date, time, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("msg", "saved");
            this.serviceFactory.getSmsService().sendAppointmentMessage(id);
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }

    public ModelAndView addDoctorProcedure(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "Procedure Fee");
        String doctorId = "";
        User user = (User) request.getSession().getAttribute("user");
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("ADMIN")) {
            map.put("doctorNames", this.serviceFactory.getSetupService().getDoctors(""));
        } else if (userType.equalsIgnoreCase("DOCTOR")) {
            doctorId = user.getDoctorId();
            map.put("doctorId", doctorId);
        }
        map.put("userType", userType);
        //map.put("medicalProcedures", this.serviceFactory.getPerformaService().getMedicalProcedures(""));
        return new ModelAndView("performa/addDoctorServices", "refData", map);
    }

    public void saveDoctorProcedure(HttpServletRequest request, HttpServletResponse response, DoctorVO ds) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        ds.setDiscounts(request.getParameterValues("discountArr[]"));
        ds.setFees(request.getParameterValues("feeArr[]"));
        ds.setPanelId(request.getParameterValues("panelCompanyIdArr[]"));
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String companyId = com.getCompanyId();
        ds.setCompanyId(companyId);
        ds.setUserName(userName);
        boolean flag = this.serviceFactory.getPerformaService().saveDoctorProcedure(ds);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getAvailableMedicalProcedures(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getPerformaService().getAvailableMedicalProcedures(doctorId);
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

    public void getDoctorProcedure(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorName = request.getParameter("doctorList");
        String panelCompanyId = request.getParameter("companyId");
        String appointmentId = request.getParameter("appointmentId");
        List<Map> list = this.serviceFactory.getPerformaService().getDoctorProcedures(doctorName, appointmentId, panelCompanyId);
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

    public ModelAndView addPrescription(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "", clinicId = "", doctorId = "";
        if (user != null) {
            userName = user.getUsername();
        }
        doctorId = request.getParameter("doctorId");
        if (doctorId == null) {
            doctorId = user.getDoctorId();
        }
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("CLINIC")) {
            clinicId = user.getClinicId();
            Map clinicObj = this.serviceFactory.getPerformaService().getDoctorClinic(clinicId);
            map.put("clinicTime", clinicObj);
            if (clinicObj != null && clinicObj.size() > 0) {
                doctorId = (String) clinicObj.get("TW_DOCTOR_ID").toString();
            }
        }
        //map.put("vaccination", this.serviceFactory.getPerformaService().getVaccinationByDoctorSpecility(doctorId));
        map.put("doctorId", doctorId);
        Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
        if (clinic != null) {
            clinicId = clinic.get("TW_CLINIC_ID").toString();
            map.put("clinicTime", this.serviceFactory.getPerformaService().getDoctorClinic(clinicId));
        }
        String prescriptionLang = "ENGLISH";
        Map docObj = this.serviceFactory.getSetupService().getDoctorById(doctorId);
        if (docObj != null && docObj.size() > 0) {
            prescriptionLang = (String) docObj.get("PRESCRIPTION_LANG").toString();
        }
        map.put("prescriptionLang", prescriptionLang);
        map.put("rightName", "Prescription");
        map.put("frequencies", this.serviceFactory.getSetupService().getFrequencies(""));
        map.put("doseUsage", this.serviceFactory.getPerformaService().getMedicineUsageForDoctor(user.getDoctorId()));

        map.put("medicines", this.serviceFactory.getSetupService().getDoctorsMedicine(doctorId));
        map.put("labTests", this.serviceFactory.getPerformaService().getLabTests());
        map.put("labs", this.serviceFactory.getPerformaService().getMedicalLab());
        map.put("diseases", this.serviceFactory.getSetupService().getDiseases("Y"));
        map.put("doseUsages", this.serviceFactory.getDoctorService().getMedicineUsageForDoctor(doctorId));
//        map.put("medicalLabs", this.serviceFactory.getClinicService().getMedicalLabs("Y"));

//        map.put("question", this.serviceFactory.getSetupService().getExaminationQuestionForDoctor(user.getDoctorId()));
//        map.put("answer", this.serviceFactory.getSetupService().getAnswer());
        map.put("categories", this.serviceFactory.getSetupService().getQuestionCategoriesForDoctor(user.getDoctorId()));
        map.put("vaccCategories", this.serviceFactory.getDoctorService().getVaccinationCategoriesForDoctor(user.getDoctorId()));

        return new ModelAndView("setup/addPrescription", "refData", map);
    }

    public void getAppointedPatientsForDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<JSONObject> objList = new ArrayList();
        String doctorId = "", clinicId = "";
        Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
        if (clinic != null) {
            clinicId = clinic.get("TW_CLINIC_ID").toString();
        }
        User user = (User) request.getSession().getAttribute("user");
        doctorId = request.getParameter("doctorId");
        if (doctorId == null) {
            doctorId = user.getDoctorId();
        }
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("CLINIC")) {
            clinicId = user.getClinicId();
            Map clinicObj = this.serviceFactory.getPerformaService().getDoctorClinic(clinicId);
            if (clinicObj != null && clinicObj.size() > 0) {
                doctorId = (String) clinicObj.get("TW_DOCTOR_ID").toString();
            }

        }
        String showAll = request.getParameter("showAll");
        List<Map> list = this.serviceFactory.getPerformaService().getAppointedPatientsForDoctor(doctorId, clinicId, showAll);
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

    public void savePrescription(HttpServletRequest request, HttpServletResponse response, PrescriptionVO vo) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        vo.setUserName(userName);
        User user = (User) request.getSession().getAttribute("user");
        vo.setMedicineId(request.getParameterValues("medicineIdArr[]"));
        vo.setQty(request.getParameterValues("qtyArr[]"));
        vo.setDays(request.getParameterValues("daysArr[]"));
        vo.setFrequencyId(request.getParameterValues("frequencyIdArr[]"));
        vo.setUsageId(request.getParameterValues("usageIdArr[]"));

        //String[] medicinesArray = request.getParameterValues("medicinesArray[]");
        //vo.setMedicineId(medicinesArray);
        vo.setLabId(request.getParameterValues("labIdArr[]"));
        vo.setLabTestId(request.getParameterValues("labTestIdArr[]"));
        vo.setLabCenterId(request.getParameterValues("labCenterIdArr[]"));
        vo.setOccurrence(request.getParameterValues("occurrenceArr[]"));

        String userType = request.getSession().getAttribute("userType").toString();
        JSONObject obj = new JSONObject();
        if (userType.equalsIgnoreCase("DOCTOR")) {
            Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
            if (clinic != null) {
                vo.setClinicId(clinic.get("TW_CLINIC_ID").toString());
                vo.setDoctorId(user.getDoctorId());
                String masterId = this.serviceFactory.getPerformaService().savePrescription(vo);
                if (!masterId.isEmpty()) {
                    obj.put("msg", "saved");
                    obj.put("masterId", masterId);
                } else {
                    obj.put("msg", "error");
                    obj.put("masterId", "");
                }
            } else {
                obj.put("msg", "no_clinic");
            }
        }
        response.getWriter().write(obj.toString());
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
        String questionCategory[] = request.getParameterValues("questionCategory[]");
        //String questionCategory = request.getParameter("questionCategory");
        String revisionNo = request.getParameter("revisionNo");
        PrescriptionVO vo = new PrescriptionVO();
        vo.setQuestionCategories(questionCategory);
        vo.setQuestions(questionarr);
        vo.setAnswers(answerarr);
        vo.setUserName(userName);
        vo.setPatientId(patientId);
        vo.setQuestionRemarks(request.getParameterValues("remarks[]"));
        vo.setPrescriptionNo(request.getParameter("prescriptionNo"));
        boolean flag = false;
        Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
        if (clinic != null) {
            vo.setDoctorId(user != null ? user.getDoctorId() : "");
            vo.setClinicId(clinic.get("TW_CLINIC_ID").toString());
            flag = this.serviceFactory.getPerformaService().saveExamination(vo);
        }
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void saveVaccination(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        User user = (User) request.getSession().getAttribute("user");
        String[] vaccinationMasterId = request.getParameterValues("vaccinationMasterId[]");
        String patientId = request.getParameter("patientId");
        String userType = request.getSession().getAttribute("userType").toString();
        PrescriptionVO vo = new PrescriptionVO();
        vo.setPatientId(patientId);
        vo.setVaccinationMasterId(vaccinationMasterId);
        vo.setUserName(userName);
        vo.setPrescriptionNo(request.getParameter("prescriptionNo"));
        JSONObject obj = new JSONObject();
        if (userType.equalsIgnoreCase("DOCTOR")) {
            Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
            if (clinic != null) {
                String clinicId = clinic.get("TW_CLINIC_ID").toString();
                String doctorId = user.getDoctorId();
                vo.setClinicId(clinicId);
                vo.setDoctorId(doctorId);
                boolean flag = this.serviceFactory.getPerformaService().saveVaccination(vo);
                if (flag) {
                    obj.put("msg", "saved");
                } else {
                    obj.put("msg", "error");
                }
            } else {
                obj.put("msg", "no_clinic");
            }
        }
        response.getWriter().write(obj.toString());
    }

    public ModelAndView searchPatient(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String query = request.getParameter("searchTopPatient");
        if (query != null && !query.isEmpty()) {
            map.put("results", this.serviceFactory.getPerformaService().searchPatient(query));
        }
        return new ModelAndView("performa/viewPatientsSearch", "refData", map);
    }

    public ModelAndView viewHealthCards(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "Health Card");
        return new ModelAndView("setup/viewHealthCards", "refData", map);
    }

    public void saveHealthCard(HttpServletRequest request, HttpServletResponse response, DoctorVO vo) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        vo.setUserName(userName);
        vo.setUserName(userName);
        JSONObject obj = new JSONObject();
        boolean flag = this.serviceFactory.getPerformaService().saveHealthCard(vo);
        if (flag) {
            obj.put("msg", "saved");
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getHealthCards(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getPerformaService().getHealthCards();
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

    public void deleteHealthCard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteHealthCard(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getHealthCardById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cardId = request.getParameter("cardId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getPerformaService().getHealthCardById(cardId);
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

    public void saveAppointmentFee(HttpServletRequest request, HttpServletResponse response, PerformaVO vo) throws IOException {
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        vo.setUserName(userName);
        vo.setProcedureFeeId(request.getParameterValues("selectedId[]"));
        vo.setFeeAmount(request.getParameterValues("feeArr[]"));
        JSONObject obj = new JSONObject();
        boolean flag = this.serviceFactory.getPerformaService().saveAppointmentFee(vo);
        if (flag) {
            obj.put("msg", "saved");
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }

    public void saveCollectedFee(HttpServletRequest request, HttpServletResponse response, PerformaVO vo) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        vo.setUserName(userName);
        vo.setCompanyId(companyId);
        JSONObject obj = new JSONObject();
        boolean flag = this.serviceFactory.getPerformaService().saveCollectedFee(vo);
        if (flag) {
            obj.put("msg", "saved");
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }

    public ModelAndView viewInvoice(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String id = request.getParameter("id");
        map.put("fees", this.serviceFactory.getPerformaService().getProcedureFeeForAppointment(id));
        return new ModelAndView("setup/feeInvoice", "refData", map);
    }

    public void getProcedureFeeForAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorName = request.getParameter("doctorList");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getPerformaService().getProcedureFeeForAppointment(doctorName);
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

    public void deleteAppointmentProcedure(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteAppointmentProcedure(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public ModelAndView printPrescription(HttpServletRequest request, HttpServletResponse response) {
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
        return new ModelAndView("performa/viewPrescription", "refData", map);
    }

    public void getAvailablePanelCompanyForDoctors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        String doctorClinicId = request.getParameter("doctorClinicId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getPerformaService().getAvailablePanelCompanyForDoctors(doctorId);
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

    public void getProcedureFeeById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String procedureId = request.getParameter("procedureId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getPerformaService().getProcedureFeeById(procedureId);
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

    public void deleteDoctorProcedure(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteDoctorProcedure(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getMarginsByDoctorId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getPerformaService().getMarginsByDoctorId(doctorId);
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

    public void getPatientFeeHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        String clinicId = request.getParameter("clinicId");
        String doctorId = request.getParameter("doctorId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getPerformaService().getPatientFeeHistory(patientId, doctorId, clinicId);
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

    // pharma company
    public ModelAndView addPharmacyCompany(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Pharmacy Company");
        map.put("rightName", "Pharmacy Company");
        return new ModelAndView("performa/addPharmacyCompany", "refData", map);
    }

    public void savePharmacyCompany(HttpServletRequest request, HttpServletResponse response, Pharma p) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        p.setUserName(userName);
        String attachmentPath = request.getServletContext().getRealPath("/upload/pharmacy/logo/");
        boolean flag = this.serviceFactory.getPerformaService().savePharmacyCompany(p, attachmentPath);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getPharmacyCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = this.serviceFactory.getPerformaService().getPharmacyCompany();
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

    public void deletePharmacyCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deletePharmacyCompany(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getPharmacyCompanyById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getPerformaService().getPharmacyCompanyById(id);
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

    // pharma Store
    public ModelAndView addPharmacyStore(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Pharmacy Store");
        map.put("cities", this.serviceFactory.getClinicService().getCitysOfPakistan());
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("MEDICAL_STORE")) {
            String pharmaId = user.getMedicalPharmacyId();
            map.put("companies", this.serviceFactory.getPerformaService().getPharmacyCompany(pharmaId));
        } else {
            map.put("companies", this.serviceFactory.getPerformaService().getPharmacyCompany());
        }
        map.put("discounts", this.serviceFactory.getDoctorService().getDiscountCategory("PHARMACY"));
        map.put("rightName", "Pharmacy Store");

        return new ModelAndView("performa/addPharmacyStore", "refData", map);
    }

    public void savePharmacyStore(HttpServletRequest request, HttpServletResponse response, Pharma p) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        p.setUserName(userName);
        boolean flag = this.serviceFactory.getPerformaService().savePharmacyStore(p);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getPharmacyStore(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pharmaId = request.getParameter("pharmaId");
        List<Map> list = this.serviceFactory.getPerformaService().getPharmacyStore(pharmaId);
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

    public void deletePharmacyStore(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deletePharmacyStore(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getPharmacyStoreById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getPerformaService().getPharmacyStoreById(id);
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

    // Delivery Order
    public ModelAndView viewDeliveryOrder(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Delivery Order");
        map.put("companies", this.serviceFactory.getPerformaService().getPharmacyCompany());
//        map.put("cities", this.serviceFactory.getClinicService().getCitysOfPakistan());
        return new ModelAndView("performa/viewDeliveryOrder", "refData", map);
    }

    // Medical Lab
    public ModelAndView addMedicalLab(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Medical Lab");
        
        map.put("rightName", "Medical Lab");
        return new ModelAndView("performa/addMedicalLab", "refData", map);
    }

    public void saveMedicalLab(HttpServletRequest request, HttpServletResponse response, Lab l) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        l.setUserName(userName);
        String attachmentPath = request.getServletContext().getRealPath("/upload/lab/logo/");
        boolean flag = this.serviceFactory.getPerformaService().saveMedicalLab(l, attachmentPath);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getMedicalLab(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = this.serviceFactory.getPerformaService().getMedicalLab();
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

    public void deleteMedicalLab(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteMedicalLab(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getMedicalLabById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getPerformaService().getMedicalLabById(id);
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

    // pharma Store
    public ModelAndView addLabCollectionCenter(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Collection Center");
        map.put("cities", this.serviceFactory.getClinicService().getCitysOfPakistan());
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("COLLECTION_CENTER")) {
            String pharmaId = user.getMedicalPharmacyId();
            map.put("lab", this.serviceFactory.getPerformaService().getMedicalLab(pharmaId));
        } else {
            map.put("lab", this.serviceFactory.getPerformaService().getMedicalLab());
        }
        map.put("discounts", this.serviceFactory.getDoctorService().getDiscountCategory("LABORTARY"));
        map.put("rightName", "Collection Center");
        return new ModelAndView("performa/addLabCollectionCenter", "refData", map);
    }

    public void saveLabCollectionCenter(HttpServletRequest request, HttpServletResponse response, Lab p) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        p.setUserName(userName);
        boolean flag = this.serviceFactory.getPerformaService().saveLabCollectionCenter(p);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getLabCollectionCenter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String medicalLabId = request.getParameter("medicalLabId");
        List<Map> list = this.serviceFactory.getPerformaService().getLabCollectionCenter(medicalLabId);
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

    public void deleteLabCollectionCenter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteLabCollectionCenter(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getLabCollectionCenterById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getPerformaService().getLabCollectionCenterById(id);
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

    public ModelAndView viewLabPatient(HttpServletRequest request, HttpServletResponse response) {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "View Test Requests");
        String userType = request.getSession().getAttribute("userType").toString();
        if (user != null && userType.equalsIgnoreCase("LAB")) {
            map.put("patientList", this.serviceFactory.getSetupService().getLabPatient(user.getLabMasterId(), user.getLabDetailId()));
        }
        map.put("rightName", "View Test Requests");
        return new ModelAndView("setup/viewLabPatient", "refData", map);
    }

    public void saveLabAttachment(HttpServletRequest request, HttpServletResponse response, DoctorVO vo) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        vo.setUserName(userName);
        String ReportPath = request.getServletContext().getRealPath("/upload/patient/labReports/");
        vo.setPath(ReportPath);
        boolean flag = this.serviceFactory.getPerformaService().saveLabAttachment(vo);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getLabActtachementsByPrescId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        String doctorId = request.getParameter("doctorId");
        String prescDetailId = request.getParameter("prescDetailId");
        Company com = (Company) request.getSession().getAttribute("company");
        List<Map> list = this.serviceFactory.getPerformaService().getLabActtachementsById(doctorId, patientId, prescDetailId);
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

    public void deleteLabAttachement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteLabAttachement(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    // DOSE USAGE
    public ModelAndView medicineUsage(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Medicine Usage");
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("rightName", "Medicine Usage");
        return new ModelAndView("performa/medicineUsage", "refData", map);
    }

    public void saveMedicineUsage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String specialityId = request.getParameter("specialityId");
        String titleEnglish = request.getParameter("titleEnglish");
        String titleUrdu = request.getParameter("titleUrdu");
        String doseUsageId = request.getParameter("doseUsageId");

        boolean flag = this.serviceFactory.getPerformaService().saveMedicineUsage(titleEnglish, titleUrdu, specialityId, doseUsageId);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getMedicineUsage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String specialityId = request.getParameter("specialityId");
        response.setContentType("text/json;charset=UTF-8");
        List<Map> list = this.serviceFactory.getPerformaService().getMedicineUsage(specialityId);
        List<JSONObject> objList = new ArrayList();
        JSONObject obj = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                obj = new JSONObject();
                obj.put("TITLE", map.get("TITLE") != null ? map.get("TITLE").toString() : "");
                String res = "";
                String urduTitle = map.get("TITLE_URDU") != null ? map.get("TITLE_URDU").toString() : "";
                obj.put("TITLE_URDU", urduTitle);
                obj.put("TW_DOSE_USAGE_ID", map.get("TW_DOSE_USAGE_ID") != null ? map.get("TW_DOSE_USAGE_ID").toString() : "");
                objList.add(obj);
            }
        }
        response.getWriter().write(objList.toString());
    }

    public void deleteMedicineUsage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteMedicineUsage(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getMedicineUsageById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        response.setContentType("text/json;charset=UTF-8");
        Map map = this.serviceFactory.getPerformaService().getMedicineUsageById(id);
        JSONObject obj = new JSONObject();
        if (map != null) {
            obj = new JSONObject();
            obj.put("TITLE", map.get("TITLE") != null ? map.get("TITLE").toString() : "");
            String res = "";
            String urduTitle = map.get("TITLE_URDU") != null ? map.get("TITLE_URDU").toString() : "";
            obj.put("TITLE_URDU", urduTitle);
            obj.put("TW_DOSE_USAGE_ID", map.get("TW_DOSE_USAGE_ID") != null ? map.get("TW_DOSE_USAGE_ID").toString() : "");
        }
        response.getWriter().write(obj.toString());
    }

    public void saveReadings(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject obj = new JSONObject();
        User user = (User) request.getSession().getAttribute("user");
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("DOCTOR")) {
            String doctorId = "";
            doctorId = user.getDoctorId();
            String patientId = request.getParameter("patientId");
            String sugar = request.getParameter("sugar");
            String fever = request.getParameter("fever");
            String bloodPressure = request.getParameter("bloodPressure");
            boolean flag = this.serviceFactory.getPerformaService().saveReadings(sugar, bloodPressure, fever, doctorId, patientId, user.getUserName());
            if (flag) {
                obj.put("result", "save_success");
            } else {
                obj.put("result", "save_error");
            }
        }
        response.getWriter().write(obj.toString());
    }

    public void getReading(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject obj = new JSONObject();
        User user = (User) request.getSession().getAttribute("user");
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("DOCTOR")) {
            String doctorId = "";
            doctorId = user.getDoctorId();
            String patientId = request.getParameter("patientId");
            Map map = this.serviceFactory.getPerformaService().getReading(patientId, doctorId);
            if (map != null) {
                Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
                while (itr.hasNext()) {
                    String key = itr.next().getKey();
                    obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
                }
            }
        }
        response.getWriter().write(obj.toString());
    }

    public void getAppointmentDates(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<JSONObject> objList = new ArrayList();
        User user = (User) request.getSession().getAttribute("user");
        String doctorId = request.getParameter("doctorId");
        if (doctorId == null) {
            doctorId = user.getDoctorId();
        }
        Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
        if (clinic != null) {
            String clinicId = clinic.get("TW_CLINIC_ID").toString();
            List<Map> list = this.serviceFactory.getPerformaService().getAppointmentDates(doctorId, clinicId);
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
        }

        response.getWriter().write(objList.toString());
    }

    public void getAppointedTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<JSONObject> objList = new ArrayList();
        User user = (User) request.getSession().getAttribute("user");
        String date = request.getParameter("date");
        String doctorId = request.getParameter("doctorId");
        if (doctorId == null) {
            doctorId = user.getDoctorId();
        }
        Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
        if (clinic != null) {
            String clinicId = clinic.get("TW_CLINIC_ID").toString();
            List<Map> list = this.serviceFactory.getPerformaService().getAppointedTime(doctorId, clinicId, date);
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
        }

        response.getWriter().write(objList.toString());
    }

    public ModelAndView viewPrescriptionForPatient(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        User user = (User) request.getSession().getAttribute("user");
        String patientId = "";
        if (user != null) {
            patientId = user.getPatientId();
        }
        map.put("Precription", this.serviceFactory.getPerformaService().getPrescriptionMasterForPatient(patientId));

        return new ModelAndView("performa/viewPrescriptionForPatient", "refData", map);
    }

    public ModelAndView editPatientProfile(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        User user = (User) request.getSession().getAttribute("user");
        String patientId = "";
        if (user != null) {
            patientId = user.getPatientId();
        }
        map.put("cities", this.serviceFactory.getClinicService().getCitysOfPakistan());
        map.put("patientInfo", this.serviceFactory.getSetupService().getPatientById(patientId));
        map.put("bloodGroup", this.serviceFactory.getSetupService().getBloodGroup());

        return new ModelAndView("performa/editPatientProfile", "refData", map);
    }

    //Intake Form Quetions
    public ModelAndView viewIntakeQuestion(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        Map map = this.serviceFactory.getUmsService().getUserRights(userName, "Intake Questions");
        map.put("speciality", this.serviceFactory.getPerformaService().getMedicalSpeciality());
        map.put("rightName", "Intake Questions");
        return new ModelAndView("performa/viewIntakeQuestions", "refData", map);
    }

    public void saveIntakeQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String questionMasterId = request.getParameter("questionMasterId");
        String question = request.getParameter("question");
        String specialityId = request.getParameter("specialityId");
        boolean flag = this.serviceFactory.getPerformaService().saveIntakeQuestion(questionMasterId, specialityId, question, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getIntakeQuestions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        String specialityId = request.getParameter("specialityId");
        List<Map> list = this.serviceFactory.getPerformaService().getIntakeQuestions(specialityId);
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

    public void getIntakeQuestionById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String questionMasterId = request.getParameter("id");
        Map map = this.serviceFactory.getPerformaService().getIntakeQuestionById(questionMasterId);
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

    public void deleteIntakeQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        boolean flag = this.serviceFactory.getPerformaService().deleteExaminationQuestion(id);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public ModelAndView addPatientIntake(HttpServletRequest request, HttpServletResponse response) {
        String patientId = request.getParameter("patientId");
        String patientName = request.getParameter("name");
        Map map = new HashMap();
        map.put("patientId", patientId);
        map.put("patientName", patientName);
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        //map.put("revision", this.serviceFactory.getSetupService().getRevision(patientId, user.getDoctorId()));
        map.put("question", this.serviceFactory.getPerformaService().getIntakeQuestionForDoctor(user.getDoctorId()));
        map.put("answer", this.serviceFactory.getPerformaService().getIntakeAnswers(user.getDoctorId()));
        //map.put("diseases", this.serviceFactory.getSetupService().getDiseases("Y"));
        map.put("diseases", this.serviceFactory.getClinicService().getIntakeDiseasesForDoctor(user.getDoctorId()));
        return new ModelAndView("performa/addPatientInTake", "refData", map);
    }

    public void savePatientIntake(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String patientId = request.getParameter("patientId");
        String[] questionarr = request.getParameterValues("questionarr[]");
        String[] answerarr = request.getParameterValues("answerarr[]");
        String[] diseases = request.getParameterValues("diseases[]");
        boolean flag = this.serviceFactory.getPerformaService().saveInTakeForm(patientId, user.getDoctorId(), questionarr, answerarr, diseases, userName);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }

    public void getIntakeFormData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        User user = (User) request.getSession().getAttribute("user");
        List<Map> list = this.serviceFactory.getPerformaService().getIntakeFormData(patientId, user.getDoctorId());
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

    public void getPateintVaccination(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");
        User user = (User) request.getSession().getAttribute("user");
        String doctorId = "";
        if (user != null) {
            doctorId = user.getDoctorId();
        }
        List<Map> list = this.serviceFactory.getPerformaService().getPateintVaccination(doctorId, patientId);
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

    public void getPateintVaccinationMedicine(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String masterId = request.getParameter("masterId");
        String dte = request.getParameter("date");
        List<Map> list = this.serviceFactory.getPerformaService().getPateintVaccinationMedicine(masterId, dte);
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

    public void getNextPrescriptionNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String patientId = request.getParameter("patientId");
        JSONObject obj = new JSONObject();
        Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
        if (clinic != null) {
            String clinicId = clinic.get("TW_CLINIC_ID").toString();
            Map m_ = this.serviceFactory.getPerformaService().getNextPrescriptionNumber(clinicId, user.getDoctorId(), patientId);
            obj.put("nextPrescriptionNumber", m_.get("nbr").toString());
            obj.put("masterId", m_.get("masterId").toString());
        }
        response.getWriter().write(obj.toString());
    }

    public void getVaccinationByDoctorSpecility(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String categoryId = request.getParameter("categoryId");
        User user = (User) request.getSession().getAttribute("user");
        String doctorId = "";
        if (user != null && user.getDoctorId() != null) {
            doctorId = user.getDoctorId();
        }
        List<Map> list = this.serviceFactory.getPerformaService().getVaccinationByDoctorSpecility(doctorId, categoryId);
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

    public void savePatientDiagnostics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getSession().getAttribute("userName") != null ? request.getSession().getAttribute("userName").toString() : "";
        User user = (User) request.getSession().getAttribute("user");
        String[] diagId = request.getParameterValues("diagId[]");
        String[] diagVal = request.getParameterValues("diagVal[]");
        String patientId = request.getParameter("patientId");
        String userType = request.getSession().getAttribute("userType").toString();
        PrescriptionVO vo = new PrescriptionVO();
        vo.setPatientId(patientId);
        vo.setDiagnosticsId(diagId);
        vo.setDiagnosticVal(diagVal);
        vo.setUserName(userName);
        vo.setPrescriptionNo(request.getParameter("prescriptionNo"));
        JSONObject obj = new JSONObject();
        if (userType.equalsIgnoreCase("DOCTOR")) {
            Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
            if (clinic != null) {
                String clinicId = clinic.get("TW_CLINIC_ID").toString();
                String doctorId = user.getDoctorId();
                vo.setClinicId(clinicId);
                vo.setDoctorId(doctorId);
                boolean flag = this.serviceFactory.getPerformaService().savePatientDiagnostics(vo);
                if (flag) {
                    obj.put("msg", "saved");
                } else {
                    obj.put("msg", "error");
                }
            } else {
                obj.put("msg", "no_clinic");
            }
        }
        response.getWriter().write(obj.toString());
    }

    public void getMedicineForPrescription(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String prescId = request.getParameter("prescId");
        User user = (User) request.getSession().getAttribute("user");
        String doctorId = "";
        if (user != null && user.getDoctorId() != null) {
            doctorId = user.getDoctorId();
        }
        List<Map> list = this.serviceFactory.getPerformaService().getPrescriptionForMedicine(prescId);
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

    public void getLabTestsForPrescription(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String prescId = request.getParameter("prescId");
        User user = (User) request.getSession().getAttribute("user");
        String doctorId = "";
        if (user != null && user.getDoctorId() != null) {
            doctorId = user.getDoctorId();
        }
        List<Map> list = this.serviceFactory.getPerformaService().getPrescriptionForLabTest(prescId);
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

    public ModelAndView getLabTestDetailsForDoctor(HttpServletRequest request, HttpServletResponse response) {
        String clinicId = "";
        String doctorId = "";
        User user = (User) request.getSession().getAttribute("user");
        Map map = new HashMap();
        Map clinic = (Map) request.getSession().getAttribute("selectedClinic");
        if (clinic != null) {
            clinicId = clinic.get("TW_CLINIC_ID").toString();
            doctorId = user.getDoctorId();
        }
        map.put("labReports", this.serviceFactory.getPerformaService().getLabTestDetailsForDoctor(doctorId, clinicId));
        return new ModelAndView("performa/viewLabTestDetailsForDoctor", "refData", map);
    }

    public void getPharmacyDiscounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pharmaId = request.getParameter("pharmaId");
        List<Map> list = this.serviceFactory.getPerformaService().getPharmacyDiscounts(pharmaId);
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
