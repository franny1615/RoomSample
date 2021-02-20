package com.example.roomsample.DB;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomsample.DAO.FlashCardDao;
import com.example.roomsample.Entities.FlashCardEntity;

/**
 * The database class has to be abstract and extend RoomDatabase class
 *
 * IMPORTANT: in the entities part of the Database(entities) you can add
 * 'exportSchema = false' which doesn't keep track of DB history, migration stuff
 * However, if not you can define a subdirectory in the build.gradle in defaultConfig which is best practice
 *
 *
 * */

@androidx.room.Database(entities = {FlashCardEntity.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static final String DB_NAME = "flashcard_table";

    // you will populate this only once
    private static Database INSTANCE = null;

    public static Database getInstance(Context context) {
        // basically make the database if it doesn't already exist
        if(INSTANCE == null) {
            synchronized (Database.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                Database.class,
                                                DB_NAME
                        ).build();
            }
        }
        return INSTANCE;
    }

    // write these abstract methods to expose Data Access Objects
    public abstract FlashCardDao flashcardDao();
}
