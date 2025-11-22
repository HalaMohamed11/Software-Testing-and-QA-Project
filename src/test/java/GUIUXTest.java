import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GUIUXTest {

    GUI gui = new GUI();

    @Test
    void testTrendColorCoding() {
        String color = gui.getTrendColor(5);
        assertEquals("Green", color);
        color = gui.getTrendColor(-3);
        assertEquals("Red", color);
    }

}
