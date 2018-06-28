package com.example.a230618_recyclerview.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultModel {
    @SerializedName("Search")
    @Expose
    private List<SearchModel> mSearchModels = null;

    @SerializedName("totalResults")
    @Expose
    private String mTotalResults;

    @SerializedName("Response")
    @Expose
    private String mResponse;

    public List<SearchModel> getSearchModels() {
        return mSearchModels;
    }

    public void setSearchModels(List<SearchModel> searchModels) {
        mSearchModels = searchModels;
    }

    public String getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(String totalResults) {
        mTotalResults = totalResults;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }
}
