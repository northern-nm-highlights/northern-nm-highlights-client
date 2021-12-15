package edu.cnm.deepdive.northernnmhighlights.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.northernnmhighlights.R;

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
        .setMessage(null)
        .setNeutralButton(android.R.string.ok, (d, w) -> { /* do nothing */ })
        .create();
  }

}
