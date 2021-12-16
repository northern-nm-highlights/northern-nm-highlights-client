package edu.cnm.deepdive.northernnmhighlights.viewmodel;

import android.app.Application;
import android.location.Location;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.service.FavoritePlaceRepository;
import edu.cnm.deepdive.northernnmhighlights.service.LocationRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class FavoritePlaceViewModel extends AndroidViewModel {

  private final FavoritePlaceRepository placeRepository;
  private final MutableLiveData<List<PlaceType>> placeTypes;
  private final MutableLiveData<Location> location;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public FavoritePlaceViewModel(
      @NonNull @NotNull Application application) {
    super(application);
    placeRepository = new FavoritePlaceRepository(application);
    throwable = new MutableLiveData<>();
    placeTypes = new MutableLiveData<>();
    location = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<List<PlaceType>> getPlaceTypes() {
    return placeRepository.getPlaceTypes();
  }


  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }
}
