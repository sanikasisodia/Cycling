package com.example.cyclingapp;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.cyclingapp.data.Setup;
import com.example.cyclingapp.data.model.LoggedInUser;
import com.example.cyclingapp.ui.event.EventList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testValidPhoneNumber() {
        EventList eventList = new EventList();

        assertTrue(eventList.isValidPhoneNumber("1234567890")); // valid number
        assertFalse(eventList.isValidPhoneNumber("123")); // invalid number
        assertFalse(eventList.isValidPhoneNumber("abcdefghij")); // invalid format
    }

}