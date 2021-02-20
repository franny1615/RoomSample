package com.example.roomsample.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class is what Room interprets as the item that will go into the database
 * Room automatically converts fields into columns, and the entity is a single row
 * use @ColumnInfo() to change how Room will interpret the field
 * use @Ignore to tell Room that you don't want that field to go into database
 * */

@Entity(tableName = "flashcard_table")
public class FlashCardEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    // the flashcard in this sample will only take
    // a front string and back string
    private String front;
    private String back;

    // START FRONT methods
    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }
    // END FRONT methods

    // START BACK methods
    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }
    // END BACK methods

    // START ID methods
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    // END ID
}
