/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Faraz
 */
public class City implements Serializable, RowMapper {

    private String cityId;
    private String cityName;
    private String cityAbbrev;
    private String countryId;
    private String companyId;

    /**
     * @return the cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
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

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        City city = new City();
        city.setCityId(rs.getString("CITY_ID"));
        city.setCityName(rs.getString("CITY_NME"));
        city.setCityAbbrev(rs.getString("ABBREV"));
        city.setCompanyId(rs.getString("COMPANY_ID"));
        return city;
    }

    /**
     * @return the cityAbbrev
     */
    public String getCityAbbrev() {
        return cityAbbrev;
    }

    /**
     * @param cityAbbrev the cityAbbrev to set
     */
    public void setCityAbbrev(String cityAbbrev) {
        this.cityAbbrev = cityAbbrev;
    }
}
