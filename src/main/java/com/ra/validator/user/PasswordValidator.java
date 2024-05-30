package com.ra.validator.user;


public class PasswordValidator {
    public static String passwordValidation(String password)
    {
        if (password.length() > 15 || password.length() < 8)
        {
            return "Mật khẩu phải dài ít hơn 15 và dài hơn 8 ký tự!";
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            return "Mật khẩu phải chứa ít nhất một chữ cái viết hoa!";
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            return "Mật khẩu phải chứa ít nhất một chữ cái viết thường!";
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            return "Mật khẩu phải chứa ít nhất một số!";
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars))
        {
            return "Mật khẩu phải chứa ít nhất một ký tự đặc biệt!";
        }
        return null;
    }
}
