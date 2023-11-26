import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import controller.PlaceOrderController;


class ValidateNameTest {
    
    private PlaceOrderController placeOrderController;

    
    /** 
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
        " , false",
        ", false",
        "ChuHuu, true",
        "Chu Huu Phuc, true",
        "Chu Huu Phuc7, false",
        "213123!@#!@$#, false",
        "Chu Huu Phuc5, false"
    })
    @Test
    public void test(String name, boolean expected) {
        boolean isValid = placeOrderController.validateName(name);
        assertEquals(expected, isValid);
    }

}
