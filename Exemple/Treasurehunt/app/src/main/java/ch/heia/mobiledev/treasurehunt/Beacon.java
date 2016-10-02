package ch.heia.mobiledev.treasurehunt;

import android.bluetooth.le.ScanRecord;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.UUID;


class Beacon {

    private final static String TAG = Beacon.class.getSimpleName();

    static final private UUID dfUUID = new UUID(-1938525831413477719L,-7392408269597520259L);//"e518f906eeae4ea9,9968e3d86fface7d";
    static final private int dfMajor = 1;
    static public int prevMinor = 245;
    public final int name;
    public final UUID uuid;
    public final int Major;
    public final int Minor;
    public final byte Pow;
    public double dist;
    public int step;
    public final int clue_name;
    public String clue;
    public LatLng position;

    static final private int ListBeaconName[] = {R.string.overview_beacon_frag_1_text,R.string.overview_beacon_frag_3_text,
                                                 R.string.overview_beacon_frag_3_text,R.string.overview_beacon_frag_4_text};

    static final private int ListClueName[] = {R.string.overview_clue_frag_1_text,R.string.overview_clue_frag_2_text,
                                               R.string.overview_clue_frag_3_text,R.string.overview_clue_frag_4_text};

    static final private int startMinor = 245;



    private Beacon(int name,int clue_name,UUID uuid, int Major, int Minor, byte Pow){
        this.name = name;
        this.clue_name = clue_name;
        this.uuid = uuid;
        this.Major = Major;
        this.Minor = Minor;
        this.Pow = Pow;
        dist = -1;
        step = 0;
        clue = null;
        position = null;
        Log.v(TAG,"Name : "+this.name);

        Log.v(TAG,"UUID : "+this.uuid);
        Log.v(TAG,"Major : "+this.Major);
        Log.v(TAG,"Minor : "+this.Minor);
        Log.v(TAG,"Pow : "+this.Pow);
        Log.v(TAG,"Dist : "+this.dist);
        Log.v(TAG,"Step : "+this.step);
        Log.v(TAG,"Clue : "+this.clue);
        Log.v(TAG,"Position : "+this.position);
    }
    Beacon(int name,int clue_name,UUID uuid, int Major, int Minor, byte Pow, double dist, int step, String clue,LatLng position){
        this.name = name;
        this.clue_name = clue_name;
        this.uuid = uuid;
        this.Major = Major;
        this.Minor = Minor;
        this.Pow = Pow;
        this.dist = dist;
        this.step = step;
        this.clue = clue;
        this.position = position;
        Log.v(TAG,"Name : "+this.name);
        Log.v(TAG,"Clue Name : "+this.clue_name);
        Log.v(TAG,"UUID : "+this.uuid);
        Log.v(TAG,"Major : "+this.Major);
        Log.v(TAG,"Minor : "+this.Minor);
        Log.v(TAG,"Pow : "+this.Pow);
        Log.v(TAG,"Dist : "+this.dist);
        Log.v(TAG,"Step : "+this.step);
        Log.v(TAG,"Clue : "+this.clue);
        Log.v(TAG,"Position : "+this.position);
    }

    static Beacon createFromScanResult(ScanRecord result){
        Beacon mBeacon = null;
        //byte payload[] = result.getManufacturerSpecificData(76);
        byte payload[] = result.getBytes();
        long firstPart = convByteToLong(Arrays.copyOfRange(payload,9,17));
        long secondPart = convByteToLong(Arrays.copyOfRange(payload,17,25));
        UUID dataUUID = new UUID(firstPart,secondPart);
        int dataMajor = convByteToInt(Arrays.copyOfRange(payload,25,27));
        int dataMinor = convByteToInt(Arrays.copyOfRange(payload,27,29));
        byte dataPow = Arrays.copyOfRange(payload,29,30)[0];

        if(payload[7]==0x02&&payload[8]==0x15){
            if(dfUUID.equals(dataUUID)){
                if(dfMajor==dataMajor){
                   if(prevMinor==dataMinor-1){
                       mBeacon = new Beacon(ListBeaconName[dataMinor-startMinor],ListClueName[dataMinor-startMinor]
                                            ,dataUUID,dataMajor,dataMinor,dataPow);
                   }
                }
            }
        }

        Log.v(TAG,"UUID : "+dataUUID);
        Log.v(TAG,"Major : "+dataMajor);
        Log.v(TAG,"Minor : "+dataMinor);
        Log.v(TAG,"Pow : "+dataPow);
        return mBeacon;
    }

    static private long convByteToLong(byte[] val){
        long result = 0;
        int nbrOfBits = val.length * 8;
        for(int i=0;i<val.length;++i){
            long temp = (((long) val[i]) & 0xFF);
            int shiftValue = (nbrOfBits - 8 * (i + 1));
            temp = temp << shiftValue;
            result |= temp;
        }
        return result;
    }

    private static int convByteToInt(byte[] val){
        int result = 0;
        int nbrOfBits = val.length*8;
        for(int i=0;i<val.length;++i){
            int temp = (((int) val[i]) & 0xFF);
            int shiftValue = (nbrOfBits-8 *(i+1));
            temp = temp << shiftValue;
            result |= temp;
        }
        return result;
    }
}
