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
public class Site implements Serializable, RowMapper {

    private BigDecimal siteId;
    private String siteName;
    private String siteAbbrevation;
    private String siteManager;
    private String faxNo;
    private String postalcode;
    private String email;
    private String url;
    private String address;
    private String coordinates;
    private BigDecimal employeeId;
    private BigDecimal cityId;
    private BigDecimal companyId;
    private String phoneNo;
    private BigDecimal businessUnitId;
    private String basedOn;
    private String activeInd;
    private String operationalInd;
    private String siteCategory;
    private String description;
    private String showOnWeb;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Site site = new Site();
        site.setSiteId(rs.getBigDecimal("SITE_ID"));
        site.setSiteName(rs.getString("SITE_NME"));
        site.setSiteAbbrevation(rs.getString("ABBREV"));
        site.setSiteManager(rs.getString("SITE_MANAGER"));
        site.setFaxNo(rs.getString("FAX_NBR"));
        site.setPostalcode(rs.getString("POSTAL_CDE"));
        site.setEmail(rs.getString("EMAIL_TXT"));
        site.setUrl(rs.getString("URL_TXT"));
        // site.setEmployeeId(rs.getBigDecimal("EMPLOYEE_ID"));
        site.setCityId(rs.getBigDecimal("CITY_ID"));
        site.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        site.setPhoneNo(rs.getString("PHONE_NBR"));
        site.setBasedOn(rs.getString("BASED_ON"));
        site.setActiveInd(rs.getString("ACTIVE_IND"));
        site.setOperationalInd(rs.getString("OPERATIONAL_IND"));
        site.setSiteCategory(rs.getString("SITE_CATEGORY"));
        site.setCoordinates(rs.getString("SITE_COORDINATES"));
        site.setAddress(rs.getString("SITE_ADDRESS"));
        site.setDescription(rs.getString("SITE_DESC"));
        site.setShowOnWeb(rs.getString("SHOW_ON_WEB"));
        return site;
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
     * @return the siteAbbrevation
     */
    public String getSiteAbbrevation() {
        return siteAbbrevation;
    }

    /**
     * @param siteAbbrevation the siteAbbrevation to set
     */
    public void setSiteAbbrevation(String siteAbbrevation) {
        this.siteAbbrevation = siteAbbrevation;
    }

    /**
     * @return the siteManager
     */
    public String getSiteManager() {
        return siteManager;
    }

    /**
     * @param siteManager the siteManager to set
     */
    public void setSiteManager(String siteManager) {
        this.siteManager = siteManager;
    }

    /**
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }

    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    /**
     * @return the postalcode
     */
    public String getPostalcode() {
        return postalcode;
    }

    /**
     * @param postalcode the postalcode to set
     */
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the employeeId
     */
    public BigDecimal getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(BigDecimal employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the cityId
     */
    public BigDecimal getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
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
     * @return the phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return the businessUnitId
     */
    public BigDecimal getBusinessUnitId() {
        return businessUnitId;
    }

    /**
     * @param businessUnitId the businessUnitId to set
     */
    public void setBusinessUnitId(BigDecimal businessUnitId) {
        this.businessUnitId = businessUnitId;
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
     * @return the activeInd
     */
    public String getActiveInd() {
        return activeInd;
    }

    /**
     * @param activeInd the activeInd to set
     */
    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    /**
     * @return the operationalInd
     */
    public String getOperationalInd() {
        return operationalInd;
    }

    /**
     * @param operationalInd the operationalInd to set
     */
    public void setOperationalInd(String operationalInd) {
        this.operationalInd = operationalInd;
    }

    /**
     * @return the siteCategory
     */
    public String getSiteCategory() {
        return siteCategory;
    }

    /**
     * @param siteCategory the siteCategory to set
     */
    public void setSiteCategory(String siteCategory) {
        this.siteCategory = siteCategory;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the coordinates
     */
    public String getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the showOnWeb
     */
    public String getShowOnWeb() {
        return showOnWeb;
    }

    /**
     * @param showOnWeb the showOnWeb to set
     */
    public void setShowOnWeb(String showOnWeb) {
        this.showOnWeb = showOnWeb;
    }
}
