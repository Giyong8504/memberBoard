package me.Giyong8504.MemberBoard.controller.admin;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.boards.BoardListViewResponse;
import me.Giyong8504.MemberBoard.service.BoardService;
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

    // 게시글 목록
    @GetMapping("/userBoardList")
    public String getBoard(Model model){
        List<BoardListViewResponse> board = boardService.findAll()
                .stream().map(BoardListViewResponse::new).toList();
        model.addAttribute("adminBoard", board);

        return "admin/user/userBoardList";
    }

    @GetMapping("/index")
    public String adIndex() {

        return "admin/index";
    }

}
