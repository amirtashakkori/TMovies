package com.example.tmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {
    long id;
    String title;
    String poster;
    int year;
    String country;
    float imdb_rating;
    List<String> genres;
    List<String> images;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(float imdb_rating) {
        this.imdb_rating = imdb_rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeInt(this.year);
        dest.writeString(this.country);
        dest.writeFloat(this.imdb_rating);
        dest.writeStringList(this.genres);
        dest.writeStringList(this.images);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.poster = source.readString();
        this.year = source.readInt();
        this.country = source.readString();
        this.imdb_rating = source.readFloat();
        this.genres = source.createStringArrayList();
        this.images = source.createStringArrayList();
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.poster = in.readString();
        this.year = in.readInt();
        this.country = in.readString();
        this.imdb_rating = in.readFloat();
        this.genres = in.createStringArrayList();
        this.images = in.createStringArrayList();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
