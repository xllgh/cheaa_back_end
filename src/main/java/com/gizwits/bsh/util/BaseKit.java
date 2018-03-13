package com.gizwits.bsh.util;

import org.apache.commons.codec.binary.Base64;

public class BaseKit{

	public final static String encodeBase(String s){
		String encodedString  = "";
		encodedString = new String(Base64.encodeBase64String(s.getBytes()));
		if (encodedString.length() ==44){
			encodedString = encodedString.substring(0,encodedString.length()-2);
		}else{
			encodedString = encodedString.substring(0,encodedString.length()-1);
		}
		return encodedString;
}

	public final static String decodeBase(String s){
		String decodedString = "";
		decodedString = new String(Base64.decodeBase64(s));
		return decodedString;
	}

}
