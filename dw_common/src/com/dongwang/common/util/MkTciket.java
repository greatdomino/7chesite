package com.dongwang.common.util;

import java.text.SimpleDateFormat;

public class MkTciket {
	public static String createTicket(String key,String account) {
		DESPlus des = new DESPlus(key);
		return des.encrypt(account);
	}
	public static String getDwkey(){
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddhh"); 
		String datetime = tempDate.format(new java.util.Date()); 
		return datetime;
	}
}
