package hes_so.greenliving.Fuctionality;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hes_so.greenliving.GreenLiving;
import hes_so.greenliving.LeftListFragment;
import hes_so.greenliving.R;


/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class FunctionalityAdapter extends BaseExpandableListAdapter {// ArrayAdapter<CustomFunctionality> {

    private ArrayList<CustomFunctionality> funcList;
    private Context context;
    private int viewRes;

    private String LOG = "FunAdapter";

    public static final int MSG_ADD = 1;
    public static final int MSG_REMOVE = 2;

    public interface OnLeftAdapterIntecationListener{
        void OnLeftAdapterInteraction(int command, long listItemId, long listSubItemId);
    }

    public FunctionalityAdapter(Context context, int textViewRessourceId,
                                ArrayList<CustomFunctionality> funcList){
        this.funcList = funcList;
        this.context = context;
        this.viewRes = textViewRessourceId;
        Resources res = context.getResources();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
                LayoutInflater layoutInflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(viewRes,parent,false);

        }

        TextView func_title = (TextView) view.findViewById(R.id.func_tittle);
        ImageView func_image = (ImageView) view.findViewById(R.id.func_button);

        CustomFunctionality func = funcList.get(groupPosition);

        func_title.setText(func.getFunctName());
        func_image.setImageBitmap(func.getFunctPicture());


        return view;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view ==null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_sub_func_cell,parent,false);
        }

        ImageView sub_func_button = (ImageButton) view.findViewById(R.id.sub_func_button);
        ImageView sub_func_image = (ImageView) view.findViewById(R.id.sub_func_image);

        final CustomFunctionality Item = funcList.get(groupPosition);
        final CustomSubFunctionality SubItem = Item.getSubFunctList().get(childPosition);

        sub_func_button.setImageBitmap(SubItem.getSubFuncButton());
        sub_func_image.setImageBitmap(SubItem.getSubFunctPicture());

        //add listener to ImageButton---------------------------------------------------------------
        sub_func_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                GreenLiving mainActivity = (GreenLiving) context;
                mainActivity.OnLeftAdapterInteraction(FunctionalityAdapter.MSG_ADD, Item.getId(),
                        SubItem.getId());
            }
        });

        //------------------------------------------------------------------------------------------

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
        return funcList.get(groupPosition).getSubFunctList().get(childPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return funcList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        CustomFunctionality item = funcList.get(groupPosition);
        if(item == null) return  0;
        else return item.getId();
    }
}

