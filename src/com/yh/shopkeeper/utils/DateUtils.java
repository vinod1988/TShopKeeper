package com.yh.shopkeeper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static final String DATE_FMT = "yyyy-MM-dd";
	private static final SimpleDateFormat FORMATER = new SimpleDateFormat(
			DATE_FMT);

	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return new Date(calendar.get(Calendar.YEAR) - 1900,
				calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
	}

	public static String getCurrentDateString() {
		return FORMATER.format(getCurrentDate());
	}

	public static Date getDateWithInterval(int field, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		calendar.add(field, interval);
		return calendar.getTime();
	}

	public static int compare(Date first, Date second) {
		return first.compareTo(second);
	}

	public static String format(Date date) {
		return FORMATER.format(date);
	}

	public static String format(String fmt, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	public static Date parse(String date) {
		try {
			return FORMATER.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parse(String fmt, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
