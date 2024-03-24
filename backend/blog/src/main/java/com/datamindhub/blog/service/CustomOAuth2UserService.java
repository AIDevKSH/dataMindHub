package com.datamindhub.blog.service;

import com.datamindhub.blog.domain.user.*;
import com.datamindhub.blog.dto.*;
import com.datamindhub.blog.repository.user.RoleRepository;
import com.datamindhub.blog.repository.user.UserRepository;
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
        OAuth2Response response = switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "kakao" -> new KakaoResponse(oAuth2User.getAttributes()); // 카카오는 권한이 없어서 이름 대신 닉네임으로 설정
            case "google" -> new GoogleResponse(oAuth2User.getAttributes());
            default -> null;
        };

        if (response == null) throw new OAuth2AuthenticationException("로그인 실패");
        Optional<User> foundUser = userRepository.findByProviderIdWithRole(response.getProviderId());
        User user;

        // 신규 회원일 때
        if (foundUser.isEmpty()) {
            Role foundRole = roleRepository.findByRoleEnum(RoleEnum.ROLE_USER).orElseThrow();
            String oAuth2UserId = UUID.randomUUID().toString() + "@" + response.getProvider().charAt(0);

            user = User.builder()
                    .email(oAuth2UserId)
                    .userName(response.getName())
                    .status(1)
                    .providerId(response.getProviderId())
                    .build();

            // 유저역할 정보 저장
            user.setUserRole(UserRole.builder()
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
        RoleEnum role = user.getUserRole().getRole().getName();

        return new CustomOAuth2User(response, role);
    }
}
