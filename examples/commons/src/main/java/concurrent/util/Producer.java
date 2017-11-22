package concurrent.util;

import java.util.concurrent.BlockingQueue;

/**
 * Producer向一个共享的BlockingQueue中放入字符串
 * Created by Kaiming Wan on 2017/1/11.
 */
public class Producer implements Runnable {
    protected BlockingQueue queue = null;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
