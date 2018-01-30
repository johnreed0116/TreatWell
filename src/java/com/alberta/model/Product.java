/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import javax.mail.Multipart;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Cori 5
 */
public class Product {
    
   
    private String productName;
    private String productType;
    private String productGenericName;
    private String temperatureRequirement;
    private String[] multiSelectDiseases;
    private String productFeatures;
    private String userName; 
    private String pharmaCompanyId;
    private String companyId;
    private String productId;
    private String therapauticClass;
    private MultipartFile medicineImage;

   /**
     * @MedicineRep Fields
     */
    private String pharmaRepId;
    private String fullName;
    private String contactNo;
    private String designation;
    private String countryId;
    private String cityId;
    private String[] brickId;

    
    
    public String getPharmaCompanyId() {
        return pharmaCompanyId;
    }

    public String[] getBrickId() {
        return brickId;
    }

    public void setBrickId(String[] brickId) {
        this.brickId = brickId;
    }
    
    public void setPharmaCompanyId(String pharmaCompanyId) {
        this.pharmaCompanyId = pharmaCompanyId;
    }

    public MultipartFile getMedicineImage() {
        return medicineImage;
    }

    public void setMedicineImage(MultipartFile medicineImage) {
        this.medicineImage = medicineImage;
    }
    

    public String getPharmaRepId() {
        return pharmaRepId;
    }

    public void setPharmaRepId(String pharmaRepId) {
        this.pharmaRepId = pharmaRepId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return the productGenericName
     */
    public String getProductGenericName() {
        return productGenericName;
    }

    /**
     * @param productGenericName the productGenericName to set
     */
    public void setProductGenericName(String productGenericName) {
        this.productGenericName = productGenericName;
    }

   
   

    /**
     * @return the productFeatures
     */
    public String getProductFeatures() {
        return productFeatures;
    }

    /**
     * @param productFeatures the productFeatures to set
     */
    public void setProductFeatures(String productFeatures) {
        this.productFeatures = productFeatures;
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
     * @return the temperatureRequirement
     */
    public String getTemperatureRequirement() {
        return temperatureRequirement;
    }

    /**
     * @param temperatureRequirement the temperatureRequirement to set
     */
    public void setTemperatureRequirement(String temperatureRequirement) {
        this.temperatureRequirement = temperatureRequirement;
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
     * @return the multiSelectDiseases
     */
    public String[] getMultiSelectDiseases() {
        return multiSelectDiseases;
    }

    /**
     * @param multiSelectDiseases the multiSelectDiseases to set
     */
    public void setMultiSelectDiseases(String[] multiSelectDiseases) {
        this.multiSelectDiseases = multiSelectDiseases;
    }

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the therapauticClass
     */
    public String getTherapauticClass() {
        return therapauticClass;
    }

    /**
     * @param therapauticClass the therapauticClass to set
     */
    public void setTherapauticClass(String therapauticClass) {
        this.therapauticClass = therapauticClass;
    }

    /**
     * @return the temperatureRequirement
     */
   
    
    
    
    
    
    
    
    
    
}
