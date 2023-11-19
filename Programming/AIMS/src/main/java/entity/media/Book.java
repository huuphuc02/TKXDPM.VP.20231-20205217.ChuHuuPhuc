package entity.media;

import entity.db.AIMSDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Book extends Media{
    protected String author;
    protected String coverType;
    protected String publisher; // the real price of product (eg: 450)
    protected String publishDate; // the price which will be displayed on browser (eg: 500)
    protected int numOfPages;
    protected String language;
    protected String bookCategory;


    public Book(int id, String title, String category, int price, int quantity, String type, String author, String coverType, String publisher, String publishDate, int numOfPages, String language, String bookCategory){
        super(id, title, category, price, quantity, type);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }

    public int getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCoverType() {
        return this.coverType;
    }

    public Book setCoverType(String coverType) {
        this.coverType = coverType;
        return this;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Book setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public Book setPublishDate(String publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public int getNumOfPages() {
        return this.numOfPages;
    }

    public Book setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getBookCategory() {
        return this.bookCategory;
    }

    public Book setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }
    public static int getQuantity(int id) {
    return getMediaById(id).quantity;
}
    @Override
    public String toString() {
        return "{" + super.toString() + " author='" + this.author + "'" + ", coverType='" + this.coverType + "'" + ", publisher='" + this.publisher + "'" + ", publishDate='" + this.publishDate + "'" + ", numOfPages='" + this.numOfPages + "'" + ", language='" + this.language + "'" + ", bookCategory='" + this.bookCategory + "'" + "}";
    }

    public static void main(String[] args){
        AIMSDB db = new AIMSDB();
        System.out.println(getQuantity(2));
        System.out.println(getMediaById(2));
    }
}
