package me.Giyong8504.MemberBoard.configs;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.configs.oauth.OAuth2UserCustomService;
import me.Giyong8504.MemberBoard.models.user.LoginFailureHandler;
import me.Giyong8504.MemberBoard.models.user.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 비활성화
        //회원 인증 설정 - 로그인
        http.formLogin(f -> {
            f.loginPage("/login") // 로그인 페이지 설정
                    .usernameParameter("userId") // 사용자 아이디 파라미터 설정
                    .passwordParameter("userPw") // 사용자 비밀번호 파라미터 설정
                    .successHandler(new LoginSuccessHandler()) //로그인 성공시 핸들러 설정.
                    .failureHandler(new LoginFailureHandler()); //로그인 실패시 핸들러 설정.
        });

        // 회원 설정 - 로그아웃
        http.logout(f -> {
            f.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 URL 설정
                    .logoutSuccessUrl("/board"); // 로그아웃 성공 후 이동할 페이지
        });

        // 회원 인가 설정 (접근 통제)
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/admin/**").hasAuthority("ADMIN") // "/admin/" 경로 요청은 'ADMIN' 권한을 가진 사용자만 접근 가능
                .requestMatchers("/mypage/**", "/new-board" ).authenticated() // "/mypage/**","/new-board" 경로 요청은 인증된 사용자만 접근 가능
                    .anyRequest().permitAll(); // 그 외 모든 요청은 누구나 접근 가능
        });

        // 관리자, 회원 페이지 접근할 때 오류코드, 리다이렉트
        http.exceptionHandling(c -> {
            c.authenticationEntryPoint((req, res, e) -> {
                String URI = req.getRequestURI();

                if (URI.indexOf("/admin") != -1) { // 관리자 페이지로 접근 시
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED"); // 401 Unauthorized 에러 반환
                } else { // 회원 전용 페이지로 접근 시
                    String redirectURL = req.getContextPath() + "/login"; // 로그인 페이지로 리다이렉트 URL 생성
                    res.sendRedirect(redirectURL); // 로그인 페이지로 리다이렉트
                }
            });
        });

//        // OAuth2 로그인
//        http.oauth2Login()
//                .loginPage("/login")
//                .authorizationEndpoint()
//                .and()
//                // 인증 성공 시 실행할 핸들러
//                .userInfoEndpoint()
//                .userService(oAuth2UserCustomService);
//
        return http.build(); // SecurityFilterChain 반환

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return w -> w.ignoring().requestMatchers(
                "/admin/css/**",
                "/admin/js/**",
                "/admin/images/**",
                "/board/css/**",
                "/board/js/**",
                "/board/images/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //암호 해시화
        return new BCryptPasswordEncoder();
    }
}
