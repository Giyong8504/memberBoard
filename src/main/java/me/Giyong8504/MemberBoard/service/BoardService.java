package me.Giyong8504.MemberBoard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.AddBoardDataRequest;
import me.Giyong8504.MemberBoard.dto.UpdateBoardDataRequest;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDataRepository boardDataRepository;

    // 저장 기능
    public BoardData save(AddBoardDataRequest request, String userName) {
        return boardDataRepository.save(request.toEntity(userName));
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


    /**
     * 수정기능 :  findById()로 id 값을 받아 조회 후 없으면 예외 발생.
     * boardData.update()에 수정 값을 받은 정보로 값 수정.
     * @Transactional 을 사용하여 하나의 작업 단위로 묶음.
     */
    @Transactional
    public BoardData update(Long id, UpdateBoardDataRequest request) {
        BoardData boardData = boardDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" +id));

        boardData.update(request.getTitle(), request.getContent());

        return boardData;
    }
}
