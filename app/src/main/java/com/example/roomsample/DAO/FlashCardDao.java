package com.example.roomsample.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomsample.Entities.FlashCardEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * the data access object in a Room database
 * define one or more methods for interacting with the data in
 * the apps database.
 *
 * Insert(onConflict = OnConflictsStrategy.STRAT) can be used without conflict
 * the parameter for an insert method has to be an Entity annotated class
 *
 * Update method will match the primary key and update that row for you.
 * can return void or int, int will be number of rows affected in database.
 *
 * Delete method will match primary key and delete that row from database
 * can return void or int, int will be number of rows affected in database
 *
 * Query() method allows you to pass a SQLite statement and you can save it to return field
 * you can select specific columns, and save that as a class, examine docs for that info
 * similarly you can pass a parameter to the SQLite statement using ':varName' in SQLite statement, and that var is parameter of
 * method you wrote for the query
 *
 * */

@Dao
public interface FlashCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAFlashcard(FlashCardEntity flashcard);

    @Update
    int updateAFlashcard(FlashCardEntity flashcard);

    @Delete
    int deleteAFlashcard(FlashCardEntity flashcard);

    @Query("SELECT * FROM flashcard_table")
    Flowable<List<FlashCardEntity>> loadAllFlashCards();
}
