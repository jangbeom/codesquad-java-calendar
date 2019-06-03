package honux.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

public class Calendar {

	private static final int[] MAX_DAYS = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final int[] LEAP_MAX_DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final String SAVE_FILE = "calendar.dat";
	private HashMap<Date, PlanItem> planMap;
	
	public Calendar(){
		planMap = new HashMap<Date, PlanItem>();
		File f = new File(SAVE_FILE);
		if (!f.exists()){
			System.err.println("no save file");
			return;
		}
			
		try {
			Scanner s = new Scanner(f);
			while(s.hasNext()){
				String line = s.nextLine();
				String[] words = line.split(",");
				String date = words[0];
				String detail = words[1].replaceAll("\"", "");
				System.out.println(date + ":" + detail);
				PlanItem p = new PlanItem(date, detail);
				planMap.put(p.getDate(), p);
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 
	 * @param date ex : "2019-05-29"
	 * @param plan
	 * @param ParseException
	 */
	public void registerPlan(String strDate, String plan){
		PlanItem p = new PlanItem(strDate, plan); 
		planMap.put(p.getDate(), p);
		
		File f = new File(SAVE_FILE);
		String item = p.saveString();
		try {
			FileWriter fw = new FileWriter(f,true);
			fw.write(item);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public PlanItem searchPlan(String strDate) {
		Date date = PlanItem.getDatefromString(strDate);
		return planMap.get(date);
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
		
		//cal.registerPlan("2019-05-29", "Let eat beef!!");
		//System.out.println(cal.searchPlan("2019-05-29").equals("Let eat beef!!"));
	}

}
