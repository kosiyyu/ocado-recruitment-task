package kosiyyu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kosiyyu.BasketSplitter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketSplitterTest {
    private BasketSplitter bs;

    @BeforeEach
    public void setUp() {
        String configPath = "src/main/resources/configuration/config.json";
        bs = new BasketSplitter(configPath);
    }

    @Test
    public void testSplitOneGroup() {
        var list = List.of("Cookies Oatmeal Raisin", "Cheese Cloth", "English Muffin", "Ecolab - Medallion");

        var results = bs.split(list);
        var expected = new LinkedHashMap<String, List<String>>();
        expected.put("Parcel locker", List.of("Cookies Oatmeal Raisin", "Ecolab - Medallion", "Cheese Cloth", "English Muffin"));

        var sortedResults = results.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().sorted().collect(Collectors.toList()),
                        (v1, v2) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));

        var sortedExpected = expected.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().sorted().collect(Collectors.toList()),
                        (v1, v2) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));

        assertEquals(sortedResults, sortedExpected);
    }
}
