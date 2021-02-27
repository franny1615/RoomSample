package com.example.roomsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.roomsample.DataSource.FlashCardDataSource;
import com.example.roomsample.Dialogs.AddFlashcardDialogFragment;
import com.example.roomsample.Dialogs.EditFlashCardDialogFragment;
import com.example.roomsample.Dialogs.OptionsDialogFragment;
import com.example.roomsample.Entities.FlashCardEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddFlashcardDialogFragment.AddFlashcardDialogListener,
        FlashCardAdapter.ItemClickListener,
        OptionsDialogFragment.OptionsInterfaceListener,
        EditFlashCardDialogFragment.EditFlashcardListener {

    private List<FlashCardEntity> flashcards;
    private FlashCardDataSource datasource;
    private RecyclerView recyclerViewLayout;
    private FlashCardAdapter fcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create our db connection and grab our cards
        datasource = new FlashCardDataSource(this);
        flashcards = datasource.getAllFlashCard().blockingFirst();

        // stuff for recyclerview
        recyclerViewLayout = findViewById(R.id.recyclerViewFC);
        recyclerViewLayout.setLayoutManager(new LinearLayoutManager(this));
        fcAdapter = new FlashCardAdapter(this, flashcards);
        fcAdapter.setClickListener(this);
        recyclerViewLayout.setAdapter(fcAdapter);
    }

    /**
     * this is a recyclerview method that listens for an item being clicked in the list
     * */
    @Override
    public void onItemClick(View view, int position) {
        OptionsDialogFragment options = new OptionsDialogFragment(position);
        options.show(getSupportFragmentManager(),"OPTIONS FOR FLASHCARD");
    }

    /**
     * this method acts as a gateway to methods that correspond with a particular button
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

    /**
     * this method corresponds to adding a flashcard dialog
     * */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String front, String back) {
        // take inputs and create a flashcard, add it to database
        FlashCardEntity flashcard = new FlashCardEntity();
        flashcard.setFront(front);
        flashcard.setBack(back);
        // do concurrent thread and add the card in
        // you need threads to do actions on database
        Thread addingThread = new Thread(() -> {
            datasource.createFlashcard(flashcard); // this one adds to database
            flashcards.add(flashcard); // this one adds to running version
        });
        addingThread.start();
        try {
            addingThread.join();
        } catch (Exception e) {
            Log.d("MAIN","Exception thrown for thread add");
        }
        fcAdapter.notifyDataSetChanged(); // this updates the recycler view
    }

    @Override
    public void deleteResponse(DialogFragment dialog, int id) {
        // so find a way to connect the two to make deletion easier, same thing with editing
        FlashCardEntity fc = flashcards.remove(id);
        Thread removingThread = new Thread(() -> datasource.deleteFlashcard(fc));
        removingThread.start();
        try {
            removingThread.join();
        } catch (Exception e) {
            Log.d("MAIN", "Exception thrown for thread in delete");
        }
        fcAdapter.notifyDataSetChanged(); // update recycler view
    }

    @Override
    public void editResponse(DialogFragment dialog, int id) {
        EditFlashCardDialogFragment editDialog = new EditFlashCardDialogFragment(id);
        editDialog.show(getSupportFragmentManager(),"Edit Dialog");
    }

    @Override
    public void onDialogSubmitEdit(DialogFragment dialog, String front, String back, int id) {
        flashcards.get(id).setFront(front);
        flashcards.get(id).setBack(back);
        Thread updateThread = new Thread(()-> datasource.updateFlashcard(flashcards.get(id)));
        updateThread.start();
        try {
            updateThread.join();
        } catch (Exception e) {
            Log.d("MAIN", "Exception thrown for thread in delete");
        }
        fcAdapter.notifyDataSetChanged(); // update recycler view
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}