package com.dmt.dangtus.fo4t;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dmt.dangtus.fo4t.fragment_main.VPAdapterMain;
import com.dmt.dangtus.fo4t.interfaces.MainInterface;
import com.dmt.dangtus.fo4t.model.PlayerFootball;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements MainInterface {

    private BottomNavigationView bottomNavigation;
    private ViewPager2 homeViewPager;
    private VPAdapterMain viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();

        viewPagerAdapter = new VPAdapterMain(this);
        homeViewPager.setAdapter(viewPagerAdapter);

        //su kien khi luot doi page trong viewpager2
        homeViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.action_listview).setChecked(true);
                        break;

                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.action_like).setChecked(true);
                        viewPagerAdapter.notifyItemChanged(position);
                        break;

                    case 2:
                        bottomNavigation.getMenu().findItem(R.id.action_menu).setChecked(true);
                        break;
                }
            }
        });

        //su kien khi click vao o bottom navigation view
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_listview:
                        homeViewPager.setCurrentItem(0);
                        break;

                    case R.id.action_like:
                        homeViewPager.setCurrentItem(1);
                        break;

                    case R.id.action_menu:
                        homeViewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

    private void anhXa() {
        bottomNavigation = findViewById(R.id.bottomNav);
        homeViewPager = findViewById(R.id.viewPagerHome);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            this.finishAffinity();
        }
        return false;
    }

    @Override
    public void PlayerFootballDetail(PlayerFootball playerFootball) {
        Intent intent = new Intent(MainActivity.this, PlayerFootballDetailActivity.class);
        intent.putExtra("cau_thu", playerFootball);
        startActivity(intent);
    }
}