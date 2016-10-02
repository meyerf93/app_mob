package ch.heia.mobiledev.treasurehunt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


@SuppressWarnings("ALL")
public class MenuScreenActivity extends Activity {
    private static final String TAG = MenuScreenActivity.class.getSimpleName();

    public static final String LOAD_MAIN_SCREEN = "ch.heia.mobiledev.treasurehunt.MenuScreenActiviy.action.LOAD";
    public static final String NEW_MAIN_SREEN = "ch.heia.mobiledev.treasurehunt.MenuScreenActiviy.action.NEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);
    }

    protected void onStart() {
        Log.v(TAG, "Hi from onStart MenuScreen activity");
        super.onStart();
    }


    @Override
    protected void onResume() {
        Log.v(TAG, "Hi from onResume MenuScreen activity");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "Hi from onPause MenuScreen activity");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "Hi from onDestroy MenuScreen activity");
        BeaconDetailsFragment.cleanVar();
        ClueDetailsFragment.cleanVar();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "Hi from onRestart MenuScreen activity");
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.v(TAG, "Hi from onSaveInst MenuScreen activity");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, "Hi from onRestoreInst MenuScreen activity");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public void onContinueClicked(View view) {
        Intent contScreenIntent = new Intent(MenuScreenActivity.this, MainScreenActivity.class);
        contScreenIntent.putExtra("Launching_mode",LOAD_MAIN_SCREEN);
        startActivity(contScreenIntent);
    }

    public void onNewClicked(View view){
        Intent newScreenIntent = new Intent(MenuScreenActivity.this, MainScreenActivity.class);
        newScreenIntent.putExtra("Launching_mode",NEW_MAIN_SREEN);
        startActivity(newScreenIntent);
    }

    public void onHelpClicked(View view){
        Intent helpScreenIntent = new Intent(MenuScreenActivity.this, HelpScreenActivity.class);
        startActivity(helpScreenIntent);
    }

}