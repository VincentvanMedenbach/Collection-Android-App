package autiboiz.collectiontestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class DialogPopup extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialogContents)
                .setPositiveButton(R.string.dialogButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getActivity(), MainPage.class);
                        startActivity(intent);
                    }
                });


        // Create the AlertDialog object and return it
        return builder.create();
    }

  public void show(FragmentManager fm, String string){
        super.show(fm, string );
  }
}
