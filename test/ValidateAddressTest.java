import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.PlaceOrderController;

public class ValidateAddressTest {
    
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
        "Cuong Gian Nghi Xuan Ha Tinh, true",
        "123ADSASD123213 Cuong Gian Nghi Xuan Ha Tinh, false",
        "   Cuong Gian Nghi Xuan Ha Tinh, true",
        "Cuong Gian Nghi Xuan  sASDSADASDSADSADon Ha Tinh , true"
    })

    @Test
    public void test(String address, boolean expected) {
        boolean isValid = placeOrderController.validateAddress(address);
        assertEquals(expected, isValid);
    }
}
