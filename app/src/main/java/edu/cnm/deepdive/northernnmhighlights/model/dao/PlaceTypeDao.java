package edu.cnm.deepdive.northernnmhighlights.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import edu.cnm.deepdive.northernnmhighlights.model.entity.PlaceType;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface PlaceTypeDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<PlaceType> placeTypes);

  @Query("SELECT * FROM place_type ORDER BY display_name ASC")
  LiveData<List<PlaceType>> getAll();

}
