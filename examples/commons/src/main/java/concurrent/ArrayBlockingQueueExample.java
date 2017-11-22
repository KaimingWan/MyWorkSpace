package concurrent;

import concurrent.util.Consumer;
import concurrent.util.Producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 用BlockingQueue实现一个生产者消费者模型
 *
 * Created by Kaiming Wan on 2017/1/11.
 */
public class ArrayBlockingQueueExample {

    public static void main(String[] args) throws Exception {
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(consumer).start();
        Thread.sleep(4000);
    }
}
