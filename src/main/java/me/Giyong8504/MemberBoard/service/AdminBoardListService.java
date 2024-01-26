package me.Giyong8504.MemberBoard.service;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBoardListService {

    private final BoardDataRepository boardDataRepository;
    private final UserUtil userUtil;

    // 삭제기능 : id 값으로 삭제한다.
    public void delete(Long id) {
        BoardData boardData = boardDataRepository.findById(id).orElse(null);

        // Admin 일 때만 삭제가능.
        if (userUtil.isAdmin()) {
            boardDataRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Unauthorized delete");
        }
    }
}
