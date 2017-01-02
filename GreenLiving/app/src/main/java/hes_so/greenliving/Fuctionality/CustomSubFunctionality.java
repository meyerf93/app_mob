package hes_so.greenliving.Fuctionality;

import android.graphics.Bitmap;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class CustomSubFunctionality {

    //parameter for sub functionality
    private long id;
    private Bitmap subFuncButton;
    private Bitmap subFunctPicture;

    public CustomSubFunctionality(long id,Bitmap subFuncButton, Bitmap subFunctPicture){
        this.id = id;
        this.subFuncButton = subFuncButton;
        this.subFunctPicture = subFunctPicture;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Bitmap getSubFuncButton() {
        return subFuncButton;
    }

    public void setSubFuncButton(Bitmap subFuncButton) {
        this.subFuncButton = subFuncButton;
    }

    public Bitmap getSubFunctPicture(){
        return subFunctPicture;
    }

    public void setSubFunctPicture(Bitmap subFunctPicture){
        this.subFunctPicture = subFunctPicture;
    }

}
