package habit.tracker.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import habit.tracker.habittracker.api.ApiUtils;
import habit.tracker.habittracker.api.model.user.UserResponse;
import habit.tracker.habittracker.api.service.ApiService;
import habit.tracker.habittracker.common.Validator;
import habit.tracker.habittracker.common.ValidatorType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText edUsername;
    EditText edPassword;
    Button btnLogin;
    TextView linkRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edit_username);
        edPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_login);
        linkRegister = findViewById(R.id.link_register);
        String registerText = getResources().getString(R.string.register_account);
        SpannableString content = new SpannableString(registerText);
        content.setSpan(new UnderlineSpan(), 0, registerText.length(), 0);
        linkRegister.setText(content);

        btnLogin.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = edUsername.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                Validator validator = new Validator();
                validator.setErrorMsgListener(new Validator.ErrorMsg() {
                    @Override
                    public void showError(ValidatorType type, String key) {
                        Toast.makeText(LoginActivity.this, key + " is empty", Toast.LENGTH_SHORT).show();
                    }
                });
                if (!validator.checkEmpty("username", username)
                        || !validator.checkEmpty("password", password)) {
                    return;
                }
                login(username, password);
                break;
            case R.id.btn_fb_login:
                showEmptyScreen();
                break;
            case R.id.btn_google_login:
                showEmptyScreen();
                break;
            case R.id.link_register:
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                break;
        }
    }

    private void login(String username, String password) {
        ApiService mService = ApiUtils.getApiService();
        mService.getUser(username, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body().getResult().equals("1")) {
                    Toast.makeText(LoginActivity.this, "Login ok!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed! username or password is not correct!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed! username or password is not correct!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
