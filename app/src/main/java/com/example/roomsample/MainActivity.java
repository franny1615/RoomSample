package com.example.roomsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomsample.DataSource.FlashCardDataSource;
import com.example.roomsample.Entities.FlashCardEntity;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;

public class MainActivity extends AppCompatActivity implements AddFlashcardDialogFragment.AddFlashcardDialogListener, FlashCardAdapter.ItemClickListener {

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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + fcAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
        Thread addingThread = new Thread(() -> {
            datasource.createFlashcard(flashcard); // this one adds to database
            flashcards.add(flashcard); // this one adds to running version
        });
        addingThread.start();
        try {
            addingThread.join();
        } catch (Exception e) {
            Log.d("MAIN","Exception thrown for thread");
        }
        fcAdapter.notifyDataSetChanged(); // this updates the recycler view
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}