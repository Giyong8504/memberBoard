package me.Giyong8504.MemberBoard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.comments.AddCommentRequest;
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

    @PostMapping("/api/comments")
    public ResponseEntity<Comment> addComment(@RequestBody @Valid Long boardId, AddCommentRequest request, Principal principal) {
        Comment addedComment = commentService.addComment(boardId, request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody @Valid AddCommentRequest request) {
        commentService.updateComment(commentId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/comments/board/{boardId}")
    public ResponseEntity<List<Comment>> getCommentsByBoardId(@PathVariable Long boardId) {
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }
}
