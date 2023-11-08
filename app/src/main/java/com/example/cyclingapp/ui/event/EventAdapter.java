package com.example.cyclingapp.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.Event;
import com.example.cyclingapp.data.model.Role;

import java.util.List;

/**
 * Adapter for the RecyclerView that displays a list of events.
 * It binds the Event data to the views that are displayed within a RecyclerView.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> events;
    private final EventAdapterListener listener;

    private Role userRole;


    /**
     * Listener interface to handle click events on buttons in each list item.
     */
    public interface EventAdapterListener {
        void onEditClick(Event event);
        void onDeleteClick(Event event);
    }

    /**
     * Constructor for EventAdapter.
     *
     * @param events   The list of events to display.
     * @param listener The listener that handles edit and delete actions.
     */
    public EventAdapter(List<Event> events, EventAdapterListener listener, Role userRole) {
        this.events = events;
        this.listener = listener;
        this.userRole = userRole;
    }

    /**
     * Updates the list of events and notifies the adapter to refresh the view.
     *
     * @param newEvents The new list of events.
     */
    public void setEvents(List<Event> newEvents) {
        this.events = newEvents;
        notifyDataSetChanged(); // Notify any registered observers that the data set has changed.
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.setCurrentEvent(event);
        holder.tvEventName.setText(event.getName());
        holder.tvEventType.setText(event.getType());
        holder.tvEventDifficulty.setText(event.getDifficulty());

        // Show edit and delete buttons only if the user is an admin
        if (userRole != null && userRole.equals(Role.ADMIN)) {
            holder.btnEditEvent.setVisibility(View.VISIBLE);
            holder.btnDeleteEvent.setVisibility(View.VISIBLE);
        } else {
            holder.btnEditEvent.setVisibility(View.GONE);
            holder.btnDeleteEvent.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return events.size();
    }

    /**
     * ViewHolder class for layout of each list item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName, tvEventType, tvEventDifficulty;
        public Button btnEditEvent, btnDeleteEvent;
        private Event currentEvent; // Holds the current event

        public ViewHolder(View itemView, final EventAdapterListener listener) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventType = itemView.findViewById(R.id.tvEventType);
            tvEventDifficulty = itemView.findViewById(R.id.tvEventDifficulty);
            btnEditEvent = itemView.findViewById(R.id.btnEditEvent);
            btnDeleteEvent = itemView.findViewById(R.id.btnDeleteEvent);

            // Set up click listeners for edit and delete buttons
            btnEditEvent.setOnClickListener(view -> {
                if (currentEvent != null) {
                    listener.onEditClick(currentEvent);
                }
            });

            btnDeleteEvent.setOnClickListener(view -> {
                if (currentEvent != null) {
                    listener.onDeleteClick(currentEvent);
                }
            });
        }

        public void setCurrentEvent(Event event) {
            this.currentEvent = event;
        }
    }

}
