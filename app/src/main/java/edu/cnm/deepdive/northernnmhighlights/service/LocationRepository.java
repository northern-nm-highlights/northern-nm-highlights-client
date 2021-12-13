package edu.cnm.deepdive.northernnmhighlights.service;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsRequest.Builder;
import com.google.android.gms.location.SettingsClient;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class LocationRepository {

  private static Application context;

  private final FusedLocationProviderClient locationClient;

  private Callback callback;

  private LocationRepository() {
    locationClient = LocationServices.getFusedLocationProviderClient(context);
  }

  public static void setContext(Application context) {
    LocationRepository.context = context;
  }

  public static LocationRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  @SuppressLint("MissingPermission")
  public Single<Location> getLastLocation() {
    return Single
        .create((SingleOnSubscribe<Location>) (emitter) ->
            locationClient
                .getLastLocation()
                .addOnSuccessListener(emitter::onSuccess)
                .addOnFailureListener(emitter::onError))
        .observeOn(Schedulers.io());
  }

  @SuppressLint("MissingPermission")
  public Observable<Location> getCurrentLocation() {
    return Observable
        .create((ObservableEmitter<Location> emitter) -> {
          LocationRequest request = LocationRequest.create();
          request.setInterval(15000);
          request.setFastestInterval(5000);
          request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
          LocationSettingsRequest settingsRequest = new Builder()
              .addLocationRequest(request)
              .build();
          SettingsClient settingsClient = LocationServices.getSettingsClient(context);
          settingsClient
              .checkLocationSettings(settingsRequest)
              .addOnSuccessListener((response) -> {
                /* TODO use response to see what location services are available. */
              })
              .addOnFailureListener((throwable) -> {
                /* TODO fail gracefully */
              });
          callback = new Callback(emitter);
          locationClient.requestLocationUpdates(request, callback, Looper.myLooper());
        })
        .observeOn(Schedulers.io());
  }

  private static class Callback extends LocationCallback {

    private final ObservableEmitter<Location> emitter;

    public Callback(ObservableEmitter<Location> emitter) {
      this.emitter = emitter;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
      super.onLocationResult(locationResult);
      Log.d(getClass().getSimpleName(), locationResult.getLastLocation().toString());
      emitter.onNext(locationResult.getLastLocation());
    }

    @Override
    public void onLocationAvailability(LocationAvailability locationAvailability) {
      super.onLocationAvailability(locationAvailability);
      Log.d(getClass().getSimpleName(), locationAvailability.toString());
    }
  }

  private static class InstanceHolder {

    private static final LocationRepository INSTANCE = new LocationRepository();
  }
}
