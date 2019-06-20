package ru.club.sfera.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import ru.club.sfera.R;
import ru.club.sfera.constants.Constants;

public class MyGroupPostActionDialog extends DialogFragment implements Constants {

    private int position;

    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListener alertPositiveListener;

    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    public interface AlertPositiveListener {

        public void onPostRemove(int position);
        public void onPostShare(int position);
        public void onPostCopyLink(int position);
    }

    /** This is a callback method executed when this fragment is attached to an activity.
     *  This function ensures that, the hosting activity implements the interface AlertPositiveListener
     * */
    public void onAttach(android.app.Activity activity) {

        super.onAttach(activity);

        try {

            alertPositiveListener = (AlertPositiveListener) activity;

        } catch(ClassCastException e){

            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }

    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        position = bundle.getInt("position");

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        arrayAdapter.add(getString(R.string.action_remove));
        arrayAdapter.add(getString(R.string.action_share));
        arrayAdapter.add(getString(R.string.action_copy_url));

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0: {

                        alertPositiveListener.onPostRemove(position);

                        break;
                    }

                    case 1: {

                        alertPositiveListener.onPostShare(position);

                        break;
                    }

                    default: {

                        alertPositiveListener.onPostCopyLink(position);

                        break;
                    }
                }

            }
        });

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = builderSingle.create();

        /** Return the alert dialog window */
        return d;
    }
}