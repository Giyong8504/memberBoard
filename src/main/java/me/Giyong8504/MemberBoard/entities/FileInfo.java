package me.Giyong8504.MemberBoard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = { // 색인을 통해 성능향상. 조회가 많이 되는 부분
        @Index(name = "idx_fileinfo_gid", columnList = "gid"),
        @Index(name = "idx_fileinfo_gid_location", columnList = "gid,location")
})
public class FileInfo extends BaseMemberEntity{ // 로그인 아이디 비교를 위한 상속

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false) // 파일을 묶기 위한 그룹
    private String gid = UUID.randomUUID().toString(); // 랜덤으로 중복되지 않는 unique 아이디 생성

    @Column(length = 50)
    private String location; // 게시물 각각 이미지의 위치

    @Column(length = 100, nullable = false)
    private String fileName; // 파일명

    @Column(length = 50)
    private String extension; // 확장자

    @Column(length = 70)
    private String fileType;

    private boolean done; // 작성 완료 시 성공여부 (성공 파일만 남겨두기)

    /* 내부에서 관리할 용도. 파일 정보 조회시에 같이 가져올 데이터*/
    @Transient
    private String filePath; // 실제 서버 업로드 경로

    @Transient
    private String fileUrl; // 서버 접속 url

}
