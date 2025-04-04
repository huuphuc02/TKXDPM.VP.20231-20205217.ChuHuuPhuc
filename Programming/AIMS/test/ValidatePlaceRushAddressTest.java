import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.PlaceOrderController;

public class ValidatePlaceRushAddressTest {
    
    private PlaceOrderController placeOrderController;

    
    /** 
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
        "Hà Nội, adssadsad, true",
        "Hà Nasdội, adssadsad, false",
    })

    @Test
    public void test(String province, String address, boolean expected) {
        boolean isValid = placeOrderController.validateAddressPlaceRushOrder(province, address);
        assertEquals(expected, isValid);
    }
}
