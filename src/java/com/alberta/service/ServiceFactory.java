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
public interface ServiceFactory {

    /**
     * @return the loginService
     */
    LoginService getLoginService();

    /**
     * @param loginService the loginService to set
     */
    void setLoginService(LoginService loginService);

    UmsService getUmsService();

    void setUmsService(UmsService umsService);

    SetupService getSetupService();

    void setSetupService(SetupService setupService);

    PerformaService getPerformaService();

    void setPerformaService(PerformaService performaService);

    EmailService getEmailService();

    void setEmailService(EmailService emailService);

    ReportService getReportService();

    void setReportService(ReportService reportService);

    ClinicService getClinicService();

    void setClinicService(ClinicService clinicService);

    SmsService getSmsService();

    void setSmsService(SmsService smsService);
}
