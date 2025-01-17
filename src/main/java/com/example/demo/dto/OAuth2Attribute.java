package com.example.demo.dto;


import com.example.demo.entity.User;
import lombok.Builder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String gender;
    private final String email;
    private final String age;

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes, String nameAttributeKey,
                           String name, String gender, String email, String age) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.age = age;
    }


    public static OAuth2Attribute of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {

        if ("google".equals(registrationId)) {
            System.out.println("Google Select" + registrationId);
            return ofGoogle(userNameAttributeName, attributes);
        }
        if ("kakao".equals(registrationId)) {
            System.out.println("Kakao Select" + registrationId);
            return ofKakao(userNameAttributeName, attributes);
        }
        if ("naver".equals(registrationId)) {
            System.out.println("Naver Select" + registrationId);
            return ofNaver(userNameAttributeName, attributes);
        }
/*
            if ("facebook".equals(registrationId)) {
            System.out.println("Google Select" + registrationId);
            return ofFacebook(userNameAttributeName, attributes);
        }
 */
        throw new OAuth2AuthenticationException("Unsupported OAuth provider: " + registrationId);
    }

    public static OAuth2Attribute ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {

        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .age((String) attributes.get("age"))
                .email((String) attributes.get("email"))
                .gender((String) attributes.get("gender"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuth2Attribute ofNaver(String userNameAttributeName,
                                          Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) response.get("name"))
                .age((String) response.get("age"))
                .email((String) response.get("email"))
                .gender((String) response.get("gender"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuth2Attribute ofKakao(String userNameAttributeName,
                                          Map<String, Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2Attribute.builder()
                .name((String) kakaoAccount.get("name"))
                .age((String) kakaoAccount.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .gender((String) kakaoAccount.get("gender"))
                .attributes(kakaoAccount)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }

   /*
    Facebook API Develop Need ++
    public static OAuth2Attribute ofFacebook() {
    }*/


    //getter;

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getNameAttributeKey() {
        return nameAttributeKey;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }
}
