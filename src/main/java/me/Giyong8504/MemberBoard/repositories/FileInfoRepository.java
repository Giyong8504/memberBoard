package me.Giyong8504.MemberBoard.repositories;

import com.querydsl.core.BooleanBuilder;
import me.Giyong8504.MemberBoard.entities.FileInfo;
import me.Giyong8504.MemberBoard.entities.QFileInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, QuerydslPredicateExecutor {

    // gid, location 으로 조회
    default List<FileInfo> getFiles(String gid, String location, String mode) {
        QFileInfo fileInfo = QFileInfo.fileInfo;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(fileInfo.gid.eq(gid));

        if (location != null && !location.isBlank()) {
            builder.and(fileInfo.location.eq(location));
        }

        // mode 가 완료 파일이거나 미완료 파일일 경우
        if (mode.equals("done")) builder.and(fileInfo.done.eq(true));
        else if (mode.equals("undone")) builder.and(fileInfo.done.eq(false));

        List<FileInfo> items = (List<FileInfo>)findAll(builder, Sort.by(Sort.Order.asc("regDt")));

        return items;
    }

    // 완료, 미완료 파일 모두 조회
    default List<FileInfo> getFiles(String gid, String location) {
        return getFiles(gid, location, "all");
    }

    // gid 만 가지고 조회
    default List<FileInfo> getFiles(String gid) {
        return getFiles(gid, null);
    }

    // 업로드가 완료된 이후(done이 붙은 것으로 조회) 파일만 조회
    default List<FileInfo> getFileDone(String gid, String location) {
        return getFiles(gid, location, "done");
    }
    default List<FileInfo> getFilesDone(String gid) {
        return getFiles(gid, null);
    }

    // 작업완료 처리
    default void processDone(String gid) {
        List<FileInfo> items = getFiles(gid);
        items.stream().forEach(item -> {
            item.setDone(true);
        });

        flush();
    }
}
