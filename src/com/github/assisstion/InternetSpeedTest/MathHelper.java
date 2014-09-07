package com.github.assisstion.InternetSpeedTest;

import java.text.DecimalFormat;

public class MathHelper{


	public static double roundThreeDecimals(double d){
		if(Double.isInfinite(d) || Double.isNaN(d)){
			return d;
		}
		DecimalFormat threeDForm = new DecimalFormat("#.###");
		return Double.valueOf(threeDForm.format(d));
	}
}
