package me.Giyong8504.MemberBoard.controller.board;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.view.BoardListViewResponse;
import me.Giyong8504.MemberBoard.dto.view.BoardViewResponse;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;

    // 게시글 목록
    @GetMapping("/board")
    public String getBoard(Model model){
        List<BoardListViewResponse> board = boardService.findAll()
                .stream().map(BoardListViewResponse::new).toList();
        model.addAttribute("board", board);

        return "board/boardList";
    }

    // 게시글 내용
    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable Long id, Model model) {
        BoardData boardData = boardService.findById(id);
        model.addAttribute("board", new BoardViewResponse(boardData));

        return "board/boardContent";
    }
}
