package com.example.android.popularmovieactivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class PopularMovieAdapter extends ArrayAdapter<PopularMovie>{

    private static final String LOG_TAG = PopularMovieAdapter.class.getSimpleName();
    private final PopularMovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface PopularMovieAdapterOnClickHandler {
        void onClick(PopularMovie movie);
    }

    /**
     * PopularMovieAdapter constructor: sets value of private
     * @param context
     * @param popularMovies
     * @param clickHandler
     */
    public PopularMovieAdapter (Activity context, ArrayList<PopularMovie> popularMovies, PopularMovieAdapterOnClickHandler clickHandler) {
        super(context, 0, popularMovies);
        mClickHandler = clickHandler;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridItemView = convertView;
        if(gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item, parent, false
            );
        }

        final PopularMovie currentPopularMovie = getItem(position);
        ImageView posterImageView = (ImageView) gridItemView.findViewById(R.id.grid_item_thumbnail);

        /**
         * Sets click listener for Movie Thumbnails
         */
        posterImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mClickHandler.onClick(currentPopularMovie);
            }
        });

        String urlString = currentPopularMovie.getPoster();
        Picasso.with(getContext()).load(urlString).into(posterImageView);

        return gridItemView;
    }
}
