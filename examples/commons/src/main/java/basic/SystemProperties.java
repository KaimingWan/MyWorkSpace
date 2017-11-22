package basic;

/**
 * @author Wan Kaiming on 2016/12/22
 * @version 1.0
 */
public class SystemProperties {
    public static void main(String[] args) {
        String conf = System.getProperty("my.conf", "classpath:my.properties");
        System.out.println(conf);
    }
}
