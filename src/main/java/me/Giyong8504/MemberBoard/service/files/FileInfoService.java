package me.Giyong8504.MemberBoard.service.files;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.FileInfo;
import me.Giyong8504.MemberBoard.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileInfoService { // 파일 개별조회, 목록조회 기능

    private final HttpServletRequest request;
    private final FileInfoRepository repository;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.url}")
    private String uploadUrl;

    // 파일 등록 번호를 사용한 개별 조회
    public FileInfo get(Long id) {
        FileInfo item = repository.findById(id).orElseThrow(FileNotFoundException::new);
        addFileInfo(item);

        return item;
    }

    public List<FileInfo> getList(Options opts) {

        return null;
    }

    /**
     *파일 업로드 서버 경로(filePath)
     * 파일 서버 접속 URL (fileUrl)
     */
    public void addFileInfo(FileInfo item) {
        long id = item.getId();
        String extension = item.getExtension();

        // 확장자가 있을 경우, 확장자 없을경우 Id만
        String fileName = getFileName(id, extension);
        long folder = id % 10L; // 각각 폴더 경로

        // 파일 업로드 서버 경로
        String fileDir = uploadPath + folder;
        String filePath = fileDir + "/" + fileName;

        // 파일경로 체크 후 없을 경우 생성.
        File _fileDir = new File(fileDir);
        if (!_fileDir.exists()) {
            _fileDir.mkdir();
        }

        // 파일 서버 접속 URL (fileUrl)
        String fileUrl = request.getContextPath() + uploadUrl + folder + "/" + fileName;


    }
    private String getFileName(long id, String extension) {
        return extension == null || extension.isBlank() ? "" + id : id + "." + extension;
    }

    @Data
    @Builder
    static class Options { // 여기서만 사용하므로 내부클래스 작성.
        private String gid;
        private String location;
        private SearchMode mode = SearchMode.ALL;
    }

    static enum SearchMode {
        ALL,
        DONE,
        UNDONE
    }

}
