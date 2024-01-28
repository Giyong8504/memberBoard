package me.Giyong8504.MemberBoard.controller.admin;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.service.AdminBoardListService;
import me.Giyong8504.MemberBoard.service.AdminUserListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminBoardListService adminBoardListService;
    private final AdminUserListService adminUserListService;

    // 관리자 권한 회원 게시글 삭제
    @DeleteMapping("/api/userBoardList/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        adminBoardListService.delete(id);

        return ResponseEntity.ok().build();
    }

    // 관리자 권한 사용자 강제 탈퇴
    @DeleteMapping("/api/userList/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminUserListService.userDelete(id);

        return ResponseEntity.ok().build();
    }
}
