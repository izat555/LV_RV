package com.example.a230618_recyclerview.ui.main;

import android.content.Context;

import com.example.a230618_recyclerview.BuildConfig;
import com.example.a230618_recyclerview.MovieApp;
import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.example.a230618_recyclerview.data.entity.SearchResultModel;
import com.example.a230618_recyclerview.ui.lv.LVFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {
    private Context mContext;
    private MainContract.View mView;
    private LVFragment mLVFragment;

    public MainPresenter(Context context) {
        mContext = context;
        mView = (MainContract.View) mContext;
        mLVFragment = new LVFragment();
    }

    @Override
    public void getData(final String title) {
        mView.showIndicator();

        Call<SearchResultModel> call = ((MovieApp) mContext.getApplicationContext())
                .getRetrofitService().getListMovieInfo(BuildConfig.API_KEY, title);

        call.enqueue(new Callback<SearchResultModel>() {
            @Override
            public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SearchModel> searchModels = response.body().getSearchModels();
                    mView.onBackGetData((ArrayList<SearchModel>) searchModels, title);
                    mView.hideIndicator();
                }
            }

            @Override
            public void onFailure(Call<SearchResultModel> call, Throwable t) {
                mView.onBackGetDataError(t.getMessage());
                mView.hideIndicator();
            }
        });
    }
}
