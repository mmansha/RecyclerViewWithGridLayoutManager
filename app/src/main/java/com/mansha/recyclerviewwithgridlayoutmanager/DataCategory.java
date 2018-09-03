package com.mansha.recyclerviewwithgridlayoutmanager;

import android.graphics.Bitmap;

public class DataCategory {
    private String categoryName, captionName;
    private String captionImageFilenameAbs, captionSoundFilenameAbs;
    private String directory = null;

    public DataCategory(String categoryName, String captionName, String captionImageFilename, String captionSoundFilename){
        this.categoryName = categoryName;
        this.captionName = captionName;
        this.captionImageFilenameAbs = this.directory +  "/" + captionImageFilename;
        this.captionSoundFilenameAbs = this.directory + "/" + captionSoundFilename;

    }

    public void setDirectory(String directory){
        this.directory = directory;
    }

}
