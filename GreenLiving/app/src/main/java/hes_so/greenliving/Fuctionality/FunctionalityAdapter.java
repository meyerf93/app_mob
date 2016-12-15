package hes_so.greenliving.Fuctionality;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import hes_so.greenliving.R;

import static android.R.id.list;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class FunctionalityAdapter extends ArrayAdapter<CustomFunctionality> {

    private ArrayList<CustomFunctionality> funcList;
    private Context context;
    private int viewRes;
    private Resources res;
    private SubFunctionalityAdapter subFunctionalityAdapter;

    public FunctionalityAdapter(Context context, int textViewRessourceId,
                                ArrayList<CustomFunctionality> funcList,
                                SubFunctionalityAdapter subFunctionalityAdapter){
        super(context,textViewRessourceId,funcList);
        this.funcList = funcList;
        this.context = context;
        this.viewRes = textViewRessourceId;
        this.res = context.getResources();
        this.subFunctionalityAdapter = subFunctionalityAdapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(viewRes,parent,false);
        }
        final CustomFunctionality customFunctionality = funcList.get(position);
        if (customFunctionality != null){
            final TextView func_title = (TextView) view.findViewById(R.id.func_tittle);
            func_title.setText(customFunctionality.getFunctName());

            final ImageButton func_button = (ImageButton) view.findViewById(R.id.func_button);
            func_button.setImageBitmap(customFunctionality.getFunctPicture());

            final ListView sub_func_list = (ListView) view.findViewById((R.id.sub_func_list));
            sub_func_list.setAdapter(subFunctionalityAdapter);

        }
        return view;
    }
}

