import com.cryptochecker.ConversionUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConversionTest {

    ConversionUtil conversion = new ConversionUtil();

    @Test
    void testValidPositiveAmount() {
        double result = conversion.cryptoToCrypto(2500.75, "BTC", "ETH");
        assertTrue(result > 0);
    }

    @Test
    void testZeroInput() {
        double result = conversion.cryptoToCrypto(0, "BTC", "ETH");
        assertEquals(0, result);
    }

    @Test
    void testInvalidNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            conversion.cryptoToCrypto(-1.0, "BTC", "ETH");
        });
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    void testBelowPrecision() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            conversion.cryptoToCrypto(0.000000009, "BTC", "ETH");
        });
        assertEquals("Amount below minimum precision", exception.getMessage());
    }

}
