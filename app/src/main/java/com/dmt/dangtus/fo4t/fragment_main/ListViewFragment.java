package com.dmt.dangtus.fo4t.fragment_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.dmt.dangtus.fo4t.R;
import com.dmt.dangtus.fo4t.adapter.PlayerFootballAdapter;
import com.dmt.dangtus.fo4t.interfaces.MainInterface;
import com.dmt.dangtus.fo4t.model.Club;
import com.dmt.dangtus.fo4t.model.PlayerFootball;
import com.dmt.dangtus.fo4t.url.PlayerFootballUrl;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ListViewFragment extends ListFragment {

    private List<PlayerFootball> playerFootballList;
    private PlayerFootballAdapter adapter;
    private MainInterface mainInterface;
    private ProgressBar pbLoad;

    public ListViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainInterface = (MainInterface) getActivity();

        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        anhXa(view);

        playerFootballList = new ArrayList<>();
        adapter = new PlayerFootballAdapter(getActivity(), R.layout.item_player_football, playerFootballList);
        setListAdapter(adapter);
        getAllPlayerFootball();

        return view;
    }

    public void getAllPlayerFootball() {
        pbLoad.setVisibility(View.VISIBLE);

        PlayerFootballUrl.get("getAll.php", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for(int i=0; i<response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        PlayerFootball playerFootball = new PlayerFootball();
                        playerFootball.setId(object.getInt("id"));
                        playerFootball.setName(object.getString("ten_cau_thu"));
                        playerFootball.setImage(object.getString("hinh_anh"));
                        //set current club
                        JSONObject clubObject = object.getJSONObject("doi_tuyen_hien_tai");
                        Club currentClub = new Club(clubObject.getInt("id"), clubObject.getString("ten_clb"), clubObject.getString("hinh_anh"));
                        playerFootball.setCurrentClub(currentClub);

                        playerFootballList.add(playerFootball);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private void anhXa(View v) {
        pbLoad = v.findViewById(R.id.loadProgressBar);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        mainInterface.PlayerFootballDetail(playerFootballList.get(position));
    }
}
