package com.example.cyclingapp.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import com.example.cyclingapp.data.model.Event;
import com.example.cyclingapp.data.model.EventDao;

/**
 * ViewModel for managing UI-related data for events in a lifecycle-conscious way.
 */
public class EventViewModel extends ViewModel {

    private EventDao eventDao;
    private LiveData<List<Event>> events;

    // Constructor or initialization method
    public EventViewModel(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * Fetches events created by a specific user.
     *
     * @param userId The ID of the user whose events to fetch.
     * @return LiveData holding the list of events.
     */
    public LiveData<List<Event>> getEventsByUserId(String userId) {
        if (events == null) {
            events = (LiveData<List<Event>>) eventDao.getEventsByUserId(userId);
        }
        return events;
    }
}
