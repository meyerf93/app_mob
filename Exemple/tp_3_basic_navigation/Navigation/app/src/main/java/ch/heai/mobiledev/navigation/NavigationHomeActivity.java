package ch.heai.mobiledev.navigation;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian.Meyer on 16.03.2016.
 */
public class NavigationHomeActivity extends ListActivity {
    private static final String TAG = NavigationHomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.v(TAG,"hi from onCreate, Meyer & Metraux, nexus 5");
        super.onCreate(savedInstanceState);
        /* Developed for step 5
        setListAdapter(new SampleAdapter());*/
        setListAdapter(new ActivityListAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG,"hi from onCreateOptionMenu, Meyer & Metraux, nexus 5");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG,"hi from onOptionsItemSelected, Meyer & Metraux, nexus 5");
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        Log.v(TAG,"hi from onStop, Meyer & Metraux, nexus 5");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.v(TAG,"hi from onPause, Meyer & Metraux, nexus 5");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.v(TAG,"hi from onResume, Meyer & Metraux, nexus 5");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG,"hi from onRestart, Meyer & Metraux, nexus 5");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.v(TAG,"hi from onStart, Meyer & Metraux, nexus 5");
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(TAG,"hi from onSaveInstanceState, Meyer & Metraux, nexus 5");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        Log.v(TAG,"hi from onRestoreIsntanceState, Meyer & Metraux, nexus 5");
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG,"hi from onDestroy, Meyer & Metraux, nexus 5");
        super.onDestroy();
    }

    /* Developed for step 7
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.v(TAG, "hi from onListItemClick, Meyer & Metraux, nexus 5");
        super.onListItemClick(l, v, position, id);
        if(v instanceof TextView){
            ((TextView)v).setTextColor(Color.BLACK);
        }

    }*/

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.v(TAG, "hi from onListItemClick, Meyer & Metraux, nexus 5");
        super.onListItemClick(l, v, position, id);

        ActivityInfo info = (ActivityInfo) getListAdapter().getItem(position);
        startActivity(info.startIntent);

    }

    protected class SampleAdapter extends BaseAdapter{
        ArrayList<String> list;

        public SampleAdapter (){
            list = new ArrayList<String>();
            list.add("bonjour");
            list.add("salut");
        }

        @Override
        public int getCount() {
            Log.v(TAG,"hi from getCount, Meyer & Metraux, nexus 5");
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG,"hi from getItem, Meyer & Metraux, nexus 5");
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.v(TAG,"hi from getItemID, Meyer & Metraux, nexus 5");
            return list.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.v(TAG,"hi from getView, Meyer & Metraux, nexus 5");
            TextView view;
            if(!(convertView instanceof TextView) || convertView == null){
                view = new TextView(getApplicationContext());
            }
            else {
                view = (TextView) convertView;
            }

            view.setText((String) getItem(position));
            view.setTextColor(Color.BLUE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.sizeof_text_navig_home));

            return view;
        }
    }

    protected class ActivityListAdapter extends  BaseAdapter{
        List<ActivityInfo> list;

        public ActivityListAdapter(){
            list = listActivities();
        }

        @Override
        public int getCount() {
            Log.v(TAG,"hi from getCount, Meyer & Metraux, nexus 5");
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            Log.v(TAG,"hi from getItem, Meyer & Metraux, nexus 5");
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.v(TAG,"hi from getItemID, Meyer & Metraux, nexus 5");
            return list.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.v(TAG,"hi from getView, Meyer & Metraux, nexus 5");
            TextView view;
            if(!(convertView instanceof TextView) || convertView == null){
                view = new TextView(getApplicationContext());
            }
            else {
                view = (TextView) convertView;
            }

            view.setText(((ActivityInfo)getItem(position)).name);
            view.setTextColor(Color.BLUE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.sizeof_text_navig_home));

            return view;
        }
    }

    protected class ActivityInfo{
        String name;
        Intent startIntent;
    }

    private ArrayList<ActivityInfo> listActivities(){
        Intent temp_intent;
        temp_intent = new Intent(Intent.ACTION_MAIN);
        temp_intent.setPackage(getPackageName());
        temp_intent.addCategory("android.intent.category.CATEGORY_LAUNCH_FROM_HOME");

        List<ResolveInfo> temp_list = getPackageManager().queryIntentActivities(temp_intent, PackageManager.MATCH_ALL);
        ArrayList<ActivityInfo> actual_list = new ArrayList<ActivityInfo>();

        for(ResolveInfo item : temp_list){
            Log.v(TAG,"h from loop of list");
            String label =(String) item.activityInfo.loadLabel(getPackageManager());
            ComponentName component = new ComponentName(item.activityInfo.packageName,item.activityInfo.name);
            Intent i = new Intent();
            i.setComponent(component);
            ActivityInfo temp_info = new ActivityInfo();
            temp_info.name =label;
            temp_info.startIntent = i;
            actual_list.add(temp_info);
        }

        return actual_list;
    }
}
