package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private ProductItemRepository productItemRepository;

    @GetMapping("/api/products/search")
    public Collection<ProductItem> search(@RequestParam("query") String query) {

        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> filteredItems = new ArrayList<>();

        // Check if the query is enclosed in quotes
        boolean isExactMatch = query.startsWith("\"") && query.endsWith("\"");
        String processedQuery = isExactMatch ? query.substring(1, query.length() - 1) : query.toLowerCase();

        for (ProductItem item : allItems) {
            String name = item.getName().toLowerCase();
            String description = item.getDescription().toLowerCase();

            if (isExactMatch) {
                // Exact match case
                if (item.getName().equals(processedQuery) || item.getDescription().equals(processedQuery)) {
                    filteredItems.add(item);
                }
            } else {
                // Partial match case (case-insensitive)
                if (name.contains(processedQuery) || description.contains(processedQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        return filteredItems;
    }
}
