package com.datamindhub.blog.dto;

import com.datamindhub.blog.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "이메일을 입력해주세요")
    @Size(max = 50, message = "50자 이내로 입력해주세요")
    @Pattern(regexp = "([\\w+.-]+@[\\w+-]+\\.[\\w+.-]+)*", message = "이메일 형식으로 입력해 주세요")  // 영문자, 숫자, 밑줄만 가능
    //@Email(message = "이메일 형식으로 입력해 주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(max = 20, message = "20자 이내로 입력해주세요")
    @Pattern(regexp = "[\\w!@#+=%^*-]*", message = "사용할 수 없는 문자가 포함되어 있습니다.")
    // 영문자, 일부 특수기호, 숫자, 밑줄만 가능
    private String password;

    @NotBlank(message = "이름를 입력해주세요")
    @Size(max = 30, message = "30자 이내로 입력해주세요")
    @Pattern(regexp = "[가-힣a-zA-Z]*", message = "사용할 수 없는 문자가 포함되어 있습니다.")  // 한글, 영문 이름만 가능
    private String userName;

    @Pattern(regexp = "([0-9]{8})*", message = "패턴에 맞게 입력해주세요")
    private String birthday;

    @NotBlank(message = "별명을 입력해주세요")
    @Size(max = 20, message = "20자 이내로 입력해주세요")
    private String nickname;

    @NotBlank(message = "핸드폰 번호를 입력해주세요")
    @Size(max = 15)
    private String phone;

    private Integer status;
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .birthday(birthday)
                .nickname(nickname)
                .phone(phone)
                .status(status)
                .userRoles(new HashSet<>())
                .build();
    }
}
