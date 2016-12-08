package hes_so.mediaplayer_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Florian.Meyer on 13.10.2016.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private OnItemClickListener mListener;

    private static final String TAG = "RecyclerListener";

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
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
        Log.v(TAG,"lsite at x : "+e.getX()+" y : "+e.getY()+" touched");
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
