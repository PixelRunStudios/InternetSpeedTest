package com.github.assisstion.InternetSpeedTest.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeHelper{
	public static String formatSeconds(double seconds){
		int hours = (int)(seconds / 3600);
		double minuteSeconds = seconds - hours * 3600;
		int minutes = (int)(minuteSeconds / 60);
		double secondSeconds = minuteSeconds - minutes * 60;
		return String.format("%1:%2:%3", hours, minutes, secondSeconds);
	}

	public static String formatSystemTime(long millis){
		Instant time = Instant.ofEpochMilli(millis);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(time, ZoneId.systemDefault());
		return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}
}
