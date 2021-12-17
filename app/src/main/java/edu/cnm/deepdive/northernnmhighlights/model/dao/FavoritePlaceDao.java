package edu.cnm.deepdive.northernnmhighlights.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Dao
public interface FavoritePlaceDao {

  @Insert
  Single<Long> insert(FavoritePlace favoritePlace);

  @Insert
  Single<List<Long>> insert(FavoritePlace... favoritePlaces);

  @Insert
  Single<List<Long>> insert(Collection<FavoritePlace> favoritePlaces);

  @Update
  Single<Integer> update(FavoritePlace favoritePlace);

  @Update
  Single<Integer> update(FavoritePlace... favoritePlaces);

  @Update
  Single<Integer> update(Collection<FavoritePlace> favoritePlaces);

  @Delete
  Single<Integer> delete(FavoritePlace favoritePlace);

  @Delete
  Single<Integer> delete(FavoritePlace... favoritePlaces);

  @Delete
  Single<Integer> delete(Collection<FavoritePlace> favoritePlaces);

  @Query("SELECT * FROM favorite_place ORDER BY place_name ASC")
  LiveData<List<FavoritePlace>> selectAll();

  @Query("SELECT * FROM favorite_place WHERE favorite_place_id = :favoritePlaceId")
  LiveData<FavoritePlace> select(long favoritePlaceId);

}
