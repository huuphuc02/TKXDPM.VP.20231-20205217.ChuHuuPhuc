package entity.order;

import entity.order.Order;
import entity.order.OrderMedia;
import entity.db.AIMSDB;
import entity.media.Media;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void testGetAmount() {
        Order order = new Order();
        AIMSDB db = new AIMSDB();

        // Assuming OrderMedia constructor takes Media, quantity, and price
        OrderMedia media1 = new OrderMedia(db.media.get(0), 1, db.media.get(0).getPrice());
        OrderMedia media2 = new OrderMedia(db.media.get(1), 1, db.media.get(1).getPrice());

        order.addOrderMedia(media1);
        order.addOrderMedia(media2);

        int expectedAmount = (int) ((media1.getPrice() + media2.getPrice()) + (10.0/100)*(media1.getPrice() + media2.getPrice()));
        assertEquals(expectedAmount, order.getAmount(), "Total amount should be calculated correctly");
    }
}