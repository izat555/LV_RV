package com.example.a230618_recyclerview.ui.main;

import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.example.a230618_recyclerview.ui.IProgressBar;

import java.util.ArrayList;

public interface MainContract {
    interface View extends IProgressBar {
        void onBackGetData(ArrayList<SearchModel> searchModels);
        void onBackGetDataError(String msg);
    }

    interface Presenter {
        void getData(String title);
    }
}
