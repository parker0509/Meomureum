package com.example.demo.service.user;

import com.example.demo.dto.OAuth2Attribute;
import com.example.demo.dto.SessionUser;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2Attribute oauth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // null 시 오류 발생
        if (oauth2Attribute == null) {
            throw new OAuth2AuthenticationException("OAuthAttribute is null for registrationID: " + registrationId);
        }


        User user = saveOrUpdate(oauth2Attribute);

        httpSession.setAttribute("user",new SessionUser(user));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oauth2Attribute.getAttributes(),
                oauth2Attribute.getNameAttributeKey()
        );
    }

    @Transactional
    public User saveOrUpdate(OAuth2Attribute oauth2Attribute) {

        Optional<User> existOpt = userRepository.findByEmail(oauth2Attribute.getEmail());

        if (existOpt.isPresent()) {
            User existingUser = existOpt.get();
            String existingUserName = existingUser.getName();

            // 기존 사용자의 이름이 null이거나, 다른 이름일 경우 업데이트 처리
            if (existingUserName == null || !existingUserName.equals(oauth2Attribute.getName())) {
                existingUser.update(oauth2Attribute.getName() != null ? oauth2Attribute.getName() : "Unknown", oauth2Attribute.getEmail());
                return userRepository.save(existingUser);
            }

            return existingUser;
        } else {
            // 새로운 사용자가 없으면 새로운 사용자 생성
            User newUser = oauth2Attribute.toEntity();
            return userRepository.save(newUser);
        }
    }
}


