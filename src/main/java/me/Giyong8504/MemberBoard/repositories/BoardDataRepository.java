package me.Giyong8504.MemberBoard.repositories;

import me.Giyong8504.MemberBoard.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDataRepository extends JpaRepository<BoardData, Long> {
}
