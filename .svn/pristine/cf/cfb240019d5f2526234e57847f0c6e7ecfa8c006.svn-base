/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author abbas
 */
public class OtherBankReconciliationEntries implements Serializable, RowMapper {

    private BigDecimal othBnkRecId;
    private BigDecimal financialYearId;
    private BigDecimal siteId;    
    private String coaCode;
    private String coaTitle;
    private String partyCode;
    private String partyTitle;    
    private String chequeFavour;
    private String chequeNbr;
    private Date chequeDate;
    private Date clearanceDate;
    private BigDecimal amnt;
    private BigDecimal companyId;
    private String preparedBy;
    private Date preparedDate;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        OtherBankReconciliationEntries obr = new OtherBankReconciliationEntries();
        obr.setOthBnkRecId(rs.getBigDecimal("OTH_BNK_REC_ID"));
        obr.setFinancialYearId(rs.getBigDecimal("FIN_YEAR_ID"));
        obr.setSiteId(rs.getBigDecimal("SITE_ID"));
        obr.setCoaCode(rs.getString("COA_CDE"));
        obr.setPartyCode(rs.getString("PARTY_CDE"));
        obr.setChequeFavour(rs.getString("CHEQUE_FAVOUR"));
        obr.setChequeNbr(rs.getString("CHEQUE_NBR"));
        obr.setChequeDate((Date) rs.getDate("CHEQUE_DTE"));
        obr.setClearanceDate((Date) rs.getDate("CHQ_CLEARANCE_DTE"));
        obr.setAmnt(rs.getBigDecimal("AMNT"));
        obr.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return obr;
    }

    /**
     * @return the othBnkRecId
     */
    public BigDecimal getOthBnkRecId() {
        return othBnkRecId;
    }

    /**
     * @param othBnkRecId the othBnkRecId to set
     */
    public void setOthBnkRecId(BigDecimal othBnkRecId) {
        this.othBnkRecId = othBnkRecId;
    } 

    /**
     * @return the coaCode
     */
    public String getCoaCode() {
        return coaCode;
    }

    /**
     * @param coaCode the coaCode to set
     */
    public void setCoaCode(String coaCode) {
        this.coaCode = coaCode;
    }

    /**
     * @return the chequeFavour
     */
    public String getChequeFavour() {
        return chequeFavour;
    }

    /**
     * @param chequeFavour the chequeFavour to set
     */
    public void setChequeFavour(String chequeFavour) {
        this.chequeFavour = chequeFavour;
    }

    /**
     * @return the chequeNbr
     */
    public String getChequeNbr() {
        return chequeNbr;
    }

    /**
     * @param chequeNbr the chequeNbr to set
     */
    public void setChequeNbr(String chequeNbr) {
        this.chequeNbr = chequeNbr;
    }

    /**
     * @return the chequeDate
     */
    public Date getChequeDate() {
        return chequeDate;
    }

    /**
     * @param chequeDate the chequeDate to set
     */
    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    /**
     * @return the clearanceDate
     */
    public Date getClearanceDate() {
        return clearanceDate;
    }

    /**
     * @param clearanceDate the clearanceDate to set
     */
    public void setClearanceDate(Date clearanceDate) {
        this.clearanceDate = clearanceDate;
    }

    /**
     * @return the amnt
     */
    public BigDecimal getAmnt() {
        return amnt;
    }

    /**
     * @param amnt the amnt to set
     */
    public void setAmnt(BigDecimal amnt) {
        this.amnt = amnt;
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
     * @return the coaTitle
     */
    public String getCoaTitle() {
        return coaTitle;
    }

    /**
     * @param coaTitle the coaTitle to set
     */
    public void setCoaTitle(String coaTitle) {
        this.coaTitle = coaTitle;
    }

    /**
     * @return the partyCode
     */
    public String getPartyCode() {
        return partyCode;
    }

    /**
     * @param partyCode the partyCode to set
     */
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    /**
     * @return the partyTitle
     */
    public String getPartyTitle() {
        return partyTitle;
    }

    /**
     * @param partyTitle the partyTitle to set
     */
    public void setPartyTitle(String partyTitle) {
        this.partyTitle = partyTitle;
    }

    /**
     * @return the financialYearId
     */
    public BigDecimal getFinancialYearId() {
        return financialYearId;
    }

    /**
     * @param financialYearId the financialYearId to set
     */
    public void setFinancialYearId(BigDecimal financialYearId) {
        this.financialYearId = financialYearId;
    }

    /**
     * @return the siteId
     */
    public BigDecimal getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(BigDecimal siteId) {
        this.siteId = siteId;
    }

    /**
     * @return the preparedBy
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     * @param preparedBy the preparedBy to set
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     * @return the preparedDate
     */
    public Date getPreparedDate() {
        return preparedDate;
    }

    /**
     * @param preparedDate the preparedDate to set
     */
    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }
    
}
