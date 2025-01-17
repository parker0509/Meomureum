package com.example.demo.service.user;

import com.example.demo.dto.OAuth2Attribute;
import com.example.demo.dto.SessionUser;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
                Collections.emptySet(),
                oauth2Attribute.getAttributes(),
                oauth2Attribute.getNameAttributeKey()
        );
    }

    @Transactional
    public User saveOrUpdate(OAuth2Attribute oauth2Attribute) {

        Optional<User> existOpt = userRepository.findByEmail(oauth2Attribute.getEmail());


        if (existOpt.isPresent()) {
            User existingUser = existOpt.get();
            existingUser.update(existingUser.getName(), existingUser.getEmail());
            return userRepository.save(existingUser);
        } else {
            User newUser = oauth2Attribute.toEntity();
            System.out.println("newUser = " + newUser);
            return userRepository.save(newUser);
        }
    }
}


