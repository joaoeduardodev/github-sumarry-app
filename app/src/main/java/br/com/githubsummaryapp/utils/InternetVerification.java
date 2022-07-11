package br.com.githubsummaryapp.utils;

import java.io.IOException;
import java.net.InetAddress;

public class InternetVerification {
    public static Boolean isAvailable() {
        try {
            String command = "ping -c 1 google.com";
            return Runtime.getRuntime().exec(command).waitFor() == 0;
        } catch (Exception e) {
        return false;
        }
    }
}
