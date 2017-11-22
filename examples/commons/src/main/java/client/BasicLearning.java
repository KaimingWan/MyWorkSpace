package client;

/**
 * @author Wan Kaiming on 2016/12/13
 * @version 1.0
 */
public class BasicLearning {
    public static void main(String[] args) {
        String str = "Hello world.";
        byte[] data = str.getBytes();
        String msg = new String(data);
        System.out.println(msg);

    }
}
