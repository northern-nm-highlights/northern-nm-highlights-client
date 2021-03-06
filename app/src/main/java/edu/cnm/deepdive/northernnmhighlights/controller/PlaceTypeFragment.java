package edu.cnm.deepdive.northernnmhighlights.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.northernnmhighlights.databinding.FragmentPlaceTypeBinding;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.FavoritePlaceViewModel;

public class PlaceTypeFragment extends Fragment {

  private FragmentPlaceTypeBinding binding;
  private FavoritePlaceViewModel viewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    binding = FragmentPlaceTypeBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(FavoritePlaceViewModel.class);
    viewModel.getPlaceTypes().observe(getViewLifecycleOwner(), (placeTypes) -> {
      ArrayAdapter<PlaceType> adapter
          = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, placeTypes);
      binding.placeTypes.setAdapter(adapter);
    });
  }


}