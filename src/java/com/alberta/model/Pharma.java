/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Cori 5
 */
public class Pharma {
    
     private String pharmaId;
    private String companyName;
    private String contactPerson;
    private String designation;
    private String email;
    private String cellNo;
    private String ptclNo;
    private String companyAddress;
    private String newUserName;
    private String userName;
    private String userPassword;
    private String company;
    private String companyId;
    private String companiesId;
    private String panelCompanyId;
    private String panelId;
    private String doctorId;
    private String expiryDate;
    private String activeIndicator;
    private String webUrl;
    private MultipartFile logoFile;
    
    
    // STORE FIELDS
    
    private String pharmaStoreId;
    private String storeName;
    private String cityId;
    private String areaId;
    private String timeFrom;
    private String timeTo;
    private String loginId;

    
    
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getPharmaStoreId() {
        return pharmaStoreId;
    }

    public void setPharmaStoreId(String pharmaStoreId) {
        this.pharmaStoreId = pharmaStoreId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }
    
    
     

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public MultipartFile getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(MultipartFile logoFile) {
        this.logoFile = logoFile;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the contactPerson
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * @param contactPerson the contactPerson to set
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
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
     * @return the cellNo
     */
    public String getCellNo() {
        return cellNo;
    }

    /**
     * @param cellNo the cellNo to set
     */
    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    /**
     * @return the ptclNo
     */
    public String getPtclNo() {
        return ptclNo;
    }

    /**
     * @param ptclNo the ptclNo to set
     */
    public void setPtclNo(String ptclNo) {
        this.ptclNo = ptclNo;
    }

    /**
     * @return the companyAddress
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * @param companyAddress the companyAddress to set
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
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
     * @return the newUserName
     */
    public String getNewUserName() {
        return newUserName;
    }

    /**
     * @param newUserName the newUserName to set
     */
    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    /**
     * @return the pharmaId
     */
    public String getPharmaId() {
        return pharmaId;
    }

    /**
     * @param pharmaId the pharmaId to set
     */
    public void setPharmaId(String pharmaId) {
        this.pharmaId = pharmaId;
    }

    /**
     * @return the companiesId
     */
    public String getCompaniesId() {
        return companiesId;
    }

    /**
     * @param companiesId the companiesId to set
     */
    public void setCompaniesId(String companiesId) {
        this.companiesId = companiesId;
    }

    /**
     * @return the panelCompanyId
     */
    public String getPanelCompanyId() {
        return panelCompanyId;
    }

    /**
     * @param panelCompanyId the panelCompanyId to set
     */
    public void setPanelCompanyId(String panelCompanyId) {
        this.panelCompanyId = panelCompanyId;
    }

    /**
     * @return the panelId
     */
    public String getPanelId() {
        return panelId;
    }

    /**
     * @param panelId the panelId to set
     */
    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    /**
     * @return the doctorId
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * @param doctorId the doctorId to set
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * @return the expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the activeIndicator
     */
    public String getActiveIndicator() {
        return activeIndicator;
    }

    /**
     * @param activeIndicator the activeIndicator to set
     */
    public void setActiveIndicator(String activeIndicator) {
        this.activeIndicator = activeIndicator;
    }
    
}
