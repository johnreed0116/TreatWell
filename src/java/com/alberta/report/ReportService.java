/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.report;

import com.alberta.dao.DAO;
import java.sql.ResultSet;

/**
 *
 * @author farazahmad
 */
public interface ReportService {

    /**
     * @return the dao
     */
    DAO getDao();

    /**
     * @param dao the dao to set
     */
    void setDao(DAO dao);
    
    ResultSet getPerformaById(String performaMasterId, String companyId);
}
