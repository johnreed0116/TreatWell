/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

/**
 *
 * @author farazahmad
 */
public class PerformaVO {

    private String appointmentId;
    private String appointmentDate;
    private String appointmentTime;
    private String doctorId;
    private String patientId;
    private String clinicId;
    private String remarks;
    private String feeCollected;
    private String userName;
    private String companyId;

    private String[] procedureFeeId;
    private String[] feeAmount;
    private String[] discountAmount;

    /**
     * @return the appointmentId
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the appointmentDate
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @param appointmentDate the appointmentDate to set
     */
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * @return the appointmentTime
     */
    public String getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * @param appointmentTime the appointmentTime to set
     */
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
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
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * @return the procedureFeeId
     */
    public String[] getProcedureFeeId() {
        return procedureFeeId;
    }

    /**
     * @param procedureFeeId the procedureFeeId to set
     */
    public void setProcedureFeeId(String[] procedureFeeId) {
        this.procedureFeeId = procedureFeeId;
    }

    /**
     * @return the feeAmount
     */
    public String[] getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param feeAmount the feeAmount to set
     */
    public void setFeeAmount(String[] feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * @return the discountAmount
     */
    public String[] getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discountAmount the discountAmount to set
     */
    public void setDiscountAmount(String[] discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @return the feeCollected
     */
    public String getFeeCollected() {
        return feeCollected;
    }

    /**
     * @param feeCollected the feeCollected to set
     */
    public void setFeeCollected(String feeCollected) {
        this.feeCollected = feeCollected;
    }

}
