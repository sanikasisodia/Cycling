package com.example.cyclingapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyclingapp.data.model.ClubProfile;

import java.util.ArrayList;
import java.util.List;

public class ClubListAdapter extends ListAdapter<ClubProfile, ClubListAdapter.ClubProfileViewHolder> implements Filterable {

    private List<ClubProfile> fullList;

    public ClubListAdapter() {
        super(new ClubProfileDiff());
    }

    public static class ClubProfileDiff extends DiffUtil.ItemCallback<ClubProfile> {
        @Override
        public boolean areItemsTheSame(@NonNull ClubProfile oldItem, @NonNull ClubProfile newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ClubProfile oldItem, @NonNull ClubProfile newItem) {
            return oldItem.equals(newItem);
        }
    }

    @NonNull
    @Override
    public ClubProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_item, parent, false);
        return new ClubProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubProfileViewHolder holder, int position) {
        ClubProfile profile = getItem(position);
        holder.bind(profile, listener);
    }

    public static class ClubProfileViewHolder extends RecyclerView.ViewHolder {

        TextView clubNameTextView;

        public ClubProfileViewHolder(View itemView) {
            super(itemView);
            clubNameTextView = itemView.findViewById(R.id.clubNameTextView);
        }

        public void bind(ClubProfile profile, OnItemClickListener listener) {
            clubNameTextView.setText(profile.getClubName());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(profile);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ClubProfile profile);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<ClubProfile> list) {
        fullList = new ArrayList<>(list);
        super.submitList(list);
    }

    @Override
    public Filter getFilter() {
        return clubFilter;
    }

    private Filter clubFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ClubProfile> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ClubProfile item : fullList) {
                    if (item.getClubName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            submitList((List) results.values);
        }
    };
}



