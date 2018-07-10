package com.example.a230618_recyclerview.ui.rv;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.a230618_recyclerview.R;
import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.example.a230618_recyclerview.ui.IDialogCallback;
import com.example.a230618_recyclerview.ui.adapter.RVAdapter;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.ArrayList;

public class RVFragment extends Fragment {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RVAdapter mRVAdapter;
    private IDialogCallback mIDialogCallback;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private ArrayList<SearchModel> mSearchModels;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        try {
            mIDialogCallback = (IDialogCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IDialogCallback!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipyRefreshLayout = view.findViewById(R.id.swipy_refresh_layout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchModels = getArguments().getParcelableArrayList(getString(R.string.search_models));
        if (mSearchModels != null) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false));
            mRecyclerView.setHasFixedSize(true);
            mRVAdapter = new RVAdapter(mContext, mSearchModels, mIDialogCallback);
            mRecyclerView.setAdapter(mRVAdapter);
            mRecyclerView.addOnItemTouchListener(mOnItemTouchListener);
        }
    }

    private RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_UP) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    mIDialogCallback.callMovieDialog(mSearchModels.get(rv.getChildAdapterPosition(child)));
                }
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };
}
