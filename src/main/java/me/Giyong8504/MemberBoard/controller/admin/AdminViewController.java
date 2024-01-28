package me.Giyong8504.MemberBoard.controller.admin;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.Role;
import me.Giyong8504.MemberBoard.dto.user.UserListResponse;
import me.Giyong8504.MemberBoard.dto.boards.BoardListViewResponse;
import me.Giyong8504.MemberBoard.entities.User;
import me.Giyong8504.MemberBoard.repositories.UserRepository;
import me.Giyong8504.MemberBoard.service.BoardService;
import me.Giyong8504.MemberBoard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminViewController {

    private final BoardService boardService;
    private final UserService userService;
    private final UserRepository userRepository;

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

    // 사용자 권한 변경
    @PostMapping("/changeUserRole")
    public String changeUserRole(@RequestParam Long userId, @RequestParam Role newRole) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setRole(newRole);
            userRepository.save(user);
        }
        return "redirect:/admin/userList";
    }
}
