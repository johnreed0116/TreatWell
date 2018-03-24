/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.setup;

import com.alberta.dao.DAO;
import com.alberta.model.*;
import com.alberta.utility.MD5;
import com.alberta.utility.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Faraz
 */
public class SetupServiceImpl implements SetupService {

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
    public boolean saveDoctorAttachment(DoctorVO d, String path) {
        boolean flag = false;
        String query = "";
        try {
            if (d.getDoctorId() != null) {
                String pic = "";
                if (d.getFile() != null && !d.getFile().isEmpty()) {
                    String sep = File.separator;
                    String picPath = path + sep + d.getDoctorId() + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    pic = new java.util.Date().getTime() + "_" + Util.renameFileName(d.getFile().getOriginalFilename());
                    d.getFile().transferTo(new File(folder + File.separator + pic));
                }

                query = "INSERT INTO TW_DOCTOR_ATTACHMENT (TW_DOCTOR_ATTACHMENT_ID,TW_DOCTOR_ID,FILE_NME,FILE_DESC,ATTACHMENT_TYP,PREPARED_BY,PREPARED_DTE) "
                        + " VALUES(SEQ_TW_DOCTOR_ATTACHMENT_ID.NEXTVAL," + d.getDoctorId() + ",'" + pic + "',"
                        + "'" + d.getAttachDescription() + "','" + d.getDoctorAttachmentType() + "','" + d.getUserName() + "',SYSDATE) ";

                int i = this.getDao().getJdbcTemplate().update(query);
                if (i > 0) {
                    flag = true;
                }
            }

        } catch (Exception exp) {
            exp.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public Company getCompanyById(String id) {
        Company company = null;
        try {
            List list = this.getDao().getJdbcTemplate().query("SELECT * FROM COMPANY WHERE COMPANY_ID=" + id + "", new Company());
            if (list != null && list.size() > 0) {
                company = (Company) list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return company;
    }

    @Override
    public List<Map> getDiseases(String showInd) {
        List<Map> list = null;
        try {
            String where = "";
            if (showInd != null && !showInd.isEmpty() && showInd.equalsIgnoreCase("Y")) {
                where = " WHERE SHOW_INTAKE_IND='Y'";
            }
            String query = "SELECT * FROM TW_DISEASE " + where + " "
                    + " ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getFrequencies(String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_FREQUENCY ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoseUsage(String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_DOSE_USAGE ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorCagetories(String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_DOCTOR_CATEGORY ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorTypes(String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_DOCTOR_TYPE ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorSpeciality(String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_MEDICAL_SPECIALITY ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getCityByCountryId(String countryId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM CITY WHERE COUNTRY_ID=" + countryId + " ORDER BY CITY_NME";

            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getStateByCountryId(String countryId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM STATE WHERE COUNTRY_ID=" + countryId + " ORDER BY STATE_NME";

            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getCountry(String comapnyId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM COUNTRY  ORDER BY COUNTRY_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isPatientAlreadyExists(String phoneNo, String companyId) {
        boolean flag = false;
        try {
            String query = "SELECT * FROM TW_PATIENT "
                    + " WHERE MOBILE_NO='" + phoneNo.trim() + "'";
            List<Map> list = this.getDao().getData(query);
            if (list != null && list.size() > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean isDoctorAlreadyExists(String phoneNo) {
        boolean flag = false;
        try {
            String query = "SELECT * FROM TW_DOCTOR "
                    + " WHERE MOBILE_NO='" + phoneNo.trim() + "'";
            List<Map> list = this.getDao().getData(query);
            if (list != null && list.size() > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /// Patient
    @Override
    public boolean savePatientReports(Patient p, String path) {
        boolean flag = false;
        String query = "";
        try {
            if (p.getPatientId() != null) {
                String pic = "";
                if (p.getReport() != null && !p.getReport().isEmpty()) {
                    String sep = File.separator;
                    String picPath = path + sep + p.getPatientId() + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    p.getReport().transferTo(new File(folder + File.separator + p.getReport().getOriginalFilename()));
                    pic = p.getReport().getOriginalFilename();
                }

                query = "INSERT INTO TW_PATIENT_ATTACHMENT(TW_PATIENT_ATTACHMENT_ID,TW_PATIENT_ID,FILE_NME,FILE_DESC,"
                        + " ATTACHMENT_TYP,PREPARED_BY,TW_DOCTOR_ID) "
                        + " VALUES(SEQ_TW_PATIENT_ATTACHMENT_ID.NEXTVAL," + p.getPatientId() + ", "
                        + "'" + pic + "',INITCAP('" + p.getReportDesc().trim() + "'),'" + p.getAttachmentType() + "','"
                        + p.getUserName() + "','" + p.getDoctorId() + "') ";

                int i = this.getDao().getJdbcTemplate().update(query);
                if (i > 0) {
                    flag = true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public String savePatient(Patient p) {
        List<String> arr = new ArrayList();
        String patientId = "";
        MD5 md = new MD5();
        String password = Util.generatePassword();
        String mdStr = md.calcMD5(password);
        Encryption pswdSec = new Encryption();
        String generatedPassword = pswdSec.encrypt(mdStr);
        try {
            String query = "";
            String masterId = "";
            if (p.getPatientId() != null && !p.getPatientId().isEmpty()) {
                query = "UPDATE TW_PATIENT SET PATIENT_NME=INITCAP('" + Util.removeSpecialChar(p.getPatientName()) + "'),"
                        + " MOBILE_NO='" + Util.removeSpecialChar(p.getContactNo().trim()) + "',"
                        + " AGE=" + (p.getAge().isEmpty() ? null : p.getAge()) + ","
                        + " GENDER='" + p.getGender() + "',"
                        + " EMAIL='" + p.getEmail() + "',"
                        + " ADDRESS=INITCAP('" + Util.removeSpecialChar(p.getPatientAddress().trim()) + "'),"
                        + " HEIGHT=" + (p.getPatientHeight().isEmpty() ? 0 : p.getPatientHeight()) + ","
                        + " WEIGHT=" + (p.getPatientWeight().isEmpty() ? 0 : p.getPatientWeight()) + ","
                        + " CITY_ID=" + (p.getCityId().isEmpty() ? null : p.getCityId()) + ","
                        + " DOB=TO_DATE('" + p.getDob() + "','DD-MM-YYYY'),"
                        + " TW_BLOOD_GROUP_ID=" + (p.getBloodGroupId().isEmpty() ? null : p.getBloodGroupId()) + ","
                        + " REFERRED_BY=INITCAP('" + Util.removeSpecialChar(p.getReferredBy()) + "'),"
                        + " PROFESSION=INITCAP('" + Util.removeSpecialChar(p.getProfession()) + "')"
                        + " WHERE TW_PATIENT_ID=" + p.getPatientId() + "";
                arr.add(query);
                patientId = p.getPatientId();
            } else {
                String prevId = "SELECT SEQ_TW_PATIENT_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                patientId = masterId;
                query = "INSERT INTO TW_PATIENT(TW_PATIENT_ID,PATIENT_NME,MOBILE_NO,AGE,"
                        + "GENDER,EMAIL,ADDRESS,HEIGHT,WEIGHT,PARENT_PATIENT_ID,RELATION_TYP,PREPARED_BY,CITY_ID,TW_BLOOD_GROUP_ID,"
                        + "DOB,REFERRED_BY,PROFESSION) "
                        + " VALUES (" + masterId + ",INITCAP('" + Util.removeSpecialChar(p.getPatientName().trim()) + "'),"
                        + "'" + Util.removeSpecialChar(p.getContactNo()) + "',"
                        + "" + (p.getAge().isEmpty() ? null : p.getAge()) + ",'" + p.getGender() + "',"
                        + "'" + p.getEmail() + "',"
                        + "INITCAP('" + Util.removeSpecialChar(p.getPatientAddress().trim()) + "'),"
                        + "" + (p.getPatientHeight().isEmpty() ? 0 : p.getPatientWeight()) + ","
                        + "" + (p.getPatientWeight().isEmpty() ? 0 : p.getPatientWeight()) + ","
                        + "" + (p.getParentId() != null ? p.getParentId() : null) + ",'"
                        + "" + p.getRelationIn() + "',"
                        + "'" + p.getUserName() + "'," + (p.getCityId().isEmpty() ? null : p.getCityId()) + ","
                        + (p.getBloodGroupId().isEmpty() ? null : p.getBloodGroupId()) + ","
                        + " TO_DATE('" + p.getDob() + "','DD-MM-YYYY'),'" + Util.removeSpecialChar(p.getReferredBy()) + "',"
                        + "  INITCAP('" + Util.removeSpecialChar(p.getProfession()) + "'))";

                arr.add(query);
                String userName = "";
                if (p.getParentId() != null) {
                    String nameLetter = (p.getPatientName().length() > 2 ? p.getPatientName().substring(0, 2) : p.getPatientName());
                    userName = nameLetter + p.getContactNo();
                } else {
                    userName = p.getContactNo();
                }

                arr.add("INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,FIRST_NME,EMAIL,TW_PATIENT_ID) VALUES ("
                        + " '" + Util.removeSpecialChar(userName) + "','" + generatedPassword + "',INITCAP('" + Util.removeSpecialChar(p.getPatientName()) + "'),"
                        + " '" + p.getEmail() + "'," + masterId + ")");
            }
            boolean flag = this.dao.insertAll(arr, p.getUserName());
            if (p.getPatientId() == null || p.getPatientId().isEmpty()) {
                if (flag) {
                    Util.sendSignUpMessage(p.getContactNo(), p.getContactNo(), password);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            patientId = "";
        }
        return patientId;
    }

    @Override
    public boolean saveDoctor(DoctorVO vo) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        MD5 md = new MD5();
        String password = Util.generatePassword();
        String mdStr = md.calcMD5(password);
        Encryption pswdSec = new Encryption();
        String generatedPassword = pswdSec.encrypt(mdStr);
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
                        + " EMAIL='" + (vo.getEmail() == null ? null : Util.removeSpecialChar(vo.getEmail().trim())) + "',"
                        + " COUNTRY_ID=" + (vo.getCountryId().isEmpty() ? null : vo.getCountryId()) + ","
                        + " ALLOW_VIDEO='" + vo.getServicesAvail() + "',"
                        + " LINKEDIN_URL='" + vo.getLink() + "',"
                        + " PRESCRIPTION_LANG='" + vo.getPrescriptionLang() + "',"
                        + " VIDEO_CLINIC_FROM=TO_DATE('" + vo.getVideoTimeFrom() + "','HH24:MI'),"
                        + " VIDEO_CLINIC_TO=TO_DATE('" + vo.getVideoTimeTo() + "','HH24:MI')"
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
                query = "INSERT INTO TW_DOCTOR(TW_DOCTOR_ID,DOCTOR_NME ,MOBILE_NO,"
                        + "DOCTOR_CATEGORY_ID,COMPANY_ID,PREPARED_BY,"
                        + "CITY_ID,COUNTRY_ID,ALLOW_VIDEO,EXPERIENCE,VIDEO_CLINIC_FROM,VIDEO_CLINIC_TO)"
                        + " VALUES (" + masterId + ",INITCAP('" + Util.removeSpecialChar(vo.getDoctorName()) + "'),"
                        + "'" + Util.removeSpecialChar(vo.getCellNo().trim()) + "'," + vo.getDoctorType() + ","
                        + "" + vo.getCompanyId() + ",'" + vo.getUserName() + "',"
                        + "" + (vo.getCityId().isEmpty() ? null : vo.getCityId()) + ","
                        + "" + (vo.getCountryId().isEmpty() ? null : vo.getCountryId()) + ","
                        + "'" + vo.getServicesAvail() + "',"
                        + " " + (vo.getTotalExperience().isEmpty() ? 1 : vo.getTotalExperience())
                        + ",TO_DATE('" + vo.getVideoTimeFrom() + "','HH24:MI'),TO_DATE('" + vo.getVideoTimeTo() + "','HH24:MI'))";
                arr.add(query);
                arr.add("INSERT INTO TW_PROCEDURE_FEE VALUES (SEQ_TW_PROCEDURE_FEE_ID.NEXTVAL," + masterId + ",2,"
                        + (vo.getConsultancyFee().isEmpty() ? 0 : vo.getConsultancyFee()) + "," + vo.getDiscount() + ",'" + vo.getUserName() + "',SYSDATE,"
                        + vo.getCompanyId() + ")");
                if (!vo.getNewUserName().isEmpty() && vo.getNewUserName() != null) {
                    arr.add("INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,FIRST_NME,TW_DOCTOR_ID) VALUES ("
                            + " '" + Util.removeSpecialChar(vo.getNewUserName()).trim().toLowerCase() + "','" + generatedPassword + "',INITCAP('" + Util.removeSpecialChar(vo.getDoctorName()) + "'),"
                            + "" + masterId + ")");
                    arr.add("INSERT INTO TW_USER_RIGHT(TW_USER_RIGHT_ID,USER_NME,RIGHT_NME,CAN_ADD,CAN_EDIT,CAN_DELETE)"
                            + "SELECT SEQ_TW_USER_RIGHT_ID.NEXTVAL,'" + Util.removeSpecialChar(vo.getNewUserName()).trim().toLowerCase() + "',RIGHT_NME,'Y','Y','Y' FROM TW_ROLE_RIGHTS  WHERE TW_ROLE_ID=2");
                }
            }
            flag = this.dao.insertAll(arr, vo.getUserName());
            if (flag) {
                Util.sendSignUpMessage(vo.getCellNo(), Util.removeSpecialChar(vo.getNewUserName()).trim().toLowerCase(), password);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveCompanyLogo(Pharma d, String path) {
        boolean flag = false;
        String query = "";
        try {
            if (d.getPharmaId() != null && !d.getPharmaId().isEmpty()) {
                if (d.getLogoFile() != null && !d.getLogoFile().isEmpty()) {
                    String sep = File.separator;
                    String picPath = path + sep + d.getPharmaId() + sep;
                    File folder = new File(picPath);
                    if (!folder.exists()) {
                        boolean succ = (new File(picPath)).mkdir();
                    }
                    String fileFileName = new java.util.Date().getTime() + "_" + Util.renameFileName(d.getLogoFile().getOriginalFilename());
                    d.getLogoFile().transferTo(new File(folder + File.separator + fileFileName));
                    query = "UPDATE TW_PHARMACEUTICAL SET LOGO_IMG='" + fileFileName + "' "
                            + " WHERE TW_PHARMACEUTICAL_ID=" + d.getPharmaId() + "";

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
    public boolean savePharma(Pharma p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";

            if (p.getPharmaId() != null && !p.getPharmaId().isEmpty()) {
                query = "UPDATE TW_PHARMACEUTICAL SET COMPANY_NME=INITCAP('" + Util.removeSpecialChar(p.getCompanyName()) + "'),"
                        + " CONTACT_PERSON=INITCAP('" + Util.removeSpecialChar(p.getContactPerson().trim()) + "'),"
                        + " MOBILE_NO=" + (p.getCellNo().isEmpty() ? 0 : p.getCellNo()) + ","
                        + " DESIGNATION=INITCAP('" + Util.removeSpecialChar(p.getDesignation()).trim() + "'),"
                        + " EMAIL='" + Util.removeSpecialChar(p.getEmail()).trim() + "',"
                        + " LANDLINE_NO=" + (p.getPtclNo().isEmpty() ? 0 : p.getPtclNo()) + ","
                        + " WEB_URL='" + Util.removeSpecialChar(p.getWebUrl().trim()) + "',"
                        + " EXP_DATE=TO_DATE('" + p.getExpiryDate() + "','DD-MM-YYYY'),"
                        + " ADDRESS=INITCAP('" + Util.removeSpecialChar(p.getCompanyAddress()) + "')"
                        + " WHERE TW_PHARMACEUTICAL_ID=" + p.getPharmaId() + "";
                arr.add(query);
            } else {
                String prevId = "SELECT SEQ_TW_PHARMACEUTICAL_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_PHARMACEUTICAL(TW_PHARMACEUTICAL_ID,COMPANY_NME ,CONTACT_PERSON,MOBILE_NO,DESIGNATION,"
                        + "EMAIL,LANDLINE_NO,ADDRESS,COMPANY_ID,PREPARED_BY,WEB_URL,EXP_DATE)"
                        + " VALUES (" + masterId + ",INITCAP('" + Util.removeSpecialChar(p.getCompanyName()) + "'),"
                        + "INITCAP('" + Util.removeSpecialChar(p.getContactPerson()) + "'),"
                        + "'" + Util.removeSpecialChar(p.getCellNo().trim()) + "',"
                        + "INITCAP('" + Util.removeSpecialChar(p.getDesignation()) + "'),"
                        + "'" + Util.removeSpecialChar(p.getEmail()) + "',"
                        + "'" + Util.removeSpecialChar(p.getPtclNo()).trim() + "',"
                        + "INITCAP('" + Util.removeSpecialChar(p.getCompanyAddress()) + "'),"
                        + "" + p.getCompanyId() + ",'" + p.getUserName() + "','" + Util.removeSpecialChar(p.getWebUrl().trim())
                        + "',TO_DATE('" + p.getExpiryDate() + "','DD-MM-YYYY') )";
                arr.add(query);
//                arr.add("INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,FIRST_NME,EMAIL,COMPANY_ID,TW_PHARMACEUTICAL_ID) VALUES ("
//                        + " '" + Util.removeSpecialChar(p.getNewUserName()).trim() + "','" + p.getUserPassword() + "','" + Util.removeSpecialChar(p.getContactPerson()) + "',"
//                        + " '" + p.getEmail() + "'," + p.getCompanyId() + "," + masterId + ")");
            }
            flag = this.dao.insertAll(arr, p.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPatient(String patientName, String mobileNbr, String startRowNo, String endRowNo, String searchCharacter) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT TW_PATIENT_ID,PATIENT_NME,MOBILE_NO,AGE,TO_CHAR(DOB,'DD-MON-YYYY') DOB,ATTEND_CLINIC,"
                    + "ANY_ALLERGY,GENDER,TAKE_MEDICINE,ADDRESS,HEIGHT,ANY_FEVER,SMOKER_IND,TAKE_STEROID,"
                    + "WEIGHT,CITY_ID,PARENT_PATIENT_ID,ROW_NUMBER() OVER (ORDER BY PATIENT_NME) ROW_NUM,COUNT(*) OVER () TOTAL_ROWS"
                    + " FROM TW_PATIENT ";
            if (patientName != null && !patientName.trim().isEmpty()) {
                where += " WHERE UPPER(PATIENT_NME) LIKE '%" + patientName.toUpperCase() + "%' ";
            }
            if (mobileNbr != null && !mobileNbr.trim().isEmpty()) {
                if (where.contains("WHERE")) {
                    where += " AND MOBILE_NO LIKE '%" + mobileNbr.trim() + "%'";
                } else {
                    where += " WHERE  MOBILE_NO LIKE '%" + mobileNbr.trim() + "%'";
                }
            }
            if (!searchCharacter.trim().equalsIgnoreCase("All")) {
                if (where.contains("WHERE")) {
                    where += " AND UPPER(PATIENT_NME) LIKE '" + searchCharacter.trim() + "%'";
                } else {
                    where += " WHERE UPPER(PATIENT_NME) LIKE '" + searchCharacter.trim() + "%'";
                }
            }
            query = query + where + " ORDER BY PATIENT_NME";
            String getPageRows = "";
            if (startRowNo != null && !startRowNo.isEmpty() && endRowNo != null && !endRowNo.isEmpty()) {
                getPageRows = " WHERE ROW_NUM BETWEEN " + startRowNo + " AND " + endRowNo + " ";
            }
            if (startRowNo != null && !startRowNo.isEmpty()) {
                query = "SELECT * FROM ( " + query + " ) " + getPageRows + "";

            }
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctors(String doctorName, String mobileNbr, String doctorType) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT DR.TW_DOCTOR_ID,DR.DOCTOR_NME,DR.MOBILE_NO,DR.CNIC,DR.GENDER,DR.EMAIL,TO_CHAR(DR.DOB,'DD-MON-YYYY') DOB,"
                    + "  DR.ADDRESS,DR.DOCTOR_CATEGORY_ID,DR.FEATURED_IND,DC.TW_DOCTOR_CATEGORY_ID,DC.TITLE,MD.ABBREV  AS DEGREETITLE,"
                    + "  NVL(DAT.COUNT,0) TOTAL_ATTACHMENTS,TO_CHAR(DR.EXPIRY_DTE,'DD-MON-YYYY') EXPIRY_DTE"
                    + "  FROM TW_DOCTOR DR,TW_DOCTOR_CATEGORY DC,TW_MEDICAL_DEGREE MD,("
                    + "  SELECT TW_DOCTOR_ID,COUNT(FILE_NME) COUNT FROM TW_DOCTOR_ATTACHMENT"
                    + "  WHERE ATTACHMENT_TYP='WEB ATTACHMENT' "
                    + "  GROUP BY TW_DOCTOR_ID) DAT"
                    + "  WHERE DR.DOCTOR_CATEGORY_ID=DC.TW_DOCTOR_CATEGORY_ID"
                    + "  AND DR.TW_MEDICAL_DEGREE_ID=MD.TW_MEDICAL_DEGREE_ID(+)"
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
    public List<Map> getDoctorsByService(String serviceId, String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT TD.TW_DOCTOR_ID,TD.DOCTOR_NME "
                    + " FROM TW_DOCTOR_SERVICE TDS,TW_DOCTOR TD"
                    + " WHERE TDS.TW_DOCTOR_TYPE_ID=" + serviceId + ""
                    + " AND TDS.TW_DOCTOR_ID=TD.TW_DOCTOR_ID"
                    + " ORDER BY TD.DOCTOR_NME";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getPharmaCompanies() {
        List<Map> list = null;
        try {
            String query = "SELECT TW_PHARMACEUTICAL_ID,COMPANY_NME,CONTACT_PERSON,MOBILE_NO,DESIGNATION,EMAIL,"
                    + " LANDLINE_NO,ADDRESS"
                    + " FROM TW_PHARMACEUTICAL "
                    + " ORDER BY COMPANY_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getPharmaCompanies(String pharmaId) {
        List<Map> list = null;
        try {
            String query = "SELECT TW_PHARMACEUTICAL_ID,COMPANY_NME,CONTACT_PERSON,MOBILE_NO,DESIGNATION,EMAIL,"
                    + " LANDLINE_NO,ADDRESS"
                    + " FROM TW_PHARMACEUTICAL WHERE TW_PHARMACEUTICAL_ID=" + pharmaId + " "
                    + " ORDER BY COMPANY_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveProduct(Product p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";

            if (p.getProductId() != null && !p.getProductId().isEmpty()) {
                masterId = p.getProductId();
                query = "UPDATE TW_PHARMA_PRODUCT SET PRODUCT_NME=INITCAP('" + Util.removeSpecialChar(p.getProductName()) + "'),"
                        + " GENERIC_NME=INITCAP('" + Util.removeSpecialChar(p.getProductGenericName()) + "'),"
                        + " PRODUCT_TYPE=INITCAP('" + Util.removeSpecialChar(p.getProductType()) + "'),"
                        + " REMARKS=INITCAP('" + Util.removeSpecialChar(p.getProductFeatures()).trim() + "')"
                        + " WHERE TW_PHARMA_PRODUCT_ID=" + p.getProductId() + "";
                arr.add(query);
                arr.add("DELETE FROM TW_PHARMA_PROD_DISEASE WHERE TW_PHARMA_PRODUCT_ID=" + p.getProductId() + "");
            } else {
                String prevId = "SELECT SEQ_TW_PHARMA_PRODUCT_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_PHARMA_PRODUCT(TW_PHARMA_PRODUCT_ID,TW_PHARMACEUTICAL_ID,PRODUCT_NME,GENERIC_NME,PRODUCT_TYPE,"
                        + "REMARKS,PREPARED_BY,PREPARED_DTE)"
                        + " VALUES (" + masterId + "," + p.getPharmaCompanyId() + ","
                        + "INITCAP('" + Util.removeSpecialChar(p.getProductName()) + "'),"
                        + "INITCAP('" + Util.removeSpecialChar(p.getProductGenericName()) + "'),"
                        + "INITCAP('" + Util.removeSpecialChar(p.getProductType()) + "'),"
                        + "INITCAP('" + Util.removeSpecialChar(p.getProductFeatures()) + "'),"
                        + "'" + p.getUserName() + "',SYSDATE)";
                arr.add(query);
            }
            if (p.getMultiSelectDiseases() != null && p.getMultiSelectDiseases().length > 0) {
                for (int i = 0; i < p.getMultiSelectDiseases().length; i++) {
                    arr.add("INSERT INTO TW_PHARMA_PROD_DISEASE(TW_PHARMA_PROD_DISEASE_ID,TW_PHARMA_PRODUCT_ID,TW_DISEASE_ID,PREPARED_DTE) VALUES ("
                            + " SEQ_TW_PHARMA_PROD_DISEASE_ID.NEXTVAL," + masterId + "," + p.getMultiSelectDiseases()[i] + "," + "SYSDATE" + ")");
                }
            }
            flag = this.dao.insertAll(arr, p.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPharma(String pharmaName) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT TW_PHARMACEUTICAL_ID,COMPANY_NME,CONTACT_PERSON,"
                    + "MOBILE_NO,DESIGNATION,EMAIL,LANDLINE_NO,ADDRESS,COMPANY_ID,PREPARED_BY FROM TW_PHARMACEUTICAL";
            if (pharmaName != null && !pharmaName.trim().isEmpty()) {
                where += " WHERE UPPER(COMPANY_NME) LIKE '%" + pharmaName.toUpperCase() + "%' ";
            }
            list = this.getDao().getData(query + where + " ORDER BY COMPANY_NME");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getPharmaProducts(String pharmaId) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT  MD.TW_PHARMA_PRODUCT_ID,MD.TW_PHARMACEUTICAL_ID,PH.COMPANY_NME,MD.PRODUCT_NME,MD.PRODUCT_TYPE,"
                    + " MD.GENERIC_NME,MD.REMARKS "
                    + " FROM TW_PHARMACEUTICAL PH,TW_PHARMA_PRODUCT MD "
                    + " WHERE PH.TW_PHARMACEUTICAL_ID=MD.TW_PHARMACEUTICAL_ID ";

            if (pharmaId != null && !pharmaId.trim().isEmpty()) {
                where += " AND MD.TW_PHARMACEUTICAL_ID =" + pharmaId + " ";
            }
            list = this.getDao().getData(query + where + " ORDER BY MD.PRODUCT_NME ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveClinic(DoctorVO c) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";

            if (c.getClinicId() != null && !c.getClinicId().isEmpty()) {
                query = "UPDATE TW_CLINIC SET CLINIC_NME=INITCAP('" + Util.removeSpecialChar(c.getClinicName().trim()) + "'),"
                        + " PHONE_NO='" + c.getPhoneNo() + "',"
                        + " MAP_COORDINATES='" + Util.removeSpecialChar(c.getMapQuardinates().trim()) + "',"
                        + " ADDRESS='" + Util.removeSpecialChar(c.getClinicAddress().trim()) + "',"
                        + " COUNTRY_ID=" + c.getCountryId() + ","
                        + " CITY_ID=" + c.getCityId() + ","
                        + " CITY_AREA_ID=" + c.getAreaId() + ""
                        + " WHERE TW_CLINIC_ID=" + c.getClinicId() + "";
                arr.add(query);
            } else {
                query = "INSERT INTO TW_CLINIC(TW_CLINIC_ID,CLINIC_NME,PHONE_NO,MAP_COORDINATES,ADDRESS,"
                        + "COMPANY_ID,PREPARED_BY,COUNTRY_ID,CITY_ID,CITY_AREA_ID)"
                        + " VALUES (SEQ_TW_CLINIC_ID.NEXTVAL,INITCAP('" + Util.removeSpecialChar(c.getClinicName().trim()) + "'),"
                        + "'" + c.getPhoneNo() + "',"
                        + "'" + Util.removeSpecialChar(c.getMapQuardinates().trim().trim()) + "',"
                        + "INITCAP('" + Util.removeSpecialChar(c.getClinicAddress().trim()) + "'),"
                        + "" + c.getCompanyId() + ",'" + c.getUserName() + "'," + c.getCountryId()
                        + "," + c.getCityId() + "," + c.getAreaId() + " )";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, c.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getClinicById(String clinicId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_CLINIC WHERE TW_CLINIC_ID=" + clinicId + "";

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
    public List<Map> getClinic(String clinicName) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT TW_CLINIC_ID,CLINIC_NME,PHONE_NO,"
                    + "MAP_COORDINATES,ADDRESS FROM TW_CLINIC";

            if (clinicName != null && !clinicName.trim().isEmpty()) {
                where += " WHERE UPPER(CLINIC_NME) LIKE '%" + clinicName.toUpperCase() + "%' ";
            }

            list = this.getDao().getData(query + where + " ORDER BY CLINIC_NME");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getClinicForStaff(String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT TW_CLINIC_ID,CLINIC_NME,PHONE_NO,"
                    + " MAP_COORDINATES,ADDRESS FROM TW_CLINIC"
                    + " WHERE TW_CLINIC_ID=" + clinicId + "";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveDoctorClinic(DoctorClinic dc) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            if (dc.getDoctorClinicId() != null && !dc.getDoctorClinicId().isEmpty()) {
                query = "UPDATE TW_DOCTOR_CLINIC SET TIME_FROM=TO_DATE('" + dc.getTimeFrom() + "','HH24:MI'),"
                        + " TIME_TO=TO_DATE('" + dc.getTimeTo() + "','HH24:MI'),"
                        + " REMARKS='" + (dc.getRemarks() != null ? Util.removeSpecialChar(dc.getRemarks().trim()) : "") + "',"
                        + " TOTAL_APPOINTMENT=" + ((dc.getMaxAppointment() != null && !dc.getMaxAppointment().isEmpty()) ? dc.getMaxAppointment() : "0") + ""
                        + " WHERE TW_DOCTOR_CLINIC_ID=" + dc.getDoctorClinicId() + "";

                arr.add(query);
                query = "DELETE FROM TW_DOCTOR_DAYS WHERE TW_DOCTOR_ID=" + dc.getDoctorId() + " AND TW_CLINIC_ID=" + dc.getClinicId() + "";
                arr.add(query);
                if (dc.getWeekdays() != null) {
                    for (int i = 0; i < dc.getWeekdays().length; i++) {
                        query = "INSERT INTO TW_DOCTOR_DAYS (TW_DOCTOR_DAYS_ID,TW_DOCTOR_ID,TW_CLINIC_ID,WEEK_DAY) VALUES "
                                + " (SEQ_TW_DOCTOR_DAYS_ID.NEXTVAL," + dc.getDoctorId() + "," + dc.getClinicId() + ",'" + dc.getWeekdays()[i] + "')";
                        arr.add(query);
                    }
                }
            } else {
                query = "INSERT INTO TW_DOCTOR_CLINIC(TW_DOCTOR_CLINIC_ID,TW_DOCTOR_ID,TW_CLINIC_ID,TIME_FROM ,TIME_TO,"
                        + "REMARKS,PREPARED_BY,TOTAL_APPOINTMENT)"
                        + " VALUES (SEQ_TW_DOCTOR_CLINIC_ID.NEXTVAL," + dc.getDoctorId() + ","
                        + "" + dc.getClinicId() + ","
                        + " TO_DATE('" + dc.getTimeFrom() + "','HH24:MI'),"
                        + "TO_DATE('" + dc.getTimeTo() + "','HH24:MI'),"
                        + "'" + Util.removeSpecialChar(dc.getRemarks().trim()) + "',"
                        + "'" + dc.getUserName() + "'," + ((dc.getMaxAppointment() != null && !dc.getMaxAppointment().isEmpty()) ? dc.getMaxAppointment() : "0") + ")";
                arr.add(query);
                if (dc.getWeekdays() != null) {
                    for (int i = 0; i < dc.getWeekdays().length; i++) {
                        query = "INSERT INTO TW_DOCTOR_DAYS (TW_DOCTOR_DAYS_ID,TW_DOCTOR_ID,TW_CLINIC_ID,WEEK_DAY) VALUES "
                                + " (SEQ_TW_DOCTOR_DAYS_ID.NEXTVAL," + dc.getDoctorId() + "," + dc.getClinicId() + ",'" + dc.getWeekdays()[i] + "')";
                        arr.add(query);
                    }
                }
            }
            flag = this.dao.insertAll(arr, dc.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getDoctors(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_DOCTOR ORDER BY DOCTOR_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getClinics(String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_CLINIC ORDER BY CLINIC_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getBloodGroup() {
        List<Map> list = null;
        try {
            String query = "SELECT TBG.* FROM TW_BLOOD_GROUP TBG ORDER BY TBG.TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getMedicalServices(String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT TW_MEDICAL_SERVICE_ID,TITLE "
                    + " FROM TW_MEDICAL_SERVICE ORDER BY TITLE ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getClinicForDoctors(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT TDC.TW_DOCTOR_CLINIC_ID,TDC.TW_DOCTOR_ID,TDC.TW_CLINIC_ID,TO_CHAR(TDC.TIME_FROM,'HH24:MI') TIME_FROM,"
                    + " TO_CHAR(TDC.TIME_TO,'HH24:MI') TIME_TO,TDC.REMARKS,TDC.TOTAL_APPOINTMENT,TC.CLINIC_NME "
                    + " FROM TW_DOCTOR_CLINIC TDC,TW_CLINIC TC"
                    + " WHERE TDC.TW_CLINIC_ID=TC.TW_CLINIC_ID"
                    + " AND TDC.TW_DOCTOR_ID=" + doctorId + " "
                    + " ORDER BY TC.CLINIC_NME ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getTimeForClinic(String doctorId, String clinicId) {
        Map map = null;
        try {
            String query = "SELECT TO_CHAR(TDC.TIME_FROM,'HH24:MI:SS') TIME_FROM,TO_CHAR(TDC.TIME_TO,'HH24:MI:SS') TIME_TO"
                    + " FROM TW_DOCTOR_CLINIC TDC,TW_CLINIC TC"
                    + " WHERE TC.TW_CLINIC_ID=" + clinicId + " "
                    + " AND TDC.TW_DOCTOR_ID=" + doctorId + " "
                    + " ORDER BY TC.CLINIC_NME ";
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
    public List<Map> getAvailableClinicForDoctors(String doctorId, String doctorClinicId) {
        List<Map> list = null;
        try {
            String where = "";
            if (doctorClinicId != null && !doctorClinicId.isEmpty()) {
                where += " AND TW_DOCTOR_CLINIC_ID <> " + doctorClinicId + "";
            }
            String query = "SELECT TC.TW_CLINIC_ID,TC.CLINIC_NME "
                    + " FROM TW_CLINIC TC"
                    + " WHERE TC.TW_CLINIC_ID NOT IN ("
                    + " SELECT TW_CLINIC_ID FROM TW_DOCTOR_CLINIC "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + " " + where + ") "
                    + " ORDER BY TC.CLINIC_NME ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteDoctorClinic(String doctorClinicId, String clinicId, String doctorId) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        String query = "";
        try {
            query = "DELETE FROM TW_DOCTOR_CLINIC WHERE TW_DOCTOR_CLINIC_ID=" + doctorClinicId + "";
            arr.add(query);
            if (clinicId != null && doctorId != null) {
                query = "DELETE FROM TW_DOCTOR_DAYS WHERE TW_CLINIC_ID=" + clinicId + " AND TW_DOCTOR_ID=" + doctorId;
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteDoctor(String doctorId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_DOCTOR WHERE TW_DOCTOR_ID=" + doctorId + "";
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
    public boolean updateDoctorExpiry(String doctorId, String expiryDate) {
        boolean flag = false;
        try {
            String query = "UPDATE TW_DOCTOR SET EXPIRY_DTE=TO_DATE('" + expiryDate + "','DD-MM-YYYY')  "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + "";
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
    public boolean deletePatient(String patientId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_PATIENT WHERE TW_PATIENT_ID=" + patientId + "";
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
    public boolean deletePharma(String pharmaId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_PHARMACEUTICAL WHERE TW_PHARMACEUTICAL_ID=" + pharmaId + "";
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
    public boolean deleteClinic(String clinicId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_CLINIC WHERE TW_CLINIC_ID=" + clinicId + "";
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
    public boolean saveVideoLink(String doctorId, String videoLink) {
        boolean flag = false;
        try {
            String query = "UPDATE TW_DOCTOR SET VIDEO_LINK='" + videoLink + "' WHERE TW_DOCTOR_ID=" + doctorId + "";
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
    public Map getDoctorById(String doctorId) {
        Map map = null;
        try {
            String query = "SELECT D.TW_DOCTOR_ID,D.DOCTOR_NME,D.MOBILE_NO,D.CNIC,D.GENDER,D.EMAIL,D.DOB"
                    + ",D.ADDRESS,D.DOCTOR_CATEGORY_ID,D.COMPANY_ID,D.TW_MEDICAL_DEGREE_ID,D.VIDEO_LINK,"
                    + "D.EXPERIENCE,D.PRESCRIPTION_LANG,D.PROFILE_IMAGE,D.TW_DOCTOR_TYPE_ID,D.CITY_ID,D.COUNTRY_ID,D.ALLOW_VIDEO,"
                    + "D.LINKEDIN_URL,TO_CHAR(D.VIDEO_CLINIC_FROM,'HH24:MI') VIDEO_CLINIC_FROM,"
                    + "TO_CHAR(D.VIDEO_CLINIC_TO,'HH24:MI') VIDEO_CLINIC_TO,PE.FEE,PE.TW_PROCEDURE_FEE_ID "
                    + "FROM TW_DOCTOR D,TW_PROCEDURE_FEE PE WHERE D.TW_DOCTOR_ID=PE.TW_DOCTOR_ID(+) "
                    + "AND D.TW_DOCTOR_ID=" + doctorId + "";
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
    public Map getPatientById(String patientId) {
        Map map = null;
        try {
            String query = "SELECT  TP.TW_PATIENT_ID,TP.PATIENT_NME,TP.TW_BLOOD_GROUP_ID,TP.MOBILE_NO,TP.AGE,TP.EMAIL,TO_CHAR(DOB,'DD-MM-YYYY') DOB,"
                    + " TP.ATTEND_CLINIC,TP.ANY_ALLERGY,TP.GENDER,TP.TAKE_MEDICINE,TP.ADDRESS,TP.CITY_ID,TP.HEIGHT,TP.ANY_FEVER,TP.SMOKER_IND,TP.TAKE_STEROID,"
                    + " TP.WEIGHT,TP.REFERRED_BY,TP.PROFESSION,C.CITY_NME"
                    + " FROM TW_PATIENT TP,CITY C"
                    + " WHERE TP.CITY_ID=C.CITY_ID(+) AND TP.TW_PATIENT_ID=" + patientId + "";
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
    public List<Map> getPatientDiseasesById(String patientId) {
        List<Map> list = null;
        try {
            String query = "SELECT D.TITLE,D.TW_DISEASE_ID FROM TW_DISEASE D ,TW_PATIENT_DISEASE PD WHERE "
                    + "PD.TW_DISEASE_ID=D.TW_DISEASE_ID AND PD.TW_PATIENT_ID=" + patientId + ""
                    + " ORDER  BY D.TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorSpecialityDiseasesById(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT D.TITLE,D.TW_DISEASE_ID FROM TW_DISEASE D ,TW_DOCTOR_DISESE DD WHERE "
                    + "DD.TW_DISEASE_ID=D.TW_DISEASE_ID AND DD.TW_DOCTOR_ID=" + doctorId + ""
                    + " ORDER BY D.TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorSpecialityById(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT MS.TITLE,MS.TW_MEDICAL_SPECIALITY_ID FROM TW_MEDICAL_SPECIALITY MS ,TW_DOCTOR_SPECIALITY DS WHERE "
                    + "DS.TW_MEDICAL_SPECIALITY_ID=MS.TW_MEDICAL_SPECIALITY_ID AND DS.TW_DOCTOR_ID=" + doctorId + ""
                    + " ORDER BY MS.TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorServiceById(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT MS.TITLE,MS.TW_MEDICAL_SERVICE_ID FROM TW_MEDICAL_SERVICE MS ,TW_DOCTOR_SERVICE DS WHERE "
                    + "DS.TW_MEDICAL_SERVICE_ID=MS.TW_MEDICAL_SERVICE_ID AND DS.TW_DOCTOR_ID=" + doctorId + ""
                    + " ORDER BY MS.TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorsForClinic(String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT TDC.TW_DOCTOR_CLINIC_ID,TDC.TW_DOCTOR_ID,TDC.TW_CLINIC_ID,TO_CHAR(TDC.TIME_FROM,'HH24:MI') TIME_FROM,"
                    + " TO_CHAR(TDC.TIME_TO,'HH24:MI') TIME_TO,TDC.REMARKS,TD.DOCTOR_NME "
                    + " FROM TW_DOCTOR_CLINIC TDC,TW_DOCTOR TD"
                    + " WHERE TDC.TW_DOCTOR_ID=TD.TW_DOCTOR_ID"
                    + " AND TDC.TW_CLINIC_ID=" + clinicId + " "
                    + " ORDER BY TDC.TW_DOCTOR_CLINIC_ID DESC ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getPharmaById(String pharmaId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_PHARMACEUTICAL WHERE TW_PHARMACEUTICAL_ID=" + pharmaId + "";
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
    public Map getDoctorClinicById(String doctorClinicId) {
        Map map = null;
        try {
            String query = "SELECT TC.TW_CLINIC_ID,TO_CHAR(TDC.TIME_FROM,'HH24:MI') TIME_FROM,"
                    + " TO_CHAR(TDC.TIME_TO,'HH24:MI') TIME_TO,TDC.REMARKS,TDC.TOTAL_APPOINTMENT,LISTAGG(DIS.WEEK_DAY, ',') WITHIN GROUP (ORDER BY DIS.TW_DOCTOR_DAYS_ID) WEEK_DAY"
                    + " FROM TW_DOCTOR_CLINIC TDC,TW_CLINIC TC,TW_DOCTOR_DAYS DIS"
                    + " WHERE TDC.TW_CLINIC_ID=TC.TW_CLINIC_ID"
                    + " AND TDC.TW_DOCTOR_ID=DIS.TW_DOCTOR_ID(+)"
                    + " AND TDC.TW_CLINIC_ID=DIS.TW_CLINIC_ID(+) AND TDC.TW_DOCTOR_CLINIC_ID=" + doctorClinicId + " "
                    + " GROUP BY (TC.TW_CLINIC_ID,TIME_FROM,TIME_TO,TDC.REMARKS,TDC.TOTAL_APPOINTMENT) ORDER BY TDC.TW_DOCTOR_CLINIC_ID DESC ";
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
    public Map getPharmaProductById(String productId) {
        Map map = null;
        try {
            String query = "SELECT MD.TW_PHARMA_PRODUCT_ID,MD.PRODUCT_NME,MD.PRODUCT_TYPE,"
                    + " MD.GENERIC_NME,MD.REMARKS ,DIS.DISEASES"
                    + " FROM TW_PHARMA_PRODUCT MD,"
                    + " (SELECT TW_PHARMA_PRODUCT_ID,LISTAGG(TW_DISEASE_ID, ',') WITHIN GROUP (ORDER BY TW_DISEASE_ID) DISEASES"
                    + " FROM TW_PHARMA_PROD_DISEASE"
                    + " GROUP BY TW_PHARMA_PRODUCT_ID) DIS"
                    + " WHERE MD.TW_PHARMA_PRODUCT_ID=" + productId + ""
                    + " AND MD.TW_PHARMA_PRODUCT_ID=DIS.TW_PHARMA_PRODUCT_ID(+)";
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
    public List<Map> getMedicines() {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_MEDICINE ORDER BY PRODUCT_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveDoctorMedicine(String doctorId, String medicineId, String userName) {
        boolean flag = false;
        try {
            String query = "INSERT INTO TW_DOCTOR_MEDICINE(TW_DOCTOR_MEDICINE_ID,TW_DOCTOR_ID,TW_MEDICINE_ID,PREPARED_BY)"
                    + " VALUES (SEQ_TW_DOCTOR_MEDICINE_ID.NEXTVAL," + doctorId + ","
                    + " " + medicineId + ",'" + userName + "')";
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
    public List<Map> getDoctorsMedicine(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT DM.TW_DOCTOR_MEDICINE_ID,TM.TW_MEDICINE_ID,TM.PRODUCT_NME,TM.GENERIC_NME,PH.COMPANY_NME "
                    + " FROM TW_DOCTOR_MEDICINE DM,TW_MEDICINE TM,TW_PHARMACEUTICAL PH"
                    + " WHERE DM.TW_MEDICINE_ID=TM.TW_MEDICINE_ID"
                    + " AND TM.TW_PHARMACEUTICAL_ID=PH.TW_PHARMACEUTICAL_ID"
                    + " AND DM.TW_DOCTOR_ID=" + doctorId + ""
                    + " ORDER BY TM.PRODUCT_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteDoctorMedicine(String id) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_DOCTOR_MEDICINE WHERE TW_DOCTOR_MEDICINE_ID=" + id + "";
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
    public boolean saveInTakeForm(Patient p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";

            query = "UPDATE TW_PATIENT SET ATTEND_CLINIC='" + p.getAttendClinic() + "',"
                    + " TAKE_MEDICINE='" + p.getMedicineOpt() + "',"
                    + " TAKE_STEROID='" + p.getSteroidOpt() + "',"
                    + " ANY_ALLERGY='" + p.getAllergy() + "',"
                    + " ANY_FEVER='" + p.getRheumatic() + "',"
                    + " SMOKER_IND='" + p.getSmoker() + "'"
                    + " WHERE TW_PATIENT_ID=" + p.getPatientId() + "";
            arr.add(query);

            flag = this.dao.insertAll(arr, p.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPatients(String patientsId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_PATIENT ORDER BY PATIENT_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getPatientHealthCards(String patientId) {
        List<Map> list = null;
        try {
            String query = "SELECT PW.PATIENT_NME,PW.TW_PATIENT_ID,CA.TW_HEALTH_CARD_ID,CA.CARD_NME,"
                    + " CA.PRODUCT_DISC,CA.DOCTOR_DISC,PH.ACTIVE_IND,PH.TW_PATIENT_HEALTH_CARD_ID,"
                    + " PH.CARD_NO,TO_CHAR(PH.EXPIRY_DTE,'DD-MON-YYYY') EXPIRY_DTE"
                    + " FROM TW_PATIENT PW,TW_HEALTH_CARD CA,TW_PATIENT_HEALTH_CARD PH"
                    + " WHERE PH.TW_PATIENT_ID=" + patientId + ""
                    + " AND PH.TW_HEALTH_CARD_ID=CA.TW_HEALTH_CARD_ID"
                    + " AND PH.TW_PATIENT_ID=PW.TW_PATIENT_ID"
                    + " ORDER BY CA.CARD_NME";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean savePatientHealthCard(Patient p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "INSERT INTO TW_PATIENT_HEALTH_CARD(TW_PATIENT_HEALTH_CARD_ID,TW_HEALTH_CARD_ID,"
                    + "TW_PATIENT_ID,CARD_NO,PREPARED_BY,ISSUE_DTE,EXPIRY_DTE)"
                    + " VALUES (SEQ_TW_PATIENT_HEALTH_CARD_ID.NEXTVAL," + p.getHealthCardId() + "," + p.getPatientId() + ","
                    + "'" + Util.removeSpecialChar(p.getHealthCardNo()) + "','" + p.getUserName() + "',"
                    + " SYSDATE,TO_DATE('" + p.getCardExpiry() + "','DD-MM-YYYY'))";
            arr.add(query);
            flag = this.dao.insertAll(arr, p.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getHealthCardById(String cardId, String patientId) {
        Map map = null;
        try {
            String query = "SELECT PW.PATIENT_NME,PW.TW_PATIENT_ID,CA.TW_HEALTH_CARD_ID,CA.CARD_NME,"
                    + " CA.PRODUCT_DISC,CA.DOCTOR_DISC"
                    + " FROM TW_PATIENT PW,TW_HEALTH_CARD CA,TW_PATIENT_HEALTH_CARD PH"
                    + " WHERE PH.TW_PATIENT_ID=" + patientId + ""
                    + " AND PH.TW_HEALTH_CARD_ID=" + cardId + ""
                    + " AND PH.TW_HEALTH_CARD_ID=CA.TW_HEALTH_CARD_ID"
                    + " AND PH.TW_PATIENT_ID=PW.TW_PATIENT_ID"
                    + " ORDER BY PH.TW_PATIENT_HEALTH_CARD_ID";
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
    public boolean deletePatientHealthCard(String healthCardId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_PATIENT_HEALTH_CARD WHERE TW_HEALTH_CARD_ID=" + healthCardId + "";
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
    public boolean updatePatientHealthCardIndicator(Patient p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {

            String query = "UPDATE TW_PATIENT_HEALTH_CARD SET ACTIVE_IND='" + p.getActiveIndicator().toUpperCase() + "'"
                    + " WHERE TW_PATIENT_HEALTH_CARD_ID=" + p.getHealthCardId() + "";
            arr.add(query);

            flag = this.dao.insertAll(arr, p.getUserName());

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
                    + " ORDER BY CARD_NME";
            list = this.getDao().getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorDegrees(String companyId) {
        List<Map> list = null;
        try {
            String query = "SELECT  *  FROM TW_MEDICAL_DEGREE ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveDiseases(Patient p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            //       String masterId = "";

            query = "DELETE FROM TW_PATIENT_DISEASE WHERE TW_PATIENT_ID=" + p.getPatientId() + "";
            arr.add(query);
            for (int i = 0; i < p.getDiseases().length; i++) {
                query = "INSERT INTO TW_PATIENT_DISEASE(TW_PATIENT_DISEASE_ID,TW_DISEASE_ID,TW_PATIENT_ID)"
                        + " VALUES (SEQ_TW_PATIENT_DISEASE_ID.NEXTVAL," + p.getDiseases()[i] + "," + p.getPatientId() + ")";

                arr.add(query);
            }

            flag = this.dao.insertAll(arr, p.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveDoctorSpecialityDisease(DoctorVO d) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            //       String masterId = "";

            query = "DELETE FROM TW_DOCTOR_DISESE WHERE TW_DOCTOR_ID=" + d.getDoctorId() + "";
            arr.add(query);
            for (int i = 0; i < d.getDiseases().length; i++) {
                query = "INSERT INTO TW_DOCTOR_DISESE(TW_DOCTOR_DISESE_ID,TW_DISEASE_ID,TW_DOCTOR_ID,PREPARED_BY,PREPARED_DTE )"
                        + " VALUES (SEQ_TW_PATIENT_DISEASE_ID.NEXTVAL," + d.getDiseases()[i] + "," + d.getDoctorId()
                        + ",'" + d.getUserName() + "',SYSDATE)";

                arr.add(query);
            }

            flag = this.dao.insertAll(arr, d.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveDoctorServices(DoctorVO d) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            //       String masterId = "";

            query = "DELETE FROM TW_DOCTOR_SERVICE WHERE TW_DOCTOR_ID=" + d.getDoctorId() + "";
            arr.add(query);
            for (int i = 0; i < d.getServices().length; i++) {
                query = "INSERT INTO TW_DOCTOR_SERVICE(TW_DOCTOR_SERVICE_ID,TW_MEDICAL_SERVICE_ID,TW_DOCTOR_ID )"
                        + " VALUES (SEQ_TW_DOCTOR_SERVICE_ID.NEXTVAL," + d.getServices()[i] + "," + d.getDoctorId() + ")";

                arr.add(query);
            }

            flag = this.dao.insertAll(arr, d.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveDoctorSpeciality(DoctorVO d) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            //       String masterId = "";

            query = "DELETE FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + d.getDoctorId() + "";
            arr.add(query);
            for (int i = 0; i < d.getSpecility().length; i++) {
                query = "INSERT INTO TW_DOCTOR_SPECIALITY(TW_DOCTOR_SPECIALITY_ID,TW_MEDICAL_SPECIALITY_ID,TW_DOCTOR_ID)"
                        + " VALUES (SEQ_TW_DOCTOR_SPECIALITY_ID.NEXTVAL," + d.getSpecility()[i] + "," + d.getDoctorId() + ")";

                arr.add(query);
            }

            flag = this.dao.insertAll(arr, d.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPatientDisease(String patientId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_PATIENT_DISEASE "
                    + " WHERE TW_PATIENT_ID=" + patientId + "";
            list = this.dao.getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getCompaniesById(String companyId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_COMPANY WHERE TW_COMPANY_ID =" + companyId + "";
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
    public boolean saveCompany(Pharma p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";

            if (p.getCompaniesId() != null && !p.getCompaniesId().isEmpty()) {
                query = "UPDATE TW_COMPANY SET COMPANY_NME=INITCAP('" + Util.removeSpecialChar(p.getCompanyName()) + "'),"
                        + " CONTACT_PERSON=INITCAP('" + Util.removeSpecialChar(p.getContactPerson().trim()) + "'),"
                        + " PHONE_NO=" + (p.getCellNo().isEmpty() ? 0 : p.getCellNo()) + ","
                        + " EMAIL='" + Util.removeSpecialChar(p.getEmail()).trim() + "',"
                        + " ADDRESS=INITCAP('" + Util.removeSpecialChar(p.getCompanyAddress()) + "')"
                        + " WHERE TW_COMPANY_ID =" + p.getCompaniesId() + "";
                arr.add(query);
            } else {
                String prevId = "SELECT SEQ_TW_COMPANY_ID .NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_COMPANY(TW_COMPANY_ID,COMPANY_NME ,CONTACT_PERSON,PHONE_NO,"
                        + "EMAIL,ADDRESS)"
                        + " VALUES (" + masterId + ",INITCAP('" + Util.removeSpecialChar(p.getCompanyName()) + "'),"
                        + "INITCAP('" + Util.removeSpecialChar(p.getContactPerson()) + "'),"
                        + "'" + Util.removeSpecialChar(p.getCellNo().trim()) + "',"
                        + "'" + Util.removeSpecialChar(p.getEmail()) + "',"
                        + "INITCAP('" + Util.removeSpecialChar(p.getCompanyAddress()) + "'))";
                arr.add(query);

            }
            flag = this.dao.insertAll(arr, p.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getCompanies(String companyName) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT TW_COMPANY_ID,COMPANY_NME,CONTACT_PERSON,"
                    + "PHONE_NO,EMAIL,ADDRESS FROM TW_COMPANY";
            if (companyName != null && !companyName.trim().isEmpty()) {
                where += " WHERE UPPER(COMPANY_NME) LIKE '%" + companyName.toUpperCase() + "%' ";
            }
            list = this.getDao().getData(query + where + " ORDER BY COMPANY_NME");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteCompany(String companyId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_COMPANY WHERE TW_COMPANY_ID=" + companyId + "";
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
    public List<Map> getPanelCompanies(String panelCompanyId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_COMPANY ORDER BY COMPANY_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean savePanelCompany(Pharma p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";

            if (p.getPharmaId() != null && !p.getPharmaId().isEmpty()) {
                query = "UPDATE TW_PHARMACEUTICAL SET COMPANY_NME=INITCAP('" + Util.removeSpecialChar(p.getCompanyName()) + "'),"
                        + " CONTACT_PERSON=INITCAP('" + Util.removeSpecialChar(p.getContactPerson().trim()) + "'),"
                        + " MOBILE_NO=" + (p.getCellNo().isEmpty() ? 0 : p.getCellNo()) + ","
                        + " DESIGNATION=INITCAP('" + Util.removeSpecialChar(p.getDesignation()).trim() + "'),"
                        + " EMAIL='" + Util.removeSpecialChar(p.getEmail()).trim() + "',"
                        + " LANDLINE_NO=" + (p.getPtclNo().isEmpty() ? 0 : p.getPtclNo()) + ","
                        + " ADDRESS=INITCAP('" + Util.removeSpecialChar(p.getCompanyAddress()) + "')"
                        + " WHERE TW_PHARMACEUTICAL_ID=" + p.getPharmaId() + "";
                arr.add(query);
            } else {
                String prevId = "SELECT SEQ_TW_DOCTOR_COMPANY_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_DOCTOR_COMPANY(TW_DOCTOR_COMPANY_ID,TW_COMPANY_ID,EXPIRY_DTE,"
                        + "TW_DOCTOR_ID)"
                        + " VALUES (" + masterId + "," + p.getPanelId() + ","
                        + " TO_DATE('" + p.getExpiryDate() + "','DD-MM-YYYY'),"
                        + "" + p.getDoctorId() + ")";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, p.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getAvailablePanelCompanies(String doctorId) {
        List<Map> list = null;
        try {
            String query = " SELECT * FROM TW_COMPANY WHERE TW_COMPANY_ID NOT IN ("
                    + " SELECT CM.TW_COMPANY_ID"
                    + " FROM TW_COMPANY CM,TW_DOCTOR_COMPANY TDC"
                    + " WHERE TDC.TW_COMPANY_ID=CM.TW_COMPANY_ID"
                    + " AND TDC.TW_DOCTOR_ID=" + doctorId + ""
                    + " AND TO_DATE(EXPIRY_DTE,'DD-MM-YYYY')<=TO_DATE(TO_CHAR(SYSDATE ,'DD-MM-YYYY'),'DD-MM-YYYY')"
                    + " )"
                    + " ORDER BY COMPANY_NME ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getPanelCompaniesForDoctors(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT CM.TW_COMPANY_ID,CM.COMPANY_NME,"
                    + " TO_CHAR(EXPIRY_DTE ,'DD-MON-YYYY') EXPIRY_DTE,TDC.TW_DOCTOR_COMPANY_ID,TDC.ACTIVE_IND "
                    + " FROM TW_COMPANY CM,TW_DOCTOR_COMPANY TDC"
                    + " WHERE TDC.TW_COMPANY_ID=CM.TW_COMPANY_ID"
                    + " AND TDC.TW_DOCTOR_ID=" + doctorId + ""
                    + " AND TO_DATE(EXPIRY_DTE,'DD-MM-YYYY')<=TO_DATE(TO_CHAR(SYSDATE ,'DD-MM-YYYY'),'DD-MM-YYYY')"
                    + " ORDER BY CM.COMPANY_NME ";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteAssignPanelCompany(String assignPanelId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_DOCTOR_COMPANY WHERE TW_DOCTOR_COMPANY_ID =" + assignPanelId + "";
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
    public boolean updateDoctorPanelCompanyIndicator(Pharma p) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {

            String query = "UPDATE TW_DOCTOR_COMPANY SET ACTIVE_IND='" + p.getActiveIndicator().toUpperCase() + "'"
                    + " WHERE TW_DOCTOR_COMPANY_ID=" + p.getPanelCompanyId() + "";
            arr.add(query);

            flag = this.dao.insertAll(arr, p.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getPanelPatient(String patientName, String mobileNbr) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT PT.TW_PATIENT_ID,PT.PATIENT_NME,PT.MOBILE_NO,PT.AGE,"
                    + " PT.EMAIL,PT.TW_COMPANY_ID,TO_CHAR(PT.DOB,'DD-MON-YYYY') DOB,PT.ATTEND_CLINIC,"
                    + " PT.ANY_ALLERGY,PT.GENDER,PT.TAKE_MEDICINE,PT.ADDRESS,PT.HEIGHT,"
                    + " PT.ANY_FEVER,"
                    + " PT.SMOKER_IND,PT.TAKE_STEROID,"
                    + " PT.WEIGHT,CP.TW_COMPANY_ID,CP.COMPANY_NME,CP.CONTACT_PERSON"
                    + " FROM TW_PATIENT PT,TW_COMPANY CP WHERE PT.TW_COMPANY_ID IS NOT NULL"
                    + " AND PT.TW_COMPANY_ID=CP.TW_COMPANY_ID";
            if (patientName != null && !patientName.trim().isEmpty()) {
                where += "AND UPPER(PATIENT_NME) LIKE '%" + patientName.toUpperCase() + "%' ";
            }
            if (mobileNbr != null && !mobileNbr.trim().isEmpty()) {
                if (where.contains("WHERE")) {
                    where += " AND MOBILE_NO LIKE '%" + mobileNbr.trim() + "%'";
                } else {
                    where += " AND MOBILE_NO LIKE '%" + mobileNbr.trim() + "%'";
                }
            }
            list = this.getDao().getData(query + where + " ORDER BY TW_PATIENT_ID DESC");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDoctorActtachementsById(String doctorId, String attachType) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_DOCTOR_ATTACHMENT"
                    + " WHERE TW_DOCTOR_ID=" + doctorId + " "
                    + " AND ATTACHMENT_TYP='" + attachType + "'"
                    + " ORDER BY TW_DOCTOR_ATTACHMENT_ID DESC";
            list = this.dao.getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getReportActtachementsById(String doctorId, String patientId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_PATIENT_ATTACHMENT"
                    + " WHERE TW_DOCTOR_ID=" + doctorId + " AND TW_PATIENT_ID=" + patientId;
            list = this.dao.getData(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteDoctorAttachement(String doctorAttachmentId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_DOCTOR_ATTACHMENT WHERE TW_DOCTOR_ATTACHMENT_ID=" + doctorAttachmentId + "";
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
    public boolean deleteReportAttachement(String attachmentId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_PATIENT_ATTACHMENT WHERE TW_PATIENT_ATTACHMENT_ID=" + attachmentId;
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
    public List<Map> getAvailablePatientsForAppointment(String date, String doctorId, String clinicId) {
        String where = "";
        List<Map> list = null;
        try {
            String query = "SELECT TW_PATIENT_ID,PATIENT_NME,MOBILE_NO,AGE,TO_CHAR(DOB,'DD-MON-YYYY') DOB,ATTEND_CLINIC,"
                    + " ANY_ALLERGY,GENDER,TAKE_MEDICINE,ADDRESS,HEIGHT,ANY_FEVER,SMOKER_IND,TAKE_STEROID,"
                    + " WEIGHT "
                    + " FROM TW_PATIENT WHERE TW_PATIENT_ID NOT IN("
                    + " SELECT TW_PATIENT_ID FROM TW_APPOINTMENT WHERE APPOINTMENT_DTE=TO_DATE('" + date + "','DD-MM-YYYY')"
                    + " AND TW_CLINIC_ID=" + clinicId + " AND TW_DOCTOR_ID=" + doctorId + ")";
            list = this.getDao().getData(query + where + " ORDER BY PATIENT_NME");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getHospital() {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_HOSPITAL ORDER BY TITLE";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveHospital(DoctorVO c) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";

            if (c.getHospitalId() != null && !c.getHospitalId().isEmpty()) {
                query = "UPDATE TW_HOSPITAL SET TITLE='" + Util.removeSpecialChar(c.getHospitalName().trim()) + "'"
                        + ",COUNTRY_ID=" + c.getCountryId() + ""
                        + ",CITY_ID=" + c.getCityId() + ""
                        + ",ADDRESS='" + Util.removeSpecialChar(c.getHospitalAddress().trim()) + "' "
                        + " WHERE TW_HOSPITAL_ID=" + c.getHospitalId() + "";
                arr.add(query);
            } else {
                query = "INSERT INTO TW_HOSPITAL(TW_HOSPITAL_ID,TITLE,COUNTRY_ID,CITY_ID,ADDRESS)"
                        + " VALUES (SEQ_TW_HOSPITAL_ID.NEXTVAL,'" + Util.removeSpecialChar(c.getHospitalName().trim()) + "',"
                        + "" + c.getCountryId() + "," + c.getCityId() + ",'"
                        + Util.removeSpecialChar(c.getHospitalAddress().trim()) + "')";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, c.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveTestGroup(String testGroupId, String testGroupName) {
        boolean flag = false;
        try {
            String query = "";

            if (testGroupId != null && !testGroupId.isEmpty()) {
                query = "UPDATE TW_TEST_GROUP SET TITLE=INITCAP('" + Util.removeSpecialChar(testGroupName.trim()) + "')"
                        + " WHERE TW_TEST_GROUP_ID=" + testGroupId + "";
            } else {
                query = "INSERT INTO TW_TEST_GROUP(TW_TEST_GROUP_ID,TITLE)"
                        + " VALUES (SEQ_TW_TEST_GROUP_ID.NEXTVAL,INITCAP('" + Util.removeSpecialChar(testGroupName.trim()) + "'))";
            }
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
    public List<Map> getTestGroups() {
        List<Map> list = null;
        String where = "";
        try {
            String query = "SELECT  * FROM TW_TEST_GROUP ORDER BY TW_TEST_GROUP_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getTestGroupById(String testGroupId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_TEST_GROUP WHERE TW_TEST_GROUP_ID=" + testGroupId + "";

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
    public boolean deleteTestGroup(String testGroupId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_TEST_GROUP WHERE TW_TEST_GROUP_ID=" + testGroupId + "";
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
    public List<Map> getLabPatient(String labId, String collectionCenterId) {
        List<Map> list = null;
        String where = "";
        try {
            String query = "SELECT PM.TW_PATIENT_ID,PM.TW_DOCTOR_ID,PD.TW_LAB_TEST_ID,PD.TW_LAB_MASTER_ID,"
                    + " PD.TW_LAB_DETAIL_ID,PD.PREPARED_DTE,PAT.PATIENT_NME,PAT.MOBILE_NO,PAT.ADDRESS,"
                    + " LT.TITLE,DOC.DOCTOR_NME,PD.TW_PRESCRIPTION_DETAIL_ID "
                    + " FROM TW_PRESCRIPTION_MASTER PM,TW_PRESCRIPTION_DETAIL PD,"
                    + " TW_PATIENT PAT, TW_LAB_TEST LT,TW_DOCTOR DOC"
                    + " WHERE PM.TW_PRESCRIPTION_MASTER_ID=PD.TW_PRESCRIPTION_MASTER_ID "
                    + " AND PM.TW_PATIENT_ID=PAT.TW_PATIENT_ID AND PD.TW_LAB_TEST_ID=LT.TW_LAB_TEST_ID"
                    + " AND PM.TW_DOCTOR_ID=DOC.TW_DOCTOR_ID AND PD.TW_LAB_MASTER_ID=" + labId
                    + " AND PD.TW_LAB_DETAIL_ID=" + collectionCenterId;
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean saveExaminationQuestion(String questionMasterId, String specialityId, String title, String userName, String categoryId) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";
            if (questionMasterId != null && !questionMasterId.isEmpty()) {
                query = "UPDATE TW_QUESTION_MASTER SET QUESTION_TXT=INITCAP('" + Util.removeSpecialChar(title.trim()) + "')"
                        + " WHERE TW_QUESTION_MASTER_ID=" + questionMasterId + "";
                arr.add(query);
            } else {
                String prevId = "SELECT SEQ_TW_QUESTION_MASTER_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_QUESTION_MASTER(TW_QUESTION_MASTER_ID,TW_MEDICAL_SPECIALITY_ID,QUESTION_TXT,PREPARED_BY,TW_QUESTION_CATEGORY_ID)"
                        + " VALUES (" + masterId + "," + specialityId
                        + ",INITCAP('" + Util.removeSpecialChar(title.trim()) + "'),'" + userName + "'," + categoryId + ")";
                arr.add(query);
                query = "INSERT INTO TW_QUESTION_DETAIL(TW_QUESTION_DETAIL_ID,TW_QUESTION_MASTER_ID,ANSWER_TXT,PREPARED_BY)"
                        + " VALUES (SEQ_TW_QUESTION_DETAIL_ID.NEXTVAL," + masterId
                        + ",'Others','" + userName + "')";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getExaminationQuestionForDoctor(String doctorId) {
        List<Map> list = null;
        String where = "";
        try {
            String query = "SELECT DU.* FROM TW_QUESTION_MASTER DU "
                    + " WHERE DU.TW_MEDICAL_SPECIALITY_ID IN "
                    + " (SELECT TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + doctorId + ")"
                    + " ORDER BY DU.TW_QUESTION_MASTER_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getExaminationQuestion(String specialityId, String categoryId) {
        List<Map> list = null;
        String where = "";
        try {
            if (specialityId != null && !specialityId.isEmpty()) {
                where = " AND TW_MEDICAL_SPECIALITY_ID=" + specialityId + "";
            }
            String query = "SELECT  * FROM TW_QUESTION_MASTER "
                    + " WHERE TW_QUESTION_CATEGORY_ID=" + categoryId + " "
                    + " " + where + " "
                    + " ORDER BY TW_QUESTION_MASTER_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getExaminationQuestionById(String questionMasterId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_QUESTION_MASTER WHERE TW_QUESTION_MASTER_ID=" + questionMasterId + "";

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
            arr.add("DELETE FROM TW_QUESTION_DETAIL "
                    + " WHERE TW_QUESTION_MASTER_ID=" + questionMasterId + "");
            String query = "DELETE FROM TW_QUESTION_MASTER "
                    + " WHERE TW_QUESTION_MASTER_ID=" + questionMasterId + "";
            arr.add(query);
            flag = this.dao.insertAll(arr, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveAnswer(String questionMasterId, String title, String userName) {
        boolean flag = false;
        try {
            String query = "";
            query = "INSERT INTO TW_QUESTION_DETAIL(TW_QUESTION_DETAIL_ID,TW_QUESTION_MASTER_ID,ANSWER_TXT,PREPARED_BY)"
                    + " VALUES (SEQ_TW_QUESTION_DETAIL_ID.NEXTVAL," + questionMasterId
                    + ",INITCAP('" + Util.removeSpecialChar(title.trim()) + "'),'" + userName + "')";
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
    public boolean saveExamination(String patientId, String doctorId, String questionarr[], String answerarr[], String userName, String questionCategory, String revisionNo) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";
            String prevId = "SELECT SEQ_TW_QUESTION_DETAIL_ID.NEXTVAL VMASTER FROM DUAL";
            List list = this.getDao().getJdbcTemplate().queryForList(prevId);
            if (list != null && list.size() > 0) {
                Map map = (Map) list.get(0);
                masterId = (String) map.get("VMASTER").toString();
            }
            query = "INSERT INTO TW_EXAMINATION_MASTER"
                    + "(TW_EXAMINATION_MASTER_ID,TW_PATIENT_ID,TW_DOCTOR_ID,REVISION_NO,PREPARED_BY,TW_QUESTION_CATEGORY_ID)"
                    + " VALUES (" + masterId + "," + patientId
                    + "," + doctorId + "," + revisionNo + ",'" + userName + "',"
                    + " " + questionCategory + ")";
            arr.add(query);
            for (int i = 0; i < questionarr.length; i++) {
                String value[] = answerarr[i].split(",");
                for (int j = 0; j < value.length; j++) {
                    String remark = "";
                    if (value[j].contains("_")) {
                        String remarks[] = value[j].split("_");
                        value[j] = remarks[0];
                        remark = remarks[1];
                    }
                    if (!value[j].isEmpty()) {
                        query = "INSERT INTO TW_EXAMINATION_DETAIL"
                                + "(TW_EXAMINATION_DETAIL_ID,TW_EXAMINATION_MASTER_ID,TW_QUESTION_MASTER_ID,TW_QUESTION_DETAIL_ID,REMARKS)"
                                + " VALUES (SEQ_TW_EXAMINATION_DETAIL_ID.NEXTVAL," + masterId
                                + "," + questionarr[i] + "," + value[j] + ",'" + Util.removeSpecialChar(remark).trim() + "')";
                        arr.add(query);
                    }
                }

            }
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getAnswer(String questionMasterId) {
        List<Map> list = null;
        String where = "";
        try {
            String query = "SELECT  * FROM TW_QUESTION_DETAIL WHERE TW_QUESTION_MASTER_ID=" + questionMasterId
                    + " ORDER BY TW_QUESTION_DETAIL_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getVaccinationDetail(String vaccinationId) {
        List<Map> list = null;
        String where = "";
        try {
            String query = "SELECT  * FROM TW_VACCINATION_DETAIL WHERE TW_VACCINATION_MASTER_ID=" + vaccinationId
                    + " ORDER BY TW_VACCINATION_DETAIL_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteAnswer(String questionDetailId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_QUESTION_DETAIL WHERE TW_QUESTION_DETAIL_ID=" + questionDetailId + "";
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
    public boolean deleteVaccinationDetail(String vaccinationDetailId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_VACCINATION_DETAIL WHERE TW_VACCINATION_DETAIL_ID=" + vaccinationDetailId + "";
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
    public List<Map> getAnswer() {
        List<Map> list = null;
        String where = "";
        try {
            String query = "SELECT  * FROM TW_QUESTION_DETAIL ORDER BY TW_QUESTION_DETAIL_ID DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getAnswerByCategory(String categoryId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_QUESTION_DETAIL QD,TW_QUESTION_MASTER QM"
                    + " WHERE QD.TW_QUESTION_MASTER_ID=QM.TW_QUESTION_MASTER_ID"
                    + " AND QM.TW_QUESTION_CATEGORY_ID=" + categoryId + ""
                    + " ORDER BY QM.TW_QUESTION_MASTER_ID,QD.TW_QUESTION_DETAIL_ID DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getExaminationRevision(String patientId, String doctorId, String revisionNo, String questionCategory) {
        List<Map> list = null;
        try {
            String query = "SELECT TED.* FROM TW_EXAMINATION_MASTER TEM,TW_EXAMINATION_DETAIL TED"
                    + " WHERE TEM.TW_EXAMINATION_MASTER_ID=TED.TW_EXAMINATION_MASTER_ID"
                    + " AND TEM.TW_PATIENT_ID=" + patientId + " AND TEM.TW_DOCTOR_ID=" + doctorId + ""
                    + " AND TEM.TW_QUESTION_CATEGORY_ID=" + questionCategory + ""
                    + " AND TEM.REVISION_NO=" + revisionNo;
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getRevision(String patientId, String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT DISTINCT REVISION_NO FROM TW_EXAMINATION_MASTER "
                    + " WHERE TW_PATIENT_ID=" + patientId + " AND TW_DOCTOR_ID=" + doctorId + " "
                    + " ORDER BY REVISION_NO DESC";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private String generateRevisionNo(String patientId, String doctorId) {
        String revisionNo = "";
        String query = "SELECT (NVL(MAX(REVISION_NO),0)+1) NEXT_REV FROM TW_EXAMINATION_MASTER"
                + " WHERE TW_DOCTOR_ID=" + doctorId + " "
                + " AND TW_PATIENT_ID=" + patientId + ""
                + " AND TO_CHAR(PREPARED_DTE,'DD-MM-YYYY')<>TO_CHAR(SYSDATE,'DD-MM-YYYY')";

        List<Map> list = this.getDao().getData(query);
        if (list != null && list.size() > 0) {
            Map map = list.get(0);
            revisionNo = map.get("NEXT_REV").toString();
        }

        return revisionNo;
    }

    @Override
    public boolean doctorFeatured(String doctorId, String status) {
        boolean flag = false;
        try {
            String query = "UPDATE TW_DOCTOR SET FEATURED_IND='" + status + "' WHERE TW_DOCTOR_ID=" + doctorId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    //Examination Question Categories
    @Override
    public boolean saveQuestionCategory(CategoryVO vo) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            String masterId = "";
            if (vo.getQuestionCategoryId() != null && !vo.getQuestionCategoryId().isEmpty()) {
                query = "UPDATE TW_QUESTION_CATEGORY SET CATEGORY_NME=INITCAP('" + Util.removeSpecialChar(vo.getCategoryName().trim()) + "')"
                        + " WHERE TW_QUESTION_CATEGORY_ID=" + vo.getQuestionCategoryId() + "";
                arr.add(query);
                masterId = vo.getQuestionCategoryId();
            } else {
                String prevId = "SELECT SEQ_TW_QUESTION_CATEGORY_ID.NEXTVAL VMASTER FROM DUAL";
                List list = this.getDao().getJdbcTemplate().queryForList(prevId);
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    masterId = (String) map.get("VMASTER").toString();
                }
                query = "INSERT INTO TW_QUESTION_CATEGORY(TW_QUESTION_CATEGORY_ID,TW_MEDICAL_SPECIALITY_ID,CATEGORY_NME,PREPARED_BY)"
                        + " VALUES (" + masterId + "," + vo.getSpecialityId()
                        + ",INITCAP('" + Util.removeSpecialChar(vo.getCategoryName().trim()) + "'),'" + vo.getUserName() + "')";
                arr.add(query);
            }
            ////
            if (vo.getCategoryAttachment() != null && !vo.getCategoryAttachment().isEmpty()) {
                String sep = File.separator;
                String picPath = vo.getFolderPath() + sep + masterId + sep;
                File folder = new File(picPath);
                if (!folder.exists()) {
                    boolean succ = (new File(picPath)).mkdir();
                }
                String fileName = new java.util.Date().getTime() + "_" + vo.getCategoryAttachment().getOriginalFilename();
                vo.getCategoryAttachment().transferTo(new File(folder + File.separator + fileName));
                arr.add("UPDATE TW_QUESTION_CATEGORY SET FILE_NME='" + fileName + "'"
                        + " WHERE TW_QUESTION_CATEGORY_ID=" + masterId + "");
            }
            if (vo.getCanChangeImage() != null && vo.getCanChangeImage().equalsIgnoreCase("Y")) {
                arr.add("UPDATE TW_QUESTION_CATEGORY SET FILE_NME=NULL"
                        + " WHERE TW_QUESTION_CATEGORY_ID=" + masterId + "");
            }
            flag = this.dao.insertAll(arr, vo.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getQuestionCategories(String specialityId) {
        List<Map> list = null;
        try {
            String query = "SELECT  * FROM TW_QUESTION_CATEGORY WHERE TW_MEDICAL_SPECIALITY_ID=" + specialityId
                    + " ORDER BY CATEGORY_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getQuestionCategoryById(String questionCategoryId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_QUESTION_CATEGORY WHERE TW_QUESTION_CATEGORY_ID=" + questionCategoryId + "";

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
    public boolean deleteQuestionCategory(String questionCategoryId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_QUESTION_CATEGORY WHERE TW_QUESTION_CATEGORY_ID=" + questionCategoryId + "";
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
    public List<Map> getQuestionCategoriesForDoctor(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT DU.* FROM TW_QUESTION_CATEGORY DU "
                    + " WHERE DU.TW_MEDICAL_SPECIALITY_ID IN "
                    + " (SELECT TW_MEDICAL_SPECIALITY_ID FROM TW_DOCTOR_SPECIALITY WHERE TW_DOCTOR_ID=" + doctorId + ")"
                    + " ORDER BY DU.SORT_BY";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //Add Vaccination
    @Override
    public boolean saveVaccination(String vaccinationId, String specialityId, String vaccinationName, String abbrev, String frequency, String userName) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            if (vaccinationId != null && !vaccinationId.isEmpty()) {
                query = "UPDATE TW_VACCINATION_MASTER SET VACCINATION_NME=INITCAP('" + Util.removeSpecialChar(vaccinationName.trim()) + "'),"
                        + " ABBREV='" + Util.removeSpecialChar(abbrev).toUpperCase() + "',FREQUENCY=" + frequency
                        + " WHERE TW_VACCINATION_MASTER_ID=" + vaccinationId + "";
                arr.add(query);
            } else {
                query = "INSERT INTO TW_VACCINATION_MASTER(TW_VACCINATION_MASTER_ID,TW_MEDICAL_SPECIALITY_ID,VACCINATION_NME,"
                        + " FREQUENCY,PREPARED_BY,PREPARED_DTE,ABBREV)"
                        + " VALUES (SEQ_TW_VACCINATION_MASTER_ID.NEXTVAL," + specialityId + ","
                        + " INITCAP('" + Util.removeSpecialChar(vaccinationName.trim()) + "')," + frequency + ","
                        + " '" + userName + "',SYSDATE,'" + Util.removeSpecialChar(abbrev).toUpperCase() + "')";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveVaccinationMedicine(String vaccinationId, String[] medicineName, String[] doseUsage, String userName) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            for (int i = 0; i < medicineName.length; i++) {

                query = "INSERT INTO TW_VACCINATION_DETAIL(TW_VACCINATION_DETAIL_ID,TW_VACCINATION_MASTER_ID,MEDICINE_NME,"
                        + " TOTAL_DOSE) VALUES (SEQ_TW_VACCINATION_DETAIL_ID.NEXTVAL," + vaccinationId
                        + ",INITCAP('" + Util.removeSpecialChar(medicineName[i].trim()) + "')," + doseUsage[i] + ")";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getVaccination(String specialityId) {
        List<Map> list = null;
        try {
            String query = "SELECT  * FROM TW_VACCINATION_MASTER WHERE TW_MEDICAL_SPECIALITY_ID=" + specialityId
                    + " ORDER BY VACCINATION_NME";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getVaccinationById(String vaccinationId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_VACCINATION_MASTER WHERE TW_VACCINATION_MASTER_ID=" + vaccinationId + "";

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
    public boolean deleteVaccination(String vaccinationId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_VACCINATION_MASTER WHERE TW_VACCINATION_MASTER_ID=" + vaccinationId + "";
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
    public boolean saveStudent(String studentId, String studentName, String cellNo, String gender, String age, String dob, String address, String userName) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            if(studentId != null && !studentId.isEmpty()) {
                query = "UPDATE TW_STUDENT SET"
                        + " STUDENT_NME=INITCAP('" + Util.removeSpecialChar(studentName.trim()) + "')," 
                        + " MOBILE_NO='"  + Util.removeSpecialChar(cellNo.trim()) + "',"
                        + " GENDER='"  + gender + "',"
                        + " AGE="  + (age != null && !age.isEmpty() ? age : 0) + ","
                        + " DOB=TO_DATE('"  + dob + "','DD-MM-YYYY'),"
                        + " ADDRESS='"  + Util.removeSpecialChar(address.trim()) + "'"
                        + " WHERE TW_STUDENT_ID="  + studentId;
            }else {
                query = "INSERT INTO TW_STUDENT(TW_STUDENT_ID,STUDENT_NME,MOBILE_NO,GENDER,AGE,DOB,ADDRESS,PREPARED_BY,"
                        + " PREPARED_DTE) VALUES (SEQ_TW_STUDENT_ID.NEXTVAL,"
                        + " INITCAP('" + Util.removeSpecialChar(studentName.trim()) + "')," 
                        + " '"  + Util.removeSpecialChar(cellNo.trim()) + "',"
                        + " '"  + gender + "',"
                        + " "  + (age != null && !age.isEmpty() ? age : 0) + ","
                        + " TO_DATE('"  + dob + "','DD-MM-YYYY'),"
                        + " '"  + Util.removeSpecialChar(address.trim()) + "',"
                        + " '"  + userName + "',SYSDATE)";
            }
                arr.add(query);
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getStudent() {
        List<Map> list = null;
        try {
            String query = "SELECT TW_STUDENT_ID,STUDENT_NME,MOBILE_NO,GENDER,AGE,TO_CHAR(DOB,'DD-MM-YYYY') DOB,ADDRESS"
                    + " FROM TW_STUDENT ORDER BY TW_STUDENT_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getStudentById(String studentId) {
        Map map = null;
        try {
            String query = "SELECT TW_STUDENT_ID,STUDENT_NME,MOBILE_NO,GENDER,AGE,TO_CHAR(DOB,'DD-MM-YYYY') DOB,ADDRESS"
                    + " FROM TW_STUDENT WHERE TW_STUDENT_ID=" + studentId + "";

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
    public boolean deleteStudent(String studentId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_STUDENT WHERE TW_STUDENT_ID=" + studentId + "";
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
    public boolean isStudentAlreadyExists(String phoneNo) {
        boolean flag = false;
        try {
            String query = "SELECT * FROM TW_STUDENT "
                    + " WHERE MOBILE_NO='" + phoneNo.trim() + "'";
            List<Map> list = this.getDao().getData(query);
            if (list != null && list.size() > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
    
    @Override
    public boolean saveDoctorArticle(String doctorArticleId, String title, String description,String userName) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            if(doctorArticleId != null && !doctorArticleId.isEmpty()) {
                query = "UPDATE TW_DOCTOR_ARTICLE SET"
                        + " TITLE=INITCAP('" + Util.removeSpecialChar(title.trim()) + "')," 
                        + " DESCRIPTION='"  + Util.removeSpecialChar(description.trim()) + "'"
                        + " WHERE TW_DOCTOR_ARTICLE_ID="  + doctorArticleId;
            }else {
                query = "INSERT INTO TW_DOCTOR_ARTICLE(TW_DOCTOR_ARTICLE_ID,TITLE,DESCRIPTION,PREPARED_BY,"
                        + " PREPARED_DTE) VALUES (SEQ_TW_DOCTOR_ARTICLE_ID.NEXTVAL,"
                        + " INITCAP('" + Util.removeSpecialChar(title.trim()) + "'),"
                        + " '"  + Util.removeSpecialChar(description.trim()) + "',"
                        + " '"  + userName + "',SYSDATE)";
            }
                arr.add(query);
            flag = this.dao.insertAll(arr, userName);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getDoctorArticle() {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_DOCTOR_ARTICLE ORDER BY TW_DOCTOR_ARTICLE_ID";
            list = this.dao.getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getDoctorArticleById(String doctorArticleId) {
        Map map = null;
        try {
            String query = "SELECT * FROM TW_DOCTOR_ARTICLE WHERE TW_DOCTOR_ARTICLE_ID=" + doctorArticleId + "";

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
    public boolean deleteDoctorArticle(String doctorArticleId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM TW_DOCTOR_ARTICLE WHERE TW_DOCTOR_ARTICLE_ID=" + doctorArticleId + "";
            int num = this.dao.getJdbcTemplate().update(query);
            if (num > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
