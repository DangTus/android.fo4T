package com.dmt.dangtus.fo4t.fragment_player_football_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.dmt.dangtus.fo4t.R;
import com.dmt.dangtus.fo4t.adapter.ClubCareerAdapter;
import com.dmt.dangtus.fo4t.model.Club;
import com.dmt.dangtus.fo4t.model.ClubCareer;
import com.dmt.dangtus.fo4t.model.PlayerFootball;
import com.dmt.dangtus.fo4t.url.ClubCareerUrl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ClubFragment extends ListFragment {

    private List<ClubCareer> clubCareerList;
    private ClubCareerAdapter adapter;
    private int idPlayerFootball;
    private ProgressBar pbLoad;

    public ClubFragment(int idPlayerFootball) {
        this.idPlayerFootball = idPlayerFootball;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club, container, false);

        pbLoad = view.findViewById(R.id.loadProgressBar);

        clubCareerList = new ArrayList<>();
        adapter = new ClubCareerAdapter(getActivity(), R.layout.item_club, clubCareerList);
        setListAdapter(adapter);
        getClub();

        return view;
    }

    private void getClub() {
        pbLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("id", idPlayerFootball);

        ClubCareerUrl.getByIDPlayerFootball("getByIDPlayerFootball.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for(int i=0; i<response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        ClubCareer clubCareer = new ClubCareer();
                        clubCareer.setYearStart(object.getString("nam_bat_dau"));
                        clubCareer.setYearFinal(object.getString("nam_ket_thuc"));
                        clubCareer.setLease(object.getInt("thue") == 1 ? true : false);
                        //setClub
                        JSONObject clubObject = object.getJSONObject("cau_lac_bo");
                        clubCareer.setClub(new Club(clubObject.getInt("id"), clubObject.getString("ten_clb"), clubObject.getString("hinh_anh")));

                        clubCareerList.add(clubCareer);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                pbLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                pbLoad.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Lỗi kết nối internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}