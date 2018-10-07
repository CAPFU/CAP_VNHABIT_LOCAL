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
        final String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
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
        final String regex = "^[0][0-9]{10,11}$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(phone).matches()){
            mErrorMsgListener.showError(ValidatorType.EMAIL, "email");
            return false;
        }
        return true;
    }

    public interface ErrorMsg {
        void showError(ValidatorType type, String key);
    }
}
