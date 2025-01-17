package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Management decided it is super important that we have lots of products that match the following terms.
 * So much so, that they would like a daily report of the number of products for each term along with the total
 * product count.
 */
@RestController
public class ReportController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        // Define the search terms for the report
        List<String> searchTerms = Arrays.asList("Cool", "Amazing", "Perfect", "Kids");

        // Get the total product count
        int totalProductCount = searchService.search("").size();

        // Get the count of products matching each search term
        Map<String, Integer> searchTermHits = searchService.countHitsByTerms(searchTerms);

        // Create and populate the response
        SearchReportResponse response = new SearchReportResponse();
        response.setProductCount(totalProductCount);
        response.setSearchTermHits(searchTermHits);

        return response;
    }
}
