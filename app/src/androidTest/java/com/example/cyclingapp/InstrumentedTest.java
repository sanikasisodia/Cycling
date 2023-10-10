package com.example.cyclingapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.cyclingapp.data.Register;
import com.example.cyclingapp.data.Setup;
import com.example.cyclingapp.data.model.LoggedInUser;
import com.example.cyclingapp.data.model.Role;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.cyclingapp", appContext.getPackageName());
    }

    @Test
    public void testRegister() {
        Register register = new Register();
        register.register(Role.PARTICIPANT,
                "test@gmail.com",
                "Test",
                "Test",
                "test1234");
        assertEquals("Test Test", register.getDisplayName());

    }

    @Test
    public void testRegisterFail() {
        Register register = new Register();
        try {
        register.register(Role.PARTICIPANT,
                "abc@gmail.com",
                "ABC",
                "DEF",
                "a");
        } catch (Exception e) {
            assertEquals("Password must be at least 5 characters", e.getMessage());
        }
    }

    @Test
    public void testLogin() {
        new Setup();
        LoggedInUser loggedInUser = new LoggedInUser("admin", "admin");
        assertEquals("ADMIN USER", loggedInUser.getDisplayName());
    }
}