package org.liuzhibin.research.order.service;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static Calendar defaultDateCalendar =  null;
    
    static {
        defaultDateCalendar = Calendar.getInstance();
        defaultDateCalendar.set(1900, 0, 1, 0, 0, 0);
    }
    
    /**
     * 获取指定日期的{@link Date}对象。
     * 
     * @param year 年，例如2016。
     * @param month 月，1-12.
     * @param date 日，1-31
     * @return
     */
    public static Date getDate(int year, int month, int date) {
        if(year==1900 && month==1 && date==1) return defaultDateCalendar.getTime();
        
        if(month<=0 || month>12 || date<=0 || date>31) return null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date, 0, 0, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取默认日期（1900-01-01）。
     * @return
     */
    public static Date defaultDate(){
        return defaultDateCalendar.getTime();
    }
}