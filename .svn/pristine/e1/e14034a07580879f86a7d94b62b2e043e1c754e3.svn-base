/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Faraz
 */
public class Company implements Serializable, RowMapper {
    private String companyId;
    private String companyName;
    private MultipartFile reportLogo;
    private String reportLogoPath;

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
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

   @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Company company = new Company();
        company.setCompanyId(rs.getString("COMPANY_ID"));
        company.setCompanyName(rs.getString("COMPANY_NME"));
        return company;
    }

    /**
     * @return the reportLogo
     */
    public MultipartFile getReportLogo() {
        return reportLogo;
    }

    /**
     * @param reportLogo the reportLogo to set
     */
    public void setReportLogo(MultipartFile reportLogo) {
        this.reportLogo = reportLogo;
    }

    /**
     * @return the reportLogoPath
     */
    public String getReportLogoPath() {
        return reportLogoPath;
    }

    /**
     * @param reportLogoPath the reportLogoPath to set
     */
    public void setReportLogoPath(String reportLogoPath) {
        this.reportLogoPath = reportLogoPath;
    }
        
}
