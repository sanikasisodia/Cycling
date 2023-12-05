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

    //public static EventAdapter.EventAdapterListener EventAdapterListener;
    private List<Event> events; // Cached copy of events
    private final EventAdapterListener listener; // Listener for edit and delete actions
    private List<Event> eventList;
    private Role userRole; // The role of the user


    /**
     * Listener interface to handle click events on buttons in each list item.
     */
    public interface EventAdapterListener {
        void onEditClick(Event event); // Called when the edit button is clicked
        void onDeleteClick(Event event); // Called when the delete button is clicked

        void onJoinClick(Event event);
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


    /**
     * Creates a new ViewHolder for the Event list items. This method is called when the RecyclerView needs a new ViewHolder
     * to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View, for different types of items in the RecyclerView.
     * @return A new ViewHolder that holds a View of the event_list_item layout type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false); // Inflate the layout
        return new ViewHolder(view, listener);
    }

    /**
     * Binds the data to the ViewHolder for each item in the RecyclerView. This method is called by the RecyclerView to
     * display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position
     *                 in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the event at the current position
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

        // Show the Join button for participant users
        if (userRole != null && userRole.equals(Role.PARTICIPANT)) {
            holder.btnJoinEvent.setVisibility(View.VISIBLE);
        } else {
            holder.btnJoinEvent.setVisibility(View.GONE);
        }
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the events list, representing the number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {

        return events.size();
    }

    /**
     * ViewHolder class for layout of each list item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName, tvEventType, tvEventDifficulty; // UI elements
        public Button btnEditEvent, btnDeleteEvent, btnJoinEvent; // UI elements
        private Event currentEvent; // Holds the current event

        public ViewHolder(View itemView, final EventAdapterListener listener) {
            super(itemView);

            // Initialize UI elements
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

            btnJoinEvent = itemView.findViewById(R.id.btnJoinEvent);

            btnJoinEvent.setOnClickListener(v -> {
                if (listener != null && currentEvent != null) {
                    listener.onJoinClick(currentEvent);
                }
            });
        }

        /**
         * Sets the current Event object to the ViewHolder. This method is used to keep a reference
         * to the current Event associated with this ViewHolder, which may be used for further
         * operations like event modifications or deletions.
         *
         * @param event The Event object to be set as the current event for this ViewHolder.
         */
        public void setCurrentEvent(Event event) {
            this.currentEvent = event;
        }
    }

}
