/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.report;

import com.alberta.dao.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author farazahmad
 */
public class ReportServiceImpl implements ReportService {

    private DAO dao;

    /**
     * @return the dao
     */
    @Override
    public DAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    @Override
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    @Override
    public ResultSet getPerformaById(String performaMasterId, String companyId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT PD.PERFORMA_MASTER_ID,PD.BUSINESS_UNIT_ID,BU.BU_ABBREV,PM.PERFORMA_NBR,"
                    + " PM.REQUEST_DTE,BM.BRAND_NME,BM.ABBREV,BD.DESIGN_CDE,BT.TITLE BOARD_TYPE_TITLE,"
                    + " BC.TITLE BOARD_CATEGORY_TITLE,PD.WIDTH,PD.HEIGHT,PD.QTY,PD.RATE,PD.AMNT,"
                    + " PD.BRAND_DETAIL_ID,PD.BRAND_MASTER_ID,PD.BOARD_TYPE_ID,PD.BOARD_CATEGORY_ID,"
                    + " PM.PREPARED_BY SUBMITTED_BY,PM.PREPARED_DTE SUBMITTED_DTE,PM.POSTED_IND,PM.POSTED_BY,PM.CANCELLED_DTE,PM.CANCELLED_BY,"
                    + " VR.COMPANY_NME,DL.DEALER_NME,DL.DEALER_TITLE,DL.DEALER_CDE,ZN.ZONE_NME,CT.CITY_NME,AR.AREA_NME,DS.*"
                    + " FROM PERFORMA_MASTER PM,PERFORMA_DETAIL PD,BUSINESS_UNIT BU,BRAND_MASTER BM,"
                    + " BRAND_DETAIL BD,BOARD_TYPE BT,BOARD_CATEGORY BC,DEALER DL,VENDOR VR,ZONE ZN,CITY CT,AREA AR,DEALER_SHOP DS"
                    + " WHERE PM.PERFORMA_MASTER_ID=PD.PERFORMA_MASTER_ID"
                    + "  AND PD.BUSINESS_UNIT_ID=BU.BUSINESS_UNIT_ID"
                    + "  AND PD.BRAND_MASTER_ID=BM.BRAND_MASTER_ID"
                    + "  AND PD.BRAND_DETAIL_ID=BD.BRAND_DETAIL_ID"
                    + "  AND PD.BOARD_TYPE_ID=BT.BOARD_TYPE_ID"
                    + "  AND PD.BOARD_CATEGORY_ID=BC.BOARD_CATEGORY_ID"
                    + "  AND PM.DEALER_ID=DL.DEALER_ID"
                    + "  AND PM.VENDOR_ID=VR.VENDOR_ID"
                    + "  AND DL.ZONE_ID=ZN.ZONE_ID"
                    + "  AND PM.CITY_ID=CT.CITY_ID"
                    + "  AND PM.DEALER_SHOP_ID=DS.DEALER_SHOP_ID(+)"
                    + "  AND DS.AREA_ID=AR.AREA_ID(+)"
                    + "  AND PM.PERFORMA_MASTER_ID=" + performaMasterId + ""
                    + " ORDER BY PD.BRAND_DETAIL_ID ASC ";
            preparedStatement = this.getDao().getJdbcTemplate().getDataSource().getConnection().prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }
}
