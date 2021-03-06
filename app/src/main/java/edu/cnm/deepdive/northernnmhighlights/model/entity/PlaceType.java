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
    tableName = "place_type",
    indices = {
        @Index(value = {"external_key"}, unique = true)
    }
)
public class PlaceType {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "place_type_id")
  private long id;

  @NonNull
  @Expose
  @ColumnInfo(name = "external_key")
  @SerializedName("id")
  private UUID externalKey;

  @ColumnInfo(name = "display_name", index = true)
  @NonNull
  @Expose
  private String displayName;

  @NonNull
  @Expose
  @ColumnInfo(index = true)
  private Date created;

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

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  @NonNull
  @Override
  public String toString() {
    return displayName;
  }
}