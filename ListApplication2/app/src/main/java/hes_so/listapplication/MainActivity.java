package hes_so.listapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> itemlistArray = new ArrayList<String>();
    private ArrayAdapter simpleAdapter;
    private ArrayList<AndroidVersion> androidList = new ArrayList<AndroidVersion>();
    private AndroidAdapter adapter;
    private EditText newItemTextField;
    private Boolean isNew = true;
    private int positions;
    private ListView simpleListeView;

    private String[] mMenuSections;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creation of the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        //creation of the drawer
        mMenuSections = getResources().getStringArray(R.array.menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuSections));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.app_name,
                R.string.app_name
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d("HomeActivity", "onDrawerClosed");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d("HomeActivity", "onDrawerOnped");
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //Init the simple list
        itemlistArray.add("Item1");
        for (int i = 2; i < 3; i++) {
            itemlistArray.add("Item" + i);
        }

        //Init the custom list
        initList(androidList);

        //creation of adapter for custom list
        simpleAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemlistArray);
        //creation of ListView for custom list
        simpleListeView = (ListView) findViewById(R.id.simpleListView);
        //set the custom list to is adapter
        simpleListeView.setAdapter(simpleAdapter);

        //creation of adapter for custom list
        adapter = new AndroidAdapter(this, R.layout.layout_android_version, androidList);

        //creation of ListView for custom list
        final ListView list = (ListView) findViewById(R.id.customListView);
        //set the custom list to is adapter
        list.setAdapter(adapter);

        simpleListeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positions = position;
                manageItem((String) parent.getItemAtPosition(position));
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                AndroidVersion selectedItem = (AndroidVersion) adapter.getItemAtPosition(position);
                Log.v("CustomAdapterExemple", "Element selectionne : " + selectedItem.getVersionName());
            }
        });
    }

    private void manageItem(final String item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_new_item_dialog, null);

        //set other diaglog properties
        builder.setTitle("Modify item").setView(dialogView);

        newItemTextField = (EditText) dialogView.findViewById(R.id.newItemEditText);

        if (!item.equals("")) {
            newItemTextField.setText(item);
            isNew = false;
        } else {
            isNew = true;
        }

        // Add the buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isNew) {
                    itemlistArray.add(String.valueOf(newItemTextField.getText()));
                } else {
                    itemlistArray.set(positions, newItemTextField.getText().toString());
                }
                simpleAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_new_item_dialog, null);

        //set other diaglog properties
        builder.setTitle("add item").setView(dialogView);

        newItemTextField = (EditText) dialogView.findViewById(R.id.newItemEditText);

        // Add the buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemlistArray.add(String.valueOf(newItemTextField.getText()));
                simpleAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initList(ArrayList<AndroidVersion> androidList) {
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

        switch (id) {
            case R.id.action_add:
                isNew = true;
                addItem();
                return true;
            case R.id.action_modify:
                isNew = false;
                String listItem = (String) simpleListeView.getItemAtPosition(positions);
                if (listItem.equals("")) {
                    Toast.makeText(this, "No item selected", Toast.LENGTH_LONG).show();
                } else {
                    manageItem((String) simpleListeView.getItemAtPosition(positions));
                }
                return true;
            case R.id.action_search:
                Toast.makeText(this, "It's hard to search item", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(),
                            "Must open an activity", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(),
                            "Do nothing", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

