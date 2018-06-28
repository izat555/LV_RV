package com.example.a230618_recyclerview.ui.lv;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a230618_recyclerview.R;
import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.example.a230618_recyclerview.ui.IDialogCallback;
import com.example.a230618_recyclerview.ui.adapter.LVAdapter;

import java.util.ArrayList;

public class LVFragment extends Fragment {
    private Context mContext;
    private ListView mListView;
    private ArrayList<SearchModel> mSearchModels = null;
    private IDialogCallback mIDialogCallback;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchModels = getArguments().getParcelableArrayList(getString(R.string.search_models));
        if (mSearchModels != null) {
            mListView.setAdapter(new LVAdapter(mContext, mSearchModels));
        }
        mListView.requestFocus();
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(getString(R.string.my_log), "onItemClick");
            mIDialogCallback.callMovieDialog(mSearchModels.get(position));
        }
    };
}
