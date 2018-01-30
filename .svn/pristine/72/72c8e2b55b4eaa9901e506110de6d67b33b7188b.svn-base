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
 * @author abbas
 */
public class Project implements Serializable, RowMapper {

    private String projectCode;
    private String project;
    private String parentCode;
    private BigDecimal levelNbr;
    private String entryLevelInd;
    private String projectName;
    private String projectAbbrev;
    private String startDate;
    private String endDate;
    private BigDecimal siteId;
    private String siteName;
    private String costCenter;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Project proj = new Project();
        proj.setProjectCode(rs.getString("PROJECT_CDE"));
        proj.setProject(rs.getString("PROJECT"));
        proj.setProjectName(rs.getString("PROJECT_NME"));
        proj.setProjectAbbrev(rs.getString("PROJECT_ABBREV"));        
        proj.setParentCode(rs.getString("PARENT_CDE"));
        proj.setLevelNbr(rs.getBigDecimal("LEVEL_NBR"));
        proj.setEntryLevelInd(rs.getString("ENTRY_LEVEL_IND"));
        proj.setStartDate(rs.getString("START_DATE"));
        proj.setEndDate(rs.getString("END_DATE"));
        proj.setSiteId(rs.getBigDecimal("SITE_ID"));
        proj.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return proj;
    }

    /**
     * @return the projectCode
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * @param projectCode the projectCode to set
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
     * @return the levelNbr
     */
    public BigDecimal getLevelNbr() {
        return levelNbr;
    }

    /**
     * @param levelNbr the levelNbr to set
     */
    public void setLevelNbr(BigDecimal levelNbr) {
        this.levelNbr = levelNbr;
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
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the projectAbbrev
     */
    public String getProjectAbbrev() {
        return projectAbbrev;
    }

    /**
     * @param projectAbbrev the projectAbbrev to set
     */
    public void setProjectAbbrev(String projectAbbrev) {
        this.projectAbbrev = projectAbbrev;
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
     * @return the project
     */
    public String getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(String project) {
        this.project = project;
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
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
}
