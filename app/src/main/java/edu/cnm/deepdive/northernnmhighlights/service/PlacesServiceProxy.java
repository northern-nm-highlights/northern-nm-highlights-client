package edu.cnm.deepdive.northernnmhighlights.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.northernnmhighlights.BuildConfig;
import edu.cnm.deepdive.northernnmhighlights.model.dto.SearchResult;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesServiceProxy {

  @GET("textsearch/json")
  Single<SearchResult> search(@Query("query") String searchText, @Query("type") String placeType,
      @Query("location") String location, @Query("radius") int radius, @Query("key") String apiKey);

  @GET("nearbysearch/json")
  Single<SearchResult> search(@Query("type") String placeType,
      @Query("location") String location, @Query("radius") int radius, @Query("key") String apiKey);

  static PlacesServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;

  }

  class InstanceHolder {

    private static final PlacesServiceProxy INSTANCE;

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
          .baseUrl(BuildConfig.PLACES_BASE_URL)
          .build();
      INSTANCE = retrofit.create(PlacesServiceProxy.class);


    }

  }

}
