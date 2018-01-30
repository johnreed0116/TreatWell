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
public class PrescriptionVO {

    private String prescriptionMasterId;
    private String patientId;
    private String remarks;
    private String doctorId;
    private String clinicId;
    private String[] medicineId;
    private String[] days;
    private String[] qty;
    private String[] frequencyId;
    private String[] usageId;
    private String[] labId;
    private String[] labTestId;
    private String[] labCenterId;
    private String userName;

    /**
     * @return the prescriptionMasterId
     */
    public String getPrescriptionMasterId() {
        return prescriptionMasterId;
    }

    /**
     * @param prescriptionMasterId the prescriptionMasterId to set
     */
    public void setPrescriptionMasterId(String prescriptionMasterId) {
        this.prescriptionMasterId = prescriptionMasterId;
    }

    public String[] getLabCenterId() {
        return labCenterId;
    }

    public void setLabCenterId(String[] labCenterId) {
        this.labCenterId = labCenterId;
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
     * @return the medicineId
     */
    public String[] getMedicineId() {
        return medicineId;
    }

    /**
     * @param medicineId the medicineId to set
     */
    public void setMedicineId(String[] medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * @return the days
     */
    public String[] getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(String[] days) {
        this.days = days;
    }

    /**
     * @return the qty
     */
    public String[] getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(String[] qty) {
        this.qty = qty;
    }

    /**
     * @return the frequencyId
     */
    public String[] getFrequencyId() {
        return frequencyId;
    }

    /**
     * @param frequencyId the frequencyId to set
     */
    public void setFrequencyId(String[] frequencyId) {
        this.frequencyId = frequencyId;
    }

    /**
     * @return the usageId
     */
    public String[] getUsageId() {
        return usageId;
    }

    /**
     * @param usageId the usageId to set
     */
    public void setUsageId(String[] usageId) {
        this.usageId = usageId;
    }

    /**
     * @return the labId
     */
    public String[] getLabId() {
        return labId;
    }

    /**
     * @param labId the labId to set
     */
    public void setLabId(String[] labId) {
        this.labId = labId;
    }

    /**
     * @return the labTestId
     */
    public String[] getLabTestId() {
        return labTestId;
    }

    /**
     * @param labTestId the labTestId to set
     */
    public void setLabTestId(String[] labTestId) {
        this.labTestId = labTestId;
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

}
