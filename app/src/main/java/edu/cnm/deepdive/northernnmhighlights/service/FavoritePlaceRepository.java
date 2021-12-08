package edu.cnm.deepdive.northernnmhighlights.service;

import android.content.Context;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.UUID;

public class FavoritePlaceRepository {

  private final Context context;
  private final GoogleSignInRepository repository;
  private final WebServiceProxy proxy;

  public FavoritePlaceRepository(Context context) {
    this.context = context;
    repository = GoogleSignInRepository.getInstance();
    proxy = WebServiceProxy.getInstance();
  }

  public Single<FavoritePlace> save(FavoritePlace favoritePlace) {
    return repository
        .refreshBearerToken()
        .flatMap((token) -> proxy.addFavorite(favoritePlace, token))
        .subscribeOn(Schedulers.io());
  }

  public Single<FavoritePlace> get(String key) {
    return repository
        .refreshBearerToken()
        .flatMap((token) -> proxy.getFavorite(key, token))
        .subscribeOn(Schedulers.io());
  }

  public Completable delete(String key) {
    return repository
        .refreshBearerToken()
        .flatMapCompletable((token) -> proxy.removeFavorite(key, token))
        .subscribeOn(Schedulers.io());
  }

  public Single<List<FavoritePlace>> getAll() {
  return repository
      .refreshBearerToken()
      .flatMap(proxy::getFavorites)
      .subscribeOn(Schedulers.io());
  }

}

