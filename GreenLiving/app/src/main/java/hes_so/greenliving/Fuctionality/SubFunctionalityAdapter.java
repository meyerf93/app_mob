package hes_so.greenliving.Fuctionality;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hes_so.greenliving.R;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class SubFunctionalityAdapter extends ArrayAdapter<CustomSubFunctionality> {

    private ArrayList<CustomSubFunctionality> subFuncList;
    private Context context;
    private int viewRes;
    private Resources res;

    public SubFunctionalityAdapter(Context context, int textViewRessourceId,
                                   ArrayList<CustomSubFunctionality> subFuncList){
        super(context,textViewRessourceId,subFuncList);
        this.subFuncList = subFuncList;
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

        final CustomSubFunctionality customSubFunctionality = subFuncList.get(position);
        if(customSubFunctionality != null){
            final ImageButton sub_func_button = (ImageButton) view.findViewById(R.id.sub_func_button);
            sub_func_button.setImageBitmap(customSubFunctionality.getSubFuncButton());

            final ImageView sub_func_image = (ImageView) view.findViewById(R.id.sub_func_image);
            sub_func_image.setImageBitmap(customSubFunctionality.getSubFunctPicture());
        }

        return view;
    }
}
