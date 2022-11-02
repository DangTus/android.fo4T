package com.dmt.dangtus.fo4t.fragment_player_football_detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPAdapterPFD extends FragmentStateAdapter {

    private int idPlayerFootball;

    public VPAdapterPFD(@NonNull FragmentActivity fragmentActivity, int idPlayerFootball) {
        super(fragmentActivity);
        this.idPlayerFootball = idPlayerFootball;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new InfomationFragment(idPlayerFootball);
            case 1:
                return new ClubFragment(idPlayerFootball);
            default:
                return new InfomationFragment(idPlayerFootball);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}