package me.Giyong8504.MemberBoard.service.files;

import me.Giyong8504.MemberBoard.commons.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {
    public FileNotFoundException() { // 파일 없을 시 발생.
        super(bundleError.getString("NotFound.file"), HttpStatus.BAD_REQUEST);
    }

}
