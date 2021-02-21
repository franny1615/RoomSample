package com.example.roomsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

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

import com.example.roomsample.DataSource.FlashCardDataSource;
import com.example.roomsample.Entities.FlashCardEntity;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;

public class MainActivity extends AppCompatActivity implements AddFlashcardDialogFragment.AddFlashcardDialogListener {

    private Flowable<List<FlashCardEntity>> flashcards;
    private FlashCardDataSource datasource;
    private LinearLayout flashcardLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create our db connection and grab our cards
        datasource = new FlashCardDataSource(this);
        flashcards = datasource.getAllFlashCard();

        // will place flashcards in this layout
        flashcardLinearLayout = findViewById(R.id.flashcardLinearLayout);
        displayFlashcards(0,null);
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
        displayFlashcards(1,flashcard);
        flashcardLinearLayout.invalidate();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }


    /**
     * this method creates a cardview for every flashcard
     * and adds it to the linearlayout
     * */
    public void displayFlashcards(int type, FlashCardEntity f) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = 10;
        params.bottomMargin = 10;
        params.leftMargin = 10;
        params.rightMargin = 10;
        switch(type) {
            case 0:
                allcards(params);
                break;
            case 1:
                onecard(params, f);
                break;
            default:
                Log.d("DISPLAY CARD:", "not valid type");
                break;
        }

    }

    public void allcards(LinearLayout.LayoutParams params) {
        for(FlashCardEntity flashcard: flashcards.blockingFirst()) {
            CardView fc = new CardView(this);
            fc.setLayoutParams(params);
            fc.setCardElevation(5f);
            fc.setRadius(5f);
            //
            LinearLayout ln = new LinearLayout(this);
            ln.setOrientation(LinearLayout.VERTICAL);
            //
            TextView front = new TextView(this);
            front.setText("Front: " + flashcard.getFront());
            TextView back = new TextView(this);
            back.setText("Back: " + flashcard.getBack());
            //
            ln.addView(front);
            ln.addView(back);
            fc.addView(ln);
            //
            flashcardLinearLayout.addView(fc);
        }
    }

    public void onecard(LinearLayout.LayoutParams params, FlashCardEntity flashcard) {
        CardView fc = new CardView(this);
        fc.setLayoutParams(params);
        fc.setCardElevation(5f);
        fc.setRadius(5f);
        //
        LinearLayout ln = new LinearLayout(this);
        ln.setOrientation(LinearLayout.VERTICAL);
        //
        TextView front = new TextView(this);
        front.setText("Front: " + flashcard.getFront());
        TextView back = new TextView(this);
        back.setText("Back: " + flashcard.getBack());
        //
        ln.addView(front);
        ln.addView(back);
        fc.addView(ln);
        //
        flashcardLinearLayout.addView(fc);
    }
}