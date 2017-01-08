package hes_so.greenliving.UI;

import android.graphics.Bitmap;

/**
 * Created by Florian_meyer on 07.01.2017.
 */

public class CustomUI {

    private Bitmap uiBackground;

    private Bitmap leftThirdIsolation;
    private Bitmap leftSecondIsolation;
    private Bitmap leftFirstIsolation;

    private boolean leftThirdIsolationVisible;
    private boolean leftSecondIsolationVisible;
    private boolean leftFirstIsolationVisible;

    private Bitmap leftWindow;
    private Bitmap middleWindow;
    private Bitmap rightWindow;

    private boolean leftWindowVisible;
    private boolean middleWindowVisible;
    private boolean rightWindowVisible;

    private Bitmap rightThirdIsolation;
    private Bitmap rightSecondIsolation;
    private Bitmap rightFirstIsolation;

    private boolean rightThirdIsolationVisible;
    private boolean rightSecondIsolationVisible;
    private boolean rightFirstIsolationVisible;

    private int leftTypeWindow;
    private int middleTypeWindow;
    private int rightTypeWindow;


    public CustomUI (Bitmap uiBackground,
                     Bitmap leftThirdIsolation, Boolean leftThirdIsolationVisible,
                     Bitmap leftSecondIsolation, Boolean leftSecondIsolationVisible,
                     Bitmap leftFirstIsolation, Boolean leftFirstIsolationVisible,
                     Bitmap leftWindow, Boolean leftWindowVisible,
                     Bitmap middleWindow, Boolean middleWindowVisible,
                     Bitmap rightWindow,Boolean rightWindowVisible,
                     Bitmap rightFirstIsolation, Boolean rightFirstIsolationVisible,
                     Bitmap rightSecondIsolation, Boolean rightSecondIsolationVisible,
                     Bitmap rightThirdIsolation, Boolean rightThirdIsolationVisible,
                     int leftTypeWindow, int middleTypeWindow, int rightTypeWindow){

        this.uiBackground = uiBackground;

        // Bitmap ----------------------------------------------------------------------------------
        this.leftThirdIsolation = leftThirdIsolation;
        this.leftSecondIsolation = leftSecondIsolation;
        this.leftFirstIsolation = leftFirstIsolation;

        this.leftWindow = leftWindow;
        this.middleWindow = middleWindow;
        this.rightWindow = rightWindow;

        this.rightFirstIsolation = rightFirstIsolation;
        this.rightSecondIsolation = rightSecondIsolation;
        this.rightThirdIsolation = rightThirdIsolation;
        //------------------------------------------------------------------------------------------

        // Active image ----------------------------------------------------------------------------
        this.leftThirdIsolationVisible = leftThirdIsolationVisible;
        this.leftSecondIsolationVisible = leftSecondIsolationVisible;
        this.leftFirstIsolationVisible = leftFirstIsolationVisible;

        this.leftWindowVisible = leftWindowVisible;
        this.middleWindowVisible = middleWindowVisible;
        this.rightWindowVisible = rightWindowVisible;

        this.rightFirstIsolationVisible = rightFirstIsolationVisible;
        this.rightSecondIsolationVisible = rightSecondIsolationVisible;
        this.rightThirdIsolationVisible = rightThirdIsolationVisible;
        //------------------------------------------------------------------------------------------
        // Type of window   ------------------------------------------------------------------------
        this.leftTypeWindow = leftTypeWindow;
        this.middleTypeWindow = middleTypeWindow;
        this.rightTypeWindow = rightTypeWindow;
        //------------------------------------------------------------------------------------------
    }

    // Setter/getter bitmap ------------------------------------------------------------------------
    public Bitmap getUiBackground() {
        return uiBackground;
    }

    public void setUiBackground(Bitmap uiBackground) {
        this.uiBackground = uiBackground;
    }

    public Bitmap getLeftThirdIsolation() {
        return leftThirdIsolation;
    }

    public void setLeftThirdIsolation(Bitmap leftThirdIsolation) {
        this.leftThirdIsolation = leftThirdIsolation;
    }

    public Bitmap getLeftSecondIsolation() {
        return leftSecondIsolation;
    }

    public void setLeftSecondIsolation(Bitmap leftSecondIsolation) {
        this.leftSecondIsolation = leftSecondIsolation;
    }

    public Bitmap getLeftFirstIsolation() {
        return leftFirstIsolation;
    }

    public void setLeftFirstIsolation(Bitmap leftFirstIsolation) {
        this.leftFirstIsolation = leftFirstIsolation;
    }

    public Bitmap getLeftWindow() {
        return leftWindow;
    }

    public void setLeftWindow(Bitmap leftWindow) {
        this.leftWindow = leftWindow;
    }

    public Bitmap getMiddleWindow() {
        return middleWindow;
    }

    public void setMiddleWindow(Bitmap middleWindow) {
        this.middleWindow = middleWindow;
    }

    public Bitmap getRightWindow() {
        return rightWindow;
    }

    public void setRightWindow(Bitmap rightWindow) {
        this.rightWindow = rightWindow;
    }

    public Bitmap getRightThirdIsolation() {
        return rightThirdIsolation;
    }

    public void setRightThirdIsolation(Bitmap rightThirdIsolation) {
        this.rightThirdIsolation = rightThirdIsolation;
    }

    public Bitmap getRightSecondIsolation() {
        return rightSecondIsolation;
    }

    public void setRightSecondIsolation(Bitmap rightSecondIsolation) {
        this.rightSecondIsolation = rightSecondIsolation;
    }

    public Bitmap getRightFirstIsolation() {
        return rightFirstIsolation;
    }

    public void setRightFirstIsolation(Bitmap rightFirstIsolation) {
        this.rightFirstIsolation = rightFirstIsolation;
    }
    //----------------------------------------------------------------------------------------------

    // Setter/getter Boolean -----------------------------------------------------------------------

    public boolean isLeftThirdIsolationVisible() {
        return leftThirdIsolationVisible;
    }

    public void setLeftThirdIsolationVisible(boolean leftThirdIsolationVisible) {
        this.leftThirdIsolationVisible = leftThirdIsolationVisible;
    }

    public boolean isLeftSecondIsolationVisible() {
        return leftSecondIsolationVisible;
    }

    public void setLeftSecondIsolationVisible(boolean leftSecondIsolationVisible) {
        this.leftSecondIsolationVisible = leftSecondIsolationVisible;
    }

    public boolean isLeftFirstIsolationVisible() {
        return leftFirstIsolationVisible;
    }

    public void setLeftFirstIsolationVisible(boolean leftFirstIsolationVisible) {
        this.leftFirstIsolationVisible = leftFirstIsolationVisible;
    }

    public boolean isLeftWindowVisible() {
        return leftWindowVisible;
    }

    public void setLeftWindowVisible(boolean leftWindowVisible) {
        this.leftWindowVisible = leftWindowVisible;
    }

    public boolean isMiddleWindowVisible() {
        return middleWindowVisible;
    }

    public void setMiddleWindowVisible(boolean middleWindowVisible) {
        this.middleWindowVisible = middleWindowVisible;
    }

    public boolean isRightWindowVisible() {
        return rightWindowVisible;
    }

    public void setRightWindowVisible(boolean rightWindowVisible) {
        this.rightWindowVisible = rightWindowVisible;
    }

    public boolean isRightThirdIsolationVisible() {
        return rightThirdIsolationVisible;
    }

    public void setRightThirdIsolationVisible(boolean rightThirdIsolationVisible) {
        this.rightThirdIsolationVisible = rightThirdIsolationVisible;
    }

    public boolean isRightSecondIsolationVisible() {
        return rightSecondIsolationVisible;
    }

    public void setRightSecondIsolationVisible(boolean rightSecondIsolationVisible) {
        this.rightSecondIsolationVisible = rightSecondIsolationVisible;
    }

    public boolean isRightFirstIsolationVisible() {
        return rightFirstIsolationVisible;
    }

    public void setRightFirstIsolationVisible(boolean rightFirstIsolationVisible) {
        this.rightFirstIsolationVisible = rightFirstIsolationVisible;
    }

    //----------------------------------------------------------------------------------------------

    // Getter/setter type of window-----------------------------------------------------------------

    public int getLeftTypeWindow() {
        return leftTypeWindow;
    }

    public void setLeftTypeWindow(int leftTypeWindow) {
        this.leftTypeWindow = leftTypeWindow;
    }

    public int getMiddleTypeWindow() {
        return middleTypeWindow;
    }

    public void setMiddleTypeWindow(int middleTypeWindow) {
        this.middleTypeWindow = middleTypeWindow;
    }

    public int getRightTypeWindow() {
        return rightTypeWindow;
    }

    public void setRightTypeWindow(int rightTypeWindow) {
        this.rightTypeWindow = rightTypeWindow;
    }

    //----------------------------------------------------------------------------------------------
}
