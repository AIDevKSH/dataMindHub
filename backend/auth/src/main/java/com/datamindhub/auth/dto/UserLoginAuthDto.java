package com.datamindhub.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginAuthDto {
    String email;
    String password;
}
