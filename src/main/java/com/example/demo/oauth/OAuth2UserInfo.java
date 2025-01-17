package com.example.demo.oauth;


public interface OAuth2UserInfo {

    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

}
