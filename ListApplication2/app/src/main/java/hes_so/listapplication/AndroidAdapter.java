package hes_so.listapplication;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Florian.Meyer on 02.10.2016.
 */

public class AndroidAdapter extends ArrayAdapter<AndroidVersion> {

    private ArrayList<AndroidVersion> androidVersionList;
    private Context context;
    private int viewRes;
    private Resources res;

    public AndroidAdapter(Context context, int textViewRessourcedId,
                          ArrayList<AndroidVersion> versions) {
        super(context, textViewRessourcedId,versions);
        this.androidVersionList = versions;
        this.context = context;
        this.viewRes = textViewRessourcedId;
        this.res = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(viewRes,parent,false);
        }

        final AndroidVersion androidVersion = androidVersionList.get(position);

        if (androidVersion !=null){

            //Search the textview with title of the item
            final TextView text = (TextView) view.findViewById(R.id.textVersion);

            //Searche the textview with number of the item
            final TextView number = (TextView) view.findViewById(R.id.numberVersion);

            //combine standard text and dynanmic one
            final String versionName = String.format("%s %s",res.getString(R.string.list_text),androidVersion.getVersionName());

            //set the string to the textview of tittle
            text.setText(versionName);

            //combine standard text and dynanmic one
            final String versionNumber = String.format("%s %s",res.getString(R.string.list_number),androidVersion.getVersionNumber());

            //set the string to the textvire of number
            number.setText(versionNumber);
        }

        return view;
    }
}
