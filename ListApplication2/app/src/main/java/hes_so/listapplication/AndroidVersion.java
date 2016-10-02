package hes_so.listapplication;

/**
 * Created by Florian.Meyer on 02.10.2016.
 * class who contain only 2 string for field of item
 */

public class AndroidVersion {

    private String versionName;
    private String versionNumber;

    public void setVersioName (String versioName){
        this.versionName = versioName;
    }

    public void setVersionNumber(String versionNumber){
        this.versionNumber = versionNumber;
    }

    public String getVersionName () {
        return versionName;
    }

    public  String getVersionNumber() {
        return versionNumber;
    }
}
