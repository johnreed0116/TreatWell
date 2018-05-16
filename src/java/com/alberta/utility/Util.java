/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

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
        String message = "Thanks you for visiting our Clinic. Kindly download our mobile app EZIMEDIC to schedule your future appointments directly and to keep your medical record. Your login details are: UserName: " + userName + " Password: " + password + "";
        flag = Util.generateSms(mobileNo, message);
        return flag;
    }

    public static boolean sendAppointmentMessage(String mobileNo, String datetime, String doctorName, String clinicName) throws IOException {
        boolean flag = false;
        String message = "Your appoinment with " + doctorName + " has been scheduled at " + datetime + " at " + clinicName + ". Kindly download our mobile app EZIMEDIC to schedule your appointments.";
        flag = Util.generateSms(mobileNo, message);
        return flag;
    }

    public static boolean sendCancelAppointmentMessage(String mobileNo, String datetime, String doctorName, String clinicName) throws IOException {
        boolean flag = false;
        String message = "Your appoinment with " + doctorName + " has been cancelled. Kindly download our mobile app EZIMEDIC to schedule your appointments.";
        flag = Util.generateSms(mobileNo, message);
        return flag;
    }

    public static boolean generateSms(String mobileNo, String message) {
        boolean flag = false;
        try {
            if (mobileNo != null && !mobileNo.isEmpty() && mobileNo.length() == 11) {
                if (mobileNo.startsWith("0")) {
                    mobileNo = mobileNo.substring(1, mobileNo.length());
                    mobileNo = "92" + mobileNo;
                } else {
                    mobileNo = "92" + mobileNo;
                }
            }
            String data = "id=" + URLEncoder.encode("ezimedic", "UTF-8");
            data += "&pass=" + URLEncoder.encode("treat135", "UTF-8");
            data += "&msg=" + URLEncoder.encode(message, "UTF-8");
            data += "&lang=" + URLEncoder.encode("English", "UTF-8");
            data += "&to=" + URLEncoder.encode(mobileNo, "UTF-8");
            data += "&mask=" + URLEncoder.encode("EZIMEDIC", "UTF-8");
            data += "&type=" + URLEncoder.encode("xml", "UTF-8");
            // Send data
            URL url = new URL("http://www.sms4connect.com/api/sendsms.php/sendsms/url");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult = "";
            while ((line = rd.readLine()) != null) {
                sResult = sResult + line + " ";
            }
            System.out.println(sResult);
            wr.close();
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
