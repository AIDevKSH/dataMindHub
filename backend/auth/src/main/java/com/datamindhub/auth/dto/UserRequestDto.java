package com.datamindhub.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    String email;
    String password;
}
