package com.rarestar.empirebesttv.retrofit;

import com.rarestar.empirebesttv.Movie_model.MovieDownloadAndPlay_model;
import com.rarestar.empirebesttv.Movie_model.comment_model;
import com.rarestar.empirebesttv.Movie_model.likePost_model;
import com.rarestar.empirebesttv.Movie_model.movie_model;
import com.rarestar.empirebesttv.Movie_model.serial_model;
import com.rarestar.empirebesttv.Movie_model.superstars_model;
import com.rarestar.empirebesttv.Movie_model.user_model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Retrofit_interface {
//  process data from database and show mainActivity ->
    @GET("get_movie.php")
    Call<List<movie_model>> getMovies();

    @GET("get_serial.php")
    Call<List<movie_model>> getSerials();

    @GET("get_anim.php")
    Call<List<movie_model>> getAnimation();

    @GET("get_trendingMovie.php")
    Call<List<movie_model>> getTrending();

    @GET("get_newest.php")
    Call<List<movie_model>> getNewest(); // <- // movie_model

//  process data from database and show drawer in mainActivity ->
    @GET("get_ImdbRate.php")
    Call<List<movie_model>> getImdb();

    @GET("get_RateUser.php")
    Call<List<movie_model>> getUserRate(); //<-

//  search and filters in Activity Search // search in database ->
    @POST("search.php")
    Call<List<movie_model>> search(@Query("key")String key);
    @POST("get_genre.php")
    Call<List<movie_model>> Genre(@Query("key")String key);
    @POST("get_year.php")
    Call<List<movie_model>> Year(@Query("key")String key);// <- // movie_model

//  login and register activity
    @FormUrlEncoded
    @POST("register.php")
    Call<List<user_model>> AddUser(@Field("username") String username, @Field("email") String email,
                                   @Field("pass") String pass);
    @POST("check_user.php")
    Call<List<user_model>> Check(@Query("username")String username, @Query("pass")String pass);
    @GET("get_UserInfo.php")
    Call<List<user_model>> get_UserInfo(@Query("username")String username, @Query("pass")String pass);
    @GET("showAll_movieSave.php")
    Call<List<user_model>> NameSaveMovie(@Query("user")String user);
    @FormUrlEncoded
    @POST("Updated_password.php")
    Call<List<user_model>> UpdatePass(@Field("email") String email, @Field("pass") String pass);
    @FormUrlEncoded
    @POST("check_user_email.php")
    Call<List<user_model>> CheckEmail(@Field("email")String email);
    @FormUrlEncoded
    @POST("delete_user.php")
    Call<List<user_model>> LogOut(@Field("email") String email, @Field("pass") String pass);
    @FormUrlEncoded
    @POST("save_movie.php")
    Call<List<user_model>> SaveMovie(@Field("user")String user, @Field("movie_name")String movie_name
            , @Field("desc")String desc, @Field("category")String category
            , @Field("genre")String genre, @Field("director")String director, @Field("rate_imdb")String rate_imdb
            , @Field("rate_user")String rate_user
            , @Field("poster_link")String poster_link, @Field("age")String age, @Field("year")String year
            , @Field("status")String status, @Field("country")String country, @Field("duration")String duration);

    @FormUrlEncoded
    @POST("deleteAllSaveMovie.php")
    Call<List<user_model>> DeleteAllSaveMovie(@Field("user")String user);// fragment Saved Movie
    @GET("check_save_movie.php")
    Call<List<user_model>> CheckSaveMovie(@Query("user")String user, @Query("movie_name")String movie_name);

    @FormUrlEncoded
    @POST("delete_save_movie.php")
    Call<List<user_model>> DeleteSaveMovie(@Field("user")String user, @Field("movie_name")String movie_name);//  <- // user_model

//  process data from database for PlayMovieActivity ->
    @FormUrlEncoded
    @POST("actors_search.php")
    Call<List<superstars_model>> superstar_movie(@Field("key") String key);
    @POST("get_superstars.php")
    Call<List<superstars_model>> searchActors(@Query("key")String key);
    @FormUrlEncoded
    @POST("like_post.php")
    Call<List<likePost_model>> LikePost(@Field("username")String username, @Field("movie_name")String movie_name);
    @FormUrlEncoded
    @POST("Dislike_post.php")
    Call<List<likePost_model>> DisLikePost(@Field("username")String username, @Field("movie_name")String movie_name);
    @FormUrlEncoded
    @POST("check_like_movie.php")
    Call<List<likePost_model>> CheckLikePost(@Field("username")String username, @Field("movie_name")String movie_name); // <-

//  get link movie and serial from database and show playMovieActivity ->
    @POST("play_link.php")
    Call<List<MovieDownloadAndPlay_model>> getLinkPlayMovie(@Query("movie_name") String movie_name);
    @GET("get_season_serial.php")
    Call<List<serial_model>> SeasonSerial(@Query("serial_name")String serial_name);
    @FormUrlEncoded
    @POST("get_episode_serial.php")
    Call<List<serial_model>> EpisodeSerial(@Field("serial_name") String serial_name,@Field("season") String season);
    @FormUrlEncoded
    @POST("getEpisode.php")
    Call<List<serial_model>> Episode(@Field("serial_name") String serial_name);
    @FormUrlEncoded
    @POST("get_link_serial.php")
    Call<List<serial_model>> LinkSerial(@Field("serial_name") String serial_name,@Field("season") String season,@Field("episode") String episode);
    @GET("get_quality.php")
    Call<List<serial_model>> QualitySerial(@Query("serial_name") String serial_name,@Query("episode") String episode);

    @GET("play_trailer.php")
    Call<List<movie_model>> playTrailer(@Query("movie_name") String movie_name);// <-

//  insert comment and show in comment activity ->
    @GET("get_comment.php")
    Call<List<comment_model>> GetCommentUsers(@Query("key")String key);

    @FormUrlEncoded
    @POST("insert_comment.php")
    Call<List<comment_model>> InsertComment(@Field("username")String username,
                                            @Field("movie_name")String movie_name,@Field("text")String post); // <- // comment_model
}