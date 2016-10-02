package hes_so.listapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> itemlistArray = new ArrayList<String>();
    private ArrayAdapter simpleAdapter;
    private ArrayList<AndroidVersion> androidList = new ArrayList<AndroidVersion>();
    private AndroidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Init the simple list
        itemlistArray.add("Item1");
        for(int i=2;i<3;i++){
            itemlistArray.add("Item"+i);
        }

        //Init the custom list
        initList(androidList);

        adapter = new AndroidAdapter(this, R.layout.layout_android_version, androidList);

        final ListView list = (ListView) findViewById(R.id.customListView);

        list.setAdapter(adapter);

        simpleAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemlistArray);

        ListView simpleListeView = (ListView) findViewById(R.id.simpleListView);

        simpleListeView.setAdapter(simpleAdapter);

        /*simpleListeView.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("simpleadapter","Element selectionne : "+ adapter.getItem))
                manageItem((String) parent.getItemAtPosition());
            }
        });*/

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id){
                AndroidVersion selectedItem = (AndroidVersion) adapter.getItemAtPosition(position);
                Log.v("CustomAdapterExemple", "Element selectionne : " + selectedItem.getVersionName());
            }
        });


    }

    private void initList(ArrayList<AndroidVersion> androidList){
        AndroidVersion version = new AndroidVersion();
        version.setVersioName("Cupcake");
        version.setVersionNumber("1.5");
        androidList.add(version);
        AndroidVersion version_2 = new AndroidVersion();
        version_2.setVersioName("Marshmallow");
        version_2.setVersionNumber("6.0");
        androidList.add(version_2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
