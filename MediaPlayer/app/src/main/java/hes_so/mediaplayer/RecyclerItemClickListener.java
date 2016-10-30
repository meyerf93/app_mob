package hes_so.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Florian.Meyer on 13.10.2016.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view,int position);
    }

    GestureDetector mGestureDetectore;

    public RecyclerItemClickListener(Context context, OnItemClickListener listenenr){
        mListener = listenenr;
        mGestureDetectore = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if(childView != null && mListener != null && mGestureDetectore.onTouchEvent(e)){
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {    }
}
