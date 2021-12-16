package edu.cnm.deepdive.northernnmhighlights.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.android.gms.maps.model.LatLng;
import edu.cnm.deepdive.northernnmhighlights.service.LocationRepository;

public class LocationViewModel extends AndroidViewModel {

  private final LocationRepository repository;

  public LocationViewModel(@NonNull Application application) {
    super(application);
    repository = LocationRepository.getInstance();
  }

  public LiveData<LatLng> getLocation() {
    //noinspection unchecked
    return repository.getLocation();
  }
}
