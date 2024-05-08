package me.Giyong8504.MemberBoard.service.files;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final HttpServletResponse response;
    private final FileInfoService fileInfoService;

    public void download(Long id) {
        FileInfo item = fileInfoService.get(id);

        // 파일이 있는지 확인
        String filePath = item.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) { // 파일이 존재하지 않는 경우.
            throw new FileNotFoundException();
        }

        String fileName = item.getFileName();
        try {
            fileName = new String(fileName.getBytes(), "ISO8859_1"); // 한글제목 파일 받기 위한 설정
        } catch (UnsupportedEncodingException e) {}

        try (FileInputStream fis = new FileInputStream(file); // 파일 읽어오기.
             BufferedInputStream bis = new BufferedInputStream(fis)) { // 버퍼를 사용한 입력

            OutputStream out = response.getOutputStream();
            // Http 응답 헤더 설정
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName); // 다운로드 설정부분
            response.setHeader("Content-Type", "application/octet-stream"); // 파일 형식
            response.setHeader("Cache-Control", "must-revalidate"); // 캐시 제어
            response.setHeader("Pragma", "public");
            response.setIntHeader("Expires", 0); // 캐시 만료
            response.setHeader("Content-Length", "" + file.length()); // 파일 크기

            while (bis.available() > 0) {
                out.write(bis.read());
            }

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
