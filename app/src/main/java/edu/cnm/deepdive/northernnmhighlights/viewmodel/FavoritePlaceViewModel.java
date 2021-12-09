package edu.cnm.deepdive.northernnmhighlights.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.service.FavoritePlaceRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class FavoritePlaceViewModel extends AndroidViewModel {

  private final FavoritePlaceRepository repository;
  private final MutableLiveData<List<PlaceType>> placeList;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public FavoritePlaceViewModel(
      @NonNull @NotNull Application application) {
    super(application);
    repository = new FavoritePlaceRepository(application);
    throwable = new MutableLiveData<>();
    placeList = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<List<PlaceType>> getPlaceList() {
    return placeList;
  }

  public void LoadPlaceTypes() {
    pending.add(
        repository
            .getPlaces()
            .subscribe(
                placeList::postValue,
                this::postThrowable
            )
    );
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }
}
