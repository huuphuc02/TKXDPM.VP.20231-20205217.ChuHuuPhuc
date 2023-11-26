import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Book;
import entity.media.Media;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class checkMediaInCartTest {
    /**
     * @throws Exception
     */
    @Test
    public void test() throws SQLException, ParseException {
        String dateString = "12/11/2023";
        // Parse the date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = dateFormat.parse(dateString);
        Media book1 = new Book(2, "Book 1", "Action", 300, 20, 1123, true, "John Doe", "Hardcover", "Publisher XYZ",parsedDate, 100, "English", "Fiction");
        Cart cart = new Cart();
        CartMedia cartMedia1 = new CartMedia(book1, cart, 2,book1.getPrice());
        cart.addCartMedia(cartMedia1);

        // Media in cart
        CartMedia cartMedia = cart.checkMediaInCart(book1);
        assertEquals(cartMedia1, cartMedia);
        // Media not in cart
        Media book2 = new Book(6, "Book 2", "Action", 300, 20, 1123, true, "John Doe", "Hardcover", "Publisher XYZ",parsedDate, 100, "English", "Fiction");
        CartMedia cartMedia2 = cart.checkMediaInCart(book2);
        assertNull(cartMedia2);
    }
}
