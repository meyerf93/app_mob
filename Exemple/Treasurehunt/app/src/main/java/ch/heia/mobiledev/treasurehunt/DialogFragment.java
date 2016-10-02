package ch.heia.mobiledev.treasurehunt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class DialogFragment extends android.app.DialogFragment {

    private final static String TAG = DialogFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Beacon found");
        builder.setMessage("Beacon with major : "+getArguments().getInt("Major")+" minor : "+getArguments().getInt("Minor")+"\n\n"+
                           "Clue for next beacon : \n"+
                           DownloadBeaconInforFrag.contain_clue[getArguments().getInt("returnValue")]+"\n"+
                           "Number of step to find the beacon : "+getArguments().getInt("ActualStep")
        );
        builder.setPositiveButton(R.string.dialog_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG,"button clicked");
            }
        });
        return builder.create();
    }
}
