package me.Giyong8504.MemberBoard.controller.comment;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.comments.AddCommentRequest;
import me.Giyong8504.MemberBoard.dto.comments.UpdateCommentRequest;
import me.Giyong8504.MemberBoard.entities.Comment;
import me.Giyong8504.MemberBoard.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/api/comments")
    public ResponseEntity<Comment> addComment(@RequestBody AddCommentRequest request, Principal principal) {
        Comment addedComment = commentService.addComment(request.getBoardDataId(), request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    }

    // 댓글 수정
    @PutMapping("/api/comments/{boardId}/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest request) {
        Comment updateComment = commentService.updateComment(id, request);

        return ResponseEntity.ok().body(updateComment);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{boardId}/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/api/comments/board/{boardId}")
    public ResponseEntity<List<Comment>> getCommentsByBoardId(@PathVariable Long boardId) {
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }
}
