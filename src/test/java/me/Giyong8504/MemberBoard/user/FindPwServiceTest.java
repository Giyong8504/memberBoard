package me.Giyong8504.MemberBoard.user;

import me.Giyong8504.MemberBoard.service.FindPwService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class FindPwServiceTest {

    @Autowired
    private FindPwService findPwService;

    @Test
    @DisplayName("비밀번호 초기화, 메일 전송 테스트")
    void resetTest() {
        assertDoesNotThrow(() -> findPwService.reset("kky5163@naver.com"));
    }
}