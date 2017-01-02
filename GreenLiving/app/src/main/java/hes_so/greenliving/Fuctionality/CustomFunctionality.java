package hes_so.greenliving.Fuctionality;

import android.graphics.Bitmap;

import java.util.ArrayList;


/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class CustomFunctionality {

    //parameter for fuctionality
    private long id;
    private String functName;
    private Bitmap functPicture;
    private ArrayList<CustomSubFunctionality> subFunctList;

    public CustomFunctionality(Long id,String functName, Bitmap functPicture,
                               ArrayList<CustomSubFunctionality> subFunctList){
        this.id = id;
        this.functName = functName;
        this.functPicture = functPicture;
        this.subFunctList = subFunctList;
    }

    //getter/setter
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

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
