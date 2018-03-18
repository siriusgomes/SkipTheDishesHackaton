package br.hackathon.skipthedishes.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Encryption {
	
	public static String getSHA1(String pass) {
		return DigestUtils.sha1Hex(pass);
	}
	
	
}
