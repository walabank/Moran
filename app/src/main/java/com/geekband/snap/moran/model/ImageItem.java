package com.geekband.snap.moran.model;


public class ImageItem {
    private int imageId;
    private String comment;
    public ImageItem(int imageId,String comment){
        this.imageId = imageId;
        this.comment = comment;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
