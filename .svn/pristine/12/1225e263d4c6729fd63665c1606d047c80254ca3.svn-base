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
public class BankBalance implements Serializable, RowMapper {

    private BigDecimal bankBalanceId;
    private BigDecimal financialYearId;
    private BigDecimal siteId; 
    private String siteTitle;
    private String coaCode;
    private String coaTitle;
    private Date balanceDate;
    private BigDecimal amnt;
    private BigDecimal companyId;
    private String preparedBy;
    private String fileName;
    private Date preparedDate;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        BankBalance bb = new BankBalance();
        bb.setBankBalanceId(rs.getBigDecimal("BANK_BALANCE_ID"));
        bb.setFinancialYearId(rs.getBigDecimal("FIN_YEAR_ID"));
        bb.setSiteId(rs.getBigDecimal("SITE_ID"));
        bb.setCoaCode(rs.getString("COA_CDE"));
        bb.setBalanceDate((java.util.Date) rs.getDate("BAL_DTE"));
        bb.setAmnt(rs.getBigDecimal("AMNT"));
        bb.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return bb;
    }

    /**
     * @return the bankBalanceId
     */
    public BigDecimal getBankBalanceId() {
        return bankBalanceId;
    }

    /**
     * @param bankBalanceId the bankBalanceId to set
     */
    public void setBankBalanceId(BigDecimal bankBalanceId) {
        this.bankBalanceId = bankBalanceId;
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
     * @return the balanceDate
     */
    public Date getBalanceDate() {
        return balanceDate;
    }

    /**
     * @param balanceDate the balanceDate to set
     */
    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
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
     * @return the siteTitle
     */
    public String getSiteTitle() {
        return siteTitle;
    }

    /**
     * @param siteTitle the siteTitle to set
     */
    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
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

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
