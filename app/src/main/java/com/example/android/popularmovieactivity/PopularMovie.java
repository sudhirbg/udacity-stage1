package com.example.android.popularmovieactivity;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class PopularMovie implements Parcelable{

    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";
    private int id;
    private String title;

    @SerializedName("release_date")
    private String date;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("vote_average")
    private Double votes;

    private String overview;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<PopularMovie> CREATOR
            = new Parcelable.Creator<PopularMovie>() {
        public PopularMovie createFromParcel(Parcel in) {
            return new PopularMovie(in);
        }

        public PopularMovie[] newArray(int size) {
            return new PopularMovie[size];
        }
    };
    private PopularMovie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.poster = in.readString();
        this.votes = in.readDouble();
        this.overview = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.poster);
        dest.writeDouble(this.votes);
        dest.writeString(this.overview);
    }

    /* getters */
    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public String getPoster() {
        return TMDB_IMAGE_PATH + this.poster;
    }

    public Double getVotes() {
        return this.votes;
    }

    public String getOverview() {
        return this.overview;
    }


    /* setters */
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String release_date) {
        this.date = release_date;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setVotes(Double votes) {
        this.votes = votes;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    /* POJO class to receive object and extract array*/
    public static class MovieResult {
        private ArrayList<PopularMovie> results;

        public ArrayList<PopularMovie> getResults() {
            return results;
        }
    }

}
