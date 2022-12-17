package com.dmt.dangtus.fo4t;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ProfileActivity extends AppCompatActivity {
    private TextView txtName, txtUsername, txtEmail, txtPhonenumber, txtLike;
    private RelativeLayout lLoad;
    private Button btnEditInfoLayout, btnEditPassLayout;
    private SharedPreferences sharedPreferences;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        anhXa();

        user = new User();

        sharedPreferences = this.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        user.setId(sharedPreferences.getInt("id", 0));

        getDataUser();

        btnEditInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayoutEditInfo();
            }
        });

        btnEditPassLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayoutEditPass();
            }
        });
    }

    private void getDataUser() {
        lLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("id", user.getId());

        UserUrl.getByID("getByID.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("trang_thai")) {
                        JSONObject data = response.getJSONObject("data");

                        user.setName(data.getString("ten_nguoi_dung"));
                        user.setUsername(data.getString("tai_khoan"));
                        user.setEmail(data.getString("email"));
                        user.setPhoneNumber(data.getString("so_dien_thoai"));
                        user.setCountLike(data.getInt("so_cau_thu_like"));

                        setDataLayout();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối internet", Toast.LENGTH_SHORT).show();
                lLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setDataLayout() {
        txtName.setText(user.getName());
        txtUsername.setText("@" + user.getUsername());
        txtEmail.setText(user.getEmail());
        txtPhonenumber.setText(user.getPhoneNumber());
        txtLike.setText(user.getCountLike() + " Player Football");
    }

    private void showLayoutEditInfo() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_user_edit);
        dialog.setCanceledOnTouchOutside(false);

        //anh xa
        ImageButton btnHuy = dialog.findViewById(R.id.huy_button);
        Button btnLuu = dialog.findViewById(R.id.luu_button);
        EditText edtFullname = dialog.findViewById(R.id.fullNameEditText);
        EditText edtPhonenumber = dialog.findViewById(R.id.phoneNumberEditText);
        EditText edtEmail = dialog.findViewById(R.id.emailEditText);

        //set data
        edtFullname.setText(user.getName());
        edtPhonenumber.setText(user.getPhoneNumber());
        edtEmail.setText(user.getEmail());

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = edtFullname.getText().toString();
                String phonenumber = edtPhonenumber.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                fullname = NameModule.nameHandling(fullname);

                if (fullname.isEmpty() || phonenumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    editInfo(fullname, phonenumber, email);
                }
            }
        });

        dialog.show();
    }

    private void editInfo(String fullname, String phonenumber, String email) {
        lLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("id", user.getId());
        params.put("fullname", fullname);
        params.put("phonenumber", phonenumber);
        params.put("email", email);

        UserUrl.edit("editInfo.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("trang_thai")) {
                        getDataUser();
                    } else {
                        showLayoutEditInfo();
                    }
                    Toast.makeText(ProfileActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối internet", Toast.LENGTH_SHORT).show();
                lLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showLayoutEditPass() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pass_user_edit);
        dialog.setCanceledOnTouchOutside(false);

        //anh xa
        ImageButton btnHuy = dialog.findViewById(R.id.huy_button);
        Button btnLuu = dialog.findViewById(R.id.luu_button);
        EditText edtPassOld = dialog.findViewById(R.id.passwordOldEditText);
        EditText edtPassNew = dialog.findViewById(R.id.passwordNewEditText);
        EditText edtPassConf = dialog.findViewById(R.id.passwordConfirmEditText);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passOld = edtPassOld.getText().toString().trim();
                String passNew = edtPassNew.getText().toString().trim();
                String passConf = edtPassConf.getText().toString().trim();

                if (passOld.isEmpty() || passNew.isEmpty() || passConf.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else if (!passNew.equals(passConf)) {
                    Toast.makeText(ProfileActivity.this, "Mật khẩu xác nhận phải trùng với mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    editPass(passOld, passNew);
                }
            }
        });

        dialog.show();
    }

    private void editPass(String passOld, String passNew) {
        lLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("id", user.getId());
        params.put("pass_old", passOld);
        params.put("pass_new", passNew);

        UserUrl.edit("editPass.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.getBoolean("trang_thai")) {
                        showLayoutEditPass();
                    }
                    Toast.makeText(ProfileActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối internet", Toast.LENGTH_SHORT).show();
                lLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void anhXa() {
        txtName = findViewById(R.id.nameTextView);
        txtUsername = findViewById(R.id.usernameTextView);
        txtEmail = findViewById(R.id.emailTextView);
        txtPhonenumber = findViewById(R.id.phoneNumberTextView);
        txtLike = findViewById(R.id.likeTextView);

        btnEditInfoLayout = findViewById(R.id.editInfoLayoutButton);
        btnEditPassLayout = findViewById(R.id.editPassLayoutButton);

        lLoad = findViewById(R.id.loadLayout);
    }
}