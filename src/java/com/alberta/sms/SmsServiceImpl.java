/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.sms;

import com.alberta.dao.DAO;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
