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
public class SiteReportColumns implements Serializable, RowMapper {
    
    private BigDecimal siteReportColumnsId;   
    private BigDecimal siteId;
    private BigDecimal reportTemplateId;
    private BigDecimal reportColumnsMasterId;
    private BigDecimal companyId;
    
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        SiteReportColumns src = new SiteReportColumns();
        src.setSiteReportColumnsId(rs.getBigDecimal("SITE_REPORT_COLUMNS_ID"));
        src.setSiteId(rs.getBigDecimal("SITE_ID"));
        src.setReportTemplateId(rs.getBigDecimal("REPORT_TEMPLATE_ID"));
        src.setReportColumnsMasterId(rs.getBigDecimal("REPORT_COLUMNS_MASTER_ID"));
        src.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return src;
    }

    /**
     * @return the siteReportColumnsId
     */
    public BigDecimal getSiteReportColumnsId() {
        return siteReportColumnsId;
    }

    /**
     * @param siteReportColumnsId the siteReportColumnsId to set
     */
    public void setSiteReportColumnsId(BigDecimal siteReportColumnsId) {
        this.siteReportColumnsId = siteReportColumnsId;
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
     * @return the reportColumnsMasterId
     */
    public BigDecimal getReportColumnsMasterId() {
        return reportColumnsMasterId;
    }

    /**
     * @param reportColumnsMasterId the reportColumnsMasterId to set
     */
    public void setReportColumnsMasterId(BigDecimal reportColumnsMasterId) {
        this.reportColumnsMasterId = reportColumnsMasterId;
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
    
}
