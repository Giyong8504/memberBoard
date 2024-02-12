# MemberBoard Service
![image](https://github.com/Giyong8504/memberBoard/assets/128211712/0f6969f8-b80a-4402-9932-6eee667f9c96)

## 목차
- [1. 주제 선정](#주제-선정)

- [2. 개요](#개요)
- [3. 요구사항](#요구사항)
- [4. EER 다이어그램](#eer-다이어그램)
- [5. 화면 소개](#화면-소개)
  - [회원가입, 로그인, 구글 로그인](#1-회원가입-로그인-구글-로그인)
  - [글 작성, 글 수정](#2-글-작성-글-수정)
  - [글 삭제, 댓글 작성](#3-글-삭제-댓글-작성)
  - [댓글 수정, 댓글 삭제](#4-댓글-수정-댓글-삭제)
  - [글 제목과 내용검색, 마이페이지 정보 표시, 비밀번호 변경](#5-글-제목과-내용검색-마이페이지-정보-표시-비밀번호-변경)
  - [회원 탈퇴, 관리자 페이지 접근 권한](#6-회원-탈퇴-관리자-페이지-접근-권한)
  - [USER권한, ADMIN권한](#7-user권한-admin권한)
- [6. 개발 내용](#개발-내용)
- [7. 만났던 오류](#만났던-오류)
- [8. 마치며](#마치며)
  - [후기](#후기)
---
## 주제 선정
- 웹의 기본인 CRUD 게시판을 만들고 회원만 작성 가능하며 권한을 부여해 관리자가 게시글과 회원을 관리하는 게시판 서비스를 선정했다.

---
## 개요
- 프로젝트명 : MemberBoard Service
- 개발 인원 : 1명
- 개발 기간 : 2023.12.01 ~ 2024.02.13
- 주요 기능
  - 게시판 : CRUD 기능, 댓글, 게시글 검색
  - 일반회원 : Security 회원가입 및 로그인, OAuth2.0 로그인
    - 이메일 검증을 통한 회원가입
    - 유효성 검사
    - 마이페이지
    - 비밀번호 찾기, 변경, 탈퇴
  - 관리자 : 권한을 부여하여 일반 회원과 게시글 조회, 삭제
<br>

---

### Stack
![java-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/a6104f4d-e299-4017-8617-6863fb9abe73)Java(17) 
![spring-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/fdaaaeb8-b2d9-424b-8b54-fdf2d7f6dcf9)Spring 
![springboot-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/7bd6f139-e97e-494f-b56a-52ebbfb37893)SpringBoot(3.0.2) + JPA + Lombok + Validation 
![thymeleaf-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/251992ab-736a-4669-8dc0-f9ccd231e949)Thymeleaf 
![js-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/ab022ed4-e0b6-4a58-a5a0-f4224aa933b6)Javascript 
![html-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/45d43c25-78e7-4d87-b499-58ea71136bc4)Html5 
![css-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/f1341976-4afc-4487-97c6-cc709cd2413a)Css 

### Tools
![intellij-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/60bb943d-8b2d-4743-bddf-981463b17fc5)IntelliJ
![mysql-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/7e2d7068-2227-4a7d-a2a3-c47e79154351)MySQL
![gradle-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/1cc55962-2a6a-427b-b069-febbfef874b6)Gardle
![git-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/3ddc519c-d58c-4b08-8da0-27ce47ff7e65)Git
![tomcat-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/e5776633-34da-4d8a-8b0c-404196de1821)Tomcat

### Collaboration
![github-icon](https://github.com/Giyong8504/memberBoard/assets/128211712/94861ab3-b104-43a7-ad5b-b89a026498e2)Github
![naverblog](https://github.com/Giyong8504/memberBoard/assets/128211712/a30bea2e-2561-462e-87cf-e5678bb91c26)Blog

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

   
-  Session 로그인 + OAuth2 로그인
    - 구글 로그인 시
      - OAuth2 로그인시 로컬에 엑세스 토큰 발급, 쿠키에는 리프레시 토큰을 발급

  
- 로그아웃 시
  - HTTP 세션 무효화
  - 사용자 인증정보 제거
  - 쿠키에 담긴 세션, 리프레시 코튼 제거
  - 액세스 토큰 제거


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

   
---
## EER 다이어그램
![image](https://github.com/Giyong8504/memberBoard/assets/128211712/704134be-b6dd-4ad9-8966-8034bc055bca)


---

## 화면 소개
### 1. 회원가입, 로그인, 구글 로그인
|회원가입|로그인, 구글로그인|
|---|---|
|- 유효성 검사<br> - 이메일 중복 확인<br> - 이메일 검증|- 유효성 검사<br>- 로그인 후 토큰 발급 (구글 로그인)|
|![회원가입테스트 (1)](https://github.com/Giyong8504/memberBoard/assets/128211712/a3258292-ddf2-436a-a98a-e3d340ce65f5)|![로그인구글로그인](https://github.com/Giyong8504/memberBoard/assets/128211712/7c68d20c-5bb8-4008-a6e7-83f13c7763bf)|

---

<br>

### 2. 글 작성, 글 수정
|글 작성|글 수정|
|---|---|
| |- 자신의 글만 수정가능|
|![글작성테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/6a88f622-622d-45d9-b0e7-a890813d9cca)|![글수정테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/c89c71ab-3a5a-4e4f-8a79-8c1942ccdd16)|

---

<br>

### 3. 글 삭제, 댓글 작성
|글 삭제|댓글 작성|
|---|---|
|- 자신의 글만 삭제가능| |
|![글삭제테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/676b7326-ae42-43e7-8351-e9127da1bf61)|![댓글작성테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/b8dd0f5d-d2f6-4eaa-86ae-6caaad98bbf8)|

---

<br>

### 4. 댓글 수정, 댓글 삭제
|댓글 수정|댓글 삭제|
|---|---|
|- 다른 사용자의 댓글 수정 불가|- 다른 사용자의 댓글 삭제 불가|
|![댓글수정권한](https://github.com/Giyong8504/memberBoard/assets/128211712/b1ff4b7c-bd12-45a7-a462-67a36527e581)|![댓글삭제테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/10240195-96fe-41a5-88f0-0f87a334b049)|

---

<br>

### 5. 글 제목과 내용검색, 마이페이지 정보 표시, 비밀번호 변경
|글 제목과 내용검색|마이페이지 정보 표시, 비밀번호 변경|
|---|---|
|- 제목으로 검색<br>- 내용으로 검색|- 유효성 검사<br>- 기존 비밀번호가 일치해야 변경 가능<br>- 새 비밀번호, 새 비밀번호 확인 시 변경 가능|
|![글제목내용검색](https://github.com/Giyong8504/memberBoard/assets/128211712/8aba1099-328d-45dd-a8fc-53b8dd86382d)|![마이페이지정보 비밀번호변경](https://github.com/Giyong8504/memberBoard/assets/128211712/b6bc4937-532d-458c-bf2b-22838f3532ab)|

---

<br>

### 6. 회원 탈퇴, 관리자 페이지 접근 권한
|회원 탈퇴|관리자 페이지 접근 권한|
|---|---|
|- 유효성 검사<br>- 기존 비밀번호와 일치해야 변경 가능|- ADMIN 권한 사용자만 가능<br>- test01@test.org (ADMIN) , kky5163@naver.com(USER)<br>![1번](https://github.com/Giyong8504/memberBoard/assets/128211712/092e42ce-826d-4914-bdf4-5166e1cd9207)|
|![회원탈퇴](https://github.com/Giyong8504/memberBoard/assets/128211712/15f5bc40-7a85-488b-af59-6c29aaf495df)|![관리자페이지접근권한](https://github.com/Giyong8504/memberBoard/assets/128211712/478e9a61-dbef-41b6-b103-946fa86f447d)|

---

<br>

### 7. USER권한, ADMIN권한
|USER 권한 : 회원 권한 수정, 강제 탈퇴, 회원 게시글 삭제 불가|ADMIN 권한 :  권한 수정, 강제 탈퇴, 회원 게시글 삭제 가능|
|---|---|
|- (경로 접근 권한을 주석처리 하여 모두 접근 가능한 상태.)<br>- USER 권한인 kky5163@naver.com 아이디로 테스트<br>![2번](https://github.com/Giyong8504/memberBoard/assets/128211712/6546f899-60f9-4a0e-9531-ee5e504cc6b0)|![3번](https://github.com/Giyong8504/memberBoard/assets/128211712/882fe65e-1ede-45df-bda1-d5504c777dda)|
|<br>![유저권한](https://github.com/Giyong8504/memberBoard/assets/128211712/df5cf74c-8942-4f61-b8ca-d7d6091bbcb3)|![어드민권한](https://github.com/Giyong8504/memberBoard/assets/128211712/a87d57c6-1e0f-42c3-afbb-2ec4db1da0e4)|

<br>

### 8. 배포 후 구글 OAuth 로그인
|현재 앱 인증받지 않은 상태 : 테스트 사용자로 등록된 사용자만 가능|
|---|
|![배포후구글로그인테스트](https://github.com/Giyong8504/memberBoard/assets/128211712/85b3fe6f-bdcb-421d-8046-3b9a5594dcca)|



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
- [15. 세션방식과 JWT 방식으로 OAuth2를 함께 구현](https://blog.naver.com/kky5163/223309099203)<br>
- [16. 글 검색 기능 추가](https://blog.naver.com/kky5163/223313370219)<br>
- [17_1. 댓글 기능 추가하기 (댓글 생성)](https://blog.naver.com/kky5163/223316180498)<br>
- [17_2. 댓글 기능 추가하기 (수정, 삭제)](https://blog.naver.com/kky5163/223317239322)<br>
- [18. 이메일 전송 - 회원가입 인증](https://blog.naver.com/kky5163/223322703326)<br>
- [19. 비밀번호 찾기 (가입한 이메일로 발송), 구글 유저는 유효성 검사로 안내](https://blog.naver.com/kky5163/223323237032)<br>
- [20. myPage 구현 (유저 정보, 비밀번호 변경, 비밀번호 변경 유효성 검사, 회원탈퇴)](https://blog.naver.com/kky5163/223332429905)<br>
- [21_1. 관리자 페이지 (회원 게시글 리스트, 관리자 권한 삭제)](https://blog.naver.com/kky5163/223334591018)<br>
- [21_2. 관리자 페이지 (회원 리스트, 권한 변경, 회원 탈퇴 처리)](https://blog.naver.com/kky5163/223336713641)<br>
- [22_1. AWS 배포하기](https://blog.naver.com/kky5163/223345686763)<br>
- [22_2. AWS 배포하기(Java 설치)](https://blog.naver.com/kky5163/223345795407)<br>
- [22_3. AWS 배포하기(DB설치)](https://blog.naver.com/kky5163/223346503252)<br>
- [22_4. AWS 배포하기(프로젝트 배포)](https://blog.naver.com/kky5163/223346544945)<br>
- [23. 도메인 구매후 연결하기](https://blog.naver.com/kky5163/223347573103)<br>
- [24. OAuth2.0 액세스 차단됨 : 승인 오류 - SSL 인증서 발급하고 적용하기](https://blog.naver.com/kky5163/223351377284)<br>


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
- [11. org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL](https://blog.naver.com/kky5163/223332393167)<br>
- [12. 배포 후 발생 오류 - Data truncation: Data too long for column 'password' at row 1](https://blog.naver.com/kky5163/223346954675)<br>
- [13. 배포 후 발생 오류 - Incorrect string value: '\xEA\xB9\x80\xEA\xB8\xB0...'](https://blog.naver.com/kky5163/223347065843)<br>
- [14. 배포 후 오류 - 403오류: disallowed_useragent](https://blog.naver.com/kky5163/223351509057)<br>
- [15. 배포 후 오류 - 403: access_denied](https://blog.naver.com/kky5163/223351527758)<br>


---

<br>

# 마치며

<details>
<summary>개선되어야 할 부분</summary>

- Session 기반과 OAuth 로그인을 같이 구현하면서 스프링 시큐리티에서 기본적으로 제공하는 csrf 공격 방지 기능은 OAuth 로그인 시 오류로 인해 disable로 해둔 상태입니다.
- Role에 ADMIN, USER, DISABLE, GOOGLE의 권한이 있는데, 접근 경로가 OAuth 로그인 사용자는 정상 작동하지 않는 문제점이 있어 .authenticated()으로 인증된 회원은 접근 가능하도록 해둔 상태입니다.
- ~~local에서는 OAuth 구글 로그인이 되지만 배포 후 https의 접근이 아니라서 승인오류가 나오는 상태입니다.~~(해결 완료)

</details>

## 후기
현재 재직 중인 회사를 다니며 학원에서 기본적인 학습을 마치고 팀 프로젝트를 진행했었습니다. 완성되지 못한 팀 프로젝트의 기간을 지내보면서 학원 기간 동안 최선을 다해 배우긴 했지만 실제 사용할 수 없던 제 스스로 너무 부족하다는 생각이 들었습니다.<br>
이래서는 안되겠다 싶어 먼저, 스스로 부족했던 Spring 책을 구입하여 다시 한번 더 공부 후 진행하였습니다.<br>
이후 기본에 충실해야 한다는 생각으로 백엔드 개발자의 기본이라 할 수 있는 게시판을 꼭 만들어 봐야겠다는 생각과 함께 바로 실행에 옮겼습니다.<br>
최초 목표는 현재 재직중에 있는 점을 감안하여 퇴근 후 개발을 주된 시간으로 삼아 약 2개월을 생각하고 진행하였으나 각종 오류들과 부족한 부분을 다시 공부하면서 개발하며 1개월이 더 소요되었습니다.<br>
<br>

처음에는 배웠던 내용이지만 막상 백지에서 하나씩 그려나간다고 생각하니 굉장히 막막했으나 백엔드 개발자를 지망한다면 이 정도는 해야 하지 않을까 하는 생각에 정리했던 학습내용을 되짚어보며 하나씩 구현해 나갔습니다.<br>
학원에서 배웠던 내용은 스프링 시큐리티에서 기본적으로 제공하는 Session기반의 로그인과 회원가입 부분이었으나 요즘 추세로는 JWT 토큰을 사용한 OAuth 로그인을 더욱 선호하기 때문에 같이 구현해 보고자 책을 구매하여 학습하였습니다.<br>
저자 신선영 님의 "스프링부트3 백엔드 개발자 되기-자바편"으로 먼저 학습하여 구현해 보고 제 프로젝트에 적용시켰습니다.<br>
<br>
기존의 Session 방식과 OAuth 로그인 방식에서 정보를 가져오는 방법, 토큰 처리, 리프레시 토큰, 세션 만료 등 함께 적용해 보고 유효성 검사, 권한으로 접근 제한 등을 구현해 보고 Thymeleaf 자바 기반 템플릿 엔진을 사용하여 필요한 부분의 정보를 가져와 뷰에 보여주는 등 구현 해보았습니다.<br>
이렇게 하나씩 구현해가면서 처음 보는 많은 오류들과 부딪혀보며 해결하고 부족한 부분들을 정리하면서 한번 더 공부하는 습관을 가지게 되었습니다.<br>
<br>
개인 포트폴리오를 준비하면서 배웠던 부분들을 직접 사용하고 구현하여 스스로 부족했던 부분을 개선하고 뒤죽박죽이었던 머릿속 퍼즐을 맞춰가며 자신감을 가지게 되는 계기가 되었습니다.<br>
MemberBoard 프로젝트는 더 나은 방향과 개발에 대한 두려움, 오류에 대한 두려움을 이겨낼 수 있던 뜻 깊은 시간이었습니다.<br>
<br>
**부족한 점이 많겠지만 끝까지 읽어주셔서 감사합니다!** :smile:
