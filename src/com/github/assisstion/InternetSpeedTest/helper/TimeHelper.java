package com.github.assisstion.InternetSpeedTest.helper;

import java.text.DateFormat;
import java.util.Date;

/*
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
 */
public class TimeHelper{

	public static String formatSeconds(double seconds){
		/*
		return formatDuration(Duration.ofSeconds((long) seconds,
				Math.round((seconds - (int) seconds) * 10) * 100000000L));// ,
																			// 1);
		 */
		int i = (int) seconds;
		int s = i % 60;
		int m = i / 60;
		int min = m % 60;
		int h = m / 60;
		return (h > 0 ? fillZeros(h, 2) + ":" : "") +
				(m > 0 || h > 0 ? fillZeros(min, 2) + ":" : "") +
				fillZeros(s, 2) + "." +
				(seconds % 1 != 0 ? String.valueOf(seconds % 1).substring(2, 3) : "0");
	}

	/*
	public static String formatDuration(Duration dur){// , int digits){
		long seconds = dur.getSeconds();
		// int nanos = dur.getNano();
		long hours = seconds / 3600;
		int minutes = (int) (seconds % 3600 / 60);
		int secs = (int) (seconds % 60);
		String s = "";
		if(hours != 0){
			s += hours + ":";
		}
		s += fillZeros(minutes, 2) + ":" + fillZeros(secs, 2);
		// if(nanos > 0){
		// s += "." + fillZeros((int) Math.round(nanos * 100000000L *
		// Math.pow(10, digits)), digits);
		// }
		return s;
	}
	 */
	private static String fillZeros(int number, int amount){

		String s = "";
		if(number == 0){
			for(int i = 0; i < amount - 1; i++){
				s += "0";
			}
		}
		else{
			for(int i = number; i < Math.pow(10, amount - 1); i *= 10){
				s += "0";
			}
		}
		return s + number;
	}


	public static String formatSystemTime(long millis){
		Date date = new Date(millis);
		return DateFormat.getInstance().format(date);
		/*
		Instant time = Instant.ofEpochMilli(millis);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(time,
				ZoneId.systemDefault());
		return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
		 */
	}


	public static String formatSystemTimeCompact(long millis){
		return formatSystemTime(millis);
		/*
		String s = formatSystemTime(millis);
		s = s.substring(5);
		s = s.split("\\.")[0];
		s = s.replace('-', '/');
		s = s.replace('T', ' ');
		return s;
		 */
	}

}
