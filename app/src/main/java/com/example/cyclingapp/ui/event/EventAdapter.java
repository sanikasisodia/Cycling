package com.example.cyclingapp.ui.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> events; // Cached copy of events

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position); // Get the event at the current position
        holder.tvEventName.setText(event.getName()); // Set the name of the event
        holder.tvEventType.setText(event.getType()); // Set the type of the event
        holder.tvEventDifficulty.setText(event.getDifficulty()); // Set the difficulty of the event
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName, tvEventType, tvEventDifficulty; // Declare the TextViews

        public ViewHolder(View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tvEventName); // Initialize the TextViews
            tvEventType = itemView.findViewById(R.id.tvEventType);
            tvEventDifficulty = itemView.findViewById(R.id.tvEventDifficulty);
        }
    }
}
