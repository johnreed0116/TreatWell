/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author farazahmad
 */
public interface ServiceFactory {

    /**
     * @return the clinicService
     */
    ClinicService getClinicService();

    /**
     * @return the emailService
     */
    EmailService getEmailService();

    /**
     * @return the loginService
     */
    LoginService getLoginService();

    /**
     * @return the performaService
     */
    PerformaService getPerformaService();

    /**
     * @return the reportService
     */
    ReportService getReportService();

    /**
     * @return the setupService
     */
    SetupService getSetupService();

    /**
     * @return the smsService
     */
    SmsService getSmsService();

    /**
     * @return the umsService
     */
    UmsService getUmsService();

    /**
     * @param clinicService the clinicService to set
     */
    void setClinicService(ClinicService clinicService);

    /**
     * @param emailService the emailService to set
     */
    void setEmailService(EmailService emailService);

    /**
     * @param loginService the loginService to set
     */
    void setLoginService(LoginService loginService);

    /**
     * @param performaService the performaService to set
     */
    void setPerformaService(PerformaService performaService);

    /**
     * @param reportService the reportService to set
     */
    void setReportService(ReportService reportService);

    /**
     * @param setupService the setupService to set
     */
    void setSetupService(SetupService setupService);

    /**
     * @param smsService the smsService to set
     */
    void setSmsService(SmsService smsService);

    /**
     * @param umsService the umsService to set
     */
    void setUmsService(UmsService umsService);
    
}
