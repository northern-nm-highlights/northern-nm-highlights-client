package edu.cnm.deepdive.northernnmhighlights.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.northernnmhighlights.model.entity.User;
import edu.cnm.deepdive.northernnmhighlights.service.GoogleSignInRepository;
import edu.cnm.deepdive.northernnmhighlights.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final GoogleSignInRepository signInRepository;
  private final UserRepository userRepository;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<User> user;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public LoginViewModel(@NonNull Application application) {
    super(application);
    signInRepository = GoogleSignInRepository.getInstance();
    userRepository = new UserRepository(application);
    account = new MutableLiveData<>();
    user = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    refresh();
  }

  public LiveData<GoogleSignInAccount> getAccount() {
    return account;
  }

  public LiveData<User> getUser() {
    return user;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void refresh() {
    pending.add(
        signInRepository
            .refresh()
            .subscribe(
                account::postValue,
                this::postThrowable
            )
    );
  }

  public void startSignIn(ActivityResultLauncher<Intent> launcher) {
    signInRepository.startSignIn(launcher);
  }

  public void completeSignIn(ActivityResult result) {
    Disposable disposable = signInRepository
        .completeSignIn(result)
        .subscribe(
            account::postValue,
            this::postThrowable
        );
    pending.add(disposable);
  }

  public void signOut() {
    Disposable disposable = signInRepository
        .signOut()
        .doFinally(() -> account.postValue(null))
        .subscribe(
            () -> {},
            this::postThrowable
        );
    pending.add(disposable);
  }

  // Testing profile round trip
  public void loadProfile() {
    Disposable disposable = userRepository
        .getProfile()
        .subscribe(
            user::postValue,
            this::postThrowable
        );
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }
}
