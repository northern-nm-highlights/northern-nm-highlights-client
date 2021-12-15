package edu.cnm.deepdive.northernnmhighlights.service;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PermissionsRepository {

  private static Application context;

  private final MutableLiveData<List<String>> permissionsToRequest;
  private final MutableLiveData<Set<String>> permissionsGranted;

  private PermissionsRepository() {
    try {
      String[] permissions = context
          .getPackageManager()
          .getPackageInfo(context.getPackageName(),
              PackageManager.GET_META_DATA | PackageManager.GET_PERMISSIONS)
          .requestedPermissions;
      List<String> requests = new LinkedList<>();
      Set<String> grants = new HashSet<>();
      for (String permission : permissions) {
        if (ContextCompat.checkSelfPermission(context, permission)
            != PackageManager.PERMISSION_GRANTED) {
          requests.add(permission);
        } else {
          grants.add(permission);
        }
      }
      permissionsToRequest = new MutableLiveData<>(requests);
      permissionsGranted = new MutableLiveData<>(grants);
    } catch (NameNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public LiveData<List<String>> getPermissionsToRequest() {
    return permissionsToRequest;
  }

  public LiveData<Set<String>> getPermissionsGranted() {
    return permissionsGranted;
  }

  public void grant(@NonNull String permission) {
    Set<String> grants = permissionsGranted.getValue();
    grants.add(permission);
    permissionsGranted.postValue(grants);
  }


  public static void setContext(Application context) {
    PermissionsRepository.context = context;
  }

  public static PermissionsRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public void updateGrants(String[] permissions, int[] results) {
    Set<String> grants = permissionsGranted.getValue();
    for (int i = 0; i < permissions.length; i++) {
      String permission = permissions[i];
      int result = results[i];
      if (result == PackageManager.PERMISSION_GRANTED) {
        grants.add(permission);
      } else {
        grants.remove(permission);
      }
    }
    permissionsGranted.postValue(grants);
  }

  private static class InstanceHolder {

    private static final PermissionsRepository INSTANCE = new PermissionsRepository();
  }
}
