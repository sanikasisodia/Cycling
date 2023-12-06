package com.example.cyclingapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

/**
 * Represents a cycling event entity in the application.
 * This class is a model for Room database to create, read, update,
 * and delete cycling events within the app's local database.
 */
@Entity(tableName = "events")
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = true)  // Unique ID for the database entry, auto-generated
    private int id;                   // Identifier for the event

    private String clubName;          //Name of club
    private String name;              // Name of the event
    private String type;              // Type of the event (e.g., race, casual, etc.)
    private String difficulty;        // Difficulty level of the event
    private String details;           // Details about the event
    private int participationCount;   // Number of participants in the event
    private double fee;               // Registration fee for the event

    private String displayName= LoggedInUser.getDisplayName();

    /**
     * Constructor for Event class.
     *
     * @param name              Name of the event.
     * @param clubName          Name of club.
     * @param type              Type of the event.
     * @param difficulty        Difficulty level of the event.
     * @param details           Detailed description of the event.
     * @param participationCount Number of participants in the event.
     * @param fee               Registration fee for the event.
     * @param displayName       Display name from profile
     */
    public Event(String clubName, String name, String type, String difficulty, String details, int participationCount, double fee, String displayName) {
        this.clubName = clubName;
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.details = details;
        this.participationCount = participationCount;
        this.fee = fee;
        this.displayName = displayName;
    }

    // Getters and setters
    public String getClubName(){
        return clubName;
    }
    public void setClubName(String clubName){this.clubName = clubName;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getParticipationCount() {
        return participationCount;
    }

    public void setParticipationCount(int participationCount) {
        this.participationCount = participationCount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getDisplayName() {return displayName;}

    public void setDisplayName(String displayName){this.displayName = displayName;}

}
