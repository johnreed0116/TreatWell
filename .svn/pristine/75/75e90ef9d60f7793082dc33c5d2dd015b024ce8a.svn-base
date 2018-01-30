/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Administrator
 */
public class FuelDip implements Serializable, RowMapper {

    private BigDecimal fuelDipId;
    private BigDecimal finYearId;
    private BigDecimal siteId;
    private String siteTitle;
    private String coaCode;
    private String coaTitle;
    private Date dipDate;
    private String voucherDate;
    private BigDecimal openingQty;
    private BigDecimal closingQty;
    private BigDecimal purchaseQty;
    private BigDecimal saleQty;
    private BigDecimal amount;
    private BigDecimal rate;
    private BigDecimal target;
    private String preparedby;
    private String preparedDate;
    private BigDecimal companyId;
    private String voucherType;
    private BigDecimal quantity;
    private BigDecimal totalQuantity;
    private BigDecimal totalValue;
    private BigDecimal totalSaleQuantity;
    private BigDecimal costPrice;
    private BigDecimal beforeCostPrice;
    private BigDecimal costValue;
    private BigDecimal dieselMargin;
    private BigDecimal regularMargin;
    private BigDecimal superMargin;
    private BigDecimal cardCharges;
    private BigDecimal openingBalance;
    private BigDecimal regularCostPrice;
    private BigDecimal superCostPrice;
    private BigDecimal recipeSuperQty;
    private BigDecimal recipeSuperAmnt;
    private BigDecimal recipeSuperValue;
    private BigDecimal recipeRegularQty;
    private BigDecimal recipeRegularAmnt;
    private BigDecimal recipeRegularValue;    
    private BigDecimal avgSellingPrice;
    private BigDecimal avgCostPrice; 
    private String itemCoaCode;
    private BigDecimal differenceQty;
    
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        FuelDip fd = new FuelDip();
        fd.setFuelDipId(rs.getBigDecimal("FUEL_DIP_ID"));
        fd.setFinYearId(rs.getBigDecimal("FIN_YEAR_ID"));
        fd.setSiteId(rs.getBigDecimal("SITE_ID"));
        fd.setCoaCode(rs.getString("COA_CDE"));
        fd.setDipDate((java.util.Date) rs.getDate("DIP_DTE"));
        fd.setClosingQty(rs.getBigDecimal("CLOSING_QTY"));
        fd.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return fd;
    }

    /**
     * @return the fuelDipId
     */
    public BigDecimal getFuelDipId() {
        return fuelDipId;
    }

    /**
     * @param fuelDipId the fuelDipId to set
     */
    public void setFuelDipId(BigDecimal fuelDipId) {
        this.fuelDipId = fuelDipId;
    }

    /**
     * @return the finYearId
     */
    public BigDecimal getFinYearId() {
        return finYearId;
    }

    /**
     * @param finYearId the finYearId to set
     */
    public void setFinYearId(BigDecimal finYearId) {
        this.finYearId = finYearId;
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
     * @return the dipDate
     */
    public Date getDipDate() {
        return dipDate;
    }

    /**
     * @param dipDate the dipDate to set
     */
    public void setDipDate(Date dipDate) {
        this.dipDate = dipDate;
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
     * @return the siteTitle
     */
    public String getSiteTitle() {
        return siteTitle;
    }

    /**
     * @param siteTitle the siteTitle to set
     */
    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
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
     * @return the coaTitle
     */
    public String getCoaTitle() {
        return coaTitle;
    }

    /**
     * @param coaTitle the coaTitle to set
     */
    public void setCoaTitle(String coaTitle) {
        this.coaTitle = coaTitle;
    }

    /**
     * @return the purchaseQty
     */
    public BigDecimal getPurchaseQty() {
        return purchaseQty;
    }

    /**
     * @param purchaseQty the purchaseQty to set
     */
    public void setPurchaseQty(BigDecimal purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    /**
     * @return the saleQty
     */
    public BigDecimal getSaleQty() {
        return saleQty;
    }

    /**
     * @param saleQty the saleQty to set
     */
    public void setSaleQty(BigDecimal saleQty) {
        this.saleQty = saleQty;
    }

    /**
     * @return the voucherDate
     */
    public String getVoucherDate() {
        return voucherDate;
    }

    /**
     * @param voucherDate the voucherDate to set
     */
    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
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
     * @return the preparedby
     */
    public String getPreparedby() {
        return preparedby;
    }

    /**
     * @param preparedby the preparedby to set
     */
    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    /**
     * @return the preparedDate
     */
    public String getPreparedDate() {
        return preparedDate;
    }

    /**
     * @param preparedDate the preparedDate to set
     */
    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
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
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return the voucherType
     */
    public String getVoucherType() {
        return voucherType;
    }

    /**
     * @param voucherType the voucherType to set
     */
    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
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
     * @return the totalValue
     */
    public BigDecimal getTotalValue() {
        return totalValue;
    }

    /**
     * @param totalValue the totalValue to set
     */
    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    /**
     * @return the costPrice
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * @param costPrice the costPrice to set
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * @return the beforeCostPrice
     */
    public BigDecimal getBeforeCostPrice() {
        return beforeCostPrice;
    }

    /**
     * @param beforeCostPrice the beforeCostPrice to set
     */
    public void setBeforeCostPrice(BigDecimal beforeCostPrice) {
        this.beforeCostPrice = beforeCostPrice;
    }

    /**
     * @return the costValue
     */
    public BigDecimal getCostValue() {
        return costValue;
    }

    /**
     * @param costValue the costValue to set
     */
    public void setCostValue(BigDecimal costValue) {
        this.costValue = costValue;
    }

    /**
     * @return the dieselMargin
     */
    public BigDecimal getDieselMargin() {
        return dieselMargin;
    }

    /**
     * @param dieselMargin the dieselMargin to set
     */
    public void setDieselMargin(BigDecimal dieselMargin) {
        this.dieselMargin = dieselMargin;
    }

    /**
     * @return the regularMargin
     */
    public BigDecimal getRegularMargin() {
        return regularMargin;
    }

    /**
     * @param regularMargin the regularMargin to set
     */
    public void setRegularMargin(BigDecimal regularMargin) {
        this.regularMargin = regularMargin;
    }

    /**
     * @return the superMargin
     */
    public BigDecimal getSuperMargin() {
        return superMargin;
    }

    /**
     * @param superMargin the superMargin to set
     */
    public void setSuperMargin(BigDecimal superMargin) {
        this.superMargin = superMargin;
    }

    /**
     * @return the cardCharges
     */
    public BigDecimal getCardCharges() {
        return cardCharges;
    }

    /**
     * @param cardCharges the cardCharges to set
     */
    public void setCardCharges(BigDecimal cardCharges) {
        this.cardCharges = cardCharges;
    }

    /**
     * @return the openingBalance
     */
    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    /**
     * @param openingBalance the openingBalance to set
     */
    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    /**
     * @return the regularCostPrice
     */
    public BigDecimal getRegularCostPrice() {
        return regularCostPrice;
    }

    /**
     * @param regularCostPrice the regularCostPrice to set
     */
    public void setRegularCostPrice(BigDecimal regularCostPrice) {
        this.regularCostPrice = regularCostPrice;
    }

    /**
     * @return the superCostPrice
     */
    public BigDecimal getSuperCostPrice() {
        return superCostPrice;
    }

    /**
     * @param superCostPrice the superCostPrice to set
     */
    public void setSuperCostPrice(BigDecimal superCostPrice) {
        this.superCostPrice = superCostPrice;
    }

    /**
     * @return the recipeSuperQty
     */
    public BigDecimal getRecipeSuperQty() {
        return recipeSuperQty;
    }

    /**
     * @param recipeSuperQty the recipeSuperQty to set
     */
    public void setRecipeSuperQty(BigDecimal recipeSuperQty) {
        this.recipeSuperQty = recipeSuperQty;
    }

    /**
     * @return the recipeSuperAmnt
     */
    public BigDecimal getRecipeSuperAmnt() {
        return recipeSuperAmnt;
    }

    /**
     * @param recipeSuperAmnt the recipeSuperAmnt to set
     */
    public void setRecipeSuperAmnt(BigDecimal recipeSuperAmnt) {
        this.recipeSuperAmnt = recipeSuperAmnt;
    }

    /**
     * @return the recipeSuperValue
     */
    public BigDecimal getRecipeSuperValue() {
        return recipeSuperValue;
    }

    /**
     * @param recipeSuperValue the recipeSuperValue to set
     */
    public void setRecipeSuperValue(BigDecimal recipeSuperValue) {
        this.recipeSuperValue = recipeSuperValue;
    }

    /**
     * @return the recipeRegularQty
     */
    public BigDecimal getRecipeRegularQty() {
        return recipeRegularQty;
    }

    /**
     * @param recipeRegularQty the recipeRegularQty to set
     */
    public void setRecipeRegularQty(BigDecimal recipeRegularQty) {
        this.recipeRegularQty = recipeRegularQty;
    }

    /**
     * @return the recipeRegularAmnt
     */
    public BigDecimal getRecipeRegularAmnt() {
        return recipeRegularAmnt;
    }

    /**
     * @param recipeRegularAmnt the recipeRegularAmnt to set
     */
    public void setRecipeRegularAmnt(BigDecimal recipeRegularAmnt) {
        this.recipeRegularAmnt = recipeRegularAmnt;
    }

    /**
     * @return the recipeRegularValue
     */
    public BigDecimal getRecipeRegularValue() {
        return recipeRegularValue;
    }

    /**
     * @param recipeRegularValue the recipeRegularValue to set
     */
    public void setRecipeRegularValue(BigDecimal recipeRegularValue) {
        this.recipeRegularValue = recipeRegularValue;
    }

    /**
     * @return the totalSaleQuantity
     */
    public BigDecimal getTotalSaleQuantity() {
        return totalSaleQuantity;
    }

    /**
     * @param totalSaleQuantity the totalSaleQuantity to set
     */
    public void setTotalSaleQuantity(BigDecimal totalSaleQuantity) {
        this.totalSaleQuantity = totalSaleQuantity;
    }

    /**
     * @return the avgSellingPrice
     */
    public BigDecimal getAvgSellingPrice() {
        return avgSellingPrice;
    }

    /**
     * @param avgSellingPrice the avgSellingPrice to set
     */
    public void setAvgSellingPrice(BigDecimal avgSellingPrice) {
        this.avgSellingPrice = avgSellingPrice;
    }

    /**
     * @return the avgCostPrice
     */
    public BigDecimal getAvgCostPrice() {
        return avgCostPrice;
    }

    /**
     * @param avgCostPrice the avgCostPrice to set
     */
    public void setAvgCostPrice(BigDecimal avgCostPrice) {
        this.avgCostPrice = avgCostPrice;
    }

    /**
     * @return the itemCoaCode
     */
    public String getItemCoaCode() {
        return itemCoaCode;
    }

    /**
     * @param itemCoaCode the itemCoaCode to set
     */
    public void setItemCoaCode(String itemCoaCode) {
        this.itemCoaCode = itemCoaCode;
    }

    /**
     * @return the differenceQty
     */
    public BigDecimal getDifferenceQty() {
        return differenceQty;
    }

    /**
     * @param differenceQty the differenceQty to set
     */
    public void setDifferenceQty(BigDecimal differenceQty) {
        this.differenceQty = differenceQty;
    }

       
}
