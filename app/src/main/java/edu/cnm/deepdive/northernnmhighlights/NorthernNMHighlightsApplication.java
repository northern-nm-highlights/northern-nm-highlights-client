package edu.cnm.deepdive.northernnmhighlights;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.northernnmhighlights.service.GoogleSignInRepository;

public class NorthernNMHighlightsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInRepository.setContext(this);
    Stetho.initializeWithDefaults(this);
  }
}
