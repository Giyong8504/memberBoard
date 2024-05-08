package me.Giyong8504.MemberBoard.commons;

import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

/**
 * 공통 예외 클래스. 애플리케이션에서 발생하는 예외들을 처리하기 위한 기본 클래스.
 */
public class CommonException extends RuntimeException {

    protected static ResourceBundle bundleValidation; // 유효성 메시지 번들
    protected static ResourceBundle bundleError;      // 오류 메시지 번들

    protected HttpStatus httpStatus; // HTTP 응답 상태 코드

    static {
        bundleValidation = ResourceBundle.getBundle("messages.validations"); // 유효성 메시지 번들 초기화
        bundleError = ResourceBundle.getBundle("messages.errors"); // 오류 메시지 번들 초기화
    }

    /**
     * 기본 생성자입니다. 내부 서버 오류에 대한 예외를 생성.
     *
     * @param message 오류 메시지
     */
    public CommonException(String message) {
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * 오류 메시지와 HTTP 응답 상태 코드를 인자로 받는 생성자.
     *
     * @param message 오류 메시지
     * @param httpStatus HTTP 응답 상태 코드
     */
    public CommonException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * HTTP 응답 상태 코드를 반환.
     *
     * @return HTTP 응답 상태 코드
     */
    public HttpStatus getStatus() {
        return httpStatus;
    }
}