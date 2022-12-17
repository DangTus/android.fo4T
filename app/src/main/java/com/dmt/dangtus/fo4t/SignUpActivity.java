package com.dmt.dangtus.fo4t;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dmt.dangtus.fo4t.model.User;
import com.dmt.dangtus.fo4t.module.NameModule;
import com.dmt.dangtus.fo4t.url.UserUrl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity {
    private TextView txtLoginLayout;
    private EditText edtFullName, edtPhoneNumber, edtEmail, edtUsername, edtPassword, edtPasswordConf;
    private Button btnSignup;
    private RelativeLayout lLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        anhXa();

        txtLoginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = edtFullName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String passwordConf = edtPasswordConf.getText().toString().trim();

                fullName = NameModule.nameHandling(fullName);

                if(fullName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConf.isEmpty()) {
                    String message = "Vui lòng nhập đầy đủ thông tin";
                    showDialogKetQua(false, message);
                } else if(!password.equals(passwordConf)){
                    String message = "Mật khẩu xác nhận phải trùng với mật khẩu";
                    showDialogKetQua(false, message);
                    edtPasswordConf.setText("");
                } else {
                    User user = new User();
                    user.setName(fullName);
                    user.setPhoneNumber(phoneNumber);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);
                    signup(user);
                }
            }
        });
    }

    private void signup(User user) {
        lLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("fullname", user.getName());
        params.put("phonenumber", user.getPhoneNumber());
        params.put("email", user.getEmail());
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());

        UserUrl.createNew("createNew.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Boolean trangThai = response.getBoolean("trang_thai");
                    String message = response.getString("message");
                    showDialogKetQua(trangThai, message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SignUpActivity.this, "Lỗi kết nối Intenet", Toast.LENGTH_SHORT).show();
                lLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showDialogKetQua(Boolean trangThai, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        if(trangThai) {
            builder.setTitle("Thông báo");
        } else {
            builder.setTitle("Lỗi");
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(trangThai) {
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        builder.show();
    }

    private void anhXa() {
        txtLoginLayout = findViewById(R.id.loginLayout);
        btnSignup = findViewById(R.id.signUpButton);

        edtFullName = findViewById(R.id.fullNameEditText);
        edtPhoneNumber = findViewById(R.id.phoneNumberEditText);
        edtEmail = findViewById(R.id.emailEditText);
        edtUsername = findViewById(R.id.userNameEditText);
        edtPassword = findViewById(R.id.passwordEditText);
        edtPasswordConf = findViewById(R.id.confirmPasswordEditText);

        lLoad = findViewById(R.id.loadLayout);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}