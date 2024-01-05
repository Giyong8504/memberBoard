package me.Giyong8504.MemberBoard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.boards.AddBoardDataRequest;
import me.Giyong8504.MemberBoard.dto.boards.UpdateBoardDataRequest;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.repositories.BoardDataRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
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
        return boardDataRepository.findAllByOrderByRegDtDesc();
    }

    // JPA에서 제공하는 findById()로 id를 받아 조회 후 없으면 예외 반환.
    public BoardData findById(Long id) {
        return boardDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
    }

    // 삭제기능 : id 값으로 삭제한다.
    public void delete(Long id) {
        BoardData boardData = boardDataRepository.findById(id).orElse(null);
        if (boardData != null && commonAuthentication(boardData) || isOAuth2UserWithEmail(boardData)) {
            boardDataRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Unauthorized delete");
        }
    }

    // 글 검색 기능
    public List<BoardData> searchBoard(String keyword) {
        return boardDataRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    /**
     * 수정기능 :  findById()로 id 값을 받아 조회 후 없으면 예외 발생.
     * boardData.update()에 수정 값을 받은 정보로 값 수정.
     *
     * @Transactional 을 사용하여 하나의 작업 단위로 묶음.
     * 글 작성자만 수정할 수 있도록 추가
     */
    @Transactional
    public BoardData update(Long id, UpdateBoardDataRequest request) {
        BoardData boardData = boardDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));

        // 글작성자만 수정할 수 있음.
        if (boardData != null && commonAuthentication(boardData) || isOAuth2UserWithEmail(boardData)){
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

    // OAuth2로 인증된 사용자의 이메일이 일치하는지 확인하는 메서드
    private boolean isOAuth2UserWithEmail(BoardData boardData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

            // 사용자의 OAuth2 인증 정보에서 이메일 추출
            String userEmail = oauth2Principal.getAttribute("email");

            // boardData의 작성자 이메일과 비교하여 일치 여부 반환
            return userEmail != null && userEmail.equals(boardData.getAuthor());
        }
        return false;
    }
}
