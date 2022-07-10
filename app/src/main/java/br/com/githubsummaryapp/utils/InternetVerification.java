package br.com.githubsummaryapp.utils;

import java.net.InetAddress;

public class InternetVerification {
    public static boolean isAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
