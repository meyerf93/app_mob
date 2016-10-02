package ch.heia.mobiledev.treasurehunt;

import android.util.Log;
import android.util.Xml;

import com.google.android.gms.maps.model.LatLng;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class XmlParser {

    private final static String TAG = XmlPullParser.class.getSimpleName();

    static public List restoreData(FileInputStream fis) {
        List entries = null;

        try
        {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(fis, "UTF-8");
            parser.nextTag();
            entries = new ArrayList();

            parser.require(XmlPullParser.START_TAG, null, "TreasureHunt");
            while (parser.next() != XmlPullParser.END_TAG) {
                Log.v(TAG, "onLoop for parse");
                if (parser.getEventType() != XmlPullParser.START_TAG) continue;
                String name = parser.getName();
                switch (name) {
                    case "Beacon":
                        entries.add(readBeacon(parser));
                        break;
                    case "TotalStep":
                        entries.add(readTotalStep(parser));
                        break;
                    case "CurrentStep":
                        entries.add(readCurrentStep(parser));
                        break;
                    case "PrevMinor":
                        entries.add(readPrevMinor(parser));
                        break;
                }
            }
        }
        catch(IOException | XmlPullParserException e)
        {
            e.printStackTrace();
        }
        try {
            if(fis!=null)fis.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
    //Xml parser code ----------------------------------------------------------------------------------------------------------
    //method to parse information of a beacon in the xml save file
    @SuppressWarnings("ConstantConditions")
    static private Beacon readBeacon(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "Beacon");
        //local data used to recreate a instance of beacon
        int Beacon_name = 0;
        int Clue_name = 0;
        UUID uuid = null;
        int Major = -1;
        int Minor = -1;
        byte Pow = 0;
        double dist = -1;
        int step = -1;
        String clue = null;
        LatLng position = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            Log.v(TAG,"onLoop breacon read");
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "Name":
                    Beacon_name = readName(parser);
                    break;
                case "Clue_name":
                    Clue_name = readClue_name(parser);
                    break;
                case "UUID":
                    uuid = readUUID(parser);
                    break;
                case "Major":
                    Major = readMajor(parser);
                    break;
                case "Minor":
                    Minor = readMinor(parser);
                    break;
                case "Powr":
                    Pow = readPow(parser);
                    break;
                case "Dist":
                    dist = readDist(parser);
                    break;
                case "Step":
                    step = readStep(parser);
                    break;
                case "Position":
                    position = readPosition(parser);
                    break;
                case "Clue":
                    clue = readClue(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new Beacon(Beacon_name,Clue_name,uuid, Major, Minor,Pow,dist,step,clue,position);
    }


    static private UUID readUUID(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "UUID");
        UUID uuid = UUID.fromString(readText(parser));
        parser.require(XmlPullParser.END_TAG, null, "UUID");
        return uuid;
    }

    static private int readMajor(XmlPullParser parser) throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,null,"Major");
        int major = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, null, "Major");
        return major;
    }

   static private int readMinor(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null, "Minor");
        int minor = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, null,"Minor");
        return minor;
    }

    static private byte readPow(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,null,"Pow");
        byte pow = Byte.parseByte(readText(parser));
        parser.require(XmlPullParser.END_TAG, null, "Pow");
        return pow;
    }

    static private double readDist(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,null,"Dist");
        double dist = Double.parseDouble(readText(parser));
        parser.require(XmlPullParser.END_TAG,null,"Dist");
        return dist;
    }

    static private int readStep(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,null,"Step");
        int step = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG,null,"Step");
        return step;
    }

    static private int readName(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,null,"Name");
        int step = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG,null,"Name");
        return step;
    }

    static private int readClue_name(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,null,"Clue_name");
        int step = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG,null,"Clue_name");
        return step;
    }

    static private LatLng readPosition(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,null,"Position");
        //regex solution (don't work)
        /*String regex = ".*\\((-*\\d+\\.\\d+),(-*\\d+\\.\\d+)\\).*";
        Pattern p = Pattern.compile(regex);
        String temp = readText(parser);
        Log.v(TAG,"result of read ofthe xml file : "+temp);
        Matcher m = p.matcher(temp);
        Log.v(TAG,"matcher status : "+m);
        double lat = Double.parseDouble(m.group(1));
        double lng = Double.parseDouble(m.group(2));*/
        String temp[] = readText(parser).split("\\(");
        String temp_2[] = temp[1].split(",");
        String temp_3[] = temp_2[1].split("\\)");
        Double lat = Double.parseDouble(temp_2[0]);
        Double lng = Double.parseDouble(temp_3[0]);
        Log.v(TAG,"value of lat : "+lat);
        Log.v(TAG,"value of lng : "+lng);
        LatLng position = new LatLng(lat,lng);
        parser.require(XmlPullParser.END_TAG,null,"Position");
        return position;
    }

    static private String readClue(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null,"Clue");
        String clue = readText(parser);
        parser.require(XmlPullParser.END_TAG,null,"Clue");
        return clue;
    }

    // For the tags title and summary, extracts their text values.
    static private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    static private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    static private int readTotalStep(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null,"TotalStep");
        int totalstep = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG,null,"TotalStep");
        return totalstep;
    }

    static private int readCurrentStep(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null,"CurrentStep");
        int currentstep = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG,null,"CurrentStep");
        return currentstep;
    }

    static private int readPrevMinor(XmlPullParser parser) throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null,"PrevMinor");
        int prevminor = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG,null,"PrevMinor");
        return prevminor;
    }

}
