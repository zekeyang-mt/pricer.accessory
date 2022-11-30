package com.ftre.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static final long EXCEL_BEGIN_TIME = -2209017600000L;
	private static final long DAY_MILLISECONDS = 1000 * 3600 * 24;

	public Integer date2ExcelNumber(String dateString) {
		Integer result = null;		
		try {
			BigDecimal days = null;
			Date date = new SimpleDateFormat("yyyy/MM/dd").parse(dateString);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Integer.parseInt(TimeUtil.getYear(date)), Integer.parseInt(TimeUtil.getMonth(date)) -1, Integer.parseInt(TimeUtil.getDay(date)));
			days = BigDecimal.valueOf((calendar.getTimeInMillis() - EXCEL_BEGIN_TIME)/DAY_MILLISECONDS + 2);
			result = days.intValue();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
