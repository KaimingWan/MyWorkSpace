package guava.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Wan Kaiming on 2016/12/21
 * @version 1.0
 */
public class State {
    private String name;
    private String code;
    private String region;
    private Set<City> mainCities =new HashSet<City>();

    public State(){}

    public State(String name){
        this.name=name;
    }
}
