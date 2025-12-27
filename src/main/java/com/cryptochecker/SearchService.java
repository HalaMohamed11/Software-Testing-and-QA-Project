package com.cryptochecker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SearchService {

    public List<WebData.Coin> searchByName(List<WebData.Coin> coins, String query) {
        if (query == null) {
            throw new IllegalArgumentException("Search query cannot be null");
        }
        
        String trimmedQuery = query.trim();
        
        if (trimmedQuery.isEmpty()) {
            return new ArrayList<>(coins);
        }
        
        if (trimmedQuery.length() == 0) {
            return new ArrayList<>();
        }
        
        return coins.stream()
                .filter(coin -> coin.name.toLowerCase().contains(trimmedQuery.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<WebData.Coin> searchBySymbol(List<WebData.Coin> coins, String query) {
        if (query == null) {
            throw new IllegalArgumentException("Search query cannot be null");
        }
        
        String trimmedQuery = query.trim();
        
        if (trimmedQuery.isEmpty()) {
            return new ArrayList<>(coins);
        }
        
        return coins.stream()
                .filter(coin -> coin.symbol.equalsIgnoreCase(trimmedQuery))
                .collect(Collectors.toList());
    }
}
