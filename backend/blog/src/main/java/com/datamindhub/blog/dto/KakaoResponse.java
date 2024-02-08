package com.datamindhub.blog.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {
    private final Map<String, Object> attributeMap;
    private final Map<String, Object> kakaoAccountMap;
    private final Map<String, Object> profileMap;

    public KakaoResponse(Map<String, Object> attributeMap) {
        this.attributeMap = attributeMap;
        this.kakaoAccountMap = (Map<String, Object>) attributeMap.get("kakao_account");
        this.profileMap = (Map<String, Object>) kakaoAccountMap.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributeMap.get("id").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccountMap.get("email").toString();
    }

    @Override
    public String getName() {
        return profileMap.get("nickname").toString();
    }
}
