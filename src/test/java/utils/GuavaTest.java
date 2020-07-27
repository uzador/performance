package utils;

import com.google.common.collect.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GuavaTest {

    @Test
    @DisplayName("multiset add and setCount")
    public void multiset() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.add("Potter");
        bookStore.add("Potter");
        bookStore.add("Potter");

        assertThat(bookStore.contains("Potter")).isTrue();
        assertThat(bookStore.count("Potter")).isEqualTo(3);

        bookStore.setCount("Potter", 50);
        assertThat(bookStore.count("Potter")).isEqualTo(50);

        assertThatThrownBy(() -> bookStore.setCount("Potter", -1))
                .isInstanceOf(IllegalArgumentException.class);

        bookStore.add(null);
        bookStore.add(null);
        System.out.println(bookStore);
        System.out.println(bookStore.size());

        Multiset<String> bookStore1 = HashMultiset.create();
        bookStore1.add("Potter");
        bookStore1.add("Potter");
        bookStore1.add("Potter");
        bookStore1.add(null);
        bookStore1.add(null);

        Multiset<String> bookStore2 = HashMultiset.create();
        bookStore2.add("Potter");

        Map<String, Multiset<String>> map = new HashMap<>();
        map.put("1", bookStore);
        map.put("2", bookStore1);
        map.put("3", bookStore2);

        System.out.println("=====================================");
        System.out.println(bookStore.size());
        System.out.println(bookStore1.size());
        System.out.println(bookStore2.size());
        System.out.println(map.values());
    }

    @Test
    @DisplayName("multimap put size get clear")
    public void multimap() {
        ListMultimap<String, Integer> hashListMultimap = MultimapBuilder
                .hashKeys().arrayListValues().build();

        hashListMultimap.put("key", 1);
        hashListMultimap.put("key", 2);
        hashListMultimap.put("key", 3);
        hashListMultimap.put("key", 3);
        hashListMultimap.put("key", 3);

        assertThat(hashListMultimap.size()).isEqualTo(5);
        assertThat(hashListMultimap.get("key")).contains(1, 2, 3);

        List<Integer> listView = hashListMultimap.get("key");
        listView.clear();
        listView.add(10);
        listView.add(11);
        listView.add(12);

        assertThat(hashListMultimap.size()).isEqualTo(3);
        assertThat(hashListMultimap.get("key")).contains(10, 11, 12);

        SetMultimap<String, Integer> hashSetMultimap =
                MultimapBuilder.hashKeys().hashSetValues().build();

        hashSetMultimap.put("key", 1);
        hashSetMultimap.put("key", 2);
        hashSetMultimap.put("key", 3);
        hashSetMultimap.put("key", 3);
        hashSetMultimap.put("key", 3);

        assertThat(hashSetMultimap.size()).isEqualTo(3);
        assertThat(hashSetMultimap.get("key")).contains(1, 2, 3);
    }

    @Test
    @DisplayName("bimap add and setCount")
    public void bimap() {
        BiMap<String, String> map = HashBiMap.create();
        map.put("RU12345", "12345");

        assertThat(map.containsKey("RU12345")).isTrue();
        assertThat(map.containsValue("12345")).isTrue();
        assertThat(map.get("RU12345")).isEqualTo("12345");
        assertThat(map.inverse().get("12345")).isEqualTo("RU12345");

        assertThatThrownBy(() -> map.put("UK12345", "12345"))
                .isInstanceOf(IllegalArgumentException.class);

        map.forcePut("UK12345", "12345");
        assertThat(map.get("UK12345")).isEqualTo("12345");
        assertThat(map.get("RU12345")).isNull();
    }
}
