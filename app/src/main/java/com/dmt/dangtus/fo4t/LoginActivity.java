package com.dmt.dangtus.fo4t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dmt.dangtus.fo4t.model.Club;
import com.dmt.dangtus.fo4t.model.PlayerFootball;
import com.dmt.dangtus.fo4t.url.PlayerFootballUrl;
import com.dmt.dangtus.fo4t.url.UserUrl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserName, edPassword;
    private TextView txtSignUp;
    private Button btnLogin;
    private ImageButton imbEye;
    private RelativeLayout lLoad;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        edUserName.setText(sharedPreferences.getString("userName", ""));
        edPassword.setText(sharedPreferences.getString("password", ""));

        //bat su kien click vao nut dang nhap
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edUserName.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if(userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    login(userName, password);
                }
            }
        });

        imbEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Integer) imbEye.getTag() == R.drawable.ic_eye_hide) {
                    imbEye.setImageResource(R.drawable.ic_eye);
                    imbEye.setTag(R.drawable.ic_eye);

                    //129 la kieu password
                    edPassword.setInputType(129);
                } else if ((Integer) imbEye.getTag() == R.drawable.ic_eye) {
                    imbEye.setImageResource(R.drawable.ic_eye_hide);
                    imbEye.setTag(R.drawable.ic_eye_hide);

                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                //dua vi tri con tro ve phia cuoi van ban
                int position = edPassword.length();
                Editable etext = edPassword.getText();
                Selection.setSelection(etext, position);
            }
        });
    }

    private void login(String user, String pass) {
        lLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("username", user);
        params.put("password", pass);

        UserUrl.checkAccount("checkLogin.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(response.getString("trang_thai").equals("success")) {
                        JSONObject data = response.getJSONObject("data");

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id", data.getInt("id"));
                        editor.putString("name", data.getString("ten_nguoi_dung"));
                        editor.putString("email", data.getString("email"));
                        editor.putString("userName", user);
                        editor.putString("password", pass);
                        editor.putBoolean("remember", true);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Ten nguoi dung hoac mat khau khong dung", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối internet", Toast.LENGTH_SHORT).show();
                lLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void anhXa() {
        txtSignUp = findViewById(R.id.signUpLayout);

        btnLogin = findViewById(R.id.loginButton);

        edUserName = findViewById(R.id.userNameEditText);
        edPassword = findViewById(R.id.passwordEditText);

        imbEye = (ImageButton) findViewById(R.id.eyeIMB);
        imbEye.setTag(R.drawable.ic_eye);

        lLoad = findViewById(R.id.loadLayout);
    }
}