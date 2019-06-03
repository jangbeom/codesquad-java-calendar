package honux.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Calendar {

	private static final int[] MAX_DAYS = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final int[] LEAP_MAX_DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private HashMap<Date, String> planMap;
	
	public Calendar(){
		planMap = new HashMap<Date, String>();
	}
	
	/**
	 * 
	 * @param date ex : "2019-05-29"
	 * @param plan
	 * @param ParseException
	 */
	public void registerPlan(String strData, String plan) throws ParseException{

		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strData);
//		System.out.println(date);
		planMap.put(date, plan);
	}
	
	public String searchPlan(String strDate) throws ParseException{
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
		String plan = planMap.get(date);
		return plan;
	}
	
	public boolean isLeapYear(int year) {
		if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
			return true;
		else
			return false;
	}

	public int MaxDaysofMonth(int year, int month) {
		if (isLeapYear(year)) {
			return LEAP_MAX_DAYS[month];
		} else {
			return MAX_DAYS[month];
		}
	}

	public void printCalendar(int year, int month) {
		System.out.printf("      <<%d년%d월>>\n", year, month);
		System.out.println(" SU MO TU WE TH FR SA");
		System.out.println("---------------------");

		int weekday = getWeekday(year, month, 1);

		// print blank space
		for (int i = 0; i < weekday; i++) {
			System.out.print("   ");
		}

		int maxDay = MaxDaysofMonth(year, month);
		int count = 7 - weekday;
		int delim = (count < 7) ? count : 0;
		// print first line
		for (int i = 1; i <= count; i++) {
			System.out.printf("%3d", i);
		}
		System.out.println();

		count++;
		// print from second line to last
		for (int i = count; i <= maxDay; i++) {
			System.out.printf("%3d", i);
			if (i % 7 == delim) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.println();
	}

	private int getWeekday(int year, int month, int day) {
		int syear = 1970;
		final int Standard_Weekday = 4; // 1970/Jan/1st = Thursday

		int count = 0;

		for (int i = syear; i < year; i++) {
			int dalta = isLeapYear(i) ? 366 : 365;
			count += dalta;
		}

		for (int i = 1; i < month; i++) {
			int delta = MaxDaysofMonth(year, i);
			count += delta;
		}

		count += day - 1;

		int weekday = (count + Standard_Weekday) % 7;
		return weekday;
	}

	public static void main(String[] args) throws ParseException {
		Calendar cal = new Calendar();
		System.out.println(cal.getWeekday(1970, 1, 1) == 4);
		System.out.println(cal.getWeekday(1971, 1, 1) == 5);
		System.out.println(cal.getWeekday(1972, 1, 1) == 6);
		System.out.println(cal.getWeekday(1973, 1, 1) == 1);
		System.out.println(cal.getWeekday(1974, 1, 1) == 2);
		
		cal.registerPlan("2019-05-29", "Let eat beef!!");
		System.out.println(cal.searchPlan("2019-05-29").equals("Let eat beef!!"));
	}

}
