# 실행하기 전에 환경 변수 먼저 설정할 것! - DB_URL, DB_USER_NAME, DB_USER_PASSWORD

# 유저 생성
CREATE USER IF NOT EXISTS '계정명'@'localhost' IDENTIFIED BY '비밀번호';
GRANT ALL PRIVILEGES ON *.* TO '계정명'@'localhost';
FLUSH PRIVILEGES;

# DB 생성
CREATE DATABASE IF NOT EXISTS blog;
USE blog;

# Table 생성
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,  # 이메일은 @ . 포함 최대 254자 - RFC 5321
    password VARCHAR(255) NOT NULL,  # 비밀번호
    user_name VARCHAR(30), # 이름
    nickname VARCHAR(20), # 별명
    birthday VARCHAR(8), # 생년월일
    phone VARCHAR(15),  # 전화번호 / E164
    profile_image TEXT,  # 프로필 이미지
    status TINYINT NOT NULL,  # 계정 상태
    updated_at TIMESTAMP,  # 수정한 시간
    created_at TIMESTAMP  # 가입한 시간
);

CREATE TABLE IF NOT EXISTS AUTHORITIES (
                                           id TINYINT PRIMARY KEY AUTO_INCREMENT,
                                           authority VARCHAR(50) NOT NULL # 권한명
);

CREATE TABLE IF NOT EXISTS USER_AUTHORITIES (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,  # 회원 id (로그인 id 아님)
    authority_id TINYINT NOT NULL, # 권한 id
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (authority_id) REFERENCES AUTHORITIES(id)
);

# 기본 권한 설정
INSERT INTO AUTHORITIES(authority)
SELECT 'read'
WHERE NOT EXISTS (SELECT 1 FROM AUTHORITIES WHERE authority = 'read');

INSERT INTO AUTHORITIES(authority)
SELECT 'write'
WHERE NOT EXISTS (SELECT 1 FROM AUTHORITIES WHERE authority = 'write');
