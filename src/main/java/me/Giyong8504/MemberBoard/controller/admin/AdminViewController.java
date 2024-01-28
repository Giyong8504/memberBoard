package me.Giyong8504.MemberBoard.controller.admin;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.user.UserListResponse;
import me.Giyong8504.MemberBoard.dto.boards.BoardListViewResponse;
import me.Giyong8504.MemberBoard.service.BoardService;
import me.Giyong8504.MemberBoard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminViewController {

    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/index")
    public String adIndex() {

        return "admin/index";
    }


    // 게시글 목록
    @GetMapping("/userBoardList")
    public String getBoard(Model model){
        List<BoardListViewResponse> board = boardService.findAll()
                .stream().map(BoardListViewResponse::new).toList();
        model.addAttribute("adminBoard", board);

        return "admin/user/userBoardList";
    }

    // 회원 목록
    @GetMapping("/userList")
    public String getUser(Model model) {
        List<UserListResponse> userList = userService.findAll()
                .stream().map(UserListResponse::new).toList();
        model.addAttribute("adminUserList", userList);

        return "admin/user/userList";
    }
}
