package com.example.roomsample.DataSource;

import android.content.Context;

import com.example.roomsample.DAO.FlashCardDao;
import com.example.roomsample.DB.Database;
import com.example.roomsample.Entities.FlashCardEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * This class is basically a bridge between your DB and your activity
 * it starts the data access objects, and defines an insert, update, delete,
 * and other interactions you want between DB and activity
 *
 * This can be for each entity, or it can be for entire DB. In other words you
 * can put multiple DAO interactions here, it is just an abstraction layer
 * */

public class FlashCardDataSource {
    private final FlashCardDao flashcardDao;

    public FlashCardDataSource(Context context) {
        // initialize DB
        Database database = Database.getInstance(context);
        // initialize each DAO
        flashcardDao = database.flashcardDao();
    }

    public void createFlashcard(FlashCardEntity flashcard) {
        // insert an entry
        long rowId = flashcardDao.insertAFlashcard(flashcard);
        // if your card is associated with other rows in another table
        // and you have a column in that other table to store the associated id
        // you can loop through those rows here and add the rowId in.
    }

    public void updateFlashcard(FlashCardEntity flashcard) {
        flashcardDao.updateAFlashcard(flashcard);
        // much like the create method, you can do other manipulations here that have to do with updating
    }

    public void deleteFlashcard(FlashCardEntity flashcard) {
        flashcardDao.deleteAFlashcard(flashcard);
        // same deal, if you have more to do, do it.
    }

    public Flowable<List<FlashCardEntity>> getAllFlashCard() {
        return flashcardDao.loadAllFlashCards();
    }
}
