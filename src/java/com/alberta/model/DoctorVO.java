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
public class DoctorVO {

    /**
     * @return the patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    private String doctorId;
    private String patientId;
    private String doctorName;
    private String cnic;
    private String doctorAddress;
    private String dateOfBirth;
    private String cellNo;
    private String email;
    private String doctorType;
    private String gender;
    private String newUserName;
    private String userPassword;
    private String[] services;
    private String[] diseases;
    private String servicesAvail;
    private String userName;
    private String company;
    private String companyId;
    private String degreeId;
    private String procedureId;
    private String residentialCityId;
    private String residentialCountryId;
    private String aboutDoctor;
    private String totalExperience;
    private String lastDegreeId;
    private String attachmentOf;
    private String[] specility;
    private String link;
    private String prescriptionLang;
    private String consultancyFee;
    private String procedureFeeId;
    private String showIntakeForm;
    private String videoTimeFrom;
    private String videoTimeTo;
    private String doctorAttachmentType;
    private String path;
    private String pmdcNo;
    private MultipartFile profileImage;
    private MultipartFile visitingCardImage;
    

    /**
     * @clinic Fields
     */
    private String clinicId;
    private String clinicName;
    private String phoneNo;
    private String mapQuardinates;
    private String clinicAddress;
    private String areaId;
    private String prescDetailId;

    /**
     * @clinic Doctor's Services Fields
     */
    private String serviceId;
    private String fee;
    private String discount;
    private String[] fees;
    private String[] discounts;

    /**
     * @clinic Doctor's Fee Procedure Fields
     */
    private String[] panelId;
    private String panelCompanyId;

    /**
     * @Health Card feilds
     */
    private String healthCardId;
    private String cardName;
    private String salePrice;
    private String doctorsDiscount;
    private String productsDiscount;
    private String noOfVisits;
    private String additionalFeatures;
    private String availableFor;

    /**
     * @Doctor Attachment Field
     */
    private String attachDescription;
    private String attachType;
    private MultipartFile file;

    /**
     * @Doctor Education Field
     */
    private String medicalDegreeId;
    private String doctorEducationId;
    private String medicalCollegeId;
    private String durationEduFrom;
    private String durationEduTo;
    private String countryId;

    /**
     * @Doctor Experience Field
     */
    private String jobTitle;
    private String hospitalId;
    private String durationExpFrom;
    private String durationExpTo;

    /**
     * @Doctor Association Field
     */
    private String associationId;

    /**
     * @Diseases Field
     */
    private String diseaseName;
    private String diseaseId;

    /**
     * @Hospital Field
     */
    private String hospitalName;
    private String cityId;
    private String hospitalAddress;
    private String provinceId;

    /**
     * @Degree Field
     */
    private String degreeName;
    private String abbreviation;

    /**
     * @Country Field
     */
    private String countryName;
    private String cityName;

    /**
     * @Medical Services Field
     */
    private String serviceName;

    /**
     * @Medical College Field
     */
    private String medicalCollegeName;

    /**
     * @Lab Test Field
     */
    private String labTestId;
    private String labTestName;

    /**
     * @Lab Test Field
     */
    private String medicinesId;
    private String medicineName;
    private String genericName;
    private String manufacturer;
    private String testGroupId;

    /**
     * @Message Field
     */
    private String messageId;
    private String message;
    private String subject;

    /**
     * @Print Layout Field
     */
    private String layoutId;
    private String marginTop;
    private String marginBottom;
    private MultipartFile headerLogo;
    private MultipartFile footerLogo;
    /**
     * @State Field
     */
    private String stateId;
    private String stateName;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getConsultancyFee() {
        return consultancyFee;
    }

    public void setConsultancyFee(String consultancyFee) {
        this.consultancyFee = consultancyFee;
    }

    public String getAttachmentOf() {
        return attachmentOf;
    }

    public void setAttachmentOf(String attachmentOf) {
        this.attachmentOf = attachmentOf;
    }

    public String getAttachDescription() {
        return attachDescription;
    }

    public void setAttachDescription(String attachDescription) {
        this.attachDescription = attachDescription;
    }

    public String[] getSpecility() {
        return specility;
    }

    public void setSpecility(String[] specility) {
        this.specility = specility;
    }

    public String getServicesAvail() {
        return servicesAvail;
    }

    public String getPrescriptionLang() {
        return prescriptionLang;
    }

    public void setPrescriptionLang(String prescriptionLang) {
        this.prescriptionLang = prescriptionLang;
    }

    public void setServicesAvail(String servicesAvail) {
        this.servicesAvail = servicesAvail;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getVideoTimeFrom() {
        return videoTimeFrom;
    }

    public void setVideoTimeFrom(String videoTimeFrom) {
        this.videoTimeFrom = videoTimeFrom;
    }

    public String getVideoTimeTo() {
        return videoTimeTo;
    }

    public String getTestGroupId() {
        return testGroupId;
    }

    public void setTestGroupId(String testGroupId) {
        this.testGroupId = testGroupId;
    }

    public void setVideoTimeTo(String videoTimeTo) {
        this.videoTimeTo = videoTimeTo;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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
     * @return the doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @param doctorName the doctorName to set
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * @return the cnic
     */
    public String getCnic() {
        return cnic;
    }

    /**
     * @param cnic the cnic to set
     */
    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    /**
     * @return the doctorAddress
     */
    public String getDoctorAddress() {
        return doctorAddress;
    }

    /**
     * @param doctorAddress the doctorAddress to set
     */
    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    /**
     * @return the dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProcedureFeeId() {
        return procedureFeeId;
    }

    public void setProcedureFeeId(String procedureFeeId) {
        this.procedureFeeId = procedureFeeId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDoctorEducationId() {
        return doctorEducationId;
    }

    public void setDoctorEducationId(String doctorEducationId) {
        this.doctorEducationId = doctorEducationId;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    public String[] getPanelId() {
        return panelId;
    }

    public void setPanelId(String[] panelId) {
        this.panelId = panelId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the services
     */
    public String[] getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(String[] services) {
        this.services = services;
    }

    public String[] getDiseases() {
        return diseases;
    }

    public void setDiseases(String[] diseases) {
        this.diseases = diseases;
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
     * @return the doctorType
     */
    public String getDoctorType() {
        return doctorType;
    }

    /**
     * @param doctorType the doctorType to set
     */
    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
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
     * @return the clinicId
     */
    public String getClinicId() {
        return clinicId;
    }

    /**
     * @param clinicId the clinicId to set
     */
    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    /**
     * @return the clinicName
     */
    public String getClinicName() {
        return clinicName;
    }

    /**
     * @param clinicName the clinicName to set
     */
    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
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
     * @return the mapQuardinates
     */
    public String getMapQuardinates() {
        return mapQuardinates;
    }

    /**
     * @param mapQuardinates the mapQuardinates to set
     */
    public void setMapQuardinates(String mapQuardinates) {
        this.mapQuardinates = mapQuardinates;
    }

    /**
     * @return the clinicAddress
     */
    public String getClinicAddress() {
        return clinicAddress;
    }

    /**
     * @param clinicAddress the clinicAddress to set
     */
    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    /**
     * @return the serviceId
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the fee
     */
    public String getFee() {
        return fee;
    }

    public String[] getFees() {
        return fees;
    }

    public void setFees(String[] fees) {
        this.fees = fees;
    }

    public String[] getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String[] discounts) {
        this.discounts = discounts;
    }

    public String getPanelCompanyId() {
        return panelCompanyId;
    }

    public void setPanelCompanyId(String panelCompanyId) {
        this.panelCompanyId = panelCompanyId;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(String fee) {
        this.fee = fee;
    }

    /**
     * @return the healthCardId
     */
    public String getHealthCardId() {
        return healthCardId;
    }

    /**
     * @param healthCardId the healthCardId to set
     */
    public void setHealthCardId(String healthCardId) {
        this.healthCardId = healthCardId;
    }

    /**
     * @return the cardName
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * @param cardName the cardName to set
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * @return the salePrice
     */
    public String getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice the salePrice to set
     */
    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * @return the doctorsDiscount
     */
    public String getDoctorsDiscount() {
        return doctorsDiscount;
    }

    /**
     * @param doctorsDiscount the doctorsDiscount to set
     */
    public void setDoctorsDiscount(String doctorsDiscount) {
        this.doctorsDiscount = doctorsDiscount;
    }

    /**
     * @return the productsDiscount
     */
    public String getProductsDiscount() {
        return productsDiscount;
    }

    /**
     * @param productsDiscount the productsDiscount to set
     */
    public void setProductsDiscount(String productsDiscount) {
        this.productsDiscount = productsDiscount;
    }

    /**
     * @return the noOfVisits
     */
    public String getNoOfVisits() {
        return noOfVisits;
    }

    /**
     * @param noOfVisits the noOfVisits to set
     */
    public void setNoOfVisits(String noOfVisits) {
        this.noOfVisits = noOfVisits;
    }

    /**
     * @return the additionalFeatures
     */
    public String getAdditionalFeatures() {
        return additionalFeatures;
    }

    /**
     * @param additionalFeatures the additionalFeatures to set
     */
    public void setAdditionalFeatures(String additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }

    /**
     * @return the availableFor
     */
    public String getAvailableFor() {
        return availableFor;
    }

    /**
     * @param availableFor the availableFor to set
     */
    public void setAvailableFor(String availableFor) {
        this.availableFor = availableFor;
    }

    /**
     * @return the degreeId
     */
    public String getDegreeId() {
        return degreeId;
    }

    /**
     * @param degreeId the degreeId to set
     */
    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    /**
     * @return the procedureId
     */
    public String getProcedureId() {
        return procedureId;
    }

    /**
     * @param procedureId the procedureId to set
     */
    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    /**
     * @return the medicalDegreeId
     */
    public String getMedicalDegreeId() {
        return medicalDegreeId;
    }

    /**
     * @param medicalDegreeId the medicalDegreeId to set
     */
    public void setMedicalDegreeId(String medicalDegreeId) {
        this.medicalDegreeId = medicalDegreeId;
    }

    /**
     * @return the medicalCollegeId
     */
    public String getMedicalCollegeId() {
        return medicalCollegeId;
    }

    /**
     * @param medicalCollegeId the medicalCollegeId to set
     */
    public void setMedicalCollegeId(String medicalCollegeId) {
        this.medicalCollegeId = medicalCollegeId;
    }

    /**
     * @return the durationEduFrom
     */
    public String getDurationEduFrom() {
        return durationEduFrom;
    }

    /**
     * @param durationEduFrom the durationEduFrom to set
     */
    public void setDurationEduFrom(String durationEduFrom) {
        this.durationEduFrom = durationEduFrom;
    }

    /**
     * @return the durationEduTo
     */
    public String getDurationEduTo() {
        return durationEduTo;
    }

    /**
     * @param durationEduTo the durationEduTo to set
     */
    public void setDurationEduTo(String durationEduTo) {
        this.durationEduTo = durationEduTo;
    }

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the durationExpFrom
     */
    public String getDurationExpFrom() {
        return durationExpFrom;
    }

    /**
     * @param durationExpFrom the durationExpFrom to set
     */
    public void setDurationExpFrom(String durationExpFrom) {
        this.durationExpFrom = durationExpFrom;
    }

    /**
     * @return the durationExpTo
     */
    public String getDurationExpTo() {
        return durationExpTo;
    }

    /**
     * @param durationExpTo the durationExpTo to set
     */
    public void setDurationExpTo(String durationExpTo) {
        this.durationExpTo = durationExpTo;
    }

    /**
     * @return the hospitalId
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * @param hospitalId the hospitalId to set
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * @return the associationId
     */
    public String getAssociationId() {
        return associationId;
    }

    /**
     * @param associationId the associationId to set
     */
    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    /**
     * @return the residentialCityId
     */
    public String getResidentialCityId() {
        return residentialCityId;
    }

    /**
     * @param residentialCityId the residentialCityId to set
     */
    public void setResidentialCityId(String residentialCityId) {
        this.residentialCityId = residentialCityId;
    }

    /**
     * @return the residentialCountryId
     */
    public String getResidentialCountryId() {
        return residentialCountryId;
    }

    /**
     * @param residentialCountryId the residentialCountryId to set
     */
    public void setResidentialCountryId(String residentialCountryId) {
        this.residentialCountryId = residentialCountryId;
    }

    /**
     * @return the aboutDoctor
     */
    public String getAboutDoctor() {
        return aboutDoctor;
    }

    /**
     * @param aboutDoctor the aboutDoctor to set
     */
    public void setAboutDoctor(String aboutDoctor) {
        this.aboutDoctor = aboutDoctor;
    }

    /**
     * @return the totalExperience
     */
    public String getTotalExperience() {
        return totalExperience;
    }

    /**
     * @param totalExperience the totalExperience to set
     */
    public void setTotalExperience(String totalExperience) {
        this.totalExperience = totalExperience;
    }

    /**
     * @return the lastDegreeId
     */
    public String getLastDegreeId() {
        return lastDegreeId;
    }

    /**
     * @param lastDegreeId the lastDegreeId to set
     */
    public void setLastDegreeId(String lastDegreeId) {
        this.lastDegreeId = lastDegreeId;
    }

    /**
     * @return the diseaseName
     */
    public String getDiseaseName() {
        return diseaseName;
    }

    /**
     * @param diseaseName the diseaseName to set
     */
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
     * @return the diseaseId
     */
    public String getDiseaseId() {
        return diseaseId;
    }

    /**
     * @param diseaseId the diseaseId to set
     */
    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    /**
     * @return the hospitalName
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * @param hospitalName the hospitalName to set
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * @return the cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the hospitalAddress
     */
    public String getHospitalAddress() {
        return hospitalAddress;
    }

    /**
     * @param hospitalAddress the hospitalAddress to set
     */
    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * @return the degreeName
     */
    public String getDegreeName() {
        return degreeName;
    }

    /**
     * @param degreeName the degreeName to set
     */
    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the medicalCollegeName
     */
    public String getMedicalCollegeName() {
        return medicalCollegeName;
    }

    /**
     * @param medicalCollegeName the medicalCollegeName to set
     */
    public void setMedicalCollegeName(String medicalCollegeName) {
        this.medicalCollegeName = medicalCollegeName;
    }

    /**
     * @return the labTestId
     */
    public String getLabTestId() {
        return labTestId;
    }

    /**
     * @param labTestId the labTestId to set
     */
    public void setLabTestId(String labTestId) {
        this.labTestId = labTestId;
    }

    /**
     * @return the labTestName
     */
    public String getLabTestName() {
        return labTestName;
    }

    /**
     * @param labTestName the labTestName to set
     */
    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    /**
     * @return the medicineName
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * @param medicineName the medicineName to set
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * @return the genericName
     */
    public String getGenericName() {
        return genericName;
    }

    /**
     * @param genericName the genericName to set
     */
    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the medicinesId
     */
    public String getMedicinesId() {
        return medicinesId;
    }

    /**
     * @param medicinesId the medicinesId to set
     */
    public void setMedicinesId(String medicinesId) {
        this.medicinesId = medicinesId;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public MultipartFile getHeaderLogo() {
        return headerLogo;
    }

    public void setHeaderLogo(MultipartFile headerLogo) {
        this.headerLogo = headerLogo;
    }

    public MultipartFile getFooterLogo() {
        return footerLogo;
    }

    public void setFooterLogo(MultipartFile footerLogo) {
        this.footerLogo = footerLogo;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the marginTop
     */
    public String getMarginTop() {
        return marginTop;
    }

    /**
     * @param marginTop the marginTop to set
     */
    public void setMarginTop(String marginTop) {
        this.marginTop = marginTop;
    }

    /**
     * @return the marginBottom
     */
    public String getMarginBottom() {
        return marginBottom;
    }

    /**
     * @param marginBottom the marginBottom to set
     */
    public void setMarginBottom(String marginBottom) {
        this.marginBottom = marginBottom;
    }

    /**
     * @return the layoutId
     */
    public String getLayoutId() {
        return layoutId;
    }

    /**
     * @param layoutId the layoutId to set
     */
    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * @return the showIntakeForm
     */
    public String getShowIntakeForm() {
        return showIntakeForm;
    }

    /**
     * @param showIntakeForm the showIntakeForm to set
     */
    public void setShowIntakeForm(String showIntakeForm) {
        this.showIntakeForm = showIntakeForm;
    }

    /**
     * @return the doctorAttachmentType
     */
    public String getDoctorAttachmentType() {
        return doctorAttachmentType;
    }

    /**
     * @param doctorAttachmentType the doctorAttachmentType to set
     */
    public void setDoctorAttachmentType(String doctorAttachmentType) {
        this.doctorAttachmentType = doctorAttachmentType;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the prescDetailId
     */
    public String getPrescDetailId() {
        return prescDetailId;
    }

    /**
     * @param prescDetailId the prescDetailId to set
     */
    public void setPrescDetailId(String prescDetailId) {
        this.prescDetailId = prescDetailId;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the pmdcNo
     */
    public String getPmdcNo() {
        return pmdcNo;
    }

    /**
     * @param pmdcNo the pmdcNo to set
     */
    public void setPmdcNo(String pmdcNo) {
        this.pmdcNo = pmdcNo;
    }

    /**
     * @return the profileImage
     */
    public MultipartFile getProfileImage() {
        return profileImage;
    }

    /**
     * @param profileImage the profileImage to set
     */
    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * @return the visitingCardImage
     */
    public MultipartFile getVisitingCardImage() {
        return visitingCardImage;
    }

    /**
     * @param visitingCardImage the visitingCardImage to set
     */
    public void setVisitingCardImage(MultipartFile visitingCardImage) {
        this.visitingCardImage = visitingCardImage;
    }

}
