package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
