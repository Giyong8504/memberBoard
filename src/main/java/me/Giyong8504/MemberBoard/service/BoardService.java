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

    // JPA에서 제공하는 findById()로 id를 받아 조회 후 없으면 예외 반환.
    public BoardData findById(Long id) {
        return boardDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
    }

    // 삭제기능 : id 값으로 삭제한다.
    public void delete(Long id) {
        boardDataRepository.deleteById(id);
    }
}
