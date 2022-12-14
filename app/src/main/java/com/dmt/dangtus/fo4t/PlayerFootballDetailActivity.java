package com.dmt.dangtus.fo4t;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmt.dangtus.fo4t.fragment_player_football_detail.VPAdapterPFD;
import com.dmt.dangtus.fo4t.model.Club;
import com.dmt.dangtus.fo4t.model.Country;
import com.dmt.dangtus.fo4t.model.PlayerFootball;
import com.dmt.dangtus.fo4t.url.LikeUrl;
import com.dmt.dangtus.fo4t.url.PlayerFootballUrl;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PlayerFootballDetailActivity extends AppCompatActivity {

    private TabLayout tabLayoutPFDetail;
    private ViewPager2 viewPagerPFDetail;
    private ImageView imvAvata;
    private ImageButton imbLike;
    private TextView txtName;
    private VPAdapterPFD vpAdapterPFD;
    private PlayerFootball playerFootball;
    private SharedPreferences sharedPreferences;
    int idUser;
    private RelativeLayout lLoad;
    private Boolean like = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_football_detail);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("id", 0);

        anhXa();

        getData(getIntent());

        vpAdapterPFD = new VPAdapterPFD(this, playerFootball.getId());
        viewPagerPFDetail.setAdapter(vpAdapterPFD);

        new TabLayoutMediator(tabLayoutPFDetail, viewPagerPFDetail, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Th??ng tin");
                    break;
                case 1:
                    tab.setText("S??? nghi???p CLB");
                    break;
            }
        }).attach();
    }

    private void getData(Intent intent) {
        playerFootball = (PlayerFootball) intent.getSerializableExtra("cau_thu");

        //set data in layout
        txtName.setText(playerFootball.getName());
        getLike(playerFootball.getId());
        Glide.with(this).load(playerFootball.getImage()).fitCenter().into(imvAvata);
    }

    private void getLike(int idSP) {
        lLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("user", idUser);
        params.put("player", idSP);

        LikeUrl.get("check.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    setLike(response.getBoolean("trang_thai"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(PlayerFootballDetailActivity.this, responseString, Toast.LENGTH_SHORT).show();
                lLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void likePlayer(View view) {
        if(idUser == 0) {
            Toast.makeText(this, "Vui l??ng ????ng nh???p", Toast.LENGTH_SHORT).show();
        } else {
            lLoad.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.put("user", idUser);
            params.put("player", playerFootball.getId());
            params.put("like", like);

            LikeUrl.get("set.php", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        if (response.getBoolean("trang_thai")) {
                            Boolean likeNew = response.getBoolean("like_new");
                            setLike(likeNew);

                            if(likeNew) {
                                Toast.makeText(PlayerFootballDetailActivity.this, "Th??m v??o y??u th??ch th??nh c??ng", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PlayerFootballDetailActivity.this, "B??? y??u th??ch th??nh c??ng", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PlayerFootballDetailActivity.this, "C?? l???i trong l??c th???c hi???n thay ?????i. Vui l??ng th??? l???i", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    lLoad.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(PlayerFootballDetailActivity.this, responseString, Toast.LENGTH_SHORT).show();
                    lLoad.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
    
    private void setLike(Boolean l) {
        if (l) {
            imbLike.setImageResource(R.drawable.ic_like);
            like = true;
        } else {
            imbLike.setImageResource(R.drawable.ic_unlike);
            like = false;
        }
    }

    private void anhXa() {
        tabLayoutPFDetail = findViewById(R.id.PFDTabLayout);
        viewPagerPFDetail = findViewById(R.id.PFDViewPager2);
        imvAvata = findViewById(R.id.avataIMV);
        txtName = findViewById(R.id.nameTextView);
        imbLike = findViewById(R.id.likeIMB);
        lLoad = findViewById(R.id.loadLayout);
    }
}