/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.sms;

import com.alberta.dao.DAO;
import com.alberta.utility.Util;
import java.util.List;
import java.util.Map;

/**
 *
 * @author farazahmad
 */
public class SmsServiceImpl implements SmsService {

    private DAO dao;

    /**
     * @return the dao
     */
    @Override
    public DAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    @Override
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean sendAppointmentMessage(String appointmentId) {
        boolean flag = false;
        try {
            String query = "SELECT PAT.PATIENT_NME,PAT.MOBILE_NO,CL.CLINIC_NME,DOC.DOCTOR_NME,TO_CHAR(APP.APPOINTMENT_DTE,'DD-MON-YY')  APPOINTMENT_DTE,"
                    + " TO_CHAR(APP.APPOINTMENT_TIME,'HH:MI AM')  APPOINTMENT_TIME,CI.CITY_NME,CA.AREA_NME"
                    + " FROM TW_APPOINTMENT APP,TW_DOCTOR DOC,TW_PATIENT PAT,TW_CLINIC CL,CITY CI,CITY_AREA CA"
                    + " WHERE APP.TW_PATIENT_ID=PAT.TW_PATIENT_ID"
                    + " AND APP.TW_DOCTOR_ID=DOC.TW_DOCTOR_ID"
                    + " AND APP.TW_CLINIC_ID=CL.TW_CLINIC_ID"
                    + " AND APP.TW_APPOINTMENT_ID=" + appointmentId + ""
                    + " AND CL.CITY_ID=CI.CITY_ID"
                    + " AND CL.CITY_AREA_ID=CA.CITY_AREA_ID";
            List<Map> l = this.dao.getData(query);
            if (l != null && l.size() > 0) {
                Map map = l.get(0);
                String dateTime = map.get("APPOINTMENT_DTE").toString() + " " + map.get("APPOINTMENT_TIME").toString();
                String clinicName = map.get("CLINIC_NME").toString() + ", " + map.get("AREA_NME").toString() + ", " + map.get("CITY_NME").toString();
                String message = "Your appoinment with " + map.get("DOCTOR_NME").toString() + " has been scheduled at " + dateTime + " at " + clinicName + ". Kindly download our mobile app EZIMEDIC to schedule your appointments.";
                Util.generateSms(map.get("MOBILE_NO").toString(), message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
