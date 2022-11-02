package com.dmt.dangtus.fo4t.fragment_main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dmt.dangtus.fo4t.LoginActivity;
import com.dmt.dangtus.fo4t.MainActivity;
import com.dmt.dangtus.fo4t.ProfileActivity;
import com.dmt.dangtus.fo4t.R;

public class MenuFragment extends Fragment {

    private Button btn;
    private TextView txtName;
    private SharedPreferences sharedPreferences;
    private int idUser;
    private LinearLayout lProfile;

    public MenuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        sharedPreferences = getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("id", 0);

        anhXa(view);

        setDataLayout();

        //su kien dang nhap, dang xuat
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idUser != 0) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("id");
                    editor.commit();
                }
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //sukien xem profile
        lProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idUser != 0) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void setDataLayout() {
        if(idUser == 0) {
            btn.setText("Đăng nhập");
            txtName.setText("Vui lòng đăng nhập");
        } else {
            String nameUser = sharedPreferences.getString("name", "");
            btn.setText("Đăng xuất");
            txtName.setText(nameUser);
        }
    }

    private void anhXa(View view) {
        btn = view.findViewById(R.id.tButton);
        txtName = view.findViewById(R.id.nameTextView);
        lProfile = view.findViewById(R.id.profileLayout);
    }
}
