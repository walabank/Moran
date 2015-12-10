package com.geekband.snap.moran.model;

import java.util.List;

public class Node {
    private String address;
    private List<ImageItem> images;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ImageItem> getImages() {
        return images;
    }

    public void setImages(List<ImageItem> images) {
        this.images = images;
    }
}
