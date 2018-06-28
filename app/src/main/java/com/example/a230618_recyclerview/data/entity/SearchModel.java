package com.example.a230618_recyclerview.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchModel implements Parcelable {
    @SerializedName("Title")
    @Expose
    private String mTitle;

    @SerializedName("Year")
    @Expose
    private String mYear;

    @SerializedName("imdbID")
    @Expose
    private String mImdbID;

    @SerializedName("Type")
    @Expose
    private String mType;

    @SerializedName("Poster")
    @Expose
    private String mPoster;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getImdbID() {
        return mImdbID;
    }

    public void setImdbID(String imdbID) {
        mImdbID = imdbID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public SearchModel() {}

    protected SearchModel(Parcel in) {
        mTitle = in.readString();
        mYear = in.readString();
        mImdbID = in.readString();
        mType = in.readString();
        mPoster = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mYear);
        dest.writeString(mImdbID);
        dest.writeString(mType);
        dest.writeString(mPoster);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SearchModel> CREATOR = new Parcelable.Creator<SearchModel>() {
        @Override
        public SearchModel createFromParcel(Parcel in) {
            return new SearchModel(in);
        }

        @Override
        public SearchModel[] newArray(int size) {
            return new SearchModel[size];
        }
    };
}
