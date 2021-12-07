package edu.cnm.deepdive.northernnmhighlights.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.Date;
import java.util.UUID;

@Entity(
    tableName = "user",
    indices = {
        @Index(value = {"oauth_key"}, unique = true),
        @Index(value = {"display_name"}, unique = true),
        @Index(value = {"external_key"}, unique = true)
    },
    foreignKeys = {
        @ForeignKey(
            entity = FavoritePlace.class,
            parentColumns = "user_id",
            childColumns = "user_id",
            onDelete = ForeignKey.NO_ACTION
        )
    }

)
public class User {

  @PrimaryKey(autoGenerate = true)
  private UUID userId;

  @Expose
  @NonNull
  private String oauthKey;

  @Expose
  @NonNull
  private String displayName;

  @Expose
  @NonNull
  private UUID externalKey;

  @Expose
  @NonNull
  private Date created;

  public UUID getUserId() {
    return userId;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  @NonNull
  public UUID getExternalKey() {
    return externalKey;
  }

  public void setExternalKey(@NonNull UUID externalKey) {
    this.externalKey = externalKey;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }
}
