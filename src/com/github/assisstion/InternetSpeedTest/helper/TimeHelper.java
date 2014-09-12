package com.github.assisstion.InternetSpeedTest.helper;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeHelper{
	public static String formatSeconds(double seconds){
		return formatDuration(Duration.ofSeconds((long)seconds,
				Math.round((seconds - (int) seconds) * 10) * 100000000L), 1);
	}

	public static String formatDuration(Duration dur, int digits){
		long seconds = dur.getSeconds();
		//int nanos = dur.getNano();
		long hours = seconds / 3600;
		int minutes = (int) (seconds % 3600 / 60);
		int secs = (int) (seconds % 60);
		String s = "";
		if (hours != 0) {
			s += hours + ":";
		}
		s += fillZeros(minutes, 2) + ":" + fillZeros(secs, 2);
		//if(nanos > 0){
		//	s += "." + fillZeros((int) Math.round(nanos * 100000000L * Math.pow(10, digits)), digits);
		//}
		return s;
	}

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
		Instant time = Instant.ofEpochMilli(millis);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(time, ZoneId.systemDefault());
		return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public static String formatSystemTimeCompact(long millis){
		String s = formatSystemTime(millis);
		s = s.substring(5);
		s = s.split("\\.")[0];
		s = s.replace('-', '/');
		s = s.replace('T', ' ');
		return s;
	}
}
