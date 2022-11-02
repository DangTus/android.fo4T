package com.dmt.dangtus.fo4t.fragment_main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPAdapterMain extends FragmentStateAdapter {

    public VPAdapterMain(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ListViewFragment();
            case 1:
                return new LikeFragment();
            case 2:
                return new MenuFragment();
            default:
                return new ListViewFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}