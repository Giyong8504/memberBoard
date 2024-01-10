package me.Giyong8504.MemberBoard.email;

/**
 * 이메일 메시지 데이터 클래스
 * @param to : 수신인
 * @param subject : 제목
 * @param message : 내용
 */
public record EmailMessage(
        String to,
        String subject,
        String message
) {}
