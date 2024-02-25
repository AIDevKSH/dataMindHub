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
    email VARCHAR(254) NOT NULL,  # 이메일은 @ . 포함 최대 254자 - RFC 5321
    password VARCHAR(255) NOT NULL,  # 비밀번호
    user_name VARCHAR(30), # 이름
    nickname VARCHAR(50), # 별명
    birthday VARCHAR(8), # 생년월일
    phone VARCHAR(15),  # 전화번호 / E164
    profile_image TEXT,  # 프로필 이미지
    status TINYINT NOT NULL,  # 계정 상태
    provider_id VARCHAR(255),  # OAuth2 인증 서버 별 고유 ID
    updated_at TIMESTAMP,  # 수정한 시간
    created_at TIMESTAMP  # 가입한 시간
);

CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL # 역할명
);

CREATE TABLE IF NOT EXISTS user_roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,  # 회원 id (로그인 id 아님)
    role_id INTEGER NOT NULL, # 역할 id
    updated_at TIMESTAMP,  # 수정한 시간
    created_at TIMESTAMP,  # 생성한 시간
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
    # PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS authorities (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL # 권한명
);

CREATE TABLE IF NOT EXISTS role_authorities (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    role_id INTEGER NOT NULL,  # 역할 id
    authority_id INTEGER NOT NULL, # 권한 id
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (authority_id) REFERENCES authorities(id)
);

CREATE TABLE IF NOT EXISTS posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    body TEXT NOT NULL,
    status TINYINT,
    views INTEGER,
    updated_at TIMESTAMP,
    created_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER DATABASE blog
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;

ALTER TABLE users
    CONVERT TO CHARACTER SET utf8mb4
        COLLATE utf8mb4_unicode_ci;