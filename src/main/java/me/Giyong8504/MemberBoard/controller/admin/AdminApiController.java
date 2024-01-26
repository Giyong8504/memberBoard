package me.Giyong8504.MemberBoard.controller.admin;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.service.AdminBoardListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminBoardListService adminBoardListService;

    @DeleteMapping("/api/userBoardList/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        adminBoardListService.delete(id);

        return ResponseEntity.ok().build();
    }
}
