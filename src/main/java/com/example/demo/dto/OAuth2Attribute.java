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
    public OAuth2Attribute(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String gender, String age) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.gender = gender != null ? gender : "N/A";
        this.age = age != null ? age : "N/A";
    }


    public static OAuth2Attribute of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {

        if ("google".equals(registrationId)) {
            System.out.println("Google Select" + registrationId);
            return ofGoogle(userNameAttributeName, attributes);
        }
        if ("kakao".equals(registrationId)) {
            System.out.println("Kakao Select" + registrationId);
            return ofKakao("kakao_account", attributes);
        }
        if ("naver".equals(registrationId)) {
            System.out.println("Naver Select" + registrationId);
            return ofNaver("id", attributes);
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

    private static OAuth2Attribute ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .gender((String) response.get("gender"))
                .age((String) response.get("age"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    private static OAuth2Attribute ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        // 카카오 응답 데이터의 구조를 확인합니다.
        System.out.println("Kakao attributes: " + attributes);

        // 카카오 응답에서 kakao_account를 가져옵니다.
        Object kakaoAccountObj = attributes.get("kakao_account");

        if (!(kakaoAccountObj instanceof Map)) {
            throw new OAuth2AuthenticationException("Expected 'kakao_account' to be a Map, but got: " + kakaoAccountObj.getClass());
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoAccountObj;

        // 이메일과 프로필 정보를 안전하게 추출
        String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;

        // 카카오 프로필 정보에서 nickname 추출
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname  = profile != null ? (String) profile.get("nickname") : null;

        // OAuthAttributes 객체 생성 후 반환
        return OAuth2Attribute.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .email(email)
                .name(nickname)  // nickname을 이름으로 설정
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
