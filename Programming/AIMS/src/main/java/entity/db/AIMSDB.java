package entity.db;


import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;

import java.sql.SQLException;
import java.util.ArrayList;

public class AIMSDB {
    public static ArrayList<Media> media = new ArrayList<Media>();
    public AIMSDB() {
        main();
    }

    public static void main(){
        Media media1 = new Media(1, "First Media", "test", 450, 15, "test");
        Media book1 = new Book(2, "Book 1", "action", 300, 20, "book", "abc", "A", "B", "17-11-2023", 100, "Eng", "123");
        Media cd = new CD(3, "cd", "cd", 100, 30, "cd", "a0", "1", "pop", "17/11/2023");
        Media dvd = new DVD(4, "dvd", "dvd", 500, 20, "dvd", "ms", "B", 200, "ks", "bc", "17/11/2023", "a");
        media.add(media1);
        media.add(book1);
        media.add(cd);
        media.add(dvd);
    }
}
