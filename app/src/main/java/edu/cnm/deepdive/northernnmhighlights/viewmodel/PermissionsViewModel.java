package edu.cnm.deepdive.northernnmhighlights.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.northernnmhighlights.service.PermissionsRepository;
import java.util.List;
import java.util.Set;

public class PermissionsViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final PermissionsRepository repository;

  public PermissionsViewModel(@NonNull Application application) {
    super(application);
    repository = PermissionsRepository.getInstance();
  }

  public LiveData<List<String>> getPermissionsToRequest() {
    return repository.getPermissionsToRequest();
  }

  public LiveData<Set<String>> getPermissionsGranted() {
    return repository.getPermissionsGranted();
  }

  public void updateGrants(String[] permissions, int[] results) {
    repository.updateGrants(permissions, results);
  }

  public void grant(String permission) {
    repository.grant(permission);
  }
}
