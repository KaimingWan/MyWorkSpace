package jdk8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal使用，顺便证明它在对象有修改时，无法保证ThreadLocal对象在多线程之间同步
 * @author Wan Kaiming on 2017/1/13
 * @version 1.0
 */
public class ThreadLocalExample {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            executorService.submit(new ThreadLocalWorekr());
        }

        executorService.shutdown();
    }
}

class ThreadLocalWorekr implements Runnable{
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "Initial Value";
        }
    };


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+ "'s ThreadLocal object's old value is "+threadLocal.get());
        threadLocal.set("New value is "+Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName()+ " set new value to ThreadLocal object: "
                + threadLocal.get());

        try {
            //休眠1秒，看看别的线程里面的值修改是否影响了当前Thread
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+ " sleep for a while and the  value of ThreadLocal object is : "
                + threadLocal.get());
    }


}
