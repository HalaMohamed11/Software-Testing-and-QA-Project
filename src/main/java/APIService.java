public class APIService {
    public void fetchData(String type) throws APIException {
        if(type.equals("simulateTimeout")) throw new APIException("API Timeout");
        if(type.equals("simulateRateLimit")) throw new APIException("Rate Limit Reached");
    }
}
