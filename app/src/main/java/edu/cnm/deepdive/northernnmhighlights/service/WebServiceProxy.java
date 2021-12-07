package edu.cnm.deepdive.northernnmhighlights.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.northernnmhighlights.BuildConfig;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import edu.cnm.deepdive.northernnmhighlights.model.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import java.util.UUID;
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
import retrofit2.http.Query;

public interface WebServiceProxy {


  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);


  @GET("users/me/favorites")
  Single<List<FavoritePlace>> getFavorites(@Header("Authorization") String bearerToken);

  @GET("users/me/favorites/{key}")
  Single<FavoritePlace> getFavorite(@Path("key") String key,
      @Header("Authorization") String bearerToken);

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
