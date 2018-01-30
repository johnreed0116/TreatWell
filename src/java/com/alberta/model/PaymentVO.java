/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

/**
 *
 * @author Faraz1
 */
public class PaymentVO {

    private String voucherMasterId;
    private String clientCode;
    private String chequeNo;
    private String companyId;
    private String userName;
    private String[] amount;
    private String[] outstanding;
    private String[] adjusted;
    private String[] tax;
    private String[] chequeAmount;
    private String[] taxAmount;
    private String[] masterId;

    /**
     * @return the clientCode
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     * @param clientCode the clientCode to set
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    /**
     * @return the chequeNo
     */
    public String getChequeNo() {
        return chequeNo;
    }

    /**
     * @param chequeNo the chequeNo to set
     */
    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    /**
     * @return the amount
     */
    public String[] getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String[] amount) {
        this.amount = amount;
    }

    /**
     * @return the outstanding
     */
    public String[] getOutstanding() {
        return outstanding;
    }

    /**
     * @param outstanding the outstanding to set
     */
    public void setOutstanding(String[] outstanding) {
        this.outstanding = outstanding;
    }

    /**
     * @return the adjusted
     */
    public String[] getAdjusted() {
        return adjusted;
    }

    /**
     * @param adjusted the adjusted to set
     */
    public void setAdjusted(String[] adjusted) {
        this.adjusted = adjusted;
    }

    /**
     * @return the tax
     */
    public String[] getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(String[] tax) {
        this.tax = tax;
    }

    /**
     * @return the chequeAoumnt
     */
    public String[] getChequeAmount() {
        return chequeAmount;
    }

    /**
     * @param chequeAoumnt the chequeAoumnt to set
     */
    public void setChequeAmount(String[] chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    /**
     * @return the taxAmount
     */
    public String[] getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount the taxAmount to set
     */
    public void setTaxAmount(String[] taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return the masterId
     */
    public String[] getMasterId() {
        return masterId;
    }

    /**
     * @param masterId the masterId to set
     */
    public void setMasterId(String[] masterId) {
        this.masterId = masterId;
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
     * @return the voucherMasterId
     */
    public String getVoucherMasterId() {
        return voucherMasterId;
    }

    /**
     * @param voucherMasterId the voucherMasterId to set
     */
    public void setVoucherMasterId(String voucherMasterId) {
        this.voucherMasterId = voucherMasterId;
    }
    
}
