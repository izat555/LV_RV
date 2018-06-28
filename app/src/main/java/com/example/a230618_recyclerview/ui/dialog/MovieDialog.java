package com.example.a230618_recyclerview.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a230618_recyclerview.R;
import com.example.a230618_recyclerview.data.entity.SearchModel;

public class MovieDialog extends DialogFragment {
    private TextView mTvTitle;
    private TextView mTvYear;
    private TextView mTvImdbId;
    private TextView mTvType;

    public static MovieDialog newInstance(SearchModel searchModel) {
        Bundle args = new Bundle();
        args.putParcelable("model", searchModel);

        MovieDialog fragment = new MovieDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        mTvTitle = view.findViewById(R.id.tv_dialog_title);
        mTvYear = view.findViewById(R.id.tv_dialog_year);
        mTvImdbId = view.findViewById(R.id.tv_dialog_imdbId);
        mTvType = view.findViewById(R.id.tv_dialog_type);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchModel searchModel = getArguments().getParcelable("model");
        if (searchModel != null) {
            mTvTitle.setText(String.format(getString(R.string.title_s), searchModel.getTitle()));
            mTvYear.setText(String.format(getString(R.string.year_s), searchModel.getYear()));
            mTvImdbId.setText(String.format(getString(R.string.imdbid_s), searchModel.getImdbID()));
            mTvType.setText(String.format(getString(R.string.type_s), searchModel.getType()));
        }
    }
}
