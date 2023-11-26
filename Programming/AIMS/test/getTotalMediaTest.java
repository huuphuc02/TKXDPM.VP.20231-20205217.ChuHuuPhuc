import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class getTotalMediaTest {
    /**
     * @throws Exception
     */

    @Test
    public void test() throws SQLException, ParseException {
        String dateString = "12/11/2023";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = dateFormat.parse(dateString);
        Media book1 = new Book(2, "Book 1", "Action", 300, 20, 1123, true, "John Doe", "Hardcover", "Publisher XYZ",parsedDate, 100, "English", "Fiction");
        Media book2 = new Book(6, "Book 2", "Action", 300, 20, 1123, true, "John Doe", "Hardcover", "Publisher XYZ",parsedDate, 100, "English", "Fiction");
        Media cd1 = new CD(3, "cd", "Music", 20, 10, 200, true, "Artist ABC", "Record Label XYZ", "Pop", parsedDate, "Track 1, Track 2");
        Media dvd1 = new DVD(5, "Movie 2", "Film", 20, 8, 180, true, "DVD-RW", "Director ABC", 135, "Studio XYZ", "Subtitle 3, Subtitle 4",parsedDate, "French");

        Cart cart = new Cart();
        CartMedia cartMedia1 = new CartMedia(book1, cart, 2,book1.getPrice());
        CartMedia cartMedia2 = new CartMedia(cd1, cart, 2, cd1.getPrice());
        CartMedia cartMedia3 = new CartMedia(dvd1, cart, 2, dvd1.getPrice());
        CartMedia cartMedia4 = new CartMedia(book2, cart, 2, dvd1.getPrice());
        cart.addCartMedia(cartMedia1);
        cart.addCartMedia(cartMedia2);
        cart.addCartMedia(cartMedia3);
        cart.addCartMedia(cartMedia4);

        assertEquals(cartMedia1.getQuantity()+cartMedia2.getQuantity()+cartMedia3.getQuantity()+cartMedia4.getQuantity(), cart.getTotalMedia());
    }
}
