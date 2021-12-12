package edu.cnm.deepdive.northernnmhighlights.controller;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import edu.cnm.deepdive.northernnmhighlights.R;
import edu.cnm.deepdive.northernnmhighlights.databinding.FragmentMapBinding;
import org.jetbrains.annotations.NotNull;

public class MapFragment extends Fragment implements OnMyLocationButtonClickListener,
    OnMyLocationClickListener,
    OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  private boolean permissionDenied = false;
  private GoogleMap map;
  private FragmentMapBinding binding;

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
    // TODO Attach event-listener to controls.
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // TODO Get access to a view-model and observe as necessary.
  }


  @Override
  public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
    //Set map coordinates to Santa Fe, New Mexico.
    LatLng northNm = new LatLng(35.691544, -105.944183);
    // Set map to terrain mode.
    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    googleMap.getUiSettings().isMyLocationButtonEnabled();
    googleMap.getUiSettings().isZoomControlsEnabled();
    googleMap.getUiSettings().isZoomGesturesEnabled();
//    map.setMyLocationEnabled(true);
    map.setOnMyLocationButtonClickListener(this);
    map.setOnMyLocationClickListener(this);
  }

  @Override
  public boolean onMyLocationButtonClick() {
    return false;
  }

  @Override
  public void onMyLocationClick(@NonNull @NotNull Location location) {
  }
}