package honux.calendar;

import java.util.Scanner;

public class Prompt {

	private final static String PROMPT = "cal> ";
	
	public void runPrompt(){

		Scanner scanner = new Scanner(System.in);
		Calendar cal = new Calendar();

		int month = -1;
		int year = -1;
		while (true) {
			System.out.println("연도를 입력하세요. ");
			System.out.print("YEAR> ");
			year = scanner.nextInt();
			System.out.println("달을 입력하세요. ");
			System.out.print("MONTH> ");
			month = scanner.nextInt();
			if (month == -1) {
				break;
			}
			if (month > 12) {
				continue;
			}
//			System.out.println(" 일   월   화   수   목   금   토 ");
//			System.out.println("--------------------");
//			System.out.println(" 1  2  3  4  5  6  7");
//			System.out.println(" 8  9 10 11 12 13 14");
//			System.out.println("15 16 17 18 19 20 21");
//			System.out.println("22 23 24 25 26 27 28");
//			if (cal.MaxDaysofMonth(month) == 30) {
//				System.out.println("29 30");
//			} else if (cal.MaxDaysofMonth(month) == 31) {
//				System.out.println("29 30 31");
//			}
			
			cal.printCalendar(year, month);			
		}
		System.out.println("Bye~ ");

		scanner.close();
		
	}

	public static void main(String[] args) {
	
		Prompt p = new Prompt();
		p.runPrompt();
		
	}
}