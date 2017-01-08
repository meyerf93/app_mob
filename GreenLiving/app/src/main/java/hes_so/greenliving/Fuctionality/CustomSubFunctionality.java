package hes_so.greenliving.Fuctionality;

import android.graphics.Bitmap;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class CustomSubFunctionality {

    //parameter for sub functionality
    private long id;
    private Bitmap subFuncButtonLeftAdd;
    private Bitmap subFuncButtonLeftRemove;
    private Bitmap subFunctPicture;
    private Bitmap subFuncButtonRightAdd;
    private Bitmap subFuncButtonRightRemove;

    public CustomSubFunctionality(long id,Bitmap subFuncButtonLeftAdd, Bitmap subFuncButtonLeftRemove,
                                  Bitmap subFunctPicture,
                                  Bitmap subFuncButtonRightAdd, Bitmap subFuncButtonRightRemove){
        this.id = id;
        this.subFuncButtonLeftAdd = subFuncButtonLeftAdd;
        this.subFuncButtonLeftRemove = subFuncButtonLeftRemove;
        this.subFuncButtonRightAdd = subFuncButtonRightAdd;
        this.subFuncButtonRightRemove = subFuncButtonRightRemove;
        this.subFunctPicture = subFunctPicture;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    //button getter/setter--------------------------------------------------------------------------
    public Bitmap getSubFuncButtonLeftAdd() {
        return subFuncButtonLeftAdd;
    }

    public void setSubFuncButtonLeftAdd(Bitmap subFuncButtonLeftAdd) {
        this.subFuncButtonLeftAdd = subFuncButtonLeftAdd;
    }

    public Bitmap getSubFuncButtonLeftRemove() {
        return subFuncButtonLeftRemove;
    }

    public void setSubFuncButtonLeftRemove(Bitmap subFuncButtonLeftRemove) {
        this.subFuncButtonLeftRemove = subFuncButtonLeftRemove;
    }

    public Bitmap getSubFuncButtonRightAdd() {
        return subFuncButtonRightAdd;
    }

    public void setSubFuncButtonRightAdd(Bitmap subFuncButtonRightAdd) {
        this.subFuncButtonRightAdd = subFuncButtonRightAdd;
    }

    public Bitmap getSubFuncButtonRightRemove() {
        return subFuncButtonRightRemove;
    }

    public void setSubFuncButtonRightRemove(Bitmap subFuncButtonRightRemove) {
        this.subFuncButtonRightRemove = subFuncButtonRightRemove;
    }
    //----------------------------------------------------------------------------------------------

    public Bitmap getSubFunctPicture(){
        return subFunctPicture;
    }

    public void setSubFunctPicture(Bitmap subFunctPicture){
        this.subFunctPicture = subFunctPicture;
    }

}
