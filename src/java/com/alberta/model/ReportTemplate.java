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
public class ReportTemplate implements Serializable, RowMapper {
    private BigDecimal reportTemplateId;
    private BigDecimal templateReportsId;
    private String reportTemplateName;
    private String basedOn;
    private String sourceCol;
    private BigDecimal companyId;
    
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {        
        ReportTemplate rt = new ReportTemplate();
        rt.setReportTemplateId(rs.getBigDecimal("REPORT_TEMPLATE_ID"));
        rt.setReportTemplateName(rs.getString("REPORT_NME"));
        rt.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return rt;
    }

    /**
     * @return the reportTemplateId
     */
    public BigDecimal getReportTemplateId() {
        return reportTemplateId;
    }

    /**
     * @param reportTemplateId the reportTemplateId to set
     */
    public void setReportTemplateId(BigDecimal reportTemplateId) {
        this.reportTemplateId = reportTemplateId;
    }

    /**
     * @return the reportTemplateName
     */
    public String getReportTemplateName() {
        return reportTemplateName;
    }

    /**
     * @param reportTemplateName the reportTemplateName to set
     */
    public void setReportTemplateName(String reportTemplateName) {
        this.reportTemplateName = reportTemplateName;
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
     * @return the templateReportsId
     */
    public BigDecimal getTemplateReportsId() {
        return templateReportsId;
    }

    /**
     * @param templateReportsId the templateReportsId to set
     */
    public void setTemplateReportsId(BigDecimal templateReportsId) {
        this.templateReportsId = templateReportsId;
    }

    
}
