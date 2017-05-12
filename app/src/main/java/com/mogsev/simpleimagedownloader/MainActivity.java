package com.mogsev.simpleimagedownloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mogsev.simpleimagedownloader.adapter.PicturesRvAdapter;
import com.mogsev.simpleimagedownloader.network.Api;
import com.mogsev.simpleimagedownloader.network.model.Item;
import com.mogsev.simpleimagedownloader.network.model.Pictures;
import com.mogsev.simpleimagedownloader.utils.PaginationHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String BUNDLE_OFFSET = "bundle_offset";
    private static final String BUNDLE_NEXT_PAGE = "bundle_next_page";
    private static final String BUNDLE_LOADING = "bundle_loading";

    private static final int MENU_ID_SORT_ASCENDING = 1001;
    private static final int MENU_ID_SORT_DESCENDING = 1002;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PicturesRvAdapter mPicturesRvAdapter;

    private CompositeDisposable mCompositeDisposable;

    private int mLimit = 10;
    private int mOffset;
    private boolean mNextPage = true;
    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        initView();

        if (savedInstanceState == null) {
            startDownloading();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        mCompositeDisposable.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putBoolean(BUNDLE_NEXT_PAGE, mNextPage);
        outState.putBoolean(BUNDLE_LOADING, mLoading);
        outState.putInt(BUNDLE_OFFSET, mOffset);
        mPicturesRvAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
        mNextPage = savedInstanceState.getBoolean(BUNDLE_NEXT_PAGE);
        mLoading = savedInstanceState.getBoolean(BUNDLE_LOADING);
        mOffset = savedInstanceState.getInt(BUNDLE_OFFSET);
        mPicturesRvAdapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_ID_SORT_ASCENDING, Menu.NONE, "Sorting ascending")
                .setIcon(R.drawable.ic_sort_ascending_white_24dp)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(Menu.NONE, MENU_ID_SORT_DESCENDING, Menu.NONE, "Sorting descending")
                .setIcon(R.drawable.ic_sort_descending_white_24dp)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_SORT_ASCENDING:
                mPicturesRvAdapter.onSort(PicturesRvAdapter.SortType.ASCENDING);
                return true;
            case MENU_ID_SORT_DESCENDING:
                mPicturesRvAdapter.onSort(PicturesRvAdapter.SortType.DESCENDING);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_default);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPicturesRvAdapter = new PicturesRvAdapter();
        mRecyclerView.setAdapter(mPicturesRvAdapter);

        mCompositeDisposable = new CompositeDisposable();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!mLoading) {
                        if (PaginationHelper.isDownPagination(mLinearLayoutManager)) {
                            startDownloading();
                        }
                    }
                }
            }
        });
    }

    private void downloadPictures(int limit, int offset) {
        mCompositeDisposable.add(Api.API.getObservableOfPictures(limit, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void startDownloading() {
        Log.i(TAG, "Pagination down start...");
        mLoading = true;
        if (!mNextPage) {
            return;
        }
        mPicturesRvAdapter.addProgress();
        downloadPictures(mLimit, mOffset);
    }

    private void handleResponse(Pictures pictures) {
        Log.i(TAG, pictures.toString());
        mPicturesRvAdapter.removeProgress();
        if (pictures != null) {
            if ((mLimit + mOffset) < pictures.getInfo().getTotalCount()) {
                mOffset = mOffset + mLimit;
            } else {
                mNextPage = false;
            }
            List<Item> list = pictures.getItems();
            if (list != null && !list.isEmpty()) {
                mPicturesRvAdapter.addItems(list);
            }
        }
        mLoading = false;
    }

    private void handleError(Throwable error) {
        Log.e(TAG, error.getLocalizedMessage());
        Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        mLoading = false;
    }

}
