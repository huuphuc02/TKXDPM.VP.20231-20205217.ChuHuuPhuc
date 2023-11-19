package entity.cart;

import entity.db.AIMSDB;
import entity.media.DVD;
import entity.media.Media;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void addCartMedia() {
            AIMSDB db = new AIMSDB();
            ArrayList<Media> media = db.media;
            // Giỏ hàng rỗng
            Cart cart = new Cart();
            for (int i=1;i<3 ; i++){
            // Thêm media vào giỏ hàng
            CartMedia cartMedia = new CartMedia(media.get(i),cart,3,media.get(i).getPrice());
            cart.addCartMedia(cartMedia);
            // Xác nhận rằng media đã được thêm vào giỏ hàng
            assertTrue(cart.getListMedia().contains(cartMedia));
        }
    }
    @Test
    void removeCartMedia() {
        // Initialize the database and get a list of media
        AIMSDB db = new AIMSDB();
        ArrayList<Media> media = db.media;

        // Tạo giỏ hàng và thêm media vào giỏ
        Cart cart = new Cart();
        for (int i = 0; i < 3; i++) {
            CartMedia cartMedia = new CartMedia(media.get(i), cart, 2, media.get(i).getPrice());
            cart.addCartMedia(cartMedia);
        }

        CartMedia cartMedia = new CartMedia(media.get(1), cart, 2, media.get(1).getPrice());
        cart.removeCartMedia(cartMedia);
        // Xác nhận rằng media đã được xóa khỏi giỏ hàng
        assertFalse(cart.getListMedia().contains(cartMedia));
    }
    @Test
    void testGetTotalMedia() {
        // Initialize the database and get a list of media
        AIMSDB db = new AIMSDB();
        ArrayList<Media> media = db.media;

        // Tạo giỏ hàng và thêm media vào giỏ
        Cart cart = new Cart();
        for (int i = 0; i < 3; i++) {
            CartMedia cartMedia = new CartMedia(media.get(i), cart, 2, media.get(i).getPrice());
            cart.addCartMedia(cartMedia);
        }
        System.out.println(cart.getTotalMedia());
        // Kiểm tra tổng số lượng media trong giỏ hàng
        assertEquals(6, cart.getTotalMedia());
    }
    @Test
    void calSubtotal() {
        // Initialize the database and get a list of media
        AIMSDB db = new AIMSDB();
        ArrayList<Media> media = db.media;

        // Tạo giỏ hàng và thêm media vào giỏ
        Cart cart = new Cart();
        for (int i = 0; i < 3; i++) {
            CartMedia cartMedia = new CartMedia(media.get(i), cart, 2, media.get(i).getPrice());
            cart.addCartMedia(cartMedia);
        }
        // Kiểm tra tổng số lượng media trong giỏ hàng
        assertEquals(2*450+2*300+2*100, cart.calSubtotal());
    }
    @Test
    void checkMediaInCart() {
        // Initialize the database and get a list of media
        AIMSDB db = new AIMSDB();
        ArrayList<Media> media = db.media;
        // Tạo giỏ hàng và thêm media vào giỏ
        Cart cart = new Cart();
        for (int i = 0; i < 3; i++) {
            CartMedia cartMedia = new CartMedia(media.get(i), cart, 2, media.get(i).getPrice());
            cart.addCartMedia(cartMedia);
        }
        CartMedia cartMedia = new CartMedia(media.get(1), cart, 2, media.get(1).getPrice());
        // Kiểm tra tổng số lượng media trong giỏ hàng
        assertEquals(cartMedia.toString(),cart.checkMediaInCart(media.get(1)).toString());
        // Kiểm tra xem phương thức checkMediaInCart trả về null khi không tìm thấy Media trong giỏ hàng
        Media media3 = new DVD(4, "dvd", "dvd", 234, 20, "dvd", "ms", "B", 3232, "ks", "bc", "17/11/2023", "a");
        assertNull(cart.checkMediaInCart(media3));
    }

}