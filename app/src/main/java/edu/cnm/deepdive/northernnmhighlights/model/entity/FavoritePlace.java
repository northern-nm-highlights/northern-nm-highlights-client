package edu.cnm.deepdive.northernnmhighlights.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
  private UUID id;

  @NonNull
  @Expose
  @ColumnInfo(name = "external_key")
  private UUID externalKey;

  @NonNull
  @Expose
  @ColumnInfo(index = true)
  private UUID userId;

  @NonNull
  @Expose
  @ColumnInfo(index = true)
  private Date created;

  @ColumnInfo(index = true)
  @NonNull
  @Expose
  private String cityName;

  @ColumnInfo(index = true)
  private String placeName;

  @Expose
  @ColumnInfo(index = true)
  private String placeId;

  public UUID getId() {
    return id;
  }

  @NonNull
  public UUID getExternalKey() {
    return externalKey;
  }

  @NonNull
  public UUID getUserId() {
    return userId;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  @NonNull
  public String getCityName() {
    return cityName;
  }

  public String getPlaceName() {
    return placeName;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setExternalKey(@NonNull UUID externalKey) {
    this.externalKey = externalKey;
  }

  public void setUserId(@NonNull UUID userId) {
    this.userId = userId;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  public void setCityName(@NonNull String cityName) {
    this.cityName = cityName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

}
