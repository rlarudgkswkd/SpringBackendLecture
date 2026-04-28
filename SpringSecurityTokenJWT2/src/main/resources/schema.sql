-- --------------------------------------------------------
-- db 생성 및 유저 권한 할당 (필요하면 실행)
-- --------------------------------------------------------
-- 1. 유저 생성 (root 계정인 postgres로 진행)
-- CREATE USER ohgiraffers PASSWORD 'ohgiraffers' CREATEDB;

-- 2. 데이터베이스 생성
-- CREATE DATABASE ohgi_restaurant
--     WITH
--     OWNER = ohgiraffers
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'en_US.utf8'
--     LC_CTYPE = 'en_US.utf8'
--     TEMPLATE template0;

-- 3. 스키마 생성 (ohgiraffers 계정으로 진행)
-- CREATE SCHEMA IF NOT EXISTS ohgi_restaurant;

-- 4. 권한 할당
-- GRANT ALL PRIVILEGES ON SCHEMA ohgi_restaurant TO ohgiraffers;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA ohgi_restaurant TO ohgiraffers;

-- 5. 검색 경로 설정
-- ALTER ROLE ohgiraffers SET search_path TO ohgi_restaurant;
-- SHOW search_path;


-- --------------------------------------------------------
-- PostgreSQL 스키마 정의 (Spring Security Session Based)
-- --------------------------------------------------------

-- 사용자 테이블
DROP TABLE IF EXISTS tbl_user CASCADE;
CREATE TABLE tbl_user (
                          user_id   SERIAL PRIMARY KEY,
                          username  VARCHAR(50) UNIQUE NOT NULL,
                          email     VARCHAR(100) UNIQUE NOT NULL,
                          password  VARCHAR(60) NOT NULL,
                          role      VARCHAR(20) NOT NULL DEFAULT 'USER'
);

-- JWT 토큰 상태 테이블
DROP TABLE IF EXISTS tbl_jwt_token CASCADE;
CREATE TABLE IF NOT EXISTS tbl_jwt_token (
    jti         VARCHAR(64) PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    token_type  VARCHAR(16)  NOT NULL CHECK (token_type IN ('access', 'refresh')),
    issued_at   TIMESTAMPTZ  NOT NULL,
    expires_at  TIMESTAMPTZ  NOT NULL,
    revoked     BOOLEAN      NOT NULL DEFAULT FALSE,
    replaced_by VARCHAR(64)
);

-- 카테고리 테이블
DROP TABLE IF EXISTS tbl_category CASCADE;
CREATE TABLE tbl_category (
                              category_code SERIAL PRIMARY KEY,
                              category_name VARCHAR(255) NOT NULL
);

-- 메뉴 테이블
DROP TABLE IF EXISTS tbl_menu CASCADE;
CREATE TABLE tbl_menu (
                          menu_code SERIAL PRIMARY KEY,
                          menu_name VARCHAR(255) NOT NULL,
                          menu_price INTEGER NOT NULL,
                          menu_description TEXT,
                          menu_orderable CHAR(1) NOT NULL DEFAULT 'Y',
                          category_code INTEGER REFERENCES tbl_category(category_code),
                          menu_image_url VARCHAR(255),
                          menu_stock INTEGER NOT NULL DEFAULT 0
);

-- 테이블 컬럼 코멘트 추가
COMMENT ON COLUMN tbl_user.user_id IS '사용자 고유 ID';
COMMENT ON COLUMN tbl_user.username IS '사용자명 (로그인 ID)';
COMMENT ON COLUMN tbl_user.email IS '이메일 주소';
COMMENT ON COLUMN tbl_user.password IS 'BCrypt 암호화된 비밀번호';
COMMENT ON COLUMN tbl_user.role IS '사용자 권한 (ADMIN, USER)';

-- JWT 토큰 테이블 인덱스 생성
-- username 인덱스: 특정 사용자의 토큰을 조회할 때 성능 향상을 위해 생성
CREATE INDEX IF NOT EXISTS idx_tbl_jwt_token_username ON tbl_jwt_token(username);
-- expires_at 인덱스: 만료된 토큰을 정리하거나 유효한 토큰을 조회할 때 성능 향상을 위해 생성
CREATE INDEX IF NOT EXISTS idx_tbl_jwt_token_expires  ON tbl_jwt_token(expires_at);