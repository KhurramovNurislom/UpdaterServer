package uz.lb.updaterserver.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uz.lb.updaterserver.annotation.StrongPassword;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

//            ^                             →   boshlanishi
//            (?=.*[a-z])                   →   kamida bitta kichik harf
//            (?=.*[A-Z])                   →   kamida bitta katta harf
//            (?=.*\\d)                     →   kamida bitta raqam
//            (?=.*[@$!%*?&])               →   kamida bitta maxsus belgi
//            [A-Za-z\\d@$!%*?&]{8,}        →   jami uzunligi 8 dan kam bo‘lmasin, faqat ruxsat etilgan belgilar
//            $                             →   tugashi

    private static final String PASSWORD_PATTERN = "^\\S+$";
//            "^(?=.*[a-z])" +
//                    "(?=.*[A-Z])" +
//                    "(?=.*\\d)" +
//                    "(?=.*[@$!%*?&])" +
//                    "[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        return password.matches(PASSWORD_PATTERN);
    }
}
