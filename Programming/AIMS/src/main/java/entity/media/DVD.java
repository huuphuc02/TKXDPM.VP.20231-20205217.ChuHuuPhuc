package entity.media;

import entity.db.AIMSDB;

public class DVD extends Media{
    protected String discType;
    protected String director;
    protected int runtime;
    protected String studio;
    protected String subtitles;
    protected String releasedDate;
    protected String filmType;


    public DVD(int id, String title, String category, int price, int quantity, String type, String discType, String director, int runtime, String studio, String subtitles, String releasedDate, String filmType) {
        super(id, title, category, price, quantity, type);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.subtitles = subtitles;
        this.releasedDate = String.valueOf(releasedDate);
        this.filmType = filmType;
    }

    public String getDiscType() {
        return this.discType;
    }

    public DVD setDiscType(String discType) {
        this.discType = discType;
        return this;
    }

    public String getDirector() {
        return this.director;
    }

    public DVD setDirector(String director) {
        this.director = director;
        return this;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public DVD setRuntime(int runtime) {
        this.runtime = runtime;
        return this;
    }

    public String getStudio() {
        return this.studio;
    }

    public DVD setStudio(String studio) {
        this.studio = studio;
        return this;
    }

    public String getSubtitles() {
        return this.subtitles;
    }

    public DVD setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public String getReleasedDate() {
        return this.releasedDate;
    }

    public DVD setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    public String getFilmType() {
        return this.filmType;
    }

    public DVD setFilmType(String filmType) {
        this.filmType = filmType;
        return this;
    }

    public String toString() {
        return "{" + super.toString() + " discType='" + this.discType + "'" + ", director='" + this.director + "'" + ", runtime='" + this.runtime + "'" + ", studio='" + this.studio + "'" + ", subtitles='" + this.subtitles + "'" + ", releasedDate='" + this.releasedDate + "'" + ", filmType='" + this.filmType + "'" + "}";
    }
    public static void main(String[] args){
        AIMSDB db = new AIMSDB();
        System.out.println(getQuantity(4));
        System.out.println(getMediaById(4));
        System.out.println(getAllMedia());
    }
}
