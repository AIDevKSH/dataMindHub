package com.datamindhub.blog.dto;

public interface OAuth2Response {
    String getProvider();  // 제공자 이름 e.g. naver
    String getProviderId();  // 제공자에서 부여한 사용자의 번호
    String getEmail();  // 사용자 이메일
    String getName();  // 사용자 이름
}
