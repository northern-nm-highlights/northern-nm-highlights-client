package edu.cnm.deepdive.northernnmhighlights.controller;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import edu.cnm.deepdive.northernnmhighlights.R;
import edu.cnm.deepdive.northernnmhighlights.databinding.FragmentMapBinding;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.LocationViewModel;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.PermissionsViewModel;

public class MapFragment extends Fragment implements OnMyLocationButtonClickListener,
    OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  private boolean permissionDenied = false;
  private GoogleMap googleMap;
  private FragmentMapBinding binding;
  private PermissionsViewModel permissionsViewModel;
  private LocationViewModel locationViewModel;
  private View mapView;
  private LatLng latLng;
  private GoogleMap map;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO read any arguments passed to the fragment.
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
      // TODO If latLng isn't null, use it along with the search text to make a search using places API.
      //  Otherwise, use map.getCameraPosition().target for the location of the search.
    });
    // TODO Attach event-listener to controls.
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    permissionsViewModel = new ViewModelProvider(getActivity()).get(PermissionsViewModel.class);
    permissionsViewModel.getPermissionsGranted().observe(getViewLifecycleOwner(), (permissions) -> {
      if (permissions.contains(permission.ACCESS_FINE_LOCATION)) {
        locationViewModel = new ViewModelProvider(getActivity()).get(LocationViewModel.class);
        locationViewModel.getLocation().observe(getViewLifecycleOwner(), (latLng) -> {
          this.latLng = latLng;
        });
      }
    });
    // TODO Get access to a view-model and observe as necessary.
  }


  @SuppressLint("MissingPermission")
  @Override
  public void onMapReady(@NonNull GoogleMap googleMap) {
    map = googleMap;
    //Set map coordinates to Santa Fe, New Mexico.
    LatLng northNm = new LatLng(35.691544, -105.944183);
    // Set map to terrain mode.
    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    CameraUpdate update = CameraUpdateFactory
        .newLatLngZoom(northNm, 6);
    googleMap.setMyLocationEnabled(true);
    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    googleMap.getUiSettings().setZoomControlsEnabled(true);
    googleMap.getUiSettings().isZoomGesturesEnabled();
    googleMap.moveCamera(update);
    View locationButton = mapView.findViewWithTag("GoogleMapMyLocationButton");
    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
    layoutParams.setMargins(16, 16,16,16);
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