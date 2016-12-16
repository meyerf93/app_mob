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
    private SubFunctionalityAdapter subFunctionalityAdapter;

    public CustomFunctionality(String functName, Bitmap functPicture,
                               ArrayList<CustomSubFunctionality> subFunctList,
                               SubFunctionalityAdapter subFunctionalityAdapter){
        this.functName = functName;
        this.functPicture = functPicture;
        this.subFunctList = subFunctList;
        this.subFunctionalityAdapter = subFunctionalityAdapter;
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

    public void setSubfunclist(ArrayList<CustomSubFunctionality> customSubFunctionalityArrayList){
        this.subFunctList = customSubFunctionalityArrayList;
    }

    public void setSubFunctionalityAdapter(SubFunctionalityAdapter subFunctionalityAdapter) {
        this.subFunctionalityAdapter = subFunctionalityAdapter;
    }

    public SubFunctionalityAdapter getSubFunctionalityAdapter() {
        return subFunctionalityAdapter;
    }
}
