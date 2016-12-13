package com.todo.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.TimeZone;

public class GenericUtils {
    public static final String INDIAN_TIME_ZONE                    = "GMT+5:30";
    
    /**
     * Here strDate = "MMM dd, yyyy"
     * 
     * @param strDate
     * @return SQL Date
     * @throws ParseException
     */
    public static Date parseDate(String strDate) throws ParseException {
        String pattern = "MMM dd, yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = converUtilToSQLDate(format.parse(strDate));
            return date;
        } catch (ParseException e) {
            // e.printStackTrace();
            throw e;
        }
    }
  
    public static Date converUtilToSQLDate(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setTimeZone(TimeZone.getTimeZone(INDIAN_TIME_ZONE));
        return new Date(cal.getTimeInMillis());
    }
    
    public static boolean isNotEmpty(Collection<?> colls) {
        return colls != null && colls.size() > 0;
    }
}