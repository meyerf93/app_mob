package ch.heia.mobiledev.uribeacon;

import android.bluetooth.le.ScanRecord;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class UriBeacon {

    private final static String TAG = UriBeacon.class.getSimpleName();

    private byte[] mFlags;
    private byte[] mPow;
    private String mURL;
    private ImageView mImage;

    private UriBeacon(byte[] mFlags,byte[] mPow,String mURL){
        this.mFlags = mFlags;
        this.mPow = mPow;
        this.mURL = mURL;
        mImage =null;
    }

    public byte[] getPow(){
        return mPow;
    }
    public byte[] getFlags(){
        return mFlags;
    }
    public String getURL(){
        return mURL;
    }
    public ImageView getImage(){ return mImage;}

    static UriBeacon createFromScanResult(ScanRecord result){
        UriBeacon mBeacon;

        byte payload[] = result.getBytes();
        byte temp_flag[]= Arrays.copyOfRange(payload,8,9);
        byte temp_power[] = Arrays.copyOfRange(payload,9,10);
        byte temp_scheme[] = Arrays.copyOfRange(payload,10,11);
        byte temp_encoded[] = Arrays.copyOfRange(payload,11,27);

        String temp = new String(temp_scheme, StandardCharsets.US_ASCII);
        temp += new String(temp_encoded,StandardCharsets.US_ASCII);


        mBeacon = new UriBeacon(temp_flag,temp_power,temp);
        return mBeacon;
    }
}
