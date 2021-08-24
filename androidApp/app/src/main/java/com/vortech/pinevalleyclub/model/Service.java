package com.vortech.pinevalleyclub.model;

public class Service {

    private String name;
    private int imageSrc;

    public Service(String name, int imageSrc) {
        this.name = name;
        this.imageSrc = imageSrc;
    }

    public String getName() {
        return name;
    }

    public int getImageSrc() {
        return imageSrc;
    }
}
