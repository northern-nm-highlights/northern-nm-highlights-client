package edu.cnm.deepdive.northernnmhighlights.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.northernnmhighlights.databinding.FragmentFavoritePlaceBinding;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.FavoritePlaceViewModel;

public class FavoritePlaceFragment extends Fragment {

  private FavoritePlaceViewModel viewModel;
  private FragmentFavoritePlaceBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentFavoritePlaceBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(this).get(FavoritePlaceViewModel.class);
    viewModel
        .getPlaceTypes()
        .observe(getViewLifecycleOwner(), (placeTypes) -> {
          // TODO Populate a spinner of the place type
//          GameSummaryAdapter adapter = new GameSummaryAdapter(getContext(), games);
//          binding.games.setAdapter(adapter);
        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}