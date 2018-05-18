/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.doctor;

import com.alberta.dao.DAO;
import com.alberta.model.CategoryVO;
import com.alberta.model.DoctorVO;
import com.alberta.model.Encryption;
import com.alberta.utility.MD5;
import com.alberta.utility.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author farazahmad
 */
public class DoctorServiceImpl implements DoctorService {

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
    public boolean saveDoctor(DoctorVO vo) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";
            if (vo.getDoctorId() != null && !vo.getDoctorId().isEmpty()) {
                query = "UPDATE TW_DOCTOR SET DOCTOR_NME=INITCAP('" + Util.removeSpecialChar(vo.getDoctorName()) + "'),"
                        + " DOCTOR_CATEGORY_ID=" + (vo.getDoctorType().isEmpty() ? null : vo.getDoctorType()) + ","
                        + " EXPERIENCE=" + (vo.getTotalExperience().isEmpty() ? 1 : vo.getTotalExperience()) + ","
                        + " CITY_ID=" + (vo.getCityId().isEmpty() ? null : vo.getCityId()) + ","
                        + " CNIC='" + (vo.getCnic() == null ? null : vo.getCnic()) + "',"
                        + " MOBILE_NO='" + Util.removeSpecialChar(vo.getCellNo()) + "',"
                        + " EMAIL='" + Util.removeSpecialChar(vo.getDoctorEmail().trim()) + "',"
                        + " COUNTRY_ID=" + (vo.getCountryId().isEmpty() ? null : vo.getCountryId()) + ","
                        + " ALLOW_VIDEO='" + vo.getServicesAvail() + "',"
                        + " LINKEDIN_URL='" + vo.getLink() + "',"
                        + " PRESCRIPTION_LANG='" + vo.getPrescriptionLang() + "',"
                        + " VIDEO_CLINIC_FROM=TO_DATE('" + vo.getVideoTimeFrom() + "','HH24:MI'),"
                        + " VIDEO_CLINIC_TO=TO_DATE('" + vo.getVideoTimeTo() + "','HH24:MI'),"
                        + " PMDC_NO='" + Util.removeSpecialChar(vo.getPmdcNo()) + "',"
                        + " ACCOUNT_IND='T'"
                        + " WHERE TW_DOCTOR_ID=" + vo.getDoctorId() + "";
                arr.add(query);
                if (vo.getProcedureFeeId() != null && !vo.getProcedureFeeId().isEmpty()) {
                    query = "UPDATE TW_PROCEDURE_FEE SET FEE=" + vo.getConsultancyFee() + ""
                            + " WHERE TW_PROCEDURE_FEE_ID=" + vo.getProcedureFeeId() + "";
                    arr.add(query);
                }
            } else {
                String prevId = "SELECT SEQ_TW_DOCTOR_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                if (vo.getVideoTimeFrom() == null) {
                    vo.setVideoTimeFrom("16:00");
                }
                if (vo.getVideoTimeTo() == null) {
                    vo.setVideoTimeTo("22:00");
                }
                query = "INSERT INTO TW_DOCTOR(TW_DOCTOR_ID,DOCTOR_NME,MOBILE_NO,"
                        + "DOCTOR_CATEGORY_ID,PREPARED_BY,"
                        + "EXPERIENCE,PMDC_NO,EMAIL,ACCOUNT_IND)"
                        + " VALUES (" + masterId + ",INITCAP('" + Util.removeSpecialChar(vo.getDoctorName()) + "'),"
                        + "'" + Util.removeSpecialChar(vo.getCellNo().trim()) + "'," + vo.getDoctorType() + ","
                        + "'" + vo.getUserName() + "',"
                        + " " + (vo.getTotalExperience().isEmpty() ? 1 : vo.getTotalExperience())
                        + ",'" + Util.removeSpecialChar(vo.getPmdcNo()) + "',"
                        + " '" + Util.removeSpecialChar(vo.getDoctorEmail().trim()) + "','T')";
                arr.add(query);
                query = "DELETE FROM TW_PROCEDURE_FEE WHERE TW_DOCTOR_ID=" + masterId + "";
                arr.add(query);
                arr.add("INSERT INTO TW_PROCEDURE_FEE VALUES (SEQ_TW_PROCEDURE_FEE_ID.NEXTVAL," + masterId + ",2,"
                        + (vo.getConsultancyFee().isEmpty() ? 0 : vo.getConsultancyFee()) + "," + vo.getDiscount() + ",'" + vo.getUserName() + "',SYSDATE,"
                        + vo.getCompanyId() + ")");
                query = "DELETE FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + masterId + "";
                arr.add(query);
                if (vo.getSpecility() != null && vo.getSpecility().length > 0) {
                    for (int j = 0; j < vo.getSpecility().length; j++) {
                        arr.add("INSERT INTO TW_DOCTOR_SPECIALITY(TW_DOCTOR_SPECIALITY_ID,TW_DOCTOR_ID,TW_MEDICAL_SPECIALITY_ID) VALUES "
                                + " (SEQ_TW_DOCTOR_SPECIALITY_ID.NEXTVAL," + masterId + "," + vo.getSpecility()[j] + ")");
                    }
                }
                query = "DELETE FROM TW_DOCTOR_SERVICE WHERE TW_DOCTOR_ID=" + masterId + "";
                arr.add(query);
                if (vo.getServices() != null && vo.getServices().length > 0) {
                    for (int i = 0; i < vo.getServices().length; i++) {
                        query = "INSERT INTO TW_DOCTOR_SERVICE(TW_DOCTOR_SERVICE_ID,TW_MEDICAL_SERVICE_ID,TW_DOCTOR_ID )"
                                + " VALUES (SEQ_TW_DOCTOR_SERVICE_ID.NEXTVAL," + vo.getServices()[i] + "," + masterId + ")";

                        arr.add(query);
                    }
                }
                // assign clinic to doctor
                query = "DELETE FROM TW_DOCTOR_CLINIC WHERE TW_DOCTOR_ID=" + masterId + "";
                arr.add(query);
                if (vo.getClinicId() != null && !vo.getClinicId().isEmpty()) {
                    arr.add("INSERT INTO TW_DOCTOR_CLINIC(TW_DOCTOR_CLINIC_ID,TW_DOCTOR_ID,TW_CLINIC_ID,TIME_FROM,TIME_TO,TOTAL_APPOINTMENT) "
                            + " VALUES (SEQ_TW_DOCTOR_CLINIC_ID.NEXTVAL," + masterId + ","
                            + " " + vo.getClinicId() + ",TO_DATE('" + vo.getTimeFrom() + "','HH24:MI'),"
                            + "TO_DATE('" + vo.getTimeTo() + "','HH24:MI'),20)");
                }

                query = "DELETE FROM TW_DOCTOR_DAYS WHERE TW_DOCTOR_ID=" + masterId + " AND TW_CLINIC_ID=" + vo.getClinicId() + "";
                arr.add(query);
                if (vo.getWeekDays() != null && vo.getWeekDays().length > 0) {
                    for (int i = 0; i < vo.getWeekDays().length; i++) {
                        query = "INSERT INTO TW_DOCTOR_DAYS (TW_DOCTOR_DAYS_ID,TW_DOCTOR_ID,TW_CLINIC_ID,WEEK_DAY) VALUES "
                                + " (SEQ_TW_DOCTOR_DAYS_ID.NEXTVAL," + masterId + "," + vo.getClinicId() + ",'" + vo.getWeekDays()[i] + "')";
                        arr.add(query);
                    }
                }
                if (vo.getProfileImage() != null && !vo.getProfileImage().isEmpty()) {
                    String folderPath = vo.getPath() + File.separator + "profilePic" + File.separator + masterId;
                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(folderPath)).mkdir();
                    }
                    String fileName = new java.util.Date().getTime() + "_" + Util.renameFileName(vo.getProfileImage().getOriginalFilename());
                    arr.add("UPDATE TW_DOCTOR SET PROFILE_IMAGE='" + fileName + "' WHERE TW_DOCTOR_ID=" + masterId + "");
                    vo.getProfileImage().transferTo(new File(folder + File.separator + fileName));
                }
                if (vo.getVisitingCardImage() != null && !vo.getVisitingCardImage().isEmpty()) {
                    String folderPath = vo.getPath() + File.separator + "visitingCard" + File.separator + masterId;
                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(folderPath)).mkdir();
                    }
                    String fileName = new java.util.Date().getTime() + "_" + Util.renameFileName(vo.getVisitingCardImage().getOriginalFilename());
                    arr.add("UPDATE TW_DOCTOR SET VISITING_CARD='" + fileName + "' WHERE TW_DOCTOR_ID=" + masterId + "");
                    vo.getVisitingCardImage().transferTo(new File(folder + File.separator + fileName));
                }
            }
            flag = this.dao.insertAll(arr, vo.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getTempDoctorById(String doctorId) {
        Map map = null;
        try {
            String query = "SELECT D.TW_DOCTOR_ID,D.DOCTOR_NME,D.MOBILE_NO,D.EMAIL"
                    + " ,D.DOCTOR_CATEGORY_ID,"
                    + " D.EXPERIENCE,D.PRESCRIPTION_LANG,D.PROFILE_IMAGE,D.TW_DOCTOR_TYPE_ID,"
                    + " D.PMDC_NO,D.VISITING_CARD,PF.FEE,MS.TW_MEDICAL_SPECIALITY_ID,DS.TW_DOCTOR_SERVICE_ID,WD.WEEK_DAY,"
                    + " CL.TW_CLINIC_ID,CL.TIME_FROM,CL.TIME_TO"
                    + " FROM TW_DOCTOR D,("
                    + " SELECT TW_DOCTOR_ID,MAX(FEE) FEE FROM TW_PROCEDURE_FEE  GROUP BY TW_DOCTOR_ID) PF,"
                    + " (SELECT TW_DOCTOR_ID,LISTAGG(TW_MEDICAL_SPECIALITY_ID,',') WITHIN GROUP (ORDER BY TW_MEDICAL_SPECIALITY_ID) AS TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + ""
                    + " GROUP BY TW_DOCTOR_ID"
                    + " ) MS,"
                    + " (SELECT TW_DOCTOR_ID,LISTAGG(TW_DOCTOR_SERVICE_ID,',') WITHIN GROUP (ORDER BY TW_DOCTOR_SERVICE_ID) AS TW_DOCTOR_SERVICE_ID   FROM TW_DOCTOR_SERVICE "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + ""
                    + " GROUP BY TW_DOCTOR_ID) DS,"
                    + " (SELECT TW_DOCTOR_ID,LISTAGG(WEEK_DAY,',') WITHIN GROUP (ORDER BY WEEK_DAY) AS WEEK_DAY   FROM TW_DOCTOR_DAYS "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + ""
                    + " GROUP BY TW_DOCTOR_ID) WD,"
                    + " (SELECT TW_DOCTOR_ID,TW_CLINIC_ID,TO_CHAR(TIME_FROM,'HH24:MI') TIME_FROM,"
                    + " TO_CHAR(TIME_TO,'HH24:MI') TIME_TO"
                    + " FROM TW_DOCTOR_CLINIC "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + ") CL"
                    + " WHERE D.TW_DOCTOR_ID=PF.TW_DOCTOR_ID(+) "
                    + " AND D.TW_DOCTOR_ID=MS.TW_DOCTOR_ID(+) "
                    + " AND D.TW_DOCTOR_ID=DS.TW_DOCTOR_ID(+) "
                    + " AND D.TW_DOCTOR_ID=WD.TW_DOCTOR_ID(+)"
                    + " AND D.TW_DOCTOR_ID=CL.TW_DOCTOR_ID(+)  "
                    + " AND D.TW_DOCTOR_ID= " + doctorId + "";
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
    public List<Map> getTempDoctors(String doctorName, String mobileNbr, String doctorType) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT DR.TW_DOCTOR_ID,DR.DOCTOR_NME,DR.MOBILE_NO,DR.CNIC,DR.GENDER,DR.EMAIL,TO_CHAR(DR.DOB,'DD-MON-YYYY') DOB,"
                    + "  DR.ADDRESS,DR.DOCTOR_CATEGORY_ID,DR.FEATURED_IND,DC.TW_DOCTOR_CATEGORY_ID,DC.TITLE,MD.ABBREV  AS DEGREETITLE,"
                    + "  NVL(DAT.COUNT,0) TOTAL_ATTACHMENTS,TO_CHAR(DR.EXPIRY_DTE,'DD-MON-YYYY') EXPIRY_DTE,DR.PMDC_NO"
                    + "  FROM TW_DOCTOR DR,TW_DOCTOR_CATEGORY DC,TW_MEDICAL_DEGREE MD,("
                    + "  SELECT TW_DOCTOR_ID,COUNT(FILE_NME) COUNT FROM TW_DOCTOR_ATTACHMENT"
                    + "  WHERE ATTACHMENT_TYP='WEB ATTACHMENT' "
                    + "  GROUP BY TW_DOCTOR_ID) DAT"
                    + "  WHERE DR.DOCTOR_CATEGORY_ID=DC.TW_DOCTOR_CATEGORY_ID"
                    + "  AND DR.TW_MEDICAL_DEGREE_ID=MD.TW_MEDICAL_DEGREE_ID(+)"
                    + "  AND DR.ACCOUNT_IND='T'"
                    + "  AND DR.TW_DOCTOR_ID=DAT.TW_DOCTOR_ID(+)";

            if (doctorName != null && !doctorName.trim().isEmpty()) {
                where += " AND UPPER(DR.DOCTOR_NME) LIKE '%" + doctorName.toUpperCase() + "%' ";
            }
            if (mobileNbr != null && !mobileNbr.trim().isEmpty()) {
                if (where.contains("WHERE")) {
                    where += " AND DR.MOBILE_NO LIKE '%" + mobileNbr.trim() + "%'";
                } else {
                    where += " AND DR.MOBILE_NO LIKE '%" + mobileNbr.trim() + "%'";
                }
            }
            if (doctorType != null && !doctorType.isEmpty()) {
                if (where.contains("WHERE")) {
                    where += " AND DR.TW_DOCTOR_CATEGORY_ID =" + doctorType + "";
                } else {
                    where += " AND DR.TW_DOCTOR_CATEGORY_ID =" + doctorType + "";
                }
            }
            list = this.getDao().getData(query + where + " ORDER BY DR.DOCTOR_NME");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveDiagnostic(String diagnosticId, String specialityId, String title, String userName, String diagnosticInd) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            if (diagnosticId != null && !diagnosticId.isEmpty()) {
                query = "UPDATE TW_DIAGNOSTICS SET TITLE=INITCAP('" + Util.removeSpecialChar(title.trim()) + "'),"
                        + " INPUT_FIELD='" + (diagnosticInd != null && !diagnosticInd.isEmpty() ? diagnosticInd : 'N') + "'"
                        + " WHERE TW_DIAGNOSTICS_ID=" + diagnosticId + "";
                arr.add(query);
            } else {
                query = "INSERT INTO TW_DIAGNOSTICS(TW_DIAGNOSTICS_ID,TW_MEDICAL_SPECIALITY_ID,TITLE,PREPARED_BY,INPUT_FIELD,PREPARED_DTE)"
                        + " VALUES (SEQ_TW_DIAGNOSTICS_ID.NEXTVAL," + specialityId
                        + ",INITCAP('" + Util.removeSpecialChar(title.trim()) + "'),'" + userName
                        + "','" + (diagnosticInd != null && !diagnosticInd.isEmpty() ? diagnosticInd : 'N') + "',SYSDATE)";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getDiagnostic(String specialityId) {
        List<Map> list = null;
        try {
            String query = "SELECT  * FROM TW_DIAGNOSTICS"
                    + " WHERE TW_MEDICAL_SPECIALITY_ID=" + specialityId + ""
                    + " ORDER BY TW_DIAGNOSTICS_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getDiagnosticById(String diagnosticId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_DIAGNOSTICS WHERE TW_DIAGNOSTICS_ID=" + diagnosticId + "";

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
    public boolean deleteDiagnostic(String diagnosticId) {
        boolean flag = false;
        try {
            List<String> arr = new ArrayList();
            arr.add("DELETE FROM TW_DIAGNOSTICS "
                    + " WHERE TW_DIAGNOSTICS_ID=" + diagnosticId + "");
            flag = this.dao.insertAll(arr, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    //Vaccination Categories
    @Override
    public boolean saveVaccinationCategory(CategoryVO vo) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";
            if (vo.getQuestionCategoryId() != null && !vo.getQuestionCategoryId().isEmpty()) {
                query = "UPDATE TW_VACCINATION_CATEGORY SET CATEGORY_NME=INITCAP('" + Util.removeSpecialChar(vo.getCategoryName().trim()) + "')"
                        + " WHERE TW_VACCINATION_CATEGORY_ID=" + vo.getQuestionCategoryId() + "";
                arr.add(query);
            } else {
                String prevId = "SELECT SEQ_TW_VACCINATION_CATEGORY_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_VACCINATION_CATEGORY(TW_VACCINATION_CATEGORY_ID,TW_MEDICAL_SPECIALITY_ID,CATEGORY_NME,PREPARED_BY)"
                        + " VALUES (" + masterId + "," + vo.getSpecialityId()
                        + ",INITCAP('" + Util.removeSpecialChar(vo.getCategoryName().trim()) + "'),'" + vo.getUserName() + "')";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, vo.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getVaccinationCategories(String specialityId) {
        List<Map> list = null;
        try {
            String query = "SELECT  * FROM TW_VACCINATION_CATEGORY WHERE TW_MEDICAL_SPECIALITY_ID=" + specialityId
                    + " ORDER BY TW_VACCINATION_CATEGORY_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getVaccinationCategoryById(String questionCategoryId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_VACCINATION_CATEGORY WHERE TW_VACCINATION_CATEGORY_ID=" + questionCategoryId + "";

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
    public boolean deleteVaccinationCategory(String questionCategoryId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_VACCINATION_CATEGORY WHERE TW_VACCINATION_CATEGORY_ID=" + questionCategoryId + "";
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
    public List<Map> getVaccinationCategoriesForDoctor(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT DU.* FROM TW_VACCINATION_CATEGORY DU "
                    + " WHERE DU.TW_MEDICAL_SPECIALITY_ID IN "
                    + " (SELECT TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + doctorId + ")"
                    + " ORDER BY DU.CATEGORY_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
