package com.tataelxsi.labtool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tataelxsi.labtool.service.StorageOptimizationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StorageOptimizationServiceTest {

    private StorageOptimizationService service;

    @BeforeEach
    public void setUp() {
        service = new StorageOptimizationService();
    }

    @Test
    public void testUpdatePartUsageAndGetFastAccessParts() {
        service.updatePartUsage(Arrays.asList("Oil Filter", "Engine Oil"));
        service.updatePartUsage(Arrays.asList("Engine Oil", "Brake"));
        service.updatePartUsage(Arrays.asList("Brake", "Brake Filter"));

        List<String> fastParts = service.getFastAccessParts();

        // Check if the expected parts are present in the fast-access list
        assertTrue(fastParts.contains("Brake"));
        assertTrue(fastParts.contains("Engine Oil"));
        assertTrue(fastParts.contains("Brake Filter"));

        // Fast access list size should not exceed 10
        assertTrue(fastParts.size() <= 10);
    }
    @Test
    public void testUpdateWithEmptyList() {
        service.updatePartUsage(List.of()); // empty list
        List<String> fastParts = service.getFastAccessParts();
        assertTrue(fastParts.isEmpty(), "Fast access parts should be empty after empty update");
    }
    @Test
    public void testDuplicatePartsInSingleUpdate() {
        service.updatePartUsage(Arrays.asList("Oil Filter", "Oil Filter", "Brake"));
        List<String> fastParts = service.getFastAccessParts();

        // Should contain unique parts only
        assertEquals(2, fastParts.size());
        assertTrue(fastParts.contains("Oil Filter"));
        assertTrue(fastParts.contains("Brake"));
    }
    @Test
    public void testAccessOrder() {
        service.updatePartUsage(List.of("PartA", "PartB"));
        service.updatePartUsage(List.of("PartC"));
        service.updatePartUsage(List.of("PartA")); // PartA accessed again

        List<String> fastParts = service.getFastAccessParts();

        // PartA should be the most recently accessed (at index 0 after reversing)
        assertEquals("PartA", fastParts.get(0));
    }
    @Test
    public void testUpdateWithNullParts() {
        service.updatePartUsage(null);  // Should not throw exception

        // Should still be empty
        assertTrue(service.getFastAccessParts().isEmpty());
    }



    @Test
    public void testLRUEviction() {
        // Add 12 different parts, only the 10 most recent should remain
        for (int i = 1; i <= 12; i++) {
            service.updatePartUsage(List.of("Part" + i));
        }

        List<String> fastParts = service.getFastAccessParts();

        // Size check
        assertEquals(10, fastParts.size());

        // Oldest (Part1, Part2) should be evicted
        assertFalse(fastParts.contains("Part1"));
        assertFalse(fastParts.contains("Part2"));

        // Most recent should be present
        assertTrue(fastParts.contains("Part12"));
    }
}
