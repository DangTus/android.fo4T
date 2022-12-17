package com.dmt.dangtus.fo4t.fragment_main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.ListFragment;

import com.dmt.dangtus.fo4t.R;
import com.dmt.dangtus.fo4t.adapter.PlayerFootballAdapter;
import com.dmt.dangtus.fo4t.interfaces.MainInterface;
import com.dmt.dangtus.fo4t.model.Club;
import com.dmt.dangtus.fo4t.model.PlayerFootball;
import com.dmt.dangtus.fo4t.url.PlayerFootballUrl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
    private EditText edtSearch;
    private ImageButton btnSearch;

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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKey = edtSearch.getText().toString().trim();
                playerFootballList.clear();
                search(searchKey);
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    closeKyboard(getActivity());

                    String searchKey = edtSearch.getText().toString().trim();
                    playerFootballList.clear();
                    search(searchKey);

                    return true;
                }
                return false;
            }
        });

        return view;
    }

    public void getAllPlayerFootball() {
        pbLoad.setVisibility(View.VISIBLE);

        PlayerFootballUrl.get("getAll.php", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("trang_thai")) {
                        JSONArray jsonData = response.getJSONArray("data");
                        setDataLayout(jsonData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pbLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), responseString, Toast.LENGTH_LONG).show();
                pbLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void search(String searchKey) {
        pbLoad.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        params.put("q", searchKey);

        PlayerFootballUrl.get("search.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("trang_thai")) {
                        JSONArray jsonData = response.getJSONArray("data");
                        setDataLayout(jsonData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pbLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), responseString, Toast.LENGTH_LONG).show();
                pbLoad.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setDataLayout(JSONArray jsonData) throws JSONException {
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

    private void closeKyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void anhXa(View v) {
        pbLoad = v.findViewById(R.id.loadProgressBar);
        edtSearch = v.findViewById(R.id.search_edittext);
        btnSearch = v.findViewById(R.id.search_button);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        mainInterface.PlayerFootballDetail(playerFootballList.get(position));
    }
}
