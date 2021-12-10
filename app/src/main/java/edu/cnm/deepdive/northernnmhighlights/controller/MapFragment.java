package edu.cnm.deepdive.northernnmhighlights.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import edu.cnm.deepdive.northernnmhighlights.databinding.FragmentMapBinding;
import java.util.List;
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
    map.setOnMyLocationButtonClickListener((OnMyLocationButtonClickListener) this);
    map.setOnMyLocationClickListener((OnMyLocationClickListener) this);
    enableMyLocation();
  }

  private void enableMyLocation() {
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//        == PackageManager.PERMISSION_GRANTED) {
//      if (map != null) {
//        map.setMyLocationEnabled(true);
//      }
//    } else {
//      // Permission to access the location is missing. Show rationale and request permission
//      PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
//    }
  }

  @Override
  public boolean onMyLocationButtonClick() {
    return false;
  }

  @Override
  public void onMyLocationClick(@NonNull @NotNull Location location) {
  }
}