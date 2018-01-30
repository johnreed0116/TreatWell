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
 * @author abbas
 */
public class ChequeTemplateMaster implements Serializable, RowMapper {

    private BigDecimal chqTempMstrId;
    private String nme;
    private BigDecimal chqHeight;
    private BigDecimal chqWidth;
    private BigDecimal bankId;
    private BigDecimal companyId;
    private String bankName;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ChequeTemplateMaster chqMaster = new ChequeTemplateMaster();
        chqMaster.setChqTempMstrId(rs.getBigDecimal("CHQ_TEMP_MSTR_ID"));
        chqMaster.setNme(rs.getString("NME"));
        chqMaster.setChqHeight(rs.getBigDecimal("CHQ_HEIGHT"));
        chqMaster.setChqWidth(rs.getBigDecimal("CHQ_WIDTH"));
        chqMaster.setBankId(rs.getBigDecimal("BANK_ID"));
        chqMaster.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        chqMaster.setBankName(rs.getString("BANK_NME"));
        return chqMaster;
    }

    /**
     * @return the chqTempMstrId
     */
    public BigDecimal getChqTempMstrId() {
        return chqTempMstrId;
    }

    /**
     * @param chqTempMstrId the chqTempMstrId to set
     */
    public void setChqTempMstrId(BigDecimal chqTempMstrId) {
        this.chqTempMstrId = chqTempMstrId;
    }

    /**
     * @return the nme
     */
    public String getNme() {
        return nme;
    }

    /**
     * @param nme the nme to set
     */
    public void setNme(String nme) {
        this.nme = nme;
    }

    /**
     * @return the chqHeight
     */
    public BigDecimal getChqHeight() {
        return chqHeight;
    }

    /**
     * @param chqHeight the chqHeight to set
     */
    public void setChqHeight(BigDecimal chqHeight) {
        this.chqHeight = chqHeight;
    }

    /**
     * @return the chqWidth
     */
    public BigDecimal getChqWidth() {
        return chqWidth;
    }

    /**
     * @param chqWidth the chqWidth to set
     */
    public void setChqWidth(BigDecimal chqWidth) {
        this.chqWidth = chqWidth;
    }

    /**
     * @return the bankId
     */
    public BigDecimal getBankId() {
        return bankId;
    }

    /**
     * @param bankId the bankId to set
     */
    public void setBankId(BigDecimal bankId) {
        this.bankId = bankId;
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
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
