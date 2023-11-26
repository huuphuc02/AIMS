import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.PlaceOrderController;

public class ValidateEmailTest {

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
            "huuphuc123@gmail.com, true",
            "huuphuc123.com, false",
            "   abc@hust.edu.vn, true",
            "huuphuc-!@gmail.com , false"
    })
    @Test
    public void test(String email, boolean expected) {
        boolean isValid = placeOrderController.validateEmail(email);
        assertEquals(expected, isValid);
    }
}

