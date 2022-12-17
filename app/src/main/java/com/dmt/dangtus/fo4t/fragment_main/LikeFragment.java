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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.dmt.dangtus.fo4t.LoginActivity;
import com.dmt.dangtus.fo4t.R;
import com.dmt.dangtus.fo4t.adapter.PlayerFootballAdapter;
import com.dmt.dangtus.fo4t.interfaces.MainInterface;
import com.dmt.dangtus.fo4t.model.Club;
import com.dmt.dangtus.fo4t.model.PlayerFootball;
import com.dmt.dangtus.fo4t.url.LikeUrl;
import com.dmt.dangtus.fo4t.url.PlayerFootballUrl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LikeFragment extends ListFragment {
    private ProgressBar pbLoad;
    private LinearLayout lError;
    private Button btnThuLai;

    private List<PlayerFootball> playerFootballList;
    private PlayerFootballAdapter adapter;
    private MainInterface mainInterface;

    private SharedPreferences sharedPreferences;
    private int idUser;

    public LikeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainInterface = (MainInterface) getActivity();

        View view = inflater.inflate(R.layout.fragment_like, container, false);
        anhXa(view);

        setDataLayout();

        return view;
    }

    private void setDataLayout() {
        sharedPreferences = getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("id", 0);

        if(idUser == 0) {
            lError.setVisibility(View.VISIBLE);
        } else {
            lError.setVisibility(View.INVISIBLE);

            //load data football player
            playerFootballList = new ArrayList<>();
            adapter = new PlayerFootballAdapter(getActivity(), R.layout.item_player_football, playerFootballList);
            setListAdapter(adapter);
            getAllPlayerFootball();
        }

        btnThuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getAllPlayerFootball() {
        pbLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("id", idUser);

        LikeUrl.get("getByIDUser.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(response.getBoolean("trang_thai")) {
                        JSONArray data = response.getJSONArray("data");
                        serDataListView(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                pbLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "Lỗi kết nối internet", Toast.LENGTH_SHORT).show();
                pbLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void serDataListView(JSONArray jsonData) throws JSONException {
        int jsonDataLen = jsonData.length();

        for (int i = 0; i < jsonDataLen; i++) {
            JSONObject playerFootballObject = jsonData.getJSONObject(i);

            PlayerFootball playerFootball = new PlayerFootball();
            playerFootball.setId(playerFootballObject.getInt("id"));
            playerFootball.setName(playerFootballObject.getString("ten_cau_thu"));
            playerFootball.setImage(playerFootballObject.getString("hinh_anh"));
            //set current club
            JSONObject clubObject = playerFootballObject.getJSONObject("doi_tuyen_hien_tai");
            Club currentClub = new Club(clubObject.getInt("id"), clubObject.getString("ten_clb"), clubObject.getString("hinh_anh"));
            playerFootball.setCurrentClub(currentClub);

            playerFootballList.add(playerFootball);
        }

        adapter.notifyDataSetChanged();
    }

    private void anhXa(View v) {
        pbLoad = v.findViewById(R.id.loadProgressBar);
        lError = v.findViewById(R.id.errorLayout);
        btnThuLai = v.findViewById(R.id.thuLaiButton);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        mainInterface.PlayerFootballDetail(playerFootballList.get(position));
    }
}
