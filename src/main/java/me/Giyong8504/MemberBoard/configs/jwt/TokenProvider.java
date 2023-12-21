package me.Giyong8504.MemberBoard.configs.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        /** 내용
         * 등록된 클레임
         * iss : kky5163@naver.com (토큰 제공자)
         * iat : 현재시간
         * exp : expiry 멤버 변숫값
         *
         * 비공개 클레임
         * sub : 유저 email
         * 클레임 id : 유저 id
         */

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT

                // 내용 iss: kky5163@naver.com (토큰 제공자로 properties.yml 에서 설정한 값)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) // 내용 iat : 현재 시간 (발급 일시)
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫값 (현재 시간 기준으로 만료 시간 설정)
                .setSubject(user.getEmail()) // 내용 sub : 유저 이메일
                .claim("id", user.getUserNo()) // 클레임 id : 유저 Id

                // 서명 : 비밀 값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;

        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰.
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework
                .security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }


    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {

        //클레임 조회
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

}
