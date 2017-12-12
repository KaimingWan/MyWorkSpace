package concurrent.lock;

import java.util.concurrent.TimeUnit;

/**
 * Created by wanshao
 * Date: 2017/11/27
 * Time: 上午11:00
 **/
public class JstackExample {
    public static void main(String[] args) {
        //WAITING example,这个例子现在没法使得其他线程BLOCKED了，可见synchronized的实现已经有所不同，不会一上来就用最重的锁。
        myExample();

    }

    private static void myExample() {
        final Thread thread = new Thread() {
            public void run() {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        wait();       //
                        //sleep(30000);   //使用sleep的时候，main方法被阻塞，等待kami线程占用的锁
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        thread.setName("kami");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        synchronized (thread) {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            thread.notify();
        }
    }

}
