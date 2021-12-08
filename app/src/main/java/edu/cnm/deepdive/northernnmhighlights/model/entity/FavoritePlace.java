package edu.cnm.deepdive.northernnmhighlights.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.UUID;

@Entity(
    tableName = "favorite_place",
    indices = {
        @Index(value = {"external_key"}, unique = true)
    },
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
        )
    }

)

public class FavoritePlace {


  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "favorite_place_id")
  private long id;

  @NonNull
  @Expose
  @ColumnInfo(name = "external_key")
  private UUID externalKey;

  @Expose
  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  @NonNull
  @Expose
  @ColumnInfo(index = true)
  private Date created;

  @ColumnInfo(name = "city_name", index = true)
  @NonNull
  @Expose
  private String cityName;

  @ColumnInfo(name = "place_id", index = true)
  @NonNull
  @Expose
  private String placeId;

  @ColumnInfo(name = "place_name", index = true)
  private String placeName;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public UUID getExternalKey() {
    return externalKey;
  }

  public void setExternalKey(@NonNull UUID externalKey) {
    this.externalKey = externalKey;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  @NonNull
  public String getCityName() {
    return cityName;
  }

  public void setCityName(@NonNull String cityName) {
    this.cityName = cityName;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

}
