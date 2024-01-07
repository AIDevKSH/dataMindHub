package com.datamindhub.blog.service;

import com.datamindhub.blog.domain.Role;
import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.domain.UserRole;
import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.repository.AuthorityRepository;
import com.datamindhub.blog.repository.RoleRepository;
import com.datamindhub.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserRequestDto userDto) throws DataIntegrityViolationException {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new DataIntegrityViolationException("중복 아이디 발견");
        }

        // 계정 설정
        userDto.setStatus(1);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userDto.toEntity();
        //User user = userRepository.save(userDto.toEntity());

//        // 해당 유저에게 부여할 권한 찾아옴
//        Set<Authority> authorityList = new HashSet<>();  // Authority 객체를 담을 리스트
//        authorityList.add(authorityRepository.findByName(AuthorityEnum.READ.toString()));  // 읽기 권한
//        authorityList.add(authorityRepository.findByName(AuthorityEnum.CREATE.toString()));  // 쓰기 권한

        // 역할 설정
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(roleRepository.findByName("user"))
                .build();
        user.getUserRoles().add(userRole);

//        // user_authorities 테이블에 저장할 객체 리스트를 만듦
//        Set<UserAuthority> userAuthorityList = new ArrayList<>();  // 새로 생성할 UserAuthority 객체를 담을 리스트
//        // 새 UserAuthority 객체를 만들고 리스트에 저장
//        authorityList.forEach(auth -> userAuthorityList.add(UserAuthority.builder()
//                                                                        .user(user)
//                                                                        .authority(auth)
//                                                                        .build()));
//        user.setUserAuthorities(userAuthorityList);
        userRepository.save(user);  // cascade를 이용해서 user_authorities 테이블에도 저장
    }

    public boolean checkIsDuplicatedUserEmail(String Email) {
        if (userRepository.findByEmail(Email) != null) {
            return true;
        }
        else return false;
    }
}
