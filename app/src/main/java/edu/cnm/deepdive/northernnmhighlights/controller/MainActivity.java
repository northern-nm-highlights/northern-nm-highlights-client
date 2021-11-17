package edu.cnm.deepdive.northernnmhighlights.controller;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.northernnmhighlights.R;
import edu.cnm.deepdive.northernnmhighlights.databinding.ActivityMainBinding;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.LoginViewModel;

public class MainActivity extends AppCompatActivity {

  private LoginViewModel loginViewModel;
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    binding.signOut.setOnClickListener((v) -> loginViewModel.signOut());
    loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    getLifecycle().addObserver(loginViewModel);
    loginViewModel.getAccount().observe(this, (account) -> {
      if (account != null) {
        binding.displayName.setText(account.getDisplayName());
      } else {
        Intent intent=new Intent(this, LoginActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      }
    });
    setContentView(binding.getRoot());
  }
}