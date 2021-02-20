package com.example.roomsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.roomsample.DataSource.FlashCardDataSource;
import com.example.roomsample.Entities.FlashCardEntity;

import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;

public class MainActivity extends AppCompatActivity implements AddFlashcardDialogFragment.AddFlashcardDialogListener {

    private Flowable<List<FlashCardEntity>> flashcards;
    private FlashCardDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create our db connection and grab our cards
        datasource = new FlashCardDataSource(this);
        flashcards = datasource.getAllFlashCard();
    }

    /**
     * this method acts as a gateway to methods that correspond with a particular
     * */
    public void onClick(View view) {
        if(view.getId() == R.id.addButton) {
            askAndAdd();
        }
    }

    /**
     * create a custom dialog, ask for front text, back text
     * */
    public void askAndAdd() {
        AddFlashcardDialogFragment dialog = new AddFlashcardDialogFragment();
        dialog.show(getSupportFragmentManager(), "ADD FLASHCARD");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String front, String back) {
        // take inputs and create a flashcard, add it to database
        FlashCardEntity flashcard = new FlashCardEntity();
        flashcard.setFront(front);
        flashcard.setBack(back);
        // do concurrent thread and add the card in
        // you need threads to do actions on database
        Thread addingThread = new Thread(() -> datasource.createFlashcard(flashcard));
        addingThread.start();
        try {
            addingThread.join();
        } catch (Exception e) {
            Log.d("MAIN","Exception thrown for thread");
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}