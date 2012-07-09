package com.dongwang.common.cache;

import java.io.IOException;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

public class CacheClient {
	private static MemcachedClient memCachedClient;
	private String addr;

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAddr() {
		return addr;
	}

	public MemcachedClient memcachedClient() throws IOException {
		return new MemcachedClient(AddrUtil.getAddresses(getAddr()));
	}

	public void init() throws Exception {
		memCachedClient = memcachedClient();
	}

	public static void set(String key, Object value, int seconds) {
		memCachedClient.set(key, seconds, value);
	}

	public static void set(String key, Object value) {
		memCachedClient.set(key, 0, value);
	}

	public static Object get(String key) {
		return memCachedClient.get(key);
	}

	public static void remove(String key) {
		memCachedClient.delete(key);
	}

	public static void setExpiry1Day(String key, Object value) {
		set(key, value, 24 * 60 * 60);
	}
	
	public static void setExpiry5Minutes(String key, Object value) {
		set(key, value, 5*60);
	}
	/*
	 * String schoolKey = String.format(schoolkey2, sxwname); List<SchoolInfo>
	 * result = (List<SchoolInfo>) CacheClient.get(schoolKey); if (result ==
	 * null) { result =this.queryForList("findSchoolInfoBySxwName", sxwname);
	 * CacheClient.set(schoolkey2, result); } return result;
	 */
	public static boolean exist(String key) {
		Object o = get(key);
		if (o == null) {
			set(key, 1, 3);
			return false;
		} else {
			return true;
		}
	}
	public static void inc(String key,int by){
		Object o = get(key);
		int count=0;
		if(o!=null){
			count=(Integer)o;
			count=count+by;
			set(key,count);
		}
	}
}
