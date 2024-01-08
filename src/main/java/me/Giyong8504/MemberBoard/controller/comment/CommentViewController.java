package me.Giyong8504.MemberBoard.controller.comment;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.comments.CommentViewResponse;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.entities.Comment;
import me.Giyong8504.MemberBoard.service.BoardService;
import me.Giyong8504.MemberBoard.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentViewController {

    private final CommentService commentService;
    private final BoardService boardService;

    @GetMapping("/updateComment")
    public String updateComment(@RequestParam Long boardId, @RequestParam(required = false) Long id, Model model) {

        Comment comment = commentService.findById(id);
        model.addAttribute("commentModify", new CommentViewResponse(comment));

        BoardData boardData = boardService.findById(boardId);
        model.addAttribute("board", boardData);

        return "board/updateComment";
    }
}
