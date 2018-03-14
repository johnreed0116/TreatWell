/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.performa;

import com.alberta.dao.DAO;
import com.alberta.model.DoctorVO;
import com.alberta.model.Encryption;
import com.alberta.model.Lab;
import com.alberta.model.PerformaVO;
import com.alberta.model.Pharma;
import com.alberta.model.PrescriptionVO;
import com.alberta.utility.MD5;
import com.alberta.utility.Util;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author farazahmad
 */
public class PerformaServiceImpl implements PerformaService {

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
    public String saveAppointment(PerformaVO vo) {
        boolean flag = false;
        String appointmentId = "";
        try {
            List<String> arr = new ArrayList();
            String masterId = "";
            String prevId = "SELECT SEQ_TW_APPOINTMENT_ID.NEXTVAL VMASTER FROM DUAL";
            List list = this.getDao().getJdbcTemplate().queryForList(prevId);
            if (list != null && list.size() > 0) {
                Map map = (Map) list.get(0);
                masterId = (String) map.get("VMASTER").toString();
            }
            String appointmentTime = vo.getAppointmentDate() + " " + vo.getAppointmentTime();
            String query = "INSERT INTO TW_APPOINTMENT(TW_APPOINTMENT_ID,TW_DOCTOR_ID,TW_PATIENT_ID,TW_CLINIC_ID,"
                    + " APPOINTMENT_DTE,APPOINTMENT_TIME,STATUS_IND,PREPARED_BY,COMPANY_ID,PREPARED_DTE,"
                    + " REMARKS,APPOINTMENT_NO) "
                    + " VALUES (" + masterId + "," + vo.getDoctorId() + "," + vo.getPatientId() + ","
                    + " " + vo.getClinicId() + ","
                    + " TO_DATE('" + vo.getAppointmentDate() + "','DD-MM-YYYY'),"
                    + " TO_DATE('" + appointmentTime + "','DD-MM-YYYY HH24:MI'),'P',"
                    + " '" + vo.getUserName() + "'," + vo.getCompanyId() + ",SYSDATE,"
                    + " '" + Util.removeSpecialChar(vo.getRemarks()) + "',"
                    + " " + getNextAppointmentNumber(vo.getClinicId(), vo.getDoctorId()) + ") ";
            arr.add(query);
            flag = this.dao.insertAll(arr, vo.getUserName());
            if (flag) {
                appointmentId = masterId;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return appointmentId;
    }

    @Override
    public List<Map> getAppointmentsForDoctor(String doctorId, String companyId, String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT AP.TW_APPOINTMENT_ID,AP.TW_DOCTOR_ID,AP.TW_PATIENT_ID,TO_CHAR(AP.APPOINTMENT_DTE,'DD-MM-YYYY') APPOINTMENT_DTE,"
                    + " TO_CHAR(AP.APPOINTMENT_TIME,'DD-MM-YYYY HH24:MI') APPOINTMENT_TIME,PAT.PATIENT_NME,PAT.MOBILE_NO,"
                    + " AP.REMARKS,AP.STATUS_IND,AP.APPOINTMENT_NO"
                    + " FROM TW_APPOINTMENT AP,TW_PATIENT PAT"
                    + " WHERE AP.TW_DOCTOR_ID=" + doctorId + ""
                    + " AND AP.TW_CLINIC_ID=" + clinicId
                    + " AND AP.TW_PATIENT_ID=PAT.TW_PATIENT_ID"
                    + " ORDER BY AP.APPOINTMENT_TIME ASC";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getAppointmentsForDate(String date, String clinicId, String doctorId) {
        List<Map> list = null;
        try {
            String where = "";
            if (clinicId != null && !clinicId.trim().isEmpty()) {
                where += " AND AP.TW_CLINIC_ID=" + clinicId + "";
            }
            if (doctorId != null && !doctorId.trim().isEmpty()) {
                where += " AND AP.TW_DOCTOR_ID=" + doctorId + "";
            }

            String query = "SELECT AP.TW_APPOINTMENT_ID,AP.TW_DOCTOR_ID,AP.TW_PATIENT_ID,TO_CHAR(AP.APPOINTMENT_DTE,'DD-MM-YYYY') APPOINTMENT_DTE, "
                    + " TO_CHAR(AP.APPOINTMENT_TIME,'HH24:MI') APPOINTMENT_TIME,PAT.PATIENT_NME,PAT.MOBILE_NO, AP.REMARKS,AP.STATUS_IND,"
                    + " AP.APPOINTMENT_NO,NVL(APP_TBL.TOTAL_AMNT,0) TOTAL_AMNT,"
                    + " NVL(COLLECTED.AMNT,0)  COLLECTED_AMNT,NVL(PREV_TOTAL.TOTAL_AMNT,0) PREVIOUS_TOTAL,AP.TW_CLINIC_ID"
                    + " FROM TW_APPOINTMENT AP,TW_PATIENT PAT,"
                    + " (SELECT TAF.TW_APPOINTMENT_ID,SUM(TAF.FEE_AMNT) TOTAL_AMNT  FROM TW_APPOINTMENT_FEE TAF GROUP BY TAF.TW_APPOINTMENT_ID) APP_TBL,"
                    + " (SELECT TW_APPOINTMENT_ID,SUM(AMNT) AMNT FROM TW_COLLECTED_FEE GROUP BY TW_APPOINTMENT_ID) COLLECTED,"
                    + " (SELECT AP.TW_PATIENT_ID,SUM(TAF.FEE_AMNT) TOTAL_AMNT  "
                    + " FROM TW_APPOINTMENT_FEE TAF,TW_APPOINTMENT AP"
                    + " WHERE TAF.TW_APPOINTMENT_ID=AP.TW_APPOINTMENT_ID"
                    + " AND AP.TW_CLINIC_ID=" + clinicId + ""
                    + " GROUP BY AP.TW_PATIENT_ID) PREV_TOTAL"
                    + " WHERE AP.APPOINTMENT_DTE=TO_DATE('" + date + "','DD-MM-YYYY') "
                    + " AND AP.TW_PATIENT_ID=PAT.TW_PATIENT_ID "
                    + " AND AP.TW_APPOINTMENT_ID=APP_TBL.TW_APPOINTMENT_ID(+)"
                    + " AND AP.TW_APPOINTMENT_ID=COLLECTED.TW_APPOINTMENT_ID(+)"
                    + " AND PAT.TW_PATIENT_ID=PREV_TOTAL.TW_PATIENT_ID(+)"
                    + " " + where + ""
                    + " ORDER BY AP.TW_APPOINTMENT_ID ASC";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateAppointmentStatus(String appointmentId, String status, String userName) {
        boolean flag = false;
        try {
            List<String> arr = new ArrayList();
            String query = "UPDATE TW_APPOINTMENT SET STATUS_IND='" + status + "',"
                    + " STATUS_UPDATED_ON=SYSDATE,STATUS_UPDATED_BY='" + userName + "' "
                    + " WHERE TW_APPOINTMENT_ID=" + appointmentId + "";
            arr.add(query);
            if (status.equalsIgnoreCase("C")) {
                arr.add("DELETE FROM TW_APPOINTMENT_FEE WHERE TW_APPOINTMENT_ID=" + appointmentId + "");
                arr.add("DELETE FROM TW_COLLECTED_FEE WHERE TW_APPOINTMENT_ID=" + appointmentId + "");
                arr.add("DELETE FROM TW_APPOINTMENT WHERE TW_APPOINTMENT_ID=" + appointmentId + "");
            }
            flag = this.getDao().insertAll(arr, userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean updateAppointmentDateTime(String appointmentId, String date, String time, String userName) {
        boolean flag = false;
        try {
            String appointmentTime = date + " " + time;
            String query = "UPDATE TW_APPOINTMENT SET APPOINTMENT_DTE=TO_DATE('" + date + "','DD-MM-YYYY'),"
                    + " APPOINTMENT_TIME=TO_DATE('" + appointmentTime + "','DD-MM-YYYY HH24:MI'),"
                    + " STATUS_UPDATED_ON=SYSDATE,STATUS_UPDATED_BY='" + userName + "' "
                    + " WHERE TW_APPOINTMENT_ID=" + appointmentId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveDoctorProcedure(DoctorVO ds) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";

            if (ds.getProcedureId() != null && !ds.getProcedureId().isEmpty()) {
                for (int i = 0; i < ds.getFees().length; i++) {
                    query = "UPDATE TW_PROCEDURE_FEE SET "
                            + " FEE=" + (ds.getFees()[i].isEmpty() ? 0 : ds.getFees()[i]) + ","
                            + " DISCOUNT=" + (ds.getDiscounts() == null ? 0 : ds.getDiscounts()[i]) + ""
                            + " WHERE TW_PROCEDURE_FEE_ID=" + ds.getProcedureId() + "";
                    arr.add(query);
                }
            } else {
                for (int i = 0; i < ds.getFees().length; i++) {
                    query = "INSERT INTO TW_PROCEDURE_FEE(TW_PROCEDURE_FEE_ID ,TW_DOCTOR_ID ,TW_MEDICAL_PROCEDURE_ID,FEE ,DISCOUNT,"
                            + "TW_COMPANY_ID,PREPARED_BY)"
                            + " VALUES (SEQ_TW_PROCEDURE_FEE_ID.NEXTVAL," + ds.getDoctorId() + ","
                            + "" + ds.getServiceId() + ","
                            + "" + (ds.getFees()[i].isEmpty() ? null : ds.getFees()[i]) + ","
                            + "" + (ds.getDiscounts()[i].isEmpty() ? null : ds.getDiscounts()[i]) + ","
                            + (ds.getPanelId()[i].isEmpty() ? null : ds.getPanelId()[i]) + ",'" + ds.getUserName() + "' )";
                    arr.add(query);
                }
            }
            flag = this.dao.insertAll(arr, ds.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getDoctorProcedures(String doctorId, String appointmentId, String companyId) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT MP.TITLE,PF.TW_DOCTOR_ID,PF.TW_MEDICAL_PROCEDURE_ID,PF.FEE,PF.DISCOUNT,"
                    + " PF.TW_MEDICAL_PROCEDURE_ID,PF.TW_PROCEDURE_FEE_ID,COMP.COMPANY_NME "
                    + " FROM TW_PROCEDURE_FEE PF,TW_MEDICAL_PROCEDURE MP,TW_COMPANY COMP"
                    + " WHERE MP.TW_MEDICAL_PROCEDURE_ID=PF.TW_MEDICAL_PROCEDURE_ID"
                    + " AND PF.TW_COMPANY_ID=COMP.TW_COMPANY_ID(+) "
                    + " AND MP.TW_MEDICAL_PROCEDURE_ID NOT IN (SELECT FA.TW_MEDEICAL_PROCEDURE_ID FROM TW_APPOINTMENT_FEE FA  WHERE FA.TW_APPOINTMENT_ID=" + appointmentId + ")"
                    + " AND PF.TW_DOCTOR_ID=" + doctorId;
            if (companyId != null && !companyId.isEmpty()) {
                where += " AND PF.TW_COMPANY_ID =" + companyId + " ";
            } else {
                where += " AND PF.TW_COMPANY_ID IS NULL ";
            }

            list = this.getDao().getData(query + where);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> searchPatient(String patientName) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT TW_PATIENT_ID,PATIENT_NME,MOBILE_NO,AGE,TO_CHAR(DOB,'DD-MON-YYYY') DOB,ATTEND_CLINIC,"
                    + "ANY_ALLERGY,GENDER,TAKE_MEDICINE,ADDRESS,HEIGHT,ANY_FEVER,SMOKER_IND,TAKE_STEROID,"
                    + "WEIGHT FROM TW_PATIENT ";
            if (patientName != null && !patientName.trim().isEmpty()) {
                where += "WHERE UPPER(PATIENT_NME) LIKE '%" + patientName.toUpperCase() + "%'"
                        + "  OR MOBILE_NO LIKE '%" + patientName.trim() + "%'";
            }
            list = this.getDao().getData(query + where);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getMedicalProcedures(String companyId) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_MEDICAL_PROCEDURE ORDER BY TITLE ";
            list = this.getDao().getData(query + where);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getAppointedPatientsForDoctor(String doctorId, String clinicId, String showAll) {
        List<Map> list = null;
        try {

            if (showAll != null && showAll.equalsIgnoreCase("Y")) {
                String query = "SELECT P.TW_PATIENT_ID,MAX(P.PATIENT_NME) PATIENT_NME,MAX(P.MOBILE_NO) MOBILE_NO,MAX(TP.APPOINTMENT_NO) APPOINTMENT_NO,"
                        + " 0 BALANCE,MAX(APP_TBL.TW_COMPANY_ID) TW_COMPANY_ID"
                        + " FROM TW_APPOINTMENT TP,TW_PATIENT P,"
                        + " (SELECT TP.TW_APPOINTMENT_ID,SUM(CF.AMNT) AMNT FROM TW_COLLECTED_FEE CF,TW_APPOINTMENT TP"
                        + " WHERE TP.TW_APPOINTMENT_ID=CF.TW_APPOINTMENT_ID AND TP.TW_DOCTOR_ID=" + doctorId + " "
                        + " AND TP.TW_CLINIC_ID= " + clinicId + " GROUP BY TP.TW_APPOINTMENT_ID) COLLECTED,"
                        + " (SELECT AP.TW_PATIENT_ID,SUM(TAF.FEE_AMNT) TOTAL_AMNT  "
                        + " FROM TW_APPOINTMENT_FEE TAF,TW_APPOINTMENT AP"
                        + " WHERE TAF.TW_APPOINTMENT_ID=AP.TW_APPOINTMENT_ID"
                        + " AND AP.TW_CLINIC_ID= " + clinicId + " "
                        + " GROUP BY AP.TW_PATIENT_ID) PREV_TOTAL,"
                        + " (SELECT SUM(FEE_AMNT) FEE_AMNT,SUM(DISCOUNT_AMNT) DISCOUNT_AMNT,MAX(TW_COMPANY_ID) TW_COMPANY_ID,TW_APPOINTMENT_ID"
                        + " FROM TW_APPOINTMENT_FEE "
                        + " GROUP BY TW_APPOINTMENT_ID) APP_TBL"
                        + " WHERE TW_DOCTOR_ID=" + doctorId + "  AND TW_CLINIC_ID= " + clinicId + "  "
                        + " AND TP.TW_PRESCRIPTION_MASTER_ID IS NULL"
                        + " AND TP.TW_PATIENT_ID=P.TW_PATIENT_ID"
                        + " AND TP.TW_APPOINTMENT_ID=COLLECTED.TW_APPOINTMENT_ID(+)"
                        + " AND P.TW_PATIENT_ID=PREV_TOTAL.TW_PATIENT_ID(+)"
                        + " AND TP.TW_APPOINTMENT_ID=APP_TBL.TW_APPOINTMENT_ID(+)"
                        + " GROUP BY P.TW_PATIENT_ID"
                        + " ORDER BY MAX(P.PATIENT_NME)";
                list = this.getDao().getData(query);
            } else {
                String query = "SELECT P.PATIENT_NME,P.TW_PATIENT_ID ,P.MOBILE_NO,TP.APPOINTMENT_NO,"
                        + " NVL(PREV_TOTAL.TOTAL_AMNT,0)- NVL(COLLECTED.AMNT,0) BALANCE,APP_TBL.TW_COMPANY_ID"
                        + " FROM TW_APPOINTMENT TP,TW_PATIENT P,"
                        + " (SELECT TP.TW_APPOINTMENT_ID,SUM(CF.AMNT) AMNT FROM TW_COLLECTED_FEE CF,TW_APPOINTMENT TP"
                        + " WHERE TP.TW_APPOINTMENT_ID=CF.TW_APPOINTMENT_ID AND TP.TW_DOCTOR_ID=" + doctorId + ""
                        + " AND TP.TW_CLINIC_ID=" + clinicId + " GROUP BY TP.TW_APPOINTMENT_ID) COLLECTED,"
                        + "(SELECT AP.TW_PATIENT_ID,SUM(TAF.FEE_AMNT) TOTAL_AMNT  "
                        + " FROM TW_APPOINTMENT_FEE TAF,TW_APPOINTMENT AP"
                        + " WHERE TAF.TW_APPOINTMENT_ID=AP.TW_APPOINTMENT_ID"
                        + " AND AP.TW_CLINIC_ID=" + clinicId + ""
                        + " GROUP BY AP.TW_PATIENT_ID) PREV_TOTAL,"
                        + " (SELECT SUM(FEE_AMNT) FEE_AMNT,SUM(DISCOUNT_AMNT) DISCOUNT_AMNT,MAX(TW_COMPANY_ID) TW_COMPANY_ID,TW_APPOINTMENT_ID"
                        + " FROM TW_APPOINTMENT_FEE "
                        + " GROUP BY TW_APPOINTMENT_ID) APP_TBL"
                        + " WHERE TW_DOCTOR_ID=" + doctorId + " AND TW_CLINIC_ID=" + clinicId + " "
                        + " AND TP.STATUS_IND  IN ('A')"
                        + " AND TP.TW_PRESCRIPTION_MASTER_ID IS NULL"
                        + " AND TP.TW_PATIENT_ID=P.TW_PATIENT_ID"
                        + " AND TP.TW_APPOINTMENT_ID=COLLECTED.TW_APPOINTMENT_ID(+)"
                        + " AND P.TW_PATIENT_ID=PREV_TOTAL.TW_PATIENT_ID(+)"
                        + " AND TP.APPOINTMENT_DTE=TO_DATE(TO_CHAR(SYSDATE,'DD-MM-YYYY'),'DD-MM-YYYY')"
                        + " AND TP.TW_APPOINTMENT_ID=APP_TBL.TW_APPOINTMENT_ID(+)"
                        + " ORDER BY P.PATIENT_NME";
                list = this.getDao().getData(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public String savePrescription(PrescriptionVO vo) {
        boolean flag = false;
        String masterId = "";
        try {
            List<String> arr = new ArrayList();
            String prevId = "SELECT SEQ_TW_PRESCRIPTION_MASTER_ID.NEXTVAL VMASTER FROM DUAL";
            List list = this.getDao().getJdbcTemplate().queryForList(prevId);
            if (list != null && list.size() > 0) {
                Map map = (Map) list.get(0);
                masterId = (String) map.get("VMASTER").toString();
            }
            String query = "INSERT INTO TW_PRESCRIPTION_MASTER(TW_PRESCRIPTION_MASTER_ID,TW_DOCTOR_ID,TW_PATIENT_ID,TW_CLINIC_ID,"
                    + " REMARKS,PREPARED_BY,PREPARED_DTE,DTE) "
                    + " VALUES (" + masterId + "," + vo.getDoctorId() + "," + vo.getPatientId() + ","
                    + " " + vo.getClinicId() + ", '" + Util.removeSpecialChar(vo.getRemarks()) + "',"
                    + " '" + vo.getUserName() + "',SYSDATE,TO_DATE(SYSDATE,'DD-MM-YYYY')) ";
            arr.add(query);
            arr.add("UPDATE TW_APPOINTMENT SET STATUS_IND='D',"
                    + " TW_PRESCRIPTION_MASTER_ID=" + masterId + ","
                    + " STATUS_UPDATED_ON=SYSDATE,STATUS_UPDATED_BY='" + vo.getUserName() + "' "
                    + " WHERE  TW_DOCTOR_ID=" + vo.getDoctorId() + " AND TW_PATIENT_ID=" + vo.getPatientId() + " "
                    + " AND TW_CLINIC_ID=" + vo.getClinicId() + "");
            if (vo.getMedicineId() != null) {
                for (int i = 0; i < vo.getMedicineId().length; i++) {
                    arr.add("INSERT INTO TW_PRESCRIPTION_DETAIL(TW_PRESCRIPTION_DETAIL_ID,TW_PRESCRIPTION_MASTER_ID,TW_MEDICINE_ID,"
                            + "DAYS,QTY,TW_FREQUENCY_ID,TW_DOSE_USAGE_ID) VALUES(SEQ_TW_PRESCRIPTION_DETAIL_ID.NEXTVAL,"
                            + " " + masterId + "," + vo.getMedicineId()[i] + ","
                            + " " + (vo.getDays()[i].isEmpty() ? 0 : vo.getDays()[i]) + ","
                            + " " + (vo.getQty()[i].isEmpty() ? 0 : vo.getQty()[i]) + ","
                            + " " + vo.getFrequencyId()[i] + "," + vo.getUsageId()[i] + ")");
                }
            }
            if (vo.getLabTestId() != null) {
                for (int i = 0; i < vo.getLabTestId().length; i++) {
                    arr.add("INSERT INTO TW_PRESCRIPTION_DETAIL(TW_PRESCRIPTION_DETAIL_ID,TW_PRESCRIPTION_MASTER_ID,TW_LAB_TEST_ID,TW_LAB_MASTER_ID,TW_LAB_DETAIL_ID,OCCURRENCE"
                            + ") VALUES(SEQ_TW_PRESCRIPTION_DETAIL_ID.NEXTVAL,"
                            + " " + masterId + "," + vo.getLabTestId()[i] + "," + vo.getLabId()[i] + "," + vo.getLabCenterId()[i] + ",'" + vo.getOccurrence()[i] + "')");
                }
            }
            flag = this.dao.insertAll(arr, vo.getUserName());
        } catch (Exception ex) {
            masterId = "";
            ex.printStackTrace();
        }
        return masterId;
    }

    @Override
    public boolean saveHealthCard(DoctorVO c) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            if (c.getHealthCardId() != null && !c.getHealthCardId().isEmpty()) {
                String query = "UPDATE TW_HEALTH_CARD SET CARD_NME='" + Util.removeSpecialChar(c.getCardName().toUpperCase()) + "',"
                        + " PRICE=" + (c.getSalePrice().isEmpty() ? 0 : c.getSalePrice()) + ","
                        + " DOCTOR_DISC=" + (c.getDoctorsDiscount().isEmpty() ? 0 : c.getDoctorsDiscount()) + ","
                        + " VISITS_ALLOWED=" + (c.getNoOfVisits().isEmpty() ? 0 : c.getNoOfVisits()) + ","
                        + " PRODUCT_DISC=" + (c.getProductsDiscount().isEmpty() ? 0 : c.getProductsDiscount()) + ","
                        + " DESCRIPTION='" + Util.removeSpecialChar(c.getAdditionalFeatures()) + "',"
                        + " AVAILABLE_FOR='" + c.getAvailableFor() + "'"
                        + " WHERE TW_HEALTH_CARD_ID=" + c.getHealthCardId() + "";
                arr.add(query);
            } else {
                String query = "INSERT INTO TW_HEALTH_CARD(TW_HEALTH_CARD_ID,CARD_NME,PRICE,DOCTOR_DISC,VISITS_ALLOWED,"
                        + "PRODUCT_DISC,DESCRIPTION,PREPARED_BY,AVAILABLE_FOR)"
                        + " VALUES (SEQ_TW_HEALTH_CARD_ID.NEXTVAL,'" + Util.removeSpecialChar(c.getCardName().toUpperCase()) + "',"
                        + "" + (c.getSalePrice().isEmpty() ? 0 : c.getSalePrice()) + ","
                        + "" + (c.getDoctorsDiscount().isEmpty() ? 0 : c.getDoctorsDiscount()) + ","
                        + "" + (c.getNoOfVisits().isEmpty() ? 0 : c.getNoOfVisits()) + ","
                        + "" + (c.getProductsDiscount().isEmpty() ? 0 : c.getProductsDiscount()) + ","
                        + " '" + Util.removeSpecialChar(c.getAdditionalFeatures()) + "',"
                        + "'" + c.getUserName() + "','" + c.getAvailableFor() + "' )";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, c.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getHealthCards() {
        List<Map> list = null;
        try {
            String query = "SELECT TW_HEALTH_CARD_ID,CARD_NME,PRICE,DOCTOR_DISC,VISITS_ALLOWED,"
                    + " PRODUCT_DISC,DESCRIPTION,PREPARED_BY,AVAILABLE_FOR"
                    + " FROM TW_HEALTH_CARD"
                    + " ORDER BY TW_HEALTH_CARD_ID";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteHealthCard(String cardId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_HEALTH_CARD WHERE TW_HEALTH_CARD_ID=" + cardId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getHealthCardById(String cardId) {
        Map map = null;
        try {
            String query = "SELECT TW_HEALTH_CARD_ID,CARD_NME,PRICE,DOCTOR_DISC,VISITS_ALLOWED,"
                    + " PRODUCT_DISC,DESCRIPTION,PREPARED_BY,AVAILABLE_FOR "
                    + " FROM TW_HEALTH_CARD "
                    + " WHERE TW_HEALTH_CARD_ID=" + cardId + "";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map> getLabTests() {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_LAB_TEST ORDER BY TITLE";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private String getNextAppointmentNumber(String clinicId, String doctorId) throws Exception {
        String nbr = "";
        String query = "SELECT MAX(TO_NUMBER(NVL(APPOINTMENT_NO,0)))+1 MAX_NBR FROM TW_APPOINTMENT  "
                + " WHERE TW_CLINIC_ID=" + clinicId + ""
                + " AND TW_DOCTOR_ID=" + doctorId + "";
        List list = this.getDao().getJdbcTemplate().queryForList(query);
        if (list != null && list.size() > 0) {
            Map map = (Map) list.get(0);
            BigDecimal b = (BigDecimal) map.get("MAX_NBR");
            if (b == null) {
                b = new BigDecimal(1);
            }
            nbr = b.toString();
        }
        return nbr;
    }

    @Override
    public boolean saveAppointmentFee(PerformaVO vo) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            for (int i = 0; i < vo.getProcedureFeeId().length; i++) {
                String query = "INSERT INTO TW_APPOINTMENT_FEE(TW_APPOINTMENT_FEE_ID,TW_APPOINTMENT_ID,TW_MEDEICAL_PROCEDURE_ID,FEE_AMNT,"
                        + "REMARKS,PREPARED_BY,TW_COMPANY_ID)"
                        + " VALUES (SEQ_TW_APPOINTMENT_FEE_ID.NEXTVAL," + vo.getAppointmentId() + ","
                        + "" + vo.getProcedureFeeId()[i] + ","
                        + "" + (vo.getFeeAmount()[i].isEmpty() ? 0 : vo.getFeeAmount()[i]) + ","
                        + " '" + Util.removeSpecialChar(vo.getRemarks()) + "',"
                        + "'" + vo.getUserName() + "','" + vo.getCompanyId() + "')";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, vo.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveCollectedFee(PerformaVO vo) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            if (!vo.getFeeCollected().isEmpty()) {
                arr.add("INSERT INTO TW_COLLECTED_FEE(TW_COLLECTED_FEE_ID,TW_PATIENT_ID,TW_APPOINTMENT_ID,DTE,AMNT,PREPARED_BY)"
                        + " VALUES (SEQ_TW_COLLECTED_FEE_ID.NEXTVAL," + vo.getPatientId() + "," + vo.getAppointmentId() + ","
                        + " TO_DATE(SYSDATE,'DD-MM-YYYY')," + vo.getFeeCollected() + ","
                        + "'" + vo.getUserName() + "')");
            }

            arr.add("UPDATE TW_APPOINTMENT SET STATUS_IND='A',"
                    + " STATUS_UPDATED_ON=SYSDATE,STATUS_UPDATED_BY='" + vo.getUserName() + "' "
                    + " WHERE  TW_APPOINTMENT_ID=" + vo.getAppointmentId() + "");
            flag = this.dao.insertAll(arr, vo.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getProcedureFeeForAppointment(String appointmentId) {
        List<Map> list = null;
        try {
            String query = "SELECT TAF.TW_APPOINTMENT_ID,TAF.TW_APPOINTMENT_FEE_ID,TAF.FEE_AMNT,"
                    + " MP.TITLE PROCEDURE_NME,"
                    + " TP.APPOINTMENT_NO,TO_CHAR(SYSDATE,'DD-MON-YYYY') CURR_DTE"
                    + " FROM TW_APPOINTMENT_FEE TAF,TW_APPOINTMENT TP,TW_MEDICAL_PROCEDURE MP"
                    + " WHERE TP.TW_APPOINTMENT_ID=TAF.TW_APPOINTMENT_ID"
                    + " AND TAF.TW_MEDEICAL_PROCEDURE_ID=MP.TW_MEDICAL_PROCEDURE_ID"
                    + " AND TAF.TW_APPOINTMENT_ID= " + appointmentId + ""
                    + " ORDER BY MP.TITLE";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteAppointmentProcedure(String id) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_APPOINTMENT_FEE WHERE TW_APPOINTMENT_FEE_ID=" + id + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPrescriptionForMedicine(String prescId) {
        List<Map> list = null;
        try {
            String query = "SELECT TP.PATIENT_NME,TPM.REMARKS,TM.PRODUCT_NME MEDICINE_NME,"
                    + " TPD.QTY,TPD.DAYS,TDU.TITLE DOSE_USAGE,TDU.TITLE_URDU,TF.TITLE FREQUENCY,"
                    + " TO_CHAR(SYSDATE,'DD-MON-YYYY') CURR_DTE"
                    + " FROM TW_PRESCRIPTION_MASTER TPM,TW_PRESCRIPTION_DETAIL TPD,TW_PATIENT TP,TW_MEDICINE TM,TW_DOSE_USAGE TDU,TW_FREQUENCY TF"
                    + " WHERE TPM.TW_PRESCRIPTION_MASTER_ID=TPD.TW_PRESCRIPTION_MASTER_ID"
                    + " AND TPM.TW_PATIENT_ID=TP.TW_PATIENT_ID"
                    + " AND TPD.TW_MEDICINE_ID=TM.TW_MEDICINE_ID"
                    + " AND TPD.TW_DOSE_USAGE_ID=TDU.TW_DOSE_USAGE_ID(+)"
                    + " AND TPD.TW_FREQUENCY_ID=TF.TW_FREQUENCY_ID(+)"
                    + " AND TPM.TW_PRESCRIPTION_MASTER_ID=" + prescId + ""
                    + " ORDER BY TM.PRODUCT_NME";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getPrescriptionForLabTest(String prescId) {
        List<Map> list = null;
        try {
            String query = "SELECT TL.TITLE LAB_TEST_NME,LAB.LAB_NME,DET.CENTER_NME"
                    + " FROM TW_PRESCRIPTION_MASTER TPM,TW_PRESCRIPTION_DETAIL TPD,TW_LAB_TEST TL,TW_LAB_MASTER LAB,TW_LAB_DETAIL DET"
                    + " WHERE TPM.TW_PRESCRIPTION_MASTER_ID=TPD.TW_PRESCRIPTION_MASTER_ID"
                    + " AND TPD.TW_LAB_TEST_ID=TL.TW_LAB_TEST_ID "
                    + " AND TPD.TW_LAB_MASTER_ID=LAB.TW_LAB_MASTER_ID(+)"
                    + " AND TPD.TW_LAB_DETAIL_ID=DET.TW_LAB_DETAIL_ID(+)"
                    + " AND TPM.TW_PRESCRIPTION_MASTER_ID=" + prescId + ""
                    + " ORDER BY TL.TITLE";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getPrescriptionMasterById(String prescId) {
        List<Map> list = null;
        Map map = null;
        try {
            String query = "SELECT TPM.TW_DOCTOR_ID,TP.PATIENT_NME,TPM.REMARKS,"
                    + " TO_CHAR(SYSDATE,'DD-MON-YYYY') CURR_DTE"
                    + " FROM TW_PRESCRIPTION_MASTER TPM,TW_PATIENT TP"
                    + " WHERE TPM.TW_PATIENT_ID=TP.TW_PATIENT_ID"
                    + " AND TPM.TW_PRESCRIPTION_MASTER_ID=" + prescId + "";
            list = this.getDao().getData(query);
            map = list.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map> getAvailablePanelCompanyForDoctors(String doctorId) {
        List<Map> list = null;
        try {
            String where = "";

            String query = "SELECT CM.TW_COMPANY_ID,CM.COMPANY_NME,"
                    + " TO_CHAR(EXPIRY_DTE ,'DD-MON-YYYY') EXPIRY_DTE,TDC.TW_DOCTOR_COMPANY_ID,TDC.ACTIVE_IND "
                    + " FROM TW_COMPANY CM,TW_DOCTOR_COMPANY TDC"
                    + " WHERE TDC.TW_COMPANY_ID=CM.TW_COMPANY_ID"
                    + " AND TDC.TW_DOCTOR_ID=" + doctorId + ""
                    + " ORDER BY TDC.TW_DOCTOR_COMPANY_ID ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getProcedureFeeById(String procedureId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_PROCEDURE_FEE WHERE TW_PROCEDURE_FEE_ID=" + procedureId + "";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean deleteDoctorProcedure(String procedureId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_PROCEDURE_FEE WHERE TW_PROCEDURE_FEE_ID=" + procedureId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getMarginsByDoctorId(String doctorId) {
        Map map = null;
        try {
            String query = "SELECT TW_DOCTOR_ID,MAX(TW_PRINT_LAYOUT_ID) TW_PRINT_LAYOUT_ID,MAX(TOP_MARGIN) TOP_MARGIN, MAX(BOTTOM_MARGIN) BOTTOM_MARGIN,"
                    + " MAX(TOP_IMAGE) TOP_IMAGE,MAX(BOTTOM_IMAGE) BOTTOM_IMAGE"
                    + " FROM TW_PRINT_LAYOUT "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + ""
                    + " GROUP BY TW_DOCTOR_ID"
                    + " ORDER  BY TW_DOCTOR_ID";

            List<Map> list = this.getDao().getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map> getPatientFeeHistory(String patientId, String doctorId, String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT APP.TW_APPOINTMENT_ID,SUM(AF.FEE_AMNT) FEE_AMNT,"
                    + " TO_CHAR(MAX(APP.APPOINTMENT_DTE),'DD-MON-YYYY') PREPARED_DTE,MAX(APP.APPOINTMENT_NO) APPOINTMENT_NO "
                    + " FROM TW_APPOINTMENT APP,TW_APPOINTMENT_FEE AF"
                    + " WHERE APP.TW_APPOINTMENT_ID IN (SELECT TW_APPOINTMENT_ID FROM TW_APPOINTMENT WHERE TW_PATIENT_ID=" + patientId + ") AND AF.TW_APPOINTMENT_ID=APP.TW_APPOINTMENT_ID "
                    + " AND APP.TW_DOCTOR_ID=" + doctorId + ""
                    + " AND APP.TW_CLINIC_ID=" + clinicId + ""
                    + " AND APP.TW_APPOINTMENT_ID=AF.TW_APPOINTMENT_ID "
                    + " GROUP BY APP.TW_APPOINTMENT_ID "
                    + " ORDER BY APP.TW_APPOINTMENT_ID DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getAvailableMedicalProcedures(String doctorId) {
        List<Map> list = null;
        try {
            String query = " SELECT * FROM TW_MEDICAL_PROCEDURE WHERE TW_MEDICAL_PROCEDURE_ID  NOT IN ("
                    + " SELECT PF.TW_MEDICAL_PROCEDURE_ID FROM TW_PROCEDURE_FEE PF"
                    + " WHERE PF.TW_DOCTOR_ID=" + doctorId + ""
                    + " )"
                    + " ORDER BY TITLE ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean savePharmacyCompany(Pharma p, String path) {
        boolean flag = false;
        String masterId = "";
        String query = "";
        try {
            if (p.getPharmaId() != null && !p.getPharmaId().isEmpty()) {
                String isFile = "";
                if (p.getLogoFile() != null && !p.getLogoFile().isEmpty()) {
                    String sep = File.separator;
                    String picPath = path + sep + p.getPharmaId() + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    String fileFileName = Util.renameFileName(p.getLogoFile().getOriginalFilename());
                    p.getLogoFile().transferTo(new File(folder + File.separator + fileFileName));
                    isFile = ",LOGO_IMAGE='" + fileFileName + "'";
                }
                query = "UPDATE TW_PHARMACY SET"
                        + " PHARMACY_NME='" + p.getCompanyName() + "',"
                        + " WEB_URL='" + p.getWebUrl() + "'"
                        + isFile
                        + " WHERE TW_PHARMACY_ID=" + p.getPharmaId();

            } else {
                String prevId = "SELECT SEQ_TW_PHARMACY_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                String fileFileName = "";
                if (p.getLogoFile() != null && !p.getLogoFile().isEmpty()) {
                    String sep = File.separator;
                    String picPath = path + sep + masterId + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    fileFileName = Util.renameFileName(p.getLogoFile().getOriginalFilename());
                    p.getLogoFile().transferTo(new File(folder + File.separator + fileFileName));
                }

                query = "INSERT INTO TW_PHARMACY (TW_PHARMACY_ID,PHARMACY_NME,WEB_URL,LOGO_IMAGE,PREPARED_BY,PREPARED_DTE) "
                        + " VALUES(" + masterId + ",INITCAP('" + Util.removeSpecialChar(p.getCompanyName().trim()) + "'),'" + Util.removeSpecialChar(p.getWebUrl().trim()) + "',"
                        + " '" + fileFileName + "','" + p.getUserName() + "',SYSDATE)";

            }
            int i = this.getDao().getJdbcTemplate().update(query);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPharmacyCompany() {
        List<Map> list = null;
        try {
            String query = "SELECT TP.* FROM TW_PHARMACY TP ORDER BY TP.TW_PHARMACY_ID DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deletePharmacyCompany(String pharmacyId
    ) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_PHARMACY WHERE TW_PHARMACY_ID=" + pharmacyId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getPharmacyCompanyById(String pharmacyId
    ) {
        Map map = null;
        try {
            String query = "SELECT TP.* FROM TW_PHARMACY TP WHERE TP.TW_PHARMACY_ID=" + pharmacyId + "";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean savePharmacyStore(Pharma p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            if (p.getPharmaStoreId() != null && !p.getPharmaStoreId().isEmpty()) {
                query = "UPDATE TW_PHARMACY_STORE SET "
                        + "STORE_NME=INITCAP('" + Util.removeSpecialChar(p.getStoreName().trim()) + "'),"
                        + "CONTACT_PERSON='" + Util.removeSpecialChar(p.getContactPerson().trim()) + "',"
                        + "MOBILE_NO='" + p.getCellNo() + "',"
                        + "LANDLINE_NO='" + p.getPtclNo() + "',"
                        + "EMAIL='" + Util.removeSpecialChar(p.getEmail().trim()) + "',"
                        + "CITY_ID=" + p.getCityId() + ","
                        + "CITY_AREA_ID=" + p.getAreaId() + ","
                        + "OPEN_FRM=TO_DATE('" + p.getTimeFrom() + "','HH24:MI'),"
                        + "OPEN_TO=TO_DATE('" + p.getTimeTo() + "','HH24:MI')"
                        + " WHERE TW_PHARMACY_STORE_ID=" + p.getPharmaStoreId();
                arr.add(query);
            } else {
                String masterId = "";
                String prevId = "SELECT SEQ_TW_PHARMACY_STORE_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_PHARMACY_STORE (TW_PHARMACY_STORE_ID,TW_PHARMACY_ID,STORE_NME,CONTACT_PERSON,"
                        + "MOBILE_NO,LANDLINE_NO,EMAIL,CITY_ID,CITY_AREA_ID,OPEN_FRM,OPEN_TO,PREPARED_BY,PREPARED_DTE) "
                        + " VALUES(" + masterId + ","
                        + "" + p.getPharmaId() + ","
                        + "INITCAP('" + Util.removeSpecialChar(p.getStoreName().trim()) + "'),"
                        + "'" + Util.removeSpecialChar(p.getContactPerson().trim()) + "',"
                        + "'" + p.getCellNo() + "',"
                        + "'" + p.getPtclNo() + "',"
                        + "'" + Util.removeSpecialChar(p.getEmail().trim()) + "',"
                        + "" + p.getCityId() + ","
                        + "" + p.getAreaId() + ","
                        + "TO_DATE('" + p.getTimeFrom() + "','HH24:MI'),"
                        + "TO_DATE('" + p.getTimeTo() + "','HH24:MI'),"
                        + "'" + p.getUserName() + "',SYSDATE)";
                arr.add(query);
                if (!p.getLoginId().isEmpty() && p.getLoginId() != null) {
                    String generatedPassword = Util.generatePassword();
                    arr.add("INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,FIRST_NME,TW_PHARMACY_STORE_ID,TW_PHARMACY_ID) VALUES ("
                            + " '" + Util.removeSpecialChar(p.getLoginId()).trim().toLowerCase() + "','" + generatedPassword + "',INITCAP('" + Util.removeSpecialChar(p.getStoreName()) + "'),"
                            + "" + masterId + "," + p.getPharmaId() + ")");
                }
            }
            flag = this.dao.insertAll(arr, p.getUserName());
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPharmacyStore(String pharmaId) {
        List<Map> list = null;
        try {
            if (!pharmaId.isEmpty() && pharmaId != null) {
                String query = "SELECT TPS.TW_PHARMACY_STORE_ID,TPS.TW_PHARMACY_ID,TPS.STORE_NME,TPS.CONTACT_PERSON,"
                        + "TPS.MOBILE_NO,TPS.LANDLINE_NO,TPS.EMAIL,TO_CHAR(TPS.OPEN_FRM,'HH24:MI') OPEN_FRM,"
                        + "TO_CHAR(TPS.OPEN_TO,'HH24:MI') OPEN_TO,C.CITY_NME,CA.AREA_NME"
                        + " FROM TW_PHARMACY_STORE TPS,CITY C,CITY_AREA CA WHERE TPS.CITY_ID=C.CITY_ID"
                        + " AND TPS.CITY_AREA_ID=CA.CITY_AREA_ID AND TPS.TW_PHARMACY_ID=" + pharmaId
                        + " ORDER BY TPS.TW_PHARMACY_STORE_ID DESC";
                list = this.dao.getData(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deletePharmacyStore(String pharmacyId
    ) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_PHARMACY_STORE WHERE TW_PHARMACY_STORE_ID=" + pharmacyId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getPharmacyStoreById(String pharmacyStoreId) {
        Map map = null;
        try {
            String query = "SELECT TPS.TW_PHARMACY_STORE_ID,TPS.TW_PHARMACY_ID,TPS.STORE_NME,TPS.CONTACT_PERSON,"
                    + "TPS.MOBILE_NO,TPS.LANDLINE_NO,TPS.EMAIL,TO_CHAR(TPS.OPEN_FRM,'HH24:MI') OPEN_FRM,"
                    + "TO_CHAR(TPS.OPEN_TO,'HH24:MI') OPEN_TO,TPS.CITY_ID,TPS.CITY_AREA_ID,TWU.USER_NME "
                    + "FROM TW_PHARMACY_STORE TPS,TW_WEB_USERS TWU "
                    + "WHERE TPS.TW_PHARMACY_STORE_ID=TWU.TW_PHARMACY_STORE_ID AND TPS.TW_PHARMACY_STORE_ID=" + pharmacyStoreId + "";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map> getPharmacyCompany(String pharmaId) {
        List<Map> list = null;
        try {
            String query = "SELECT TP.* FROM TW_PHARMACY TP WHERE TP.TW_PHARMACY_ID=" + pharmaId + " ORDER BY TP.TW_PHARMACY_ID DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Medical Lab
    @Override
    public boolean saveMedicalLab(Lab p, String path) {
        boolean flag = false;
        String masterId = "";
        String query = "";
        try {
            if (p.getMedicalLabId() != null && !p.getMedicalLabId().isEmpty()) {
                String isFile = "";
                if (p.getLabLogo() != null && !p.getLabLogo().isEmpty()) {
                    String sep = File.separator;
                    String picPath = path + sep + p.getMedicalLabId() + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    String fileFileName = Util.renameFileName(p.getLabLogo().getOriginalFilename());
                    p.getLabLogo().transferTo(new File(folder + File.separator + fileFileName));
                    isFile = ",LOGO_IMAGE='" + fileFileName + "'";
                }
                query = "UPDATE TW_LAB_MASTER SET"
                        + " LAB_NME='" + p.getLabName() + "',"
                        + " WEB_URL='" + p.getLabUrl() + "'"
                        + isFile
                        + " WHERE TW_LAB_MASTER_ID=" + p.getMedicalLabId();

            } else {
                String prevId = "SELECT SEQ_TW_LAB_MASTER_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                String fileFileName = "";
                if (p.getLabLogo() != null && !p.getLabLogo().isEmpty()) {
                    String sep = File.separator;
                    String picPath = path + sep + masterId + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    fileFileName = Util.renameFileName(p.getLabLogo().getOriginalFilename());
                    p.getLabLogo().transferTo(new File(folder + File.separator + fileFileName));
                }

                query = "INSERT INTO TW_LAB_MASTER (TW_LAB_MASTER_ID,LAB_NME,WEB_URL,LOGO_IMAGE,PREPARED_BY,PREPARED_DTE) "
                        + " VALUES(" + masterId + ",INITCAP('" + Util.removeSpecialChar(p.getLabName().trim()) + "'),'" + Util.removeSpecialChar(p.getLabUrl().trim()) + "',"
                        + " '" + fileFileName + "','" + p.getUserName() + "',SYSDATE)";

            }
            int i = this.getDao().getJdbcTemplate().update(query);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getMedicalLab() {
        List<Map> list = null;
        try {
            String query = "SELECT LM.* FROM TW_LAB_MASTER LM ORDER BY LM.TW_LAB_MASTER_ID DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteMedicalLab(String MedicalLabId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_LAB_MASTER WHERE TW_LAB_MASTER_ID=" + MedicalLabId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getMedicalLabById(String MedicalLabId) {
        Map map = null;
        try {
            String query = "SELECT LM.* FROM TW_LAB_MASTER LM WHERE LM.TW_LAB_MASTER_ID=" + MedicalLabId + "";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean saveLabCollectionCenter(Lab p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        MD5 md = new MD5();
        String password = Util.generatePassword();
        String mdStr = md.calcMD5(password);
        Encryption pswdSec = new Encryption();
        String generatedPassword = pswdSec.encrypt(mdStr);
        try {
            String query = "";
            if (p.getLabCollectionCenterId() != null && !p.getLabCollectionCenterId().isEmpty()) {
                query = "UPDATE TW_LAB_DETAIL SET "
                        + "CENTER_NME=INITCAP('" + Util.removeSpecialChar(p.getCenterName().trim()) + "'),"
                        + "CONTACT_PERSON='" + Util.removeSpecialChar(p.getContactPerson().trim()) + "',"
                        + "MOBILE_NO='" + p.getCellNo() + "',"
                        + "LANDLINE_NO='" + p.getPtclNo() + "',"
                        + "EMAIL='" + Util.removeSpecialChar(p.getEmail().trim()) + "',"
                        + "CITY_ID=" + p.getCityId() + ","
                        + "CITY_AREA_ID=" + p.getAreaId() + ","
                        + "OPEN_FRM=TO_DATE('" + p.getTimeFrom() + "','HH24:MI'),"
                        + "OPEN_TO=TO_DATE('" + p.getTimeTo() + "','HH24:MI')"
                        + " WHERE TW_LAB_DETAIL_ID=" + p.getLabCollectionCenterId();
                arr.add(query);
            } else {
                String masterId = "";
                String prevId = "SELECT SEQ_TW_LAB_DETAIL_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_LAB_DETAIL (TW_LAB_DETAIL_ID,TW_LAB_MASTER_ID,CENTER_NME,CONTACT_PERSON,"
                        + "MOBILE_NO,LANDLINE_NO,EMAIL,CITY_ID,CITY_AREA_ID,OPEN_FRM,OPEN_TO,PREPARED_BY,PREPARED_DTE) "
                        + " VALUES(" + masterId + ","
                        + "" + p.getMedicalLabId() + ","
                        + "INITCAP('" + Util.removeSpecialChar(p.getCenterName().trim()) + "'),"
                        + "'" + Util.removeSpecialChar(p.getContactPerson().trim()) + "',"
                        + "'" + p.getCellNo() + "',"
                        + "'" + p.getPtclNo() + "',"
                        + "'" + Util.removeSpecialChar(p.getEmail().trim()) + "',"
                        + "" + p.getCityId() + ","
                        + "" + p.getAreaId() + ","
                        + "TO_DATE('" + p.getTimeFrom() + "','HH24:MI'),"
                        + "TO_DATE('" + p.getTimeTo() + "','HH24:MI'),"
                        + "'" + p.getUserName() + "',SYSDATE)";
                arr.add(query);
                if (!p.getLoginId().isEmpty() && p.getLoginId() != null) {
                    arr.add("INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,FIRST_NME,TW_LAB_DETAIL_ID,TW_LAB_MASTER_ID) VALUES ("
                            + " '" + Util.removeSpecialChar(p.getLoginId()).trim().toLowerCase() + "','" + generatedPassword + "',INITCAP('" + Util.removeSpecialChar(p.getCenterName()) + "'),"
                            + "" + masterId + "," + p.getMedicalLabId() + ")");
                }
            }
            flag = this.dao.insertAll(arr, p.getUserName());
            if (flag) {
                Util.sendSignUpMessage(p.getContactPerson(), Util.removeSpecialChar(p.getLoginId()).trim().toLowerCase(), password);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getLabCollectionCenter(String medicalLabId) {
        List<Map> list = null;
        try {
            if (medicalLabId != null && !medicalLabId.isEmpty()) {
                String query = "SELECT LD.TW_LAB_DETAIL_ID,LD.TW_LAB_MASTER_ID,LD.CENTER_NME,LD.CONTACT_PERSON,"
                        + "LD.MOBILE_NO,LD.LANDLINE_NO,LD.EMAIL,TO_CHAR(LD.OPEN_FRM,'HH24:MI') OPEN_FRM,"
                        + "TO_CHAR(LD.OPEN_TO,'HH24:MI') OPEN_TO,C.CITY_NME,CA.AREA_NME"
                        + " FROM TW_LAB_DETAIL LD,CITY C,CITY_AREA CA WHERE LD.CITY_ID=C.CITY_ID"
                        + " AND LD.CITY_AREA_ID=CA.CITY_AREA_ID AND LD.TW_LAB_MASTER_ID=" + medicalLabId
                        + " ORDER BY LD.TW_LAB_DETAIL_ID DESC";
                list = this.dao.getData(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteLabCollectionCenter(String labCollectionCenterId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_LAB_DETAIL WHERE TW_LAB_DETAIL_ID=" + labCollectionCenterId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getLabCollectionCenterById(String labCollectionCenterId) {
        Map map = null;
        try {
            String query = "SELECT LD.TW_LAB_DETAIL_ID,LD.TW_LAB_MASTER_ID,LD.CENTER_NME,LD.CONTACT_PERSON,"
                    + "LD.MOBILE_NO,LD.LANDLINE_NO,LD.EMAIL,TO_CHAR(LD.OPEN_FRM,'HH24:MI') OPEN_FRM,"
                    + "TO_CHAR(LD.OPEN_TO,'HH24:MI') OPEN_TO,LD.CITY_ID,LD.CITY_AREA_ID,TWU.USER_NME "
                    + "FROM TW_LAB_DETAIL LD,TW_WEB_USERS TWU "
                    + "WHERE LD.TW_LAB_DETAIL_ID=TWU.TW_LAB_DETAIL_ID AND LD.TW_LAB_DETAIL_ID=" + labCollectionCenterId + "";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map> getMedicalLab(String medicalLabId) {
        List<Map> list = null;
        try {
            String query = "SELECT LM.* FROM TW_LAB_MASTER LM WHERE LM.TW_LAB_MASTER_ID=" + medicalLabId + " ORDER BY LM.TW_LAB_MASTER_ID DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveLabAttachment(DoctorVO d) {
        boolean flag = false;
        String query = "";
        try {
            if (d.getDoctorId() != null) {
                String pic = "";
                if (d.getFile() != null && !d.getFile().isEmpty()) {
                    String sep = File.separator;
                    String picPath = d.getPath() + sep + d.getPatientId() + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    pic = new java.util.Date().getTime() + "_" + Util.renameFileName(d.getFile().getOriginalFilename());
                    d.getFile().transferTo(new File(folder + File.separator + pic));
                    query = "INSERT INTO TW_LAB_ATTACHMENT (TW_LAB_ATTACHMENT_ID,TW_DOCTOR_ID,TW_PATIENT_ID,TW_PRESCRIPTION_DETAIL_ID,"
                            + "FILE_NME,FILE_DESC,PREPARED_BY,PREPARED_DTE) "
                            + " VALUES(SEQ_TW_LAB_ATTACHMENT_ID.NEXTVAL," + d.getDoctorId() + "," + d.getPatientId() + "," + d.getPrescDetailId() + ","
                            + "'" + pic + "','" + d.getAttachDescription() + "','" + d.getUserName() + "',SYSDATE) ";
                    int i = this.getDao().getJdbcTemplate().update(query);
                    if (i > 0) {
                        flag = true;
                    }
                }
            }

        } catch (Exception exp) {
            exp.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Map> getLabActtachementsById(String doctorId, String patientId, String prescDetailId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_LAB_ATTACHMENT"
                    + " WHERE TW_DOCTOR_ID=" + doctorId + ""
                    + " AND TW_PATIENT_ID=" + patientId + ""
                    + " AND TW_PRESCRIPTION_DETAIL_ID=" + prescDetailId + " "
                    + " ORDER BY TW_LAB_ATTACHMENT_ID DESC";
            list = this.dao.getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteLabAttachement(String labAttachmentId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_LAB_ATTACHMENT WHERE TW_LAB_ATTACHMENT_ID=" + labAttachmentId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getMedicalSpeciality() {
        List<Map> list = null;
        try {
            String query = "SELECT MS.* FROM TW_MEDICAL_SPECIALITY MS ORDER BY MS.TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //Dose Usage
    @Override
    public boolean saveMedicineUsage(String titleEnglish, String titleUrdu, String specialityId, String medicineUsageId) {
        boolean flag = false;
        try {
            String query = "";
            if (medicineUsageId != null && !medicineUsageId.isEmpty()) {
                query = "UPDATE TW_DOSE_USAGE SET "
                        + " TITLE=INITCAP('" + Util.removeSpecialChar(titleEnglish.trim()) + "'),"
                        + " TITLE_URDU=INITCAP(N'" + Util.removeSpecialChar(titleUrdu.trim()) + "')"
                        + " WHERE TW_DOSE_USAGE_ID=" + medicineUsageId;
            } else {
                query = "INSERT INTO TW_DOSE_USAGE (TW_DOSE_USAGE_ID,TITLE,TITLE_URDU,TW_MEDICAL_SPECIALITY_ID) "
                        + " VALUES(SEQ_TW_DOSE_USAGE_ID.NEXTVAL,"
                        + "INITCAP('" + Util.removeSpecialChar(titleEnglish.trim()) + "'),"
                        + "INITCAP(N'" + Util.removeSpecialChar(titleUrdu.trim()) + "'),"
                        + "" + specialityId + ")";
            }
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getMedicineUsage(String specialityId) {
        List<Map> list = null;
        try {
            if (specialityId != null && !specialityId.isEmpty()) {
                String query = "SELECT DU.* FROM TW_DOSE_USAGE DU "
                        + " WHERE DU.TW_MEDICAL_SPECIALITY_ID=" + specialityId
                        + " ORDER BY DU.TITLE";
                list = this.dao.getData(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteMedicineUsage(String medicineUsageId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_DOSE_USAGE WHERE TW_DOSE_USAGE_ID=" + medicineUsageId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getMedicineUsageById(String medicineUsageId) {
        Map map = null;
        try {
            String query = "SELECT DU.* FROM TW_DOSE_USAGE DU WHERE DU.TW_DOSE_USAGE_ID=" + medicineUsageId;
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean saveReadings(String sugar, String bloodPressure, String fever, String doctorId, String patientId, String username) {
        boolean flag = false;
        try {
            String query = "";
            query = "INSERT INTO TW_PATIENT_READING"
                    + " (TW_PATIENT_READING_ID,TW_PATIENT_ID,TW_DOCTOR_ID,FEVER,BLOOD_PRESSURE,SUGAR,PREPARED_BY,PREPARED_DTE) "
                    + " VALUES(SEQ_TW_PATIENT_READING_ID.NEXTVAL,"
                    + " " + patientId + ","
                    + " " + doctorId + ","
                    + " " + (fever.isEmpty() ? 0 : fever) + ","
                    + " " + (bloodPressure.isEmpty() ? 0 : bloodPressure) + ","
                    + " " + (sugar.isEmpty() ? 0 : sugar) + ","
                    + " '" + username + "',SYSDATE)";

            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getReading(String patientId, String doctorId) {
        Map map = null;
        try {
            String query = "SELECT TWPR.* FROM ("
                    + "  SELECT TWPR.*,ROW_NUMBER() OVER (PARTITION BY TWPR.TW_PATIENT_ID,TWPR.TW_DOCTOR_ID ORDER BY TWPR.PREPARED_DTE DESC) AS LAST_ROW "
                    + "    FROM TW_PATIENT_READING TWPR WHERE TWPR.TW_PATIENT_ID=" + patientId + " AND TWPR.TW_DOCTOR_ID=" + doctorId + ""
                    + ") TWPR WHERE TWPR.LAST_ROW=1";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map> getAppointmentDates(String doctorId, String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT TO_CHAR(DT.DAYS,'DD-MM-YYYY') DTE FROM ("
                    + " SELECT (SYSDATE + ROWNUM -1) DAYS"
                    + " FROM ALL_OBJECTS "
                    + " WHERE ROWNUM <= (SYSDATE+14)-SYSDATE+1 "
                    + " )DT WHERE TO_DATE(DT.DAYS,'DD-MM-YYYY') NOT IN ("
                    + " SELECT TO_DATE(AP.APPOINTMENT_DTE,'DD-MM-YYYY') "
                    + " FROM TW_APPOINTMENT AP,TW_DOCTOR_CLINIC DC"
                    + " WHERE AP.APPOINTMENT_DTE BETWEEN SYSDATE-1 AND SYSDATE+14"
                    + " AND AP.TW_DOCTOR_ID=DC.TW_DOCTOR_ID"
                    + " AND AP.TW_CLINIC_ID=DC.TW_CLINIC_ID"
                    + " AND AP.TW_DOCTOR_ID=" + doctorId + ""
                    + " AND AP.TW_CLINIC_ID=" + clinicId + ""
                    + " GROUP BY AP.APPOINTMENT_DTE"
                    + " HAVING COUNT(AP.APPOINTMENT_DTE)>MAX(DC.TOTAL_APPOINTMENT)"
                    + " ) AND TRIM(TO_CHAR(DT.DAYS,'DY')) IN ("
                    + "  SELECT WEEK_DAY FROM TW_DOCTOR_DAYS WHERE TW_DOCTOR_ID=" + doctorId + " "
                    + " AND TW_CLINIC_ID=" + clinicId + ")"
                    + " ORDER BY DT.DAYS";

            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getAppointedTime(String doctorId, String clinicId, String date) {
        List<Map> list = null;
        try {
            String query = "SELECT TO_CHAR(APPOINTMENT_TIME,'HH24:MI') APPOINTED_TIME"
                    + " FROM TW_APPOINTMENT AP"
                    + " WHERE AP.APPOINTMENT_DTE = TO_DATE('" + date + "','DD-MM-YYYY')"
                    + " AND AP.TW_DOCTOR_ID=" + doctorId + ""
                    + " AND AP.TW_CLINIC_ID=" + clinicId + "";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getDoctorClinic(String clinicId) {
        Map map = null;
        try {
            String query = "SELECT TO_CHAR(TIME_FROM,'HH24:MI') TIME_FROM,TO_CHAR(TIME_TO,'HH24:MI') TIME_TO,TW_DOCTOR_ID"
                    + " FROM TW_DOCTOR_CLINIC WHERE TW_CLINIC_ID=" + clinicId + "";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Map> getPrescriptionMasterForPatient(String patientId) {
        List<Map> list = null;
        try {
            String query = "SELECT PM.TW_PRESCRIPTION_MASTER_ID,MAX(PM.REMARKS) REMARKS,"
                    + " MAX(DOC.DOCTOR_NME) DOCTOR_NME,MAX(CL.CLINIC_NME) CLINIC_NME,TO_CHAR(MAX(PM.PREPARED_DTE),'DD-MON-YY HH:MI AM') PREPARED_DTE"
                    + " FROM TW_PRESCRIPTION_MASTER PM,TW_DOCTOR DOC,TW_CLINIC CL"
                    + " WHERE PM.TW_DOCTOR_ID=DOC.TW_DOCTOR_ID"
                    + " AND PM.TW_CLINIC_ID=CL.TW_CLINIC_ID"
                    + " AND PM.TW_PATIENT_ID=" + patientId + ""
                    + " GROUP BY PM.TW_PRESCRIPTION_MASTER_ID"
                    + " ORDER BY PM.TW_PRESCRIPTION_MASTER_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveIntakeQuestion(String questionMasterId, String specialityId, String title, String userName) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";
            if (questionMasterId != null && !questionMasterId.isEmpty()) {
                query = "UPDATE TW_INTAKE_MASTER SET QUESTION_TXT=INITCAP('" + Util.removeSpecialChar(title.trim()) + "')"
                        + " WHERE TW_INTAKE_MASTER_ID=" + questionMasterId + "";
                arr.add(query);
            } else {
                String prevId = "SELECT SEQ_TW_INTAKE_MASTER_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_INTAKE_MASTER(TW_INTAKE_MASTER_ID,TW_MEDICAL_SPECIALITY_ID,QUESTION_TXT,PREPARED_BY)"
                        + " VALUES (" + masterId + "," + specialityId
                        + ",INITCAP('" + Util.removeSpecialChar(title.trim()) + "'),'" + userName + "')";
                arr.add(query);
                query = "INSERT INTO TW_INTAKE_DETAIL(TW_INTAKE_DETAIL_ID,TW_INTAKE_MASTER_ID,ANSWER_TXT)"
                        + " VALUES (SEQ_TW_INTAKE_DETAIL_ID.NEXTVAL," + masterId
                        + ",'Yes')";
                arr.add(query);
                query = "INSERT INTO TW_INTAKE_DETAIL(TW_INTAKE_DETAIL_ID,TW_INTAKE_MASTER_ID,ANSWER_TXT)"
                        + " VALUES (SEQ_TW_INTAKE_DETAIL_ID.NEXTVAL," + masterId
                        + ",'No')";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getIntakeQuestionForDoctor(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT DU.* FROM TW_INTAKE_MASTER DU "
                    + " WHERE DU.TW_MEDICAL_SPECIALITY_ID IN "
                    + " (SELECT TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + doctorId + ")"
                    + " ORDER BY DU.TW_INTAKE_MASTER_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getIntakeQuestions(String specialityId) {
        List<Map> list = null;
        try {
            String query = "SELECT  * FROM TW_INTAKE_MASTER "
                    + " WHERE TW_MEDICAL_SPECIALITY_ID=" + specialityId + " "
                    + " ORDER BY TW_INTAKE_MASTER_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getIntakeAnswers(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT DID.ANSWER_TXT,DID.TW_INTAKE_DETAIL_ID,DID.TW_INTAKE_MASTER_ID"
                    + " FROM TW_INTAKE_MASTER DU,TW_INTAKE_DETAIL DID "
                    + " WHERE DU.TW_MEDICAL_SPECIALITY_ID IN "
                    + " (SELECT TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + doctorId + " )"
                    + " AND DID.TW_INTAKE_MASTER_ID=DU.TW_INTAKE_MASTER_ID"
                    + " ORDER BY DU.TW_INTAKE_MASTER_ID,DID.ANSWER_TXT";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getIntakeQuestionById(String questionMasterId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_INTAKE_MASTER WHERE TW_INTAKE_MASTER_ID=" + questionMasterId + "";

            List<Map> list = this.getDao().getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean deleteExaminationQuestion(String questionMasterId) {
        boolean flag = false;
        try {
            List<String> arr = new ArrayList();
            arr.add("DELETE FROM TW_INTAKE_DETAIL WHERE TW_INTAKE_MASTER_ID=" + questionMasterId + "");
            arr.add("DELETE FROM TW_INTAKE_MASTER WHERE TW_INTAKE_MASTER_ID=" + questionMasterId + "");
            flag = this.dao.insertAll(arr, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    private String generateRevisionNo(String patientId) {
        String revisionNo = "";
        String query = "SELECT (NVL(MAX(REVISION_NO),0)+1) NEXT_REV FROM TW_PATIENT_INTAKE_MASTER"
                + " WHERE TW_PATIENT_ID=" + patientId;

        List<Map> list = this.getDao().getData(query);
        if (list != null && list.size() > 0) {
            Map map = list.get(0);
            revisionNo = map.get("NEXT_REV").toString();
        }

        return revisionNo;
    }

    @Override
    public boolean saveInTakeForm(String patientId, String doctorId, String questionarr[], String answerarr[], String diseases[], String userName) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";
            String prevId = "SELECT SEQ_TW_PTNT_INTAKE_MASTER_ID.NEXTVAL VMASTER FROM DUAL";
            List list = this.getDao().getJdbcTemplate().queryForList(prevId);
            if (list != null && list.size() > 0) {
                Map map = (Map) list.get(0);
                masterId = (String) map.get("VMASTER").toString();
            }
            query = "INSERT INTO TW_PATIENT_INTAKE_MASTER"
                    + "(TW_PATIENT_INTAKE_MASTER_ID,TW_PATIENT_ID,TW_DOCTOR_ID,REVISION_NO,PREPARED_BY)"
                    + " VALUES (" + masterId + "," + patientId
                    + "," + doctorId + "," + this.generateRevisionNo(patientId) + ",'" + userName + "')";
            arr.add(query);
            for (int i = 0; i < questionarr.length; i++) {
                query = "INSERT INTO TW_PATIENT_INTAKE_DETAIL"
                        + "(TW_PATIENT_INTAKE_DETAIL_ID,TW_PATIENT_INTAKE_MASTER_ID,TW_INTAKE_MASTER_ID,TW_INTAKE_DETAIL_ID,REMARKS)"
                        + " VALUES (SEQ_TW_PTNT_INTAKE_DETAIL_ID.NEXTVAL," + masterId
                        + "," + questionarr[i] + "," + answerarr[i] + ",NULL)";
                arr.add(query);
            }
            query = "DELETE FROM TW_PATIENT_DISEASE WHERE TW_PATIENT_ID=" + patientId + "";
            arr.add(query);
            if (diseases != null) {
                for (int i = 0; i < diseases.length; i++) {
                    query = "INSERT INTO TW_PATIENT_DISEASE(TW_PATIENT_DISEASE_ID,TW_DISEASE_ID,TW_PATIENT_ID)"
                            + " VALUES (SEQ_TW_PATIENT_DISEASE_ID.NEXTVAL," + diseases[i] + "," + patientId + ")";

                    arr.add(query);
                }
            }
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getIntakeFormData(String patientId) {
        List<Map> list = null;
        try {
            String query = "SELECT ID.TW_INTAKE_DETAIL_ID,ID.TW_PATIENT_INTAKE_MASTER_ID,"
                    + " ID.TW_PATIENT_INTAKE_DETAIL_ID,ID.TW_INTAKE_MASTER_ID,TID.ANSWER_TXT,"
                    + " TIM.QUESTION_TXT"
                    + " FROM TW_PATIENT_INTAKE_MASTER IM,TW_PATIENT_INTAKE_DETAIL ID,TW_INTAKE_DETAIL TID,TW_INTAKE_MASTER TIM"
                    + " WHERE ID.TW_PATIENT_INTAKE_MASTER_ID=IM.TW_PATIENT_INTAKE_MASTER_ID"
                    + " AND IM.TW_PATIENT_ID=" + patientId + ""
                    + " AND ID.TW_INTAKE_DETAIL_ID=TID.TW_INTAKE_DETAIL_ID"
                    + " AND ID.TW_INTAKE_MASTER_ID=TIM.TW_INTAKE_MASTER_ID"
                    + " AND IM.REVISION_NO =(SELECT (NVL(MAX(REVISION_NO),0)) REV_NBR FROM TW_PATIENT_INTAKE_MASTER WHERE TW_PATIENT_ID=" + patientId + ")"
                    + " ORDER BY ID.TW_INTAKE_MASTER_ID,ID.TW_INTAKE_DETAIL_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getVaccinationByDoctorSpecility(String doctorId) {
        List<Map> list = null;
        try {
//            String query = "SELECT * FROM TW_VACCINATION_MASTER WHERE TW_MEDICAL_SPECIALITY_ID IN"
//                    + " (SELECT TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + doctorId + ")";
            String query = "SELECT VM.TW_VACCINATION_MASTER_ID,MAX(VM.VACCINATION_NME) VACCINATION_NME,"
                    + " MAX(VM.ABBREV) ABBREV,"
                    + " LISTAGG(VD.MEDICINE_NME, '<br/>') WITHIN GROUP (ORDER BY VD.MEDICINE_NME) DOSE_LISTING"
                    + " FROM TW_VACCINATION_MASTER VM,TW_VACCINATION_DETAIL VD"
                    + " WHERE VM.TW_MEDICAL_SPECIALITY_ID IN"
                    + " (SELECT TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + doctorId + ")"
                    + " AND VM.TW_VACCINATION_MASTER_ID=VD.TW_VACCINATION_MASTER_ID"
                    + " GROUP BY VM.TW_VACCINATION_MASTER_ID"
                    + " ORDER BY MAX(VM.VACCINATION_NME)";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveVaccination(String patientId, String doctorId, String clinicId, String[] vaccinationMasterId, String userName) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            for (int i = 0; i < vaccinationMasterId.length; i++) {
                query = "INSERT INTO TW_PATIENT_VACCINATION"
                        + "(TW_PATIENT_VACCINATION_ID,TW_PATIENT_ID,VACCINATION_DTE,TW_VACCINATION_MASTER_ID,"
                        + "TW_DOCTOR_ID,TW_CLINIC_ID,PREPARED_BY,PREPARED_DTE)"
                        + " VALUES (SEQ_TW_PATIENT_VACCINATION_ID.NEXTVAL," + patientId
                        + ",SYSDATE," + vaccinationMasterId[i] + "," + doctorId + "," + clinicId + ","
                        + " '" + userName + "',SYSDATE)";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPateintVaccination(String doctorId, String patientId) {
        List<Map> list = null;
        try {
            String query = "SELECT VM.VACCINATION_NME,VM.ABBREV,VM.TW_VACCINATION_MASTER_ID,TO_CHAR(PV.VACCINATION_DTE,'DD-MON-YYYY')"
                    + " VACCINATION_DTE FROM TW_PATIENT_VACCINATION PV,TW_VACCINATION_MASTER VM WHERE"
                    + " PV.TW_VACCINATION_MASTER_ID=VM.TW_VACCINATION_MASTER_ID AND"
                    + " PV.TW_DOCTOR_ID=" + doctorId + " AND PV.TW_PATIENT_ID=" + patientId
                    + " ORDER BY PV.VACCINATION_DTE,VM.VACCINATION_NME";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getPateintVaccinationMedicine(String masterId, String dte) {
        List<Map> list = null;
        try {
            String query = "SELECT VD.MEDICINE_NME,VD.TW_VACCINATION_DETAIL_ID,VD.TOTAL_DOSE"
                    + " FROM TW_PATIENT_VACCINATION PV,TW_VACCINATION_DETAIL VD WHERE"
                    + " PV.TW_VACCINATION_DETAIL_ID=VD.TW_VACCINATION_DETAIL_ID AND"
                    + " PV.TW_VACCINATION_MASTER_ID=" + masterId
                    + " AND TO_CHAR(PV.VACCINATION_DTE,'DD-MM-YYYY')='" + dte + "'";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
