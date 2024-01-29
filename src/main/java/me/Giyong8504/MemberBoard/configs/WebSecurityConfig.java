package me.Giyong8504.MemberBoard.configs;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.configs.jwt.TokenAuthenticationFilter;
import me.Giyong8504.MemberBoard.configs.jwt.TokenProvider;
import me.Giyong8504.MemberBoard.configs.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import me.Giyong8504.MemberBoard.configs.oauth.OAuth2SuccessHandler;
import me.Giyong8504.MemberBoard.configs.oauth.OAuth2UserCustomService;
import me.Giyong8504.MemberBoard.models.user.LoginFailureHandler;
import me.Giyong8504.MemberBoard.models.user.LoginSuccessHandler;
import me.Giyong8504.MemberBoard.repositories.RefreshTokenRepository;
import me.Giyong8504.MemberBoard.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 토큰 방식으로 csrf 보안 비활성화
        .httpBasic().disable() // 기본적인 http 비활성화
//        http.formLogin().disable() // formLogin 비활성화 (토큰만 사용할 경우)
                .logout().disable();

        //회원 인증 설정 - 로그인
        http.formLogin(f -> {
            f.loginPage("/login") // 로그인 페이지 설정
                    .defaultSuccessUrl("/board")
                    .usernameParameter("email") // 사용자 아이디 파라미터 설정
                    .passwordParameter("password") // 사용자 비밀번호 파라미터 설정
                    .successHandler(new LoginSuccessHandler()) //로그인 성공시 핸들러 설정.
                    .failureHandler(new LoginFailureHandler()); //로그인 실패시 핸들러 설정.
        });

//        http.sessionManagement() // 세션을 사용하지 않기 때문에 비활성화
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 헤더를 확인할 커스텀 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 토큰 재발급, 이메일 URL은 인증없이 접근 가능하도록 설정. 나머지 API URL은 인증 필요
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/admin/**").hasAuthority("ADMIN") // "/admin/" 경로 요청은 'ADMIN' 권한을 가진 사용자만 접근 가능
                    .requestMatchers("/changePassword/**", "/deleteId/**").hasAuthority("USER")
                    .requestMatchers("/myPage/**", "/new-board").authenticated() // "/myPage/**","/new-board" 경로 요청은 인증된 사용자만 접근 가능
                    .requestMatchers("/api/token", "/api/email/**").permitAll()
                    .anyRequest().permitAll(); // 그 외 모든 요청은 누구나 접근 가능
        });

        // OAuth2 로그인
        http.oauth2Login()
                .loginPage("/login")
                .authorizationEndpoint()

                // Authorization 요청과 관련된 상태 저장
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler()) // 인증 성공 시 실행할 핸들러
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.logout(f -> {
            f.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 URL 설정
                    .logoutSuccessUrl("/board") // 로그아웃 성공 후 이동할 페이지
                    .invalidateHttpSession(true) // HTTP 세션 무효화
                    .clearAuthentication(true) // 사용자 인증정보 제거
                    .deleteCookies("JSESSIONID", "refresh_token"); // 쿠키에 담긴 세션, 리프레시 토큰 제거

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

        // /api로 시작하는 url인 경우 401 상태코드를 반환하도록 예외 처리
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));;

        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // 스프링 시큐리티 기능 비활성화
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
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(), userService);
    }
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //암호 해시화
        return new BCryptPasswordEncoder();
    }
}
