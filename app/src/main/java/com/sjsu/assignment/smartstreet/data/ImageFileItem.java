package com.sjsu.assignment.smartstreet.data;

import android.graphics.Bitmap;

/**
 * Created by sindhya on 9/20/17.
 */
public class ImageFileItem {

    private Bitmap imgFile;
    private String fileLocation;

    public Bitmap getImgFile() {
        return imgFile;
    }

    public void setImgFile(Bitmap imgFile) {
        this.imgFile = imgFile;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
