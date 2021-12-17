package edu.cnm.deepdive.northernnmhighlights.viewmodel;

import android.app.Application;
import android.location.Location;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.google.android.gms.maps.model.LatLng;
import edu.cnm.deepdive.northernnmhighlights.model.dto.Place;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.service.FavoritePlaceRepository;
import edu.cnm.deepdive.northernnmhighlights.service.LocationRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class FavoritePlaceViewModel extends AndroidViewModel implements DefaultLifecycleObserver {


  private final FavoritePlaceRepository placeRepository;
  private final MutableLiveData<List<PlaceType>> placeTypes;
  private final MutableLiveData<List<Place>> places;
  private final MutableLiveData<Location> location;
  private final MutableLiveData<Long> favoritePlaceId;
  private final LiveData<FavoritePlace> favoritePlace;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public FavoritePlaceViewModel(
      @NonNull @NotNull Application application) {
    super(application);
    placeRepository = new FavoritePlaceRepository(application);
    throwable = new MutableLiveData<>();
    placeTypes = new MutableLiveData<>();
    places = new MutableLiveData<>();
    location = new MutableLiveData<>();
    favoritePlaceId = new MutableLiveData<>();
    favoritePlace = Transformations.switchMap(favoritePlaceId, placeRepository::getLocal);
    pending = new CompositeDisposable();
  }

  public LiveData<List<PlaceType>> getPlaceTypes() {
    return placeRepository.getPlaceTypes();
  }

  public LiveData<List<FavoritePlace>> getPlacesLocal() {
    return placeRepository.getAllLocal();
  }

  public MutableLiveData<List<Place>> getPlaces() {
    return places;
  }

  public void setFavoritePlaceId(long id) {
    favoritePlaceId.setValue(id);
  }

  public LiveData<FavoritePlace> getFavoritePlace() {
    return favoritePlace;
  }

  public void search(PlaceType placeType, String searchText, LatLng latLng, int radius) {
    pending.add(
        placeRepository
            .search(placeType, searchText, latLng, radius)
            .subscribe(
                places::postValue,
                this::postThrowable
            )
    );
  }

  public void search(PlaceType placeType, LatLng latLng, int radius) {
    pending.add(
        placeRepository
            .search(placeType, latLng, radius)
            .subscribe(
                places::postValue,
                this::postThrowable
            )
    );
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }
}
