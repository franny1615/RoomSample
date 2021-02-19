package com.example.roomsample.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class is what Room interprets as the item that will go into the database
 * Room automatically converts fields into columns, and the entity is a single row
 * use @ColumnInfo() to change how Room will interpret the field
 * use @Ignore to tell Room that you don't want that field to go into database
 * */

@Entity
public class FlashCardEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    // the flashcard in this sample will only take
    // a front string and back string
    private String front;
    private String back;



}
