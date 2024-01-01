package me.Giyong8504.MemberBoard.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.dto.CreateAccessTokenRequest;
import me.Giyong8504.MemberBoard.dto.CreateAccessTokenResponse;
import me.Giyong8504.MemberBoard.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenService tokenService;

    // 요청이 오면 토큰 서비스에서 리프레시 토큰 기반으로 새로운 액세스 토큰 생성
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
            (@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    // 로그아웃 성공 시
    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        return ResponseEntity.ok("Logout success");
    }
}
