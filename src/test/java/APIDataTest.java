import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class APIDataTest {

    APIService api = new APIService();

    @Test
    void testAPITimeout() {
        Exception exception = assertThrows(APIException.class, () -> {
            api.fetchData("simulateTimeout");
        });
        assertEquals("API Timeout", exception.getMessage());
    }

    @Test
    void testRateLimit() {
        Exception exception = assertThrows(APIException.class, () -> {
            api.fetchData("simulateRateLimit");
        });
        assertEquals("Rate Limit Reached", exception.getMessage());
    }

}
