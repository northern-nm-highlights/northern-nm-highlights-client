package edu.cnm.deepdive.northernnmhighlights.controller;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfiguration.Builder;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import edu.cnm.deepdive.northernnmhighlights.MobileNavigationDirections;
import edu.cnm.deepdive.northernnmhighlights.R;
import edu.cnm.deepdive.northernnmhighlights.controller.PermissionsFragment.OnAcknowledgeListener;
import edu.cnm.deepdive.northernnmhighlights.databinding.ActivityMainBinding;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.LoginViewModel;
import edu.cnm.deepdive.northernnmhighlights.viewmodel.PermissionsViewModel;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements OnAcknowledgeListener {

  public static final int PERMISSIONS_REQUEST_CODE = 1234;
  private AppBarConfiguration appBarConfiguration;
  private ActivityMainBinding binding;
  private LoginViewModel loginViewModel;
  private PermissionsViewModel permissionsViewModel;
  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ViewModelProvider viewModelProvider = new ViewModelProvider(this);
    setupLoginViewModel(viewModelProvider);
    setupPermissionsViewModel(viewModelProvider);
    setupUI();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled;
    int itemId = item.getItemId();
    if (itemId == R.id.sign_out) {
      loginViewModel.signOut();
      handled = true;
    } else if (itemId == R.id.action_settings) {
      navController.navigate(MobileNavigationDirections.openSettings());
      handled = true;
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this,
        R.id.nav_host_fragment_content_main);
    return NavigationUI.navigateUp(navController, appBarConfiguration)
        || super.onSupportNavigateUp();
  }


  @Override
  public void onAcknowledge(String[] permissionsToRequest) {
    ActivityCompat.requestPermissions(this, permissionsToRequest, PERMISSIONS_REQUEST_CODE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == PERMISSIONS_REQUEST_CODE) {
      permissionsViewModel.updateGrants(permissions, grantResults);
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  private void setupUI() {
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.appBarMain.toolbar);
    DrawerLayout drawer = binding.drawerLayout;
    NavigationView navigationView = binding.navView;
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    appBarConfiguration =
        new Builder(R.id.nav_favorites, R.id.nav_place_types, R.id.nav_map)
            .setOpenableLayout(drawer)
            .build();
    navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);
  }

  private void setupPermissionsViewModel(ViewModelProvider viewModelProvider) {
    permissionsViewModel = viewModelProvider.get(PermissionsViewModel.class);
    permissionsViewModel.getPermissionsToRequest().observe(this, (permissionsToRequest) -> {
      List<String> permissionsToExplain = permissionsToRequest
          .stream()
          .filter((permission) ->
              ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
          .collect(Collectors.toList());
      if (!permissionsToExplain.isEmpty()) {
        navController.navigate(MobileNavigationDirections.openPermissionExplanations(
            permissionsToRequest.toArray(new String[0]), permissionsToExplain.toArray(new String[0])));
      } else if (!permissionsToRequest.isEmpty()) {
       onAcknowledge(permissionsToRequest.toArray(new String[0]));
      }
    });
  }

  private void setupLoginViewModel(ViewModelProvider viewModelProvider) {
    loginViewModel = viewModelProvider.get(LoginViewModel.class);
    getLifecycle().addObserver(loginViewModel);
    loginViewModel.getAccount().observe(this, (account) -> {
      if (account == null) {
        Intent intent = new Intent(this, LoginActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      }
    });
    loginViewModel.getUser().observe(this, (user) -> {
      Log.d(getClass().getSimpleName(), user.getDisplayName());
    });
    loginViewModel.loadProfile();
  }


}