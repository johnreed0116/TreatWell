/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.performa;

import com.alberta.dao.DAO;
import com.alberta.model.DoctorVO;
import com.alberta.model.Lab;
import com.alberta.model.PerformaVO;
import com.alberta.model.Pharma;
import com.alberta.model.PrescriptionVO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author farazahmad
 */
public interface PerformaService {

    /**
     * @return the dao
     */
    DAO getDao();

    /**
     * @param dao the dao to set
     */
    void setDao(DAO dao);

    String saveAppointment(PerformaVO vo);

    List<Map> getAppointmentsForDoctor(String doctorId, String clinicId, String startDate, String endDate);

    List<Map> getAppointmentsForDate(String date, String clinicId, String doctorId);

    boolean updateAppointmentStatus(String appointmentId, String status, String userName);

    boolean updateAppointmentDateTime(String appointmentId, String date, String time, String userName);

    boolean saveDoctorProcedure(DoctorVO ds);

    List<Map> getDoctorProcedures(String doctorId, String appointmentId, String companyId);

    List<Map> getPateintVaccination(String doctorId, String patientId);

    List<Map> getPateintVaccinationMedicine(String masterId, String dte);

    List<Map> searchPatient(String patientName);

    List<Map> getMedicalProcedures(String companyId);

    List<Map> getAppointedPatientsForDoctor(String doctorId, String clinicId, String showAll);

    String savePrescription(PrescriptionVO vo);

    boolean saveHealthCard(DoctorVO c);

    boolean saveVaccination(PrescriptionVO vo);

    List<Map> getHealthCards();

    boolean deleteHealthCard(String cardId);

    Map getHealthCardById(String cardId);

    List<Map> getLabTests();

    boolean saveAppointmentFee(PerformaVO vo);

    List<Map> getProcedureFeeForAppointment(String appointmentId);

    boolean deleteAppointmentProcedure(String id);

    List<Map> getPrescriptionForMedicine(String prescId);

    List<Map> getPrescriptionForLabTest(String prescId);

    Map getPrescriptionMasterById(String prescId);

    List<Map> getAvailablePanelCompanyForDoctors(String doctorId);

    Map getProcedureFeeById(String procedureId);

    boolean deleteDoctorProcedure(String procedureId);

    Map getMarginsByDoctorId(String doctorId);

    boolean saveCollectedFee(PerformaVO vo);

    boolean savePharmacyCompany(Pharma p, String attachmentPath);

    public List<Map> getPharmacyCompany();

    public List<Map> getPharmacyCompany(String pharmaId);

    boolean deletePharmacyCompany(String pharmacyId);

    Map getPharmacyCompanyById(String pharmacyId);

    boolean savePharmacyStore(Pharma p);

    public List<Map> getPharmacyStore(String pharmaId);

    boolean deletePharmacyStore(String pharmacyStoreId);

    Map getPharmacyStoreById(String pharmacyStoreId);

    Map getMedicalLabById(String MedicalLabId);

    boolean deleteMedicalLab(String MedicalLabId);

    List<Map> getMedicalLab();

    List<Map> getMedicalSpeciality();

    boolean saveMedicalLab(Lab p, String path);

    boolean saveLabCollectionCenter(Lab p);

    List<Map> getLabCollectionCenter(String labCollectionCenterId);

    boolean deleteLabCollectionCenter(String labCollectionCenterId);

    Map getLabCollectionCenterById(String labCollectionCenterId);

    List<Map> getMedicalLab(String medicalLabId);

    List<Map> getPatientFeeHistory(String patientId, String doctorId, String clinicId);

    List<Map> getAvailableMedicalProcedures(String doctorId);

    boolean saveLabAttachment(DoctorVO d);

    List<Map> getLabActtachementsById(String doctorId, String patientId, String prescDetailId);

    boolean deleteLabAttachement(String labAttachmentId);

    boolean saveMedicineUsage(String titleEnglish, String titleUrdu, String specialityId, String medicineUsageId);

    public List<Map> getMedicineUsage(String specialityId);

    boolean deleteMedicineUsage(String medicineUsageId);

    Map getMedicineUsageById(String medicineUsageId);

    Map getReading(String patientId, String doctorId);

    List<Map> getAppointmentDates(String doctorId, String clinicId);

    List<Map> getAppointedTime(String doctorId, String clinicId, String date);

    Map getDoctorClinic(String clinicId);

    boolean saveReadings(String sugar, String bloodPressure, String fever, String doctorId, String patientId, String username);

    List<Map> getPrescriptionMasterForPatient(String patientId);

    boolean saveIntakeQuestion(String questionMasterId, String specialityId, String title, String userName);

    List<Map> getIntakeQuestionForDoctor(String doctorId);

    List<Map> getIntakeQuestions(String specialityId);

    Map getIntakeQuestionById(String questionMasterId);

    boolean deleteExaminationQuestion(String questionMasterId);

    List<Map> getIntakeAnswers(String doctorId);

    boolean saveInTakeForm(String patientId, String doctorId, String questionarr[], String answerarr[], String diseases[], String userName);

    List<Map> getIntakeFormData(String patientId, String doctorId);

    List<Map> getVaccinationByDoctorSpecility(String doctorId, String categoryId);

    boolean saveExamination(PrescriptionVO vo);

    Map getNextPrescriptionNumber(String clinicId, String doctorId, String patientId);

    List<Map> getMedicineUsageForDoctor(String doctorId);

    boolean savePatientDiagnostics(PrescriptionVO vo);

    List<Map> getLabTestDetailsForDoctor(String doctorId, String clinicId);

    List<Map> getDiscountTypes(String category);

    List<Map> getPharmacyDiscounts(String pharmaId);

    Map getAppointmentById(String appointmentId);
}
