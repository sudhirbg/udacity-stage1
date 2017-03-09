package com.example.android.popularmovieactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class PopularMovieDetailActivity extends AppCompatActivity {

    private String mId;
    private TextView mTitle;
    private ImageView mImage;
    private TextView mReleaseDate;
    private TextView mVoteAvg;
    private TextView mSynopsis;

    private static final String DELIMITER = "-";
    private static final String VOTE_MAX = "/10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie_detail);

        PopularMovie movie = getIntent().getExtras().getParcelable("movie");

        mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setText(movie.getTitle());

        mImage = (ImageView) findViewById(R.id.iv_image);
        String urlString = movie.getPoster();
        Picasso.with(PopularMovieDetailActivity.this)
                .load(urlString)
                .placeholder(R.mipmap.placeholder)
                .into(mImage);

        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mReleaseDate.setText(movie.getYear(movie.getDate()));

        mVoteAvg = (TextView) findViewById(R.id.tv_vote_avg);
        mVoteAvg.setText(movie.getVotes().toString() + VOTE_MAX);

        mSynopsis = (TextView) findViewById(R.id.tv_synopsis);
        mSynopsis.setText(movie.getOverview());
    }
}
