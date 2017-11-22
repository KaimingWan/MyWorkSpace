package jdk8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wan Kaiming on 2016/12/7
 * @version 1.0
 */
public class LearnJava8 {
    public static void main(String[] args) {
        List<String> stringCollection = new ArrayList<>();

        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

    }


}
