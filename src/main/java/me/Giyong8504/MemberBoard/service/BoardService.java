package me.Giyong8504.MemberBoard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.AddBoardDataRequest;
import me.Giyong8504.MemberBoard.dto.UpdateBoardDataRequest;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.repositories.BoardDataRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        BoardData boardData = boardDataRepository.findById(id).orElse(null);
        if (boardData != null && commonAuthentication(boardData)) {
            boardDataRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Unauthorized deletion");
        }

    }

    /**
     * 수정기능 :  findById()로 id 값을 받아 조회 후 없으면 예외 발생.
     * boardData.update()에 수정 값을 받은 정보로 값 수정.
     * @Transactional 을 사용하여 하나의 작업 단위로 묶음.
     * 글 작성자만 수정할 수 있도록 추가
     */
    @Transactional
    public BoardData update(Long id, UpdateBoardDataRequest request) {
        BoardData boardData = boardDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" +id));

        // 글작성자만 수정할 수 있음.
        if (boardData != null && commonAuthentication(boardData)) {
            boardData.update(request.getTitle(), request.getContent());
        } else {
            throw new IllegalArgumentException("Unauthorized update");
        }

        return boardData;
    }

    // Spring Security를 사용하여 현재 로그인한 사용자의 정보를 얻고, 해당 정보를 기반으로 삭제 권한을 확인, 수정도 함께 사용
    private boolean commonAuthentication(BoardData boardData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return username.equals(boardData.getAuthor());
    }
}
