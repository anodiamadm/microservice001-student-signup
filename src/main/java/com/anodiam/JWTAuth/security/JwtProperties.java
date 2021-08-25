package com.anodiam.JWTAuth.security;

public class JwtProperties {

    private String SECRET;
    private Integer EXPIRATION_TIME;
    private String TOKEN_PREFIX;
    private String HEADER_STRING;

    public JwtProperties(String SECRET, Integer EXPIRATION_TIME, String TOKEN_PREFIX, String HEADER_STRING) {
        this.SECRET = SECRET;
        this.EXPIRATION_TIME = EXPIRATION_TIME;
        this.TOKEN_PREFIX = TOKEN_PREFIX;
        this.HEADER_STRING = HEADER_STRING;
    }

    public String getSECRET() {
        return SECRET;
    }

    public Integer getEXPIRATION_TIME() {
        return EXPIRATION_TIME;
    }

    public String getTOKEN_PREFIX() {
        return TOKEN_PREFIX;
    }

    public String getHEADER_STRING() {
        return HEADER_STRING;
    }
}
