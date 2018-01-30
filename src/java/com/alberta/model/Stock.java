/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.math.BigDecimal;

/**
 *
 * @author Faraz
 */
public class Stock {

    private BigDecimal id;
    private String coaCode;
    private String title;
    private String month;
    private String week;
    private BigDecimal purchaseQuantity;
    private BigDecimal returnQuantity;
    private BigDecimal saleQuantity;    
    private BigDecimal companyId;
    private BigDecimal siteId;
    private String purchaseUnit;
    private String saleUnit;
    private BigDecimal packing;
    private BigDecimal adjustedQuantity;
    private String reason;
    private BigDecimal openingQty;
    private BigDecimal closingQty;
    private BigDecimal target;
    private BigDecimal targetW1;
    private BigDecimal targetW2;
    private BigDecimal targetW3;
    private BigDecimal targetW4;
    private BigDecimal targetW5;
    private BigDecimal w1;
    private BigDecimal w2;
    private BigDecimal w3;
    private BigDecimal w4;
    private BigDecimal w5;
    private BigDecimal quantity;
    private String remarks;
    private String startDate;
    private String endDate;    
    private BigDecimal totalQuantity;
    private String dataMode;
    private BigDecimal sold;
    private String soldDate;
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
     * @return the id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BigDecimal id) {
        this.id = id;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the week
     */
    public String getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     * @return the purchaseQuantity
     */
    public BigDecimal getPurchaseQuantity() {
        return purchaseQuantity;
    }

    /**
     * @param purchaseQuantity the purchaseQuantity to set
     */
    public void setPurchaseQuantity(BigDecimal purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    /**
     * @return the saleQuantity
     */
    public BigDecimal getSaleQuantity() {
        return saleQuantity;
    }

    /**
     * @param saleQuantity the saleQuantity to set
     */
    public void setSaleQuantity(BigDecimal saleQuantity) {
        this.saleQuantity = saleQuantity;
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
     * @return the purchaseUnit
     */
    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    /**
     * @param purchaseUnit the purchaseUnit to set
     */
    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    /**
     * @return the saleUnit
     */
    public String getSaleUnit() {
        return saleUnit;
    }

    /**
     * @param saleUnit the saleUnit to set
     */
    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit;
    }

    /**
     * @return the packing
     */
    public BigDecimal getPacking() {
        return packing;
    }

    /**
     * @param packing the packing to set
     */
    public void setPacking(BigDecimal packing) {
        this.packing = packing;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the adjustedQuantity
     */
    public BigDecimal getAdjustedQuantity() {
        return adjustedQuantity;
    }

    /**
     * @param adjustedQuantity the adjustedQuantity to set
     */
    public void setAdjustedQuantity(BigDecimal adjustedQuantity) {
        this.adjustedQuantity = adjustedQuantity;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the openingQty
     */
    public BigDecimal getOpeningQty() {
        return openingQty;
    }

    /**
     * @param openingQty the openingQty to set
     */
    public void setOpeningQty(BigDecimal openingQty) {
        this.openingQty = openingQty;
    }

    /**
     * @return the closingQty
     */
    public BigDecimal getClosingQty() {
        return closingQty;
    }

    /**
     * @param closingQty the closingQty to set
     */
    public void setClosingQty(BigDecimal closingQty) {
        this.closingQty = closingQty;
    }

    /**
     * @return the returnQuantity
     */
    public BigDecimal getReturnQuantity() {
        return returnQuantity;
    }

    /**
     * @param returnQuantity the returnQuantity to set
     */
    public void setReturnQuantity(BigDecimal returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    /**
     * @return the target
     */
    public BigDecimal getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    /**
     * @return the w1
     */
    public BigDecimal getW1() {
        return w1;
    }

    /**
     * @param w1 the w1 to set
     */
    public void setW1(BigDecimal w1) {
        this.w1 = w1;
    }

    /**
     * @return the w2
     */
    public BigDecimal getW2() {
        return w2;
    }

    /**
     * @param w2 the w2 to set
     */
    public void setW2(BigDecimal w2) {
        this.w2 = w2;
    }

    /**
     * @return the w3
     */
    public BigDecimal getW3() {
        return w3;
    }

    /**
     * @param w3 the w3 to set
     */
    public void setW3(BigDecimal w3) {
        this.w3 = w3;
    }

    /**
     * @return the w4
     */
    public BigDecimal getW4() {
        return w4;
    }

    /**
     * @param w4 the w4 to set
     */
    public void setW4(BigDecimal w4) {
        this.w4 = w4;
    }

    /**
     * @return the w5
     */
    public BigDecimal getW5() {
        return w5;
    }

    /**
     * @param w5 the w5 to set
     */
    public void setW5(BigDecimal w5) {
        this.w5 = w5;
    }

    /**
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the totalQuantity
     */
    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * @param totalQuantity the totalQuantity to set
     */
    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * @return the dataMode
     */
    public String getDataMode() {
        return dataMode;
    }

    /**
     * @param dataMode the dataMode to set
     */
    public void setDataMode(String dataMode) {
        this.dataMode = dataMode;
    }

    /**
     * @return the sold
     */
    public BigDecimal getSold() {
        return sold;
    }

    /**
     * @param sold the sold to set
     */
    public void setSold(BigDecimal sold) {
        this.sold = sold;
    }

    /**
     * @return the soldDate
     */
    public String getSoldDate() {
        return soldDate;
    }

    /**
     * @param soldDate the soldDate to set
     */
    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    /**
     * @return the targetW1
     */
    public BigDecimal getTargetW1() {
        return targetW1;
    }

    /**
     * @param targetW1 the targetW1 to set
     */
    public void setTargetW1(BigDecimal targetW1) {
        this.targetW1 = targetW1;
    }

    /**
     * @return the targetW2
     */
    public BigDecimal getTargetW2() {
        return targetW2;
    }

    /**
     * @param targetW2 the targetW2 to set
     */
    public void setTargetW2(BigDecimal targetW2) {
        this.targetW2 = targetW2;
    }

    /**
     * @return the targetW3
     */
    public BigDecimal getTargetW3() {
        return targetW3;
    }

    /**
     * @param targetW3 the targetW3 to set
     */
    public void setTargetW3(BigDecimal targetW3) {
        this.targetW3 = targetW3;
    }

    /**
     * @return the targetW4
     */
    public BigDecimal getTargetW4() {
        return targetW4;
    }

    /**
     * @param targetW4 the targetW4 to set
     */
    public void setTargetW4(BigDecimal targetW4) {
        this.targetW4 = targetW4;
    }

    /**
     * @return the targetW5
     */
    public BigDecimal getTargetW5() {
        return targetW5;
    }

    /**
     * @param targetW5 the targetW5 to set
     */
    public void setTargetW5(BigDecimal targetW5) {
        this.targetW5 = targetW5;
    }
}
