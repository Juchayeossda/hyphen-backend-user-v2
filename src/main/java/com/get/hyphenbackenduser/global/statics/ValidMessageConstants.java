package com.get.hyphenbackenduser.global.statics;

public class ValidMessageConstants {

    private ValidMessageConstants() {
        throw new IllegalStateException("Utility class");
    }


    public static final String USER_EMAIL_NOT_BLANK = "유저 이메일은 Null, 공백, 띄어쓰기를 허용하지 않습니다.";
    public static final String USER_EMAIL_AND_NAME_NOT_BLANK = "유저 이메일과 유저 이름은 Null, 공백, 띄어쓰기를 허용하지 않습니다.";
    public static final String USER_PASSWORD_NOT_BLANK = "유저 비밀번호는 Null, 공백, 띄어쓰기를 허용하지 않습니다.";


    public static final String USER_NAME_SIZE = "유저 이름은 2자 이상, 12자 이하이어야 합니다.";
    public static final String USER_PASSWORD_SIZE = "유저 비밀번호는 8자 이상, 24자 이하이어야 합니다.";


    public static final String INQUIRY_TITLE_NOT_BLANK = "문의 제목은 Null, 공백, 띄어쓰기를 허용하지 않습니다.";
    public static final String INQUIRY_CONTENT_NOT_BLANK = "문의 내용은 Null, 공백, 띄어쓰기를 허용하지 않습니다.";


    public static final String EMAIL_NOT_BLANK = "이메일은 Null, 공백, 띄어쓰기를 허용하지 않습니다.";
    public static final String EMAIL_NOT_TYPE = "이메일의 형식이 올바르지 않습니다.";


    public static final String REFRESH_TOKEN_NOT_BLANK = "Refresh Token은 Null, 공백, 띄어쓰기를 허용하지 않습니다.";


    public static final String PREVIOUS_PASSWORD_NOT_BLANK = "이전 비밀번호는 Null, 공백, 띄어쓰기를 허용하지 않습니다.";
    public static final String NEW_PASSWORD_NOT_BLANK = "새로운 비밀번호는 Null, 공백, 띄어쓰기를 허용하지 않습니다.";


    public static final String NEW_NAME_NOT_BLANK = "새로운 이름은 Null, 공백, 띄어쓰기를 허용하지 않습니다.";
}
