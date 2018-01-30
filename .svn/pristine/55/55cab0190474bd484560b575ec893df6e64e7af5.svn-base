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
public class InventoryCoa implements Serializable, RowMapper {

    private String coaCode;
    private String parentCode;
    private String chartOfAccount;
    private String title;
    private String description;
    private BigDecimal coaTypeId;
    private BigDecimal levelNo;
    private String entryLevelInd;
    private String itemType;
    private BigDecimal minLevel;
    private BigDecimal packing;
    private String saleUnit;
    private String purchaseUnit;
    private BigDecimal siteId;
    private String activeInd;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        InventoryCoa coa = new InventoryCoa();
        coa.setCoaCode(rs.getString("COA_CDE"));
        coa.setParentCode(rs.getString("PARENT_CDE"));
        coa.setChartOfAccount(rs.getString("CHART_OF_ACCOUNT"));
        coa.setTitle(rs.getString("TITLE"));
        coa.setDescription(rs.getString("COA_DESC"));
        coa.setCoaTypeId(rs.getBigDecimal("COA_TYPE_ID"));
        coa.setLevelNo(rs.getBigDecimal("LEVEL_NBR"));
        coa.setEntryLevelInd(rs.getString("ENTRY_LEVEL_IND"));
        coa.setItemType(rs.getString("ITEM_TYP"));
        coa.setMinLevel(rs.getBigDecimal("MIN_LEVEL"));
        coa.setPacking(rs.getBigDecimal("PACKING"));
        coa.setPurchaseUnit(rs.getString("PUR_UNIT"));
        coa.setSaleUnit(rs.getString("SAL_UNIT"));
        return coa;
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
     * @return the parentCode
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * @param parentCode the parentCode to set
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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
     * @return the coaTypeId
     */
    public BigDecimal getCoaTypeId() {
        return coaTypeId;
    }

    /**
     * @param coaTypeId the coaTypeId to set
     */
    public void setCoaTypeId(BigDecimal coaTypeId) {
        this.coaTypeId = coaTypeId;
    }

    /**
     * @return the levelNo
     */
    public BigDecimal getLevelNo() {
        return levelNo;
    }

    /**
     * @param levelNo the levelNo to set
     */
    public void setLevelNo(BigDecimal levelNo) {
        this.levelNo = levelNo;
    }

    /**
     * @return the entryLevelInd
     */
    public String getEntryLevelInd() {
        return entryLevelInd;
    }

    /**
     * @param entryLevelInd the entryLevelInd to set
     */
    public void setEntryLevelInd(String entryLevelInd) {
        this.entryLevelInd = entryLevelInd;
    }

    /**
     * @return the itemType
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * @return the minLevel
     */
    public BigDecimal getMinLevel() {
        return minLevel;
    }

    /**
     * @param minLevel the minLevel to set
     */
    public void setMinLevel(BigDecimal minLevel) {
        this.minLevel = minLevel;
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
     * @return the chartOfAccount
     */
    public String getChartOfAccount() {
        return chartOfAccount;
    }

    /**
     * @param chartOfAccount the chartOfAccount to set
     */
    public void setChartOfAccount(String chartOfAccount) {
        this.chartOfAccount = chartOfAccount;
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
}
