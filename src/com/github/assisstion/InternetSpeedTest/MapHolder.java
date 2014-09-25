package com.github.assisstion.InternetSpeedTest;

import java.util.Map;

public interface MapHolder<T, S, R extends Map<T, S>>{
	R getMap();
}
