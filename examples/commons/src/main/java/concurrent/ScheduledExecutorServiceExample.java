package concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 该类用于周期地、定期地执行线程池里面的线程
 * @author Wan Kaiming on 2017/1/12
 * @version 1.0
 */
public class ScheduledExecutorServiceExample {

    public static void main(String[] args) {

        executeFixedTime();
        executeFixedRate();
        executeFixedDelay();

    }

    /**
     * 定期执行
     */
    public static void executeFixedTime(){

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        //执行一个定时线程,延迟1秒执行
        scheduledExecutorService.schedule(() -> {
            System.out.println(Thread.currentThread().getName()+ " will work after 1 seconds.");
            return null;
        },1, TimeUnit.SECONDS);


        scheduledExecutorService.shutdown();
    }


    /**
     * 按照FixedRate执行,period间隔以上次线程“开始执行”的时间和下个线程开始执行的时间作为间隔
     */
    public static void executeFixedRate(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println();
            }
        }, 1, 2, TimeUnit.SECONDS);


        scheduledExecutorService.shutdown();
    }

    /**
     * 按照固定delay执行，这里的时间间隔是上次线程“结束执行”的时间和下个线程开始执行的时间作为间隔
     */
    public static void executeFixedDelay(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        scheduledExecutorService.scheduleWithFixedDelay(() -> {
        }, 1, 1, TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();
    }
}


