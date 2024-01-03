package me.Giyong8504.MemberBoard.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.AddCommentRequest;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.entities.Comment;
import me.Giyong8504.MemberBoard.repositories.BoardDataRepository;
import me.Giyong8504.MemberBoard.repositories.CommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardDataRepository boardDataRepository;

    // 게시글의 모든 댓글을 조회
    public List<Comment> getCommentsByBoardId(Long boardId) {
        BoardData boardData = boardDataRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        return commentRepository.findByBoardData(boardData);
    }

    // 게시글에 댓글 추가
    @Transactional
    public Comment addComment(Long boardId, AddCommentRequest request) {
        BoardData boardData = boardDataRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(request.getAuthor())
                .boardData(boardData)
                .build();

        return commentRepository.save(comment);
    }

    // 삭제 기능 댓글 id값으로 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment != null && commonAuthentication(comment) || isOAuth2UserWithEmail(comment)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("Unauthorized delete");
        }
    }

    // 수정 기능
    @Transactional
    public Comment updateComment(Long commentId, @Valid AddCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("not found" + commentId));

        // 글작성자만 수정할 수 있음.
        if (comment != null && commonAuthentication(comment) || isOAuth2UserWithEmail(comment)){
            comment.update(request.getContent());
        } else {
            throw new IllegalArgumentException("Unauthorized update");
        }

        return comment;
    }

    // Spring Security를 사용하여 현재 로그인한 사용자의 정보를 얻고, 해당 정보를 기반으로 삭제 권한을 확인, 수정도 함께 사용
    private boolean commonAuthentication(Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return username.equals(comment.getAuthor());
    }

    // OAuth2로 인증된 사용자의 이메일이 일치하는지 확인하는 메서드
    private boolean isOAuth2UserWithEmail(Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

            // 사용자의 OAuth2 인증 정보에서 이메일 추출
            String userEmail = oauth2Principal.getAttribute("email");

            // boardData의 작성자 이메일과 비교하여 일치 여부 반환
            return userEmail != null && userEmail.equals(comment.getAuthor());
        }
        return false;
    }
}
