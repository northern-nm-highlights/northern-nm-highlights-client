package edu.cnm.deepdive.northernnmhighlights.controller;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import edu.cnm.deepdive.northernnmhighlights.R;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class PermissionsFragment extends DialogFragment {

  private static final String EXPLANATION_KEY_SUFFIX = "_explanation";
  private static final String PERMISSION_DELIMITER = "\\.";

  private String[] permissionsToExplain;
  private String[] permissionsToRequest;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PermissionsFragmentArgs args = PermissionsFragmentArgs.fromBundle(getArguments());
    permissionsToExplain = args.getPermissionsToExplain();
    permissionsToRequest = args.getPermissionsToRequest();
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    return new AlertDialog.Builder(getContext())
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(R.string.permissions_title)
        .setMessage(buildMessage())
        .setNeutralButton(android.R.string.ok, (d, w) ->
            getListener().onAcknowledge(permissionsToRequest))
        .create();
  }

  private String buildMessage() {
    Resources resources = getResources();
    String packageName = getContext().getPackageName();
    return Arrays
        .stream(permissionsToExplain)
        .map((permission) -> {
          String[] parts = permission.split(PERMISSION_DELIMITER);
          String key = parts[parts.length - 1].toLowerCase() + EXPLANATION_KEY_SUFFIX;
          int id = resources.getIdentifier(key, "string", packageName);
          return (id != 0) ? getString(id) : null;
        })
        .filter(Objects::nonNull)
        .distinct()
        .collect(Collectors.joining("\n"));
  }

  private OnAcknowledgeListener getListener() {
    OnAcknowledgeListener listener;
    Fragment parentFragment = getParentFragment();
    FragmentActivity hostActivity = getActivity();
    if (parentFragment instanceof OnAcknowledgeListener) {
      listener = (OnAcknowledgeListener) parentFragment;
    } else if (hostActivity instanceof OnAcknowledgeListener) {
      listener = (OnAcknowledgeListener) hostActivity;
    } else {
      listener = (permissions) -> {
      };
    }
    return listener;
  }

  public interface OnAcknowledgeListener {

    void onAcknowledge(String[] permissionsToRequest);
  }
}
