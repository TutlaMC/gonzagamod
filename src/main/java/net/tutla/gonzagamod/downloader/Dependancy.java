package net.tutla.gonzagamod.downloader;

public class Dependancy {
    public String name;
    public String url;
    public String pattern;
    public Dependancy(String name, String url, String pattern){
        this.name = name;
        this.url = url;
        this.pattern = pattern;
    }
}
