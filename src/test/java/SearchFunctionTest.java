import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SearchFunctionTest {

    SearchUtil search = new SearchUtil();

    @Test
    void testSearchValidName() {
        String result = search.searchByName("Bitcoin");
        assertEquals("Bitcoin", result);
    }

    @Test
    void testSearchWithSpaces() {
        String result = search.searchByName(" Bitcoin ");
        assertEquals("Bitcoin", result);
    }

    @Test
    void testSearchEmptyQuery() {
        String result = search.searchByName(" ");
        assertEquals("No results found", result);
    }

}
