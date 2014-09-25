package com.github.assisstion.InternetSpeedTest;

import java.util.List;

public interface ListHolder<T, S extends List<T>>{
	S getList();
}
