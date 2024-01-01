package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardDataRepository extends JpaRepository<BoardData, Long> {
    List<BoardData> findAllByOrderByRegDtDesc(); // 작성일 기준 내림차순
}
