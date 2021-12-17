package edu.cnm.deepdive.northernnmhighlights.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.google.android.gms.maps.model.LatLng;
import edu.cnm.deepdive.northernnmhighlights.R;
import edu.cnm.deepdive.northernnmhighlights.model.dao.FavoritePlaceDao;
import edu.cnm.deepdive.northernnmhighlights.model.dao.PlaceTypeDao;
import edu.cnm.deepdive.northernnmhighlights.model.dto.Place;
import edu.cnm.deepdive.northernnmhighlights.model.dto.SearchResult;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;

public class FavoritePlaceRepository {

  private static final String STATUS_OK = "OK";

  private final Context context;
  private final GoogleSignInRepository repository;
  private final NnmhlServiceProxy nnmhlServiceProxy;
  private final PlacesServiceProxy placesServiceProxy;
  private final PlaceTypeDao placeTypeDao;
  private final FavoritePlaceDao favoritePlaceDao;
  private final String apiKey;
  private final String locationFormat;


  public FavoritePlaceRepository(Context context) {
    this.context = context;
    repository = GoogleSignInRepository.getInstance();
    nnmhlServiceProxy = NnmhlServiceProxy.getInstance();
    placesServiceProxy = PlacesServiceProxy.getInstance();
    NnmhlDatabase database = NnmhlDatabase.getInstance();
    placeTypeDao = database.getPlaceTypeDao();
    favoritePlaceDao = database.getFavoritePlaceDao();
    syncPlaceTypes().subscribe();
    apiKey = context.getString(R.string.api_key);
    locationFormat = context.getString(R.string.location_format);

  }

  public Single<FavoritePlace> save(FavoritePlace favoritePlace) {
    return repository
        .refreshBearerToken()
        .flatMap((token) -> nnmhlServiceProxy.addFavorite(favoritePlace, token))
        .subscribeOn(Schedulers.io());
  }

  public Single<FavoritePlace> get(String key) {
    return repository
        .refreshBearerToken()
        .flatMap((token) -> nnmhlServiceProxy.getFavorite(key, token))
        .subscribeOn(Schedulers.io());
  }

  public Completable delete(String key) {
    return repository
        .refreshBearerToken()
        .flatMapCompletable((token) -> nnmhlServiceProxy.removeFavorite(key, token))
        .subscribeOn(Schedulers.io());
  }

  public Single<List<FavoritePlace>> getAll() {
    return repository
        .refreshBearerToken()
        .flatMap(nnmhlServiceProxy::getFavorites)
        .subscribeOn(Schedulers.io());
  }

  public LiveData<List<FavoritePlace>> getAllLocal() {
    return favoritePlaceDao.selectAll();
  }

  public LiveData<FavoritePlace> getLocal(long id) {
    return favoritePlaceDao.select(id);
  }

  public LiveData<List<PlaceType>> getPlaceTypes() {
    return placeTypeDao
        .getAll();
  }

  public Single<List<Place>> search(PlaceType placeType, String searchText, LatLng latLng,
      int radius) {
    String location = formatLatLng(latLng);
    return processResponse(
        placesServiceProxy
            .search(searchText, placeType.getName(), location, radius, apiKey)
    )
        .subscribeOn(Schedulers.io());
  }

  public Single<List<Place>> search(PlaceType placeType, LatLng latLng, int radius) {
    String location = formatLatLng(latLng);
    return processResponse(
        placesServiceProxy
            .search(placeType.getName(), location, radius, apiKey)
    )
        .subscribeOn(Schedulers.io());
  }

  private Single<List<Place>> processResponse(Single<SearchResult> result) {
    return result
        .flatMap((r) -> r.getStatus().equalsIgnoreCase(STATUS_OK)
            ? Single.just(r)
            : Single.error(new ServiceException()))
        .map(SearchResult::getResults);
  }

  private String formatLatLng(LatLng latLng) {
    return String.format(locationFormat, latLng.latitude, latLng.longitude);
  }

  private Completable syncPlaceTypes() {
    return repository
        .refreshBearerToken()
        .flatMap(nnmhlServiceProxy::getPlaces)
        .flatMap(placeTypeDao::insert)
        .ignoreElement()
        .subscribeOn(Schedulers.io());

  }

  public static class ServiceException extends IOException {

    public ServiceException() {
    }

    public ServiceException(String message) {
      super(message);
    }

    public ServiceException(String message, Throwable cause) {
      super(message, cause);
    }

    public ServiceException(Throwable cause) {
      super(cause);
    }
  }
}

