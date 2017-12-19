package concurrent.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by wanshao
 * Date: 2017/12/12
 * Time: 下午3:04
 **/
public class LockSupportExample {

    static final class Worker1 extends Thread {

        public void run(){
            //enable schedule until get permit with 'unpark'，
            LockSupport.park();
            System.out.println("Worker1 is runing...");
        }

    }


    static final class Worker2 extends Thread {
        volatile Thread t;

        public void run(){
            LockSupport.unpark(t);
            System.out.println("Worker2 is runing...");
        }

    }

    public static void main(String[] args) {

        Worker1 worker1 = new Worker1();
        Worker2 worker2 = new Worker2();
        worker2.t=worker1;
        worker1.start();
        worker2.start();
    }
}
