/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.math.BigDecimal;

/**
 *
 * @author Abbas
 */
public class ManagementAccounts {
    private BigDecimal managementAccountId;
    private BigDecimal seqNbr;
    private String title;
    private String displayTxt;
    private BigDecimal companyId;

    /**
     * @return the managementAccountId
     */
    public BigDecimal getManagementAccountId() {
        return managementAccountId;
    }

    /**
     * @param managementAccountId the managementAccountId to set
     */
    public void setManagementAccountId(BigDecimal managementAccountId) {
        this.managementAccountId = managementAccountId;
    }

    /**
     * @return the seqNbr
     */
    public BigDecimal getSeqNbr() {
        return seqNbr;
    }

    /**
     * @param seqNbr the seqNbr to set
     */
    public void setSeqNbr(BigDecimal seqNbr) {
        this.seqNbr = seqNbr;
    }

    /**
     * @return the Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param Title the Title to set
     */
    public void setTitle(String Title) {
        this.title = Title;
    }

    /**
     * @return the displayTxt
     */
    public String getDisplayTxt() {
        return displayTxt;
    }

    /**
     * @param displayTxt the displayTxt to set
     */
    public void setDisplayTxt(String displayTxt) {
        this.displayTxt = displayTxt;
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
