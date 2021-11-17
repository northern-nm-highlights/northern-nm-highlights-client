package edu.cnm.deepdive.northernnmhighlights.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.northernnmhighlights.databinding.FragmentPlaceTypeBinding;

public class PlaceTypeFragment extends Fragment {

  private FragmentPlaceTypeBinding binding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
     super.onCreateView(inflater, container, savedInstanceState);
     binding = FragmentPlaceTypeBinding.inflate(inflater, container, false );
     return binding.getRoot();
  }
}