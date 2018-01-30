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
 * @author Administrator
 */
public class DynamicTab {
    private String dynamicTabMasterId;
    private String dynamicTabBasedOnId;
    private String reportNbr;
    private String reportName;
    private String basedOn;
    private String sourceCol;
    private String companyId;    
    private String coaCode;
    private String coaTitle;
    private BigDecimal amount;
    
    /**
     * @return the dynamicTabMasterId
     */
    public String getDynamicTabMasterId() {
        return dynamicTabMasterId;
    }

    /**
     * @param dynamicTabMasterId the dynamicTabMasterId to set
     */
    public void setDynamicTabMasterId(String dynamicTabMasterId) {
        this.dynamicTabMasterId = dynamicTabMasterId;
    }

    /**
     * @return the reportNbr
     */
    public String getReportNbr() {
        return reportNbr;
    }

    /**
     * @param reportNbr the reportNbr to set
     */
    public void setReportNbr(String reportNbr) {
        this.reportNbr = reportNbr;
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the basedOn
     */
    public String getBasedOn() {
        return basedOn;
    }

    /**
     * @param basedOn the basedOn to set
     */
    public void setBasedOn(String basedOn) {
        this.basedOn = basedOn;
    }

    /**
     * @return the sourceCol
     */
    public String getSourceCol() {
        return sourceCol;
    }

    /**
     * @param sourceCol the sourceCol to set
     */
    public void setSourceCol(String sourceCol) {
        this.sourceCol = sourceCol;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the dynamicTabBasedOnId
     */
    public String getDynamicTabBasedOnId() {
        return dynamicTabBasedOnId;
    }

    /**
     * @param dynamicTabBasedOnId the dynamicTabBasedOnId to set
     */
    public void setDynamicTabBasedOnId(String dynamicTabBasedOnId) {
        this.dynamicTabBasedOnId = dynamicTabBasedOnId;
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
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
}
