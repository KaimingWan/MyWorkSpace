package concurrent;

import java.util.concurrent.*;

/**
 * Future接口的实现类FutureTask主要提供三种功能：
 * 1. 判断任务是否完成或者成功取消(isCancelled和isDone方法)
 * 2. 终止任务Cancel方法
 * 3. 能够获取任务执行结果（get方法，该方法会阻塞直到获取到结果）
 * @author Wan Kaiming on 2017/1/12
 * @version 1.0
 */
public class FutureTaskExample {
    public static void main(String[] args) {
        //使用Callable Future来获取执行结果
        getResultByCallableAndFuture();
        //使用Callable FutureTask来获取执行结果.需要把FutureTask对象也扔进线程池才可以使用
        getResultByCallableAndFutureTask();

    }



    public static void getResultByCallableAndFutureTask(){
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
        executor.submit(futureTask);
        executor.shutdown();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            System.out.println("task运行结果"+futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");

    }


    public static void getResultByCallableAndFuture(){
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = executor.submit(task);
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            //result实际上是一个FutureTask对象
            System.out.println("task运行结果"+result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }
}


class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        return sum;
    }
}