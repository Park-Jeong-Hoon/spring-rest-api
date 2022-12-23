package com.example.restapi.jwt;

public interface JwtProperties {
    String SECRET = "asdKlja124o8qrchOcawl8fn4Ruqoh328hr2h3uwerhl21qil8Leu93reWalh23oI157p0w843u3qkeAfoirowafiowQpurwknz";
    int EXPIRATION_TIME = 60000; // 10일 (1/1000초)
    String AUTH_TYPE = "Bearer ";
    String HEADER_STRING = "Authorization";
}
