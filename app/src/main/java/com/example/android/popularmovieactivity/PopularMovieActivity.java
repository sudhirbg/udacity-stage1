package com.example.android.popularmovieactivity;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularMovieActivity extends AppCompatActivity
        implements PopularMovieAdapter.PopularMovieAdapterOnClickHandler {

    private static final String LOG_TAG = PopularMovieActivity.class.getSimpleName();

    private PopularMovieAdapter mAdapter = null;
    private GridView mGridView = null;
    private ProgressBar mProgressBar;
    private TextView mErrorMessageTextView;
    private String mSortBy;

    private final static String TOP_RATED =
            "top_rated";

    private final static String POPULAR =
            "popular";

    static final String SORT_ORDER =
            "sort_order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageTextView = (TextView) findViewById(R.id.error_message);

        mGridView = (GridView) findViewById(R.id.gridview_movies);
        mAdapter = new PopularMovieAdapter(this, new ArrayList<PopularMovie>(), this);
        mGridView.setAdapter(mAdapter);

        /* Displays progress bar */
        showLoading();

        // Checks whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mSortBy = savedInstanceState.getString(SORT_ORDER);
        } else {
            // Initialize members with default values for a new instance
            mSortBy = POPULAR;
        }

        // check internet connectivity
        if (isOnline(this)) {
            getMoviesJson();

        } else {
            mErrorMessageTextView.setText(R.string.network_error);
            showErrorMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mAdapter.clear();
        mGridView.setEmptyView(new View(this));
        mGridView.setAdapter(mAdapter);

        if(isOnline(this)) {
            showLoading();
            if (id == R.id.sort_by_popularity) {
                mSortBy = POPULAR;

            } else if (id == R.id.sort_by_rating) {
                mSortBy = TOP_RATED;
            }

            // check internet connectivity
            if (isOnline(this)) {
                getMoviesJson();

            } else {
                mErrorMessageTextView.setText(R.string.network_error);
                showErrorMessage();
            }

        } else {
            showErrorMessage();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(PopularMovie movie) {
        Class destinationActivity = PopularMovieDetailActivity.class;
        Intent startChildActivityIntent = new Intent (this, destinationActivity);

        /* pass movie info to the movie detail activity via putExtra method*/
        startChildActivityIntent.putExtra("movie", movie);
        startActivity(startChildActivityIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the user selected sort order
        outState.putString(SORT_ORDER, mSortBy);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore state members from saved instance
        mSortBy = savedInstanceState.getString(SORT_ORDER);

        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Displays GridView
     * Hides ProgressBar & Error TextView
     */
    private void showMoviesGridView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mGridView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Displays Error TextView
     * Hides GridView & ProgressBar
     */
    private void showErrorMessage() {
        mGridView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Displays ProgressBar
     * Hides GridView & Error TextView
     */
    private void showLoading() {
        mGridView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * check network connectivity
     * @param mContext
     * @return
     */
    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * GET movies json from MoviesDB using Retrofit
     */
    private void getMoviesJson() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesAPI service = retrofit.create(MoviesAPI.class);
        Call call = service.getMovies(mSortBy, BuildConfig.API_KEY);
        call.enqueue(new Callback<PopularMovie.MovieResult>(){
            @Override
            public void onResponse(Call<PopularMovie.MovieResult> call, Response<PopularMovie.MovieResult> response) {
                PopularMovie.MovieResult results = response.body();
                ArrayList<PopularMovie> movies = results.getResults();
                mAdapter.addAll(movies);
                showMoviesGridView();
            }
            @Override
            public void onFailure(Call<PopularMovie.MovieResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
