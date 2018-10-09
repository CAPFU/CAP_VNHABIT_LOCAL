package habit.tracker.habittracker.common;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class Validator {
    private ErrorMsg mErrorMsgListener;

    public void setErrorMsgListener(ErrorMsg errorMsgListener) {
        mErrorMsgListener = errorMsgListener;
    }

    public boolean checkEmpty(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            mErrorMsgListener.showError(ValidatorType.EMPTY, key);
            return false;
        }
        return true;
    }

    public boolean checkEmail(String email) {
        final String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(email).matches()){
            mErrorMsgListener.showError(ValidatorType.EMAIL, "email");
            return false;
        }
        return true;
    }

    public boolean checkEqual(String str, String str1, String key) {
        if (!str.equals(str1)) {
            mErrorMsgListener.showError(ValidatorType.EQUAL, key);
            return false;
        }
        return true;
    }

    public boolean checkPhone(String phone) {
        final String regex = "^[0][0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        if(phone == null || phone.length() < 10
                || phone.length() > 15
                || !pattern.matcher(phone).matches()){
            mErrorMsgListener.showError(ValidatorType.PHONE, "Số điện thoại");
            return false;
        }
        return true;
    }

    public interface ErrorMsg {
        void showError(ValidatorType type, String key);
    }
}
