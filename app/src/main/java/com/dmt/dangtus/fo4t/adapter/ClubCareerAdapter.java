package com.dmt.dangtus.fo4t.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmt.dangtus.fo4t.R;
import com.dmt.dangtus.fo4t.model.ClubCareer;

import java.util.List;

public class ClubCareerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ClubCareer> clubCareerList;

    public ClubCareerAdapter(Context context, int layout, List<ClubCareer> clubCareerList) {
        this.context = context;
        this.layout = layout;
        this.clubCareerList = clubCareerList;
    }

    @Override
    public int getCount() {
        return clubCareerList.size();
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
            holder.imvAvata = view.findViewById(R.id.avataIMV);
            holder.txtYear = view.findViewById(R.id.yearTextView);
            holder.imvThue = view.findViewById(R.id.thueIMV);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //gan gia tri
        ClubCareer clubCareer = clubCareerList.get(i);
        holder.txtName.setText(clubCareer.getClub().getName());
        holder.txtYear.setText(clubCareer.getYearStart() + " ~ " + clubCareer.getYearFinal());
        if(clubCareer.isLease()) {
            holder.imvThue.setImageResource(R.drawable.ic_checked);
        } else {
            holder.imvThue.setImageResource(0);
        }
        //gan hinh anh
        if (clubCareer.getClub().getImage().equals("null")) {
            holder.imvAvata.setImageResource(R.drawable.ic_empty);
        } else {
            Glide.with(context).load(clubCareer.getClub().getImage()).fitCenter().into(holder.imvAvata);
        }

        return view;
    }

    private class ViewHolder {
        ImageView imvAvata, imvThue;
        TextView txtName, txtYear;
    }
}
