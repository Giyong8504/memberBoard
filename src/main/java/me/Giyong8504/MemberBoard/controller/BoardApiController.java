package me.Giyong8504.MemberBoard.controller;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.AddBoardDataRequest;
import me.Giyong8504.MemberBoard.dto.BoardDataResponse;
import me.Giyong8504.MemberBoard.entities.BoardData;
import me.Giyong8504.MemberBoard.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // HTTP 메서드가 POST 일 때 전달받은 URL과 동일하면 메서드로 매핑.
    @PostMapping("/api/boards")
    public ResponseEntity<BoardData> addBoardData(@RequestBody AddBoardDataRequest request) {
        BoardData saveBoardData = boardService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveBoardData);
    }

    // GET 요청이 들어오면 BoardDataResponse로 파싱하여 클라이언트에 반환.
    @GetMapping("/api/boards")
    public ResponseEntity<List<BoardDataResponse>> findAllBoardData() {
        List<BoardDataResponse> boards = boardService.findAll()
                .stream().map(BoardDataResponse::new).toList();

        return ResponseEntity.ok().body(boards);
    }

    // {id}에 값이 들어오면 findBoard()로 넘어가 {id} 글을 찾고 게시글 정보를 body에 담아 웹브라우저에 보낸다.
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<BoardDataResponse> findBoard(@PathVariable Long id) {
        BoardData boardData = boardService.findById(id);

        return ResponseEntity.ok(new BoardDataResponse(boardData));
    }
}
