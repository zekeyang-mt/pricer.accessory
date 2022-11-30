package com.ftre.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
private static SimpleDateFormat df;
	
	public static String getYear(Date date) {
		df = new SimpleDateFormat("yyyy");
		return df.format(date);
	}
	
	public static String getMonth(Date date) {
		df = new SimpleDateFormat("MM");
		return df.format(date);
	}
	
	public static String getDay(Date date) {
		df = new SimpleDateFormat("dd");
		return df.format(date);
	}
}
