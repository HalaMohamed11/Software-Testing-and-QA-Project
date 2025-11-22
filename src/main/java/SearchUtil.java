public class SearchUtil {
    public String searchByName(String name) {
        if(name == null || name.trim().isEmpty()) return "No results found";
        return name.trim();
    }
}

