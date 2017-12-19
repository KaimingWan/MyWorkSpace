package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Locksupport.park 和Object.wait的比较
 * Created by wanshao
 * Date: 2017/12/19
 * Time: 下午9:38
 **/
public class ParkVsWait {

    static Object shareObject = new Object();

    boolean condition = false;

    public static void main(String[] args) throws InterruptedException {
        ParkVsWait parkVsWait = new ParkVsWait();

        // example1和example2比较他们响应中断方式的不同
        //parkVsWait.example1();
        //parkVsWait.example2();
        // example3和example4比较两种同步原语的开销
        int MAX = 100000;
        Thread example3Worker;
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            example3Worker = new Thread(() -> {
                parkVsWait.example4();
            });
            example3Worker.start();
            example3Worker.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("cost ms:" + (end - start));

    }

    // Example 1: Locksupport.park会更改标志位，但是不抛interruptException来响应中断
    private void example1() {
        Thread t = new Thread(() -> {
            //防止虚假唤醒
            while (condition != true) {

                LockSupport.park();
            }
            System.out.println("thread over." + Thread.currentThread().isInterrupted());
        });
        t.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        condition = true;
        // 中断线程
        t.interrupt();
    }

    // Example 2: Object.wait 会抛异常响应中断
    private void example2() {
        Thread t2 = new Thread(() -> {
            try {
                synchronized (shareObject) {
                    //防止虚假唤醒
                    while (condition != true) { shareObject.wait(3000); }
                }
            } catch (InterruptedException e) {
                System.out.println("Response interrupt");
                e.printStackTrace();
            }

        });
        t2.start();
        System.out.println("start to interrupt");
        t2.interrupt();
    }

    // Example 3: Object.wait 被唤醒需要获取锁
    private void example3() {
        Thread t2 = new Thread(() -> {
            try {
                //获取当前线程的监视器锁
                synchronized (shareObject) {
                    while (condition != true) {
                        System.out.println("start to wait");
                        shareObject.wait();
                    }
                }

            } catch (InterruptedException e) {
                System.out.println("Response interrupt");
                e.printStackTrace();
            }

        });
        t2.start();

        //唤醒在该锁上等待的线程
        synchronized (shareObject) {
            //System.out.println("start to notify");
            condition = true;
            shareObject.notifyAll();
        }
    }

    private void example4() {
        Thread t2 = new Thread(() -> {

            while (condition != true) {
                //System.out.println("start to wait");
                LockSupport.park();
            }

        });
        t2.start();

        //唤醒在该锁上等待的线程
        synchronized (shareObject) {
            //System.out.println("start to notify");
            condition = true;
            LockSupport.unpark(t2);

        }
    }

}

