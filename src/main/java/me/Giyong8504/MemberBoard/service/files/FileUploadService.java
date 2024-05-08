package me.Giyong8504.MemberBoard.service.files;

import lombok.RequiredArgsConstructor;
import me.Giyong8504.MemberBoard.entities.FileInfo;
import me.Giyong8504.MemberBoard.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService { // 파일정보를 저장하고 업로드 처리 방법을 사용

    private final FileInfoService fileInfoService;
    private final FileInfoRepository fileInfoRepository;

    // 업로드 시 성공한 파일을 반환
    public List<FileInfo> upload(MultipartFile[] files, String gid, String location) {

        // gid가 없으면 java util 패키지의 UUID로 랜덤생성, 있으면 gid.
        gid = gid == null || gid.isBlank() ? UUID.randomUUID().toString() : gid;

        List<FileInfo> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileType = file.getContentType();
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);

            // 파일 정보를 저장
            FileInfo item = FileInfo.builder()
                    .fileType(fileType)
                    .fileName(fileName)
                    .extension(extension)
                    .location(location)
                    .gid(gid)
                    .build();

            fileInfoRepository.saveAndFlush(item);

            // 파일 서버 접속 URL, 경로, 정보 등.
            fileInfoService.addFileInfo(item); // 파일 저장 처리 완료.

            /* 파일 업로드 처리 S */
            try {
                File _file = new File(item.getFilePath());
                file.transferTo(_file);
                uploadedFiles.add(item);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return uploadedFiles;
    }

    public List<FileInfo> upload(MultipartFile[] files, String gid) {
        return upload(files, gid, null);
    }

    //gid 없는경우 자동생성
    private List<FileInfo> upload(MultipartFile[] files) {
        return upload(files, null);
    }
}
