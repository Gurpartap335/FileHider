package service;

import java.util.Random;

public class OTPService {
    public static String getOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}