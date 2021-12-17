package edu.cnm.deepdive.northernnmhighlights.controller;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.cnm.deepdive.northernnmhighlights.R;
import edu.cnm.deepdive.northernnmhighlights.databinding.FragmentMapBinding;
import edu.cnm.deepdive.northernnmhighlights.model.dto.Place;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.FavoritePlaceViewModel;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.LocationViewModel;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.PermissionsViewModel;
import java.util.stream.Collectors;

public class MapFragment extends Fragment implements OnMyLocationButtonClickListener,
    OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

  private GoogleMap googleMap;
  private FragmentMapBinding binding;
  private PermissionsViewModel permissionsViewModel;
  private LocationViewModel locationViewModel;
  private FavoritePlaceViewModel favoritePlaceViewModel;
  private View mapView;
  private LatLng latLng;
  private GoogleMap map;
  private boolean locationAvailable;
  private long favoritePlaceId;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MapFragmentArgs args = MapFragmentArgs.fromBundle(getArguments());
    favoritePlaceId = args.getId();
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentMapBinding.inflate(inflater, container, false);
    SupportMapFragment fragment =
        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    fragment.getMapAsync(this);
    mapView = fragment.getView();
    binding.search.setOnClickListener((v) -> {
      LatLng latLng = (this.latLng != null) ? this.latLng : map.getCameraPosition().target;
//      float zoom = map.getCameraPosition().zoom;
//      double circumference = 256 * Math.pow(2, zoom);
      int radius = 25_000;
      PlaceType placeType = (PlaceType) binding.placeType.getSelectedItem();
      favoritePlaceViewModel.search(placeType, binding.searchText.getText().toString().trim(),
          latLng, radius);
    });
    // TODO Attach event-listener to controls.
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ViewModelProvider viewModelProvider = new ViewModelProvider(getActivity());
    permissionsViewModel = viewModelProvider.get(PermissionsViewModel.class);
    permissionsViewModel.getPermissionsGranted().observe(getViewLifecycleOwner(), (permissions) -> {
      if (permissions.contains(permission.ACCESS_FINE_LOCATION)) {
        locationAvailable = true;
        if (map != null) {
          useLocation();
        }
        locationViewModel = viewModelProvider.get(LocationViewModel.class);
        locationViewModel.getLocation().observe(getViewLifecycleOwner(), (latLng) -> {
          this.latLng = latLng;
        });
      }
    });
    favoritePlaceViewModel = viewModelProvider.get(FavoritePlaceViewModel.class);
    favoritePlaceViewModel.getPlaceTypes().observe(getViewLifecycleOwner(), (placeTypes) -> {
      ArrayAdapter<PlaceType> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, placeTypes);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      binding.placeType.setAdapter(adapter);
    });
//    favoritePlaceViewModel.getPlaces().observe(getViewLifecycleOwner(), (places) -> {
//      Log.d(getClass().getSimpleName(), places.stream().map(Place::getName).collect(Collectors.joining("\n")));
//    });
    if (favoritePlaceId != 0) {
      favoritePlaceViewModel.getFavoritePlace().observe(getViewLifecycleOwner(), (place) -> {
        if (map != null) {
          LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
          CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
          MarkerOptions marker = new MarkerOptions()
              .position(latLng)
              .title(place.getPlaceName());
          map.addMarker(marker);
          map.moveCamera(update);

        }
        // TODO Center map on favorite place.
      });
      favoritePlaceViewModel.setFavoritePlaceId(favoritePlaceId);
    }
  }


  @Override
  public void onMapReady(@NonNull GoogleMap googleMap) {
    map = googleMap;
    //Set map coordinates to Santa Fe, New Mexico.
    LatLng northNm = new LatLng(35.691544, -105.944183);
    // Set map to terrain mode.
    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    CameraUpdate update = CameraUpdateFactory
        .newLatLngZoom(northNm, 6);
    googleMap.moveCamera(update);
    googleMap.getUiSettings().setZoomControlsEnabled(true);
    if (locationAvailable) {
      useLocation();
    }

  }

  @SuppressLint("MissingPermission")
  private void useLocation() {
    View locationButton = mapView.findViewWithTag("GoogleMapMyLocationButton");
    map.setMyLocationEnabled(true);
    map.getUiSettings().setMyLocationButtonEnabled(true);
    LayoutParams layoutParams =
        new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
    layoutParams.setMargins(16, 16, 16, 16);
    locationButton.setLayoutParams(layoutParams);
  }

  @Override
  public boolean onMyLocationButtonClick() {
    return false;
  }

  @Override
  public void onMyLocationClick(@NonNull Location location) {
  }
}