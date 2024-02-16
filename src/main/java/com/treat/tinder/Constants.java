package com.treat.tinder;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String PET_FINDER_BASE_URL = "https://api.petfinder.com/";
    public static final String GET_AUTH_TOKEN_URL = PET_FINDER_BASE_URL + "v2/oauth2/token";
    public static final String CLIENT_ID ="6tlDO7TgJj1fhW58hriLLfdh2pY9CwlFNEbrbLdOuaUwgDML5F";
    public static final String CLIENT_SECRET ="a4ZXI51HQsWh5KEu7xaoPWimtzLPjiZdRYSyFmQj";
    public static final String GRANT_TYPE ="client_credentials";

}
