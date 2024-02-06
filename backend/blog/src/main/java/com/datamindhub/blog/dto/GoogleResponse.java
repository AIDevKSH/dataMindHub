package com.datamindhub.blog.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleResponse implements OAuth2Response {

    private final Map<String, Object> attributeMap;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attributeMap.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attributeMap.get("email").toString();
    }

    @Override
    public String getName() {
        return attributeMap.get("name").toString();
    }
}
