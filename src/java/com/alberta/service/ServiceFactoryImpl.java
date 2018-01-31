/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.service;

import com.alberta.clinic.ClinicService;
import com.alberta.email.EmailService;
import com.alberta.login.LoginService;
import com.alberta.performa.PerformaService;
import com.alberta.report.ReportService;
import com.alberta.setup.SetupService;
import com.alberta.sms.SmsService;
import com.alberta.ums.UmsService;

/**
 *
 * @author Faraz
 */
public class ServiceFactoryImpl implements ServiceFactory {

    private LoginService loginService;
    private UmsService umsService;
    private SetupService setupService;
    private PerformaService performaService;
    private EmailService emailService;
    private ReportService reportService;
    private ClinicService clinicService;
    private SmsService smsService;

    /**
     * @return the loginService
     */
    @Override
    public LoginService getLoginService() {
        return loginService;
    }

    /**
     * @param loginService the loginService to set
     */
    @Override
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * @return the umsService
     */
    @Override
    public UmsService getUmsService() {
        return umsService;
    }

    /**
     * @param umsService the umsService to set
     */
    @Override
    public void setUmsService(UmsService umsService) {
        this.umsService = umsService;
    }

    /**
     * @return the setupService
     */
    @Override
    public SetupService getSetupService() {
        return setupService;
    }

    /**
     * @param setupService the setupService to set
     */
    @Override
    public void setSetupService(SetupService setupService) {
        this.setupService = setupService;
    }

    /**
     * @return the performaService
     */
    @Override
    public PerformaService getPerformaService() {
        return performaService;
    }

    /**
     * @param performaService the performaService to set
     */
    @Override
    public void setPerformaService(PerformaService performaService) {
        this.performaService = performaService;
    }

    /**
     * @return the emailService
     */
    @Override
    public EmailService getEmailService() {
        return emailService;
    }

    /**
     * @param emailService the emailService to set
     */
    @Override
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * @return the reportService
     */
    @Override
    public ReportService getReportService() {
        return reportService;
    }

    /**
     * @param reportService the reportService to set
     */
    @Override
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * @return the clinicService
     */
    @Override
    public ClinicService getClinicService() {
        return clinicService;
    }

    /**
     * @param clinicService the clinicService to set
     */
    @Override
    public void setClinicService(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    /**
     * @return the smsService
     */
    @Override
    public SmsService getSmsService() {
        return smsService;
    }

    /**
     * @param smsService the smsService to set
     */
    @Override
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

}
