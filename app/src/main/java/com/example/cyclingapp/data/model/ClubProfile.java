package com.example.cyclingapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "club_profiles")
public class ClubProfile {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "club_name")
    private String clubName;

    @ColumnInfo(name = "social_media_link")
    private String socialMediaLink;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "main_contact_name")
    private String mainContactName;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "user_id")
    private String userId;

    public ClubProfile( ){

    }

    public int getId() {
        return id;
    }

    public String getClubName() {
        return clubName;
    }

    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        this.socialMediaLink = socialMediaLink;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
    }

}

