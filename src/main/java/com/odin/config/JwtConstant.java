package com.odin.config;

/**
 * Constants used for JWT configuration.
 * Contains secret key and header name values.
 */
public class JwtConstant {
    
    /**
     * Private constructor to prevent instantiation
     */
    private JwtConstant() {
        // Utility class, no instantiation
    }
    
    private static final String DEFAULT_SECRET = "defaultSecretKey123!@#$%^&*(defaultSecretKey123!@#$%^&*(";
    
    /**
     * Secret key used for JWT token signing.
     * Read from environment variable JWT_SECRET_KEY
     */
    public static final String SECRETE_KEY = System.getenv("JWT_SECRET_KEY") != null ? 
            System.getenv("JWT_SECRET_KEY") : DEFAULT_SECRET;

    /**
     * HTTP header name for JWT token
     */
    public static final String JWT_HEADER = "Authorization";
}
