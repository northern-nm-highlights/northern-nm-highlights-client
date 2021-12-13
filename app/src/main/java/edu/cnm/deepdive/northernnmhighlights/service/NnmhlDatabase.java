package edu.cnm.deepdive.northernnmhighlights.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.northernnmhighlights.model.dao.FavoritePlaceDao;
import edu.cnm.deepdive.northernnmhighlights.model.dao.PlaceTypeDao;
import edu.cnm.deepdive.northernnmhighlights.model.dao.UserDao;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import edu.cnm.deepdive.northernnmhighlights.model.entity.User;
import edu.cnm.deepdive.northernnmhighlights.service.NnmhlDatabase.Converters;
import java.util.Date;
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
            .build();

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
