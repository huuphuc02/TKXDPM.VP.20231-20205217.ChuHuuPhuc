package entity.media;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.db.AIMSDB;

public class Media {
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;


    public Media (int id, String title, String category, int price, int quantity, String type) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public static int getQuantity(int id) {
        return getMediaById(id).quantity;
    }

    public static Media getMediaById(int id) {
        ArrayList<Media> mediaData = AIMSDB.media;
        for (Media mediaDatum : mediaData) {
            if (mediaDatum.id == id) {
                return mediaDatum;
            }
        }
        return null;
    }

    public static ArrayList<Media> getAllMedia() {
        return AIMSDB.media;
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    private Media setId(int id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public Media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", title='" + title + "'" +
            ", category='" + category + "'" +
            ", price='" + price + "'" +
            ", quantity='" + quantity + "'" +
            ", type='" + type + "'" +
            ", imageURL='" + imageURL + "'" +
            "}";
    }

    public static void main(String[] args) {
        AIMSDB db = new AIMSDB();
        System.out.println(getQuantity(1));
        System.out.println(getMediaById(1));
    }
}