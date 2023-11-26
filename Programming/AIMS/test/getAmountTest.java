import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class getAmountTest {

    @Test
    void getAmount() throws SQLException, ParseException {
        Order order = new Order();
        String dateString = "12/11/2023";
        // Parse the date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = dateFormat.parse(dateString);
        Media book1 = new Book(2, "Book 1", "Action", 300, 20, 1123, true, "John Doe", "Hardcover", "Publisher XYZ",parsedDate, 100, "English", "Fiction");
        Media dvd1 = new DVD(5, "Movie 2", "Film", 20, 8, 180, true, "DVD-RW", "Director ABC", 135, "Studio XYZ", "Subtitle 3, Subtitle 4",parsedDate, "French");
        // Assuming OrderMedia constructor takes Media, quantity, and price
        OrderMedia media1 = new OrderMedia(book1, 1,book1.getPrice());
        OrderMedia media2 = new OrderMedia(dvd1, 2, dvd1.getPrice());

        order.addOrderMedia(media1);
        order.addOrderMedia(media2);

        int expectedAmount = (int) ((media1.getPrice() + media2.getPrice()) + (10.0/100)*(media1.getPrice() + media2.getPrice()));
        assertEquals(expectedAmount, order.getAmount(), "Total amount should be calculated correctly");
    }
}
