# MemberBoard Service
![image](https://github.com/Giyong8504/memberBoard/assets/128211712/0f6969f8-b80a-4402-9932-6eee667f9c96)

## 목차
- [1. 주제 선정](#주제-선정)

- [2. 개요](#개요)
- [3. 요구사항](#요구사항)
- [4. EER 다이어그램](#eer-다이어그램)
- [5. 화면 소개](#화면-소개)
  - [회원가입, 로그인, 구글 로그인](#회원가입-로그인-구글-로그인)
  - [글 작성, 글 수정](#글-작성-글-수정)
  - [글 삭제, 댓글 작성](#글-삭제-댓글-작성)
  - [댓글 수정, 댓글 삭제](#댓글-수정-댓글-삭제)
  - [글 제목과 내용검색, 마이페이지 정보 표시, 비밀번호 변경](#글-제목과-내용검색-마이페이지-정보-표시-비밀번호-변경)
  - [회원 탈퇴, 관리자 페이지 접근 권한](#회원-탈퇴-관리자-페이지-접근-권한)
  - [USER권한, ADMIN권한](#user권한-admin권한)
- [6. 개발 내용](#개발-내용)
- [7. 만났던 오류](#만났던-오류)
---
## 주제 선정
- 웹의 기본인 CRUD 게시판을 만들고 회원만 작성 가능하며 권한을 부여해 관리자가 게시글과 회원을 관리하는 게시판 서비스를 선정했다.

---
## 개요
- 프로젝트명 : MemberBoard Service
- 개발 인원 : 1명
- 개발 기간 : 2023.12.01 ~
- 기능
  - 게시판 : CRUD 기능, 댓글, 게시글 검색
  - 일반회원 : Security 회원가입 및 로그인, OAuth2.0 로그인
    - 이메일 검증을 통한 회원가입
    - 유효성 검사
    - 마이페이지
    - 비밀번호 찾기, 변경, 탈퇴
  - 관리자 : 권한을 부여하여 일반 회원과 게시글 조회, 삭제
<br>

- Stacks : Java, SpringBoot, Thymeleaf, JavaScript, HTML5, CSS
- Tools : IntelliJ, MySQL, Gradle, Git
- Collaboration : GitHub

---
## 요구사항
- 게시글
    - 글 목록 조회
    - 제목 검색 조회, 내용 조회
    - 작성했던 사용자만 수정, 삭제 가능
    - 로그인하지 않은 상태로 글쓰기 클릭 시 로그인 페이지 이동


- 댓글
    - 로그인을 한 사용자만 댓글 가능
    - 댓글 작성자만 수정, 삭제 가능


- 회원 가입
  - 이메일 인증
    - DB에 저장된 이메일이 없을 시 메일로 인증코드 전송
    - 가입된 이메일이 있을 시 '이미 가입된 메일입니다' 표시
    - 발급한 인증번호와 세션에 저장한 인증번호가 동일한지 확인
    - 유효 시간 카운트
    - 인증번호 입력 후 '확인된 이메일 입니다.' 표시
    - 인증코드 재전송
  - 유효성 검사
    - 아이디 이메일 형식 확인
    - 비밀번호 잘못 입력 시 비밀번호 8자리 이상 확인
    - 회원명 미 입력 시 확인
    - 휴대전화 번호 형식 확인
    - 회원가입 약관에 동의 받기 체크 여부 확인
  - 중복 확인
    - 이메일 중복 여부 체크
    - 비밀번호, 비밀번호 확인 일치 여부
    - 휴대전화 번호 -> 형식 체크


- 로그인
  - 로그인 검사
    - 이메일 또는 비밀번호가 일치하지 않을 시 "이메일 또는 비밀번호가 일치하지 않습니다." 메시지 노출
  - 로그인하지 않을 경우 접속 가능 페이지
    - 회원가입
    - 로그인
    - 게시글 목록
    - 이 외에 접근 시 로그인 요청 메시지 노출 후 로그인 페이지로 이동


- 비밀번호 찾기
  - 가입한 이메일과 회원명 조합으로 조회 검증
  - 비밀번호 랜덤값으로 초기화
  - 찾기 시 비밀번호 초기화 후 가입한 메일로 전송, 이후 발송 안내 페이지로 이동
  - 초기화된 비밀번호로 로그인
  - 유효성 검사
    - 미가입 회원일 경우 '가입되지 않은 회원입니다' 메시지 노출
    - 구글 연동 로그인 계정일 경우 '구글연동 회원입니다. 구글 계정을 확인해주세요.' 메시지 노출
    

- 마이페이지
  - 로그인 중인 회원 정보 표시
    - 이메일, 사용자명, 전화번호
  - 비밀번호 변경
    - 유효성 검사
      - NotBlank로 '필수 항목입니다' 표시
      - 기존 비밀번호와 다른지 여부 표시
      - 비밀번호 8자리이상 표시
      - 비밀번호 일치여부 표시
  - 회원탈퇴
    - 유효성검사
      - NotBlank로 '필수 항목입니다' 표시
      - 기존 비밀번호와 다른지 여부 표시
    - 탈퇴 시 탈퇴회원을 다른 엔터티에 저장 후 사용자 삭제


- 관리자
    - 관리자 권한이 있는 사용자만 접속 가능
    - 관리자 권한으로 접속 가능 페이지
      - 글 목록 리스트 페이지
        - 관리자 권한만 글 삭제
      - 회원 정보
        - 관리자만 USER, DISABLE 권한변경
        - 관리자만 강제 탈퇴 가능


- OAuth2 로그인 + Session 로그인
  - 구글
  - 로그인 시
    - OAuth2 로그인시 로컬에 엑세스 토큰 발급, 쿠키에는 리프레시 토큰을 발급
  
  - 로그아웃 시
    - HTTP 세션 무효화
    - 사용자 인증정보 제거
    - 쿠키에 담긴 세션, 리프레시 코튼 제거
    - 액세스 토큰 제거
   
---
## EER 다이어그램
![image](https://github.com/Giyong8504/memberBoard/assets/128211712/704134be-b6dd-4ad9-8966-8034bc055bca)


---

## 화면 소개
### 회원가입, 로그인, 구글 로그인
|회원가입|로그인, 구글로그인|
|---|---|
|- 유효성 검사<br> - 이메일 중복 확인<br> - 이메일 검증|- 유효성 검사|
|![회원가입테스트 (1)](https://github.com/Giyong8504/memberBoard/assets/128211712/a3258292-ddf2-436a-a98a-e3d340ce65f5)| ![로그인테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/b867bc45-b8f1-456f-b2f2-b18b3e5ecc11)|

---
### 글 작성, 글 수정
|글 작성|글 수정|
|---|---|
| |- 자신의 글만 수정가능|
|![글작성테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/6a88f622-622d-45d9-b0e7-a890813d9cca)|![글수정테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/c89c71ab-3a5a-4e4f-8a79-8c1942ccdd16)|

---
### 글 삭제, 댓글 작성
|글 삭제|댓글 작성|
|---|---|
|자신의 글만 삭제가능| |
|![글삭제테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/676b7326-ae42-43e7-8351-e9127da1bf61)|![댓글작성테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/b8dd0f5d-d2f6-4eaa-86ae-6caaad98bbf8)|

---
### 댓글 수정, 댓글 삭제
|댓글 수정|댓글 삭제|
|---|---|
|다른 사용자의 댓글 수정 불가|다른 사용자의 댓글 삭제 불가|
|![댓글수정권한](https://github.com/Giyong8504/memberBoard/assets/128211712/b1ff4b7c-bd12-45a7-a462-67a36527e581)|![댓글삭제테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/10240195-96fe-41a5-88f0-0f87a334b049)|

---
### 글 제목과 내용검색, 마이페이지 정보 표시, 비밀번호 변경
|글 제목과 내용검색|마이페이지 정보 표시, 비밀번호 변경|
|---|---|
|- 제목으로 검색<br>- 내용으로 검색|- 유효성 검사<br>- 기존 비밀번호가 일치해야 변경 가능<br>- 새 비밀번호, 새 비밀번호 확인 시 변경 가능|
|![글제목내용검색](https://github.com/Giyong8504/memberBoard/assets/128211712/8aba1099-328d-45dd-a8fc-53b8dd86382d)|![마이페이지정보 비밀번호변경](https://github.com/Giyong8504/memberBoard/assets/128211712/b6bc4937-532d-458c-bf2b-22838f3532ab)|

---
### 회원 탈퇴, 관리자 페이지 접근 권한
|회원 탈퇴|관리자 페이지 접근 권한|
|---|---|
|- 유효성 검사<br>- 기존 비밀번호와 일치해야 변경 가능|- ADMIN 권한 사용자만 가능<br>- test01@test.org (ADMIN) , kky5163@naver.com(USER)<br>![1번](https://github.com/Giyong8504/memberBoard/assets/128211712/092e42ce-826d-4914-bdf4-5166e1cd9207)|
|![회원탈퇴](https://github.com/Giyong8504/memberBoard/assets/128211712/15f5bc40-7a85-488b-af59-6c29aaf495df)|![관리자페이지접근권한](https://github.com/Giyong8504/memberBoard/assets/128211712/478e9a61-dbef-41b6-b103-946fa86f447d)|

---
### USER권한, ADMIN권한
|USER 권한 : 회원 권한 수정, 강제 탈퇴, 회원 게시글 삭제 불가|ADMIN 권한 :  권한 수정, 강제 탈퇴, 회원 게시글 삭제 가능|
|---|---|
|- (경로 접근 권한을 주석처리 하여 모두 접근 가능한 상태.)<br>- USER 권한인 kky5163@naver.com 아이디로 테스트<br>![2번](https://github.com/Giyong8504/memberBoard/assets/128211712/6546f899-60f9-4a0e-9531-ee5e504cc6b0)| |
|<br>![유저권한](https://github.com/Giyong8504/memberBoard/assets/128211712/df5cf74c-8942-4f61-b8ca-d7d6091bbcb3)|![3번](https://github.com/Giyong8504/memberBoard/assets/128211712/882fe65e-1ede-45df-bda1-d5504c777dda)<br>![어드민권한](https://github.com/Giyong8504/memberBoard/assets/128211712/a87d57c6-1e0f-42c3-afbb-2ec4db1da0e4)|

---

## 개발 내용
- [1. 글 목록 조회를 위한 API 구현](https://blog.naver.com/kky5163/223281343625)<br>
- [2. 글 조회 API 구현](https://blog.naver.com/kky5163/223281891034)<br>
- [3. 글 삭제 API 구현](https://blog.naver.com/kky5163/223282366123)<br>
- [4. 글 수정 API 구현](https://blog.naver.com/kky5163/223282530507)<br>
- [5. 글 목록 뷰 구현하기](https://blog.naver.com/kky5163/223283957692)<br>
- [6. 삭제 기능 추가](https://blog.naver.com/kky5163/223284518055)<br>
- [7. 수정, 생성 기능 추가](https://blog.naver.com/kky5163/223285756397)<br>
- [8. 스프링 시큐리티를 사용해 인증, 인가 기능을 구현](https://blog.naver.com/kky5163/223288440941)<br>
- [9. 회원 가입 구현, 유효성 검사, 뷰 구현](https://blog.naver.com/kky5163/223290414355)<br>
- [10. 비회원, 회원, 관리자 권한 통제, 회원 인가 설정](https://blog.naver.com/kky5163/223291609875)<br>
- [11. 가입한 아이디를 작성자로 자동 변경, 글 삭제, 수정 권한](https://blog.naver.com/kky5163/223295712206)<br>
- [12. Session 방식에서 JWT 방식으로 변경(토큰)](https://blog.naver.com/kky5163/223300332829)<br>
- [13. 리프레시 토큰, 토큰 필터, 토큰 API 구현](https://blog.naver.com/kky5163/223301399892)<br>
- [14. OAuth2.0 서비스 구현](https://blog.naver.com/kky5163/223305712327)<br>
- [15. 세션방식과 JWT 방식인 OAuth2를 함께 구현](https://blog.naver.com/kky5163/223309099203)<br>
- [16. 글 검색 기능 추가](https://blog.naver.com/kky5163/223313370219)<br>
- [17_1. 댓글 기능 추가하기 (댓글 생성)](https://blog.naver.com/kky5163/223316180498)<br>
- [17_2. 댓글 기능 추가하기 (수정, 삭제)](https://blog.naver.com/kky5163/223317239322)<br>
- [18. 이메일 전송 - 회원가입 인증](https://blog.naver.com/kky5163/223322703326)<br>
- [19. 비밀번호 찾기 (가입한 이메일로 발송), 구글 유저는 유효성 검사로 안내](https://blog.naver.com/kky5163/223323237032)<br>
- [20. myPage 구현 (유저 정보, 비밀번호 변경, 비밀번호 변경 유효성 검사, 회원탈퇴)](https://blog.naver.com/kky5163/223332429905)<br>
- [21_1. 관리자 페이지 (회원 게시글 리스트, 관리자 권한 삭제)](https://blog.naver.com/kky5163/223334591018)<br>
- [21_2. 관리자 페이지 (회원 리스트, 권한 변경, 회원 탈퇴 처리)](https://blog.naver.com/kky5163/223336713641)<br>


## 만났던 오류
- [1. data.sql 생성 후 오류](https://blog.naver.com/kky5163/223281350345)<br>
- [2. PropertyValueException](https://blog.naver.com/kky5163/223285756762)<br>
- [3. A bean with that name has already been defined and overriding is disabled.](https://blog.naver.com/kky5163/223289051980)<br>
- [4. java.lang.NullPointerException](https://blog.naver.com/kky5163/223304709871)<br>
- [5. org.springframework.dao.DataIntegrityViolationException](https://blog.naver.com/kky5163/223305387115)<br>
- [6. 경로 요청 권한](https://blog.naver.com/kky5163/223305694711)<br>
- [7. StackOverflowError 무한 재귀 (Infinite recursion)](https://blog.naver.com/kky5163/223316618704)<br>
- [8. java.lang.AssertionError: Status expected:<200> but was:<302> (테스트중 에러)](https://blog.naver.com/kky5163/223320041329)<br>
- [9. java.lang.IllegalArgumentException: Unexpected user](https://blog.naver.com/kky5163/223330218340)<br>
- [10. org.thymeleaf.exceptions.TemplateInputException: An error happened during template parsing](https://blog.naver.com/kky5163/223330236120)<br>
[11. org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL](https://blog.naver.com/kky5163/223332393167)<br>
