package com.odin.util;

import java.util.Random;

/**
 * Utility class for generating OTPs.
 */
public class OtpUtils
{
    /**
     * Default constructor.
     */
    public OtpUtils() {
    }

    /**
     * Generates a random OTP.
     *
     * @return The generated OTP as a string.
     */
    public static String generateOtp()
    {
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++)
        {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

}
