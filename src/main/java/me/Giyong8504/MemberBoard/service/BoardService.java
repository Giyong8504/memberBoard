package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.AddBoardDataRequest;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDataRepository boardDataRepository;

    // 저장 기능
    public BoardData save(AddBoardDataRequest request) {
        return boardDataRepository.save(request.toEntity());
    }

    // BoardData 테이블에 저장되어있는 모든 값 조회
    public List<BoardData> findAll() {
        return boardDataRepository.findAll();
    }
}
