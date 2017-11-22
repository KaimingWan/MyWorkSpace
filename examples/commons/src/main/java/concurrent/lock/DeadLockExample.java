package concurrent.lock;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kaiming Wan on 2017/1/12.
 */
public class DeadLockExample {



    public static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        LoggingWidget loggingWidget = new LoggingWidget();

        for(int i=0;i<10;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {


                    try {
                        //多线程访问上锁的代码块.这里是访问同一个实例。
                        loggingWidget.dosomething();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();


    }

}


 class Widget{
    public synchronized void dosomething() throws InterruptedException {
        System.out.println("Widget want to do something.");
    }
}
 class LoggingWidget extends Widget{
    public synchronized void dosomething() throws InterruptedException {
        System.out.println("Start to call super class Widget do something.");
        super.dosomething();
    }
}
