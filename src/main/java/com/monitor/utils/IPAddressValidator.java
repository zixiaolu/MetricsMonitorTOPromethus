package com.monitor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressValidator {

    private static final String IP_PORT_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5]):" +
                    "(\\d{1,5})$";

    public static boolean isValidIPPort(String ipPort) {
        Pattern pattern = Pattern.compile(IP_PORT_PATTERN);
        Matcher matcher = pattern.matcher(ipPort);
        return matcher.matches();
    }
}
