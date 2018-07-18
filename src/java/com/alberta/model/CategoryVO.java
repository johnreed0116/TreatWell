/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author farazahmad
 */
public class CategoryVO {

    private String questionCategoryId;
    private String categoryName;
    private String specialityId;
    private String folderPath;
    private String canChangeImage;
    private MultipartFile categoryAttachment;
    private String userName;
    private String userType;

    /**
     * @return the questionCategoryId
     */
    public String getQuestionCategoryId() {
        return questionCategoryId;
    }

    /**
     * @param questionCategoryId the questionCategoryId to set
     */
    public void setQuestionCategoryId(String questionCategoryId) {
        this.questionCategoryId = questionCategoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the specialityId
     */
    public String getSpecialityId() {
        return specialityId;
    }

    /**
     * @param specialityId the specialityId to set
     */
    public void setSpecialityId(String specialityId) {
        this.specialityId = specialityId;
    }

    /**
     * @return the folderPath
     */
    public String getFolderPath() {
        return folderPath;
    }

    /**
     * @param folderPath the folderPath to set
     */
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    /**
     * @return the categoryAttachment
     */
    public MultipartFile getCategoryAttachment() {
        return categoryAttachment;
    }

    /**
     * @param categoryAttachment the categoryAttachment to set
     */
    public void setCategoryAttachment(MultipartFile categoryAttachment) {
        this.categoryAttachment = categoryAttachment;
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
     * @return the canChangeImage
     */
    public String getCanChangeImage() {
        return canChangeImage;
    }

    /**
     * @param canChangeImage the canChangeImage to set
     */
    public void setCanChangeImage(String canChangeImage) {
        this.canChangeImage = canChangeImage;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
