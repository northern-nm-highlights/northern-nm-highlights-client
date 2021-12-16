package edu.cnm.deepdive.northernnmhighlights.model.dto;

import com.google.gson.annotations.Expose;
import java.util.List;

public class SearchResult {

  @Expose
  private List<Place> results;

  @Expose
  private String status;

  public List<Place> getResults() {
    return results;
  }

  public void setResults(List<Place> results) {
    this.results = results;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
