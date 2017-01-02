package hes_so.greenliving.Fuctionality;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hes_so.greenliving.R;


/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class FunctionalityAdapter extends BaseExpandableListAdapter {// ArrayAdapter<CustomFunctionality> {

    private ArrayList<CustomFunctionality> funcList;
    private Context context;
    private int viewRes;
    private Resources res;

    private String LOG = "FunAdapter";


    public FunctionalityAdapter(Context context, int textViewRessourceId,
                                ArrayList<CustomFunctionality> funcList){
        this.funcList = funcList;
        this.context = context;
        this.viewRes = textViewRessourceId;
        this.res = context.getResources();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            if(view == null){
                LayoutInflater layoutInflater = (LayoutInflater)
                        context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.custom_func_cell,parent,false);
            }
        }

        TextView func_title = (TextView) view.findViewById(R.id.func_tittle);
        ImageView func_image = (ImageView) view.findViewById(R.id.func_button);

        CustomFunctionality func = funcList.get(groupPosition);

        func_title.setText(func.getFunctName());
        func_image.setImageBitmap(func.getFunctPicture());


        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view ==null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_sub_func_cell,parent,false);
        }

        ImageView sub_func_button = (ImageButton) view.findViewById(R.id.sub_func_button);
        ImageView sub_func_image = (ImageView) view.findViewById(R.id.sub_func_image);

        CustomSubFunctionality item = funcList.get(groupPosition)
                .getSubFunctList().get(childPosition);

        sub_func_button.setImageBitmap(item.getSubFuncButton());
        sub_func_image.setImageBitmap(item.getSubFunctPicture());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        if(funcList == null) return 0;
        else return funcList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(funcList.get(groupPosition).getSubFunctList() == null){
            return 0;
        }
        else    return funcList.get(groupPosition).getSubFunctList().size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        CustomSubFunctionality item = funcList.get(groupPosition).getSubFunctList().get(childPosition);
        if(item == null) return 0;
        else return item.getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        CustomSubFunctionality item = funcList.get(groupPosition)
                .getSubFunctList().get(childPosition);
        return item;
    }

    @Override
    public Object getGroup(int groupPosition) {
        CustomFunctionality item = funcList.get(groupPosition);
        return item;
    }

    @Override
    public long getGroupId(int groupPosition) {
        CustomFunctionality item = funcList.get(groupPosition);
        if(item == null) return  0;
        else return item.getId();
    }
}

