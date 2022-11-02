package com.dmt.dangtus.fo4t.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmt.dangtus.fo4t.R;
import com.dmt.dangtus.fo4t.model.PlayerFootball;

import java.util.List;

public class PlayerFootballAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<PlayerFootball> playerFootballList;

    public PlayerFootballAdapter(Context context, int layout, List<PlayerFootball> playerFootballList) {
        this.context = context;
        this.layout = layout;
        this.playerFootballList = playerFootballList;
    }

    @Override
    public int getCount() {
        return playerFootballList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            holder = new ViewHolder();
            //anh xa
            holder.txtName = view.findViewById(R.id.nameTextView);
            holder.txtClub = view.findViewById(R.id.clubTextView);
            holder.imvAvata = view.findViewById(R.id.avataImage);
            holder.imvClub = view.findViewById(R.id.clubImage);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //gan gia tri
        PlayerFootball playerFootball = playerFootballList.get(i);
        holder.txtName.setText(playerFootball.getName());
        holder.txtClub.setText(playerFootball.getCurrentClub().getName());
        //gan hinh anh
        Glide.with(context).load(playerFootball.getImage()).fitCenter().into(holder.imvAvata);
        Glide.with(context).load(playerFootball.getCurrentClub().getImage()).fitCenter().into(holder.imvClub);

        return view;
    }

    private class ViewHolder {
        ImageView imvAvata, imvClub;
        TextView txtName, txtClub;
    }
}
