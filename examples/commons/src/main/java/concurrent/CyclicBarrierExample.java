package concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier是线程之间互相等待，等待一个目标达成之后，所有被CyclicBarrier约束的线程才能继续执行
 * <p>
 * Example场景：每个线程代表一个跑步运动员，当裁判和观众准备好后，运动员才一起出发，只要裁判或者观众没有准备好，运动员都等待. 这里的“准备好”可以看成是一个栅栏
 * <p>
 * Created by Kaiming Wan on 2017/1/11.
 */


/**
 * 表示裁判准备好这个栅栏行为，必须这个准备行为的线程执行完毕，其他被栅栏约束的线程才能继续往下执行
 */
class RefereeReadyActionBarrier implements Runnable {


    @Override
    public void run() {
        int prepareTimeMs = new Random().nextInt(10000);
        try {
            Thread.sleep(prepareTimeMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After prepare for "+prepareTimeMs+". Now referees are ready!!");

    }
}

/**
 * 定义“观众准备好”这个栅栏行为，栅栏行为都是一个线程
 */
class AudienceReadyActionBarrier implements Runnable {

    @Override
    public void run() {
        int prepareTimeMs = new Random().nextInt(10000);
        try {
            Thread.sleep(prepareTimeMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After prepare for "+prepareTimeMs+". Now audience are ready!!");
    }
}


/**
 * 定义运动员类，其中包含栅栏成员变量，用于约束运动员
 */

class Player implements Runnable {

    private CyclicBarrier refereeBarrier;
    private CyclicBarrier audienceBarrier;

    public Player(CyclicBarrier refereeBarrier,CyclicBarrier audienceBarrier){
        this.refereeBarrier = refereeBarrier;
        this.audienceBarrier = audienceBarrier;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at refereeBarrier");
            this.refereeBarrier.await();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at audienceBarrier");
            this.audienceBarrier.await();
            System.out.println(Thread.currentThread().getName() +
                    "start to run...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }
}

public class CyclicBarrierExample {
    public static void main(String[] args) {

        //6个运动员线程要被放入线程池
        ExecutorService executorService = Executors.newFixedThreadPool(6);


        //根据裁判的栅栏行为来定义裁判栅栏，用于约束6个线程
        CyclicBarrier refereeBarrier = new CyclicBarrier(6,new RefereeReadyActionBarrier());
        CyclicBarrier audienceBarrier = new CyclicBarrier(6, new AudienceReadyActionBarrier());

        for(int i=0;i<6;i++) {
            executorService.submit(new Player(refereeBarrier, audienceBarrier));
        }

        executorService.shutdown();



    }

}
