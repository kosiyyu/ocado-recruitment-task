package org.kosiyyu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class BasketSplitter {

    private final Map<String, List<String>> configMap;

    public BasketSplitter(String absolutePathToConfigFile) {
        configMap = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePathToConfigFile))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            var map = mapper.readValue(sb.toString(), new TypeReference<Map<String, List<String>>>() {});
            configMap.putAll(map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException();
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, List<String>> basketMap = new LinkedHashMap<>();
        for(var i : items) {
            basketMap.put(i, configMap.get(i));
        }
        basketMap = Utils.sortedBasket(basketMap);

        Map<String, List<String>> result = new LinkedHashMap<>();
        Iterator<String> iterator = basketMap.keySet().iterator();
        while (iterator.hasNext()) {
            String i = iterator.next();
            Map<String, Integer> deliveryQuantityMap = Utils.deliveriesQuantityMapReversed(basketMap);
            List<String> deliveryList = basketMap.get(i);

            List<String> matches = deliveryQuantityMap.keySet().stream()
                    .filter(deliveryList::contains)
                    .toList();

            int maxMatchValue = matches.stream()
                    .mapToInt(deliveryQuantityMap::get)
                    .max()
                    .orElse(0);

            List<String> maxMatches = matches.stream()
                    .filter(key -> deliveryQuantityMap.get(key) == maxMatchValue)
                    .toList();

            String maxMatch;
            if(maxMatches.size() > 1) {
                maxMatch = maxMatches.stream()
                        .filter(result::containsKey)
                        .max(Comparator.comparing(key -> result.get(key).size()))
                        .orElse(maxMatches.getFirst());
            } else {
                maxMatch = maxMatches.getFirst();
            }

            if (result.containsKey(maxMatch)) {
                List<String> resultList = result.get(maxMatch);
                resultList.add(i);
                result.put(maxMatch, resultList);
            } else {
                List<String> newList = new ArrayList<>();
                newList.add(i);
                result.put(maxMatch, newList);
            }
            iterator.remove();
        }
        return result;
    }
}
