package kosiyyu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class UtilsTest {
    public static void printMap(Map map) {
        for (var key : map.keySet()) {
            System.out.println("key: " + key + ", value: " + map.get(key));
        }
    }

    public static Map<String, Integer> deliveriesQuantityMapReversed(Map<String, List<String>> map) {
        // create quantity map
        Map<String, Integer> quantityMap = new LinkedHashMap<>();
        for (var str : map.keySet()) {
            var list = map.get(str);
            for (var listItem : list) {
                quantityMap.put(listItem, quantityMap.getOrDefault(listItem, 1) + 1);
            }
        }
        // sort quantity map
        quantityMap  = quantityMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> oldVal, LinkedHashMap::new));

        return quantityMap;
    }

    public static Map<String, List<String>> sortedBasket(Map<String, List<String>> map) {
        return map.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(i -> i.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> oldVal, LinkedHashMap::new));
    }

    public static List<String> loadBasket(String absolutePathToBasket) {
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePathToBasket))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(sb.toString(), new TypeReference<List<String>>() {});
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
