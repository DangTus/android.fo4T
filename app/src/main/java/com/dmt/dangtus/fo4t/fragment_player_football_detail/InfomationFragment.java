package com.dmt.dangtus.fo4t.fragment_player_football_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dmt.dangtus.fo4t.R;
import com.dmt.dangtus.fo4t.model.Country;
import com.dmt.dangtus.fo4t.url.PlayerFootballUrl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class InfomationFragment extends Fragment {

    private int idPlayerFootball;
    private TextView txtNgaySinh, txtQuocGia, txtChieuCao, txtCanNang, txtDanhTieng;
    private ImageView imvQuocGia;
    private RatingBar rbKyThuat;
    private ProgressBar pbLoad;
    private LinearLayout lError, lInfo;
    private Button btnThuLai;

    public InfomationFragment(int idPlayerFootball) {
        this.idPlayerFootball = idPlayerFootball;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infomation, container, false);

        anhXa(view);

        pbLoad.setVisibility(View.VISIBLE);
        lError.setVisibility(View.INVISIBLE);
        lInfo.setVisibility(View.INVISIBLE);

        getInfomation();

        btnThuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfomation();
            }
        });

        return view;
    }

    private void getInfomation() {
        pbLoad.setVisibility(View.VISIBLE);
        lError.setVisibility(View.INVISIBLE);
        lInfo.setVisibility(View.INVISIBLE);

        RequestParams params = new RequestParams();
        params.put("id", idPlayerFootball);

        PlayerFootballUrl.get("getByID.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject object = response.getJSONObject(0);

                    txtNgaySinh.setText(object.getString("ngay_sinh"));
                    txtChieuCao.setText(object.getString("chieu_cao"));
                    txtCanNang.setText(object.getString("can_nang"));
                    txtDanhTieng.setText(object.getString("danh_tieng"));
                    rbKyThuat.setRating(object.getInt("ky_thuat"));
                    //set quoc gia
                    JSONObject countryObject = object.getJSONObject("quoc_gia");
                    txtQuocGia.setText(countryObject.getString("ten_quoc_gia"));
                    Glide.with(getActivity()).load(countryObject.getString("hinh_anh")).fitCenter().into(imvQuocGia);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pbLoad.setVisibility(View.INVISIBLE);
                lInfo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                pbLoad.setVisibility(View.INVISIBLE);
                lError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void anhXa(View view) {
        txtNgaySinh = view.findViewById(R.id.ngaySinhTextView);
        txtQuocGia = view.findViewById(R.id.quocGiaTextView);
        txtChieuCao = view.findViewById(R.id.chieuCaoTextView);
        txtCanNang = view.findViewById(R.id.canNangTextView);
        rbKyThuat = view.findViewById(R.id.kyThuatRatingBar);
        txtDanhTieng = view.findViewById(R.id.danhTiengTextView);
        imvQuocGia = view.findViewById(R.id.quocGiaImage);

        pbLoad = view.findViewById(R.id.loadProgressBar);
        lError = view.findViewById(R.id.errorLayout);
        btnThuLai = view.findViewById(R.id.thuLaiButton);
        lInfo = view.findViewById(R.id.thongTinLayout);
    }
}