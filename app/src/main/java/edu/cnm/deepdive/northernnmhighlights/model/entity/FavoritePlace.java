package edu.cnm.deepdive.northernnmhighlights.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
  private long id;

  @NonNull
  @Expose
  @ColumnInfo(name = "external_key")
  @SerializedName("id")
  private UUID externalKey;

  @Expose
  @ColumnInfo(name = "user_id", index = true)
  private Long userId;

  @NonNull
  @Expose
  @ColumnInfo(index = true)
  private Date created = new Date();

  @ColumnInfo(name = "city_name", index = true)
  @NonNull
  @Expose
  private String cityName;

  @ColumnInfo(name = "place_id", index = true)
  @NonNull
  @Expose
  private String placeId;

  @Expose
  @ColumnInfo(name = "place_name", index = true)
  private String placeName;

  @Expose
  private double latitude;

  @Expose
  private double longitude;

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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
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

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
}
