package com.example.a230618_recyclerview.ui.main;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.a230618_recyclerview.R;
import com.example.a230618_recyclerview.data.entity.SearchModel;
import com.example.a230618_recyclerview.ui.IDialogCallback;
import com.example.a230618_recyclerview.ui.dialog.MovieDialog;
import com.example.a230618_recyclerview.ui.lv.LVFragment;
import com.example.a230618_recyclerview.ui.rv.RVFragment;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.LV;

public class MainActivity extends AppCompatActivity implements MainContract.View, IDialogCallback {
    private EditText mEtToolbarSearch;
    private ImageButton mIbToolbarSearch;
    private BottomNavigationView mBottomNavigationView;
    private ProgressBar mProgressBar;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));

        mEtToolbarSearch = findViewById(R.id.et_toolbar_search);
        mIbToolbarSearch = findViewById(R.id.ib_toolbar_search);
        mIbToolbarSearch.setOnClickListener(mOnClickListener);
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mProgressBar = findViewById(R.id.progress_bar);

        mPresenter = new MainPresenter(this);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mEtToolbarSearch.getText().toString().isEmpty()) {
                Snackbar.make(mBottomNavigationView, "Введите название фильма!", Snackbar.LENGTH_LONG).show();
                return;
            }
            mPresenter.getData(mEtToolbarSearch.getText().toString());
        }
    };

    @Override
    public void onBackGetData(ArrayList<SearchModel> searchModels) {
        refreshFragment(searchModels);
    }

    @Override
    public void onBackGetDataError(String msg) {
        Snackbar.make(mBottomNavigationView, msg, Snackbar.LENGTH_LONG).show();
        Log.d(getString(R.string.my_log), msg);
    }

    private void refreshFragment(ArrayList<SearchModel> searchModels) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.search_models), searchModels);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (currentFragment != null) {
            transaction.remove(currentFragment);
        }

        switch (mBottomNavigationView.getSelectedItemId()) {
            case R.id.action_lv:
                Log.d(getString(R.string.my_log), "ListViewFragment");
                LVFragment lvFragment = new LVFragment();
                lvFragment.setArguments(bundle);
                transaction.add(R.id.fragment_container, lvFragment);
                break;
            case R.id.action_rv:
                Log.d(getString(R.string.my_log), "RecyclerViewFragment");
                RVFragment rvFragment = new RVFragment();
                rvFragment.setArguments(bundle);
                transaction.add(R.id.fragment_container, rvFragment);
                break;
        }

        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_lv:
                    Log.d(getString(R.string.my_log), "LV");
                    break;
                case R.id.action_rv:
                    Log.d(getString(R.string.my_log), "RV");
                    break;
            }
            if (mEtToolbarSearch.getText().toString().isEmpty()) {
                Snackbar.make(mBottomNavigationView, "Введите название фильма!", Snackbar.LENGTH_LONG).show();
                return true;
            }
            mPresenter.getData(mEtToolbarSearch.getText().toString());

            return true;
        }
    };

    @Override
    public void showIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIndicator() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void callMovieDialog(SearchModel searchModel) {
        MovieDialog.newInstance(searchModel).show(getSupportFragmentManager(), "movie_dialog");
    }
}
