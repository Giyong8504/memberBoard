package me.Giyong8504.MemberBoard.commons;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {

    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    static { // properties 내용을 가져온다.
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    public static String getMessage(String code, String type) {
        type = StringUtils.hasText(type) ? type : "validations";

        ResourceBundle bundle = null;
        if (type.equals("commons")) {
            bundle = commonsBundle;
        } else if (type.equals("errors")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }

        return bundle.getString(code);
    }
}
