package com.pozafly.tripllo.common.domain.network;

public class ResponseMessage {

    // 공통 메세지
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";

    // 회원 메세지
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "가입하지 않은 ID 입니다.";
    public static final String PASSWORD_WRONG = "비밀번호가 일치하지 않습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String POSSIBLE_CREATE = "회원가입 가능한 ID 입니다.";
    public static final String ALREADY_USE = "이미 회원 ID가 사용되고 있습니다.";

    // 보드 메세지
    public static final String READ_BOARD = "보드 리스트 조회 성공";
    public static final String NOT_FOUND_BOARD = "보드 목록이 없습니다.";
    public static final String READ_BOARD_DETAIL = "상세 보드 리스트 조회 성공";
    public static final String CREATED_BOARD = "보드 생성 성공";
    public static final String UPDATE_BOARD = "보드 정보 수정 성공";
    public static final String DELETE_BOARD = "보드 삭제 성공";

    // 리스트 메세지
    public static final String CREATED_LIST = "리스트 생성 성공";
    public static final String UPDATE_LIST = "리스트 정보 수정 성공";
    public static final String DELETE_LIST = "리스트 삭제 성공";
    public static final String NOT_FOUND_LIST = "해당 리스트가 없습니다.";

    // 카드 메세지
    public static final String CREATED_CARD = "카드 생성 성공";
    public static final String READ_CARD = "카드 조회 성공";
    public static final String UPDATE_CARD = "카드 정보 수정 성공";
    public static final String DELETE_CARD = "카드 삭제 성공";
    public static final String NOT_FOUND_CARD = "해당 카드가 없습니다.";

    // 체크리스트 메세지
    public static final String CREATED_CHECKLIST = "체크리스트 생성 성공";
    public static final String READ_CHECKLIST = "체크리스트 조회 성공";
    public static final String UPDATE_CHECKLIST = "체크리스트 정보 수정 성공";
    public static final String DELETE_CHECKLIST = "체크리스트 삭제 성공";
    public static final String NOT_FOUND_CHECKLIST = "해당 체크리스트가 없습니다.";

    // 체크리스트 아이템 메세지
    public static final String CREATED_CHECKLIST_ITEM = "체크리스트 생성 성공";
    public static final String UPDATE_CHECKLIST_ITEM = "체크리스트 정보 수정 성공";
    public static final String DELETE_CHECKLIST_ITEM = "체크리스트 삭제 성공";
    public static final String NOT_FOUND_CHECKLIST_ITEM = "해당 체크리스트가 없습니다.";

    // 코멘트 메세지
    public static final String CREATED_COMMENT = "코멘트 생성 성공";
    public static final String READ_COMMENT = "코멘트 조회 성공";
    public static final String UPDATE_COMMENT = "코멘트 정보 수정 성공";
    public static final String DELETE_COMMENT = "코멘트 삭제 성공";
    public static final String NOT_FOUND_COMMENT = "해당 코멘트가 없습니다.";

    // 푸시 메세지
    public static final String CREATED_PUSH_MESSAGE = "푸시메세지 생성 성공";
    public static final String READ_PUSH_MESSAGE = "푸시메세지 조회 성공";
    public static final String UPDATE_PUSH_MESSAGE = "푸시메세지 정보 수정 성공";
    public static final String DELETE_PUSH_MESSAGE = "푸시메세지 삭제 성공";
    public static final String NOT_FOUND_PUSH_MESSAGE = "해당 푸시메세지 없습니다.";

    // 파일 메세지
    public static final String CREATED_FILE = "파일 생성 성공";
    public static final String READ_FILE = "파일 조회 성공";
    public static final String DELETE_FILE = "파일 삭제 성공";
    public static final String NOT_FOUND_FILE = "해당 파일이 없습니다.";
}
