/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.utility;

import com.alberta.model.Encryption;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author abbas
 */
public class Util {

    public static String removeSpecialChar(String st) {
        if (st != null) {
            st = st.replace('\'', '`');
        } else {
            st = "";
        }
        return st;
    }

    public static int numOfdays(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static int numOfdays(String month) {
        int days = 0;
        try {
            Date date = new SimpleDateFormat("dd-MMM-yyyy").parse("01-" + month.toUpperCase());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (int) days; // return total number of dyas
    }

    public static int numOfWeeks(String month, String year) {
        Calendar weeks = new GregorianCalendar();
        weeks.set(Calendar.DAY_OF_MONTH, 1);
        weeks.set(Calendar.MONTH, Integer.parseInt(month) - 1); // month start from zero index ex: jan=0, feb=1....
        weeks.set(Calendar.YEAR, Integer.parseInt(year));
        return (int) (weeks.getActualMaximum(Calendar.WEEK_OF_MONTH)); // return total number of weeks
    }

    // month: Feb-2012 => start date:01-02-2012 & end date:29-02-2012
    public static String[] getStartEndDateByGiventMonth(String month) {
        String[] dt = new String[2];
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = dateFormat.parse("01-" + month.toUpperCase());
            Calendar cal = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            cal.setTime(date);
            dt[0] = dateFormat.format((Date) cal.getTime());
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            dt[1] = dateFormat.format((Date) cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dt;
    }

    // month: Feb-2012 => end date:29-02-2012
    public static String getEndDateByGivenMonth(String month) {
        String dt = "";
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = dateFormat.parse("01-" + month.toUpperCase());
            Calendar cal = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            dt = dateFormat.format((Date) cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dt;
    }

    // date: 01-Mar-2012 => end date:29-02-2012
    public static String getNextDateByGivenDate(String date, String datePattern) {
        String previousDate = "";
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(datePattern);
            Date dt = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.add(Calendar.DATE, 1);
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            previousDate = dateFormat.format((Date) cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return previousDate;
    }

    // date: 01-Mar-2012 => end date:29-02-2012
    public static String getPreviousDateByGivenDate(String date, String datePattern) {
        String previousDate = "";
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(datePattern);
            Date dt = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.add(Calendar.DATE, -1);
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            previousDate = dateFormat.format((Date) cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return previousDate;
    }

    // given month: 11 => 10
    public static String getPreviousMonthByGivenMonth(String month, String datePattern) {
        String previousMonth = "";
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(datePattern);
            Date dt = dateFormat.parse("01-" + month.toUpperCase());
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.add(Calendar.MONTH, -1);
            dateFormat = new SimpleDateFormat("MMM-yyyy");
            previousMonth = dateFormat.format((Date) cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return previousMonth;
    }

    // given month: 11 => 12
    public static String getNextMonthByGivenMonth(String month, String datePattern) {
        String previousMonth = "";
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(datePattern);
            Date dt = dateFormat.parse("01-" + month.toUpperCase());
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.add(Calendar.MONTH, 1);
            dateFormat = new SimpleDateFormat("MMM-yyyy");
            previousMonth = dateFormat.format((Date) cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return previousMonth;
    }

    // get Due Date 
    public static String getDueDate(String date, String datePattern, String CrDays) {
        String previousDate = "";
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(datePattern);
            Date dt = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.add(Calendar.DATE, Integer.parseInt(CrDays));
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            previousDate = dateFormat.format((Date) cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return previousDate;
    }

    public static List<String> convertArrayToList(String[] input) {
        List<String> list = new ArrayList<String>();
        if (input != null) {
            for (int i = 0; i < input.length; i++) {
                list.add(input[i]);
            }
        }
        return list;
    }

    public static String convertArrayToString(String[] input) {
        String output = "";
        if (input != null) {
            for (int i = 0; i < input.length; i++) {
                if (i != input.length - 1) {
                    output += "" + input[i].trim() + ", ";
                } else {
                    output += "" + input[i].trim() + "";
                }
            }
        }
        return output;
    }

    public static String assignSingleQouteToString(String input) {
        String output = "";
        String[] strArray = input.split(",");
        for (int i = 0; i < strArray.length; i++) {
            if (i != strArray.length - 1) {
                output += "'" + strArray[i].trim() + "', ";
            } else {
                output += "'" + strArray[i].trim() + "'";
            }
        }
        return output;
    }

    public static String renameFileName(String input) {
        String output = input != null ? input.trim().replace(" ", "_") : "";
        if (output.contains("%")) {
            output = output.replace("%", "_");
        }
        if (output.contains("?")) {
            output = output.replace("?", "_");
        }
        if (output.contains("#")) {
            output = output.replace("#", "_");
        }
        if (output.contains("&")) {
            output = output.replace("&", "_");
        }
        return output;
    }

    public static String generatePassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString().toLowerCase();

        return saltStr;
    }

    public static boolean sendSignUpMessage(String mobileNo, String userName, String password) throws IOException {
        boolean flag = false;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String message = "Your login details are: UserName: " + userName + " Password: " + password + " Please login to www.treatwellservices.com";
            String url = "http://pk.eocean.us/APIManagement/API/RequestAPI?user=TWS&pwd=ANreowHdVt%2fbvT6ubUCK01SuOXWcxjM5H2QOUH1MUdnBh1fhqiq4kWFJjPctIAFSlA%3d%3d&sender=TWS&response=string";
            HttpGet httpGet = new HttpGet(url);
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("reciever", mobileNo));
            nvps.add(new BasicNameValuePair("msg-data", message));
            URI uri = new URIBuilder(httpGet.getURI()).addParameters(nvps).build();
            httpGet.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpclient.close();
        }
        return flag;
    }
}
