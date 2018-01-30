/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Faraz
 */
public class DailyShiftReconciliation {

    private BigDecimal reconId;
    private Date reconDate;
    private String reconDateText;
    private String preparedBy;
    private Date preparedDate;
    private String siteName;
    private BigDecimal siteId;
    private String jvInd;
    private String piInd;
    private String siInd;
    private String basedOn;

    /**
     * @return the reconId
     */
    public BigDecimal getReconId() {
        return reconId;
    }

    /**
     * @param reconId the reconId to set
     */
    public void setReconId(BigDecimal reconId) {
        this.reconId = reconId;
    }

    /**
     * @return the reconDate
     */
    public Date getReconDate() {
        return reconDate;
    }

    /**
     * @param reconDate the reconDate to set
     */
    public void setReconDate(Date reconDate) {
        this.reconDate = reconDate;
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
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
     * @return the reconDateText
     */
    public String getReconDateText() {
        return reconDateText;
    }

    /**
     * @param reconDateText the reconDateText to set
     */
    public void setReconDateText(String reconDateText) {
        this.reconDateText = reconDateText;
    }

    /**
     * @return the jvInd
     */
    public String getJvInd() {
        return jvInd;
    }

    /**
     * @param jvInd the jvInd to set
     */
    public void setJvInd(String jvInd) {
        this.jvInd = jvInd;
    }

    /**
     * @return the piInd
     */
    public String getPiInd() {
        return piInd;
    }

    /**
     * @param piInd the piInd to set
     */
    public void setPiInd(String piInd) {
        this.piInd = piInd;
    }

    /**
     * @return the siInd
     */
    public String getSiInd() {
        return siInd;
    }

    /**
     * @param siInd the siInd to set
     */
    public void setSiInd(String siInd) {
        this.siInd = siInd;
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
}
