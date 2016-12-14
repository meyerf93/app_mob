package hes_so.greenliving.Fuctionality;

import android.graphics.Bitmap;

import java.util.ArrayList;


/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class CustomFunctionality {

    //parameter for fuctionality
    private String functName;
    private Bitmap functPicture;
    private ArrayList<CustomSubFunctionality> subFunctList;

    public CustomFunctionality(String functName, Bitmap functPicture){
        this.functName = functName;
        this.functPicture = functPicture;
    }

    //getter/setter
    public void setFunctName(String functName) {
        this.functName = functName;
    }

    public String getFunctName() {
        return functName;
    }

    public void setFunctPicture(Bitmap functPicture) {
        this.functPicture = functPicture;
    }

    public Bitmap getFunctPicture() {
        return functPicture;
    }

    public void setSubFunctList(ArrayList<CustomSubFunctionality> subFunctList) {
        this.subFunctList = subFunctList;
    }

    public ArrayList<CustomSubFunctionality> getSubFunctList() {
        return subFunctList;
    }
}
