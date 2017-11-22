package concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch的Example。6个运动员参加比赛，必须等6个运动员全部跑完，裁判才能宣布结果
 *
 * CountDownLatch是有1个线程（裁判线程）等待其他所有被count的线程（运动员线程）
 * Created by Kaiming Wan on 2017/1/11.
 */
public class CountDownLatchExample {
    private static final int N = 10;

    public static void main(String[] args) throws InterruptedException {
        //主线程为裁判，子线程为运动员
        final CountDownLatch operatorNum=new CountDownLatch(6);
        for(int i=0;i<6;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Long s=System.currentTimeMillis();
                    Random random=new Random();
                    try {
                        Thread.sleep(random.nextInt(10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Long time=System.currentTimeMillis()-s;
                    System.out.println(String.format("运动员%s跑完,花了%s", Thread.currentThread().getName(),time));
                    //一个运动员跑完了
                    operatorNum.countDown();
                }
            }).start();
        }
        try {
            operatorNum.await();
            System.out.println(String.format("所有运行员都跑完了，裁判%s可宣布结果啦！", Thread.currentThread().getName()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
