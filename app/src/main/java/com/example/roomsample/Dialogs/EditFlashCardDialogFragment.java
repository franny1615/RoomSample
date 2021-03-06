package com.example.roomsample.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.roomsample.R;

public class EditFlashCardDialogFragment extends DialogFragment {
    // to pass where on recycler view card is.
    private int loc;

    public EditFlashCardDialogFragment(int id) {
        super();
        this.loc = id;
    }

    // implement it in main activity to actually add to database
    public interface EditFlashcardListener {
        void onDialogSubmitEdit(DialogFragment dialog, String front, String back, int id);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // set up view and get things you have to save
        View mView = inflater.inflate(R.layout.add_flashcard_dialog,null);
        final EditText front = mView.findViewById(R.id.frontOfCard);
        final EditText back = mView.findViewById(R.id.backOfCard);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                .setPositiveButton(R.string.dialog_positive, (dialog, id) -> {
                    // Send the positive button event back to the host activity
                    listener.onDialogSubmitEdit(EditFlashCardDialogFragment.this, front.getText().toString(), back.getText().toString(), loc);
                })
                .setNegativeButton(R.string.dialog_negative, (dialog, id) -> {
                    // Send the negative button event back to the host activity
                    listener.onDialogNegativeClick(EditFlashCardDialogFragment.this);
                });
        return builder.create();
    }

    // Use this instance of the interface to deliver action events
    EditFlashCardDialogFragment.EditFlashcardListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (EditFlashCardDialogFragment.EditFlashcardListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Activity must implement NoticeDialogListener");
        }
    }

}
