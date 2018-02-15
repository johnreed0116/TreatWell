/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.filter;

import com.alberta.model.User;
import com.alberta.service.ServiceFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Faraz
 */
public class RequestFilter implements Filter {

    private FilterConfig config = null;
    private static int popUP = 0;
    public ServiceFactory serviceFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Map map = new HashMap();
        String sub = "";
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession currentSession = req.getSession(false);

        RequestDispatcher rd = null;
        String action = request.getParameter("action");
        String name = null;

        if (request instanceof HttpServletRequest) {
            name = ((HttpServletRequest) request).getRequestURI();
        }
        sub = name.substring(name.lastIndexOf('/') + 1, name.length());
        String url = name.substring(name.lastIndexOf('/') + 1, name.length());
        if (action != null) {
            url = url + "?action=" + action;
        }
        if (currentSession != null || url.equals("login.htm?action=login") || url.equals("login.htm?action=processSignOut")) {
            User user = currentSession != null ? (User) currentSession.getAttribute("user") : null;
            if (user != null) {
                if (isAllowed(user, url)) {
                    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    res.addHeader("Cache-Control", "post-check=0, pre-check=0");
                    res.setHeader("Pragma", "no-cache");
                    res.setDateHeader("Expires", 0);
                    chain.doFilter(request, response);
                } else {
                    rd = request.getRequestDispatcher("login.htm?action=accessDenied");
                    rd.forward(request, response);
                }
            } else if (url.equalsIgnoreCase("login.htm?action=login") || url.equalsIgnoreCase("login.htm?action=processLogin") || url.equals("index.htm") || url.equals("login.htm?action=processSignOut")) {
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                res.addHeader("Cache-Control", "post-check=0, pre-check=0");
                res.setHeader("Pragma", "no-cache");
                res.setDateHeader("Expires", 0);
                chain.doFilter(request, response);
            } else {
                rd = request.getRequestDispatcher("login.htm?action=expireSession");
                rd.forward(request, response);
            }
        } else {
            rd = request.getRequestDispatcher("login.htm?action=expireSession");
            rd.forward(request, response);
        }
    }

    private boolean isAllowed(User user, String url) {
        //if (url.equalsIgnoreCase("login.htm?action=processLogin") || url.equalsIgnoreCase("login.htm?action=processSignOut")) {
        return true;
        /*} else {
         return serviceFactory.getUmsService().hasRightsOn(user.getUsername(), url);*/

        //}
    }

    @Override
    public void destroy() {
        config = null;
    }

    /**
     * @return the serviceFactory
     */
    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    /**
     * @param serviceFactory the serviceFactory to set
     */
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
}
