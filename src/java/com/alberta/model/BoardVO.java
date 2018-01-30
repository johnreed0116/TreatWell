/*
 * To change this license header choose License Headers in Project Properties.
 * To change this template file choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

/**
 *
 * @author farazahmad
 */
public class BoardVO {

    private String boardRateId;
    private String typeId;
    private String categoryId;
    private String finYearId;
    private String rate;
    private String userName;
    private String companyId;

    

    /**
     * @return the typeId
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the finYearId
     */
    public String getFinYearId() {
        return finYearId;
    }

    /**
     * @param finYearId the finYearId to set
     */
    public void setFinYearId(String finYearId) {
        this.finYearId = finYearId;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(String rate) {
        this.rate = rate;
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
     * @return the boardRateId
     */
    public String getBoardRateId() {
        return boardRateId;
    }

    /**
     * @param boardRateId the boardRateId to set
     */
    public void setBoardRateId(String boardRateId) {
        this.boardRateId = boardRateId;
    }
}
