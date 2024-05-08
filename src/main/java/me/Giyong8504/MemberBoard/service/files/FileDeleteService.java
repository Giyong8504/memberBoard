package me.Giyong8504.MemberBoard.service.files;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.commons.UserUtil;
import me.Giyong8504.MemberBoard.entities.FileInfo;
import me.Giyong8504.MemberBoard.models.user.UserInfo;
import me.Giyong8504.MemberBoard.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final UserUtil userUtil;
    private final FileInfoService fileInfoService;
    private final FileInfoRepository fileInfoRepository;

    public void delete(Long id) {
        FileInfo item = fileInfoService.get(id); // id 정보 조회

        /* 파일 삭제 권한 체크 S */
        // 업로드한 사용자와 로그인한 회원이 같은지 확인, 관리자 권한인지 확인
        String createBy = item.getCreateBy();
        UserInfo user = userUtil.getMember();
        if (createBy != null && createBy.isBlank() && !userUtil.isAdmin()
                && (!userUtil.isLogin() || userUtil.isLogin() && user.getEmail().equals(createBy))) {
            throw new ArithmeticException("UnAuthorized.delete.file");
        }
        /* 파일 삭제 권한 체크 E */

        /**
         * 1. 파일 삭제
         * 2. 파일 정보삭제
         */

        File file = new File(item.getFilePath());
        if (file.exists()) file.delete(); // 파일 삭제 부분.

        fileInfoRepository.delete(item);
        fileInfoRepository.flush();
    }
}
