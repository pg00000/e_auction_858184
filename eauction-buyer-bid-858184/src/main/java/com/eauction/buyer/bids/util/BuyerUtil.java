package com.eauction.buyer.bids.util;

import java.util.regex.Pattern;

public final class BuyerUtil {
	private BuyerUtil() {
	}

	public static boolean isValidLength(String value, int minLength, int maxLength) {
		if (value == null) {
			return false;
		}

		int length = value.length();
		return length >= minLength && length <= maxLength;
	}
	
    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^(.+)@(.+)$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
