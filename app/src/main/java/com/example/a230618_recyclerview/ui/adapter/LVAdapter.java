package com.example.a230618_recyclerview.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a230618_recyclerview.R;
import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LVAdapter extends ArrayAdapter<SearchModel> {
    private Context mContext;
    private ArrayList<SearchModel> mSearchModels;

    private class SearchHolder {
        private TextView mTvTitle;
        private TextView mTvYear;
        private ImageView mIvPoster;

        public SearchHolder(View view) {
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvYear = view.findViewById(R.id.tv_year);
            mIvPoster = view.findViewById(R.id.iv_poster);
        }
    }

    public LVAdapter(@NonNull Context context, ArrayList<SearchModel> searchModels) {
        super(context, 0, searchModels);
        mContext = context;
        mSearchModels = searchModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SearchHolder searchHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            searchHolder = new SearchHolder(convertView);
            convertView.setTag(searchHolder);
        } else {
            searchHolder = (SearchHolder) convertView.getTag();
        }

        SearchModel searchModel = getItem(position);

        searchHolder.mTvTitle.setText(searchModel.getTitle());
        searchHolder.mTvYear.setText(searchModel.getYear());
        if (searchModel.getPoster().equals("N/A")) {
            searchHolder.mIvPoster.setImageDrawable(mContext.getResources().getDrawable(R.drawable.image_na));
        } else {
            Uri uri = Uri.parse(searchModel.getPoster());
            Picasso.get().load(uri).into(searchHolder.mIvPoster);
        }

        return convertView;
    }
}
