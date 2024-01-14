package me.Giyong8504.MemberBoard.commons.rests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JSONData<T> {
    private HttpStatus status = HttpStatus.OK;
    private boolean success = true;

    private T data;
    @NonNull
    private String message;
}