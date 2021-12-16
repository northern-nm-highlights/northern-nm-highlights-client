package edu.cnm.deepdive.northernnmhighlights.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.net.URL;

public class Place {

  @Expose
  private String name;

  @Expose
  @SerializedName("formatted_address")
  private String address;

  @Expose
  @SerializedName("icon")
  private URL iconUrl;

  @Expose
  private Geometry geometry;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public URL getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(URL iconUrl) {
    this.iconUrl = iconUrl;
  }

  public Geometry getGeometry() {
    return geometry;
  }

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }

  public static class Geometry {

    @Expose
    private Location location;

    public Location getLocation() {
      return location;
    }

    public void setLocation(Location location) {
      this.location = location;
    }

    public static class Location {

      @Expose
      @SerializedName("lat")
      private double latitude;

      @Expose
      @SerializedName("lng")
      private double longitude;

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
  }


}
