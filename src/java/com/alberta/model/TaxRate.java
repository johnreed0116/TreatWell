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
public class TaxRate implements Serializable, RowMapper {

    private BigDecimal taxId;
    private String taxName;
    private BigDecimal taxRate;
    private String taxCode;
    private String taxSection;
    private String coaCode;
    private String coaTitle;
    private String fromDate;
    private String toDate;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        TaxRate taxRate = new TaxRate();
        taxRate.setTaxId(rs.getBigDecimal("TAX_ID"));
        taxRate.setTaxName(rs.getString("TAX_TITLE"));
        taxRate.setTaxRate(rs.getBigDecimal("TAX_RTE"));
        taxRate.setTaxCode(rs.getString("TAX_CDE"));
        taxRate.setTaxSection(rs.getString("TAX_SECTION"));
        taxRate.setCoaCode(rs.getString("COA_CDE"));
        taxRate.setFromDate(rs.getString("FROM_DTE"));
        taxRate.setToDate(rs.getString("TO_DTE"));
        taxRate.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return taxRate;
    }

    /**
     * @return the taxId
     */
    public BigDecimal getTaxId() {
        return taxId;
    }

    /**
     * @param taxId the taxId to set
     */
    public void setTaxId(BigDecimal taxId) {
        this.taxId = taxId;
    }

    /**
     * @return the taxName
     */
    public String getTaxName() {
        return taxName;
    }

    /**
     * @param taxName the taxName to set
     */
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return the taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    /**
     * @return the taxSection
     */
    public String getTaxSection() {
        return taxSection;
    }

    /**
     * @param taxSection the taxSection to set
     */
    public void setTaxSection(String taxSection) {
        this.taxSection = taxSection;
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
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
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
}
