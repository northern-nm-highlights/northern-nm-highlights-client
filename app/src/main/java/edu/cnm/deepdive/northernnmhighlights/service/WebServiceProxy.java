package edu.cnm.deepdive.northernnmhighlights.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.northernnmhighlights.BuildConfig;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.model.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceProxy {

  /**
   * This will get you a list of place types.
   * @param bearerToken Its a specialized identifier for a specific user.
   * @return   A list of {@link PlaceType}
   */
  @GET("place-types")
  Single<List<PlaceType>> getPlaces(@Header("Authorization") String bearerToken);

  /**
   * This will log what user it is.
   * @param bearerToken
   * @return {@link User} profile
   */
  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);

  /**
   * Will get you a list favorite places.
   * @param bearerToken
   * @return List {@link FavoritePlace}
   */
  @GET("users/me/favorites")
  Single<List<FavoritePlace>> getFavorites(@Header("Authorization") String bearerToken);

  /**
   * This will allow you to get a single favorite place.
   * @param key
   * @param bearerToken
   * @return {@link FavoritePlace}
   */
  @GET("users/me/favorites/{key}")
  Single<FavoritePlace> getFavorite(@Path("key") String key,
      @Header("Authorization") String bearerToken);

  /**
   * Will add a favorite to your database.
   * @param favorite
   * @param bearerToken
   * @return {@link FavoritePlace}
   */
  @POST("users/me/favorites")
  Single<FavoritePlace> addFavorite(@Body FavoritePlace favorite,
      @Header("Authorization") String bearerToken);

  @DELETE("users/me/favorites/{key}")
  Completable removeFavorite(@Path("key") String key,
      @Header("Authorization") String bearerToken);

  static WebServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;

  }

  class InstanceHolder {

    private static final WebServiceProxy INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(client)
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(WebServiceProxy.class);


    }

  }

}
