package com.datamindhub.blog.service;

import com.datamindhub.blog.domain.*;
import com.datamindhub.blog.dto.*;
import com.datamindhub.blog.repository.RoleRepository;
import com.datamindhub.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response response = null;

        switch (registrationId) {
            case "naver":
                response = new NaverResponse(oAuth2User.getAttributes());
                break;
            case "kakao" :
                response = new KakaoResponse(oAuth2User.getAttributes()); // 카카오는 권한이 없어서 이름 대신 닉네임으로 설정
                break;
            case "google":
                response = new GoogleResponse(oAuth2User.getAttributes());
        }

        Role foundRole = roleRepository.findByName(RoleEnum.ROLE_USER.toString()).orElseThrow();
        Optional<User> foundUser = userRepository.findByProviderId(response.getProviderId());
        User user;

        // 신규 회원일 때
        if (foundUser.isEmpty()) {
            String oAuth2UserId = UUID.randomUUID().toString() + "@" + response.getProvider().charAt(0);

            user = User.builder()
                    .email(oAuth2UserId)
                    .userName(response.getName())
                    .status(1)
                    .providerId(response.getProviderId())
                    .build();

            // 유저역할 정보 저장
            user.setUserRoles(UserRole.builder()
                    .user(user)
                    .role(foundRole)
                    .build()
            );
            userRepository.save(user);  // 유저 정보 저장
        } 
        // 기존 회원일 때
        else {
            user = foundUser.get();
            user.setUserName(response.getName());
        }

        return new CustomOAuth2User(response, user.getUserRoles().getRole());
    }
}
