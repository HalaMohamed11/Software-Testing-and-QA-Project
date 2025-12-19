package com.cryptochecker.Integration;

import com.cryptochecker.APIException;

public class APIService {

    public void fetchData(String endpoint) throws APIException {
        if(endpoint.equals("simulateTimeout")) {
            throw new APIException("API Timeout");
        } else if(endpoint.equals("simulateRateLimit")) {
            throw new APIException("Rate Limit Reached");
        }
    }
}
