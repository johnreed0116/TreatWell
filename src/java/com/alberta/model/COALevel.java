/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Faraz
 */
public class COALevel implements Serializable, RowMapper {

    private Integer levelId;
    private Integer levelNo;
    private String levelCode;
    private String levelDesc;
    private Integer companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        COALevel coa = new COALevel();
        coa.setLevelId(rs.getInt("COA_LEVEL_ID"));
        coa.setLevelCode(rs.getString("COA_LEVEL_CDE"));
        coa.setLevelDesc(rs.getString("COA_LEVEL_DESC"));
        coa.setLevelNo(rs.getInt("COA_LEVEL_NBR"));
        coa.setCompanyId(rs.getInt("COMPANY_ID"));
        return coa;
    }

    /**
     * @return the levelId
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the levelNo
     */
    public Integer getLevelNo() {
        return levelNo;
    }

    /**
     * @param levelNo the levelNo to set
     */
    public void setLevelNo(Integer levelNo) {
        this.levelNo = levelNo;
    }

    /**
     * @return the levelCode
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @param levelCode the levelCode to set
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * @return the levelDesc
     */
    public String getLevelDesc() {
        return levelDesc;
    }

    /**
     * @param levelDesc the levelDesc to set
     */
    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    /**
     * @return the companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
