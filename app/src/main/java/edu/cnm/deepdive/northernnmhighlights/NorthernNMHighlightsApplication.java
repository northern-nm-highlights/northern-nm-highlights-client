package edu.cnm.deepdive.northernnmhighlights;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.northernnmhighlights.service.GoogleSignInRepository;
import edu.cnm.deepdive.northernnmhighlights.service.LocationRepository;
import edu.cnm.deepdive.northernnmhighlights.service.NnmhlDatabase;
import io.reactivex.schedulers.Schedulers;

public class NorthernNMHighlightsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInRepository.setContext(this);
    Stetho.initializeWithDefaults(this);
    LocationRepository.setContext(this);
    NnmhlDatabase.setContext(this);
    NnmhlDatabase
        .getInstance()
        .getFavoritePlaceDao()
        .delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }
}
