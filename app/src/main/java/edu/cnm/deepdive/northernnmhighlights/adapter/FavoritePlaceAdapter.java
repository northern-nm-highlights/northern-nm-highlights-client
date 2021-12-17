package edu.cnm.deepdive.northernnmhighlights.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.northernnmhighlights.adapter.FavoritePlaceAdapter.Holder;
import edu.cnm.deepdive.northernnmhighlights.databinding.ItemFavoritePlaceBinding;
import edu.cnm.deepdive.northernnmhighlights.model.entity.FavoritePlace;
import java.util.List;

public class FavoritePlaceAdapter extends RecyclerView.Adapter<Holder> {

  private final LayoutInflater inflater;
  private final List<FavoritePlace> places;
  private final OnNavigateListener listener;

  public FavoritePlaceAdapter(Context context, List<FavoritePlace> places,
      OnNavigateListener listener) {
    inflater = LayoutInflater.from(context);
    this.places = places;
    this.listener = listener;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(ItemFavoritePlaceBinding.inflate(inflater, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return places.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    private final ItemFavoritePlaceBinding binding;

    public Holder(@NonNull ItemFavoritePlaceBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(int position) {
      FavoritePlace favoritePlace = places.get(position);
      binding.name.setText(favoritePlace.getPlaceName());
      binding.city.setText(favoritePlace.getCityName());
      binding.getRoot().setOnClickListener((v) -> listener.onNavigate(favoritePlace));
    }
  }

  public interface OnNavigateListener {

    void onNavigate(FavoritePlace favoritePlace);
  }
}
