package me.Giyong8504.MemberBoard.controller.board;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.view.BoardListViewResponse;
import me.Giyong8504.MemberBoard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String getBoard(Model model){
        List<BoardListViewResponse> board = boardService.findAll()
                .stream().map(BoardListViewResponse::new).toList();
        model.addAttribute("board", board);

        return "board";
    }
}
