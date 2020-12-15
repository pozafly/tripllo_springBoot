package com.pozafly.tripllo.common.domain.network;

public class ResponseMessage {

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "가입하지 않은 ID 입니다.";
    public static final String PASSWORD_WRONG = "잘못된 비밀번호 입니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String POSSIBLE_CREATE = "회원가입 가능한 ID 입니다.";
    public static final String IMPOSSIBLE_CREATE = "회원가입 불가능한 ID 입니다.";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String ALREADY_USE = "이미 회원 ID가 사용되고 있습니다.";

}