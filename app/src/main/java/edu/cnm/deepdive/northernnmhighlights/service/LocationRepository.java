package edu.cnm.deepdive.northernnmhighlights.service;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import androidx.lifecycle.LiveData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class LocationRepository {

  private static Application context;

  private final LocationLiveData location;

  private LocationRepository() {
    location = new LocationLiveData();
  }

  public static void setContext(Application context) {
    LocationRepository.context = context;
  }

  public static LocationRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public LiveData getLocation() {
    return location;
  }

  private static class InstanceHolder {

    private static final LocationRepository INSTANCE = new LocationRepository();
  }

  private static class LocationLiveData extends LiveData<LatLng> {

    private static final LocationRequest request;


    private final FusedLocationProviderClient client;
    private final LocationCallback callback;

    static {
      request = LocationRequest.create();
      request.setInterval(10_000);
      request.setFastestInterval(5_000);
      request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private LocationLiveData() {
      client = LocationServices.getFusedLocationProviderClient(context);
      callback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
          if (locationResult != null && !locationResult.getLocations().isEmpty()) {
            List<Location> locations = locationResult.getLocations();
            setLocation(locations.get(locations.size() - 1));
          }
        }
      };
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
      super.onActive();
      client
          .getLastLocation()
          .addOnSuccessListener((location) -> {
            if (location != null) {
              setLocation(location);
            }
          });
      startLocationUpdates();
    }

    @Override
    protected void onInactive() {
      super.onInactive();
      client.removeLocationUpdates(callback);
    }

    private void setLocation(Location location) {
      setValue(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
      client.requestLocationUpdates(request, callback, null);
    }
  }
}
