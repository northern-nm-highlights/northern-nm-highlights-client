package edu.cnm.deepdive.northernnmhighlights.service;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.northernnmhighlights.R;
import edu.cnm.deepdive.northernnmhighlights.model.dao.FavoritePlaceDao;
import edu.cnm.deepdive.northernnmhighlights.model.dao.PlaceTypeDao;
import edu.cnm.deepdive.northernnmhighlights.model.dao.UserDao;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.model.entity.User;
import edu.cnm.deepdive.northernnmhighlights.service.NnmhlDatabase.Converters;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Database(
    entities = {FavoritePlace.class, PlaceType.class, User.class},
    version = 1
)
@TypeConverters({Converters.class})
public abstract class NnmhlDatabase extends RoomDatabase {

  private static Application context;

  public static void setContext(Application context) {
    NnmhlDatabase.context = context;
  }

  public static NnmhlDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public abstract FavoritePlaceDao getFavoritePlaceDao();

  public abstract PlaceTypeDao getPlaceTypeDao();

  public abstract UserDao getUserDao();

  private static class InstanceHolder {

    private static final NnmhlDatabase INSTANCE =
        Room.databaseBuilder(context, NnmhlDatabase.class, "nnmhl-db")
            .addCallback(new Callback())
            .build();

  }

  private static class Callback extends RoomDatabase.Callback {

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      try (
          InputStream input = context.getResources().openRawResource(R.raw.favorite_places);
          Reader reader = new InputStreamReader(input)
      ) {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
        //noinspection ResultOfMethodCallIgnored
        NnmhlDatabase
            .getInstance()
            .getFavoritePlaceDao()
            .insert(gson.fromJson(reader, FavoritePlace[].class))
            .subscribeOn(Schedulers.io())
            .subscribe(
                (ids) -> {},
                (throwable) -> Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable)
            );
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static class Converters {

    @TypeConverter
    public static Long toLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    @TypeConverter
    public static Date toDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }

    @TypeConverter
    public static String toString(UUID value) {
      return (value != null) ? value.toString() : null;
    }

    @TypeConverter
    public static UUID toUUID(String value) {
      return (value != null) ? UUID.fromString(value) : null;
    }
  }
}
