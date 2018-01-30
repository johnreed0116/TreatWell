/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.sms;

import com.alberta.dao.DAO;

/**
 *
 * @author farazahmad
 */
public interface SmsService {

    /**
     * @return the dao
     */
    DAO getDao();

    /**
     * @param dao the dao to set
     */
    void setDao(DAO dao);
    
}
