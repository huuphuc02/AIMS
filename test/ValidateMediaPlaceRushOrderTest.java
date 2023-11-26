import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.PlaceOrderController;

public class ValidateMediaPlaceRushOrderTest {

    private PlaceOrderController placeOrderController;


    /**
     * @throws Exception
     */
    @BeforeEach
    void setup() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "book 1",
            "dvd 2",
            "cd 3"
    })

    @Test
    public void test(String titleProduct) {
        boolean isValid = placeOrderController.validateMediaPlaceRushorder();
        assertTrue(isValid);
    }
}
