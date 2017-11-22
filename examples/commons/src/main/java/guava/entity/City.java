package guava.entity;

/**
 * @author Wan Kaiming on 2016/12/21
 * @version 1.0
 */
public class City {
    private String name;
    private String zipCode;
    private Integer population;
    private String climate;
    private double averageRainfall;

    public City(){}
    public City(String name){
        this(name,null,null);
    }

    public City(String name,String zipCode,Integer population){
        this.name=name;
        this.zipCode=zipCode;
        this.population=population;
    }
}
