package com.example.a230618_recyclerview.ui.lv;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a230618_recyclerview.BuildConfig;
import com.example.a230618_recyclerview.MovieApp;
import com.example.a230618_recyclerview.R;
import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.example.a230618_recyclerview.data.entity.SearchResultModel;
import com.example.a230618_recyclerview.ui.IDialogCallback;
import com.example.a230618_recyclerview.ui.adapter.LVAdapter;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LVFragment extends Fragment {
    private Context mContext;
    private ListView mListView;
    private ArrayList<SearchModel> mSearchModels = null;
    private LVAdapter mLVAdapter;
    private IDialogCallback mIDialogCallback;

    private SwipyRefreshLayout mSwipyRefreshLayout;
    private int mPage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        try {
            mIDialogCallback = (IDialogCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement IDialogCallback!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lv, container, false);
        mListView = view.findViewById(R.id.list_view);
        mSwipyRefreshLayout = view.findViewById(R.id.swipy_refresh_layout);
        mPage = 2;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchModels = getArguments().getParcelableArrayList(getString(R.string.search_models));
        if (mSearchModels != null) {
            mLVAdapter = new LVAdapter(mContext, mSearchModels);
            mListView.setAdapter(mLVAdapter);
        }
        mListView.setOnItemClickListener(mOnItemClickListener);
        mListView.requestFocus();

        mSwipyRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mIDialogCallback.callMovieDialog(mSearchModels.get(position));
        }
    };

    private SwipyRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipyRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction) {
            mSwipyRefreshLayout.setRefreshing(true);

            Call<SearchResultModel> call = MovieApp.get(mContext)
                    .getRetrofitService()
                    .getListMovieInfoByPage(BuildConfig.API_KEY, getArguments().getString(mContext.getString(R.string.title)), mPage++);

            call.enqueue(new Callback<SearchResultModel>() {
                @Override
                public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getResponse().equals("True")) {
                            mSearchModels.addAll(response.body().getSearchModels());
                            mLVAdapter.notifyDataSetChanged();
                            mSwipyRefreshLayout.setRefreshing(false);
                        } else {
                            Toast.makeText(mContext, "No data!", Toast.LENGTH_LONG).show();
                            mSwipyRefreshLayout.setRefreshing(false);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SearchResultModel> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                    mSwipyRefreshLayout.setRefreshing(false);
                }
            });
        }
    };
}
