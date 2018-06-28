package com.example.a230618_recyclerview.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a230618_recyclerview.R;
import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.example.a230618_recyclerview.ui.IDialogCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SearchHolder> {
    private Context mContext;
    private ArrayList<SearchModel> mSearchModels;
    private IDialogCallback mIDialogCallback;

    public class SearchHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvYear;
        private ImageView mIvPoster;

        public SearchHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvYear = itemView.findViewById(R.id.tv_year);
            mIvPoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIDialogCallback.callMovieDialog(mSearchModels.get(getAdapterPosition()));
                }
            });
        }
    }

    public RVAdapter(Context context, ArrayList<SearchModel> searchModels, IDialogCallback iDialogCallback) {
        mContext = context;
        mSearchModels = searchModels;
        mIDialogCallback = iDialogCallback;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        view.setLayoutParams(new CardView.LayoutParams(getDisplaySize() / 2, ViewGroup.LayoutParams.WRAP_CONTENT));
        SearchHolder holder = new SearchHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        SearchModel searchModel = mSearchModels.get(position);

        holder.mTvTitle.setText(searchModel.getTitle());
        holder.mTvYear.setText(searchModel.getYear());
        if (searchModel.getPoster().equals("N/A")) {
            holder.mIvPoster.setImageDrawable(mContext.getResources().getDrawable(R.drawable.image_na));
        } else {
            Uri uri = Uri.parse(searchModel.getPoster());
            Picasso.get().load(uri).into(holder.mIvPoster);
        }
    }

    @Override
    public int getItemCount() {
        return mSearchModels.size();
    }

    private int getDisplaySize() {
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }


}
