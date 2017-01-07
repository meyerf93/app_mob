package hes_so.greenliving.UI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hes_so.greenliving.R;

/**
 * Created by Florian_meyer on 07.01.2017.
 */

public class UIAdapter extends ArrayAdapter<CustomUI> {

    private ArrayList<CustomUI> customUIList;
    private Context context;
    private int viewRes;
    private Resources res;

    public UIAdapter (Context context, int textViewRessourceId,
                          ArrayList<CustomUI> customUIList){
        super(context,textViewRessourceId,customUIList);
        this.customUIList = customUIList;
        this.context = context;
        this.viewRes = textViewRessourceId;
        this.res = context.getResources();
    }


    @NonNull
    @Override
    public View getView(int position, View convertView,@NonNull ViewGroup parent) {
        Log.v("UI_adapter","on ui adapter called");


        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(viewRes,parent,false);
        }

        final CustomUI customUI = customUIList.get(position);
        if(customUI != null){
            final LinearLayout background = (LinearLayout) view.findViewById(R.id.ui_background);
            background.setBackground(new BitmapDrawable(res,customUI.getUiBackground()));

            // Left isolation -------------------------------------------------------------------------------------------------
            final ImageButton leftThirdIsolation = (ImageButton) view.findViewById(R.id.left_third_isolation);
            if(customUI.getLeftThirdIsolation() != null) leftThirdIsolation.setImageBitmap(customUI.getLeftThirdIsolation());
            if(!customUI.isLeftThirdIsolationVisible()) leftThirdIsolation.setVisibility(View.INVISIBLE);

            final ImageButton leftSecondIsolation = (ImageButton) view.findViewById(R.id.left_second_isolation);
            if(customUI.getLeftSecondIsolation() != null) leftSecondIsolation.setImageBitmap(customUI.getLeftSecondIsolation());
            if(!customUI.isLeftSecondIsolationVisible()) leftSecondIsolation.setVisibility(View.INVISIBLE);

            final ImageButton leftFirstIsolation = (ImageButton) view.findViewById(R.id.left_first_isolation);
            if(customUI.getLeftFirstIsolation() != null) leftFirstIsolation.setImageBitmap(customUI.getLeftFirstIsolation());
            if(!customUI.isLeftFirstIsolationVisible()) leftFirstIsolation.setVisibility(View.INVISIBLE);

            //--------------------------------------------------------------------------------------------------------------------

            // Window ------------------------------------------------------------------------------------------------------------
            final ImageButton leftWindow = (ImageButton) view.findViewById(R.id.left_window);
            if(customUI.getLeftWindow() != null) {
                Log.v("COUCOU","LEFT WINWDOW NOT NULL");
                leftWindow.setImageBitmap(customUI.getLeftWindow());
            }
            if(!customUI.isLeftWindowVisible()) leftWindow.setVisibility(View.INVISIBLE);

            final ImageButton middleWindow = (ImageButton) view.findViewById(R.id.midle_window);
            if(customUI.getMiddleWindow() != null) middleWindow.setImageBitmap(customUI.getMiddleWindow());
            if(!customUI.isMiddleWindowVisible()) middleWindow.setVisibility(View.INVISIBLE);

            final ImageButton rightWindow = (ImageButton) view.findViewById(R.id.right_window);
            if(customUI.getRightWindow() != null) rightWindow.setImageBitmap(customUI.getRightWindow());
            if(!customUI.isRightWindowVisible()) rightWindow.setVisibility(View.INVISIBLE);
            //--------------------------------------------------------------------------------------------------------------------

            // Right isonaltion --------------------------------------------------------------------------------------------------
            final ImageButton rightFirstIsolation = (ImageButton) view.findViewById(R.id.right_first_isolation);
            if(customUI.getRightFirstIsolation() != null) rightFirstIsolation.setImageBitmap(customUI.getRightFirstIsolation());
            if(!customUI.isRightFirstIsolationVisible()) rightFirstIsolation.setVisibility(View.INVISIBLE);

            final ImageButton rightSecondIsolation = (ImageButton) view.findViewById(R.id.right_second_isolation);
            if(customUI.getRightSecondIsolation() != null) rightSecondIsolation.setImageBitmap(customUI.getRightSecondIsolation());
            if(!customUI.isRightSecondIsolationVisible()) rightSecondIsolation.setVisibility(View.INVISIBLE);

            final ImageButton rightThirdIsolation = (ImageButton) view.findViewById(R.id.right_third_isolation);
            if(customUI.getRightThirdIsolation() != null) rightThirdIsolation.setImageBitmap(customUI.getRightThirdIsolation());
            if(!customUI.isRightThirdIsolationVisible()) rightThirdIsolation.setVisibility(View.INVISIBLE);
            //--------------------------------------------------------------------------------------------------------------------
        }

        return view;
    }
}
