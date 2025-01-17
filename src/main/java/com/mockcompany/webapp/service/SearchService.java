package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    @Autowired
    private ProductItemRepository productItemRepository;

    public Collection<ProductItem> search(String query) {
        Iterable<ProductItem> allItems = productItemRepository.findAll();
        List<ProductItem> filteredItems = new ArrayList<>();

        boolean isExactMatch = query.startsWith("\"") && query.endsWith("\"");
        String processedQuery = isExactMatch ? query.substring(1, query.length() - 1) : query.toLowerCase();

        for (ProductItem item : allItems) {
            String name = item.getName().toLowerCase();
            String description = item.getDescription().toLowerCase();

            if (isExactMatch) {
                if (item.getName().equals(processedQuery) || item.getDescription().equals(processedQuery)) {
                    filteredItems.add(item);
                }
            } else {
                if (name.contains(processedQuery) || description.contains(processedQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        return filteredItems;
    }

    public Map<String, Integer> countHitsByTerms(List<String> searchTerms) {
        Iterable<ProductItem> allItems = productItemRepository.findAll();
        Map<String, Integer> termCounts = new HashMap<>();

        for (String term : searchTerms) {
            String lowerTerm = term.toLowerCase();
            int count = 0;
            for (ProductItem item : allItems) {
                if (item.getName().toLowerCase().contains(lowerTerm) ||
                        item.getDescription().toLowerCase().contains(lowerTerm)) {
                    count++;
                }
            }
            termCounts.put(term, count);
        }
        return termCounts;
    }
}
