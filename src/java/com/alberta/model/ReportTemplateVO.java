/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.math.BigDecimal;

/**
 *
 * @author abbas
 */
public class ReportTemplateVO {
    private String columnId ;
    private String columnName ;
    private String columnSort ;
    private BigDecimal columnAmount;
    private String basedOn;
    private String sourceCol;
    private String sourceText;
    private String sourceType;
    private String basedOnComm;
    private BigDecimal currentAmount;
    private BigDecimal previousAmount;
    private BigDecimal previous2Amount;
    /**
     * @return the columnId
     */
    public String getColumnId() {
        return columnId;
    }

    /**
     * @param columnId the columnId to set
     */
    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the columnSort
     */
    public String getColumnSort() {
        return columnSort;
    }

    /**
     * @param columnSort the columnSort to set
     */
    public void setColumnSort(String columnSort) {
        this.columnSort = columnSort;
    }

    /**
     * @return the columnAmount
     */
    public BigDecimal getColumnAmount() {
        return columnAmount;
    }

    /**
     * @param columnAmount the columnAmount to set
     */
    public void setColumnAmount(BigDecimal columnAmount) {
        this.columnAmount = columnAmount;
    }

    /**
     * @return the basedOn
     */
    public String getBasedOn() {
        return basedOn;
    }

    /**
     * @param basedOn the basedOn to set
     */
    public void setBasedOn(String basedOn) {
        this.basedOn = basedOn;
    }

    /**
     * @return the sourceCol
     */
    public String getSourceCol() {
        return sourceCol;
    }

    /**
     * @param sourceCol the sourceCol to set
     */
    public void setSourceCol(String sourceCol) {
        this.sourceCol = sourceCol;
    }

    /**
     * @return the sourceText
     */
    public String getSourceText() {
        return sourceText;
    }

    /**
     * @param sourceText the sourceText to set
     */
    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * @param sourceType the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @return the basedOnComm
     */
    public String getBasedOnComm() {
        return basedOnComm;
    }

    /**
     * @param basedOnComm the basedOnComm to set
     */
    public void setBasedOnComm(String basedOnComm) {
        this.basedOnComm = basedOnComm;
    }

    /**
     * @return the currentAmount
     */
    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    /**
     * @param currentAmount the currentAmount to set
     */
    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * @return the previousAmount
     */
    public BigDecimal getPreviousAmount() {
        return previousAmount;
    }

    /**
     * @param previousAmount the previousAmount to set
     */
    public void setPreviousAmount(BigDecimal previousAmount) {
        this.previousAmount = previousAmount;
    }

    /**
     * @return the previous2Amount
     */
    public BigDecimal getPrevious2Amount() {
        return previous2Amount;
    }

    /**
     * @param previous2Amount the previous2Amount to set
     */
    public void setPrevious2Amount(BigDecimal previous2Amount) {
        this.previous2Amount = previous2Amount;
    }
   
    

    
}
