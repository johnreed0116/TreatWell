/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.setup;

import com.alberta.dao.DAO;
import com.alberta.model.Article;
import com.alberta.model.CategoryVO;
import com.alberta.model.Company;
import com.alberta.model.DoctorClinic;
import com.alberta.model.DoctorVO;
import com.alberta.model.Patient;
import com.alberta.model.Pharma;
import com.alberta.model.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author farazahmad
 */
public interface SetupService {

    List<Map> getReportActtachementsById(String doctorId, String patientId);

    boolean saveCompanyLogo(Pharma d, String path);

    boolean deleteReportAttachement(String attachmentId);

    boolean deletePatient(String patientId);

    boolean saveDoctorServices(DoctorVO d);

    List<Map> getHospital();

    boolean saveHospital(DoctorVO c);

    Map getTimeForClinic(String doctorId, String clinicId);

    boolean deleteAssignPanelCompany(String assignPanelId);

    boolean saveDoctorSpeciality(DoctorVO d);

    boolean deleteClinic(String clinicId);

    boolean deleteCompany(String companyId);

    boolean deleteDoctor(String doctorId);

    boolean deleteDoctorAttachement(String doctorAttachmentId);

    boolean deleteDoctorClinic(String doctorClinicId, String clinicId, String doctorId);

    boolean deleteDoctorMedicine(String id);

    boolean deletePatientHealthCard(String healthCardId);

    boolean deletePharma(String pharmaId);

    List<Map> getAvailableClinicForDoctors(String doctorId, String doctorClinicId);

    List<Map> getCityByCountryId(String countryId);

    List<Map> getStateByCountryId(String countryId);

    List<Map> getClinic(String clinicName);

    Map getClinicById(String clinicId);

    List<Map> getClinicForDoctors(String doctorId);

    List<Map> getClinics(String clinicId);

    List<Map> getBloodGroup();

    List<Map> getCompanies(String companyName);

    Map getCompaniesById(String companyId);

    Company getCompanyById(String id);

    List<Map> getCountry(String comapnyId);

    /**
     * @return the dao
     */
    DAO getDao();

    List<Map> getDiseases(String companyId);

    List<Map> getDoctorActtachementsById(String doctorId, String attachType);

    Map getDoctorById(String doctorId);

    List<Map> getDoctorCagetories(String companyId);

    Map getDoctorClinicById(String doctorClinicId);

    List<Map> getDoctorDegrees(String companyId);

    List<Map> getDoctorServiceById(String doctorId);

    List<Map> getDoctorSpeciality(String companyId);

    List<Map> getDoctorSpecialityById(String doctorId);

    List<Map> getDoctorSpecialityDiseasesById(String doctorId);

    List<Map> getDoctorTypes(String companyId);

    List<Map> getDoctors(String doctorName, String mobileNbr, String doctorType);

    List<Map> getDoctors(String doctorId);

    List<Map> getDoctorsByService(String serviceId, String companyId);

    List<Map> getDoctorsForClinic(String clinicId);

    List<Map> getDoctorsMedicine(String doctorId);

    List<Map> getDoseUsage(String companyId);

    List<Map> getFrequencies(String companyId);

    Map getHealthCardById(String cardId, String patientId);

    List<Map> getHealthCards();

    List<Map> getMedicalServices(String companyId);

    List<Map> getMedicines();

    List<Map> getPanelCompanies(String panelCompanyId);

    List<Map> getPanelCompaniesForDoctors(String doctorId);

    List<Map> getPanelPatient(String patientName, String mobileNbr);

    List<Map> getPatient(String patientName, String mobileNbr, String startRowNo, String endRowNo, String searchCharacter);

    Map getPatientById(String patientId);

    List<Map> getPatientDisease(String patientId);

    List<Map> getPatientDiseasesById(String patientId);

    List<Map> getPatientHealthCards(String patientId);

    List<Map> getPatients(String patientsId);

    List<Map> getPharma(String pharmaName);

    Map getPharmaById(String pharmaId);

    List<Map> getPharmaCompanies();

    Map getPharmaProductById(String productId);

    List<Map> getPharmaProducts(String pharmaId);

    boolean isDoctorAlreadyExists(String phoneNo);

    boolean isPatientAlreadyExists(String phoneNo, String companyId);

    boolean saveClinic(DoctorVO c);

    boolean saveCompany(Pharma p);

    boolean saveDiseases(Patient p);

    boolean saveDoctor(DoctorVO vo);

    boolean saveDoctorClinic(DoctorClinic dc);

    boolean saveDoctorMedicine(String doctorId, String medicineId, String userName);

    boolean saveDoctorSpecialityDisease(DoctorVO d);

    boolean saveInTakeForm(Patient p);

    boolean savePanelCompany(Pharma p);

    String savePatient(Patient p);

    boolean savePatientHealthCard(Patient p);

    /// Patient
    boolean savePatientReports(Patient p, String path);

    boolean savePharma(Pharma p);

    boolean saveProduct(Product p);

    /**
     * @param dao the dao to set
     */
    void setDao(DAO dao);

    boolean updateDoctorPanelCompanyIndicator(Pharma p);

    boolean updatePatientHealthCardIndicator(Patient p);

    boolean saveDoctorAttachment(DoctorVO d, String path);

    List<Map> getAvailablePatientsForAppointment(String date, String doctorId, String clinicId);

    List<Map> getAvailablePanelCompanies(String doctorId);

    boolean updateDoctorExpiry(String doctorId, String expiryDate);

    List<Map> getPharmaCompanies(String pharmaId);

    boolean saveTestGroup(String testGroupId, String testGroupName);

    List<Map> getTestGroups();

    Map getTestGroupById(String testGroupId);

    boolean deleteTestGroup(String testGroupId);

    List<Map> getLabPatient(String labId, String collectionCenterId);

    boolean saveExaminationQuestion(String questionMasterId, String specialityId, String title, String userName, String categoryId);

    List<Map> getExaminationQuestion(String specialityId, String categoryId);

    List<Map> getAnswer(String questionMasterId);

    List<Map> getVaccinationDetail(String vaccinatiionId);

    List<Map> getAnswer();

    Map getExaminationQuestionById(String questionMasterId);

    boolean deleteExaminationQuestion(String questionMasterId);

    boolean saveAnswer(String questionMasterId, String title, String userName);

    boolean deleteAnswer(String questionDetailId);

    boolean deleteVaccinationDetail(String vaccinationDetailId);

    List<Map> getExaminationRevision(String patientId, String doctorId, String revisionNo, String questionCategory);

    List<Map> getRevision(String patientId, String doctorId);

    boolean doctorFeatured(String doctorId, String status);

    List<Map> getClinicForStaff(String clinicId);

    List<Map> getExaminationQuestionForDoctor(String doctorId);

    boolean saveQuestionCategory(CategoryVO vo);

    List<Map> getQuestionCategories(String specialityId);

    Map getQuestionCategoryById(String questionCategoryId);

    boolean deleteQuestionCategory(String questionCategoryId);

    List<Map> getQuestionCategoriesForDoctor(String doctorId);

    List<Map> getAnswerByCategory(String categoryId);

    boolean saveVaccination(String vaccinationId, String specialityId, String vaccinationName, String abbrev, String frequency, String categoryId, String userName);

    boolean saveVaccinationMedicine(String vaccinationId, String[] medicineName, String[] doseUsage, String userName);

    List<Map> getVaccination(String specialityId, String categoryId);

    Map getVaccinationById(String vaccinationId);

    boolean deleteVaccination(String vaccinationId);

    boolean saveVideoLink(String doctorId, String videoLink);

    boolean saveStudent(String studentId, String studentName, String cellNo, String gender, String age, String dob, String address, String userName);

    List<Map> getStudent();

    Map getStudentById(String studentId);

    boolean deleteStudent(String studentId);

    boolean isStudentAlreadyExists(String phoneNo);

    boolean deleteDoctorArticle(String doctorArticleId);

    Map getDoctorArticleById(String doctorArticleId);

    List<Map> getDoctorArticle();

    boolean saveDoctorArticle(Article ar);

    boolean saveDoctorArticleAttachment(Article ar);

    List<Map> getAnswerByQuestion(String questionId);

    List<Map> searchPatients(String patientName);

    boolean copyExaminationQuestions(String specialityId, String fromCategoryId, String toCategoryId, String userName);

    List<Map> getDoctorDiscounts(String doctorId);
    List<Map> getLabDiscounts(String collectionCenterId);
}
