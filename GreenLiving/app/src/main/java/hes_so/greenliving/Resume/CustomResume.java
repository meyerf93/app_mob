package hes_so.greenliving.Resume;

import android.graphics.Bitmap;

/**
 * Created by Florian.Meyer on 03.01.2017.
 */

public class CustomResume {

    private Bitmap imageResume;
    private int numberResume;

    private int northResume;
    private int southResume;
    private int westResume;
    private int eastResume;

    private String descriptionResume;

    public CustomResume(Bitmap imageResume, int numberResume, int northResume,
                        int southResume, int westResume,
                        int eastResume, String descriptionResume) {

        this.imageResume = imageResume;
        this.numberResume = numberResume;
        this.northResume = northResume;
        this.southResume = southResume;
        this.westResume = westResume;
        this.eastResume = eastResume;
        this.descriptionResume = descriptionResume;
    }

    //getter/setter

    public Bitmap getImageResume() {
        return imageResume;
    }

    public void setImageResume(Bitmap imageResume) {
        this.imageResume = imageResume;
    }

    public int getNumberResume() {
        return numberResume;
    }

    public void setNumberResume(int numberResume) {
        this.numberResume = numberResume;
    }

    public int getNorthResume() {
        return northResume;
    }

    public void setNorthResume(int northResume) {
        if(northResume > this.northResume) numberResume += northResume-this.northResume;
        else numberResume -=this.northResume-northResume;
        this.northResume = northResume;
    }

    public int getSouthResume() {
        return southResume;
    }

    public void setSouthResume(int southResume) {
        if(southResume > this.southResume) numberResume += southResume-this.southResume;
        else numberResume -=this.southResume-southResume;
        this.southResume = southResume;
    }

    public int getWestResume() {
        return westResume;
    }

    public void setWestResume(int westResume) {
        if(westResume > this.westResume) numberResume += westResume-this.westResume;
        else numberResume -=this.westResume-westResume;
        this.westResume = westResume;
    }

    public int getEastResume() {
        return eastResume;
    }

    public void setEastResume(int eastResume) {
        if(eastResume> this.eastResume) numberResume += eastResume-this.eastResume;
        else numberResume -= this.eastResume-eastResume;
        this.eastResume = eastResume;
    }

    public String getDescriptionResume() {
        return descriptionResume;
    }

    public void setDescriptionResume(String descriptionResume) {
        this.descriptionResume = descriptionResume;
    }
}
