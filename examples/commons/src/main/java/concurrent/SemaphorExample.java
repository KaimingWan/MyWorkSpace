package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量主要功能：
 * 1. 防止一段代码部分同时被N个以上的线程访问。一般用于流量分流。
 * 2. 两个线程之间发送信号量。一般用于互斥资源访问，类似于实现锁。acquire类似于获得锁。无法获得锁则会加入阻塞队列。
 * 许可的数量可以理解为阻塞队列的长度。默认使用非公平许可
 * @author Wan Kaiming on 2017/1/12
 * @version 1.0
 */
public class SemaphorExample {


    public static void main(String[] args) {

        //例1，假设有50个线程需要同时访问一个方法，通过信号量约束成同一时间只能有10个同时进行访问。

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        //非公平的定义方式
        Semaphore semaphore = new Semaphore(10);
        //公平的定义方式 ,第二个参数为true表示使用FIFO的公平策略。
        Semaphore semaphoreFair = new Semaphore(10, true);


        for(int i=0;i<50;i++) {
            //按顺序执行
            executorService.submit(new SemaphorUnfairWorker(semaphore));
        }
        executorService.shutdown();
    }


}





//用于并发的工作线程,用非公平竞争
class SemaphorUnfairWorker implements Runnable{

    private Semaphore semaphore;

    public SemaphorUnfairWorker(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {

            //非公平的策略，因为阻塞队列的线程可能和刚进来尝试acquire的线程一起竞争许可
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+" is working");
            forCallMethod();
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+" is finished");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    //刚方法同一时间最多被10个线程访问
    private void forCallMethod(){
        try {
            //模拟处理时间耗时2秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
