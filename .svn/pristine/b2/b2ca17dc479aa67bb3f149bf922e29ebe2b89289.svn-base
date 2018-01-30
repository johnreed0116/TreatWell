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
public class VoucherSubType implements Serializable, RowMapper {

    private BigDecimal subTypeId;
    private String subTypeDesc;
    private String subTypeTitle;
    private String subType;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VoucherSubType vs = new VoucherSubType();
        vs.setSubTypeId(rs.getBigDecimal("VOUCHER_SUB_TYP_ID"));
        vs.setSubTypeDesc(rs.getString("VOUCHER_SUB_TYP_DESC"));
        vs.setSubTypeTitle(rs.getString("VOUCHER_SUB_TYP_TITLE"));
        vs.setSubType(rs.getString("VOUCHER_TYP"));
        vs.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return vs;
    }

    /**
     * @return the subTypeId
     */
    public BigDecimal getSubTypeId() {
        return subTypeId;
    }

    /**
     * @param subTypeId the subTypeId to set
     */
    public void setSubTypeId(BigDecimal subTypeId) {
        this.subTypeId = subTypeId;
    }

    /**
     * @return the subTypeDesc
     */
    public String getSubTypeDesc() {
        return subTypeDesc;
    }

    /**
     * @param subTypeDesc the subTypeDesc to set
     */
    public void setSubTypeDesc(String subTypeDesc) {
        this.subTypeDesc = subTypeDesc;
    }

    /**
     * @return the subType
     */
    public String getSubType() {
        return subType;
    }

    /**
     * @param subType the subType to set
     */
    public void setSubType(String subType) {
        this.subType = subType;
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

    /**
     * @return the subTypeTitle
     */
    public String getSubTypeTitle() {
        return subTypeTitle;
    }

    /**
     * @param subTypeTitle the subTypeTitle to set
     */
    public void setSubTypeTitle(String subTypeTitle) {
        this.subTypeTitle = subTypeTitle;
    }
}
