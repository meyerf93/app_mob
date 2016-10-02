package ch.heai.mobiledev.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Button btnDisp;
    private Button btnClr;
    private EditText myEditTxt;
    private TextView myTextView;

    final private DisplayMetrics metrics = new DisplayMetrics();

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Recuperation of the display Manager
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Logging
        Log.v(TAG, "Hi from onCreate");
        Log.v(TAG,"Density of screen in dpi "+metrics.densityDpi);
        Log.v(TAG,"Density bucket in px/dp "+metrics.density);
        Log.v(TAG,"xdpi : "+metrics.xdpi+" and ydpi : "+metrics.ydpi);
        Log.v(TAG,"Screen dimension in px, y : "+metrics.heightPixels+" ,x : "+metrics.widthPixels);
        Log.v(TAG, "Screen dimension in dp, y : " + (metrics.heightPixels * 160) / metrics.densityDpi +
                " ,x : " + (metrics.widthPixels * 160) / metrics.densityDpi);

        //getResources().getConfiguration().screenWidthDp

        // Default code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the different component to our view
        btnDisp=(Button)findViewById(R.id.buttonDisp);
        myEditTxt=(EditText)findViewById(R.id.myEditText);
        myTextView=(TextView)findViewById(R.id.textView);
        btnClr=(Button)findViewById(R.id.buttonClr);

        // Initialisation of the button
        initDisplayButton();
        initClearButton();
    }

    private void initDisplayButton(){
        // Display button clicked
        btnDisp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"click on the Display Button");
                myTextView.setText("Hello " + myEditTxt.getText());
                myEditTxt.setText("");
            }
        });
    }
    private void initClearButton(){
        // Clear button clicked
        btnClr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"click on hte Clear Button");
                myTextView.setText(R.string.text_view);
                myEditTxt.setText("");
            }
        });
    }
}
