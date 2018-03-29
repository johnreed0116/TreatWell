/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ali Zaidi
 */
public class Article {
    
    private String doctorArticleId;
    private String title;
    private String description;
    private MultipartFile file;
    private String fileType;
    private String userName;
    private String filePath;

    public String getDoctorArticleId() {
        return doctorArticleId;
    }

    public void setDoctorArticleId(String doctorArticleId) {
        this.doctorArticleId = doctorArticleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    
    
    
}
