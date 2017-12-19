package basic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 *  有两个工作线程. 开始时，第一个线程挂起自己; 第二个线程唤醒第一个线程，再挂起自己;
 * 第一个线程醒来之后唤醒第二个线程, 再挂起自己. 就这样一来一往,互相唤醒对方, 挂起自己. 代码如下:
 * Created by wanshao
 * Date: 2017/12/12
 * Time: 下午2:38
 **/
public class CpuContextSwitchExample {
    static final int RUNS = 3;
    //static final int ITERATES = 1000000;
    static final int ITERATES = 2;
    static AtomicReference turn = new AtomicReference();

    static final class WorkerThread extends Thread {
        volatile Thread other;
        volatile int nparks;

        public void run() {
            final AtomicReference t = turn;
            final Thread other = this.other;
            if (turn == null || other == null)
                throw new NullPointerException();
            int p = 0;
            for (int i = 0; i < ITERATES; ++i) {
                while (!t.compareAndSet(other, this)) {
                    LockSupport.park();
                    ++p;
                }
                LockSupport.unpark(other);
            }
            LockSupport.unpark(other);
            nparks = p;
            System.out.println("parks: " + p);

        }
    }

    static void test() throws Exception {
        WorkerThread a = new WorkerThread();
        WorkerThread b = new WorkerThread();
        a.setName("workerA");
        b.setName("workerB");
        a.other = b;
        b.other = a;
        turn.set(a);
        long startTime = System.nanoTime();
        a.start();
        //b.start();
        a.join();
        //b.join();
        long endTime = System.nanoTime();
        int parkNum = a.nparks + b.nparks;
        System.out.println("Average time: " + ((endTime - startTime) / parkNum)
            + "ns");
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < RUNS; i++) {
            test();
        }
    }
}
