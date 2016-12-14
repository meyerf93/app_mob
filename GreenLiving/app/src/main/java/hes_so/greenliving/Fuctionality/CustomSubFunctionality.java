package hes_so.greenliving.Fuctionality;

import android.graphics.Bitmap;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class CustomSubFunctionality {

    //parameter for sub functionality
    private Bitmap subFuncButton;
    private Bitmap subFunctPicture;

    public CustomSubFunctionality(Bitmap subFuncButton, Bitmap subFunctPicture){
        this.subFuncButton = subFuncButton;
        this.subFunctPicture = subFunctPicture;
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
