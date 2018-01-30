/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Faraz
 */
public class BusinessUnit implements Serializable, RowMapper {

    private BigDecimal buUnitId;
    private String buName;
    private String buAbbrev;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        BusinessUnit obj = new BusinessUnit();
        obj.setBuUnitId(rs.getBigDecimal("BUSINESS_UNIT_ID"));
        obj.setBuName(rs.getString("BU_NME"));
        obj.setBuAbbrev(rs.getString("BU_ABBREV"));
        obj.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return obj;
    }

    /**
     * @return the buUnitId
     */
    public BigDecimal getBuUnitId() {
        return buUnitId;
    }

    /**
     * @param buUnitId the buUnitId to set
     */
    public void setBuUnitId(BigDecimal buUnitId) {
        this.buUnitId = buUnitId;
    }

    /**
     * @return the buName
     */
    public String getBuName() {
        return buName;
    }

    /**
     * @param buName the buName to set
     */
    public void setBuName(String buName) {
        this.buName = buName;
    }

    /**
     * @return the buAbbrev
     */
    public String getBuAbbrev() {
        return buAbbrev;
    }

    /**
     * @param buAbbrev the buAbbrev to set
     */
    public void setBuAbbrev(String buAbbrev) {
        this.buAbbrev = buAbbrev;
    }

    /**
     * @return the companyId
     */
    public BigDecimal getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(BigDecimal companyId) {
        this.companyId = companyId;
    }
}
