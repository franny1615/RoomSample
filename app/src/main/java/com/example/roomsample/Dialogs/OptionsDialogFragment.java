package com.example.roomsample.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import com.example.roomsample.R;

public class OptionsDialogFragment extends DialogFragment {

    private int id; // refers to position in recycler view not database

    public interface OptionsInterfaceListener {
        void deleteResponse(DialogFragment dialog, int id);
        void editResponse(DialogFragment dialog, int id);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    public OptionsDialogFragment(int id) {
        super();
        this.id = id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Options")
                .setItems(R.array.options_arr, (dialog, which) -> {
                    // The 'which' argument contains the index position of the selected item
                    switch (which) {
                        case 0:
                            listener.deleteResponse(OptionsDialogFragment.this, id);
                            break;
                        case 1:
                            listener.editResponse(OptionsDialogFragment.this, id);
                            break;
                        case 2:
                            listener.onDialogNegativeClick(OptionsDialogFragment.this);
                            break;
                        default:
                            Log.d("OPTIONS DIALOG", "not an option");
                            break;
                    }
                });
        return builder.create();
    }

    // Use this instance of the interface to deliver action events
    OptionsDialogFragment.OptionsInterfaceListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (OptionsDialogFragment.OptionsInterfaceListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Activity must implement NoticeDialogListener");
        }
    }
}
