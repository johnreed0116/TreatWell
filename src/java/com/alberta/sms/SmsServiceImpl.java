/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.sms;

import com.alberta.dao.DAO;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
                CloseableHttpClient httpclient = HttpClients.createDefault();
                String dateTime = map.get("APPOINTMENT_DTE").toString() + " " + map.get("APPOINTMENT_TIME").toString();
                String clinicName = map.get("CLINIC_NME").toString() + ", " + map.get("AREA_NME").toString() + ", " + map.get("CITY_NME").toString();
                String message = "Your appoinment with " + map.get("DOCTOR_NME").toString() + " has been scheduled at " + dateTime + " at " + clinicName + ". Kindly download our mobile app EZIMEDIC to schedule your appointments.";
                String url = "http://pk.eocean.us/APIManagement/API/RequestAPI?user=TWS&pwd=ANreowHdVt%2fbvT6ubUCK01SuOXWcxjM5H2QOUH1MUdnBh1fhqiq4kWFJjPctIAFSlA%3d%3d&sender=TWS&response=string";
                HttpGet httpGet = new HttpGet(url);
                List<NameValuePair> nvps = new ArrayList<>();
                nvps.add(new BasicNameValuePair("reciever", map.get("MOBILE_NO").toString()));
                nvps.add(new BasicNameValuePair("msg-data", message));
                URI uri = new URIBuilder(httpGet.getURI()).addParameters(nvps).build();
                httpGet.setURI(uri);
                CloseableHttpResponse response = httpclient.execute(httpGet);
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                EntityUtils.consume(entity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
