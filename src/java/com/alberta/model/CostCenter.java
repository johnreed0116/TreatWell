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
public class CostCenter implements Serializable, RowMapper {

    private String costCenterCode;
    private String costCenter;
    private String title;
    private String description;
    private String parentCode;
    private BigDecimal levelNo;
    private String entryLevelInd;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        CostCenter cost = new CostCenter();
        cost.setCostCenterCode(rs.getString("COST_CENTER_CDE"));
        cost.setCostCenter(rs.getString("COST_CENTER"));
        cost.setTitle(rs.getString("TITLE"));
        cost.setDescription(rs.getString("DESCRIPTION_TXT"));
        cost.setParentCode(rs.getString("PARENT_CDE"));
        cost.setLevelNo(rs.getBigDecimal("LEVEL_NBR"));
        cost.setEntryLevelInd(rs.getString("ENTRY_LEVEL_IND"));
        return cost;
    }

    /**
     * @return the costCenterCode
     */
    public String getCostCenterCode() {
        return costCenterCode;
    }

    /**
     * @param costCenterCode the costCenterCode to set
     */
    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
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
     * @return the costCenter
     */
    public String getCostCenter() {
        return costCenter;
    }

    /**
     * @param costCenter the costCenter to set
     */
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
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
}
