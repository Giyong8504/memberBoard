package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.AddBoardDataRequest;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDataRepository boardDataRepository;

    public BoardData save(AddBoardDataRequest request) {
        return boardDataRepository.save(request.toEntity());
    }
}
