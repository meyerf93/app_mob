package hes_so.greenliving.Resume;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hes_so.greenliving.Fuctionality.CustomSubFunctionality;
import hes_so.greenliving.R;

/**
 * Created by Florian.Meyer on 03.01.2017.
 */

public class ResumeAdapter extends ArrayAdapter<CustomResume> {

    private ArrayList<CustomResume> customResumesList;
    private Context context;
    private int viewRes;
    private Resources res;

    private String LOG = "resume_adapter";

    public ResumeAdapter (Context context, int textViewRessourceId,
                          ArrayList<CustomResume> customResumesList){
        super(context,textViewRessourceId,customResumesList);
        this.customResumesList = customResumesList;
        this.context = context;
        this.viewRes = textViewRessourceId;
        this.res = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(viewRes,parent,false);
        }

        final CustomResume customResume = customResumesList.get(position);
        if(customResume != null){
            final ImageView image = (ImageView) view.findViewById(R.id.image_resume);
            if(image ==null) Log.v(LOG,"IMAGE est null");
            image.setImageBitmap(customResume.getImageResume());

            final TextView numbers = (TextView) view.findViewById(R.id.numbers_resume);
            numbers.setText(getContext().getString(R.string.numbers_resume_text)+customResume.getNumberResume());

            final TextView orientation_north = (TextView) view.findViewById(R.id.orientation_north_resume);
            orientation_north.setText(getContext().getString(R.string.orientation_resume_north)+customResume.getNorthResume());

            final TextView orientation_south = (TextView) view.findViewById(R.id.orientation_south_resume);
            orientation_south.setText(getContext().getString(R.string.orientation_resume_south)+customResume.getSouthResume());

            final TextView orientation_west = (TextView) view.findViewById(R.id.orientation_west_resume);
            orientation_west.setText(getContext().getString(R.string.orientation_resume_west)+customResume.getWestResume());

            final TextView orientation_east = (TextView) view.findViewById(R.id.orientation_east_resum);
            orientation_east.setText(getContext().getString(R.string.orientation_reusme_east)+customResume.getEastResume());

            final TextView description = (TextView) view.findViewById(R.id.description_resume);
            description.setText(getContext().getString(R.string.description_resume)+customResume.getDescriptionResume());
        }

        return view;
    }
}
