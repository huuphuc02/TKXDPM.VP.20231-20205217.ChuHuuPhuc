package entity.media;

import entity.db.AIMSDB;

import java.util.ArrayList;

public class CD extends Media{
    protected String artist;
    protected String recordLabel;
    protected String musicType;
    protected String releasedDate;
    public CD(int id, String title, String category, int price, int quantity, String type, String artist, String recordLabel, String musicType, String releasedDate){
        super(id, title, category, price, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = String.valueOf(releasedDate);
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public String getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    public String toString() {
        return "{" + super.toString() + " artist='" + this.artist + "'" + ", recordLabel='" + this.recordLabel + "'" + "'" + ", musicType='" + this.musicType + "'" + ", releasedDate='" + this.releasedDate + "'" + "}";
    }

    public static void main(String[] args){
        AIMSDB db = new AIMSDB();
        System.out.println(getQuantity(3));
        System.out.println(getMediaById(3));
        System.out.println(getAllMedia());
    }
}
