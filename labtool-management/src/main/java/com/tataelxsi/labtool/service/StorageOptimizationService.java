package com.tataelxsi.labtool.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageOptimizationService {

    private static final int MAX_FAST_ACCESS = 10;

    // LinkedHashMap in access-order mode acts like an LRU cache
    private final LinkedHashMap<String, Boolean> lruCache = new LinkedHashMap<>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
            return size() > MAX_FAST_ACCESS;
        }
    };

    // Call this when parts are used in appointments
    public void updatePartUsage(List<String> partsUsed) {
        if (partsUsed == null) {
            return;
        }
        for (String part : partsUsed) {
            lruCache.put(part, true); // Put will update access order
        }
    }
    public void clearCache() {
    lruCache.clear();
    }

    public List<String> getFastAccessParts() {
        // Return keys in LRU order (least recently used first)
        List<String> result = new ArrayList<>(lruCache.keySet());
        // Reverse to show most recently used first (optional)
        Collections.reverse(result);
        return result;
    }
}

