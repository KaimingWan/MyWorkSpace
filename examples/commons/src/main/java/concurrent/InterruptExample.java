package concurrent;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wanshao
 * Date: 2017/12/18
 * Time: 下午3:42
 **/
public class InterruptExample {
    public static void main(String[] args) throws InterruptedException {
        InterruptTask interruptTask = new InterruptTask();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(interruptTask);
        Thread.sleep(100);
        interruptTask.cancel();
        executorService.shutdown();

    }
}

/**
 * 一个响应中断的任务
 */
class InterruptTask implements Callable<Integer> {
    private BlockingQueue<Task> queue;
    //保存要被interrupt的线程
    Thread t;

    @Override
    public Integer call() throws InterruptedException {
        System.out.println("start a blocked task");
        try {
            t = Thread.currentThread();
            Thread.currentThread().sleep(50000);
        } catch (InterruptedException e) {
            System.out.println("be interrupted");
            e.printStackTrace();
        }

        return 0;
    }

    public void cancel() {
        System.out.println("cacel a task....");
        //这里直接调用Thread.currentThread()会获取到main线程，而不是线程池里面的线程

        if (!t.isInterrupted()) {
            t.interrupt();
        }
    }
}



