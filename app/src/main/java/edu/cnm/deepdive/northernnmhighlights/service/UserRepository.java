package edu.cnm.deepdive.northernnmhighlights.service;

import android.content.Context;
import edu.cnm.deepdive.northernnmhighlights.model.entity.User;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

  private final Context context;
  private final GoogleSignInRepository repository;
  private final WebServiceProxy proxy;

  public UserRepository(Context context) {
    this.context = context;
    repository = GoogleSignInRepository.getInstance();
    proxy = WebServiceProxy.getInstance();
  }

  public Single<User> getProfile() {
    return repository
        .refreshBearerToken()
        .flatMap(proxy::getProfile)
        // TODO Add to the database if necessary.
        .subscribeOn(Schedulers.io());
  }
}
