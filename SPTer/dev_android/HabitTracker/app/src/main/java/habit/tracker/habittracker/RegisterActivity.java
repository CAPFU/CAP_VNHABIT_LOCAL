package habit.tracker.habittracker;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import habit.tracker.habittracker.api.ApiUtils;
import habit.tracker.habittracker.api.model.user.User;
import habit.tracker.habittracker.api.model.user.UserResult;
import habit.tracker.habittracker.api.service.ApiService;
import habit.tracker.habittracker.common.Validator;
import habit.tracker.habittracker.common.ValidatorType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    Button btnRegister;
    TextView linkLogin;

    EditText edUsername;
    EditText edPhone;
    EditText edEmail;
    EditText edPassword;
    EditText edPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        linkLogin = findViewById(R.id.link_login);
        linkLogin.setOnClickListener(this);
        String registerText = getResources().getString(R.string.remind_login);
        SpannableString content = new SpannableString(registerText);
        content.setSpan(new UnderlineSpan(), 0, registerText.length(), 0);
        linkLogin.setText(content);

        edUsername = findViewById(R.id.edit_username);
        edPhone = findViewById(R.id.edit_phone);
        edEmail = findViewById(R.id.edit_email);
        edPassword = findViewById(R.id.edit_password);
        edPasswordConfirm = findViewById(R.id.edit_conf_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String phone = edPhone.getText().toString();
                String email = edEmail.getText().toString();
                String passwordConf = edPasswordConfirm.getText().toString();
                User newUser = new User();
                Validator validator = new Validator();
                validator.setErrorMsgListener(new Validator.ErrorMsg() {
                    @Override
                    public void showError(ValidatorType type, String key) {
                        switch (type) {
                            case EMPTY:
                                Toast.makeText(RegisterActivity.this, key + " không được rỗng", Toast.LENGTH_SHORT).show();
                                break;
                            case PHONE:
                                Toast.makeText(RegisterActivity.this, key + " không đúng", Toast.LENGTH_SHORT).show();
                                break;
                            case EMAIL:
                                Toast.makeText(RegisterActivity.this, key + " không đúng", Toast.LENGTH_SHORT).show();
                                break;
                            case EQUAL:
                                Toast.makeText(RegisterActivity.this, key + " không trùng khớp", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                if (!validator.checkEmpty("Tên tài khoản", username)
                        || !validator.checkEmpty("Số điện thoại", phone)
                        || !validator.checkEmpty("Email", email)
                        || !validator.checkEmpty("Mật khẩu", password)
                        || !validator.checkEmpty("Mật khẩu", passwordConf)) {
                    return;
                }
                if (!validator.checkPhone(phone)) {
                    return;
                }
                if (!validator.checkEmail(email)) {
                    return;
                }
                if (!validator.checkEqual(password, passwordConf, "Mật khẩu")) {
                    return;
                }
                register(newUser);
                break;
            case R.id.btn_fb_login:
                showEmptyScreen();
                break;
            case R.id.btn_google_login:
                showEmptyScreen();
                break;
            case R.id.link_login:
                finish();
                break;
        }
    }

    private void register(User user) {
        ApiService mService = ApiUtils.getApiService();
        mService.addUser(user).enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                if (response.body().getResult().equals("1")) {
                    Toast.makeText(RegisterActivity.this, "Tài khoản đã tạo", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "not ok", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "not ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
