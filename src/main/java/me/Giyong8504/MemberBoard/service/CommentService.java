package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.AddCommentRequest;
import me.Giyong8504.MemberBoard.entities.Comment;
import me.Giyong8504.MemberBoard.repositories.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 저장 기능
    public Comment save(AddCommentRequest request) {
        return commentRepository.save(request.toEntity());
    }
}
