package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시물에 대한 모든 댓글 가져오기
    List<Comment> findByBoardData(BoardData boardData);
}
