package ch.heai.mobiledev.navigation;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Florian.Meyer on 19.03.2016.
 */
public class PeerActivity extends Activity {

    private static final String EXTRA_PEER_COUNT = "ch.heai.mobiledev.navigation.EXTRA_PEER_COUNT";
    private int lCountPeer; //number of activity laucnhed

    private static final String TAG= PeerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"hi from onCreate, Meyer & Metraux, nexus 5");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        lCountPeer = getIntent().getIntExtra(EXTRA_PEER_COUNT,0)+1;
        TextView textView = (TextView) findViewById(R.id.nbPeer);
        String text = getResources().getText(R.string.nbpeer).toString()+" "+lCountPeer;
        Button btPeer = (Button) findViewById(R.id.btPeer);
        btPeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchPeer(v);
            }
        });
        textView.setText(text);
    }

    protected void onLaunchPeer(View v){
        Log.v(TAG,"hi from onLaunchPeer, Meyer & Metraux, nexus 5");
        Intent start = new Intent(getApplicationContext(),getClass());
        start.putExtra(EXTRA_PEER_COUNT,lCountPeer);
        startActivity(start);
    }

    @Override
    protected void onStart() {
        Log.v(TAG,"hi from onStart, Meyer & Metraux, nexus 5");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG,"hi from onRestart, Meyer & Metraux, nexus 5");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG,"hi from onResume, Meyer & Metraux, nexus 5");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG,"hi from onPause, Meyer & Metraux, nexus 5");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(TAG,"hi from onStop, Meyer & Metraux, nexus 5");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG,"hi from onDestroy, Meyer & Metraux, nexus 5");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.v(TAG,"hi from onSaveInstaneState, Meyer & Metraux, nexus 5");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG,"hi from onRestoreIstanceState, Meyer & Metraux, nexus 5");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG,"hi from onCreateOptionsMenu, Meyer & Metraux, nexus 5");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG,"hi from onOptionsiItemSelected, Meyer & Metraux, nexus 5");

        switch (item.getItemId()){
            case android.R.id.home :
                Intent intent = getParentActivityIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
